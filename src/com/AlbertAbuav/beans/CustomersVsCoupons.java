package com.AlbertAbuav.beans;

public class CustomersVsCoupons {
    private int customerID;
    private int couponID;

    public CustomersVsCoupons() {
    }

    public CustomersVsCoupons(int customerID, int couponID) {
        this.customerID = customerID;
        this.couponID = couponID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getCouponID() {
        return couponID;
    }

    public void setCouponID(int couponID) {
        this.couponID = couponID;
    }

    @Override
    public String toString() {
        return "CustomersVsCoupons{" +
                "customerID=" + customerID +
                ", couponID=" + couponID +
                '}';
    }
}
