package com.nc.med.service;

import com.nc.med.model.Company;
import com.nc.med.repo.CompanyRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepo companyRepo;

    @Override
    public Company saveCompany(Company company) {
        return companyRepo.save(company);
    }

    @Override
    public List<Company> fetchAllCategories() {
        return companyRepo.findAll(Sort.by("companyName"));
    }

    @Override
    public Company findByCompanyID(Long companyID) {
        return companyRepo.findById(companyID).orElse(null);
    }

    @Override
    public void deleteCompany(Company companyID) {
        companyRepo.delete(companyID);
    }

    @Override
    public Company findByCompanyName(String companyName) {
        return companyRepo.findByCompanyNameContainingIgnoreCase(companyName);
    }
}
