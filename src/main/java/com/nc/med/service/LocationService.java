package com.nc.med.service;

import com.nc.med.model.Location;

import java.util.List;

public interface LocationService {

	Location saveLocation(Location category);

	Location findByLocationID(Integer categoryID);

	void deleteLocation(Location categoryID);

	Location findByLocationName(String cityName);

	List<Location> fetchAllCategories();

	Location findLocationById(Integer id);
}
