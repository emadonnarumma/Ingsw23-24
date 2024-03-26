package com.ingsw.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNotification;

    @Column(nullable = false)
    private String message;
    
    
    @JsonBackReference
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="owner_email", referencedColumnName="email"),
            @JoinColumn(name="owner_role", referencedColumnName="role")
    })
    @JsonIgnore
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "idAuction")
    private DownwardAuction auction;

}
