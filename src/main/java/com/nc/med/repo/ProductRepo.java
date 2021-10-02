package com.nc.med.repo;

import com.nc.med.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

    Product findByProductName(String productName);
}
