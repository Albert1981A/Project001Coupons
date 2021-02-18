package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.Category;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.Customer;

import java.util.List;

public class CustomerFacade extends ClientFacade{

    /**
     * Login method, the method is implemented from the abstract class "ClientFacade"
     * @param email String
     * @param password String
     * @return boolean
     */
    @Override
    public boolean login(String email, String password)  {
        return customersDAO.isCustomerExists(email, password);
    }

    /**
     * Purchase a coupon.
     * You cannot purchase the same coupon more than once.
     * The coupon cannot be purchased if its quantity is 0.
     * The coupon cannot be purchased if its expiration date has already been reached.
     * After the purchase, the quantity in stock of the coupon must be reduced by 1.
     * @param coupon Coupon
     */
    public void addCoupon(Coupon coupon){

    }

    /**
     * Get all coupons purchased by the customer.
     * That means, all the coupons purchased by the customer who made the login.
     * @return List
     */
    public List<Coupon> getAllCustomerCoupons() {
        return null;
    }

    /**
     * Get all coupons from a specific category purchased by the customer.
     * That means, only coupons from a specific category of the customer who made the login.
     * @param category Category
     * @return List
     */
    public List<Coupon> getAllCustomerCouponsOfSpecificCategory(Category category) {
        return null;
    }

    /**
     * Get all coupons up to the maximum price set by the customer purchased.
     * That means, return only coupons up to the maximum price set by the customer who made the login.
     * @param maxPrice double
     * @return List
     */
    public List<Coupon> getAllCustomerCouponsUpToMaxPrice(double maxPrice) {
        return null;
    }

    /**
     * Get customer details.
     * That means, the details of the customer who performed the login.
     * @param customerID int
     * @return List
     */
    public Customer getSingleCustomer(int customerID) {
        return null;
    }
}
