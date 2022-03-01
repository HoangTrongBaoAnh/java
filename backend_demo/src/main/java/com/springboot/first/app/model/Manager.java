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
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "Managers")
public class Manager {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "position")
	private String position;
	
//	@JsonManagedReference
	@JsonIgnoreProperties("manager")
	@OneToMany(mappedBy = "manager",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Employee> employees  = new ArrayList<>();;
	
	@PreRemove
	private void preRemove() {
		getEmployees().clear();
	}
	
}
