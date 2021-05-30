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

public class QBNovelFetch {
    public void startFetch() {
        Scanner sc = new Scanner(System.in);
        File outputDirectory = FileOperation.initOutputFolder();
        System.out.println("please provide index page url: ");
        String url = sc.nextLine();
        Pattern baseUrlPattern = Pattern.compile("https://.*?net");
        Matcher m = baseUrlPattern.matcher(url);
        String baseUrl = null;
        if(m.find()) baseUrl = m.group(0);
//        String baseUrl = "https://www.23qb.net";
//        String url = "https://www.23qb.net/book/1081/";
        try {
            Document indexPageDocument = Jsoup.connect(url).get();
            Element chapterListUl = indexPageDocument.getElementById("chapterList");
            String bookName = indexPageDocument.select(".d_title h1").text();
            File bookNameDir = new File(outputDirectory + File.separator + bookName);
            if(!bookNameDir.exists()) bookNameDir.mkdir();
            Element firstChapter = chapterListUl.child(0);

            // fetch contents and then next page, loop
            int count = 1;
            String firstChapterLink = baseUrl + firstChapter.select("a").attr("href");
            while(!firstChapterLink.equals(url)) {
                Document chapterContentPage = Jsoup.connect(firstChapterLink).get();
                Elements box = chapterContentPage.select(".mlfy_page");
                Element nextPageATag = box.select("a").last();
                String nextPageLink = nextPageATag.select("a").attr("href");
                firstChapterLink = baseUrl + nextPageLink;
                DataCleanse data = new DataCleanse(chapterContentPage, DataCleanse.QB_NOVEL);
                FileOperation.outputFile(outputDirectory, bookName, String.valueOf(count), data.getChapterTitle(), data.getContent(), DataCleanse.QB_NOVEL);
                count++;
            }

            FileOperation.generateZip(outputDirectory, bookName);
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
