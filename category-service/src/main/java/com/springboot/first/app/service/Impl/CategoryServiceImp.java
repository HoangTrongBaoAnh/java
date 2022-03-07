package com.springboot.first.app.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.first.app.Model.Category;
import com.springboot.first.app.Repository.CategoryRepository;
//import com.springboot.first.app.exception.ResourceNotFoundException;
import com.springboot.first.app.service.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public List<Category> getCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		Category nCategory = new Category();
		nCategory.setName(category.getName());
		categoryRepository.save(nCategory);
		// TODO Auto-generated method stub
		return nCategory;
	}

	@Override
	public Boolean check(Long id) {
		return categoryRepository.existsById(id);
		// TODO Auto-generated method stub
//		if(categoryRepository.existsById(id)) {
//			return true;
//		}
//		//categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
//		return false;
	}

}
