package com.AlbertAbuav.testCategories;

import com.AlbertAbuav.beans.*;
import com.AlbertAbuav.dao.*;
import com.AlbertAbuav.db.ConnectionPool;
import com.AlbertAbuav.db.DatabaseManager;
import com.AlbertAbuav.dbdao.*;
import com.AlbertAbuav.utils.ArtUtils;
import com.AlbertAbuav.utils.DateUtils;

public class TestCoupons {

    public static void main(String[] args) {
        /**
         * JDBC - Java Database Connectivity
         * Their are five steps to perform an operation from the code to the database
         * 1. Setup to MySql Driver
         * 2. Open connection to Database
         * 3. Prepared Statement (fox example: select * from... / insert into...)
         * 4. ResultSet (Optional) - Get from DB results into ResultSet
         * 5. Close connection to Database
         */

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

        System.out.println(ArtUtils.COMPANIES_DAO);
        System.out.println();

        CompaniesDAO companiesDAO = new CompaniesDBDAO();

        System.out.println("-------------------------- QUERY ADD COMPANY ---------------------------");
        Company c1 = new Company();
        companiesDAO.addCompany(c1);
        System.out.println(c1);
        Company c2 = new Company();
        companiesDAO.addCompany(c2);
        System.out.println(c2);
        System.out.println();

        System.out.println("----------------------- QUERY GET ALL COMPANIES ------------------------");
        companiesDAO.getAllCompanies().forEach(System.out::println);
        System.out.println();

        System.out.println(ArtUtils.CUSTOMERS_DAO);
        System.out.println();

        CustomersDAO customersDAO = new CustomersDBDAO();

        System.out.println("-------------------------- CREATE CUSTOMERS ---------------------------");
        Customer cu1 = new Customer();
        Customer cu2 = new Customer();
        System.out.println(cu1);
        System.out.println(cu2);
        System.out.println();

        System.out.println("-------------------------- QUERY ADD CUSTOMER ---------------------------");
        customersDAO.addCustomer(cu1);
        customersDAO.addCustomer(cu2);
        System.out.println();
        System.out.println("----------------------- QUERY GET ALL CUSTOMERS ------------------------");
        customersDAO.getAllCustomers().forEach(System.out::println);
        System.out.println();

        System.out.println(ArtUtils.COUPONS_DAO);
        System.out.println();

        CouponsDAO couponsDAO = new CouponsDBDAO();

        System.out.println("--------------------------- CREATE COUPONS ----------------------------");
        Coupon coupon1 = new Coupon();
        Coupon coupon2 = new Coupon();
        System.out.println(coupon1);
        System.out.println(coupon2);
        System.out.println("Checking Date SQL before entering the DB: " + DateUtils.convertJavaDateToSqlDate(coupon1.getStartDate()));
        System.out.println("Checking Date SQL before entering the DB: " + DateUtils.convertJavaDateToSqlDate(coupon2.getStartDate()));
        System.out.println();

        System.out.println("-------------------------- QUERY ADD COUPON ----------------------------");
        couponsDAO.addCoupon(coupon1);
        couponsDAO.addCoupon(coupon2);
        System.out.println();

        System.out.println("------------------------ QUERY GET ALL COUPONS -------------------------");
        couponsDAO.getAllCoupons().forEach(System.out::println);

        System.out.println("------------------------- QUERY UPDATE COUPON --------------------------");
        Coupon toUpdate = couponsDAO.getSingleCoupon(1);
        toUpdate.setCategory(Category.VACATIONS_IN_ISRAEL);
        couponsDAO.updateCoupon(toUpdate);
        couponsDAO.getAllCoupons().forEach(System.out::println);
        System.out.println();

        System.out.println("----------------------- QUERY GET SINGLE COUPON ------------------------");
        System.out.println(couponsDAO.getSingleCoupon(1));
        System.out.println();

        System.out.println("------------------------- QUERY DELETE COUPON --------------------------");
        Coupon toDelete = couponsDAO.getSingleCoupon(2);
        System.out.println("Deleting Coupon: " + toDelete);
        couponsDAO.deleteCoupon(toDelete.getId());
        System.out.println("Coupons Left: ");
        couponsDAO.getAllCoupons().forEach(System.out::println);
        System.out.println();

        System.out.println("---------------------- QUERY_ADD_COUPON_PURCHASE ------------------------");
        couponsDAO.addCouponPurchase(1, 1);
        System.out.println("Purchase a coupon by customer: " + customersDAO.getSingleCustomer(1));
        System.out.println("He Purchase coupon: " + couponsDAO.getSingleCoupon(1));
        System.out.println();

        System.out.println("---------------- QUERY_GET_ALL_CUSTOMER_COUPON_PURCHASE------------------");
        couponsDAO.getAllCustomersCoupons(1).forEach(System.out::println);
        System.out.println();

        System.out.println("--------------------- QUERY_DELETE_COUPON_PURCHASE-----------------------");
        couponsDAO.deleteCouponPurchase(1, 1);
        System.out.println("Deleted coupon by customer: " + customersDAO.getSingleCustomer(1));
        System.out.println("He Deleted coupon: " + couponsDAO.getSingleCoupon(1));
        System.out.println();

        System.out.println("---------------- QUERY_GET_ALL_CUSTOMER_COUPON_PURCHASE------------------");
        couponsDAO.getAllCustomersCoupons(1).forEach(System.out::println);
        System.out.println();


        /**
         * closing all connections.
         */
        ConnectionPool.getInstance().closeAllConnections();
        System.out.println("END");
    }
}
