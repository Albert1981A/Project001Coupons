package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.*;
import com.AlbertAbuav.exceptions.invalidCompanyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanyFacade extends ClientFacade {

    private int companyID;

    public CompanyFacade() {
    }

    /**
     * Login method, the method is implemented from the abstract class "ClientFacade"
     *
     * @param email    String
     * @param password String
     * @return boolean
     */
    @Override
    public boolean login(String email, String password) throws invalidCompanyException {
        if (!companiesDAO.isCompanyExists(email, password)) {
            throw new invalidCompanyException("Could not login. One or both parameters are incorrect!");
        }
        Company logged = companiesDAO.getCompanyByEmailAndPassword(email, password);
        System.out.println("The logged Company is: | " + logged);
        companyID = logged.getId();
        return true;
    }

    /**
     * Add a new coupon.
     * Do not add a coupon with the same title to an existing coupon of the same company.
     * It is ok to add a coupon with the same title to another company's coupon.
     *
     * @param coupon Coupon
     */
    public void addCoupon(Coupon coupon) throws invalidCompanyException {
        if (Objects.isNull(coupon)) {
            throw new invalidCompanyException("There is no coupon like you entered");
        }
        if (couponsDAO.isCouponExistsByCompanyIdAndTitle(coupon.getCompanyID(), coupon.getTitle())) {
            throw new invalidCompanyException("Cannot add a coupon with the same title to an existing coupon of the same company!");
        }
        couponsDAO.addCoupon(coupon);
    }

    /**
     * Update an existing coupon.
     * The coupon id could not be updated.
     * The company id could not be updated.
     *
     * @param coupon Coupon
     */
    public void updateCoupon(Coupon coupon) throws invalidCompanyException {
        if (Objects.isNull(coupon)) {
            throw new invalidCompanyException("There is no coupon like you entered!");
        }
        if (couponsDAO.isCouponExistsByTitle(coupon.getTitle())) {
            Coupon toCompare = couponsDAO.getCouponByTitle(coupon.getTitle());
            if (toCompare.getCompanyID() != coupon.getCompanyID()) {
                throw new invalidCompanyException("The \"company id\" cannot be updated");
            }
            if (toCompare.getId() != coupon.getId()) {
                throw new invalidCompanyException("The \"coupon id\" cannot be updated");
            }
        }
        couponsDAO.updateCoupon(coupon);
    }

    /**
     * Delete an existing coupon.
     * The purchase history of the coupon by customers must also be deleted.
     *
     * @param coupon Coupon
     */
    public void deleteCoupon(Coupon coupon) throws invalidCompanyException {
        if (Objects.isNull(coupon)) {
            throw new invalidCompanyException("There is no coupon like you entered");
        }
        if (!couponsDAO.isCouponExistsByCouponIdAndCompanyId(coupon.getId(), coupon.getCompanyID())) {
            throw new invalidCompanyException("There is no coupon \"id-" + coupon.getId() + "\" in the system!");
        } else if (coupon.getCompanyID() != companyID) {
            throw new invalidCompanyException("you can not delete other companies coupons!");
        }
        List<CustomersVsCoupons> purchases = couponsDAO.getAllCustomersCouponsByCouponId(coupon.getId());
        if (purchases.size() == 0) {
            throw new invalidCompanyException("There are no coupons in the system!");
        }
        for (CustomersVsCoupons purchase : purchases) {
            couponsDAO.deleteCouponPurchase(purchase.getCustomerID(), purchase.getCouponID());
        }
        couponsDAO.deleteCoupon(coupon.getId());
    }

    /**
     * Get all company coupons.
     * That means, all the coupons of the company that made the login.
     *
     * @return List
     */
    public List<Coupon> getAllCompanyCoupons() throws invalidCompanyException {
        List<Coupon> companyCoupons = couponsDAO.getAllCouponsOfSingleCompany(companyID);
        if (companyCoupons.size() == 0) {
            throw new invalidCompanyException("No coupons found in system");
        }
        return companyCoupons;
    }

    /**
     * Get all coupons from a specific category of the company.
     * That means, only coupons from a specific category of the company that made the login.
     *
     * @param category Category
     * @return List
     */
    public List<Coupon> getAllCompanyCouponsOfSpecificCategory(Category category) throws invalidCompanyException {
        if (!couponsDAO.isCouponsExistsByCompanyId(companyID)) {
            throw new invalidCompanyException("The company don't have any coupons!");
        }
        List<Coupon> companyCouponsByCategory = couponsDAO.getAllCompanyCouponsOfSpecificCategory(companyID, category);
        if (companyCouponsByCategory.size() == 0) {
            throw new invalidCompanyException("The company don't have any coupons of the category you entered!");
        }
        return companyCouponsByCategory;
    }

    /**
     * Get all coupons up to the maximum price set by the company.
     * That means, only coupons up to the maximum price set by the company that performed the login.
     *
     * @param maxPrice double
     * @return List
     */
    public List<Coupon> getAllCompanyCouponsUpToMaxPrice(double maxPrice) throws invalidCompanyException {
        if (!couponsDAO.isCouponsExistsByCompanyId(companyID)) {
            throw new invalidCompanyException("The company don't have any coupons!");
        }
        List<Coupon> companyCouponsByCategory = couponsDAO.getAllCompanyCouponsUpToMaxPrice(companyID, maxPrice);
        if (companyCouponsByCategory.size() == 0) {
            throw new invalidCompanyException("The company don't have any coupons of the category you entered!");
        }
        return companyCouponsByCategory;
    }

    /**
     * Get company details.
     * That means, the details of the company that performed the login.
     *
     * @return Company
     */
    public Company getTheLoggedCompanyDetails() throws invalidCompanyException {
        Company loggedCompany = companiesDAO.getSingleCompany(companyID);
        List<Coupon> companyCoupons = getAllCompanyCoupons();
        if (companyCoupons.size() == 0) {
            return loggedCompany;
        } else {
            loggedCompany.setCoupons(companyCoupons);
        }
        return loggedCompany;
    }

    /**
     * The method return a single coupon of the logged company by id.
     *
     * @param id int
     * @return Coupon
     */
    public Coupon getSingleCoupon(int id) throws invalidCompanyException {
        Coupon singleCoupon = couponsDAO.getSingleCoupon(id);
        if (Objects.isNull(singleCoupon)) {
            throw new invalidCompanyException("There are no coupon for the couponID: \"" + id + "\" you entered!");
        }
        return singleCoupon;
    }

    /**
     * Get all company customers of a single Coupon
     *
     * @param couponID int
     * @return List
     */
    public List<Customer> getAllCompanyCustomersOfASingleCouponByCouponId(int couponID) throws invalidCompanyException {
        if (couponID <= 0) {
            throw new invalidCompanyException("There is no couponID Like you entered!");
        }
        List<CustomersVsCoupons> companyCouponCustomers = couponsDAO.getAllCustomersCouponsByCouponId(couponID);
        if (companyCouponCustomers.size() == 0) {
            throw new invalidCompanyException("There are no customers for the couponID you entered!");
        }
        List<Customer> customersOfCoupon = new ArrayList<>();
        Customer temp;
        for (CustomersVsCoupons vsCoupons : companyCouponCustomers) {
            temp = customersDAO.getSingleCustomer(vsCoupons.getCustomerID());
            customersOfCoupon.add(temp);
        }
        if (customersOfCoupon.size() == 0) {
            throw new invalidCompanyException("There are no customers for the couponID you entered!");
        }
        return customersOfCoupon;
    }


}
