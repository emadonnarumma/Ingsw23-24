package com.ingsw.dietiDeals24.model;

public class ExternalLink {

    private Integer idExternalLink;
    private String title;
    private String url;
    private User user;

    public ExternalLink(String title, String url, User user) {
        this.title = title;
        this.url = url;
        this.user = user;
    }

    public Integer getIdExternalLink() {
        return idExternalLink;
    }

    public void setIdExternalLink(Integer idExternalLink) {
        this.idExternalLink = idExternalLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
