package com.ingsw.dietiDeals24.model;

import java.io.Serializable;

public class ExternalLink implements Serializable {

    private Integer idExternalLink;
    private String title;
    private String url;

    /**
     * Constructor for the ExternalLink created by the user
     */
    public ExternalLink(String title, String url) {
        this.title = title;
        this.url = url;
    }

    /**
     * Constructor for the ExternalLink with the id generated from the server
     */
    public ExternalLink(Integer id, String title, String url) {
        this.idExternalLink = id;
        this.title = title;
        this.url = url;
    }

    /**
     * Copy constructor
     */
    public ExternalLink(ExternalLink externalLink) {
        if(externalLink == null) {
            return;
        }
        this.idExternalLink = externalLink.idExternalLink;
        this.title = externalLink.title;
        this.url = externalLink.url;
    }

    public Integer getId() {
        return idExternalLink;
    }

    public void setId(Integer id) {
        this.idExternalLink = id;
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
        return idExternalLink != null && idExternalLink.equals(that.idExternalLink);
    }
}
