/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Koneksi;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Base64;

/**
 *
 * @author Nidzzz
 */
public class AESUtil {

    private static final String ALGORITHM = "AES";

    // Enkripsi
    public static String encrypt(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // Dekripsi
    public static String decrypt(String encryptedData, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        return new String(cipher.doFinal(decoded));
    }

    public static void main(String[] args) throws Exception {
        String key = "1234567887654321";
        System.out.println("host=" + AESUtil.encrypt("localhost", key));
        System.out.println("port=" + AESUtil.encrypt("5432", key));
        System.out.println("database=" + AESUtil.encrypt("DBUAS", key));
        System.out.println("username=" + AESUtil.encrypt("postgres", key));
        System.out.println("password=" + AESUtil.encrypt("isma111004", key));
    }
}
