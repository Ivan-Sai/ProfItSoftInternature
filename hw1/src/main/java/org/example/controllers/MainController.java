package org.example.controllers;


import org.example.services.JsonCountStatisticsService;
import org.example.services.XmlWriteService;

import java.io.IOException;


public class MainController {

    private final JsonCountStatisticsService jsonCountStatisticsService = new JsonCountStatisticsService();
    private final XmlWriteService xmlWriteService = new XmlWriteService();

    public void run(String path, String arg) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        xmlWriteService.writeMapToXml(jsonCountStatisticsService.readJson(path, arg,8),arg);
        long finish = System.currentTimeMillis();
        System.out.println("Execution time: " + (finish - start) + " ms");
    }
}
