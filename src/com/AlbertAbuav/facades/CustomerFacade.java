package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.Category;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.exceptions.invalidCustomerException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerFacade extends ClientFacade {

    private int customerID;

    public CustomerFacade() {
    }

    public CustomerFacade(int customerID) {
        this.customerID = customerID;
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
        boolean isExist = customersDAO.isCustomerExists(email, password);
        if (isExist) {
            customerID = customersDAO.getCustomerByEmailAndPassword(email, password).getId();
        }
        return isExist;
    }

    /**
     * Purchase a coupon.
     * You cannot purchase the same coupon more than once.
     * The coupon cannot be purchased if its quantity is 0.
     * The coupon cannot be purchased if its expiration date has already been reached.
     * After the purchase, the quantity in stock of the coupon must be reduced by 1.
     *
     * @param coupon Coupon
     */
    public void addCoupon(Coupon coupon) throws invalidCustomerException {
        List<CustomersVsCoupons> allCustomerPurchase = customersVsCouponsDAO.getAllCustomersCoupons(customerID);
        if (coupon.getAmount() == 0) {
            throw new invalidCustomerException("The coupons of \"" + coupon.getTitle() + "\" are out of stock!");
        } else if (coupon.getEndDate().before(new Date())) {
            throw new invalidCustomerException("The coupon \"" + coupon.getTitle() + "\" has expired!");
        }
        if (allCustomerPurchase.size() != 0) {
            for (CustomersVsCoupons customerVsCoupon : allCustomerPurchase) {
                if (coupon.getId() == customerVsCoupon.getCouponID()) {
                    throw new invalidCustomerException("You already purchase coupon \"" + coupon.getTitle() + "\" cannot purchase the same coupon more than once");
                }
            }
        }
        couponsDAO.addCouponPurchase(customerID, coupon.getId());
        coupon.setAmount((coupon.getAmount()) - 1);
        couponsDAO.updateCoupon(coupon);
    }

    /**
     * Get all coupons purchased by the customer.
     * That means, all the coupons purchased by the customer who made the login.
     *
     * @return List
     */
    public List<Coupon> getAllCustomerCoupons() {
        List<CustomersVsCoupons> purchases = couponsDAO.getAllCustomersCoupons(customerID);
        List<Coupon> couponList = new ArrayList<>();
        for (CustomersVsCoupons Purchase : purchases) {
            couponList.add(couponsDAO.getSingleCoupon(Purchase.getCouponID()));
        }
        return couponList;
    }

    /**
     * Get all coupons from a specific category purchased by the customer.
     * That means, only coupons from a specific category of the customer who made the login.
     *
     * @param category Category
     * @return List
     */
    public List<Coupon> getAllCustomerCouponsOfSpecificCategory(Category category) {
        return null;
    }

    /**
     * Get all coupons up to the maximum price set by the customer purchased.
     * That means, return only coupons up to the maximum price set by the customer who made the login.
     *
     * @param maxPrice double
     * @return List
     */
    public List<Coupon> getAllCustomerCouponsUpToMaxPrice(double maxPrice) {
        return null;
    }

    /**
     * Get customer details.
     * That means, the details of the customer who performed the login.
     *
     * @param customerID int
     * @return List
     */
    public Customer getSingleCustomer(int customerID) {
        return null;
    }
}
