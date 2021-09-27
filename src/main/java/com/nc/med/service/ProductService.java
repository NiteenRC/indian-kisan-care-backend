package com.nc.med.service;

import com.nc.med.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> saveAllProduct(List<Product> products);

    Product findByProductName(String productName);

    Product findByProductID(Long productID);

    void deleteProduct(Product productID);

    List<Product> findAllProduct();
}
