package lk.codebridge.TravelMate.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

    @Column(name = "otp")
    private String otp;

    @Column(name = "status")
    private int status;

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", mobile=" + mobile + ", password="
                + password + ", otp=" + otp + ", status=" + status + "]";
    }

    

}
