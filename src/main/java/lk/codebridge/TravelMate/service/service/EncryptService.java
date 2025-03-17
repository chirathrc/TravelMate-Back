package lk.codebridge.TravelMate.service.service;

import org.springframework.stereotype.Service;

@Service
public interface EncryptService {


    public String encrypt(String data) throws Exception;
    public String decrypt(String strToDecrypt) throws Exception;
    
}
