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
import org.springframework.web.bind.annotation.RestController;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Customer;
import com.nc.med.service.CustomerService;

@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {

	public static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	public CustomerService customerService;

	@PostMapping
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
		if (customer == null) {
			return new ResponseEntity<>(new CustomErrorTypeException("Customer is not saved"), HttpStatus.NOT_FOUND);
		}

		Customer customer1 = customerService.findByCustomerName(customer.getCustomerName());
		if (customer1 != null) {
			return new ResponseEntity<>(new CustomErrorTypeException("Customer name already exist!!"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
		LOGGER.info("customer " + customer.getCustomerName());
		return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
	}

	@DeleteMapping("/{customerID}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Integer customerID) {
		Customer customer = customerService.findByCustomerID(customerID);
		if (customer == null) {
			return new ResponseEntity<>(
					new CustomErrorTypeException("Customer with customerID " + customerID + " not found."),
					HttpStatus.NOT_FOUND);
		}
		customerService.deleteCustomer(customer);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Customer>> findAllCustomerList() {
		return new ResponseEntity<>(customerService.fetchAllCategories(), HttpStatus.OK);
	}

	@GetMapping("/{customerID}")
	public ResponseEntity<Customer> findCustomerByID(@PathVariable Integer customerID) {
		return new ResponseEntity<>(customerService.findByCustomerID(customerID), HttpStatus.OK);
	}
}
