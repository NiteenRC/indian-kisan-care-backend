package com.nc.med.controller;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Category;
import com.nc.med.model.Product;
import com.nc.med.service.CategoryService;
import com.nc.med.service.ProductService;
import com.nc.med.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nc.med.util.WebUrl.*;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private PurchaseOrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> saveProducts(@RequestBody Product product) {
        if (product == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("input is empty"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @PostMapping("/{categoryID}")
    public ResponseEntity<?> saveProduct(@PathVariable Long categoryID, @RequestBody Product product) {
        Category category = categoryService.findByCategoryID(categoryID);
        product.setCategory(category);
        Product productName = productService.findByProductName(product.getProductName());
        if (productName != null) {
            LOGGER.info("Product name already exist!!");
            return new ResponseEntity<>(new CustomErrorTypeException("Product name already exist!!"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/{productID}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productID) {
        Product product = productService.findByProductID(productID);
        if (product == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("ProductID: " + productID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/{productID}")
    public ResponseEntity<?> getProductsById(@PathVariable Long productID) {
        Product product = productService.findByProductID(productID);
        if (product == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("ProductID: " + productID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getProductsForAllCategories() {
        return new ResponseEntity<>(productService.findAllProduct(), HttpStatus.OK);
    }

    @PostMapping(PRODUCTS_ADD)
    public ResponseEntity<?> addOrderList(@RequestBody List<Product> products) {
        return new ResponseEntity<>(productService.addToStock(products), HttpStatus.OK);
    }

    @PostMapping(PRODUCTS_REMOVE_TEMP)
    public ResponseEntity<?> removeOrderListTemp(@RequestBody List<Product> products) {
        return new ResponseEntity<>(productService.removeProductTemp(products), HttpStatus.OK);
    }

    @GetMapping(PRODUCTS_GET_TEMP)
    public ResponseEntity<?> removeOrderGetListTemp() {
        return new ResponseEntity<>(productService.removeProductGetTemp(), HttpStatus.OK);
    }

    @PostMapping(PRODUCTS_REMOVE)
    public ResponseEntity<?> removeOrderList(@RequestBody List<Product> products) {
        return productService.removeFromStock(products);
    }
}
