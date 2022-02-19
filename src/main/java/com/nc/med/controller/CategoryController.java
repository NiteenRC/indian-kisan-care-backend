package com.nc.med.controller;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Category;
import com.nc.med.repo.ProductRepo;
import com.nc.med.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {
    public static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    public final CategoryService categoryService;
    public final ProductRepo productRepo;

    public CategoryController(CategoryService categoryService, ProductRepo productRepo) {
        this.categoryService = categoryService;
        this.productRepo = productRepo;
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        if (category == null || category.getCategoryName() == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Category is not saved"), HttpStatus.NOT_FOUND);
        }
        String categoryName = category.getCategoryName().toUpperCase();
        Category category1 = categoryService.findByCategoryName(categoryName);
        if (category1 != null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Category already exist!!"), HttpStatus.CONFLICT);
        }
        category.setCategoryName(categoryName);
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        LOGGER.info("category " + category.getCategoryName());
        String categoryName = category.getCategoryName().toUpperCase();
        Category categoryObj = categoryService.findByCategoryName(categoryName);
        if (categoryObj == null || Objects.equals(categoryObj.getId(), category.getId())) {
            category.setCategoryName(categoryName);
            return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomErrorTypeException("Category already exist!!"), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{categoryID}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryID) {
        Category category = categoryService.findByCategoryID(categoryID);
        if (category == null) {
            return new ResponseEntity<>(
                    new CustomErrorTypeException("Category with categoryID " + categoryID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        if (!category.getProducts().isEmpty()) {
            return new ResponseEntity<>(
                    new CustomErrorTypeException("Category is associated with product. Please delete product before deleting category"),
                    HttpStatus.CONFLICT);
        }
        categoryService.deleteCategory(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAllCategoryList() {
        return new ResponseEntity<>(categoryService.fetchAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{categoryID}")
    public ResponseEntity<Category> findCategoryByID(@PathVariable Long categoryID) {
        return new ResponseEntity<>(categoryService.findByCategoryID(categoryID), HttpStatus.OK);
    }

    @GetMapping("/categoryName")
    public ResponseEntity<?> findCategoryByID(@RequestParam String categoryName) {
        Category category = categoryService.findByCategoryNameContainIgnoreCase(categoryName);
        return new ResponseEntity<>(category, HttpStatus.OK);

    }
}
