package com.springboot.first.app.Controller;

import java.util.UUID;

import org.bouncycastle.mime.MimeWriter;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.first.app.Model.Order;
import com.springboot.first.app.Repository.OrderRepository;
import com.springboot.first.app.client.InventoryClient;
import com.springboot.first.app.dto.OrderDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
	private final OrderRepository orderRepository;
	private final InventoryClient inventoryClient;
	
	@PostMapping
	public String placeOrder(@RequestBody OrderDTO orderDTO) {
		//boolean allProductsInStock = orderDTO.getOrderLineItems().stream().allMatch(item -> inventoryClient.checkStock(item.getSkuCode()));
		Order order = new Order();
		order.setOrderLineItems(orderDTO.getOrderLineItems());
		order.setOrderNumber(UUID.randomUUID().toString());
		orderRepository.save(order);
		return "Order place successfully";
	}
}
