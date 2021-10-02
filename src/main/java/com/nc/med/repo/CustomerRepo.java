package com.nc.med.repo;

import com.nc.med.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findByCustomerNameContainingIgnoreCase(String customerName);

    Customer findByCustomerName(String customerName);
}
