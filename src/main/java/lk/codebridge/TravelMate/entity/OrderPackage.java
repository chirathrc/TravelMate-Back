package lk.codebridge.TravelMate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orderPackage")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "check_in", nullable = false)
    private java.sql.Date checkIn;

    @Column(name = "checkout", nullable = false)
    private java.sql.Date checkout;

    @Column(name = "persons", nullable = false)
    private int persons;

    @Column(name = "payment_status", nullable = false)
    private int payment_status;

    @Column(name = "total", nullable = false)
    private double total;

    @ManyToOne
    @JoinColumn(name = "travel_packages_id")
    private TravelPackagesEntity travelPackage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
