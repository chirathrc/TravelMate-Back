package lk.codebridge.TravelMate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "travel_packages")
@Getter
@Setter
@Data
public class TravelPackagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "packages_name", length = 45, nullable = false)
    private String packageName;

    @Column(name = "desc_One", nullable = false)
    private String descOne;

    @Column(name = "desc_two")
    private String descTwo;

    @Column(name = "price_for_person", length = 45, nullable = false)
    private double pricePerPerson;

    @Column(name = "days", nullable = false)
    private int days;

    @Column(name = "nights", nullable = false)
    private int nights;

    @Column(name = "wiki_url", nullable = false)
    private String wikiUrl;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "city", nullable = false)
    private String city;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(name = "location_place", nullable = false)
    private String location;

}
