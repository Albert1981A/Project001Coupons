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
import com.AlbertAbuav.facades.AdminFacade;
import com.AlbertAbuav.facades.CompanyFacade;
import com.AlbertAbuav.login.ClientType;
import com.AlbertAbuav.login.LoginManager;
import com.AlbertAbuav.utils.ArtUtils;

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
        adminFacade.getAllCompanies().forEach(System.out::println);
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
        adminFacade.getAllCustomers().forEach(System.out::println);
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
        System.out.println("----------------------------- Checking Connection to Company ---------------------------");
        System.out.println(companyFacade);
        System.out.println();

        System.out.println("---------------------------- Adding new coupon to the company --------------------------");
        System.out.println("Trying to get all companies coupons when their is no coupons in the system:");
        try {
            companyFacade.getAllCompanyCoupons().forEach(System.out::println);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Trying to add coupon: ");
        Coupon couponToAdd1 = new Coupon(loggedCompany.getId());
        System.out.println(couponToAdd1);
        Coupon couponToAdd2 = new Coupon(loggedCompany.getId());
        System.out.println(couponToAdd2);
        Coupon couponToAdd3 = new Coupon(loggedCompany.getId());
        System.out.println(couponToAdd3);
        Coupon couponToAdd4 = new Coupon(loggedCompany.getId());
        System.out.println(couponToAdd4);
        System.out.println();

        try {
            companyFacade.addCoupon(couponToAdd1);
            companyFacade.addCoupon(couponToAdd2);
            companyFacade.addCoupon(couponToAdd3);
            companyFacade.addCoupon(couponToAdd4);
            System.out.println("Coupons added!\nGet all companies coupons:");
            companyFacade.getAllCompanyCoupons().forEach(System.out::println);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Get the logged company details:");
        try {
            System.out.println(companyFacade.getTheLoggedCompanyDetails());
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }

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


        System.out.println(ArtUtils.CUSTOMERS_FACADE);

        /**
         * closing all connections.
         */
        ConnectionPool.getInstance().closeAllConnections();

        System.out.println("End");
    }
}
