package com.springboot.first.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.first.app.model.TransactionCategory;
import com.springboot.first.app.repository.TransactionCategoryRepository;
import com.springboot.first.app.repository.TransactionRepository;

@RestController
@RequestMapping("/api/transactioncategory")
public class TransactionCategoryController {
	
	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;
	
	@PostMapping
	public ResponseEntity<TransactionCategory> addTransCate(@RequestBody TransactionCategory transactionCategory){
		transactionCategoryRepository.save(transactionCategory);
		return new ResponseEntity<TransactionCategory>(transactionCategory,HttpStatus.CREATED);
	}
}
