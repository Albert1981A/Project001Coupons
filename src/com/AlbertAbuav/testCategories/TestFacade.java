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
            Company c1 = new Company();
            adminFacade.addCompany(c1);
            Company c2 = new Company();
            adminFacade.addCompany(c2);
            Company c3 = new Company();
            adminFacade.addCompany(c3);
            Company c4 = new Company();
            adminFacade.addCompany(c4);
            Company c5 = new Company();
            adminFacade.addCompany(c5);
            Company c6 = new Company();
            adminFacade.addCompany(c6);
            Company c7 = new Company();
            adminFacade.addCompany(c7);
            Company c8 = new Company();
            adminFacade.addCompany(c8);
            Company c9 = new Company();
            adminFacade.addCompany(c9);
            Company c10 = new Company();
            adminFacade.addCompany(c10);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        try {
            adminFacade.getAllCompanies().forEach(System.out::println);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------------------- Attempt to add a company with an existing name -------------------");
        Company temp = adminFacade.getSingleCompany(1);
        Company companyToAdd1 = new Company(temp.getName(), "email@email.com", "11111");
        try {
            adminFacade.addCompany(companyToAdd1);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------------------- Attempt to add a company with an existing email ------------------");
        Company companyToAdd2 = new Company("name", temp.getEmail(), "22222");
        try {
            adminFacade.addCompany(companyToAdd2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------------------- Update a Company ---------------------------------");
        Company companyToUpdate = adminFacade.getSingleCompany(2);
        System.out.println("Updating Company: \n" + companyToUpdate);
        companyToUpdate.setEmail("email.email.com");
        try {
            adminFacade.updateCompany(companyToUpdate);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Company: " + companyToUpdate.getName() + " - Email Updated");
        System.out.println(adminFacade.getSingleCompany(2));
        System.out.println();

        System.out.println("----------------------------- Attempt to update a Company's id -------------------------");
        Company companyToUpdate2 = adminFacade.getSingleCompany(3);
        System.out.println("Updating Company: \n" + companyToUpdate2);
        companyToUpdate2.setId(2);
        try {
            adminFacade.updateCompany(companyToUpdate2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("----------------------------- Attempt to update a Company's Name -------------------------");
        Company companyToUpdate3 = adminFacade.getSingleCompany(4);
        System.out.println("Updating Company: \n" + companyToUpdate3);
        companyToUpdate3.setName(adminFacade.getSingleCompany(5).getName());
        try {
            adminFacade.updateCompany(companyToUpdate3);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------- Attempt to update a company whose name does not exist in the system ----------");
        Company companyToUpdate4 = adminFacade.getSingleCompany(5);
        System.out.println("Updating Company: \n" + companyToUpdate4);
        companyToUpdate4.setName("Looly");
        try {
            adminFacade.updateCompany(companyToUpdate4);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------------------ Adding Customers ----------------------------------");
        try {
            Customer cu1 = new Customer();
            adminFacade.addCustomer(cu1);
            Customer cu2 = new Customer();
            adminFacade.addCustomer(cu2);
            Customer cu3 = new Customer();
            adminFacade.addCustomer(cu3);
            Customer cu4 = new Customer();
            adminFacade.addCustomer(cu4);
            Customer cu5 = new Customer();
            adminFacade.addCustomer(cu5);
            Customer cu6 = new Customer();
            adminFacade.addCustomer(cu6);
            Customer cu7 = new Customer();
            adminFacade.addCustomer(cu7);
            Customer cu8 = new Customer();
            adminFacade.addCustomer(cu8);
            Customer cu9 = new Customer();
            adminFacade.addCustomer(cu9);
            Customer cu10 = new Customer();
            adminFacade.addCustomer(cu10);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        try {
            adminFacade.getAllCustomers().forEach(System.out::println);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------------------- Attempt to add a customer with an existing email -----------------");
        Customer temp1 = new Customer("firstName", "lastName", adminFacade.getSingleCustomer(1).getEmail(), "11111");
        try {
            adminFacade.addCustomer(temp1);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("-------------------------------------- Update customer ---------------------------------");
        Customer toUpdate = adminFacade.getSingleCustomer(1);
        System.out.println("Update First name of customer: \n" + toUpdate);
        toUpdate.setFirstName("Laura");
        try {
            adminFacade.updateCustomer(toUpdate);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(adminFacade.getSingleCustomer(1));
        System.out.println();

        System.out.println("------------------------- Attempt to update a customer email and id --------------------");
        Customer toUpdate1 = adminFacade.getSingleCustomer(2);
        System.out.println("Update email of customer: \n" + toUpdate1);
        toUpdate1.setEmail("email@email.com");
        toUpdate1.setId(3);
        System.out.println(toUpdate1);
        try {
            adminFacade.updateCustomer(toUpdate1);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(adminFacade.getSingleCustomer(3));
        System.out.println("The system will choose to change the object according to its - id!");
        System.out.println();

        System.out.println("------------------------------ Attempt to update a customer id -------------------------");
        Customer toUpdate2 = adminFacade.getSingleCustomer(4);
        System.out.println("Update id of customer: \n" + toUpdate2);
        toUpdate2.setId(2);
        try {
            adminFacade.updateCustomer(toUpdate2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();



        /**
         * Login to Company Facade
         */
        System.out.println(ArtUtils.COMPANIES_FACADE);

        Company loggedCompany = adminFacade.getSingleCompany(4);
        CompanyFacade companyFacade = (CompanyFacade)loginManager.login(loggedCompany.getEmail(), loggedCompany.getPassword(), ClientType.COMPANY);
        Company loggedCompany2 = adminFacade.getSingleCompany(2);
        CompanyFacade companyFacade2 = (CompanyFacade)loginManager.login(loggedCompany2.getEmail(), loggedCompany2.getPassword(), ClientType.COMPANY);
        Company loggedCompany3 = adminFacade.getSingleCompany(3);
        CompanyFacade companyFacade3 = (CompanyFacade)loginManager.login(loggedCompany3.getEmail(), loggedCompany3.getPassword(), ClientType.COMPANY);
        System.out.println("----------------------------- Checking Connection to Company ---------------------------");
        System.out.println(companyFacade);
        System.out.println(companyFacade2);
        System.out.println(companyFacade3);
        System.out.println();

        System.out.println("---------------------------- Adding new coupon to the company --------------------------");
        System.out.println("Trying to get all companies coupons when their is no coupons in the system:");
        try {
            companyFacade.getAllCompanyCoupons().forEach(System.out::println);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Trying to add coupons: ");
        System.out.println("--------------------------------------");
        Coupon couponToAdd1 = new Coupon(loggedCompany.getId());
        System.out.println(couponToAdd1);
        Coupon couponToAdd2 = new Coupon(loggedCompany.getId());
        System.out.println(couponToAdd2);
        Coupon couponToAdd3 = new Coupon(loggedCompany.getId());
        System.out.println(couponToAdd3);
        Coupon couponToAdd4 = new Coupon(loggedCompany.getId());
        System.out.println(couponToAdd4);
        Coupon couponToAdd5 = new Coupon(loggedCompany.getId());
        System.out.println(couponToAdd5);
        Coupon couponToAdd6 = new Coupon(loggedCompany.getId());
        System.out.println(couponToAdd6);
        Coupon couponToAdd7 = new Coupon(loggedCompany.getId());
        System.out.println(couponToAdd7);
        System.out.println();

        try {
            companyFacade.addCoupon(couponToAdd1);
            companyFacade.addCoupon(couponToAdd2);
            companyFacade.addCoupon(couponToAdd3);
            companyFacade.addCoupon(couponToAdd4);
            companyFacade.addCoupon(couponToAdd5);
            companyFacade.addCoupon(couponToAdd6);
            companyFacade.addCoupon(couponToAdd7);
            System.out.println("Coupons added!\nGet the logged company details:");
            System.out.println(companyFacade.getTheLoggedCompanyDetails());
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("adding coupons of other companies :");
        System.out.println("--------------------------------------");

        System.out.println("Creating new coupons:");
        Coupon couponToAdd8 = new Coupon(loggedCompany2.getId());
        System.out.println(couponToAdd8);
        Coupon couponToAdd9 = new Coupon(loggedCompany2.getId());
        System.out.println(couponToAdd9);
        Coupon couponToAdd10 = new Coupon(loggedCompany2.getId());
        System.out.println(couponToAdd10);
        Coupon couponToAdd11 = new Coupon(loggedCompany3.getId());
        System.out.println(couponToAdd11);
        Coupon couponToAdd12 = new Coupon(loggedCompany3.getId());
        System.out.println(couponToAdd12);
        Coupon couponToAdd13 = new Coupon(loggedCompany3.getId());
        System.out.println(couponToAdd13);

        try {
            companyFacade2.addCoupon(couponToAdd8);
            companyFacade2.addCoupon(couponToAdd9);
            companyFacade2.addCoupon(couponToAdd10);
            System.out.println("The coupons have been added to company id-2:");
            System.out.println(companyFacade2.getTheLoggedCompanyDetails());
            companyFacade3.addCoupon(couponToAdd11);
            companyFacade3.addCoupon(couponToAdd12);
            companyFacade3.addCoupon(couponToAdd13);
            System.out.println("The coupons have been added to company id-3:");
            System.out.println(companyFacade3.getTheLoggedCompanyDetails());
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------------------------------- Update a company coupon ------------------------------");
        try {
            Coupon couponToUpdate = companyFacade.getTheLoggedCompanyDetails().getCoupons().get(1);
            System.out.println("Trying to update the \"amount\" of coupon id 2: ");
            System.out.println(couponToUpdate);
            couponToUpdate.setAmount(23);
            companyFacade.updateCoupon(couponToUpdate);
            System.out.println(companyFacade.getTheLoggedCompanyDetails().getCoupons().get(1));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------ Attempt to Update a company coupons title ---------------------");
        try {
            Coupon couponToUpdate2 = companyFacade.getTheLoggedCompanyDetails().getCoupons().get(2);
            System.out.println("Trying to update the \"title\" of coupon id 3: ");
            System.out.println(couponToUpdate2);
            couponToUpdate2.setTitle("The new Title!");
            companyFacade.updateCoupon(couponToUpdate2);
            System.out.println(companyFacade.getTheLoggedCompanyDetails().getCoupons().get(2));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("-------------------------- Attempt to Update a company coupons id ----------------------");
        try {
            Coupon couponToUpdate3 = companyFacade.getTheLoggedCompanyDetails().getCoupons().get(0);
            System.out.println("Trying to update the \"id\" of coupon id 1: ");
            System.out.println(couponToUpdate3);
            couponToUpdate3.setId(7);
            companyFacade.updateCoupon(couponToUpdate3);
            System.out.println(companyFacade.getTheLoggedCompanyDetails().getCoupons().get(0));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("-------------------------- Attempt to Update a coupons company id ----------------------");
        try {
            Coupon couponToUpdate4 = companyFacade.getTheLoggedCompanyDetails().getCoupons().get(0);
            System.out.println("Trying to update the \"company id\" of coupon id 1: ");
            System.out.println(couponToUpdate4);
            couponToUpdate4.setCompanyID(7);
            companyFacade.updateCoupon(couponToUpdate4);
            System.out.println(companyFacade.getTheLoggedCompanyDetails().getCoupons().get(0));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------- Attempt to Update a coupon id and Title ----------------------");
        try {
            Coupon couponToUpdate5 = companyFacade.getTheLoggedCompanyDetails().getCoupons().get(0);
            System.out.println("Trying to update the \"id\" of coupon id 1: ");
            System.out.println(couponToUpdate5);
            couponToUpdate5.setId(3);
            couponToUpdate5.setTitle("The new Title2");
            companyFacade.updateCoupon(couponToUpdate5);
            System.out.println(companyFacade.getTheLoggedCompanyDetails().getCoupons().get(2));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The system will choose to change the object according to its - id!");
        System.out.println();

        System.out.println("---------------- Get all coupons from a specific category of the company ---------------");
        try {
            Category categoryToCheck = companyFacade.getTheLoggedCompanyDetails().getCoupons().get(1).getCategory();
            companyFacade.getAllCompanyCouponsOfSpecificCategory(categoryToCheck).forEach(System.out::println);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("-------------- Get all coupons up to the maximum price set by the company --------------");
        try {
            companyFacade.getAllCompanyCouponsUpToMaxPrice(75).forEach(System.out::println);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();


        /**
         * Login to Customer Facade
         */
        System.out.println(ArtUtils.CUSTOMERS_FACADE);
        Customer loggedCustomer = adminFacade.getSingleCustomer(4);
        CustomerFacade customerFacade = (CustomerFacade) loginManager.login(loggedCustomer.getEmail(), loggedCustomer.getPassword(), ClientType.CUSTOMER);
        Customer loggedCustomer2 = adminFacade.getSingleCustomer(6);
        CustomerFacade customerFacade2 = (CustomerFacade) loginManager.login(loggedCustomer2.getEmail(), loggedCustomer2.getPassword(), ClientType.CUSTOMER);
        Customer loggedCustomer3 = adminFacade.getSingleCustomer(7);
        CustomerFacade customerFacade3 = (CustomerFacade) loginManager.login(loggedCustomer3.getEmail(), loggedCustomer3.getPassword(), ClientType.CUSTOMER);
        System.out.println("----------------------------- Checking Connection to Customer --------------------------");
        System.out.println(customerFacade);
        System.out.println(customerFacade2);
        System.out.println(customerFacade3);
        System.out.println();

        System.out.println("------------------------- Adding new coupon to CustomersVsCoupons ----------------------");
        System.out.println("Attempting to add coupons:");
        Coupon couponToPurchase1 = null;
        Coupon couponToPurchase2 = null;
        try {
            couponToPurchase1 = companyFacade.getSingleCoupon(1);
            System.out.println(couponToPurchase1);
            couponToPurchase2 = companyFacade.getSingleCoupon(2);
            System.out.println(couponToPurchase2);
            Coupon couponToPurchase4 = companyFacade.getSingleCoupon(4);
            System.out.println(couponToPurchase4);
            Coupon couponToPurchase5 = companyFacade2.getSingleCoupon(8);
            System.out.println(couponToPurchase5);
            Coupon couponToPurchase6 = companyFacade2.getSingleCoupon(9);
            System.out.println(couponToPurchase6);
            Coupon couponToPurchase7 = companyFacade2.getSingleCoupon(10);
            System.out.println(couponToPurchase7);
            Coupon couponToPurchase8 = companyFacade3.getSingleCoupon(11);
            System.out.println(couponToPurchase8);
            Coupon couponToPurchase9 = companyFacade3.getSingleCoupon(12);
            System.out.println(couponToPurchase9);
            Coupon couponToPurchase10 = companyFacade3.getSingleCoupon(13);
            System.out.println(couponToPurchase10);
            customerFacade.addCoupon(couponToPurchase1);
            customerFacade.addCoupon(couponToPurchase2);
            customerFacade.addCoupon(couponToPurchase4);
            customerFacade.addCoupon(couponToPurchase6);
            customerFacade.addCoupon(couponToPurchase9);
            System.out.println("The coupons were added to customer \"" + customerFacade.getTheLoggedCustomerDetails().getFirstName() + " " + customerFacade.getTheLoggedCustomerDetails().getLastName() + " \":");
            System.out.println(customerFacade.getTheLoggedCustomerDetails());
            customerFacade2.addCoupon(couponToPurchase2);
            customerFacade2.addCoupon(couponToPurchase6);
            customerFacade2.addCoupon(couponToPurchase7);
            customerFacade2.addCoupon(couponToPurchase9);
            customerFacade2.addCoupon(couponToPurchase10);
            System.out.println("The coupons were added to customer \"" + customerFacade2.getTheLoggedCustomerDetails().getFirstName() + " " + customerFacade.getTheLoggedCustomerDetails().getLastName() + " \":");
            System.out.println(customerFacade2.getTheLoggedCustomerDetails());
            customerFacade3.addCoupon(couponToPurchase1);
            customerFacade3.addCoupon(couponToPurchase2);
            customerFacade3.addCoupon(couponToPurchase4);
            customerFacade3.addCoupon(couponToPurchase5);
            customerFacade3.addCoupon(couponToPurchase7);
            customerFacade3.addCoupon(couponToPurchase8);
            customerFacade3.addCoupon(couponToPurchase9);
            System.out.println("The coupons were added to customer \"" + customerFacade3.getTheLoggedCustomerDetails().getFirstName() + " " + customerFacade.getTheLoggedCustomerDetails().getLastName() + " \":");
            System.out.println(customerFacade3.getTheLoggedCustomerDetails());
        } catch (invalidCompanyException | invalidCustomerException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();


        System.out.println("---------- Attempt to add a coupon that is out of stock to CustomersVsCoupons ----------");
        System.out.println("Attempting to add coupons:");

        try {
            Coupon couponToPurchase11 = companyFacade.getSingleCoupon(5);
            customerFacade.addCoupon(couponToPurchase11);
            System.out.println("The coupon was added:");
            customerFacade.getAllCustomerCoupons().forEach(System.out::println);
        } catch (invalidCompanyException | invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("---------- Attempt to add a coupon that has been expired to CustomersVsCoupons ---------");
        System.out.println("Attempting to add coupons:");
        try {
            Coupon couponToPurchase12 = companyFacade.getSingleCoupon(6);
            Date reduceDate = DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(2));
            System.out.println("The update Date: " + DateUtils.beautifyDate(reduceDate));
            couponToPurchase12.setEndDate(reduceDate);
            System.out.println(couponToPurchase12);
            customerFacade.addCoupon(couponToPurchase12);
            System.out.println("The coupon was added:");
            customerFacade.getAllCustomerCoupons().forEach(System.out::println);
        } catch (invalidCompanyException | invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------ Attempt to add a coupon that already exist in CustomersVsCoupons ----------");
        System.out.println("Attempting to add coupon:");
        System.out.println(couponToPurchase1);
        try {
            if (couponToPurchase1 != null) {
                customerFacade.addCoupon(couponToPurchase1);
            }
            System.out.println("The coupon was added:");
            customerFacade.getAllCustomerCoupons().forEach(System.out::println);
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("----------------------- Get all coupons from a specific category -----------------------");
        try {
            if (couponToPurchase2 != null) {
                customerFacade.getAllCustomerCouponsOfSpecificCategory(couponToPurchase2.getCategory()).forEach(System.out::println);
            }
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------ Get all coupons up to the maximum price -----------------------");
        try {
            customerFacade.getAllCustomerCouponsUpToMaxPrice(80).forEach(System.out::println);
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("---------------------------- Get the logged customer details ---------------------------");
        try {
            System.out.println(customerFacade.getTheLoggedCustomerDetails());
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println(ArtUtils.DELETING_METHODS);

        System.out.println("--------------------------- Company attempt to delete a coupon -------------------------");
        Coupon couponToDelete = null;
        try {
            couponToDelete = companyFacade2.getSingleCoupon(9);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Attempt to delete the coupon: ");
        System.out.println(couponToDelete);
        System.out.println();

        System.out.println("The customers that purchase the coupon are: ");
        try {
            if (couponToDelete != null) {
                companyFacade2.getAllCompanyCustomersOfASingleCouponByCouponId(couponToDelete.getId()).forEach(System.out::println);
            }
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        try {
            if (couponToDelete != null) {
                companyFacade2.deleteCoupon(couponToDelete);
            }
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The coupon was deleted! ");
        System.out.println();

        try {
            System.out.println("Company coupons left :");
            companyFacade2.getAllCompanyCoupons().forEach(System.out::println);
        } catch (invalidCompanyException e) {
            e.printStackTrace();
        }
        System.out.println();

        System.out.println("The customer that purchase the coupon after deleting the coupon:");
        try {
            if (couponToDelete != null) {
                companyFacade2.getAllCompanyCustomersOfASingleCouponByCouponId(couponToDelete.getId()).forEach(System.out::println);
            }
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("------------------------------ Deleting an existing customer ---------------------------");
        Customer customerToDelete = adminFacade.getSingleCustomer(7);
        System.out.println("Attempting to delete customer: ");
        System.out.println(customerToDelete);
        System.out.println();

        try {
            adminFacade.deleteCustomer(customerToDelete);
            System.out.println("Customer deleted!");
            System.out.println("Customers left:");
            adminFacade.getAllCustomers().forEach(System.out::println);
        } catch (invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Is customer exist: ");
        try {
            adminFacade.getAllCustomersVsCoupons(customerToDelete.getId()).forEach(System.out::println);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------------ Deleting an existing company ----------------------------");
        Company companyToDelete = adminFacade.getSingleCompany(2);
        System.out.println("Attempting to delete company: ");
        System.out.println(companyToDelete);
        System.out.println();

        try {
            adminFacade.deleteCompany(companyToDelete);
            System.out.println("Company deleted!");
            System.out.println("Companies left:");
            adminFacade.getAllCompanies().forEach(System.out::println);
        } catch (invalidCustomerException | invalidAdminException e) {
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
