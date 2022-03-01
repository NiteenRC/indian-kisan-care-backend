package com.nc.med.controller;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Customer;
import com.nc.med.service.CustomerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/customer")
@Validated
@AllArgsConstructor
public class CustomerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    public CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        if (customer == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Customer is not saved"), HttpStatus.NOT_FOUND);
        }

        String customerName = customer.getCustomerName().toUpperCase();
        Customer customer1 = customerService.findByCustomerName(customerName);
        if (customer1 != null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Customer name already exist!!"),
                    HttpStatus.NOT_FOUND);
        }
        customer.setCustomerName(customerName);
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        LOGGER.info("customer " + customer.getCustomerName());
        String customerName = customer.getCustomerName().toUpperCase();

        Customer categoryObj = customerService.findByCustomerName(customerName);
        if (categoryObj == null || Objects.equals(categoryObj.getId(), customer.getId())) {
            customer.setCustomerName(customerName);
            return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomErrorTypeException("Customer already exist!!"), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{customerID}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerID) {
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
    public ResponseEntity<Customer> findCustomerByID(@PathVariable Long customerID) {
        return new ResponseEntity<>(customerService.findByCustomerID(customerID), HttpStatus.OK);
    }

    @GetMapping("/customerName")
    public ResponseEntity<?> findBySupplierName(@RequestParam String customerName) {
        return new ResponseEntity<>(customerService.findByCustomerNameContainingIgnoreCase(customerName),
                HttpStatus.OK);
    }
}
