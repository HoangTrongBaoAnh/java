package com.springboot.first.app.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.first.app.dto.productDto;

@FeignClient(name = "catalog-service")
public interface ProductClient {
	@PostMapping("/api/product")
	void addProduct(@RequestBody productDto productDto);
	
	@GetMapping("/api/product/allProduct")
	List<productDto> getproduct();
}
