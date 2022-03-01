package com.springboot.first.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "images")
public class Image {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	public long id;
	
	@Column(name = "name_image")
	public String nameImage;
	
	
}
