package com.nc.med.repo;

import com.nc.med.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {
    Company findByCompanyNameContainingIgnoreCase(String companyName);

    Company findByCompanyName(String companyName);
}
