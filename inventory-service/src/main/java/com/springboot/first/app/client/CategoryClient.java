package com.springboot.first.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service")
public interface CategoryClient {
	@GetMapping("/api/category/{id}")
	Boolean check(@PathVariable long id);
}
