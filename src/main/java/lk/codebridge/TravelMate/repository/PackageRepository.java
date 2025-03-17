package lk.codebridge.TravelMate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.codebridge.TravelMate.entity.Admin;
import lk.codebridge.TravelMate.entity.TravelPackagesEntity;

public interface PackageRepository extends JpaRepository<TravelPackagesEntity, Integer> {

    List<TravelPackagesEntity> findByPackageName(String packageName);

    List<TravelPackagesEntity> findByAdmin(Admin admin);

    TravelPackagesEntity findById(int id);

    

}
