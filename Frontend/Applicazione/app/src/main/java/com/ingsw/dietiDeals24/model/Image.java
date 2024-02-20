package com.ingsw.dietiDeals24.model;

public class Image {

    private Integer idImage;
    private String base64Data;
    private Auction auction;

    public Image(Integer idImage, String base64Data, Auction auction) {
        this.idImage = idImage;
        this.base64Data = base64Data;
        this.auction = auction;
    }

    public Integer getIdImage() {
        return idImage;
    }

    public void setIdImage(Integer idImage) {
        this.idImage = idImage;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

}
