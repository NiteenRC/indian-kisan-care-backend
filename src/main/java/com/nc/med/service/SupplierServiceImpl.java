package com.nc.med.service;

import com.nc.med.model.Supplier;
import com.nc.med.repo.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepo supplierRepo;

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepo.save(supplier);
    }

    @Override
    public List<Supplier> fetchAllCategories() {
        return supplierRepo.findAll(Sort.by("supplierName")).stream()
                .filter(x -> !x.getSupplierName().startsWith("UNKNOWN"))
                .collect(Collectors.toList());
    }

    @Override
    public Supplier findBySupplierID(Long supplierID) {
        return supplierRepo.findById(supplierID).orElse(null);
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
    public Supplier findBySupplierNameContainingIgnoreCase(String supplierName) {
        return supplierRepo.findBySupplierNameContainingIgnoreCase(supplierName);
    }
}
