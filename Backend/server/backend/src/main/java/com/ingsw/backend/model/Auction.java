package com.ingsw.backend.model;

import com.ingsw.backend.enumeration.AuctionStatus;
import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.enumeration.Wear;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auctions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = D\iscriminatorType.STRING)
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
public abstract class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAuction;

    @OneToMany(mappedBy = "auction", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Image> images;

    @JsonIgnore
    @OneToMany(mappedBy = "auction", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Notification> notifications;

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
    @JoinColumn(name = "owner_role", referencedColumnName = "role")
    private User owner;
}

