package lk.codebridge.TravelMate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lk.codebridge.TravelMate.entity.Admin;
import lk.codebridge.TravelMate.entity.TravelPackagesEntity;
import lk.codebridge.TravelMate.repository.PackageRepository;
import lk.codebridge.TravelMate.service.service.PackageService;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Override
    public List<TravelPackagesEntity> getAllPackages() {
     
        return packageRepository.findAll();
    }

    @Override
    public TravelPackagesEntity getPackageById(int id) {
        
        return packageRepository.findById(id);
    }

    @Transactional
    @Override
    public TravelPackagesEntity addPackage(TravelPackagesEntity trvelPackage) {

        return packageRepository.save(trvelPackage);

    }

    @Override
    public TravelPackagesEntity updatePackage(TravelPackagesEntity trvelPackage) {
      
        return packageRepository.save(trvelPackage);
    }


    @Override
    public void deletePackage(int id) {

        // if (packageRepository.existsById(id)) {
        packageRepository.deleteById(id);
        // return true;
        // } else {
        // return false;
        // }

    }

    @Override
    public List<TravelPackagesEntity> getPackagesByPackageName(String packageName) {
        
        return packageRepository.findByPackageName(packageName);
    }

    @Override
    public List<TravelPackagesEntity> getPackagesByAdminId(Admin admin) {
        
        return packageRepository.findByAdmin(admin);

    }

}
