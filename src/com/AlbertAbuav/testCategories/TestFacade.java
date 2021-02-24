package com.AlbertAbuav.testCategories;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.dao.CategoriesDAO;
import com.AlbertAbuav.db.ConnectionPool;
import com.AlbertAbuav.db.DatabaseManager;
import com.AlbertAbuav.dbdao.CategoriesDBDAO;
import com.AlbertAbuav.exceptions.invalidAdminException;
import com.AlbertAbuav.facades.AdminFacade;
import com.AlbertAbuav.facades.ClientFacade;
import com.AlbertAbuav.login.ClientType;
import com.AlbertAbuav.login.LoginManager;
import com.AlbertAbuav.utils.ArtUtils;
import com.AlbertAbuav.utils.FactoryUtils;

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
        System.out.println("Updating Company: " + companyToUpdate);
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
        System.out.println("Updating Company: " + companyToUpdate2);
        companyToUpdate2.setId(2);
        try {
            adminFacade.updateCompany(companyToUpdate2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("----------------------------- Attempt to update a Company's Name -------------------------");
        Company companyToUpdate3 = adminFacade.getSingleCompany(4);
        System.out.println("Updating Company: " + companyToUpdate3);
        companyToUpdate3.setName(adminFacade.getSingleCompany(5).getName());
        try {
            adminFacade.updateCompany(companyToUpdate3);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------- Attempt to update a company whose name does not exist in the system ----------");
        Company companyToUpdate4 = adminFacade.getSingleCompany(5);
        System.out.println("Updating Company: " + companyToUpdate4);
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
        System.out.println("Update First name of customer: " + toUpdate);
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
        System.out.println("Update email of customer: " + toUpdate1);
        toUpdate1.setEmail("email@email.com");
        toUpdate1.setId(3);
        System.out.println(toUpdate1);
        try {
            adminFacade.updateCustomer(toUpdate1);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(adminFacade.getSingleCustomer(3));
        System.out.println();

        System.out.println("------------------------------ Attempt to update a customer id -------------------------");
        Customer toUpdate2 = adminFacade.getSingleCustomer(4);
        System.out.println("Update id of customer: " + toUpdate2);
        toUpdate2.setId(2);
        try {
            adminFacade.updateCustomer(toUpdate2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(adminFacade.getSingleCustomer(4));
        System.out.println();

        System.out.println(ArtUtils.COMPANIES_FACADE);
        System.out.println(ArtUtils.CUSTOMERS_FACADE);

        /**
         * closing all connections.
         */
        ConnectionPool.getInstance().closeAllConnections();

        System.out.println("End");
    }
}
