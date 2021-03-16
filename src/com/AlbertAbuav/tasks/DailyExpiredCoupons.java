package com.AlbertAbuav.tasks;

import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.dao.CouponsDAO;
import com.AlbertAbuav.dbdao.CouponsDBDAO;
import com.AlbertAbuav.utils.Colors;

import java.util.List;

public class DailyExpiredCoupons implements Runnable{

    private CouponsDAO couponsDAO = new CouponsDBDAO();
    private volatile boolean quit = false;

    public DailyExpiredCoupons() {
    }

    @Override
    public void run() {
        while (!quit) {
            System.out.println("\"" + Thread.currentThread().getName() + " is checking and deleting expired coupons - " + Colors.GREEN + "RUNNING!...." + Colors.RESET);
            List<Coupon> dbExpiredCoupons = couponsDAO.getAllExpiredCoupons();
            if (dbExpiredCoupons.size() > 0) {
                for (Coupon coupon : dbExpiredCoupons) {
                    List<CustomersVsCoupons> temp = couponsDAO.getAllCustomersCouponsByCouponId(coupon.getId());
                        if (temp.size() > 0) {
                            for (CustomersVsCoupons customersVsCoupons : temp) {
                                couponsDAO.deleteCouponPurchase(customersVsCoupons.getCustomerID(), customersVsCoupons.getCouponID());
                                Colors.setYellowBoldPrint("DELETED: | " + customersVsCoupons);
                            }
                        }
                    couponsDAO.deleteCoupon(coupon.getId());
                    Colors.setYellowBoldPrint("DELETED: | " + coupon);
                }
            }
            try {
                Thread.sleep(200 /* needs to be ==> 1000*60*60*24 */);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Deleting expired coupons \"" + Thread.currentThread().getName() + "\" is - " + Colors.RED + "STOPPED!....." + Colors.RESET);
    }
    public void stop() {
        quit = true;
    }
}
