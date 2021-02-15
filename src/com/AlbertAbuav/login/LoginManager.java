package com.AlbertAbuav.login;

import com.AlbertAbuav.facades.AdminFacade;
import com.AlbertAbuav.facades.ClientFacade;
import com.AlbertAbuav.facades.CompanyFacade;
import com.AlbertAbuav.facades.CustomerFacade;

public class LoginManager {

    private static LoginManager instance = null;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (instance == null){
            synchronized (LoginManager.class){
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public ClientFacade login(String email, String password, ClientType clientType) {
        ClientFacade clientFacade;
        switch (clientType) {
            case ADMINISTRATOR:
                clientFacade = new AdminFacade();
                return (clientFacade.login(email, password)) ? clientFacade : null;
            case COMPANY:
                clientFacade = new CompanyFacade();
                return (clientFacade.login(email, password)) ? clientFacade : null;
            case CUSTOMER:
                clientFacade = new CustomerFacade();
                return (clientFacade.login(email, password)) ? clientFacade : null;
        }
        return null;
    }






}
