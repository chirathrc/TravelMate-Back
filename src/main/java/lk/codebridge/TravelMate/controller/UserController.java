package lk.codebridge.TravelMate.controller;

import org.springframework.web.bind.annotation.RestController;

import lk.codebridge.TravelMate.entity.User;
import lk.codebridge.TravelMate.service.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lk.codebridge.TravelMate.service.service.EmailService;
import lk.codebridge.TravelMate.service.service.EncryptService;
import lk.codebridge.TravelMate.service.service.OtherServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtherServices otherServices;

    @Autowired
    private EncryptService encoder;


    @PostMapping("/travelmate/userRegister")
    public ResponseEntity<?> postMethodName(@RequestBody User user) throws Exception {

        if (!user.getName().isEmpty() && !user.getEmail().isEmpty() && !user.getPassword().isEmpty()
                && !user.getMobile().isEmpty()) {

            user.setStatus(2);
            user.setOtp(otherServices.generateOTP());
            user.setPassword(encoder.encrypt(user.getPassword()));

            System.out.println(user);

            if (userService.registerUser(user)) {

                System.out.println("User registered successfully");

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        emailService.sendOtpEmail(user.getEmail(), user.getOtp(), user.getName());

                    }

                }).start();

                return ResponseEntity.status(201).body("User registered successfully");

            } else {

                System.out.println("error1");
                return ResponseEntity.status(422).body("User already exists");

            }

        } else {
            System.out.println("error2");
            return ResponseEntity.status(422).body("Please fill all the fields");
        }

    }

    @PostMapping("/travelmate/userSignIn")
    public ResponseEntity<?> userSignIn(@RequestBody User user) throws Exception {

        System.out.println(user.getEmail() + " " + user.getPassword());

        User loginUser = userService.getUserByEmail(user.getEmail());

        if (loginUser != null && encoder.decrypt(loginUser.getPassword()).equals(user.getPassword())) {

            if (loginUser.getStatus() == 1) {
                return ResponseEntity.status(200).body(loginUser);
            } else {

                String otp = otherServices.generateOTP();
                loginUser.setOtp(otp);
                userService.updateUser(loginUser);

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        emailService.sendOtpEmail(loginUser.getEmail(), otp, loginUser.getName());
                    }

                }).start();

                return ResponseEntity.status(200).body("OTP not submitted");
            }

        } else {
            return ResponseEntity.status(422).body("User not found");
        }

    }

    @GetMapping("/travelmate/otpSubmission")
    public ResponseEntity<?> otpSubmisson(@RequestParam String otp, @RequestParam String email) throws Exception {

        User user = userService.getUserByEmail(email);
        System.out.println(otp);

        if (user.getOtp().equals(otp)) {

            user.setStatus(1);
            userService.updateUser(user);
            user.setPassword(encoder.decrypt(user.getPassword()));
            return ResponseEntity.status(200).body(user);

        } else {
            return ResponseEntity.status(422).body(false);
        }

    }

    @GetMapping("/travelmate/findAllUsers")
    public ResponseEntity<List<User>> findAllUsers() throws Exception {

        List<User> users = userService.getAllUsers();

        for (User u : users) {
            u.setPassword(encoder.decrypt(u.getPassword()));
        }

        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/travelmate/changeUserStatus")
    public ResponseEntity<?> changeUserStatus(@RequestParam String email, @RequestParam boolean status) {

        User user = userService.getUserByEmail(email);
        user.setStatus(status ? 1 : 2);
        userService.updateUser(user);

        return ResponseEntity.status(200).body(true);

    }

    @PostMapping("/travelmate/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception {

        User userUpdateTo = userService.getUserByEmail(user.getEmail());

        if (userUpdateTo != null) {

            userUpdateTo.setMobile(user.getMobile());
            userUpdateTo.setName(user.getName());
            userUpdateTo.setPassword(encoder.encrypt(user.getPassword()));

            if (userService.updateUser(userUpdateTo)) {

                userUpdateTo.setPassword(encoder.decrypt(userUpdateTo.getPassword()));

                return ResponseEntity.status(200).body(userUpdateTo);

            } else {
                return ResponseEntity.status(422).body(null);
            }

        } else {

            return ResponseEntity.status(422).body(null);

        }

    }

    @GetMapping("/travelmate/userCount")
    public ResponseEntity<?> getUserCount() {

        @Getter
        @Setter
        class UserCount {
            private int activeUsers;

            private int nonActiveusers;
        }

        UserCount count = new UserCount();

        List<User> listUser = userService.getAllUsers();

        for (User u : listUser) {

            if (u.getStatus() == 1) {

                count.setActiveUsers(count.getActiveUsers() + 1);
            } else {

                count.setNonActiveusers(count.getNonActiveusers() + 1);
            }

        }

        return ResponseEntity.ok().body(count);
    }

}
