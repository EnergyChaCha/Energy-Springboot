package com.chacha.energy.cj.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Aes256Util {
    private static String KEY;
    private static byte[] SALT;
    private static String IV;

    @Value("${aes256.key}")
    public void setKEY(String KEY) {
        Aes256Util.KEY = KEY;
    }

    @Value("${aes256.salt}")
    public void setSALT(String SALT) throws DecoderException {
        Aes256Util.SALT = Hex.decodeHex(SALT.toCharArray());
    }

    @Value("${aes256.iv}")
    public void setIV(String IV) {
        Aes256Util.IV = IV;
    }

    public static String encrypt(String str) {
        try {
            SecretKey key = generateKey(KEY);
            byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, IV, str.getBytes("UTF-8"));
            return encodeHex(encrypted);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.ENCRYPT_FAIL);
        }
    }

    public static String encrypt(String str, String salt) {
        try {
            SecretKey key = generateKey(KEY, salt);
            byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, IV, str.getBytes("UTF-8"));
            return encodeHex(encrypted);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.ENCRYPT_FAIL);
        }
    }

    public static String decrypt(String str) {
        try {
            SecretKey key = generateKey(KEY);
            byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, IV, decodeHex(str));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.DECRYPT_FAIL);
        }
    }

    public static String decrypt(String str, String salt) {
        try {
            SecretKey key = generateKey(KEY, salt);
            byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, IV, decodeHex(str));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.DECRYPT_FAIL);
        }
    }

    public static Integer decryptBpm(String str) {
        try {
            return Integer.parseInt(decrypt(str));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.DECRYPT_FAIL);
        }
    }


    private static byte[] doFinal(int encryptMode, SecretKey key, String iv, byte[] bytes) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(encryptMode, key, new IvParameterSpec(decodeHex(iv)));
        return cipher.doFinal(bytes);
    }

    private static SecretKey generateKey(String passPhrase) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        // generate key with salt
        PBEKeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), SALT, 3000, 256);
        SecretKey key = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");

        return key;
    }

    private static SecretKey generateKey(String passPhrase, String salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        // generate custom salt
        PBEKeySpec saltSpec = new PBEKeySpec(salt.toCharArray(), SALT, 3000, 128);
        SecretKey saltKey = new SecretKeySpec(factory.generateSecret(saltSpec).getEncoded(), "AES");

        // generate key with custom salt
        PBEKeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), saltKey.toString().getBytes(), 3000, 256);
        SecretKey key = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");

        return key;
    }

    private static String encodeHex(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    private static byte[] decodeHex(String str) throws Exception {
        return Hex.decodeHex(str.toCharArray());
    }
}

