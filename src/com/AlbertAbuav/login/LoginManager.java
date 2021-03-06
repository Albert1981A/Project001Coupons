package com.AlbertAbuav.login;

import com.AlbertAbuav.exceptions.invalidAdminException;
import com.AlbertAbuav.exceptions.invalidCompanyException;
import com.AlbertAbuav.exceptions.invalidCustomerException;
import com.AlbertAbuav.facades.AdminFacade;
import com.AlbertAbuav.facades.ClientFacade;
import com.AlbertAbuav.facades.CompanyFacade;
import com.AlbertAbuav.facades.CustomerFacade;

public class LoginManager {
    /**
     * This class does a collection of method actions to create a single instance of the class.
     * This Singleton is thread safe.
     * It is Lazily loaded.
     */
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

    /**
     * This method defines what type of client is trying to connect.
     * It will define whether he is allowed to enter the facade layer.
     * @param email String
     * @param password String
     * @param clientType String
     * @return ClientFacade
     */
    public ClientFacade login(String email, String password, ClientType clientType) throws invalidCompanyException, invalidCustomerException, invalidAdminException {
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
