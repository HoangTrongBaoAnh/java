package com.springboot.first.app.dto;

import java.math.BigDecimal;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class productDto {
	private String name;
	private String description;
    private BigDecimal price;
	public productDto(String name, String description, BigDecimal price) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
    
}
