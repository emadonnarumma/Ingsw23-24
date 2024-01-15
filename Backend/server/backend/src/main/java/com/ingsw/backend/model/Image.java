package com.ingsw.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(columnDefinition = "BYTEA")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    @JsonBackReference
    private Auction auction;

    
}
