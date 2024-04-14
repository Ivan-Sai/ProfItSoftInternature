package org.example;


import org.example.controllers.StatisticsCountController;

import java.io.IOException;

public class Main {

    private static final StatisticsCountController statisticsCountController = new StatisticsCountController();
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 2) {
            System.out.println("Usage: java App <path to JSON files> <attribute for statistics>");
            System.exit(1);
        }

        statisticsCountController.run(args[0], args[1]);

    }
}