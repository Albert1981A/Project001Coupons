package com.AlbertAbuav.dbdao;

import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.dao.CouponsDAO;

import java.util.List;

public class CouponsDBDAO implements CouponsDAO {

    private static final String QUERY_INSERT_COUPON = "";
    private static final String QUERY_UPDATE_COUPON = "";
    private static final String QUERY_DELETE_COUPON = "";
    private static final String QUERY_GET_ALL_COUPONS = "";
    private static final String QUERY_GET_SINGLE_COUPON = "";
    private static final String QUERY_ADD_COUPON_PURCHASE = "";
    private static final String QUERY_DELETE_COUPON_PURCHASE = "";

    /**
     * To perform an operation from the code to the database there are five steps.
     * This methods use the steps below.
     * Step 2 - Taking a connection from the ConnectionPool class.
     * Step 3 - Preparing the instruction for the SQL end execute it.
     * Step 4 - ResultSet (Optional) - Get from the database results into "ResultSet".
     * Step 5 - Returning the connection to the ConnectionPool.
     */

    @Override
    public void addCoupon(Coupon coupon) {

    }

    @Override
    public void updateCoupon(Coupon coupon) {

    }

    @Override
    public void deleteCoupon(int couponID) {

    }

    @Override
    public List<Coupon> getAllCoupons() {
        return null;
    }

    @Override
    public Coupon getSingleCoupon(int couponID) {
        return null;
    }

    @Override
    public void addCouponPurchase(int customerID, int couponID) {
        // TODO pending to step 5
    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {
        // TODO pending to step 5
    }
}
