package com.nc.med.service;

import com.nc.med.model.Category;
import com.nc.med.repo.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public List<Category> fetchAllCategories() {
        return categoryRepo.findAll(Sort.by("categoryName"));
    }

    @Override
    public Category findByCategoryID(Long categoryID) {
        return categoryRepo.findById(categoryID).orElse(null);
    }

    @Override
    public void deleteCategory(Category categoryID) {
        categoryRepo.delete(categoryID);
    }

    @Override
    public Category findByCategoryNameContainIgnoreCase(String categoryName) {
        return categoryRepo.findByCategoryNameContainingIgnoreCase(categoryName);
    }

    @Override
    public Category findByCategoryName(String categoryName) {
        return categoryRepo.findByCategoryName(categoryName);
    }
}
