package lk.codebridge.TravelMate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import lk.codebridge.TravelMate.service.service.EmailService;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    @Async
    @Override
    public void sendEmail(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(email);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);

        System.out.println("Email Sent Succesfull to, " + to);
    }

    @Override
    public void welcomeEmail(String toEmail, String password, String name) {
        String subject = "Welcome to TravelMate!";
        String content = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }" +
                "        .container { max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }"
                +
                "        .header { background-color: #4CAF50; padding: 20px; text-align: center; color: #ffffff; }" +
                "        .header h1 { margin: 0; font-size: 24px; }" +
                "        .content { padding: 20px; line-height: 1.6; color: #333333; }" +
                "        .content h2 { color: #4CAF50; }" +
                "        .content p { margin: 10px 0; }" +
                "        .credentials { margin: 20px 0; padding: 15px; background-color: #f9f9f9; border: 1px solid #ddd; border-radius: 4px; }"
                +
                "        .credentials span { display: block; font-weight: bold; }" +
                "        .footer { text-align: center; padding: 10px; font-size: 12px; color: #666666; background-color: #f4f4f4; }"
                +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <div class='header'>" +
                "            <h1>Welcome to TravelMate Admin Account</h1>" +
                "        </div>" +
                "        <div class='content'>" +
                "            <h2>Hello " + name + ",</h2>" +
                "            <p>We're excited to have you on board! Here are your account details:</p>" +
                "            <div class='credentials'>" +
                "                <span>Username: " + toEmail + "</span>" +
                "                <span>Password: " + password + "</span>" +
                "            </div>" +
                "            <p>Please keep your credentials safe. You can log in to your account and start exploring our services right away.</p>"
                +
                "            <p>If you have any questions, feel free to reply to this email or contact our support team.</p>"
                +
                "        </div>" +
                "        <div class='footer'>" +
                "            &copy; " + java.time.Year.now() + " TravelMate. All Rights Reserved." +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(email);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true); // Set `true` to indicate the email content is HTML

            mailSender.send(message);

            System.out.println("HTML Email sent successfully to: " + toEmail);

        } catch (MessagingException e) {
            System.err.println("Error while sending email: " + e.getMessage());
        }
    }

    @Override
    public void sendOtpEmail(String toEmail, String otp, String name) {
        String subject = "Your OTP for TravelMate Registration";
        String content = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }" +
                "        .container { max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }"
                +
                "        .header { background-color: #4CAF50; padding: 20px; text-align: center; color: #ffffff; }" +
                "        .header h1 { margin: 0; font-size: 24px; }" +
                "        .content { padding: 20px; line-height: 1.6; color: #333333; }" +
                "        .content h2 { color: #4CAF50; }" +
                "        .content p { margin: 10px 0; }" +
                "        .otp { margin: 20px 0; padding: 15px; background-color: #f9f9f9; border: 1px solid #ddd; border-radius: 4px; text-align: center; font-size: 18px; font-weight: bold; color: #4CAF50; }"
                +
                "        .footer { text-align: center; padding: 10px; font-size: 12px; color: #666666; background-color: #f4f4f4; }"
                +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <div class='header'>" +
                "            <h1>Welcome to TravelMate, " + name + "!</h1>" +
                "        </div>" +
                "        <div class='content'>" +
                "            <h2>We are excited to have you register with us.</h2>" +
                "            <p>Your One-Time Password (OTP) for completing your registration is:</p>" +
                "            <div class='otp'>" + otp + "</div>" +
                "            <p>Please enter this OTP on the registration page to verify your account.</p>" +
                "            <p>If you did not request this OTP, please ignore this email.</p>" +
                "        </div>" +
                "        <div class='footer'>" +
                "            &copy; " + java.time.Year.now() + " TravelMate. All Rights Reserved." +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(email);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true); // Set `true` to indicate the email content is HTML

            mailSender.send(message);

            System.out.println("OTP email sent successfully to: " + toEmail);

        } catch (MessagingException e) {
            System.err.println("Error while sending OTP email: " + e.getMessage());
        }
    }

    @Override
    public void sendTravelConfirmationEmail(String toEmail, String userName, String mobile, String packageName,
            int personCount, String visitPlace, double totalPrice, String paymentDate, String paymentTime,
            String bookingDate, String endTripDate) {
        String subject = "Your TravelMate Booking Confirmation";
        String content = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #e8f5e9; }" +
                "        .container { max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }"
                +
                "        .header { background-color: #2e7d32; padding: 20px; text-align: center; color: #ffffff; }" +
                "        .header h1 { margin: 0; font-size: 24px; }" +
                "        .content { padding: 20px; line-height: 1.6; color: #333333; }" +
                "        .section { margin-bottom: 20px; background-color: #f1f8e9; padding: 15px; border-radius: 6px; border: 1px solid #c5e1a5; }"
                +
                "        .section h2 { margin-top: 0; color: #2e7d32; }" +
                "        .footer { text-align: center; padding: 10px; font-size: 12px; color: #666666; background-color: #e8f5e9; }"
                +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <div class='header'>" +
                "            <h1>TravelMate Booking Confirmation</h1>" +
                "        </div>" +
                "        <div class='content'>" +
                "            <h2>Dear " + userName + ",</h2>" +
                "            <p>Thank you for booking your travel package with TravelMate. Below are your booking details:</p>"
                +
                "            <div class='section'>" +
                "                <h2>User Details</h2>" +
                "                <p><strong>Email:</strong> " + toEmail + "</p>" +
                "                <p><strong>Mobile:</strong> " + mobile + "</p>" +
                "            </div>" +
                "            <div class='section'>" +
                "                <h2>Order Details</h2>" +
                "                <p><strong>Package Name:</strong> " + packageName + "</p>" +
                "                <p><strong>Person Count:</strong> " + personCount + "</p>" +
                "                <p><strong>Visit Place:</strong> " + visitPlace + "</p>" +
                "                <p><strong>Total Price:</strong> $" + totalPrice + "</p>" +
                "                <p><strong>Payment Date:</strong> " + paymentDate + "</p>" +
                "                <p><strong>Payment Time:</strong> " + paymentTime + "</p>" +
                "                <p><strong>Booking Date:</strong> " + bookingDate + "</p>" +
                "                <p><strong>End Trip Date:</strong> " + endTripDate + "</p>" +
                "            </div>" +
                "            <p>We appreciate your trust in TravelMate. Have a great journey!</p>" +
                "        </div>" +
                "        <div class='footer'>" +
                "            &copy; " + java.time.Year.now() + " TravelMate. All Rights Reserved." +
                "            <br>For inquiries, contact us at +94-71-382-8744" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(email);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);

            System.out.println("Travel confirmation email sent successfully to: " + toEmail);

        } catch (MessagingException e) {
            System.err.println("Error while sending travel confirmation email: " + e.getMessage());
        }
    }
}
