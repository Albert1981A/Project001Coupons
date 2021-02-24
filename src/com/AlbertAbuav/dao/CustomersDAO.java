package com.AlbertAbuav.dao;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.beans.Customer;

import java.util.List;

public interface CustomersDAO {

    boolean isCustomerExists(String email, String password);

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(int customerID);

    List<Customer> getAllCustomers();

    Customer getSingleCustomer(int customerID);

    Customer getCustomerByEmailAndPassword(String email, String password);

}
