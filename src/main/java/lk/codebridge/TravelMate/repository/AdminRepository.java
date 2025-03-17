package lk.codebridge.TravelMate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.codebridge.TravelMate.entity.Admin;
import lk.codebridge.TravelMate.entity.Position;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

     
    Admin findByEmail(String email);
    
    Admin findByEmailAndPassword(String email, String password);
    
    Admin findByMobile(String mobile);
    
    Admin findByMobileAndPassword(String mobile, String password);

    List<Admin> findByPosition(Position position);
    
}
