package com.springboot.first.app.service;

import java.util.List;

import com.springboot.first.app.Model.Category;

public interface CategoryService {
	List<Category> getCategories();
	Category addCategory(Category category);
	Boolean check(Long id);
}
