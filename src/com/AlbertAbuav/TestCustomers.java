package com.AlbertAbuav;

import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.dao.CustomersDAO;
import com.AlbertAbuav.db.ConnectionPool;
import com.AlbertAbuav.db.DatabaseManager;
import com.AlbertAbuav.dbdao.CustomersDBDAO;
import com.AlbertAbuav.utils.ArtUtils;

public class TestCustomers {

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

        System.out.println(ArtUtils.CUSTOMERS_DAO);
        System.out.println();

        /**
         * Interface equals to implementation.
         */
        CustomersDAO customersDAO = new CustomersDBDAO();

        /**
         * using the CustomersDBDAO query's.
         */
        System.out.println("-------------------------- QUERY ADD CUSTOMER ---------------------------");
        Customer c1 = new Customer();
        Customer c2 = new Customer();
        Customer c3 = new Customer();
        Customer c4 = new Customer();
        Customer c5 = new Customer();

        customersDAO.addCustomer(c1);
        customersDAO.addCustomer(c2);
        customersDAO.addCustomer(c3);
        customersDAO.addCustomer(c4);
        customersDAO.addCustomer(c5);
        System.out.println();

        System.out.println("------------------------ QUERY GET ALL CUSTOMERS -------------------------");
        customersDAO.getAllCustomers().forEach(System.out::println);
        System.out.println();

        System.out.println("------------------------- QUERY UPDATE CUSTOMER --------------------------");
        Customer toUpdate = customersDAO.getSingleCustomer(1);
        toUpdate.setFirstName("The first Customer");
        customersDAO.updateCustomer(toUpdate);
        customersDAO.getAllCustomers().forEach(System.out::println);
        System.out.println();

        System.out.println("----------------------- QUERY GET SINGLE CUSTOMER ------------------------");
        System.out.println(customersDAO.getSingleCustomer(3));
        System.out.println();

        System.out.println("------------------------- QUERY DELETE CUSTOMER --------------------------");
        Customer toDelete = customersDAO.getSingleCustomer(5);
        customersDAO.deleteCustomer(toDelete.getId());
        customersDAO.getAllCustomers().forEach(System.out::println);
        System.out.println();

        System.out.println("----------------------- QUERY IS CUSTOMER EXISTS -------------------------");
        System.out.println("Customer number 4 exists: " + customersDAO.isCustomerExists("Email: 4", "Password: 4"));
        System.out.println();

        /**
         * closing all connections.
         */
        ConnectionPool.getInstance().closeAllConnections();
        System.out.println("END");
    }
}
