package com.AlbertAbuav.dao;

import com.AlbertAbuav.beans.CustomersVsCoupons;

import java.util.List;

public interface CustomersVsCouponsDAO {

    void addCustomersVsCoupons(int customerID, int couponID);

    void deleteCustomersVsCoupons(int customerID, int couponID);

    List<CustomersVsCoupons> getAllCustomersCoupons(int customerID);

    List<CustomersVsCoupons> getAllCustomersCouponsByCouponId(int couponID);

}
