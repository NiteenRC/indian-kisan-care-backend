package com.nc.med.service;

import com.nc.med.model.Category;
import com.nc.med.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public List<Category> fetchAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category findByCategoryID(Integer categoryID) {
        return categoryRepo.findById(categoryID).get();
    }

    @Override
    public void deleteCategory(Category categoryID) {
        categoryRepo.delete(categoryID);
    }

    @Override
    public Category findByCategoryName(String categoryName) {
        return categoryRepo.findByCategoryName(categoryName);
    }

	@Override
	public Category findCategoryById(Integer id) {
		return categoryRepo.findById(id).get();
	}
}
