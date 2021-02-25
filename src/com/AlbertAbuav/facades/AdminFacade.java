package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.exceptions.invalidAdminException;

import java.util.List;

public class AdminFacade extends ClientFacade {

    public AdminFacade() {
    }

    /**
     * Login method, the method is implemented from the abstract class "ClientFacade"
     *
     * @param email    String
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
     *
     * @param company Company
     */
    public void addCompany(Company company) throws invalidAdminException {
        List<Company> companyList = companiesDAO.getAllCompanies();
        if (companyList.size() == 0) {
            companiesDAO.addCompany(company);
            return;
        }
        for (Company c : companyList) {
            if (company.getName().equals(c.getName())) {
                throw new invalidAdminException("The name of the company you are trying to add already appears in the system.\nCompanies with the same name cannot be added.");
            } else if (company.getEmail().equals(c.getEmail())) {
                throw new invalidAdminException("The email of the company you are trying to add already appears in the system.\nCompanies with the same email cannot be added.");
            }
        }
        companiesDAO.addCompany(company);
    }

    /**
     * Update an existing company.
     * The company id could not be updated.
     * The company name could not be updated.
     *
     * @param company Company
     */
    public void updateCompany(Company company) throws invalidAdminException {
        List<Company> companyList = companiesDAO.getAllCompanies();
        Company toCompare = null;
        for (Company company1 : companyList) {
            if (company.getName().equals(company1.getName())) {
                toCompare = company1;

            }
        }
        if (toCompare == null) {
            throw new invalidAdminException("No company matching the name: \"" + company.getName() + "\", was found:");
        } else if (company.getId() != toCompare.getId()) {
            throw new invalidAdminException("The company id or name cannot be updated");
        }
        companiesDAO.updateCompany(company);
    }

    /**
     * Deleting an existing company.
     * The coupons created by the company must be deleted as well.
     * The purchase history of the company's coupons by customers must also be deleted.
     *
     * @param company Company
     */
    public void deleteCompany(Company company) {
        companiesDAO.deleteCompany(company.getId());
        //TODO - method delete a Company
    }

    /**
     * Get all Companies.
     *
     * @return List
     */
    public List<Company> getAllCompanies() {
        return companiesDAO.getAllCompanies();
    }

    /**
     * Get a single Company by id.
     *
     * @param id int
     * @return Company
     */
    public Company getSingleCompany(int id) {
        return companiesDAO.getSingleCompany(id);
    }

    /**
     * Adding a new customer.
     * It is not possible to add a customer with the same email to an existing customer.
     *
     * @param customer Customer
     */
    public void addCustomer(Customer customer) throws invalidAdminException {
        List<Customer> customerList = customersDAO.getAllCustomers();
        if (customerList.size() == 0) {
            customersDAO.addCustomer(customer);
            return;
        }
        for (Customer customer1 : customerList) {
            if (customer.getEmail().equals(customer1.getEmail())) {
                throw new invalidAdminException("The email of the customer you are trying to add already appears in the system.\nCustomers with the same email cannot be added.");
            }
        }
        customersDAO.addCustomer(customer);

    }

    /**
     * Updating an existing customer.
     * Cannot update customer id.
     *
     * @param customer Customer
     */
    public void updateCustomer(Customer customer) throws invalidAdminException {
        List<Customer> customerList = customersDAO.getAllCustomers();
        Customer toCompare = null;
        for (Customer customer1 : customerList) {
            if (customer.getEmail().equals(customer1.getEmail())) {
                toCompare = customer1;
            }
        }
        if (toCompare == null) {
            for (Customer customer1 : customerList) {
                if (customer.getId() == customer1.getId()) {
                    customersDAO.updateCustomer(customer);
                    return;
                }
            }
        } else if (customer.getId() != toCompare.getId()) {
            throw new invalidAdminException("The customer id cannot be updated");
        }
        customersDAO.updateCustomer(customer);
    }

    /**
     * Delete an existing customer.
     * The customer's purchase history should also be deleted.
     *
     * @param customer Customer
     */
    public void deleteCustomer(Customer customer) {
        //TODO - delete a Customer
        customersDAO.deleteCustomer(customer.getId());
    }

    /**
     * Get all customers.
     *
     * @return List
     */
    public List<Customer> getAllCustomers() {
        return customersDAO.getAllCustomers();
    }

    /**
     * Get a single customers by id.
     *
     * @param id int
     * @return Customer
     */
    public Customer getSingleCustomer(int id) {
        return customersDAO.getSingleCustomer(id);
    }

}
