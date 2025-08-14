package com.fbn.case_study.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class EncryptionService {
    private final Logger logger = LoggerFactory.getLogger(EncryptionService.class.getName());

    @Value("${key}")
    private String key;

    @Value("${iv}")
    private String iv;

    public String encrypt(String plainData){
        try {
            SecretKey key = binaryToSecretKey();
            IvParameterSpec iv = binaryToIv();
            Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encrypted = cipher.doFinal(plainData.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  String decrypt(String encryptedData) {
        try {
            logger.info("Encrypted text {}", encryptedData);

            SecretKey key = binaryToSecretKey();
            IvParameterSpec iv = binaryToIv();
            Cipher cipher = initCipher(Cipher.DECRYPT_MODE, key, iv);
            byte[] encrypted = Base64.getDecoder().decode(encryptedData);
            byte[] decrypted = cipher.doFinal(encrypted);
            logger.info("Decrypted {}", new String(decrypted));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKey binaryToSecretKey() {
        String binaryText = key;
        try{
            byte[] keyBytes = binaryStringToByteArray(binaryText);
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            logger.info("key: {}",key);
            return key;
        }catch (Exception ex){
            logger.error("Error converting to secretkey {}", ex.getMessage());
            return null;
        }
    }

    private byte[] binaryStringToByteArray(String binaryString) {
        int len = binaryString.length();
        byte[] keyBytes = new byte[len / 8];
        for (int i = 0; i < len; i += 8) {
            keyBytes[i / 8] = (byte) Integer.parseInt(binaryString.substring(i, i + 8), 2);
        }
        return keyBytes;
    }

    private IvParameterSpec binaryToIv(){
        String ivText = iv;
        try{
            logger.info("ivtext:  {}", ivText.length());
            byte[] ivBytes = binaryStringToByteArray(ivText);
            logger.info("bytes length {}", ivBytes.length);
            // Create an IvParameterSpec from the byte array
            return new IvParameterSpec(ivBytes);
        }catch(Exception ex){
            logger.error("An Error converting to secretkey {}", ex.getMessage());
            return null;
        }
    }

    private Cipher initCipher(int mode, SecretKey key, IvParameterSpec iv) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, key, iv);
        return cipher;
    }
}
