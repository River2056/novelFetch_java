package com.river.module;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataCleanse {
    public static final String UU_BOOK = "uubook";
    public static final String QB_NOVEL = "qb";

    private String episodeTitle;
    private String chapterTitle;
    private String content;

    public DataCleanse() {}

    public DataCleanse(String chapterTitle, String content) {
        this.chapterTitle = chapterTitle;
        this.content = content;
    }


    public DataCleanse(Document chapterDocument, String website) {
        Element chapterBody = chapterDocument.body();
        String chapterTitle;
        String content;
        switch(website) {
            // UU看書專用
            case UU_BOOK:
                chapterTitle = chapterBody.select("h3").text();
                this.setChapterTitle(chapterTitle);
                // remove ads
                chapterBody.select(".ad_content").remove();
                chapterBody.select(".box").remove();
                content = chapterBody.getElementById("bookContent").html();
                content = content.replaceAll("</?p>", "\n");
                content = content.replaceAll("\t", "");
                content = content.replaceAll("&nbsp;", "");
                content = content.replaceAll("<br>", "\n");
                content = content.trim();
                this.setContent(content);
                break;
            // 鉛筆小説
            case QB_NOVEL:
                chapterTitle = chapterBody.select("h1").text();
                this.setChapterTitle(chapterTitle);
                this.setEpisodeTitle(chapterTitle);

                chapterBody.select(".mlfy_page").remove();
                content = chapterBody.select("p").html();
                this.setContent(content);
                break;
        }
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }
}
