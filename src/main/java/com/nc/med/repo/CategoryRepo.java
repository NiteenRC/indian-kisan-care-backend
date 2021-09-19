package com.nc.med.repo;

import com.nc.med.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category findByCategoryNameContainingIgnoreCase(String categoryName);

    Category findByCategoryName(String categoryName);
}
