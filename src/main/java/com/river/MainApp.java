package com.river;

import com.river.module.DataCleanse;
import com.river.module.FileOperation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class MainApp {
    public static void main(String[] args) {
        File outputDirectory = new File("./output/");
        if(!outputDirectory.exists()) {
            outputDirectory.mkdir();
        }

        String baseUrl = "https://t.uukanshu.com/";
        String url = "https://t.uukanshu.com/book.aspx?id=128714#gsc.tab=0";
        try {
            Document document = Jsoup.connect(url).get();
            Element bodyContent = document.body();

            Element chapterListUl = bodyContent.getElementById("chapterList");
            Elements allChapterELements = chapterListUl.select("li");

            int count = 1;
            String episodeTitle = "";
            for(Element e: allChapterELements) {
                String href = e.select("a").attr("href");
                if(href == null || "".equals(href)) {
                    episodeTitle = e.text();
                } else {
                    String chapterLink = String.format("%s%s", baseUrl, href);
                    Document chapterDocument = Jsoup.connect(chapterLink).get();
                    // data cleanse
                    DataCleanse data = new DataCleanse(chapterDocument);
                    data.setEpisodeTitle(episodeTitle);
                    String chapterTitle = data.getChapterTitle();
                    String content = data.getContent();
                    FileOperation.outputFileFromUU(outputDirectory, String.format("%s_%s", String.valueOf(count), data.getEpisodeTitle()), chapterTitle, content);
                    count++;
                }
            }
        } catch(IOException e) {
            System.out.printf("Error: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }
}
