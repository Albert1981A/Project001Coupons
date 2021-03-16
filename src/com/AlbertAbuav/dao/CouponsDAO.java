package com.AlbertAbuav.dao;

import com.AlbertAbuav.beans.Category;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.CustomersVsCoupons;

import java.util.List;

public interface CouponsDAO {

    void addCoupon(Coupon coupon);

    void updateCoupon(Coupon coupon);

    void deleteCoupon(int couponID);

    List<Coupon> getAllCoupons();

    List<Coupon> getAllCouponsOfSingleCompany(int companyID);

    boolean isCouponExistsByCouponIdAndCompanyId(int couponID, int companyID);

    boolean isCouponsExistsByCompanyId(int companyID);

    boolean isCouponExistsByCompanyIdAndTitle(int companyID, String title);

    boolean isCouponExistsByTitle(String title);

    boolean isCouponExistsByCouponIdAndCategory(int couponID, Category category);

    boolean isCouponExistsByCouponIdAndMaxPrice(int couponID, double maxPrice);

    List<Coupon> getAllExpiredCoupons();

    Coupon getSingleCoupon(int couponID);

    Coupon getSingleCouponByCouponIdAndCategory(int couponID, Category category);

    Coupon getCouponByTitle(String title);

    List<Coupon> getAllCompanyCouponsOfSpecificCategory(int companyID, Category category);

    List<Coupon> getAllCompanyCouponsUpToMaxPrice(int companyID, double maxPrice);

    void addCouponPurchase(int customerID, int couponID);

    void deleteCouponPurchase(int customerID, int couponID);

    List<CustomersVsCoupons> getAllCustomersCoupons(int customerID);

    List<CustomersVsCoupons> getAllCustomersCouponsByCouponId(int couponID);

    boolean isCustomersCouponsExistsByCustomerIdAndCouponId(int customerID, int couponID);

    boolean isCustomersCouponsExistsByCouponId(int couponID);

}
