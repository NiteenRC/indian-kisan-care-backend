package com.nc.med.repo;

import com.nc.med.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findByCustomerNameContainingIgnoreCase(String customerName);

    List<Customer> findAllByLocationId(Long locationId);

    List<Customer> findByLocationId(Long locationId);// same as above
}
