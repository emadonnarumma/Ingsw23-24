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
@Table(name = "notifications")
public class Notification {

    @Id
    private Integer idNotifications;

    @Column(nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    @JsonBackReference
    private User owner;
    //TODO: jonny per piacere completa la classe non sappiamo cosa significa getImage e controlla anche le sottoclassi
}
