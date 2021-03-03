package com.AlbertAbuav.dbdao;

import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.dao.CustomersDAO;
import com.AlbertAbuav.db.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersDBDAO implements CustomersDAO {

    private static final String QUERY_IS_CUSTOMER_EXISTS = "SELECT * FROM `couponsystem`.`customers` WHERE `email`=? and `password`=?;";
    private static final String QUERY_INSERT_CUSTOMER = "INSERT INTO `couponsystem`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?);";
    private static final String QUERY_UPDATE_CUSTOMER = "UPDATE `couponsystem`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
    private static final String QUERY_DELETE_CUSTOMER = "DELETE FROM `couponsystem`.`customers` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL_CUSTOMERS = "SELECT * FROM `couponsystem`.`customers`;";
    private static final String QUERY_GET_SINGLE_CUSTOMER = "SELECT * FROM `couponsystem`.`customers` WHERE (`id` = ?);";

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
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_IS_CUSTOMER_EXISTS);
            statement.setString(1, email);
            statement.setString(2, password);
            // Step 4
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    @Override
    public void addCustomer(Customer customer) {
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.setInt(5, customer.getId());
            statement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void deleteCustomer(int customerID) {
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_CUSTOMER);
            statement.setInt(1, customerID);
            statement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_ALL_CUSTOMERS);
            // Step 4
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String firsName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);
                Customer tmp = new Customer(id, firsName, lastName, email, password);
                customers.add(tmp);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return customers;
    }

    @Override
    public Customer getSingleCustomer(int customerID) {
        Customer customer = null;
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_SINGLE_CUSTOMER);
            statement.setInt(1, customerID);
            // Step 4
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            String firsName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);
            customer = new Customer(id, firsName, lastName, email, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return customer;
    }

    @Override
    public Customer getCustomerByEmailAndPassword(String email, String password) {
        Customer customer = null;
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_IS_CUSTOMER_EXISTS);
            statement.setString(1, email);
            statement.setString(2, password);
            // Step 4
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            String firsName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            customer = new Customer(id, firsName, lastName, email, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return customer;
    }
}
