package com.nc.med.controller;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Category;
import com.nc.med.repo.ProductRepo;
import com.nc.med.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public CategoryService categoryService;

    @Autowired
    public ProductRepo productRepo;

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        if (category == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Category is not saved"), HttpStatus.NOT_FOUND);
        }

        Category category1 = categoryService.findByCategoryName(category.getCategoryName());
        if (category1 != null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Category name already exist!!"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        LOGGER.info("category " + category.getCategoryName());
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @DeleteMapping("/{categoryID}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryID) {
        Category category = categoryService.findByCategoryID(categoryID);
        if (category == null) {
            return new ResponseEntity<>(
                    new CustomErrorTypeException("Category with categoryID " + categoryID + " not found."),
                    HttpStatus.NOT_FOUND);
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
}
