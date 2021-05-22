package com.river.module;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DataCleanse {
    private String episodeTitle;
    private String chapterTitle;
    private String content;

    public DataCleanse() {}

    public DataCleanse(String chapterTitle, String content) {
        this.chapterTitle = chapterTitle;
        this.content = content;
    }

    // UU看書專用
    public DataCleanse(Document chapterDocument) {
        Element chapterBody = chapterDocument.body();
        String chapterTitle = chapterBody.select("h3").text();
        this.setChapterTitle(chapterTitle);
        // remove ads
        chapterBody.select(".ad_content").remove();
        chapterBody.select(".box").remove();
        String content = chapterBody.getElementById("bookContent").html();
        content = content.replaceAll("</?p>", "\n");
        content = content.replaceAll("\t", "");
        content = content.replaceAll("&nbsp;", "");
        content = content.replaceAll("<br>", "\n");
        content = content.trim();
        this.setContent(content);
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
