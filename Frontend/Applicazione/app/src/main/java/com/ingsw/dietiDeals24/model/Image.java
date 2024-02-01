package com.ingsw.dietiDeals24.model;

public class Image {

    private Integer id;
    private byte[] data;
    private Auction auction;

    public Image(Integer id, byte[] data, Auction auction) {
        this.id = id;
        this.data = data;
        this.auction = auction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
