package com.nc.med.service;

import com.nc.med.model.Supplier;
import com.nc.med.repo.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepo supplierRepo;

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepo.save(supplier);
    }

    @Override
    public List<Supplier> fetchAllCategories() {
        return supplierRepo.findAll();
    }

    @Override
    public Supplier findBySupplierID(Integer supplierID) {
        return supplierRepo.findById(supplierID).get();
    }

    @Override
    public void deleteSupplier(Supplier supplierID) {
        supplierRepo.delete(supplierID);
    }

    @Override
    public Supplier findBySupplierName(String supplierName) {
        return supplierRepo.findBySupplierName(supplierName);
    }

	@Override
	public Supplier findSupplierById(Integer id) {
		return supplierRepo.findById(id).get();
	}
}
