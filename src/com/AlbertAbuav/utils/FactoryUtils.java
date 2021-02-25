package com.AlbertAbuav.utils;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.dao.CompaniesDAO;
import com.AlbertAbuav.dbdao.CompaniesDBDAO;

import java.util.List;
import java.util.UUID;

public class FactoryUtils {

    /**
     * This method generate a random customer first name.
     * @return String
     */
    public static String generateFirstName() {
        FirstName firstName = FirstName.values()[(int)(Math.random() * FirstName.values().length)];
        return firstName.toString();
    }

    /**
     * This method generate a random customer last name.
     * @return String
     */
    public static String generateLastName() {
        LastName lastName = LastName.values()[(int)(Math.random() * LastName.values().length)];
        return lastName.toString();
    }

    /**
     * This method sends a random company name.
     * There can be no two identical company names!
     * The method checks in the data base if the name exists before sending it.
     * If the name exist it's changes.
     * @return String
     */
    public static String generateCompanyName() {
        String companyName = CompaniesType.values()[(int)(Math.random() * CompaniesType.values().length)].toString();
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        List<Company> companyList = companiesDAO.getAllCompanies();
        if (companyList.size() == 0) {
            return companyName;
        }
        boolean isExist = true;
        while (isExist) {
            for (Company company : companyList) {
                if (companyName.equals(company.getName())) {
                    isExist = true;
                    companyName = CompaniesType.values()[(int)(Math.random() * CompaniesType.values().length)].toString();
                    break;
                } else {
                    isExist = false;
                }
            }
        }
        return companyName;
    }

    /**
     * This method generate a random ending for a customers mail.
     * @return String
     */
    public static String generateCustomerEmailType() {
        EmailType emailType = EmailType.values()[(int)(Math.random() * EmailType.values().length)];
        return "@" + emailType.toString() + ".com";
    }

    /**
     * This method generate a random 8 figure password.
     * @return String
     */
    public static String createPassword() {
        String name = UUID.randomUUID().toString();
        char[] nameChar = name.toCharArray();
        char[] password = new char[8];
        for (int i = 0; i < password.length; i++) {
            password[i] = nameChar[i];
        }
        return String.valueOf(password);
    }
}
