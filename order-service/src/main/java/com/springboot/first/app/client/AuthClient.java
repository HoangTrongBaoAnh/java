package com.springboot.first.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthClient {
	@GetMapping("api/auth/{id}")
	Boolean check(@PathVariable Long id);
}
