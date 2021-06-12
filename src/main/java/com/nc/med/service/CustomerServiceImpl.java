package com.nc.med.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nc.med.model.Customer;
import com.nc.med.repo.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepo customerRepo;

	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepo.save(customer);
	}

	@Override
	public List<Customer> fetchAllCategories() {
		return customerRepo.findAll(Sort.by("id").descending());
	}

	@Override
	public Customer findByCustomerID(Long customerID) {
		return customerRepo.findById(customerID).get();
	}

	@Override
	public void deleteCustomer(Customer customerID) {
		customerRepo.delete(customerID);
	}

	@Override
	public Customer findByCustomerName(String customerName) {
		return customerRepo.findByCustomerName(customerName);
	}

	@Override
	public Customer findByCustomerNameContainingIgnoreCase(String customerName) {
		return customerRepo.findByCustomerNameContainingIgnoreCase(customerName);
	}

	@Override
	public Customer findCustomerById(Long id) {
		return customerRepo.findById(id).get();
	}
}
