package com.nc.med.service;

import com.nc.med.model.Location;
import com.nc.med.repo.LocationRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepo categoryRepo;

    @Override
    public Location saveLocation(Location category) {
        return categoryRepo.save(category);
    }

    @Override
    public List<Location> fetchAllCategories() {
        return categoryRepo.findAll(Sort.by("cityName"));
    }

    @Override
    public Location findByLocationID(Long categoryID) {
        return categoryRepo.findById(categoryID).orElse(null);
    }

    @Override
    public void deleteLocation(Location categoryID) {
        categoryRepo.delete(categoryID);
    }

    @Override
    public Location findByLocationName(String cityName) {
        return categoryRepo.findByCityName(cityName);
    }
}
