package com.springboot.first.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@Table(name = "transaction_categories")
public class TransactionCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@JsonIgnoreProperties("transactionCategory")
	@OneToMany(mappedBy = "TransactionCategory",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Transaction> transactions  = new ArrayList<>();
}
