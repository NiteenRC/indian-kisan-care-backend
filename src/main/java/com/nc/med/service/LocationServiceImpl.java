package com.nc.med.service;

import com.nc.med.model.Location;
import com.nc.med.repo.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepo categoryRepo;

    @Override
    public Location saveLocation(Location category) {
        return categoryRepo.save(category);
    }

    @Override
    public List<Location> fetchAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Location findByLocationID(Long categoryID) {
        return categoryRepo.findById(categoryID).get();
    }

    @Override
    public void deleteLocation(Location categoryID) {
        categoryRepo.delete(categoryID);
    }

    @Override
    public Location findByLocationName(String cityName) {
        return categoryRepo.findByCityNameContainingIgnoreCase(cityName);
    }

    @Override
    public Location findLocationById(Long id) {
        return categoryRepo.findById(id).get();
    }
}
