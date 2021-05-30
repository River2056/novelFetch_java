package com.river.application;

import com.river.module.DataCleanse;
import com.river.module.FileOperation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUBookFetch {
    public void startFetch() {
        Scanner sc = new Scanner(System.in);
        File outputDirectory = FileOperation.initOutputFolder();
        String baseUrl = null;
        System.out.println("please provide index page url: ");
        String url = sc.nextLine();
        Pattern urlPattern = Pattern.compile("https://.*?com/");
        Matcher m = urlPattern.matcher(url);
        if(m.find()) baseUrl = m.group(0);

        try {
            Document document = Jsoup.connect(url).get();
            Element bodyContent = document.body();

            Elements bookNameElement = bodyContent.select(".bookname b");
            String bookName = bookNameElement.text();
            System.out.println(bookName);
            File bookNameDir = new File(outputDirectory.getPath() + File.separator + bookName);
            if(!bookNameDir.exists()) bookNameDir.mkdir();

            Element chapterListUl = bodyContent.getElementById("chapterList");
            Elements allChapterElements = chapterListUl.select("li");

            // starting generating novels
            int count = 1;
            String episodeTitle = "";
            for(Element e: allChapterElements) {
                String href = e.select("a").attr("href");
                if(href == null || "".equals(href)) {
                    episodeTitle = e.text();
                } else {
                    String chapterLink = String.format("%s%s", baseUrl, href);
                    Document chapterDocument = Jsoup.connect(chapterLink).get();
                    // data cleanse
                    DataCleanse data = new DataCleanse(chapterDocument, DataCleanse.UU_BOOK);
                    data.setEpisodeTitle(episodeTitle);
                    String chapterTitle = data.getChapterTitle();
                    String content = data.getContent();
                    FileOperation.outputFile(outputDirectory, bookName, String.format("%s_%s", String.valueOf(count), data.getEpisodeTitle()), chapterTitle, content, DataCleanse.UU_BOOK);
                    count++;
                }
            }
            FileOperation.generateZip(outputDirectory, bookName);
            sc.close();
        } catch(IOException e) {
            System.out.printf("Error: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }
}
