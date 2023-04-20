package com.toyproject.bookmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		// `/**` : 모든 요청
		// .allowedMethods("*") : 모든 메서드에서 열어준다
		// .allowedOrigins("http://localHost:3000") : 해당 port에서 오는 요청을
		registry.addMapping("/**")
				.allowedMethods("*")
				.allowedOrigins("*");
//				.allowedOrigins("http://localHost:3000");
	}
}
