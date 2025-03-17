package lk.codebridge.TravelMate.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lk.codebridge.TravelMate.Dto.TravlePackageDetailsDTO;
import lk.codebridge.TravelMate.entity.Admin;
import lk.codebridge.TravelMate.entity.TravelPackagesEntity;
import lk.codebridge.TravelMate.service.service.AdminService;
import lk.codebridge.TravelMate.service.service.PackageService;
import lk.codebridge.TravelMate.storage.StorageFileNotFoundException;
import lk.codebridge.TravelMate.storage.StorageService;

import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PackageController {

    @Autowired
    private PackageService packageService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/travelmate/package/addNew")
    public ResponseEntity<Boolean> addNewPackage(@RequestPart("image") MultipartFile file,
            @ModelAttribute TravelPackagesEntity travelPakage, @RequestParam String email) {

        Admin admin = adminService.getAdmin(email);

        travelPakage.setAdmin(admin);
        travelPakage.setStatus(1);

        TravelPackagesEntity addedPackage = packageService
                .addPackage(travelPakage);

        try {

            if (addedPackage != null) {

                storageService.store(file, addedPackage.getId() + "travelmate");
                return ResponseEntity.status(201).body(true);
            } else {
                return ResponseEntity.status(422).body(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            packageService.deletePackage(addedPackage.getId());

            return ResponseEntity.status(500).body(false);
        }
    }

    // for user side product loading
    @GetMapping("/travelmate/package/getAllPackagesForUser")
    public ResponseEntity<List<TravlePackageDetailsDTO>> getAllPackagesForUser() {

        List<TravlePackageDetailsDTO> packagesNew = new ArrayList<>();

        List<TravelPackagesEntity> packages = packageService.getAllPackages();
        Iterator<TravelPackagesEntity> iterator = packages.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getStatus() == 2) {
                iterator.remove();
            }
        }

        for (TravelPackagesEntity travelPackagesEntity : packages) {

            Resource file = getImageFromID(travelPackagesEntity.getId() + "travelmate.png");

            String base64Image = null;
            if (file != null) {
                try {
                    // Read the file as bytes and encode it as a Base64 string
                    byte[] fileBytes = Files.readAllBytes(file.getFile().toPath());
                    base64Image = Base64.getEncoder().encodeToString(fileBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            packagesNew.add(new TravlePackageDetailsDTO(travelPackagesEntity.getId(),
                    travelPackagesEntity.getPackageName(),
                    travelPackagesEntity.getDescOne(), travelPackagesEntity.getDescTwo(),
                    travelPackagesEntity.getPricePerPerson(),
                    travelPackagesEntity.getDays(), travelPackagesEntity.getNights(), travelPackagesEntity.getWikiUrl(),
                    travelPackagesEntity.getCity(), travelPackagesEntity.getStatus(),
                    base64Image, travelPackagesEntity.getLocation()));

        }

        return ResponseEntity.status(200).body(packagesNew);
    }

    @GetMapping("/travelmate/package/getAllPackages")
    public ResponseEntity<List<TravlePackageDetailsDTO>> getAllPackages(@RequestParam String email) {

        System.out.println(email);

        Admin admin = adminService.getAdmin(email);

        List<TravlePackageDetailsDTO> packagesNew = new ArrayList<>();

        List<TravelPackagesEntity> packages;

        if (admin.getPosition().getIdposition() == 1) {

            packages = packageService.getAllPackages();

        } else {

            packages = packageService.getPackagesByAdminId(admin);

        }

        for (TravelPackagesEntity travelPackagesEntity : packages) {

            Resource file = getImageFromID(travelPackagesEntity.getId() + "travelmate.png");

            String base64Image = null;
            if (file != null) {
                try {
                    // Read the file as bytes and encode it as a Base64 string
                    byte[] fileBytes = Files.readAllBytes(file.getFile().toPath());
                    base64Image = Base64.getEncoder().encodeToString(fileBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            packagesNew.add(new TravlePackageDetailsDTO(travelPackagesEntity.getId(),
                    travelPackagesEntity.getPackageName(),
                    travelPackagesEntity.getDescOne(), travelPackagesEntity.getDescTwo(),
                    travelPackagesEntity.getPricePerPerson(),
                    travelPackagesEntity.getDays(), travelPackagesEntity.getNights(), travelPackagesEntity.getWikiUrl(),
                    travelPackagesEntity.getCity(), travelPackagesEntity.getStatus(),
                    base64Image, travelPackagesEntity.getLocation()));

        }

        return ResponseEntity.status(200).body(packagesNew);
    }

    public Resource getImageFromID(String filename) {
        try {
            // Load file as Resource
            Resource file = storageService.loadAsResource(filename);
            return file;

        } catch (StorageFileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("travelmate/package/getImage/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            // Load file as Resource
            Resource file = storageService.loadAsResource(filename);

            // Return file in response
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                            file.getFilename() + "\"")
                    .body(file);

        } catch (StorageFileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/travelmate/package/updateStatus/{id}")
    public ResponseEntity<Boolean> updatePackageStatus(@PathVariable int id, @RequestParam boolean status) {

        TravelPackagesEntity travelPackage = packageService.getPackageById(id);

        travelPackage.setStatus(status ? 1 : 2);

        TravelPackagesEntity updatedPackage = packageService.updatePackage(travelPackage);

        if (updatedPackage != null) {

            return ResponseEntity.ok().body(true);

        } else {

            return ResponseEntity.status(422).body(false);

        }
    }

    @PostMapping("/travelmate/package/updatePackage/{id}")
    public ResponseEntity<?> updatePackage(@RequestBody TravelPackagesEntity entity, @PathVariable int id) {

        TravelPackagesEntity travelPackage = packageService.getPackageById(id);
        travelPackage.setPackageName(entity.getPackageName());
        travelPackage.setPricePerPerson(entity.getPricePerPerson());
        travelPackage.setDescOne(entity.getDescOne());
        travelPackage.setDescTwo(entity.getDescTwo());
        travelPackage.setCity(entity.getCity());
        travelPackage.setDays(entity.getDays());
        travelPackage.setNights(entity.getNights());
        travelPackage.setWikiUrl(entity.getWikiUrl());

        TravelPackagesEntity packagesEntityUpdated = packageService.updatePackage(travelPackage);

        if (packagesEntityUpdated != null) {

            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.status(422).body(false);
        }
    }

    @GetMapping("/travelmate/package/getPackageFromId/{id}")
    public ResponseEntity<?> getMethodName(@PathVariable String id) {

        TravelPackagesEntity travelPackage = packageService.getPackageById(Integer.parseInt(id));

        if (travelPackage != null) {

            return ResponseEntity.status(200).body(travelPackage);


        } else {

            return ResponseEntity.status(422).body("Invalid Details");
        }

    }

}
