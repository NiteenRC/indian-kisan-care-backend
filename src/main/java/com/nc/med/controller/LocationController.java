package com.nc.med.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Location;
import com.nc.med.service.LocationService;

@RestController
@RequestMapping("/location")
@Validated
public class LocationController {

	public static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

	@Autowired
	public LocationService locationService;

	@PostMapping
	public ResponseEntity<?> addCategory(@RequestBody Location location) {
		if (location == null) {
			return new ResponseEntity<>(new CustomErrorTypeException("Location is not saved"), HttpStatus.NOT_FOUND);
		}

		Location category1 = locationService.findByLocationName(location.getCityName());
		if (category1 != null) {
			return new ResponseEntity<>(new CustomErrorTypeException("Location name already exist!!"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(locationService.saveLocation(location), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> updateCategory(@RequestBody Location location) {
		LOGGER.info("location " + location.getCityName());
		Location locationObj = locationService.findByLocationName(location.getCityName());
		if (locationObj == null || locationObj.getId() == location.getId()) {
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
		locationService.deleteLocation(location);
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
