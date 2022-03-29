package com.springboot.first.app.DTO;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TransactionDtoAdd {
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date transactionDate;
	
	
	private String to;
	
	private BigDecimal amount;
	public Long TransactionCategory_id;
	
	private String description;
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getTransactionCategory_id() {
		return TransactionCategory_id;
	}
	public void setTransactionCategory_id(Long transactionCategory_id) {
		TransactionCategory_id = transactionCategory_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public TransactionDtoAdd(Date transactionDate, String to, BigDecimal amount, Long transactionCategory_id,
			String description) {
		super();
		this.transactionDate = transactionDate;
		this.to = to;
		this.amount = amount;
		TransactionCategory_id = transactionCategory_id;
		this.description = description;
	}
	
	
	

}
