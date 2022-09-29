package com.nc.med.service;

import com.nc.med.model.Product;
import com.nc.med.repo.CategoryRepo;
import com.nc.med.repo.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public void deleteProduct(Product productID) {
        productRepo.delete(productID);
    }

    @Override
    public Product findByProductID(Long productID) {
        return productRepo.findById(productID).orElse(null);
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
        /*String categoryName = product.getCategory().getCategoryName();
        Category category = categoryRepo.findByCategoryName(categoryName);

        if (category == null) {
            category = categoryRepo.save(Category.builder().categoryName(categoryName).build());
            product.setCategory(category);
        }*/
        return productRepo.save(product);
    }

    @Override
    public List<Product> saveAllProduct(List<Product> products) {
        return productRepo.saveAll(products);
    }
}
