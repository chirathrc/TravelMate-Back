package lk.codebridge.TravelMate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import lk.codebridge.TravelMate.Dto.AdminDataDTO;
import lk.codebridge.TravelMate.entity.Admin;
import lk.codebridge.TravelMate.entity.Position;
import lk.codebridge.TravelMate.service.impl.EmailServiceImpl;
import lk.codebridge.TravelMate.service.service.AdminService;
import lk.codebridge.TravelMate.service.service.OtherServices;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private OtherServices otherServices;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @PostMapping("/travelmate/admin/signIn")
    public ResponseEntity<?> SignInAdmin(@RequestBody Admin admin) {

        Admin signinAdmin = adminService.getAdmin(admin.getEmail(), admin.getPassword());

        if (signinAdmin != null) {

            if (signinAdmin.getStatus() == 2) {

                String otp = otherServices.generateOTP();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        emailServiceImpl.sendOtpEmail(signinAdmin.getEmail(), otp,
                                signinAdmin.getFirstName());

                    }
                });

                thread.start();

                signinAdmin.setOtp(otp);
                Admin updatedOtpAdmin = adminService.upadateAdmin(signinAdmin);

                if (updatedOtpAdmin != null) {

                    return ResponseEntity.status(200).body("Account is Deactivated");

                } else {

                    return ResponseEntity.status(422).body("Something Went Wrong. Please Try Again");

                }

            }

            return ResponseEntity.status(200).body(signinAdmin);

        } else {

            return ResponseEntity.status(422).body("Invalid Details");
        }

    }

    @GetMapping("/travelmate/admin/otpVerification")
    public ResponseEntity<?> getMethodName(@RequestParam String email, @RequestParam String otp) {

        Admin admin = adminService.getAdmin(email);

        if (admin != null) {

            if (admin.getOtp().equals(otp)) {

                admin.setStatus(1);
                adminService.upadateAdmin(admin);
                return ResponseEntity.status(200).body(true);

            } else {
                return ResponseEntity.status(200).body(false);
            }

        } else {

            return ResponseEntity.status(422).body("Invalid Details");

        }
    }

    @GetMapping("/travelmate/admin/getPositions")
    public ResponseEntity<List<Position>> getAdminPositions() {

        List<Position> positionList = adminService.getAllPositions();

        return ResponseEntity.status(200).body(positionList);

    }

    @PostMapping("/travelmate/admin/addNewAdmin")
    public ResponseEntity<?> postMethodName(@RequestBody AdminDataDTO adminDataDTO) {

        System.out.println(adminDataDTO);

        if (adminService.getAdmin(adminDataDTO.getEmail()) != null
                || adminService.getAdminByMobile(adminDataDTO.getMobile()) != null) {

            return ResponseEntity.status(422).body("Email or Mobile Number Already Exists");
        }

        Position position = adminService.getPosition(adminDataDTO.getPosition());

        if (position != null) {

            Admin admin = new Admin();
            admin.setEmail(adminDataDTO.getEmail());
            admin.setMobile(adminDataDTO.getMobile());
            admin.setFirstName(adminDataDTO.getName());
            admin.setPassword(otherServices.generateOTP());
            admin.setPosition(position);
            admin.setStatus(2);

            Admin newAdmin = adminService.addAdmin(admin);

            if (newAdmin != null) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        emailServiceImpl.welcomeEmail(newAdmin.getEmail(), newAdmin.getPassword(),
                                newAdmin.getFirstName());

                    }
                });

                thread.start();

                return ResponseEntity.status(201).body("Admin Added Successfully");

            } else {

                return ResponseEntity.status(422).body("Something Went Wrong. Please Try Again");

            }

        } else {

            return ResponseEntity.status(422).body("Invalid Position");

        }

    }

    @PostMapping("/travelmate/admin/updateAdmin")
    public ResponseEntity<?> postMethodName(@RequestBody Admin admin) {
       
        Admin adminByEmail = adminService.getAdmin(admin.getEmail());

        if (adminByEmail != null) {

            adminByEmail.setFirstName(admin.getFirstName());
            adminByEmail.setMobile(admin.getMobile());
            adminByEmail.setLastName(admin.getLastName());
            adminByEmail.setPassword(admin.getPassword());

            Admin updatedAdmin = adminService.upadateAdmin(adminByEmail);

            if (updatedAdmin != null) {

                return ResponseEntity.status(200).body("Admin Updated Successfully");

            } else {

                return ResponseEntity.status(422).body("Something Went Wrong. Please Try Again");

            }
        } else {
            return ResponseEntity.status(422).body("Something Went Wrong. Please Try Again");
        }

            
    }
    

}
