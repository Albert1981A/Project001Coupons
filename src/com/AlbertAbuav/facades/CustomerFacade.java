package com.AlbertAbuav.facades;

public class CustomerFacade extends ClientFacade{

    /**
     * Login method, the method is implemented from the abstract class "ClientFacade"
     * @param email
     * @param password
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
     */

    /**
     * Get all coupons purchased by the customer.
     * That means, all the coupons purchased by the customer who made the login.
     */

    /**
     * Get all coupons from a specific category purchased by the customer.
     * That means, only coupons from a specific category of the customer who made the login.
     */

    /**
     * Get all coupons up to the maximum price set by the customer purchased.
     * That means, return only coupons up to the maximum price set by the customer who made the login.
     */

    /**
     * Get customer details.
     * That means, the details of the customer who performed the login.
     */
}
