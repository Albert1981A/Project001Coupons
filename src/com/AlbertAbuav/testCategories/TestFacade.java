package com.AlbertAbuav.testCategories;

import com.AlbertAbuav.beans.Company;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.facades.ClientFacade;
import com.AlbertAbuav.login.ClientType;
import com.AlbertAbuav.login.LoginManager;
import com.AlbertAbuav.utils.ArtUtils;
import com.AlbertAbuav.utils.FactoryUtils;

public class TestFacade {

    public static void main(String[] args) {

        LoginManager loginManager = LoginManager.getInstance();

        System.out.println(ArtUtils.ADMIN_FACADE);

        ClientFacade admin = loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        System.out.println(admin);


        System.out.println(ArtUtils.COMPANIES_FACADE);
        System.out.println(ArtUtils.CUSTOMERS_FACADE);

    }

}
