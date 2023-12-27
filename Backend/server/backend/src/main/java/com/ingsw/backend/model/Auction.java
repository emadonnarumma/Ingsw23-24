    package com.ingsw.backend.model;

    import com.ingsw.backend.enumeration.AuctionStatus;
    import com.ingsw.backend.enumeration.Wear;
    import jakarta.persistence.*;

    import java.util.List;

    @Entity
    @Inheritance(strategy = InheritanceType.JOINED)
    @Table(name="auctions")
    public abstract class Auction {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @OneToMany(mappedBy = "auction", fetch = FetchType.EAGER,
                cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Image> images;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false)
        private String description;

        @Enumerated(EnumType.STRING)
        private Wear wear;

        @Enumerated(EnumType.STRING)
        private AuctionStatus status;










        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Wear getWear() {
            return wear;
        }

        public void setWear(Wear wear) {
            this.wear = wear;
        }

        public AuctionStatus getStatus() {
            return status;
        }

        public void setStatus(AuctionStatus status) {
            this.status = status;
        }
    }

