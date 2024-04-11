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
@Table(name = "external_links")
public class ExternalLink {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idExternalLink;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 2083)
    private String url;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    @JoinColumn(name = "owner_role", referencedColumnName = "role")
    @JsonIgnore
    @ToString.Exclude
    private User user;
}
