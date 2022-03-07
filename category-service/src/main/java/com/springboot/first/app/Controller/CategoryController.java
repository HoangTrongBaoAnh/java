package com.springboot.first.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.first.app.Model.Category;
import com.springboot.first.app.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public List<Category> categoriesFind(){
		return categoryService.getCategories();
	}
	
	@GetMapping("{id}")
	public Boolean check(@PathVariable Long id) {
		if(categoryService.check(id)) {
			return true;
		}
		return false;
	}
	
	@PostMapping
	public ResponseEntity<Category> categoryAdd(@RequestBody Category category){
		return new ResponseEntity<Category>(categoryService.addCategory(category),HttpStatus.CREATED);
	}
}
