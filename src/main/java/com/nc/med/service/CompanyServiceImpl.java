package com.nc.med.service;

import com.nc.med.model.Company;
import com.nc.med.repo.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepo companyRepo;

    @Override
    public Company saveCompany(Company company) {
        return companyRepo.save(company);
    }

    @Override
    public List<Company> fetchAllCategories() {
        return companyRepo.findAll();
    }

    @Override
    public Company findByCompanyID(Long companyID) {
        return companyRepo.findById(companyID).get();
    }

    @Override
    public void deleteCompany(Company companyID) {
        companyRepo.delete(companyID);
    }

    @Override
    public Company findByCompanyName(String companyName) {
        return companyRepo.findByCompanyName(companyName);
    }

	@Override
	public Company findCompanyById(Long id) {
		return companyRepo.findById(id).get();
	}
}
