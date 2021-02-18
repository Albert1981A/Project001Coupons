package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.beans.Customer;

import java.util.List;

public class AdminFacade extends ClientFacade{

    /**
     * Login method, the method is implemented from the abstract class "ClientFacade"
     * @param email String
     * @param password String
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
     * @param company Company
     */
    public void addCompany(Company company) {
        companiesDAO.addCompany(company);
    }

    /**
     * Update an existing company.
     * The company id could not be updated.
     * The company name could not be updated.
     * @param company Company
     */
    public void updateCompany(Company company) {
        companiesDAO.updateCompany(company);
    }

    /**
     * Deleting an existing company.
     * The coupons created by the company must be deleted as well.
     * The purchase history of the company's coupons by customers must also be deleted.
     * @param company Company
     */
    public void deleteCompany(Company company) {
        companiesDAO.deleteCompany(company.getId());
    }

    /**
     * Get all Companies.
     * @return List
     */
    public List<Company> getAllCompanies() {
        return companiesDAO.getAllCompanies();
    }

    /**
     * Get a single Company by id.
     * @param id int
     * @return Company
     */
    public Company getSingleCompany(int id) {
        return companiesDAO.getSingleCompany(id);
    }

    /**
     * Adding a new customer.
     * It is not possible to add a customer with the same email to an existing customer.
     * @param customer Customer
     */
    public void addCustomer(Customer customer) {
        customersDAO.addCustomer(customer);
    }

    /**
     * Updating an existing customer.
     * Cannot update customer id.
     * @param customer Customer
     */
    public void updateCustomer(Customer customer) {
        customersDAO.updateCustomer(customer);
    }

    /**
     * Delete an existing customer.
     * The customer's purchase history should also be deleted.
     * @param customer Customer
     */
    public void deleteCustomer(Customer customer) {
        customersDAO.deleteCustomer(customer.getId());
    }

    /**
     * Get all customers.
     * @return List
     */
    public List<Customer> getAllCustomers() {
        return customersDAO.getAllCustomers();
    }

    /**
     * Get a single customers by id.
     * @param id int
     * @return Customer
     */
    public Customer getSingleCustomer(int id) {
        return customersDAO.getSingleCustomer(id);
    }

}
