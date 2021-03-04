package com.AlbertAbuav.tasks;

import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.dao.CouponsDAO;
import com.AlbertAbuav.dbdao.CouponsDBDAO;

import java.util.List;

public class DailyExpiredCoupons implements Runnable{

    private CouponsDAO couponsDAO = new CouponsDBDAO();
    private volatile boolean quit = false;

    public DailyExpiredCoupons() {
    }

    @Override
    public void run() {
        while (!quit) {
            System.out.println("\"" + Thread.currentThread().getName() + " is checking and deleting expired coupons - RUNNING!....");
            if (couponsDAO.getAllExpiredCoupons() != null) {
                List<Coupon> dbExpiredCoupons = couponsDAO.getAllExpiredCoupons();
                List<CustomersVsCoupons> temp = null;
                for (Coupon coupon : dbExpiredCoupons) {
                    if (couponsDAO.getAllCustomersCouponsByCouponId(coupon.getId()) != null) {
                        temp = couponsDAO.getAllCustomersCouponsByCouponId(coupon.getId());
                        for (CustomersVsCoupons customersVsCoupons : temp) {
                            couponsDAO.deleteCouponPurchase(customersVsCoupons.getCustomerID(), customersVsCoupons.getCouponID());
                            System.out.println("DELETED: | " + customersVsCoupons);
                        }
                    }
                    couponsDAO.deleteCoupon(coupon.getId());
                    System.out.println("DELETED: | " + coupon);
                }
            }
            try {
                Thread.sleep(200 /* needs to be ==> 1000*60*60*24 */);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Deleting expired coupons \"" + Thread.currentThread().getName() + "\" is - STOPPED!.....");
    }

    public void stop() {
        quit = true;
    }
}
