package com.nc.med.service;

import com.nc.med.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    List<Product> saveProducts(List<Product> products);

    Product saveProduct(Product product);

    Product findByProductName(String productName);

    Product findByProductID(Long productID);

    void deleteProduct(Product productID);

    ResponseEntity<?> removeFromStock(List<Product> products);

    Product addToStock(List<Product> products);

    List<Product> findAllProduct();

    ResponseEntity<?> removeProductTemp(List<Product> products);

    List<Product> removeProductGetTemp();

}
