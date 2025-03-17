package lk.codebridge.TravelMate.service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lk.codebridge.TravelMate.entity.TravelPackagesEntity;
import lk.codebridge.TravelMate.entity.User;
import lk.codebridge.TravelMate.entity.Wishlist;

@Service
public interface WishlistService {

    Wishlist save(Wishlist wishlist);

    Wishlist findById(int id);

    List<Wishlist> findByUser(User userId);

    void delete(int id);

    Wishlist findByUserAndPackage(TravelPackagesEntity packageId, User userId);

}
