package com.springboot.first.app.Controller;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.http.auth.AUTH;
import org.apache.naming.java.javaURLContextFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Supplier;
import com.springboot.first.app.Model.Order;
import com.springboot.first.app.Model.OrderLineItems;
import com.springboot.first.app.Repository.OrderDetailRepository;
import com.springboot.first.app.Repository.OrderRepository;
import com.springboot.first.app.client.AuthClient;
import com.springboot.first.app.client.InventoryClient;
import com.springboot.first.app.dto.OrderDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final InventoryClient inventoryClient;
	private final AuthClient authClient;
	@PostMapping
	public String placeOrder(@RequestBody OrderDTO orderDTO) {
		List<OrderLineItems> orderDT = orderDTO.getOrderLineItems().stream().map(item->item).collect(Collectors.toList());
		List<String> sku= orderDTO.getOrderLineItems().stream().map(item->item.getSkuCode()).collect(Collectors.toList());
		boolean allProductsInStock = true;
		//allProductsInStock = inventoryClient.isInStock("ASUS_GAMMING");
		for (String item : sku) {
			if(inventoryClient.check(item) == false) {
				allProductsInStock = false;
			}
		}
		//boolean allProductsInStock = orderDTO.getOrderLineItems().stream().allMatch(item -> inventoryClient.checkStock(item.getSkuCode()));
		if(allProductsInStock && authClient.check(orderDTO.getUser_id())) {
			Order order = new Order();
			order.setUser_id(orderDTO.getUser_id());
//			order.setOrderLineItems(orderDTO.getOrderLineItems());
			order.setOrderNumber(UUID.randomUUID().toString());
			orderRepository.save(order);
			for (OrderLineItems item : orderDT) {
				OrderLineItems orderLineItems = new OrderLineItems();
				orderLineItems.setOrder(order);
				orderLineItems.setPrice(item.getPrice());
				orderLineItems.setQuantity(item.getQuantity());
				orderLineItems.setSkuCode(item.getSkuCode());
				orderDetailRepository.save(orderLineItems);
			}
			return "Order place successfully";
		}
		return "fail to order";
		
		
	}
	
	@GetMapping
	public Boolean test() {
		
		return authClient.check((long)1);
				}
	
	private Boolean handlerror() {
		return false;
	}
}
