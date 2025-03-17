package lk.codebridge.TravelMate.service.impl;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import lk.codebridge.TravelMate.service.service.OtherServices;

@Service
public class OtherServicesImpl implements OtherServices {

    private static final SecureRandom random = new SecureRandom();

    @Override
    public String generateOTP() {
        int otp = 100000 + random.nextInt(900000); // Generates a 6-digit number
        return String.valueOf(otp);
    }

}
