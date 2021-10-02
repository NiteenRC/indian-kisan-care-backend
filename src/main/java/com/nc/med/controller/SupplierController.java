package com.nc.med.controller;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Supplier;
import com.nc.med.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/supplier")
@Validated
public class SupplierController {

    public static final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier) {
        if (supplier == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Supplier is not saved"), HttpStatus.NOT_FOUND);
        }
        String supplierName = supplier.getSupplierName().toUpperCase();
        Supplier supplier1 = supplierService.findBySupplierName(supplierName);
        if (supplier1 != null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Supplier name already exist!!"),
                    HttpStatus.NOT_FOUND);
        }
        supplier.setSupplierName(supplierName);
        return new ResponseEntity<>(supplierService.saveSupplier(supplier), HttpStatus.CREATED);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> addSupplierPurchase(@RequestBody Supplier supplier) {
        return new ResponseEntity<>(supplierService.saveSupplier(supplier), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateSupplier(@RequestBody Supplier supplier) {
        LOGGER.info("supplier " + supplier.getSupplierName());
        String supplierName = supplier.getSupplierName().toUpperCase();
        Supplier supplierObj = supplierService.findBySupplierName(supplierName);
        if (supplierObj == null || Objects.equals(supplierObj.getId(), supplier.getId())) {
            supplier.setSupplierName(supplierName);
            return new ResponseEntity<>(supplierService.saveSupplier(supplier), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new CustomErrorTypeException("Supplier already exist!!"), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{supplierID}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long supplierID) {
        Supplier supplier = supplierService.findBySupplierID(supplierID);
        if (supplier == null) {
            return new ResponseEntity<>(
                    new CustomErrorTypeException("Supplier with supplierID " + supplierID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        supplierService.deleteSupplier(supplier);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> findAllSupplierList() {
        return new ResponseEntity<>(supplierService.fetchAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{supplierID}")
    public ResponseEntity<Supplier> findSupplierByID(@PathVariable Long supplierID) {
        return new ResponseEntity<>(supplierService.findBySupplierID(supplierID), HttpStatus.OK);
    }

    @GetMapping("/supplierName")
    public ResponseEntity<?> findBySupplierName(@RequestParam String supplierName) {
        return new ResponseEntity<>(supplierService.findBySupplierNameContainingIgnoreCase(supplierName),
                HttpStatus.OK);
    }
}
