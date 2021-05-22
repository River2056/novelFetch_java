package com.river.module;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class FileOperation {
    public static void outputFileFromUU(File outputDirectory, String episodeTitle, String chapterTitle, String content) throws IOException {
        // write to file
        chapterTitle = chapterTitle.replaceAll("\\s+", "_");
        chapterTitle = chapterTitle.replaceAll("[/\"]", "_");
        File dest = new File(String.format("%s%s%s", outputDirectory.getPath() + File.separator, episodeTitle + "-", chapterTitle + ".txt"));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dest, false), StandardCharsets.UTF_8));
        pw.println(chapterTitle);
        pw.println(content);
        pw.close();
        System.out.printf("done writing: %s%n", dest.getName());
    }
}
