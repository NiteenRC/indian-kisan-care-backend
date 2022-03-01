package com.nc.med.controller;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Location;
import com.nc.med.service.LocationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/location")
@Validated
@AllArgsConstructor
@Slf4j
public class LocationController {
    public LocationService locationService;

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody Location location) {
        if (location == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Location is not saved"), HttpStatus.NOT_FOUND);
        }
        String locationName = location.getCityName().toUpperCase();
        Location category1 = locationService.findByLocationName(locationName);
        if (category1 != null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Location name already exist!!"),
                    HttpStatus.NOT_FOUND);
        }
        location.setCityName(locationName);
        return new ResponseEntity<>(locationService.saveLocation(location), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody Location location) {
        log.info("location " + location.getCityName());
        String locationName = location.getCityName().toUpperCase();
        Location locationObj = locationService.findByLocationName(locationName);
        if (locationObj == null || Objects.equals(locationObj.getId(), location.getId())) {
            location.setCityName(locationName);
            return new ResponseEntity<>(locationService.saveLocation(location), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new CustomErrorTypeException("Location name already exist!!"),
                    HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{locationID}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long locationID) {
        Location location = locationService.findByLocationID(locationID);
        if (location == null) {
            return new ResponseEntity<>(
                    new CustomErrorTypeException("Location with locationID " + locationID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        try {
            locationService.deleteLocation(location);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new CustomErrorTypeException("Location is already in use"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Location>> findAllCategoryList() {
        return new ResponseEntity<>(locationService.fetchAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{locationID}")
    public ResponseEntity<Location> findCategoryByID(@PathVariable Long locationID) {
        return new ResponseEntity<>(locationService.findByLocationID(locationID), HttpStatus.OK);
    }
}
