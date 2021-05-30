package com.nc.med.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Supplier;
import com.nc.med.service.SupplierService;

@RestController
@RequestMapping("/supplier")
@Validated
public class SupplierController {

	public static final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);

	@Autowired
	private SupplierService supplierService;

	@PostMapping
	public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier) {
		if (supplier == null) {
			return new ResponseEntity<>(new CustomErrorTypeException("Supplier is not saved"), HttpStatus.NOT_FOUND);
		}

		Supplier supplier1 = supplierService.findBySupplierName(supplier.getSupplierName());
		if (supplier1 != null) {
			return new ResponseEntity<>(new CustomErrorTypeException("Supplier name already exist!!"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(supplierService.saveSupplier(supplier), HttpStatus.CREATED);
	}

	@PostMapping("/purchase")
	public ResponseEntity<?> addSupplierPurchase(@RequestBody Supplier supplier) {
		return new ResponseEntity<>(supplierService.saveSupplier(supplier), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> updateSupplier(@RequestBody Supplier supplier) {
		LOGGER.info("supplier " + supplier.getSupplierName());
		return new ResponseEntity<>(supplierService.saveSupplier(supplier), HttpStatus.CREATED);
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
		return new ResponseEntity<>(supplierService.findBySupplierName(supplierName), HttpStatus.OK);
	}
}
