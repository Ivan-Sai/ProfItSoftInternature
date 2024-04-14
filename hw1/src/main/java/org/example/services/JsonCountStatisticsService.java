package org.example.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class JsonCountStatisticsService {

    private final Map<String,Integer> statistics = new HashMap<>();

    public Map<String,Integer> readJson(String path, String arg, int threadCount) throws IOException, InterruptedException {
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()){
            return statistics;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return statistics;
        }
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();
        JsonFactory factory = new JsonFactory();
        for (File file : files) {
            if (file.getName().endsWith(".json")) futures.add(executor.submit(() -> parseFile(file, factory, arg)));
        }
        for (Future<Map<String, Integer>> future : futures) {
            try {
                mergeStatistics(future.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return sortByValue(statistics);
    }

    private Map<String, Integer> parseFile(File file, JsonFactory factory, String arg) throws IOException {
        Map<String, Integer> fileStatistics = new HashMap<>();
        try (JsonParser parser = factory.createParser(file)) {
            while (parser.nextToken() != null) {
                String fieldName = parser.getCurrentName();
                if (arg.equals(fieldName)) {
                    parser.nextToken();
                    String value = parser.getText();
                    if ("genre".equals(arg)){
                        List<String> genres = List.of(value.split(","));
                        for (String genre : genres) {
                            fileStatistics.put(genre.strip(), fileStatistics.getOrDefault(genre.strip(), 0) + 1);
                        }
                    }
                    else {
                        fileStatistics.put(value.strip(), fileStatistics.getOrDefault(value.strip(), 0) + 1);
                    }
                }
            }
        }
        return fileStatistics;
    }

    private synchronized void mergeStatistics(Map<String, Integer> fileStatistics) {
        for (Map.Entry<String, Integer> entry : fileStatistics.entrySet()) {
            statistics.put(entry.getKey(), statistics.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
    }

    public Map<String,Integer> sortByValue(Map<String, Integer> map) {
        return map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
