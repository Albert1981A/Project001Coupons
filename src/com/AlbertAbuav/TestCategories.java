package com.AlbertAbuav;

import com.AlbertAbuav.dao.CategoriesDAO;
import com.AlbertAbuav.db.ConnectionPool;
import com.AlbertAbuav.db.DatabaseManager;
import com.AlbertAbuav.dbdao.CategoriesDBDAO;
import com.AlbertAbuav.utils.ArtUtils;

public class TestCategories {

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

        /**
         * Interface equals to implementation.
         */
        CategoriesDAO categoriesDAO = new CategoriesDBDAO();

        /**
         * using the CompaniesDBDAO query's.
         */

        categoriesDAO.addCategories();
        categoriesDAO.getAllCategories().forEach(System.out::println);
        System.out.println();

        /**
         * closing all connections.
         */
        ConnectionPool.getInstance().closeAllConnections();
        System.out.println("END");
    }
}
