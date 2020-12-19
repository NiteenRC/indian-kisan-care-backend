package com.nc.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nc.med.model.Company;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {
	Company findByCompanyName(String companyName);
	// List<Company> findByCompanyName(String name); //same
	// List<Company> findByCompanyNameIs(String name);//same
	// List<Company> findByCompanyNameEquals(String name);//same

	// boolean existsById(Integer id);//in-built method
}
