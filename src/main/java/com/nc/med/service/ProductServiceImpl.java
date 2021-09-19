package com.nc.med.service;

import com.nc.med.model.Category;
import com.nc.med.model.Product;
import com.nc.med.model.PurchaseOrder;
import com.nc.med.repo.CategoryRepo;
import com.nc.med.repo.ProductRepo;
import com.nc.med.repo.PurchaseOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    @Autowired
    private CategoryRepo categoryRepo;

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
        return productRepo.findAll(Sort.by("productName"));
    }

    @Override
    public Product findByProductName(String productName) {
        return productRepo.findByProductName(productName);
    }

    @Override
    public Product saveProduct(Product product) {
        String CategoryName = product.getCategory().getCategoryName();
        Category category = categoryRepo.findByCategoryName(CategoryName);

        if (category == null) {
            category = categoryRepo.save(new Category(CategoryName));
            product.setCategory(category);
        }
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
    public List<Product> removeProductGetTemp() {
        return productsList;
    }
}
