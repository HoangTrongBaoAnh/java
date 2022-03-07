package com.springboot.first.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springboot.first.app.JwtAuthenticationFilter;

@Configuration
public class config {
	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes().route("auth", r -> r.path("/api/auth/**").filters(f -> f.filter(filter)).uri("lb://auth-service"))
				.route("product",r -> r.path("/api/product/**").uri("lb://catalog-service"))
				.route("inventory",r -> r.path("/api/inventory/**").filters(f -> f.filter(filter)).uri("lb://inventory-service"))
				.route("order",r -> r.path("/api/order/**").filters(f -> f.filter(filter)).uri("lb://order-service"))
				.route("category",r -> r.path("/api/category/**").uri("lb://category-service"))
				.build();
	}
}
