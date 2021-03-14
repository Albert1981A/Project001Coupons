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
import com.AlbertAbuav.tasks.DailyExpiredCoupons;
import com.AlbertAbuav.utils.ArtUtils;
import com.AlbertAbuav.utils.DateUtils;

import java.time.LocalDate;
import java.util.Date;

public class TestFacade {

    public static void testAll() {

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

        System.out.println("----------------------- QUERY GET ALL CATEGORIES ------------------------");
        CategoriesDAO categoriesDAO = new CategoriesDBDAO();
        categoriesDAO.addCategories();
        System.out.println("Categories were added!");
        System.out.println();

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

        AdminFacade adminFacade = null;

        System.out.println("------------------ Attempting to Connect to Admin with false parameters ----------------");
        try {
            adminFacade = (AdminFacade) loginManager.login("false@false.com", "false", ClientType.ADMINISTRATOR);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------------ Checking Connection to Admin ----------------------------");
        try {
            adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(adminFacade);
        System.out.println();

        System.out.println("---------- Attempt to get all companies when there is no companies in system -----------");
        try {
            adminFacade.getAllCompanies().forEach(System.out::println);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------- Attempt to get a single company when there is no companies in system ---------");
        try {
            adminFacade.getSingleCompany(1);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("---------------------------------- Adding new companies --------------------------------");

        try {
            for (int i = 0; i < 10; i++) {
                adminFacade.addCompany(new Company());
            }
            adminFacade.getAllCompanies().forEach(System.out::println);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("--------------------- Attempt to add a company with an existing name -------------------");
        Company temp = null;
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
            String nameB = adminFacade.getSingleCompany(5).getName();
            System.out.println("Attempt to update the name to: \"" + nameB + "\" witch is an existing name!");
            companyToUpdate3.setName(nameB);
            adminFacade.updateCompany(companyToUpdate3);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------------------------ Attempt to update a Company's Name and id ---------------------");

        try {
            Company companyToUpdate3b = adminFacade.getSingleCompany(4);
            System.out.println("Updating Company: \n" + companyToUpdate3b);
            String nameC = adminFacade.getSingleCompany(5).getName();
            System.out.println("Attempt to update the name to: \"" + nameC + "\" witch is an existing name!");
            companyToUpdate3b.setName(nameC);
            int idC = 3;
            System.out.println("Attempt to update the id to: \"" + idC + "\" witch is an existing id!");
            adminFacade.updateCompany(companyToUpdate3b);
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

        System.out.println("---------- Attempt to get all customers when there is no customers in system -----------");
        try {
            adminFacade.getAllCustomers().forEach(System.out::println);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("------- Attempt to get a single customers when there is no customers in system ---------");
        try {
            System.out.println(adminFacade.getSingleCustomer(1));
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

        System.out.println("------------------------------- Update a customers first name --------------------------");
        Customer toUpdate = null;
        try {
            toUpdate = adminFacade.getSingleCustomer(1);
            System.out.println("Update the first name of the customer: \n" + toUpdate);
            System.out.println("Customer after the update:");
            toUpdate.setFirstName("Laura");
            adminFacade.updateCustomer(toUpdate);
            System.out.println(adminFacade.getSingleCustomer(1));
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

        System.out.println("------------------------- Attempt to update a customer email and id --------------------");

        try {
            Customer toUpdate1 = adminFacade.getSingleCustomer(2);
            System.out.println("Update email and id of customer: \n" + toUpdate1);
            toUpdate1.setEmail("email@email.com");
            toUpdate1.setId(3);
            System.out.println("This are the changes that will be attempted to update: \n" + toUpdate1);
            adminFacade.updateCustomer(toUpdate1);
            System.out.println("The system will choose to change the object according to its - id!\nThis is the customer after the update:");
            System.out.println(adminFacade.getSingleCustomer(3));
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
            System.out.println("Login with the companies:");
            System.out.println(loggedCompany1 + "\n" + loggedCompany2 + "\n" + loggedCompany3 + "\n");
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        CompanyFacade companyFacade1 = null;
        CompanyFacade companyFacade2 = null;
        CompanyFacade companyFacade3 = null;

        System.out.println("---------------- Attempting to Connect to a company with false parameters --------------");
        try {
            companyFacade1 = (CompanyFacade) loginManager.login("false@false.com", "false", ClientType.COMPANY);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("----------------------------- Checking Connection to Company ---------------------------");
        try {
            companyFacade1 = (CompanyFacade) loginManager.login(loggedCompany1.getEmail(), loggedCompany1.getPassword(), ClientType.COMPANY);
            companyFacade2 = (CompanyFacade) loginManager.login(loggedCompany2.getEmail(), loggedCompany2.getPassword(), ClientType.COMPANY);
            companyFacade3 = (CompanyFacade) loginManager.login(loggedCompany3.getEmail(), loggedCompany3.getPassword(), ClientType.COMPANY);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println(companyFacade1);
        System.out.println(companyFacade2);
        System.out.println(companyFacade3);
        System.out.println();

        System.out.println("------ Trying to get all companies coupons when there is no coupons in the system ------");
        try {
            companyFacade1.getAllCompanyCoupons().forEach(System.out::println);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("---------------------------- Adding new coupon to the company --------------------------");

        System.out.println("Coupons were added:");
        System.out.println("-------------------");

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

        System.out.println("---------------------------- Update a company coupon start date ------------------------");
        try {
            Coupon couponToUpdateD = companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(1);
            System.out.println("Trying to update the \"startDate\" of coupon id-2 to be \"current date\": ");
            System.out.println(couponToUpdateD);
            couponToUpdateD.setStartDate(DateUtils.addOneDayToUtilDate(DateUtils.javaDateFromLocalDate(LocalDate.now())));
            companyFacade1.updateCoupon(couponToUpdateD);
            System.out.println(companyFacade1.getTheLoggedCompanyDetails().getCoupons().get(1));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("---------------------------- Update a company coupon amount ------------------------");
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
        CustomerFacade customerFacade1 = null;
        CustomerFacade customerFacade2 = null;
        CustomerFacade customerFacade3 = null;

        System.out.println("---------------- Attempting to Connect to a customer with false parameters --------------");
        try {
            customerFacade1 = (CustomerFacade) loginManager.login("false@false.com", "false", ClientType.CUSTOMER);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("----------------------------- Checking Connection to Customer --------------------------");
        try {
            customerFacade1 = (CustomerFacade) loginManager.login(loggedCustomer1.getEmail(), loggedCustomer1.getPassword(), ClientType.CUSTOMER);
            customerFacade2 = (CustomerFacade) loginManager.login(loggedCustomer2.getEmail(), loggedCustomer2.getPassword(), ClientType.CUSTOMER);
            customerFacade3 = (CustomerFacade) loginManager.login(loggedCustomer3.getEmail(), loggedCustomer3.getPassword(), ClientType.CUSTOMER);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

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


        /**
         * Checking deleting methods of the different facades.
         */
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

        System.out.println("-------------- Getting all customers coupons of a customer with no coupons -------------");
        try {
            adminFacade.getAllCustomersVsCoupons((adminFacade.getSingleCustomer(9)).getId()).forEach(System.out::println);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("---------------------- Deleting an existing customer with no coupons -------------------");
        try {
            Customer customerToDelete2 = adminFacade.getSingleCustomer(9);
            System.out.println("Attempting to delete customer: ");
            System.out.println(customerToDelete2);
            adminFacade.deleteCustomer(customerToDelete2);
            System.out.println("Customer deleted!");
            System.out.println("Customers left:");
            adminFacade.getAllCustomers().forEach(System.out::println);
            System.out.println("Is customer exist: ");
            adminFacade.getAllCustomersVsCoupons(customerToDelete2.getId()).forEach(System.out::println);
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
         * Checking the Thread of the daily expired coupons.
         */
        System.out.println(ArtUtils.CHECK_THREAD);

        System.out.println("------------------------ Entering coupons with an expired date -------------------------");
        System.out.println();

        try {
            for (int i = 0; i < 3; i++) {
                Coupon temp2 = new Coupon();
                temp2.setCompanyID(4);
                companyFacade1.addCoupon(temp2);
                Coupon expiredCoupon1 = companyFacade1.getSingleCoupon(i + 14);
                customerFacade1.addCoupon(expiredCoupon1);
                expiredCoupon1.setStartDate(DateUtils.addOneDayToUtilDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(19))));
                expiredCoupon1.setEndDate(DateUtils.addOneDayToUtilDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(12))));
                companyFacade1.updateCoupon(expiredCoupon1);
                System.out.println("Expired coupons that was added:");
                System.out.println(companyFacade1.getSingleCoupon(expiredCoupon1.getId()));
            }
        } catch (invalidCustomerException | invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        try {
            System.out.println("All coupons of company id-4:");
            companyFacade1.getAllCompanyCoupons().forEach(System.out::println);
            System.out.println("All customer id-4 details:");
            System.out.println(customerFacade1.getTheLoggedCustomerDetails());
        } catch (invalidCompanyException | invalidCustomerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();


        System.out.println("-------------------- Checking the Thread of the daily expired coupons ------------------");

        /**
         * The thread will run for 200 millis on the test.
         * Will be set for 24 hours on the live program.
         */
        DailyExpiredCoupons dailyExpiredCoupons = new DailyExpiredCoupons();
        new Thread(dailyExpiredCoupons, "DailyExpiredCoupons1").start();

        /**
         * Stopping DailyExpiredCoupons thread.
         */
        System.out.println(Thread.currentThread().getName() + " is stopping DailyExpiredCoupons thread");
        dailyExpiredCoupons.stop();

        /**
         * Checking if DailyExpiredCoupons thread stopped.
         */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(Thread.currentThread().getName() + " is finished now");
        System.out.println();

        /**
         * closing all connections.
         */
        ConnectionPool.getInstance().closeAllConnections();

        System.out.println("End");
    }
}
