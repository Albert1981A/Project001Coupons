package com.AlbertAbuav.facades;

public class CompanyFacade extends ClientFacade{

    /**
     * Login method, the method is implemented from the abstract class "ClientFacade"
     * @param email
     * @param password
     * @return boolean
     */
    @Override
    public boolean login(String email, String password)  {
        return companiesDAO.isCompanyExists(email, password);
    }

    /**
     * Add a new coupon.
     * Do not add a coupon with the same title to an existing coupon of the same company.
     * It is ok to add a coupon with the same title to another company's coupon.
     */

    /**
     * Update an existing coupon.
     * The coupon id could not be updated.
     * The company id could not be updated.
     */

    /**
     * Delete an existing coupon.
     * The purchase history of the coupon by customers must also be deleted.
     */

    /**
     * Get all company coupons.
     * That means, all the coupons of the company that made the login.
     */

    /**
     * Get all coupons from a specific category of the company.
     * That means, only coupons from a specific category of the company that made the login.
     */

    /**
     * Get all coupons up to the maximum price set by the company.
     * That means, only coupons up to the maximum price set by the company that performed the login.
     */

    /**
     * Get company details.
     * That means, the details of the company that performed the login.
     */

}
