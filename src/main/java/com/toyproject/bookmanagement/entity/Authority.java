package com.toyproject.bookmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class Authority {
	private int authorityId;
	private int userId;
	private int roleId;
	
	
	private Role role;
	
}
