package com.nc.med.service;

import com.nc.med.model.Location;
import com.nc.med.model.Supplier;
import com.nc.med.repo.LocationRepo;
import com.nc.med.repo.SupplierRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepo supplierRepo;
    private final LocationRepo locationRepo;

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        String locationName = supplier.getLocation().getCityName();
        Location location = locationRepo.findByCityName(locationName);

        if (location == null) {
            location = locationRepo.save(Location.builder().cityName(locationName.toUpperCase(Locale.ROOT)).build());
            supplier.setLocation(location);
        }
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
