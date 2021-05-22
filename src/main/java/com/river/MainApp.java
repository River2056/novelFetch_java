package com.river;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args) {
        String baseUrl = "https://t.uukanshu.com/";
        String url = "https://t.uukanshu.com/book.aspx?id=128714#gsc.tab=0";
        try {
            Document document = Jsoup.connect(url).get();
            Element bodyContent = document.body();

            Element chapterListUl = bodyContent.getElementById("chapterList");
            Elements aTags = chapterListUl.select("a");

            Element e = aTags.get(0);
            String chapterLink = String.format("%s%s", baseUrl, e.attr("href"));
            Document chapterDocument = Jsoup.connect(chapterLink).get();
            System.out.println(chapterDocument.body());

//            for(Element e: aTags) {
//
//            }
        } catch(IOException e) {
            System.out.printf("Error: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }
}
