package com.AlbertAbuav.dao;

import com.AlbertAbuav.beans.Customer;

import java.util.List;

public interface CustomersDAO {

    boolean isCustomerExists(String email, String password);

    boolean isCustomerExistsById(int id);

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(int customerID);

    List<Customer> getAllCustomers();

    Customer getSingleCustomer(int customerID);

    boolean isCustomerExistsByEmail(String email);

    Customer getSingleCustomerByEmailB(String email);

    Customer getCustomerByEmailAndPassword(String email, String password);

}
