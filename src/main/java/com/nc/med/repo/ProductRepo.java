package com.nc.med.repo;

import com.nc.med.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryCategoryID(int categoryID);

    List<Product> findByCategoryCategoryIDAndProductNameContainingIgnoreCaseOrderByPriceDesc(Integer categoryID,
                                                                                             String productName);

    List<Product> findByCategoryCategoryIDAndProductNameContainingIgnoreCase(Integer categoryID, String productName);

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    Product findByProductName(String productName);
}