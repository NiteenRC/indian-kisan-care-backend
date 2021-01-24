package com.nc.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nc.med.model.Location;

@Repository
public interface LocationRepo extends JpaRepository<Location, Long> {
	Location findByCityNameContainingIgnoreCase(String cityName);
}
