package org.example.services;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonReadServiceTest {

    @Test
    void testInvalidPath () {
        String path = "./src/test/java/resources/invalid";
        String arg = "genre";
        int threadCount = 1;
        JsonCountStatisticsService jsonCountStatisticsService = new JsonCountStatisticsService();

        try {
            Map<String, Integer> result = jsonCountStatisticsService.readJson(path, arg, threadCount);

            assertTrue(result.isEmpty());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testInvalidArg () {
        String path = "./src/test/java/resources/out";
        String arg = "invalid";
        int threadCount = 1;
        JsonCountStatisticsService jsonCountStatisticsService = new JsonCountStatisticsService();

        try {
            Map<String, Integer> result = jsonCountStatisticsService.readJson(path, arg, threadCount);

            assertTrue(result.isEmpty());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEmptyFolder() {
        String path = "./src/test/java/resources/emptyFolder";
        String arg = "genre";
        int threadCount = 1;
        JsonCountStatisticsService jsonCountStatisticsService = new JsonCountStatisticsService();

        try {
            Map<String, Integer> result = jsonCountStatisticsService.readJson(path, arg, threadCount);

            assertTrue(result.isEmpty());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testValidPathAndArg () {
        String path = "./src/test/java/resources/out";
        String arg = "genre";
        int threadCount = 1;
        JsonCountStatisticsService jsonCountStatisticsService = new JsonCountStatisticsService();

        LinkedHashMap<String,Integer> testMap = new LinkedHashMap<>();
        testMap.put("Historical Fiction", 2);
        testMap.put("Gothic", 1);
        testMap.put("Science Fiction", 1);
        testMap.put("Epic Novel", 1);
        testMap.put("Magical Realism", 1);

        try {
            Map<String, Integer> result = jsonCountStatisticsService.readJson(path, arg, threadCount);
            assertEquals(testMap, result);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
