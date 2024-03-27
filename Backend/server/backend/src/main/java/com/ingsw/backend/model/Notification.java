package com.ingsw.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNotification;

    @Column(nullable = false)
    private String message;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    @JoinColumn(name = "owner_role", referencedColumnName = "role")
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "idAuction")
    @ToString.Exclude
    private DownwardAuction auction;

}
