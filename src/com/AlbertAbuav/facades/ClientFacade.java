package com.AlbertAbuav.facades;

import com.AlbertAbuav.beans.Customer;
import com.AlbertAbuav.dao.CompaniesDAO;
import com.AlbertAbuav.dao.CouponsDAO;
import com.AlbertAbuav.dao.CustomersDAO;
import com.AlbertAbuav.dbdao.CompaniesDBDAO;
import com.AlbertAbuav.dbdao.CouponsDBDAO;
import com.AlbertAbuav.dbdao.CustomersDBDAO;

public abstract class ClientFacade {
    protected CompaniesDAO companiesDAO = new CompaniesDBDAO();
    protected CustomersDAO customersDAO = new CustomersDBDAO();
    protected CouponsDAO couponsDAO = new CouponsDBDAO();

    public abstract boolean login(String email, String password);

}
