package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.exceptions.invalidAdminException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public boolean login(String email, String password) throws invalidAdminException {
        if (!(email.equals("admin@admin.com") && password.equals("admin"))) {
            throw new invalidAdminException("Could not login. One or both parameters are incorrect!");
        }
        return true;
    }

    /**
     * Adding a new company.
     * It is not possible to add a company with the same name to an existing company.
     * It is not possible to add a company with the same email to an existing company.
     *
     * @param company Company
     */
    public void addCompany(Company company) throws invalidAdminException {
        if (Objects.isNull(company)) {
            throw new invalidAdminException("There is no customer like the one you entered!");
        } else if (companiesDAO.isCompanyExistByName(company.getName())) {
            throw new invalidAdminException("The name of the company you are trying to add already appears in the system.\nCompanies with the same name cannot be added!");
        } else if (companiesDAO.isCompanyExistByEmail(company.getEmail())) {
            throw new invalidAdminException("The email of the company you are trying to add already appears in the system.\nCompanies with the same email cannot be added!");
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
        if (Objects.isNull(company)) {
            throw new invalidAdminException("There is no customer like you entered!");
        }
        Company toCompare = companiesDAO.getSingleCompanyByName(company.getName());
        if (Objects.isNull(toCompare)) {
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
        if (Objects.isNull(company)) {
            throw new invalidAdminException("There is no company like you entered!");
        }
        if (!companiesDAO.isCompanyExistByName(company.getName())) {
            throw new invalidAdminException("There is no company by the name \"" + company.getName() + "\" in the system!");
        }
        List<Coupon> companyCoupons = company.getCoupons();
        if (companyCoupons.size() != 0) {
            for (Coupon coupon : companyCoupons) {
                List<CustomersVsCoupons> purchases = couponsDAO.getAllCustomersCouponsByCouponId(coupon.getId());
                if (purchases.size() == 0) {
                    couponsDAO.deleteCoupon(coupon.getId());
                    continue;
                }
                for (CustomersVsCoupons purchase : purchases) {
                    couponsDAO.deleteCouponPurchase(purchase.getCustomerID(), purchase.getCouponID());
                }
                couponsDAO.deleteCoupon(coupon.getId());
            }
        }
        companiesDAO.deleteCompany(company.getId());
    }

    /**
     * Get all Companies.
     *
     * @return List
     */
    public List<Company> getAllCompanies() throws invalidAdminException {
        List<Company> companies = companiesDAO.getAllCompanies();
        if (companies.size() == 0) {
            throw new invalidAdminException("There are no companies in the system");
        }
        return companies;
    }

    /**
     * Get a single Company by id.
     *
     * @param id int
     * @return Company
     */
    public Company getSingleCompany(int id) throws invalidAdminException {
        if (id <= 0) {
            throw new invalidAdminException("There is no id like you enter !");
        }
        Company company = companiesDAO.getSingleCompany(id);
        if (Objects.isNull(company)) {
            throw new invalidAdminException("No Company was found by this id!");
        }
        List<Coupon> companyCoupons = couponsDAO.getAllCouponsOfSingleCompany(id);
        if (companyCoupons.size() != 0) {
            company.setCoupons(companyCoupons);
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
        if (Objects.isNull(customer)) {
            throw new invalidAdminException("There is no customer like you entered");
        }
        if (customersDAO.isCustomerExistsByEmail(customer.getEmail())) {
            throw new invalidAdminException("The email of the customer you are trying to add already appears in the system.\nCustomers with the same email cannot be added.");
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
        if (Objects.isNull(customer)) {
            throw new invalidAdminException("There is no customer like you entered");
        }
        if (!customersDAO.isCustomerExistsByEmail(customer.getEmail())) {
            if (!customersDAO.isCustomerExistsById(customer.getId())) {
                throw new invalidAdminException("There is no customer like you entered in the system or the email is incorrect!");
            }
            customersDAO.updateCustomer(customer);
        }
        Customer toCompare = customersDAO.getSingleCustomerByEmailB(customer.getEmail());
        if (customer.getId() != toCompare.getId()) {
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
        if (Objects.isNull(customer)) {
            throw new invalidAdminException("This customer doesn't exists!");
        }
        if (!customersDAO.isCustomerExists(customer.getEmail(), customer.getPassword())) {
            throw new invalidAdminException("There is no customer by the name \"" + customer.getFirstName() + " " + customer.getLastName() + "\" in the system!");
        }
        List<CustomersVsCoupons> purchases = couponsDAO.getAllCustomersCoupons(customer.getId());
        if (purchases.size() != 0) {
            for (CustomersVsCoupons purchase : purchases) {
                couponsDAO.deleteCouponPurchase(purchase.getCustomerID(), purchase.getCouponID());
            }
        }
        customersDAO.deleteCustomer(customer.getId());
    }

    /**
     * Get all customers.
     *
     * @return List
     */
    public List<Customer> getAllCustomers() throws invalidAdminException {
        List<Customer> customers = customersDAO.getAllCustomers();
        if (customers.size() == 0) {
            throw new invalidAdminException("There are no customers in the system");
        }
        return customers;
    }

    /**
     * Get a single customers by id.
     *
     * @param id int
     * @return Customer
     */
    public Customer getSingleCustomer(int id) throws invalidAdminException {
        if (id <= 0) {
            throw new invalidAdminException("There is no id like you enter !");
        }
        Customer customer = customersDAO.getSingleCustomer(id);
        if (Objects.isNull(customer)) {
            throw new invalidAdminException("There is no customer with the id: " + id);
        }
        List<CustomersVsCoupons> purchases = couponsDAO.getAllCustomersCoupons(id);
        if (purchases.size() == 0) {
            return customer;
        }
        List<Coupon> customerCoupon = new ArrayList<>();
        for (CustomersVsCoupons purchase : purchases) {
            customerCoupon.add(couponsDAO.getSingleCoupon(purchase.getCouponID()));
        }
        if (customerCoupon.size() == 0) {
            return customer;
        }
        customer.setCoupons(customerCoupon);
        return customer;
    }

    public List<CustomersVsCoupons> getAllCustomersVsCoupons(int customerID) throws invalidAdminException {
        if (customerID <= 0) {
            throw new invalidAdminException("There is no id like you enter !");
        }
        if (!customersDAO.isCustomerExistsById(customerID)) {
            throw new invalidAdminException("There is no customer with the id: " + customerID);
        }
        List<CustomersVsCoupons> purchase = couponsDAO.getAllCustomersCoupons(customerID);
        if (purchase.size() == 0) {
            throw new invalidAdminException("There are no customer purchases to customer:\n" + customersDAO.getSingleCustomer(customerID));
        }
        return purchase;
    }

}
