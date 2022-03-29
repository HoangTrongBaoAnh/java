package com.springboot.first.app.service;

import java.io.IOException;
import java.util.List;

import com.springboot.first.app.DTO.categoryDTO;
import com.springboot.first.app.model.Category;

public interface CategoryService {
	List<Category> findCategories();
	Category creaCategory(categoryDTO categorydto) throws IOException;
	Category updateCategory(long id,categoryDTO categoryDTO) throws IOException;
	void deleteCagetory(long id);
}
