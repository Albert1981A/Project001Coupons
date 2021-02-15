package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.beans.Customer;

import java.util.List;

public class AdminFacade extends ClientFacade{

    /**
     * Login method, the method is implemented from the abstract class "ClientFacade"
     * @param email
     * @param password
     * @return boolean
     */
    @Override
    public boolean login(String email, String password) {
        return (email.equals("admin@admin.com") && password.equals("admin"));
    }

    /**
     * Adding a new company.
     * It is not possible to add a company with the same name to an existing company.
     * It is not possible to add a company with the same email to an existing company.
     */
    public void addCompany(Company company) {

    }

    /**
     * Update an existing company.
     * The company id could not be updated.
     * The company name could not be updated.
     */
    public void updateCompany(Company company) {

    }

    /**
     * Deleting an existing company.
     * The coupons created by the company must be deleted as well.
     * The purchase history of the company's coupons by customers must also be deleted.
     */
    public void deleteCompany(Company company) {

    }

    /**
     * Get all Companies.
     */
    public List<Company> getAllCompanies() {
        return null;
    }

    /**
     * Get a single Company by id.
     */
    public Company getSingleCompany(int id) {
        return null;
    }

    /**
     * Adding a new customer.
     * It is not possible to add a customer with the same email to an existing customer.
     */
    public void addCustomer(Customer customer) {

    }

    /**
     * Updating an existing customer.
     * Cannot update customer id.
     */
    public void updateCustomer(Customer customer) {

    }

    /**
     * Delete an existing customer.
     * The customer's purchase history should also be deleted.
     */
    public void deleteCustomer(Customer customer) {

    }

    /**
     * Get all customers.
     */
    public List<Customer> getAllCustomers() {
        return null;
    }

    /**
     * Get a single customers by id.
     */
    public Customer getSingleCustomer(int id) {
        return null;
    }

}
