package com.nc.med.service;

import com.nc.med.model.Company;

import java.util.List;

public interface CompanyService {

    Company saveCompany(Company company);

    Company findByCompanyID(Long companyID);

    void deleteCompany(Company companyID);

    Company findByCompanyName(String companyName);

    List<Company> fetchAllCategories();
}
