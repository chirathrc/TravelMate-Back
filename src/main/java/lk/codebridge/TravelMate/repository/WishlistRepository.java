package lk.codebridge.TravelMate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lk.codebridge.TravelMate.entity.TravelPackagesEntity;
import lk.codebridge.TravelMate.entity.User;
import lk.codebridge.TravelMate.entity.Wishlist;
import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    List<Wishlist> findByUser(User user);

    Wishlist findByUserAndTravelPackage(User user, TravelPackagesEntity travelPackage);
}
    
