package com.AlbertAbuav.testCategories;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.dao.CompaniesDAO;
import com.AlbertAbuav.db.ConnectionPool;
import com.AlbertAbuav.db.DatabaseManager;
import com.AlbertAbuav.dbdao.CompaniesDBDAO;
import com.AlbertAbuav.utils.ArtUtils;

public class TestCompanies {
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

        System.out.println(ArtUtils.COMPANIES_DAO);
        System.out.println();

        /**
         * Interface equals to implementation.
         */
        CompaniesDAO companiesDAO = new CompaniesDBDAO();

        /**
         * using the CompaniesDBDAO query's.
         */

        System.out.println("-------------------------- QUERY ADD COMPANY ---------------------------");
        Company c1 = new Company();
        companiesDAO.addCompany(c1);
        Company c2 = new Company();
        companiesDAO.addCompany(c2);
        Company c3 = new Company();
        companiesDAO.addCompany(c3);
        Company c4 = new Company();
        companiesDAO.addCompany(c4);
        Company c5 = new Company();
        companiesDAO.addCompany(c5);
        System.out.println();

        System.out.println("------------------------ QUERY GET ALL COMPANY -------------------------");
        companiesDAO.getAllCompanies().forEach(System.out::println);
        System.out.println();

        System.out.println("------------------------- QUERY UPDATE COMPANIES --------------------------");
        Company toUpdate = companiesDAO.getSingleCompany(1);
        toUpdate.setName("The first Company");
        companiesDAO.updateCompany(toUpdate);
        companiesDAO.getAllCompanies().forEach(System.out::println);
        System.out.println();

        System.out.println("----------------------- QUERY GET SINGLE COMPANY ------------------------");
        System.out.println(companiesDAO.getSingleCompany(3));
        System.out.println();

        System.out.println("------------------------- QUERY DELETE COMPANY --------------------------");
        Company toDelete = companiesDAO.getSingleCompany(5);
        companiesDAO.deleteCompany(toDelete.getId());
        companiesDAO.getAllCompanies().forEach(System.out::println);
        System.out.println();

        System.out.println("----------------------- QUERY IS COMPANY EXISTS -------------------------");
        Company C1 = companiesDAO.getSingleCompany(4);
        System.out.println("Company number 4 exists: " + companiesDAO.isCompanyExists(C1.getEmail(),C1.getPassword()));
        System.out.println();

        System.out.println("------------------ GET COMPANY BY EMAIL AND PASSWORD --------------------");
        Company C2 = companiesDAO.getSingleCompany(3);
        System.out.println("Company number 3 exists: " + companiesDAO.getCompanyByEmailAndPassword(C2.getEmail(),C2.getPassword()));
        System.out.println();

        /**
         * closing all connections.
         */
        ConnectionPool.getInstance().closeAllConnections();
        System.out.println("END");
    }
}
