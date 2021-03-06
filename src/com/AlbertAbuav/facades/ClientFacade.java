package com.AlbertAbuav.facades;

import com.AlbertAbuav.dao.CompaniesDAO;
import com.AlbertAbuav.dao.CouponsDAO;
import com.AlbertAbuav.dao.CustomersDAO;
import com.AlbertAbuav.dbdao.CompaniesDBDAO;
import com.AlbertAbuav.dbdao.CouponsDBDAO;
import com.AlbertAbuav.dbdao.CustomersDBDAO;
import com.AlbertAbuav.exceptions.invalidAdminException;
import com.AlbertAbuav.exceptions.invalidCompanyException;
import com.AlbertAbuav.exceptions.invalidCustomerException;

public abstract class ClientFacade {
    /**
     * An abstract class that connects the "Facade" layer to the "LoginManager" class.
     * It makes the connection of the "Facade" to the Database by the "DAO" and "DBDAO" classes.
     */
    protected CompaniesDAO companiesDAO = new CompaniesDBDAO();
    protected CustomersDAO customersDAO = new CustomersDBDAO();
    protected CouponsDAO couponsDAO = new CouponsDBDAO();

    public abstract boolean login(String email, String password) throws invalidCompanyException, invalidAdminException, invalidCustomerException;

}
