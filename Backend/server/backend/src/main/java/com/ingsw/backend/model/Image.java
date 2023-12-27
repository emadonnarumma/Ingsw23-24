package com.ingsw.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name="images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    private Auction auction;


    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
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
}
