package com.springboot.first.app.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer{
	@Override
	  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/user-photos/**").addResourceLocations("file:///C:/Users/Bao%20Anh/Documents/workspace-spring-tool-suite-4-4.13.1.RELEASE/backend_demo/user-photos/");
	  }
}
