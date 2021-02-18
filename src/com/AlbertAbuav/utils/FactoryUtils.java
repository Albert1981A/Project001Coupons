package com.AlbertAbuav.utils;

import java.rmi.server.UID;
import java.util.UUID;

public class FactoryUtils {

    public static String generateFirstName() {
        FirstName firstName = FirstName.values()[(int)(Math.random() * FirstName.values().length)];
        return firstName.toString();
    }

    public static String generateLastName() {
        LastName lastName = LastName.values()[(int)(Math.random() * LastName.values().length)];
        return lastName.toString();
    }

    public static String generateCustomerEmailType() {
        EmailType emailType = EmailType.values()[(int)(Math.random() * EmailType.values().length)];
        return "@" + emailType.toString() + ".com";
    }

    public static String createPassword() {
        String name = UUID.randomUUID().toString();
        char[] nameChar = name.toCharArray();
        char[] password = new char[8];
        for (int i = 0; i < password.length; i++) {
            password[i] = nameChar[i];
        }
        String strPassword = String.valueOf(password);
        return strPassword;
    }
}
