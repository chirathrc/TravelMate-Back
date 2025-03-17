package lk.codebridge.TravelMate.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.codebridge.TravelMate.entity.OrderPackage;
import lk.codebridge.TravelMate.entity.User;

public interface OrderedPackageRepository extends JpaRepository<OrderPackage, Integer> {

    List<OrderPackage> findByUserOrderByIdDesc(User user);
    
    List<OrderPackage> findAllByOrderByIdDesc(); 

    //  List<OrderPackage> findByUser(User user);
    
} 