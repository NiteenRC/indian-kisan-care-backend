package com.nc.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nc.med.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	Customer findByCustomerName(String customerName);
	
	List<Customer> findAllByLocationLocationID(Integer locationId);

	List<Customer> findByLocationLocationID(Integer locationId);// same as above
}
