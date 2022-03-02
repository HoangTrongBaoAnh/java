package com.springboot.first.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.first.app.Model.Inventory;
import com.springboot.first.app.Repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryRepository inventoryRepository;
	@GetMapping("/{stock}")
	Boolean isInStock(@PathVariable String stock) {
		Inventory inventory = inventoryRepository.findByskuCode(stock).orElseThrow(() -> new RuntimeException("Cant not find product by Skucode " + stock));
		return inventory.getStock()>0;
	}
	
}
