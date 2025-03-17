package lk.codebridge.TravelMate.controller;

import org.springframework.web.bind.annotation.RestController;

import lk.codebridge.TravelMate.Dto.OrderPackageDTO;
import lk.codebridge.TravelMate.entity.Admin;
import lk.codebridge.TravelMate.entity.OrderPackage;
import lk.codebridge.TravelMate.entity.TravelPackagesEntity;
import lk.codebridge.TravelMate.entity.User;
import lk.codebridge.TravelMate.service.service.AdminService;
import lk.codebridge.TravelMate.service.service.PackageOrderService;
import lk.codebridge.TravelMate.service.service.UserService;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class PackageOrderingController {

    @Autowired
    private PackageOrderService packageOrderService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/travelmate/packageOrdering/getOrderedDetails")
    public ResponseEntity<List<OrderPackage>> getMethodName(@RequestParam String email) {
        Admin admin = adminService.getAdmin(email);

        List<OrderPackage> orderedPackages = packageOrderService.getAllOrderedPackages();

        if (orderedPackages.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        System.out.println(orderedPackages.size());

        if (admin.getPosition().getIdposition() != 1) {
            // Use an iterator to safely remove items during iteration
            Iterator<OrderPackage> iterator = orderedPackages.iterator();
            while (iterator.hasNext()) {
                OrderPackage orderPackage = iterator.next();
                if (orderPackage.getTravelPackage().getAdmin() != admin) {
                    iterator.remove(); // Safely remove using iterator
                } else {
                    System.out.println(orderPackage.getTravelPackage().getPackageName());
                }
            }

            return ResponseEntity.status(200).body(orderedPackages);
        }

        // If admin has position 1, just print the package names
        for (OrderPackage orderPackage : orderedPackages) {
            System.out.println(orderPackage.getTravelPackage().getPackageName());
        }

        return ResponseEntity.status(200).body(orderedPackages);
    }

    @GetMapping("/travelmate/packageOrdering/getOrderedDetailsByUser")
    public ResponseEntity<List<OrderPackage>> getOrderedDetailsByUser(@RequestParam String email) {

        User user = userService.getUserByEmail(email);

        return ResponseEntity.status(200).body(packageOrderService.getOrderedPackagesByUser(user));

    }
}
