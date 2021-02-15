package com.AlbertAbuav.testCategories;

import com.AlbertAbuav.facades.ClientFacade;
import com.AlbertAbuav.login.ClientType;
import com.AlbertAbuav.login.LoginManager;

public class TestFacade {

    public static void main(String[] args) {

        LoginManager loginManager = LoginManager.getInstance();

        ClientFacade clientFacade = loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        System.out.println(clientFacade);
    }

}
