package lk.codebridge.TravelMate.service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lk.codebridge.TravelMate.entity.OrderPackage;
import lk.codebridge.TravelMate.entity.User;

@Service
public interface PackageOrderService {

    public List<OrderPackage> getAllOrderedPackages();

    public List<OrderPackage> getOrderedPackagesByUser(User user);

    public OrderPackage orderPackage(OrderPackage orderPackage);
    
}
