package com.AlbertAbuav.tasks;

import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.dao.CouponsDAO;
import com.AlbertAbuav.dbdao.CouponsDBDAO;

import java.util.List;

public class DailyExpiredCoupons implements Runnable{

    private final CouponsDAO couponsDAO = new CouponsDBDAO();
    private boolean quit = false;

    public DailyExpiredCoupons() {
    }

    @Override
    public void run() {
        while (true) {
            if (couponsDAO.getAllExpiredCoupons() != null) {
                List<Coupon> dbExpiredCoupons = couponsDAO.getAllExpiredCoupons();
                List<CustomersVsCoupons> temp = null;
                for (Coupon coupon : dbExpiredCoupons) {
                    if (couponsDAO.getAllCustomersCouponsByCouponId(coupon.getId()) != null) {
                        temp = couponsDAO.getAllCustomersCouponsByCouponId(coupon.getId());
                        for (CustomersVsCoupons customersVsCoupons : temp) {
                            couponsDAO.deleteCouponPurchase(customersVsCoupons.getCustomerID(), customersVsCoupons.getCouponID());
                        }
                    }
                    couponsDAO.deleteCoupon(coupon.getId());
                }
            }
        }
    }

    public void stop() {
        quit = true;
    }
}
