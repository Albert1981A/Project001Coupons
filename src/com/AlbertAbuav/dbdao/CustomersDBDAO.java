package com.AlbertAbuav.dbdao;

import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.dao.CustomersDAO;

import java.util.List;

public class CustomersDBDAO implements CustomersDAO {

    private static final String QUERY_IS_CUSTOMER_EXISTS = "";
    private static final String QUERY_INSERT_CUSTOMER = "";
    private static final String QUERY_UPDATE_CUSTOMER = "";
    private static final String QUERY_DELETE_CUSTOMER = "";
    private static final String QUERY_GET_ALL_CUSTOMERS = "";
    private static final String QUERY_GET_SINGLE_CUSTOMER = "";

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
        return false;
    }

    @Override
    public void addCustomer(Customer customer) {

    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public void deleteCustomer(int customerID) {

    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public Customer getSingleCustomer(int customerID) {
        return null;
    }
}
