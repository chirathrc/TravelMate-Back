package lk.codebridge.TravelMate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lk.codebridge.TravelMate.entity.OrderPackage;
import lk.codebridge.TravelMate.entity.User;
import lk.codebridge.TravelMate.repository.OrderedPackageRepository;
import lk.codebridge.TravelMate.service.service.PackageOrderService;

@Service
public class PackageOrderServiceImpl implements PackageOrderService {

    @Autowired
    private OrderedPackageRepository orderedPackageRepository;

    @Override
    public List<OrderPackage> getAllOrderedPackages() {

        return orderedPackageRepository.findAllByOrderByIdDesc();
        // return orderedPackageRepository.findAll();

    }

    @Override
    public List<OrderPackage> getOrderedPackagesByUser(User user) {

        return orderedPackageRepository.findByUserOrderByIdDesc(user);
    }

    @Override
    public OrderPackage orderPackage(OrderPackage orderPackage) {

        return orderedPackageRepository.save(orderPackage);
    }
}
