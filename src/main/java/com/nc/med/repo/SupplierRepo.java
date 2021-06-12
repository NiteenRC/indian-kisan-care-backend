package com.nc.med.repo;

import com.nc.med.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {
    Supplier findBySupplierNameContainingIgnoreCase(String supplierName);

	Supplier findBySupplierName(String supplierName);
}
