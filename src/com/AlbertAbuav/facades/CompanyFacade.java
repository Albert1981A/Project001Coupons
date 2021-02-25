package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.Category;
import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.exceptions.invalidCompanyException;

import java.util.ArrayList;
import java.util.List;

public class CompanyFacade extends ClientFacade {

    private int companyID;

    public CompanyFacade() {
    }

    public CompanyFacade(int companyID) {
        this.companyID = companyID;
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
        boolean isExist = companiesDAO.isCompanyExists(email, password);
        if (isExist) {
            companyID = companiesDAO.getCompanyByEmailAndPassword(email, password).getId();
        }
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
        List<Coupon> dbCoupons = couponsDAO.getAllCoupons();
        List<Coupon> companyCoupons = new ArrayList<>();
        if (dbCoupons.size() != 0) {
            for (Coupon dbCoupon : dbCoupons) {
                if (dbCoupon.getCompanyID() == companyID) {
                    companyCoupons.add(dbCoupon);
                }
            }
            if (companyCoupons.size() == 0) {
                couponsDAO.addCoupon(coupon);
                return;
            }
        } else {
            couponsDAO.addCoupon(coupon);
            return;
        }
        for (Coupon coupon1 : companyCoupons) {
            if (coupon.getTitle().equals(coupon1.getTitle())) {
                throw new invalidCompanyException("Cannot add a coupon with the same title to an existing coupon of the same company.");
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
    public void updateCoupon(Coupon coupon) {
        couponsDAO.updateCoupon(coupon);
        //TODO - Constraints
    }

    /**
     * Delete an existing coupon.
     * The purchase history of the coupon by customers must also be deleted.
     *
     * @param coupon Coupon
     */
    public void deleteCoupon(Coupon coupon) {
        //TODO - deleting Company coupons
    }

    /**
     * Get all company coupons.
     * That means, all the coupons of the company that made the login.
     *
     * @return List
     */
    public List<Coupon> getAllCompanyCoupons() throws invalidCompanyException {
        List<Coupon> dbCoupons = couponsDAO.getAllCoupons();
        List<Coupon> companyCoupons = new ArrayList<>();
        if (dbCoupons.size() != 0) {
            for (Coupon dbCoupon : dbCoupons) {
                if (dbCoupon.getCompanyID() == companyID) {
                    companyCoupons.add(dbCoupon);
                }
            }
            if (companyCoupons.size() == 0) {
                throw new invalidCompanyException("No coupons of the logged company: \"" + companiesDAO.getSingleCompany(companyID).getName() + "\" were found in system");
            }
        } else {
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
    public List<Coupon> getAllCompanyCouponsOfSpecificCategory(Category category) {
        return null;
    }

    /**
     * Get all coupons up to the maximum price set by the company.
     * That means, only coupons up to the maximum price set by the company that performed the login.
     *
     * @param maxPrice double
     * @return List
     */
    public List<Coupon> getAllCompanyCouponsUpToMaxPrice(double maxPrice) {
        return null;
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


}
