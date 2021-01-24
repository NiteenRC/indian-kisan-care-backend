package com.nc.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nc.med.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

	Category findByCategoryNameContainingIgnoreCase(String categoryName);

	@Query(value = "SELECT category, COUNT(*) as count FROM Product GROUP BY category", nativeQuery = true)
	int findCount();

	//List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);
	
	//List<Category> findAllByCategory();
}
