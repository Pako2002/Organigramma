package com.example.organigramma.Composite;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    //private int ID;
    private String Name;
    private String password;

    public User(String Name, String password) {
        //this.ID = ID;
        this.Name = Name;
        this.password = password;
    }
    //public int getID() {return ID;}
    //public void setID(int ID) {this.ID = ID;}
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //capire come funziona l'hashing della password
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
