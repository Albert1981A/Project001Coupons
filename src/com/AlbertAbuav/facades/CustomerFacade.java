package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.Category;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.exceptions.invalidCustomerException;
import com.AlbertAbuav.utils.DBUtils;
import com.AlbertAbuav.utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerFacade extends ClientFacade {

    private int customerID;

    public CustomerFacade() {
    }

    /**
     * Login method, the method is implemented from the abstract class "ClientFacade"
     *
     * @param email    String
     * @param password String
     * @return boolean
     */
    @Override
    public boolean login(String email, String password) throws invalidCustomerException {
        if (!customersDAO.isCustomerExists(email, password)) {
            throw new invalidCustomerException("Could not login. One or both parameters are incorrect!");
        }
        Customer logged = customersDAO.getCustomerByEmailAndPassword(email, password);
        System.out.println("The logged Customer is: | " + logged);
        customerID = logged.getId();
        return true;
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
        if (Objects.isNull(coupon)) {
            throw new invalidCustomerException("There is no coupon like you entered!");
        }
        if (coupon.getAmount() == 0) {
            throw new invalidCustomerException("The coupons of id: \"" + coupon.getId() + "\" are out of stock!");
        } else if (coupon.getEndDate().before(DateUtils.javaDateFromLocalDate(LocalDate.now()))) {
            throw new invalidCustomerException("The coupon id: \"" + coupon.getId() + "\" has expired!");
        } else if (couponsDAO.isCustomersCouponsExistsByCustomerIdAndCouponId(customerID, coupon.getId())) {
            throw new invalidCustomerException("You already purchase coupon id: \"" + coupon.getId() + "\". You cannot purchase the same coupon more than once");
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
    public List<Coupon> getAllCustomerCoupons() throws invalidCustomerException {
        return DBUtils.getAllCustomerCouponsGeneric(customerID);
    }

    /**
     * Get all coupons from a specific category purchased by the customer.
     * That means, only coupons from a specific category of the customer who made the login.
     *
     * @param category Category
     * @return List
     */
    public List<Coupon> getAllCustomerCouponsOfSpecificCategory(Category category) throws invalidCustomerException {
        return DBUtils.getAllCustomerCouponsGeneric(customerID, category);
    }

    /**
     * Get all coupons up to the maximum price set by the customer purchased.
     * That means, return only coupons up to the maximum price set by the customer who made the login.
     *
     * @param maxPrice double
     * @return List
     */
    public List<Coupon> getAllCustomerCouponsUpToMaxPrice(double maxPrice) throws invalidCustomerException {
        return DBUtils.getAllCustomerCouponsGeneric(customerID, maxPrice);
    }

    /**
     * Get customer details.
     * That means, the details of the customer who performed the login.
     *
     * @return List
     */
    public Customer getTheLoggedCustomerDetails() throws invalidCustomerException {
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
