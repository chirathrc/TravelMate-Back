package lk.codebridge.TravelMate.service.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    public void sendEmail(String to, String subject, String text);

    public void welcomeEmail(String toEmail, String password, String name);

    public void sendOtpEmail(String toEmail, String otp, String name);

    void sendTravelConfirmationEmail(String toEmail, String userName, String mobile, String packageName, int personCount, String visitPlace, double totalPrice, String paymentDate, String paymentTime, String bookingDate, String endTripDate);

}
