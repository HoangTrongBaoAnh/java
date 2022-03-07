package com.springboot.first.app.dto;

import java.util.List;

import com.springboot.first.app.Model.OrderLineItems;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	private Long user_id;
	private List<OrderLineItems> orderLineItems;
}
