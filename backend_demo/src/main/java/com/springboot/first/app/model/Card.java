package com.springboot.first.app.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Entity
@Data
@Table(name = "cards")
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cardnumber;
	private String securitycode;
	private BigDecimal balance;
	
	@JsonIgnoreProperties("card")
	@OneToOne(mappedBy = "card", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Wallet wallet;
	
	@JsonIgnoreProperties("bank")
    @JsonProperty("bank_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_id", referencedColumnName = "id",nullable = true)
	private Bank bank;
}
