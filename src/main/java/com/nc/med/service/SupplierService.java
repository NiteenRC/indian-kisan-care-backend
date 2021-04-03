package com.nc.med.service;

import com.nc.med.model.Supplier;

import java.util.List;

public interface SupplierService {

    Supplier saveSupplier(Supplier supplier);

    Supplier findBySupplierID(Long supplierID);

    void deleteSupplier(Supplier supplierID);

    Supplier findBySupplierName(String supplierName);

    List<Supplier> fetchAllCategories();

    Supplier findSupplierById(Long id);
}
