package com.nc.med.repo;

import com.nc.med.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {
    Company findByCompanyNameContainingIgnoreCase(String companyName);
    // List<Company> findByCompanyName(String name); //same
    // List<Company> findByCompanyNameIs(String name);//same
    // List<Company> findByCompanyNameEquals(String name);//same

	Company findByCompanyName(String companyName);

    // boolean existsById(Integer id);//in-built method
}
