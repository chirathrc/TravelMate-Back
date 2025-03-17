package lk.codebridge.TravelMate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import lk.codebridge.TravelMate.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByEmail(String email);
    
    User findByEmailAndPassword(String email, String password);
    
    User findByMobile(String mobile);
    
    User findByMobileAndPassword(String mobile, String password);
}
