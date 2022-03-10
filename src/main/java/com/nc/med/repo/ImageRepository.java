package com.nc.med.repo;

import java.util.Optional;

import com.nc.med.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
	Optional<Image> findByName(String name);
}
