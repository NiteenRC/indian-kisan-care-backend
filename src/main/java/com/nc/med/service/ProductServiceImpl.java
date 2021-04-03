package com.nc.med.service;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Product;
import com.nc.med.model.PurchaseOrder;
import com.nc.med.repo.ProductRepo;
import com.nc.med.repo.PurchaseOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    public PurchaseOrderService orderService;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    PurchaseOrderRepo orderRepo;
    List<Product> productsList = null;

    @Override
    public void deleteProduct(Product productID) {
        productRepo.delete(productID);
    }

    @Override
    public Product findByProductID(Long productID) {
        return productRepo.findById(productID).get();
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepo.findAll();
    }

    @Override
    public Product findByProductName(String productName) {
        return productRepo.findByProductName(productName);
    }

    @Override
    public List<Product> saveProducts(List<Product> products) {
        return productRepo.saveAll(products);
    }

    @Override
    public Product saveProduct(Product product) {
        //product.set
        return productRepo.save(product);
    }

    @Override
    public Product addToStock(List<Product> products) {
        for (Product product : products) {
            if (product.getProductName() != null) {
                Product product2 = productRepo.findByProductName(product.getProductName());
                if (product2 == null) {
                    productRepo.save(product);
                } else {
                    product2.setQty(product2.getQty() + product.getQty());
                    productRepo.save(product2);
                }
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<?> removeFromStock(List<Product> products) {
        for (Product product : products) {
            if (product.getProductName() != null) {
                Product product2 = productRepo.findByProductName(product.getProductName());
                // deduct from stock
                product2.setQty(product2.getQty() - product.getQty());
                productRepo.save(product2);

                // Add to order
                PurchaseOrder order = new PurchaseOrder();
                order.setTotalPrice(product.getPrice());
                order.setTotalQty(product.getQty());
                orderService.saveOrder(order);
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<?> removeProductTemp(List<Product> products) {
        boolean validation = true;
        for (Product product : products) {
            Product product2 = productRepo.findByProductName(product.getProductName());
            if (product.getProductName() != null) {
                if (product2 == null) {
                    validation = false;
                    return new ResponseEntity<>(
                            new CustomErrorTypeException("Stock is not avaible for " + product.getProductName()), HttpStatus.OK);
                } else {
                    if (product2.getQty() < product.getQty()) {
                        validation = false;
                        return new ResponseEntity<>(
                                new CustomErrorTypeException(
                                        "Stock avaible for " + product.getProductName() + " is " + product2.getQty()),
                                HttpStatus.OK);
                    }
                }
            }
        }
        if (validation) {
            productsList = new ArrayList<>();
            productsList.addAll(products);
            return new ResponseEntity<>(productsList, HttpStatus.OK);
        }
        return null;
    }

    @Override
    public List<Product> removeProductGetTemp() {
        return productsList;
    }
}
