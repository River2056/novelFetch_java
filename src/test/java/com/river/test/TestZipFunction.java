package com.river.test;

import com.river.module.FileOperation;

import java.io.File;

public class TestZipFunction {
    public static void main(String[] args) {
        File outputDirectory = FileOperation.initOutputFolder();
        FileOperation.generateZip(outputDirectory, "test");
    }
}
