package com.ingsw.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer idImage;

    @Column(columnDefinition = "TEXT")
    private String base64Data;

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "idAuction")
    @JsonIgnore
    private Auction auction;

    
}
