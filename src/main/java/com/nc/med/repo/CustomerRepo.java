package com.nc.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nc.med.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
	Customer findByCustomerNameContainingIgnoreCase(String customerName);
	
	List<Customer> findAllByLocationId(Long locationId);

	List<Customer> findByLocationId(Long locationId);// same as above
}
