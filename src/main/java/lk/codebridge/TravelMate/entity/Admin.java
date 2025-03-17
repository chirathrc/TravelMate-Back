package lk.codebridge.TravelMate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin")
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 45, nullable = true)
    private String lastName;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "mobile", length = 45, nullable = false)
    private String mobile;

    @Column(name = "otp", length = 10)
    private String otp;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "password", length = 45, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "position_idposition", nullable = false)
    private Position position;

}
