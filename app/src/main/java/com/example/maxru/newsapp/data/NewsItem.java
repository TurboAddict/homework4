package com.example.maxru.newsapp.data;

/**
 * Created by maxru on 7/2/17.
 */

public class NewsItem {
    private String title;
    private String url;
    private String author;
    private String description;
    private String urlToImage;
    private String date;

    public NewsItem(String title, String description, String url, String date, String urlToImage) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.date = date;
        this.urlToImage = urlToImage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void SetUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getUrlToImage() { return urlToImage; }

    public void setUrlToImage(String urlToImage) { this.urlToImage = urlToImage; }
}
