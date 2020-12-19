package com.nc.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nc.med.model.Supplier;

public interface SupplierRepo extends JpaRepository<Supplier, Integer> {
	Supplier findBySupplierName(String supplierName);
}
