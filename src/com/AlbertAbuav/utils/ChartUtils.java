package com.AlbertAbuav.utils;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.dao.CouponsDAO;
import com.AlbertAbuav.dbdao.CouponsDBDAO;

import java.util.ArrayList;
import java.util.List;

public class ChartUtils {

    private static CouponsDAO couponsDAO = new CouponsDBDAO();

    public static String centerString(String str, int len) {
        int padSize = len - str.length();
        int padStart = padSize / 2;
        int padEnd = 0;
        if (padSize%2 == 0) {
            padEnd = (padSize / 2);
        } else {
            padEnd = (padSize / 2) + 1;
        }
        return String.format("%" + padStart + "s" + "%" + str.length() + "s" + "%" +padEnd+ "s", "", str, "");
    }

    public static void printCompanyHeader() {
        Colors.setCyanBoldPrint(String.format("%s", "=============================================================================="));
        Colors.setCyanBoldPrint(String.format("%s %3s %s %15s %s %30s %s %15s %s", "||", "Id", "|", centerString("Name", 15), "|", centerString("Email", 30), "|" , centerString("Password", 15), "||"));
        Colors.setCyanBoldPrint(String.format("%s", "=============================================================================="));
    }

    public static void printCompanyDetails(Company c) {
        System.out.println(String.format("%s %3d %s %15s %s %30s %s %15s %s", Colors.CYAN_BOLD + "||" + Colors.RESET, c.getId(), Colors.CYAN_BOLD + "|" + Colors.RESET, centerString(c.getName(),15), Colors.CYAN_BOLD + "|" + Colors.RESET, centerString(c.getEmail(), 30), Colors.CYAN_BOLD + "|" + Colors.RESET, centerString(c.getPassword(), 15), Colors.CYAN_BOLD + "||" + Colors.RESET));
        Colors.setCyanBoldPrint(String.format("%s", "------------------------------------------------------------------------------"));
        System.out.println(String.format("%10s %77s %s", Colors.CYAN_BOLD + "|| Coupons:" + Colors.RESET, Colors.CYAN_BOLD + "||" + Colors.RESET, PrintUtils.listToString(c.getCoupons())));
        Colors.setCyanBoldPrint(String.format("%s", "=============================================================================="));
    }

    public static void printCompany (Company company) {
        printCompanyHeader();
        printCompanyDetails(company);
        System.out.println();
    }

    public static void printCompanies (List<Company> companies) {
        printCompanyHeader();
        for (Company company : companies) {
            List <Coupon> temp = couponsDAO.getAllCouponsOfSingleCompany(company.getId());
            company.setCoupons(temp);
            printCompanyDetails(company);
        }
        System.out.println();
    }


//==========================================================


    public static void printCustomerHeader() {
        Colors.setPurpleBoldPrint(String.format("%s", "=========================================================================================================="));
        Colors.setPurpleBoldPrint(String.format("%s %3s %s %20s %s %20s %s %30s %s %15s %s", "||", "Id", "|", centerString("First name", 20), "|", centerString("Last name", 20), "|", centerString("Email", 30), "|" , centerString("Password", 15), "||"));
        Colors.setPurpleBoldPrint(String.format("%s", "=========================================================================================================="));
    }

    public static void printCustomerDetails(Customer cu) {
        System.out.println(String.format("%s %3d %s %20s %s %20s %s %30s %s %15s %s", Colors.PURPLE_BOLD + "||" + Colors.RESET, cu.getId(), Colors.PURPLE_BOLD + "|" + Colors.RESET, centerString(cu.getFirstName(),20), Colors.PURPLE_BOLD + "|" + Colors.RESET, centerString(cu.getLastName(), 20), Colors.PURPLE_BOLD + "|" + Colors.RESET, centerString(cu.getEmail(), 30), Colors.PURPLE_BOLD + "|" + Colors.RESET, centerString(cu.getPassword(), 15), Colors.PURPLE_BOLD + "||" + Colors.RESET));
        Colors.setPurpleBoldPrint(String.format("%s", "----------------------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%10s %105s %s", Colors.PURPLE_BOLD + "|| Coupons:" + Colors.RESET, Colors.PURPLE_BOLD + "||" + Colors.RESET, PrintUtils.listToString(cu.getCoupons())));
        Colors.setPurpleBoldPrint(String.format("%s", "=========================================================================================================="));
    }

    public static void printCustomer (Customer customer) {
        printCustomerHeader();
        printCustomerDetails(customer);
        System.out.println();
    }

    public static void printCustomers (List<Customer> customers) {
        printCustomerHeader();
        for (Customer customer : customers) {
            List<CustomersVsCoupons> temp = couponsDAO.getAllCustomersCoupons(customer.getId());
            if (temp.size() != 0) {
                List<Coupon> temp2 = new ArrayList<>();
                for (CustomersVsCoupons customersVsCoupons : temp) {
                    Coupon tempCoupon = couponsDAO.getSingleCoupon(customersVsCoupons.getCouponID());
                    temp2.add(tempCoupon);
                }
                if (temp2.size() != 0) {
                    customer.setCoupons(temp2);
                }
            }
            printCustomerDetails(customer);
        }
        System.out.println();
    }

}
