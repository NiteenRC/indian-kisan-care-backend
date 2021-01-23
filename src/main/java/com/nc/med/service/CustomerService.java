package com.nc.med.service;

import com.nc.med.model.Customer;

import java.util.List;

public interface CustomerService {

	Customer saveCustomer(Customer customer);

	Customer findByCustomerID(Long customerID);

	void deleteCustomer(Customer customerID);

	Customer findByCustomerName(String customerName);

	List<Customer> fetchAllCategories();

	Customer findCustomerById(Long id);
}
