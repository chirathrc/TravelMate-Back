package lk.codebridge.TravelMate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lk.codebridge.TravelMate.entity.TravelPackagesEntity;
import lk.codebridge.TravelMate.entity.User;
import lk.codebridge.TravelMate.entity.Wishlist;
import lk.codebridge.TravelMate.repository.WishlistRepository;
import lk.codebridge.TravelMate.service.service.WishlistService;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    WishlistRepository wishlistRepository;

    @Override
    public Wishlist save(Wishlist wishlist) {

        return wishlistRepository.save(wishlist);

    }

    @Override
    public Wishlist findById(int id) {

        return wishlistRepository.findById(id).get();
    }

    @Override
    public List<Wishlist> findByUser(User userId) {

        return wishlistRepository.findByUser(userId);
    }

    @Override
    public void delete(int id) {

        wishlistRepository.deleteById(id);
    }

    @Override
    public Wishlist findByUserAndPackage(TravelPackagesEntity packageId, User userId) {

        // List<Wishlist> wishlist =
        // wishlistRepository.findByUserAndTravelPackage(userId, packageId);
        return wishlistRepository.findByUserAndTravelPackage(userId, packageId);

        // if (wishlist.size() > 0) {
        // return wishlist.get(0);
        // } else {
        // return null;
        // }
    }

}
