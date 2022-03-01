package com.nc.med.service;

import com.nc.med.model.Customer;
import com.nc.med.repo.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public List<Customer> fetchAllCategories() {
        return customerRepo.findAll(Sort.by("customerName")).stream()
                .filter(x -> !x.getCustomerName().startsWith("UNKNOWN"))
                .collect(Collectors.toList());
    }

    @Override
    public Customer findByCustomerID(Long customerID) {
        return customerRepo.findById(customerID).orElse(null);
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
}
