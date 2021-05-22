package com.river.test;

import java.io.File;

public class TestFilePath {
    public static void main(String[] args) {
        File outputDirectory = new File("./output/");
        if(!outputDirectory.exists()) {
            outputDirectory.mkdir();
        }

        String chapter = "demo01";

        System.out.printf("%s%s%n", outputDirectory.getPath() + File.separator, chapter + ".txt");
    }
}
