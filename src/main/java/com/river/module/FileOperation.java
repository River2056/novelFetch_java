package com.river.module;


import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileOperation {
    public static File initOutputFolder() {
        File outputDirectory = new File("./output/");
        if(!outputDirectory.exists()) {
            outputDirectory.mkdir();
        }
        return outputDirectory;
    }

    public static void outputFile(File outputDirectory, String bookName, String episodeTitle, String chapterTitle, String content, String mode) throws IOException {
        File dest;
        PrintWriter pw;
        switch(mode) {
            case DataCleanse.UU_BOOK:
            case DataCleanse.QB_NOVEL:
                // write to file
                chapterTitle = chapterTitle.replaceAll("\\s+", "_");
                chapterTitle = chapterTitle.replaceAll("[/\"]", "_");
                dest = new File(String.format("%s%s%s%s", outputDirectory.getPath() + File.separator, bookName + File.separator, episodeTitle + "-", chapterTitle + ".txt"));
                pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dest, false), StandardCharsets.UTF_8));
                pw.println(chapterTitle);
                pw.println(content);
                pw.close();
                System.out.printf("done writing: %s%n", dest.getName());
                break;
        }
    }

    public static void generateZip(File outputDirectory, String bookName) {
        try {
            new ZipFile(outputDirectory.getPath() + File.separator + bookName + ".zip").addFolder(new File(outputDirectory + File.separator + bookName));
            System.out.printf("Done Zipping: %s%n", bookName);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

//    public static void generateZip(File outputDirectory, String bookName) {
//        String source = outputDirectory.getPath() + File.separator + bookName;
//        try {
//            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(source + ".zip"));
//            Files.walkFileTree(Path.of(source), new SimpleFileVisitor<>() {
//
//                @Override
//                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                    try(FileInputStream fis = new FileInputStream(file.toFile())) {
//                        Path targetFilePath = Path.of(source).relativize(file);
//                        zos.putNextEntry(new ZipEntry(targetFilePath.toString()));
//
//                        int len;
//                        byte[] bytes = new byte[1024];
//                        while((len = fis.read(bytes)) > 0) {
//                            zos.write(bytes, 0, len);
//                        }
//                        zos.closeEntry();
//
//                        System.out.printf("File zipped: %s%n", file);
//                    }
//                    return FileVisitResult.CONTINUE;
//                }
//
//                @Override
//                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
//                    System.err.printf("Unable to zip: %s%n%s%n", file, exc);
//                    return FileVisitResult.CONTINUE;
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
