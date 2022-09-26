package com.nc.med.service;

import com.nc.med.model.Customer;
import com.nc.med.model.Location;
import com.nc.med.repo.CustomerRepo;
import com.nc.med.repo.LocationRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final LocationRepo locationRepo;

    @Override
    public Customer saveCustomer(Customer customer) {
        String locationName = customer.getLocation().getCityName();
        Location location = locationRepo.findByCityName(locationName);

        if (location == null && locationName != null) {
            location = locationRepo.save(Location.builder().cityName(locationName.toUpperCase(Locale.ROOT)).build());
        }
        customer.setLocation(location);
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

    @Override
    public void createDirectory(String customerName, String prevCustomerName) {
        Path sourceFilePath = Paths.get("D://customers/" + prevCustomerName);
        Path targetFilePath = Paths.get("D://customers/" + customerName);

        try {
            Files.move(sourceFilePath, targetFilePath);
        } catch (FileAlreadyExistsException ex) {
            System.out.println("Target file already exists");
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
    }
}
