package com.toyproject.bookmanagement.entity;

import java.util.ArrayList;
import java.util.List;

import com.toyproject.bookmanagement.security.PrincipalUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	private int userId;
	private String email;
	private String password;
	private String name;
	private String provider;
	
	private List<Authority> authorities;
	
	
	public PrincipalUser toPrincipal() {
		
		List<String> roles = new ArrayList<>();
		
		authorities.forEach(authority ->{
			roles.add(authority.getRole().getRoleName());
		});
		
		return PrincipalUser.builder()
				.userId(userId)
				.email(email)
				.password(password)
				.authorities(authorities)
				.build();
	}
}
