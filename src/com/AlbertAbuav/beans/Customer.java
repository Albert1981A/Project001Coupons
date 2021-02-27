package com.AlbertAbuav.beans;

import com.AlbertAbuav.utils.FactoryUtils;
import com.AlbertAbuav.utils.PrintUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Customer {
    private static int COUNT = 1;
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Coupon> coupons = new ArrayList<>();

    public Customer() {
        this.firstName = FactoryUtils.generateFirstName();
        this.lastName = FactoryUtils.generateLastName();
        this.email = createEmail(firstName);
        this.password = FactoryUtils.createPassword();
    }

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Customer(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + PrintUtils.listToString(coupons) +
                '}';
    }

    public String createEmail(String firstName) {
        String email = firstName + (COUNT++) + FactoryUtils.generateCustomerEmailType();
        return email.toLowerCase(Locale.ROOT);
    }

}
