package com.AlbertAbuav.dao;

import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.CustomersVsCoupons;

import java.util.List;

public interface CouponsDAO {

    void addCoupon(Coupon coupon);

    void updateCoupon(Coupon coupon);

    void deleteCoupon(int couponID);

    List<Coupon> getAllCoupons();

    Coupon getSingleCoupon(int couponID);

    void addCouponPurchase(int customerID, int couponID);

    void deleteCouponPurchase(int customerID, int couponID);

    List<CustomersVsCoupons> getAllCustomersCoupons(int customerID);

    List<CustomersVsCoupons> getAllCustomersCouponsByCouponId(int couponID);

}
