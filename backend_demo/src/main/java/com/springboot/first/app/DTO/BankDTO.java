package com.springboot.first.app.DTO;

import org.springframework.web.multipart.MultipartFile;

public class BankDTO {
	private String name;
	
	private MultipartFile url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartFile getUrl() {
		return url;
	}

	public void setUrl(MultipartFile url) {
		this.url = url;
	}
}
