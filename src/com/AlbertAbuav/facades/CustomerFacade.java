package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.Category;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.exceptions.invalidCustomerException;
import com.AlbertAbuav.utils.DateUtils;

import java.time.Instant;
import java.time.LocalDate;
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
        } else if (coupon.getEndDate().before(DateUtils.javaDateFromLocalDate(LocalDate.now()))) {
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
        for (CustomersVsCoupons purchase : purchases) {
            couponList.add(couponsDAO.getSingleCoupon(purchase.getCouponID()));
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
        List<Coupon> couponList = getAllCustomerCoupons();
        List<Coupon> categoryList = new ArrayList<>();
        for (Coupon coupon: couponList) {
            if (coupon.getCategory().equals(category)) {
                categoryList.add(coupon);
            }
        }
        return categoryList;
    }

    /**
     * Get all coupons up to the maximum price set by the customer purchased.
     * That means, return only coupons up to the maximum price set by the customer who made the login.
     *
     * @param maxPrice double
     * @return List
     */
    public List<Coupon> getAllCustomerCouponsUpToMaxPrice(double maxPrice) {
        List<Coupon> couponList = getAllCustomerCoupons();
        List<Coupon> categoryList = new ArrayList<>();
        for (Coupon coupon: couponList) {
            if (coupon.getPrice() <= maxPrice) {
                categoryList.add(coupon);
            }
        }
        return categoryList;
    }

    /**
     * Get customer details.
     * That means, the details of the customer who performed the login.
     *
     * @return List
     */
    public Customer getTheLoggedCustomerDetails() {
        Customer loggedCustomer = customersDAO.getSingleCustomer(customerID);
        List<Coupon> customerCoupon = getAllCustomerCoupons();
        if (customerCoupon.size() == 0) {
            return loggedCustomer;
        } else {
            loggedCustomer.setCoupons(customerCoupon);
        }
        return loggedCustomer;
    }
}
