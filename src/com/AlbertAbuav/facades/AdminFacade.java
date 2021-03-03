package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.exceptions.invalidAdminException;

import java.util.ArrayList;
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
        if (company == null) {
            throw new invalidAdminException("There is no customer like you entered!");
        }
        if (companiesDAO.getAllCompanies() == null) {
            companiesDAO.addCompany(company);
            return;
        }
        List<Company> companyList = companiesDAO.getAllCompanies();
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
        if (companiesDAO.getAllCompanies() == null) {
            throw new invalidAdminException("There are no companies in the system!");
        }
        if (company == null) {
            throw new invalidAdminException("There is no customer like you entered!");
        }
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
    public void deleteCompany(Company company) throws invalidAdminException {
        if (company == null) {
            throw new invalidAdminException("There is no company like you entered!");
        }
        if (companiesDAO.isCompanyExists(company.getEmail(), company.getPassword())) {
            if (company.getCoupons() != null) {
                List<Coupon> companyCoupons = company.getCoupons();
                for (Coupon coupon : companyCoupons) {
                    List<CustomersVsCoupons> purchases = couponsDAO.getAllCustomersCouponsByCouponId(coupon.getId());
                    for (CustomersVsCoupons purchase : purchases) {
                        couponsDAO.deleteCouponPurchase(purchase.getCustomerID(), purchase.getCouponID());
                    }
                    couponsDAO.deleteCoupon(coupon.getId());
                }
            }
            companiesDAO.deleteCompany(company.getId());
        } else {
            throw new invalidAdminException("There is no company by the name \"" + company.getName() + "\" in the system!");
        }
    }

    /**
     * Get all Companies.
     *
     * @return List
     */
    public List<Company> getAllCompanies() throws invalidAdminException {
        if (companiesDAO.getAllCompanies() == null) {
            throw new invalidAdminException("There are no companies in the system");
        }
        return companiesDAO.getAllCompanies();
    }

    /**
     * Get a single Company by id.
     *
     * @param id int
     * @return Company
     */
    public Company getSingleCompany(int id) throws invalidAdminException {
        if (companiesDAO.getSingleCompany(id) == null) {
            throw new invalidAdminException("No Company was found by this id!");
        }
        Company company = companiesDAO.getSingleCompany(id);
        if (couponsDAO.getAllCoupons() != null) {
            List<Coupon> dbCoupons = couponsDAO.getAllCoupons();
            List<Coupon> companyCoupons = new ArrayList<>();
            for (Coupon dbCoupon : dbCoupons) {
                if (dbCoupon.getCompanyID() == id) {
                    companyCoupons.add(dbCoupon);
                }
            }
            company.setCoupons(companyCoupons);
            return company;
        }
        return company;
    }

    /**
     * Adding a new customer.
     * It is not possible to add a customer with the same email to an existing customer.
     *
     * @param customer Customer
     */
    public void addCustomer(Customer customer) throws invalidAdminException {
        if (customer == null) {
            throw new invalidAdminException("There is no customer like you entered");
        }
        if (customersDAO.getAllCustomers() == null) {
            customersDAO.addCustomer(customer);
            return;
        }
        List<Customer> customerList = customersDAO.getAllCustomers();
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
        if (customersDAO.getAllCustomers() == null) {
            throw new invalidAdminException("There are no customers in the system!");
        }
        if (customer == null) {
            throw new invalidAdminException("There is no customer like you entered");
        }
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
    public void deleteCustomer(Customer customer) throws invalidAdminException {
        if (customer == null) {
            throw new invalidAdminException("This customer doesn't exists!");
        }
        if (customersDAO.isCustomerExists(customer.getEmail(), customer.getPassword())) {
            if (couponsDAO.getAllCustomersCoupons(customer.getId()) != null) {
                List<CustomersVsCoupons> purchases = couponsDAO.getAllCustomersCoupons(customer.getId());
                for (CustomersVsCoupons purchase : purchases) {
                    couponsDAO.deleteCouponPurchase(purchase.getCustomerID(), purchase.getCouponID());
                }
                customersDAO.deleteCustomer(customer.getId());
            }
        } else {
            throw new invalidAdminException("There is no customer by the name \"" + customer.getFirstName() + " " + customer.getLastName() + "\" in the system!");
        }
    }

    /**
     * Get all customers.
     *
     * @return List
     */
    public List<Customer> getAllCustomers() throws invalidAdminException {
        if (customersDAO.getAllCustomers() == null) {
            throw new invalidAdminException("There are no customers in the system");
        }
        return customersDAO.getAllCustomers();
    }

    /**
     * Get a single customers by id.
     *
     * @param id int
     * @return Customer
     */
    public Customer getSingleCustomer(int id) throws invalidAdminException {
        if (customersDAO.getSingleCustomer(id) == null) {
            throw new invalidAdminException("There is no customer with the id: " + id);
        }
        Customer customer = customersDAO.getSingleCustomer(id);
        List<Coupon> customerCoupon = new ArrayList<>();
        if (couponsDAO.getAllCustomersCoupons(id) != null) {
            List<CustomersVsCoupons> purchases = couponsDAO.getAllCustomersCoupons(id);
            for (CustomersVsCoupons purchase : purchases) {
                customerCoupon.add(couponsDAO.getSingleCoupon(purchase.getCouponID()));
            }
            if (customerCoupon.size() == 0) {
                return customer;
            }
            customer.setCoupons(customerCoupon);
        }
        return customer;
    }

    public List<CustomersVsCoupons> getAllCustomersVsCoupons(int customerID) throws invalidAdminException {
        if (customersDAO.getSingleCustomer(customerID) == null) {
            throw new invalidAdminException("There is no customer with the id: " + customerID);
        }
        List<CustomersVsCoupons> purchase = null;
        if (couponsDAO.getAllCustomersCoupons(customerID) != null) {
            purchase = couponsDAO.getAllCustomersCoupons(customerID);
        } else {
            throw new invalidAdminException("There are no customer purchases!");
        }
        return purchase;
    }

}
