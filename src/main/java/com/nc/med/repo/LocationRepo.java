package com.nc.med.repo;

import com.nc.med.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepo extends JpaRepository<Location, Long> {
    Location findByCityNameContainingIgnoreCase(String cityName);

	Location findByCityName(String cityName);
}
