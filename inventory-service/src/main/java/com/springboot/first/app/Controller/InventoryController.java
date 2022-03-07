package com.springboot.first.app.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.first.app.Model.Inventory;
import com.springboot.first.app.Repository.InventoryRepository;
import com.springboot.first.app.client.CategoryClient;
import com.springboot.first.app.client.ProductClient;
import com.springboot.first.app.dto.productDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	private final CategoryClient categoryClient;

	private final ProductClient productClient;
	private final InventoryRepository inventoryRepository;
	@GetMapping("/{stock}")
	public Boolean isInStock(@PathVariable String stock) {
		Inventory inventory = inventoryRepository.findByskuCode(stock).orElseThrow(() -> new RuntimeException("Cant not find product by Skucode " + stock));
		if(inventory.getStock()>0) {
			return true;
		}
		return false;
	}
	
	
	@PostMapping
	public String addInventory(@RequestBody Inventory inventory) {
		//productDto product = new productDto(inventory.getSkuCode(),inventory.getSkuCode(),new BigDecimal("1200"));
		//productClient.addProduct(product);
		if(categoryClient.check(inventory.getCategory_id())) {
			inventoryRepository.save(inventory);
			return "Add Successfully";
		}
		
		return "category id not found";
	}
	
	@GetMapping()
	public List<Inventory> findall() {
		return inventoryRepository.findAll();
	}
}
