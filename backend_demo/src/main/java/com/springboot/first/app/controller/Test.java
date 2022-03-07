package com.springboot.first.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class Test {
	@GetMapping
	public String AllRole() {
		return "public content";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String admin() {
		return "admin content";
	}
	
	@PreAuthorize("hasRole('MODERATOR')")
	@GetMapping("/moderator")
	public String moderator() {
		return "moderator content";
	}
}
