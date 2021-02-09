package com.AlbertAbuav;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.dao.CategoriesDAO;
import com.AlbertAbuav.dao.CompaniesDAO;
import com.AlbertAbuav.dao.CouponsDAO;
import com.AlbertAbuav.dao.CustomersDAO;
import com.AlbertAbuav.db.ConnectionPool;
import com.AlbertAbuav.db.DatabaseManager;
import com.AlbertAbuav.dbdao.CategoriesDBDAO;
import com.AlbertAbuav.dbdao.CompaniesDBDAO;
import com.AlbertAbuav.dbdao.CouponsDBDAO;
import com.AlbertAbuav.dbdao.CustomersDBDAO;
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
        categoriesDAO.getAllCategories();
        System.out.println();

        System.out.println(ArtUtils.COMPANIES_DAO);
        System.out.println();

        CompaniesDAO companiesDAO = new CompaniesDBDAO();

        System.out.println("-------------------------- CREATE COMPANIES ---------------------------");
        Company c1 = new Company();
        Company c2 = new Company();
        System.out.println(c1);
        System.out.println(c2);
        System.out.println();

        System.out.println("-------------------------- QUERY ADD COMPANY ---------------------------");
        companiesDAO.addCompany(c1);
        companiesDAO.addCompany(c2);
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
        System.out.println();
        System.out.println(DateUtils.convertJavaDateToSqlDate(coupon1.getStartDate()));
        System.out.println(DateUtils.convertJavaDateToSqlDate(coupon2.getStartDate()));

        System.out.println("-------------------------- QUERY ADD COUPON ----------------------------");
        couponsDAO.addCoupon(coupon1);
        couponsDAO.addCoupon(coupon2);
        System.out.println();

        System.out.println("------------------------ QUERY GET ALL COUPONS -------------------------");
        couponsDAO.getAllCoupons().forEach(System.out::println);

        /**
         * closing all connections.
         */
        ConnectionPool.getInstance().closeAllConnections();
        System.out.println("END");
    }
}
