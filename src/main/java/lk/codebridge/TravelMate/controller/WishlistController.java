package lk.codebridge.TravelMate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lk.codebridge.TravelMate.entity.TravelPackagesEntity;
import lk.codebridge.TravelMate.entity.User;
import lk.codebridge.TravelMate.entity.Wishlist;
import lk.codebridge.TravelMate.service.service.PackageService;
import lk.codebridge.TravelMate.service.service.UserService;
import lk.codebridge.TravelMate.service.service.WishlistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

    @Autowired
    PackageService packageService;

    @Autowired
    UserService userService;

    @GetMapping("/travelmate/addToWishlist")
    public ResponseEntity<?> addToWishlist(@RequestParam String email, @RequestParam String packageId) {

        User user = userService.getUserByEmail(email);

        System.out.println(email);

        TravelPackagesEntity packagesEntity = packageService.getPackageById(Integer.parseInt(packageId));
        System.out.println(packagesEntity);
        System.out.println(user);

        if (wishlistService.findByUserAndPackage(packagesEntity, user) != null) {

            return ResponseEntity.status(200).body("Already in wishlist");
        }

        Wishlist wishlistAdding = new Wishlist();
        wishlistAdding.setUser(user);
        wishlistAdding.setTravelPackage(packagesEntity);

        Wishlist wishlist = wishlistService.save(wishlistAdding);

        if (wishlist != null) {
            return ResponseEntity.ok(wishlist);

        } else {

            return ResponseEntity.status(422).body("Failed to add to wishlist");

        }
    }

    @GetMapping("/travelmate/getFromWishlist")
    public ResponseEntity<?> getFromWishlist(@RequestParam String email) {

        User user = userService.getUserByEmail(email);

        List<Wishlist> wishlist = wishlistService.findByUser(user);

        if (wishlist.size() > 0) {
            return ResponseEntity.ok(wishlist);
        } else {
            return ResponseEntity.status(422).body("No items in wishlist");
        }

    }

    @GetMapping("/travelmate/removeFromWishlist")
    public ResponseEntity<?> getMethodName(@RequestParam String id) {

        wishlistService.delete(Integer.parseInt(id));
       
        return ResponseEntity.ok("Removed from wishlist");
    }
    
}
