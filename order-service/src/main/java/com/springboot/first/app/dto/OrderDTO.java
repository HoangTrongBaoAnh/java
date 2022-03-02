package com.springboot.first.app.dto;

import java.util.List;

import com.springboot.first.app.Model.OrderLineItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	private List<OrderLineItems> orderLineItems;
}
