package com.AlbertAbuav.dbdao;

import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.dao.CustomersDAO;
import com.AlbertAbuav.utils.Colors;
import com.AlbertAbuav.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDBDAO implements CustomersDAO {

    private static final String QUERY_IS_CUSTOMER_EXISTS = "SELECT * FROM `couponsystem`.`customers` WHERE EXISTS (SELECT * FROM `couponsystem`.`customers` WHERE `email`=? and `password`=?);";
    private static final String QUERY_IS_CUSTOMER_EXISTS_BY_ID = "SELECT * FROM `couponsystem`.`customers` WHERE EXISTS (SELECT * FROM `couponsystem`.`customers` WHERE (`id`= ?));";
    private static final String QUERY_GET_CUSTOMER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM `couponsystem`.`customers` WHERE `email`=? and `password`=?;";
    private static final String QUERY_GET_CUSTOMER_BY_EMAIL = "SELECT * FROM `couponsystem`.`customers` WHERE (`email`= ?);";
    private static final String QUERY_INSERT_CUSTOMER = "INSERT INTO `couponsystem`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?);";
    private static final String QUERY_UPDATE_CUSTOMER = "UPDATE `couponsystem`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
    private static final String QUERY_DELETE_CUSTOMER = "DELETE FROM `couponsystem`.`customers` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL_CUSTOMERS = "SELECT * FROM `couponsystem`.`customers`;";
    private static final String QUERY_GET_SINGLE_CUSTOMER = "SELECT * FROM `couponsystem`.`customers` WHERE (`id` = ?);";
    private static final String QUERY_GET_SINGLE_CUSTOMER_BY_EMAIL = "SELECT * FROM `couponsystem`.`customers` WHERE EXISTS (SELECT * FROM `couponsystem`.`customers` WHERE (`email` = ?));";

    /**
     * To perform an operation from the code to the database there are five steps.
     * This methods use the steps below.
     * Step 2 - Taking a connection from the ConnectionPool class.
     * Step 3 - Preparing the instruction for the SQL end execute it.
     * Step 4 - ResultSet (Optional) - Get from the database results into "ResultSet".
     * Step 5 - Returning the connection to the ConnectionPool.
     */

    @Override
    public boolean isCustomerExists(String email, String password) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_IS_CUSTOMER_EXISTS, map);
        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isCustomerExistsById(int id) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_IS_CUSTOMER_EXISTS_BY_ID, map);
        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return false;
    }

    @Override
    public void addCustomer(Customer customer) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customer.getFirstName());
        map.put(2, customer.getLastName());
        map.put(3, customer.getEmail());
        map.put(4, customer.getPassword());
        DBUtils.runQuery(QUERY_INSERT_CUSTOMER, map);
    }

    @Override
    public void updateCustomer(Customer customer) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customer.getFirstName());
        map.put(2, customer.getLastName());
        map.put(3, customer.getEmail());
        map.put(4, customer.getPassword());
        map.put(5, customer.getId());
        DBUtils.runQuery(QUERY_UPDATE_CUSTOMER, map);
    }

    @Override
    public void deleteCustomer(int customerID) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerID);
        DBUtils.runQuery(QUERY_DELETE_CUSTOMER, map);
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_ALL_CUSTOMERS);
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String firsName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);
                Customer tmp = new Customer(id, firsName, lastName, email, password);
                customers.add(tmp);
            }
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return customers;
    }

    @Override
    public Customer getSingleCustomer(int customerID) {
        Customer customer = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerID);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_SINGLE_CUSTOMER, map);
        try {
            resultSet.next();
            int id = resultSet.getInt(1);
            String firsName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);
            customer = new Customer(id, firsName, lastName, email, password);
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return customer;
    }

    @Override
    public boolean isCustomerExistsByEmail(String email) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_SINGLE_CUSTOMER_BY_EMAIL, map);
        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return false;
    }

    @Override
    public Customer getSingleCustomerByEmailB(String email) {
        Customer customer = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_CUSTOMER_BY_EMAIL, map);
        try {
            resultSet.next();
            int id = resultSet.getInt(1);
            String firsName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String password = resultSet.getString(5);
            customer = new Customer(id, firsName, lastName, email, password);
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return customer;
    }

    @Override
    public Customer getCustomerByEmailAndPassword(String email, String password) {
        Customer customer = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_CUSTOMER_BY_EMAIL_AND_PASSWORD, map);
        try {
            resultSet.next();
            int id = resultSet.getInt(1);
            String firsName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            customer = new Customer(id, firsName, lastName, email, password);
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return customer;
    }
}
