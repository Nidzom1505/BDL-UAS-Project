/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Koneksi;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nidzzz
 */
public class konek1 {

    private Connection connection;
    private static final konek konek = new konek();
    private static final String AES_KEY = "1234567887654321"; // Harus 16 karakter untuk AES-128

    public static konek getKonek() {
        return konek;
    }

    public Connection getConnection() {
        return connection;
    }

    public konek1() {
        String configFilePath = "src/Koneksi/koneksi2.txt";
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(configFilePath);
            properties.load(fis);

            // Dekripsi nilai properti terenkripsi
            String host = AESUtil.decrypt(properties.getProperty("host"), AES_KEY);
            String port = AESUtil.decrypt(properties.getProperty("port"), AES_KEY);
            String database = AESUtil.decrypt(properties.getProperty("database"), AES_KEY);
            String username = AESUtil.decrypt(properties.getProperty("username"), AES_KEY);
            String password = AESUtil.decrypt(properties.getProperty("password"), AES_KEY);

            String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;

            connection = DriverManager.getConnection(url, username, password);
            if (connection == null) {
                System.out.println("koneksi gagal");
            } else {
                System.out.println("koneksi berhasil");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(konek.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(konek.class.getName()).log(Level.SEVERE, "Error saat dekripsi", ex);
        }
    }
}
