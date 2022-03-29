package com.springboot.first.app.DTO;

import java.math.BigDecimal;

import javax.persistence.Column;

public class BillInfoDTO {

	private String Customer_code;
	
	
	private String address;
	
	private BigDecimal amount;
	
	private long category_id;

	public String getCustomer_code() {
		return Customer_code;
	}

	public void setCustomer_code(String customer_code) {
		Customer_code = customer_code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}

	public BillInfoDTO(String customer_code, String address, BigDecimal amount, long category_id) {
		super();
		Customer_code = customer_code;
		this.address = address;
		this.amount = amount;
		this.category_id = category_id;
	}
	
	
}
