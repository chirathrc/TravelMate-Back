package lk.codebridge.TravelMate.controller;

import org.springframework.web.bind.annotation.RestController;

import lk.codebridge.TravelMate.Dto.OrderDto;
import lk.codebridge.TravelMate.entity.OrderPackage;
import lk.codebridge.TravelMate.entity.TravelPackagesEntity;
import lk.codebridge.TravelMate.entity.User;
import lk.codebridge.TravelMate.service.service.EmailService;
import lk.codebridge.TravelMate.service.service.PackageOrderService;
import lk.codebridge.TravelMate.service.service.PackageService;
import lk.codebridge.TravelMate.service.service.UserService;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PayementController {

    @Autowired
    private UserService userService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private PackageOrderService packageOrderService;

    @Autowired
    private EmailService emailService;

    private static String merchantSecret = "MTE2NzUxMzIwMTE2NjA5NTU1MTQxMjczMDU5ODgxNDIxNDE0OTY2OQ==";

    @PostMapping("/travelmate/pay/confirmation")
    public ResponseEntity<String> handlePaymentConfirmation(@RequestParam Map<String, String> params) {

        System.out.println("Received PayHere Data: " + params);

        // Extract data
        String merchantId = params.get("merchant_id");
        String orderId = params.get("order_id");
        String paymentId = params.get("payment_id");
        String amount = params.get("payhere_amount");
        String currency = params.get("payhere_currency");
        String statusCode = params.get("status_code");
        String receivedMd5Sig = params.get("md5sig");

        // Debug: Print Values
        System.out.println("Merchant ID: " + merchantId);
        System.out.println("Order ID: " + orderId);
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Amount: " + amount);
        System.out.println("Currency: " + currency);
        System.out.println("Status Code: " + statusCode);
        System.out.println("Received MD5 Signature: " + receivedMd5Sig);

        String merchant_secret_md5hash = generateMD5(merchantSecret);

        String generated_md5hash = generateMD5(
                merchantId
                        + orderId
                        + amount
                        + currency
                        + statusCode
                        + merchant_secret_md5hash);

        System.out.println("Generated MD5 Signature: " + generated_md5hash);
        System.out.println("Received MD5 Signature: " + receivedMd5Sig);

        if (!generated_md5hash.equals(receivedMd5Sig)) {
            return ResponseEntity.badRequest().body("Invalid Signature");
        }

        if ("2".equals(statusCode)) {
            System.out.println("✅ Payment successful for Order ID: " + orderId);
            return ResponseEntity.ok("Payment confirmed");
        } else {
            return ResponseEntity.badRequest().body("Payment failed");
        }

    }

    public static String generateMD5(String input) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, digest);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            return hexString.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(e);

        }
    }

    @PostMapping("/travelmate/pay/confirmationInUI")
    public ResponseEntity<?> postMethodName(@RequestBody OrderDto entity) {

        System.out.println("Received PayHere Data: " + entity);

        User user = userService.getUserByEmail(entity.getUserEmail());
        TravelPackagesEntity packagesEntity = packageService.getPackageById(Integer.parseInt(entity.getPackageId()));

        Date sqlDate = getSqlDate(entity.getDate());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sqlDate);

        calendar.add(Calendar.DAY_OF_MONTH, (packagesEntity.getDays() - 1));
        java.sql.Date newSqlDate = new java.sql.Date(calendar.getTimeInMillis());

        OrderPackage orderPackage = new OrderPackage();
        orderPackage.setTravelPackage(packagesEntity);
        orderPackage.setUser(user);
        orderPackage.setPayment_status(1);
        orderPackage.setCheckIn(sqlDate);
        orderPackage.setCheckout(newSqlDate);
        orderPackage.setPersons(Integer.parseInt(entity.getPersons()));
        orderPackage.setTotal(packagesEntity.getPricePerPerson() * Integer.parseInt(entity.getPersons()));

        OrderPackage order = packageOrderService.orderPackage(orderPackage);

        if (order != null) {

            // Get current date
            String currentDate = LocalDate.now().toString();

            // Get current time
            String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    emailService.sendTravelConfirmationEmail(user.getEmail(), user.getName(), user.getMobile(),
                    packagesEntity.getPackageName(), order.getPersons(), packagesEntity.getCity(), order.getTotal(),
                    currentDate, currentTime, entity.getDate(), newSqlDate.toString());

                }
            }).start();
            return ResponseEntity.ok("Order Placed Successfully");
        } else {
            return ResponseEntity.badRequest().body("Order Failed");

        }

    }

    private Date getSqlDate(String dateString) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate;
        try {
            utilDate = sdf.parse(dateString);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        return new java.sql.Date(utilDate.getTime());
    }

}
