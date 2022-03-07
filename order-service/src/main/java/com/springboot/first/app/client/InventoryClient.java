package com.springboot.first.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
	@GetMapping("/api/inventory/{id}")
	Boolean check(@PathVariable String id);
}
