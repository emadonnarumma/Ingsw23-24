package com.ingsw.backend.model;

import com.ingsw.backend.enumeration.AuctionStatus;
import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.enumeration.Wear;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="auctions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME, 
		  include = JsonTypeInfo.As.PROPERTY, 
		  property = "type"
		)
@JsonSubTypes({
		  @JsonSubTypes.Type(value = SilentAuction.class, name = "SILENT"),
		  @JsonSubTypes.Type(value = DownwardAuction.class, name = "DOWNWARD"),
		  @JsonSubTypes.Type(value = ReverseAuction.class, name = "REVERSE")
				})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "auction", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("auction-image")
    private List<Image> images;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private Wear wear;

    @Enumerated(EnumType.STRING)
    private AuctionStatus status;
    
    @Enumerated(EnumType.STRING)
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    @JsonBackReference
    private User owner;

}

