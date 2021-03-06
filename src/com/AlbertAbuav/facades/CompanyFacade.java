package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.*;
import com.AlbertAbuav.exceptions.invalidCompanyException;

import java.util.ArrayList;
import java.util.List;

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
        boolean isExist = companiesDAO.isCompanyExists(email, password);
        companyID = companiesDAO.getCompanyByEmailAndPassword(email, password).getId();
        return isExist;
    }

    /**
     * Add a new coupon.
     * Do not add a coupon with the same title to an existing coupon of the same company.
     * It is ok to add a coupon with the same title to another company's coupon.
     *
     * @param coupon Coupon
     */
    public void addCoupon(Coupon coupon) throws invalidCompanyException {
        if (coupon == null) {
            throw new invalidCompanyException("There is no coupon in the system like the coupon you entered");
        }
        if (couponsDAO.getAllCoupons() != null) {
            List<Coupon> dbCoupons = couponsDAO.getAllCoupons();
            List<Coupon> companyCoupons = new ArrayList<>();
            for (Coupon dbCoupon : dbCoupons) {
                if (dbCoupon.getCompanyID() == companyID) {
                    companyCoupons.add(dbCoupon);
                }
            }
            if (companyCoupons.size() == 0) {
                couponsDAO.addCoupon(coupon);
                return;
            }
            for (Coupon coupon1 : companyCoupons) {
                if (coupon.getTitle().equals(coupon1.getTitle())) {
                    throw new invalidCompanyException("Cannot add a coupon with the same title to an existing coupon of the same company.");
                }
            }
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
        if (coupon == null) {
            throw new invalidCompanyException("There is no coupon in the system like the coupon you entered");
        }
        List<Coupon> companyCoupons = getAllCompanyCoupons();
        Coupon couponToCompare = null;
        for (Coupon coupon1 : companyCoupons) {
            if (coupon1.getTitle().equals(coupon.getTitle())) {
                couponToCompare = coupon1;
            }
        }
        if (couponToCompare == null) {
            for (Coupon coupon2 : companyCoupons) {
                if (coupon2.getId() == coupon.getId()) {
                    if (coupon2.getCompanyID() == coupon.getCompanyID()) {
                        couponsDAO.updateCoupon(coupon);
                        return;
                    } else {
                        throw new invalidCompanyException("The \"coupon id\" or the \"company id\" cannot be updated");
                    }
                } else if (coupon2.getCompanyID() != coupon.getCompanyID()) {
                    throw new invalidCompanyException("The \"coupon id\" or the \"company id\" cannot be updated");
                }
            }
        } else if (couponToCompare.getId() == coupon.getId()) {
            if (couponToCompare.getCompanyID() != coupon.getCompanyID()) {
                throw new invalidCompanyException("The \"coupon id\" or the \"company id\" cannot be updated");
            }
        } else if (couponToCompare.getId() != coupon.getId()) {
            throw new invalidCompanyException("The \"coupon id\" or the \"company id\" cannot be updated");
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
        if (coupon == null) {
            throw new invalidCompanyException("There is no coupon in the system like the coupon you entered");
        }
        if (couponsDAO.getAllCustomersCouponsByCouponId(coupon.getId()) == null) {
            throw new invalidCompanyException("There are no coupons in the system!");
        }
        List<CustomersVsCoupons> purchases = couponsDAO.getAllCustomersCouponsByCouponId(coupon.getId());
        for (CustomersVsCoupons purchase : purchases) {
            if (purchase.getCouponID() == coupon.getId()) {
                couponsDAO.deleteCouponPurchase(purchase.getCustomerID(), purchase.getCouponID());
            }
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
        if (couponsDAO.getAllCoupons() == null) {
            throw new invalidCompanyException("No coupons found in system");
        }
        List<Coupon> dbCoupons = couponsDAO.getAllCoupons();
        List<Coupon> companyCoupons = new ArrayList<>();
        for (Coupon dbCoupon : dbCoupons) {
            if (dbCoupon.getCompanyID() == companyID) {
                companyCoupons.add(dbCoupon);
            }
        }
        if (companyCoupons.size() == 0) {
            throw new invalidCompanyException("No coupons of the logged company: \"" + companiesDAO.getSingleCompany(companyID).getName() + "\" were found in system");
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
        if (getAllCompanyCoupons() == null) {
            throw new invalidCompanyException("The company don't have any coupons!");
        }
        List<Coupon> companyCoupons = getAllCompanyCoupons();
        List<Coupon> companyCouponsByCategory = new ArrayList<>();
        for (Coupon coupon3 : companyCoupons) {
            if (coupon3.getCategory().equals(category)) {
                companyCouponsByCategory.add(coupon3);
            }
        }
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
        if (getAllCompanyCoupons() == null) {
            throw new invalidCompanyException("The company don't have any coupons!");
        }
        List<Coupon> companyCoupons = getAllCompanyCoupons();
        List<Coupon> companyCouponsByMax = new ArrayList<>();
        for (Coupon coupon4 : companyCoupons) {
            if (coupon4.getPrice() <= maxPrice) {
                companyCouponsByMax.add(coupon4);
            }
        }
        if (companyCouponsByMax.size() == 0) {
            throw new invalidCompanyException("The company don't have any coupons up to the price you entered!");
        }
        return companyCouponsByMax;
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
        if (couponsDAO.getSingleCoupon(id) == null) {
            throw new invalidCompanyException("There are no coupon for the couponID you entered!");
        }
        return couponsDAO.getSingleCoupon(id);
    }

    /**
     * Get all company customers of a single Coupon
     *
     * @param couponID int
     * @return List
     */
    public List<Customer> getAllCompanyCustomersOfASingleCouponByCouponId(int couponID) throws invalidCompanyException {
        if (couponsDAO.getAllCustomersCouponsByCouponId(couponID) == null) {
            throw new invalidCompanyException("There are no customers for the couponID you entered!");
        }
        List<CustomersVsCoupons> companyCouponCustomers = couponsDAO.getAllCustomersCouponsByCouponId(couponID);
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
