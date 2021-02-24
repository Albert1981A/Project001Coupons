package com.AlbertAbuav.dao;

import com.AlbertAbuav.beans.Company;

import java.util.List;

public interface CompaniesDAO {

    boolean isCompanyExists(String email, String password);

    void addCompany(Company company);

    void updateCompany(Company company);

    void deleteCompany(int companyID);

    List<Company> getAllCompanies();

    Company getSingleCompany(int companyID);

    Company getCompanyByEmailAndPassword(String email, String password);

}
