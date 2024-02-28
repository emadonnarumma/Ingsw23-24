package com.ingsw.dietiDeals24.model;

public class ExternalLink {

    private Integer id;
    private String title;
    private String url;

    public ExternalLink(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ExternalLink that = (ExternalLink) obj;
        return id != null && id.equals(that.id);
    }
}
