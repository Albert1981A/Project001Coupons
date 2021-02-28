package com.AlbertAbuav.beans;

import com.AlbertAbuav.utils.DateUtils;

import java.time.LocalDate;
import java.util.Date;

public class Coupon {

    private static int COUNT = 1;

    private int id;
    private int companyID;
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    public Coupon() {
        this.companyID = COUNT;
        this.category = Category.values()[(int)(Math.random()*Category.values().length)];
        this.title = "Title: " + COUNT;
        this.description = "Description: " + COUNT;
        this.startDate = DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(3));
        this.endDate = DateUtils.javaDateFromLocalDate(LocalDate.now().plusDays(7));
        this.amount = (int)(Math.random()*21)+30;
        this.price = (int)(Math.random()*21)+80;
        this.image = "Image: " + COUNT++;
    }

    public Coupon(int companyID) {
        this.companyID = companyID;
        this.category = Category.values()[(int)(Math.random()*Category.values().length)];
        this.title = "Title: " + COUNT;
        this.description = "Description: " + COUNT;
        this.startDate = DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(3));
        this.endDate = DateUtils.javaDateFromLocalDate(LocalDate.now().plusDays(7));
        this.amount = (int)(Math.random()*21)+30;
        this.price = (int)(Math.random()*41)+60;
        this.image = "Image: " + COUNT++;
    }

    public Coupon(int companyID, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    public Coupon(int id, int companyID, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.id = id;
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coupons{" +
                "id=" + id +
                ", companyID=" + companyID +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + DateUtils.beautifyDate(startDate) +
                ", endDate=" + DateUtils.beautifyDate(endDate) +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
