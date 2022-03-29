package com.springboot.first.app.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.first.app.DTO.categoryDTO;
import com.springboot.first.app.exception.ResourceNotFoundException;
import com.springboot.first.app.helper.upload;
import com.springboot.first.app.model.BillInfo;
import com.springboot.first.app.model.Category;
import com.springboot.first.app.repository.CategoryRepository;
import com.springboot.first.app.service.CategoryService;

@Service
public class CagetoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public List<Category> findCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category creaCategory(categoryDTO category) throws IOException {
		Category category2 = new Category();
		category2.setName(category.getName());
		upload upload = new upload();
		category2.setUrl(upload.uploadImage(category.getUrl()));
		// TODO Auto-generated method stub
		return categoryRepository.save(category2);
	}

	@Override
	public void deleteCagetory(long id) {
		Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cagetory", "id", id));
		//List<BillInfo> billInfos = category.getBillInfos();
		
		for(BillInfo billInfo : category.getBillInfos()) {
			//category.removeChild(billInfo);
			billInfo.setCategory(null);
			
			
		}
		categoryRepository.delete(category);
		// TODO Auto-generated method stub
		
	}

	@Override
	public Category updateCategory(long id,categoryDTO categoryDTO) throws IOException {
		Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category0", "id", id));
		category.setName(categoryDTO.getName());
		if(categoryDTO.getUrl() != null) {
			upload upload = new upload();
			category.setUrl(upload.uploadImage(categoryDTO.getUrl()));
		}
		categoryRepository.save(category);
		// TODO Auto-generated method stub
		return null;
	}

}
