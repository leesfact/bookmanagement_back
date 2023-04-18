package com.toyproject.bookmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.csrf().disable();
		 http.httpBasic().disable();
		 http.formLogin().disable();
		 
		 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 
		 http.authorizeRequests()
		 	 .antMatchers("/auth/**")
		 	 .permitAll()
		 	 .anyRequest()
		 	 .authenticated();
	}
}
