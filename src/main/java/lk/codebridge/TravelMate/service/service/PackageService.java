package lk.codebridge.TravelMate.service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lk.codebridge.TravelMate.entity.Admin;
import lk.codebridge.TravelMate.entity.TravelPackagesEntity;

@Service
public interface PackageService {
    public List<TravelPackagesEntity> getAllPackages();

    public TravelPackagesEntity getPackageById(int id);

    public TravelPackagesEntity addPackage(TravelPackagesEntity trvelPackage);

    public TravelPackagesEntity updatePackage(TravelPackagesEntity trvelPackage);

    public void deletePackage(int id);

    public List<TravelPackagesEntity> getPackagesByPackageName(String packageName);

    public List<TravelPackagesEntity> getPackagesByAdminId(Admin admin);
}
