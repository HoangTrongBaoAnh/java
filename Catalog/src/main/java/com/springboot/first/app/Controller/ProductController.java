package com.springboot.first.app.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.springboot.first.app.Model.Product;
import com.springboot.first.app.Repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductRepository productRepository;
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
public List<Product> getProducts() {
	return productRepository.findAll();
}
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Product creProducts(@RequestBody Product product) {
		return productRepository.save(product);
	}
}
