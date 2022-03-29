package com.springboot.first.app.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallets")
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal balance;
	private String cardNumber;
	private boolean active;
	
	@JsonIgnoreProperties("wallet")
	@OneToOne
    @MapsId
    private Card card;
	
	@JsonIgnoreProperties("user")
    @JsonProperty("user_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@ManyToOne( fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id",nullable = true)
	private User user;
	
	public boolean getactive() {
		return active;
	}
	
	@Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", name='" + cardNumber + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
