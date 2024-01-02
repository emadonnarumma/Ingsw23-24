package com.ingsw.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Integer id;

    @Column(nullable = false)
    private String message;

    //TODO: jonny per piacere completa la classe non sappiamo cosa significa getImage e controlla anche le sottoclassi
}
