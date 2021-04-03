package com.nc.med.service;

import com.nc.med.model.Customer;
import com.nc.med.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return customerRepo.findAll();
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
        return customerRepo.findByCustomerNameContainingIgnoreCase(customerName);
    }

    @Override
    public Customer findCustomerById(Long id) {
        return customerRepo.findById(id).get();
    }
}
