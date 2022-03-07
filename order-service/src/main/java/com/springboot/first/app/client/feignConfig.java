package com.springboot.first.app.client;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class feignConfig implements RequestInterceptor{
	private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer";
    private static final String BEARER_TOKEN_VALUE_STRING = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifSx7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV0sImlhdCI6MTY0NjMyNjE1NywiZXhwIjoxNjQ2NDEyNTU3fQ.jmmHyvuzxXwU3Y1qQLHFVIgZVrcmxMfgQuyygzfGT9hm8Q1J1WAAykuyexjDBQ2UzG7FstffggfwCDifTN771w";
	@Override
	public void apply(RequestTemplate template) {
		
		// TODO Auto-generated method stub
		template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE,BEARER_TOKEN_VALUE_STRING));
		
	}
	
}
