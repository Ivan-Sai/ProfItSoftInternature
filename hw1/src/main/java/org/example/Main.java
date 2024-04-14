package org.example;

import lombok.RequiredArgsConstructor;
import org.example.controllers.MainController;

import java.io.IOException;

public class Main {

    private static final MainController mainController = new MainController();
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 2) {
            System.out.println("Usage: java App <path to JSON files> <attribute for statistics>");
            System.exit(1);
        }

        mainController.run(args[0], args[1]);

    }
}