package com.nc.med.repo;

import com.nc.med.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(int Id);

    List<Product> findByCategoryIdAndProductNameContainingIgnoreCaseOrderByPriceDesc(Integer id,
                                                                                     String productName);

    List<Product> findByCategoryIdAndProductNameContainingIgnoreCase(Integer id, String productName);

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    Product findByProductName(String productName);
}
