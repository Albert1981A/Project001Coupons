package com.AlbertAbuav.dbdao;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.dao.CompaniesDAO;
import com.AlbertAbuav.db.ConnectionPool;
import com.AlbertAbuav.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompaniesDBDAO implements CompaniesDAO {

    private static final String QUERY_IS_COMPANY_EXISTS = "SELECT * FROM `couponsystem`.`companies` WHERE `email`=? and `password`=?;";
    private static final String QUERY_INSERT_COMPANY = "INSERT INTO `couponsystem`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?);";
    private static final String QUERY_UPDATE_COMPANY = "UPDATE `couponsystem`.`companies` SET `name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
    private static final String QUERY_DELETE_COMPANY = "DELETE FROM `couponsystem`.`companies` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL_COMPANIES = "SELECT * FROM `couponsystem`.`companies`;";
    private static final String QUERY_GET_SINGLE_COMPANY = "SELECT * FROM `couponsystem`.`companies` WHERE (`id` = ?);";

    /**
     * To perform an operation from the code to the database there are five steps.
     * This methods use the steps below.
     * Step 2 - Taking a connection from the ConnectionPool class.
     * Step 3 - Preparing the instruction for the SQL end execute it.
     * Step 4 - ResultSet (Optional) - Get from the database results into "ResultSet".
     * Step 5 - Returning the connection to the ConnectionPool.
     */

    @Override
    public boolean isCompanyExists(String email, String password) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_IS_COMPANY_EXISTS, map);
        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_IS_COMPANY_EXISTS);
//            statement.setString(1, email);
//            statement.setString(2, password);
//            // Step 4
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()){
//                return true;
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
        return false;
    }

    @Override
    public void addCompany(Company company) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());
        DBUtils.runQuery(QUERY_INSERT_COMPANY, map);

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_COMPANY);
//            statement.setString(1, company.getName());
//            statement.setString(2, company.getEmail());
//            statement.setString(3, company.getPassword());
//            statement.execute();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
    }

    @Override
    public void updateCompany(Company company) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());
        map.put(4, company.getId());
        DBUtils.runQuery(QUERY_UPDATE_COMPANY, map);

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE_COMPANY);
//            statement.setString(1, company.getName());
//            statement.setString(2, company.getEmail());
//            statement.setString(3, company.getPassword());
//            statement.setInt(4, company.getId());
//            statement.execute();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
    }

    @Override
    public void deleteCompany(int companyID) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyID);
        DBUtils.runQuery(QUERY_DELETE_COMPANY, map);

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_COMPANY);
//            statement.setInt(1, companyID);
//            statement.execute();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_ALL_COMPANIES);
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                Company tmp = new Company(id, name, email, password);
                companies.add(tmp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_GET_ALL_COMPANIES);
//            // Step 4
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                int id = resultSet.getInt(1);
//                String name = resultSet.getString(2);
//                String email = resultSet.getString(3);
//                String password = resultSet.getString(4);
//                Company tmp = new Company(id, name, email, password);
//                companies.add(tmp);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
        return companies;
    }

    @Override
    public Company getSingleCompany(int companyID) {
        Company company = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyID);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_SINGLE_COMPANY, map);
        try {
            resultSet.next();
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String password = resultSet.getString(4);
            company = new Company(id, name, email, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_GET_SINGLE_COMPANY);
//            statement.setInt(1, companyID);
//            // Step 4
//            ResultSet resultSet = statement.executeQuery();
//            resultSet.next();
//            int id = resultSet.getInt(1);
//            String name = resultSet.getString(2);
//            String email = resultSet.getString(3);
//            String password = resultSet.getString(4);
//            company = new Company(id, name, email, password);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
        return company;
    }

    @Override
    public Company getCompanyByEmailAndPassword(String email, String password) {
        Company company = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_IS_COMPANY_EXISTS, map);
        try {
            resultSet.next();
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            company = new Company(id, name, email, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_IS_COMPANY_EXISTS);
//            statement.setString(1, email);
//            statement.setString(2, password);
//            // Step 4
//            ResultSet resultSet = statement.executeQuery();
//            resultSet.next();
//            int id = resultSet.getInt(1);
//            String name = resultSet.getString(2);
//            company = new Company(id, name, email, password);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
        return company;
    }
}
