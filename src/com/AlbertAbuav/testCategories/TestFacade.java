package com.AlbertAbuav.testCategories;

import com.AlbertAbuav.beans.Category;
import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.dao.CategoriesDAO;
import com.AlbertAbuav.db.ConnectionPool;
import com.AlbertAbuav.db.DatabaseManager;
import com.AlbertAbuav.dbdao.CategoriesDBDAO;
import com.AlbertAbuav.exceptions.invalidAdminException;
import com.AlbertAbuav.exceptions.invalidCompanyException;
import com.AlbertAbuav.exceptions.invalidCustomerException;
import com.AlbertAbuav.facades.AdminFacade;
import com.AlbertAbuav.facades.CompanyFacade;
import com.AlbertAbuav.facades.CustomerFacade;
import com.AlbertAbuav.login.ClientType;
import com.AlbertAbuav.login.LoginManager;
import com.AlbertAbuav.utils.ArtUtils;
import com.AlbertAbuav.utils.DateUtils;

import java.time.LocalDate;
import java.util.Date;

public class TestFacade {

    public static void main(String[] args) {

        System.out.println("START");

        /**
         * Step 1 - Setup to MySql Driver using Reflection API
         */
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        /**
         * creating 10 connections loaded in "Stuck" in "ConnectionPool" class.
         */
        ConnectionPool.getInstance();

        /**
         * using the "DatabaseManager" query's to create and drop "Schemas" and "Tables".
         */
        DatabaseManager.dropSchema();
        DatabaseManager.createSchema();
        DatabaseManager.createTableCompanies();
        DatabaseManager.createTableCategories();
        DatabaseManager.createTableCustomers();
        DatabaseManager.createTableCoupons();
        DatabaseManager.createTableCustomersVsCoupons();

        System.out.println(ArtUtils.CATEGORIES_DAO);
        System.out.println();

        CategoriesDAO categoriesDAO = new CategoriesDBDAO();

        System.out.println("----------------------- QUERY ADD ALL CATEGORIES ------------------------");
        System.out.println();
        categoriesDAO.addCategories();

        System.out.println("----------------------- QUERY GET ALL CATEGORIES ------------------------");
        categoriesDAO.getAllCategories().forEach(System.out::println);
        System.out.println();

        /**
         * Invoking the LoginManager to login to the requested facade
         */
        LoginManager loginManager = LoginManager.getInstance();

        /**
         * Login to Admin Facade
         */
        System.out.println(ArtUtils.ADMIN_FACADE);
        AdminFacade adminFacade = (AdminFacade)loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        System.out.println("------------------------------ Checking Connection to Admin ----------------------------");
        System.out.println(adminFacade);
        System.out.println();

        System.out.println("---------------------------------- Adding new companies --------------------------------");

        try {
            for (int i = 0; i < 10; i++) {
                adminFacade.addCompany(new Company());
            }
//            Company c1 = new Company();
//            adminFacade.addCompany(c1);
//            Company c2 = new Company();
//            adminFacade.addCompany(c2);
//            Company c3 = new Company();
//            adminFacade.addCompany(c3);
//            Company c4 = new Company();
//            adminFacade.addCompany(c4);
//            Company c5 = new Company();
//            adminFacade.addCompany(c5);
//            Company c6 = new Company();
//            adminFacade.addCompany(c6);
//            Company c7 = new Company();
//            adminFacade.addCompany(c7);
//            Company c8 = new Company();
//            adminFacade.addCompany(c8);
//            Company c9 = new Company();
//            adminFacade.addCompany(c9);
//            Company c10 = new Company();
//            adminFacade.addCompany(c10);
            adminFacade.getAllCompanies().forEach(System.out::println);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------------------- Attempt to add a company with an existing name -------------------");
        Company temp =null;
        try {
            temp = adminFacade.getSingleCompany(1);
            Company companyToAdd1 = new Company(temp.getName(), "email@email.com", "11111");
            adminFacade.addCompany(companyToAdd1);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------------------- Attempt to add a company with an existing email ------------------");

        try {
            Company companyToAdd2 = new Company("name", temp.getEmail(), "22222");
            adminFacade.addCompany(companyToAdd2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------------------- Update a Company ---------------------------------");

        try {
            Company companyToUpdate = adminFacade.getSingleCompany(2);
            System.out.println("Updating Company: \n" + companyToUpdate);
            companyToUpdate.setEmail("email.email.com");
            adminFacade.updateCompany(companyToUpdate);
            System.out.println("Company: " + companyToUpdate.getName() + " - Email Updated");
            System.out.println(adminFacade.getSingleCompany(2));
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("----------------------------- Attempt to update a Company's id -------------------------");

        try {
            Company companyToUpdate2 = adminFacade.getSingleCompany(3);
            System.out.println("Updating Company: \n" + companyToUpdate2);
            companyToUpdate2.setId(2);
            adminFacade.updateCompany(companyToUpdate2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("----------------------------- Attempt to update a Company's Name -------------------------");

        try {
            Company companyToUpdate3 = adminFacade.getSingleCompany(4);
            System.out.println("Updating Company: \n" + companyToUpdate3);
            companyToUpdate3.setName(adminFacade.getSingleCompany(5).getName());
            adminFacade.updateCompany(companyToUpdate3);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------- Attempt to update a company whose name does not exist in the system ----------");

        try {
            Company companyToUpdate4 = adminFacade.getSingleCompany(5);
            companyToUpdate4.setName("Looly");
            System.out.println("Updating Company: \n" + companyToUpdate4);
            adminFacade.updateCompany(companyToUpdate4);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------------------ Adding Customers ----------------------------------");
        try {
            for (int i = 0; i < 10; i++) {
                adminFacade.addCustomer(new Customer());
            }
            adminFacade.getAllCustomers().forEach(System.out::println);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------------------- Attempt to add a customer with an existing email -----------------");
        try {
            Customer temp1 = new Customer("firstName", "lastName", adminFacade.getSingleCustomer(1).getEmail(), "11111");
            adminFacade.addCustomer(temp1);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("-------------------------------------- Update customer ---------------------------------");
        Customer toUpdate = null;
        try {
            toUpdate = adminFacade.getSingleCustomer(1);
            System.out.println("Update First name of customer: \n" + toUpdate);
            toUpdate.setFirstName("Laura");
            adminFacade.updateCustomer(toUpdate);
            System.out.println(adminFacade.getSingleCustomer(1));
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------- Attempt to update a customer email and id --------------------");

        try {
            Customer toUpdate1 = adminFacade.getSingleCustomer(2);
            System.out.println("Update email of customer: \n" + toUpdate1);
            toUpdate1.setEmail("email@email.com");
            toUpdate1.setId(3);
            System.out.println(toUpdate1);
            adminFacade.updateCustomer(toUpdate1);
            System.out.println(adminFacade.getSingleCustomer(3));
            System.out.println("The system will choose to change the object according to its - id!");
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------------ Attempt to update a customer id -------------------------");

        try {
            Customer toUpdate2 = adminFacade.getSingleCustomer(4);
            System.out.println("Update id of customer: \n" + toUpdate2);
            toUpdate2.setId(2);
            adminFacade.updateCustomer(toUpdate2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();


        /**
         * Login to Company Facade
         */
        System.out.println(ArtUtils.COMPANIES_FACADE);

        Company loggedCompany1 = null;
        Company loggedCompany2 = null;
        Company loggedCompany3 = null;
        try {
            loggedCompany1 = adminFacade.getSingleCompany(4);
            loggedCompany2 = adminFacade.getSingleCompany(2);
            loggedCompany3 = adminFacade.getSingleCompany(3);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        CompanyFacade companyFacade1 = (CompanyFacade)loginManager.login(loggedCompany1.getEmail(), loggedCompany1.getPassword(), ClientType.COMPANY);
        CompanyFacade companyFacade2 = (CompanyFacade)loginManager.login(loggedCompany2.getEmail(), loggedCompany2.getPassword(), ClientType.COMPANY);
        CompanyFacade companyFacade3 = (CompanyFacade)loginManager.login(loggedCompany3.getEmail(), loggedCompany3.getPassword(), ClientType.COMPANY);

        System.out.println("----------------------------- Checking Connection to Company ---------------------------");

        System.out.println(companyFacade1);
        System.out.println(companyFacade2);
        System.out.println(companyFacade3);
        System.out.println();

        System.out.println("------ Trying to get all companies coupons when their is no coupons in the system ------");
        try {
            companyFacade1.getAllCompanyCoupons().forEach(System.out::println);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("---------------------------- Adding new coupon to the company --------------------------");
        System.out.println();

        System.out.println("Trying to add coupons: ");
        System.out.println("----------------------");
        try {
            for (int i = 0; i < 7; i++) {
                companyFacade1.addCoupon(new Coupon(loggedCompany1.getId()));
            }
            for (int i = 0; i < 3; i++) {
                companyFacade2.addCoupon(new Coupon(loggedCompany2.getId()));
            }
            for (int i = 0; i < 3; i++) {
                companyFacade3.addCoupon(new Coupon(loggedCompany3.getId()));
            }
            System.out.println("The coupons have been added to company id-4:");
            System.out.println(companyFacade1.getTheLoggedCompanyDetails());
            System.out.println();
            System.out.println("The coupons have been added to company id-2:");
            System.out.println(companyFacade2.getTheLoggedCompanyDetails());
            System.out.println();
            System.out.println("The coupons have been added to company id-3:");
            System.out.println(companyFacade3.getTheLoggedCompanyDetails());
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------------------------------- Update a company coupon ------------------------------");
        try {
            Coupon couponToUpdate = companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(1);
            System.out.println("Trying to update the \"amount\" of coupon id-2 to be \"23\": ");
            System.out.println(couponToUpdate);
            couponToUpdate.setAmount(23);
            companyFacade1.updateCoupon(couponToUpdate);
            System.out.println(companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(1));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------ Attempt to Update a company coupons title ---------------------");
        try {
            Coupon couponToUpdate2 = companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(2);
            System.out.println("Trying to update the \"title\" of coupon id 3: ");
            System.out.println(couponToUpdate2);
            couponToUpdate2.setTitle("The new Title!");
            companyFacade1.updateCoupon(couponToUpdate2);
            System.out.println(companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(2));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("-------------------------- Attempt to Update a company coupons id ----------------------");
        try {
            Coupon couponToUpdate3 = companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(0);
            System.out.println("Trying to update the \"id\" of coupon id 1: ");
            System.out.println(couponToUpdate3);
            couponToUpdate3.setId(7);
            companyFacade1.updateCoupon(couponToUpdate3);
            System.out.println(companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(0));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("-------------------------- Attempt to Update a coupons company id ----------------------");
        try {
            Coupon couponToUpdate4 = companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(0);
            System.out.println("Trying to update the \"company id\" of coupon id 1: ");
            System.out.println(couponToUpdate4);
            couponToUpdate4.setCompanyID(7);
            companyFacade1.updateCoupon(couponToUpdate4);
            System.out.println(companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(0));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------- Attempt to Update a coupon id and Title ----------------------");
        try {
            Coupon couponToUpdate5 = companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(0);
            System.out.println("Trying to update the \"id\" of coupon id 1: ");
            System.out.println(couponToUpdate5);
            couponToUpdate5.setId(3);
            couponToUpdate5.setTitle("The new Title2");
            companyFacade1.updateCoupon(couponToUpdate5);
            System.out.println(companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(2));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The system will choose to change the object according to its - id!");
        System.out.println();

        System.out.println("---------------- Get all coupons from a specific category of the company ---------------");
        try {
            Category categoryToCheck = companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(1).getCategory();
            companyFacade1.getAllCompanyCouponsOfSpecificCategory(categoryToCheck).forEach(System.out::println);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("-------------- Get all coupons up to the maximum price set by the company --------------");
        try {
            companyFacade1.getAllCompanyCouponsUpToMaxPrice(75).forEach(System.out::println);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();


        /**
         * Login to Customer Facade
         */
        System.out.println(ArtUtils.CUSTOMERS_FACADE);
        Customer loggedCustomer1 = null;
        Customer loggedCustomer2 = null;
        Customer loggedCustomer3 = null;
        try {
            loggedCustomer1 = adminFacade.getSingleCustomer(4);
            loggedCustomer2 = adminFacade.getSingleCustomer(6);
            loggedCustomer3 = adminFacade.getSingleCustomer(7);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        CustomerFacade customerFacade1 = (CustomerFacade) loginManager.login(loggedCustomer1.getEmail(), loggedCustomer1.getPassword(), ClientType.CUSTOMER);
        CustomerFacade customerFacade2 = (CustomerFacade) loginManager.login(loggedCustomer2.getEmail(), loggedCustomer2.getPassword(), ClientType.CUSTOMER);
        CustomerFacade customerFacade3 = (CustomerFacade) loginManager.login(loggedCustomer3.getEmail(), loggedCustomer3.getPassword(), ClientType.CUSTOMER);
        System.out.println("----------------------------- Checking Connection to Customer --------------------------");
        System.out.println(customerFacade1);
        System.out.println(customerFacade2);
        System.out.println(customerFacade3);
        System.out.println();

        System.out.println("------------------------- Adding new coupon to CustomersVsCoupons ----------------------");
        System.out.println("Attempting to add coupons:");
        System.out.println();

        try {
            customerFacade1.addCoupon(companyFacade1.getSingleCoupon(1));
            customerFacade1.addCoupon(companyFacade1.getSingleCoupon(2));
            customerFacade1.addCoupon(companyFacade1.getSingleCoupon(4));
            customerFacade1.addCoupon(companyFacade2.getSingleCoupon(9));
            customerFacade1.addCoupon(companyFacade3.getSingleCoupon(12));
            System.out.println("Coupons were added to customer \"" + customerFacade1.getTheLoggedCustomerDetails().getFirstName() + " " + customerFacade1.getTheLoggedCustomerDetails().getLastName() + "\":");
            System.out.println(customerFacade1.getTheLoggedCustomerDetails());
            System.out.println();
            customerFacade2.addCoupon(companyFacade1.getSingleCoupon(2));
            customerFacade2.addCoupon(companyFacade2.getSingleCoupon(9));
            customerFacade2.addCoupon(companyFacade2.getSingleCoupon(10));
            customerFacade2.addCoupon(companyFacade3.getSingleCoupon(12));
            customerFacade2.addCoupon(companyFacade3.getSingleCoupon(13));
            System.out.println("Coupons were added to customer \"" + customerFacade2.getTheLoggedCustomerDetails().getFirstName() + " " + customerFacade1.getTheLoggedCustomerDetails().getLastName() + "\":");
            System.out.println(customerFacade2.getTheLoggedCustomerDetails());
            System.out.println();
            customerFacade3.addCoupon(companyFacade1.getSingleCoupon(1));
            customerFacade3.addCoupon(companyFacade1.getSingleCoupon(2));
            customerFacade3.addCoupon(companyFacade1.getSingleCoupon(4));
            customerFacade3.addCoupon(companyFacade2.getSingleCoupon(8));
            customerFacade3.addCoupon(companyFacade2.getSingleCoupon(10));
            customerFacade3.addCoupon(companyFacade3.getSingleCoupon(11));
            customerFacade3.addCoupon(companyFacade3.getSingleCoupon(12));
            System.out.println("Coupons were added to customer \"" + customerFacade3.getTheLoggedCustomerDetails().getFirstName() + " " + customerFacade1.getTheLoggedCustomerDetails().getLastName() + "\":");
            System.out.println(customerFacade3.getTheLoggedCustomerDetails());
        } catch (invalidCompanyException | invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("---------- Attempt to add a coupon that is out of stock to CustomersVsCoupons ----------");
        System.out.println("Attempting to add coupon:");
        try {
            Coupon couponToPurchase11 = companyFacade1.getSingleCoupon(5);
            couponToPurchase11.setAmount(0);
            System.out.println(couponToPurchase11);
            customerFacade1.addCoupon(couponToPurchase11);
        } catch (invalidCompanyException | invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        try {
            System.out.println("Get all customer coupons:");
            customerFacade1.getAllCustomerCoupons().forEach(System.out::println);
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("---------- Attempt to add a coupon that has been expired to CustomersVsCoupons ---------");
        System.out.println("Attempting to add coupons:");
        try {
            Coupon couponToPurchase12 = companyFacade1.getSingleCoupon(6);
            Date reduceDate = DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(2));
            couponToPurchase12.setEndDate(reduceDate);
            System.out.println(couponToPurchase12);
            System.out.println("The date was updated to an expired date: " + DateUtils.beautifyDate(reduceDate));
            customerFacade1.addCoupon(couponToPurchase12);
            System.out.println("The coupon was added:");
            customerFacade1.getAllCustomerCoupons().forEach(System.out::println);
        } catch (invalidCompanyException | invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------ Attempt to add a coupon that already exist in CustomersVsCoupons ----------");
        System.out.println("Attempting to add coupon:");
        try {
            Coupon existingCoupon = companyFacade1.getSingleCoupon(1);
            System.out.println(existingCoupon);
            customerFacade1.addCoupon(existingCoupon);
            System.out.println("The coupon was added:");
            customerFacade1.getAllCustomerCoupons().forEach(System.out::println);
        } catch (invalidCustomerException | invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("----------------------- Get all coupons from a specific category -----------------------");
        try {
            Coupon couponToCheck = companyFacade1.getSingleCoupon(2);
            customerFacade1.getAllCustomerCouponsOfSpecificCategory(couponToCheck.getCategory()).forEach(System.out::println);
        } catch (invalidCustomerException | invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------ Get all coupons up to the maximum price -----------------------");
        try {
            System.out.println("High attempt:");
            customerFacade1.getAllCustomerCouponsUpToMaxPrice(95).forEach(System.out::println);
            System.out.println("Low attempt:");
            customerFacade1.getAllCustomerCouponsUpToMaxPrice(62).forEach(System.out::println);
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("---------------------------- Get the logged customer details ---------------------------");
        try {
            System.out.println(customerFacade1.getTheLoggedCustomerDetails());
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println(ArtUtils.DELETING_METHODS);

        System.out.println("--------------------------- Company attempt to delete a coupon -------------------------");
        try {
            Coupon couponToDelete = companyFacade2.getSingleCoupon(9);
            System.out.println("Attempt to delete the coupon: ");
            System.out.println(couponToDelete);
            System.out.println();
            System.out.println("The customers that purchase the coupon are: ");
            companyFacade2.getAllCompanyCustomersOfASingleCouponByCouponId(couponToDelete.getId()).forEach(System.out::println);
            companyFacade2.deleteCoupon(couponToDelete);
            System.out.println("The coupon was deleted! ");
            System.out.println();
            System.out.println("Company coupons left :");
            companyFacade2.getAllCompanyCoupons().forEach(System.out::println);
            System.out.println();
            System.out.println("The customer that purchase the coupon after deleting the coupon:");
            companyFacade2.getAllCompanyCustomersOfASingleCouponByCouponId(couponToDelete.getId()).forEach(System.out::println);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------------ Deleting an existing customer ---------------------------");
        try {
            Customer customerToDelete = adminFacade.getSingleCustomer(7);
            System.out.println("Attempting to delete customer: ");
            System.out.println(customerToDelete);
            adminFacade.deleteCustomer(customerToDelete);
            System.out.println("Customer deleted!");
            System.out.println("Customers left:");
            adminFacade.getAllCustomers().forEach(System.out::println);
            System.out.println("Is customer exist: ");
            adminFacade.getAllCustomersVsCoupons(customerToDelete.getId()).forEach(System.out::println);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------------ Deleting an existing company ----------------------------");

        try {
            Company companyToDelete = adminFacade.getSingleCompany(2);
            System.out.println("Attempting to delete company: ");
            System.out.println(companyToDelete);
            System.out.println();
            adminFacade.deleteCompany(companyToDelete);
            System.out.println("Company deleted!");
            System.out.println("Companies left:");
            adminFacade.getAllCompanies().forEach(System.out::println);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Is customer purchases exist: ");
        try {
            System.out.println(adminFacade.getSingleCompany(2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        /**
         * closing all connections.
         */
        ConnectionPool.getInstance().closeAllConnections();

        System.out.println("End");
    }
}
