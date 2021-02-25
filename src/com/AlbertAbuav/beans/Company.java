package com.AlbertAbuav.beans;

import com.AlbertAbuav.utils.FactoryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Company {

    private int id;
    private String name;
    private String email;
    private String password;
    private List<Coupon> coupons = new ArrayList<>();

    public Company() {
        this.name = FactoryUtils.generateCompanyName();
        this.email = createCompanyEmail();
        this.password = FactoryUtils.createPassword();
    }

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Company(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons='" + coupons +
                '}';
    }

    public String createCompanyEmail() {
        String email = FactoryUtils.generateFirstName()+"@" + name + ".com";
        return email.toLowerCase(Locale.ROOT);
    }

}
