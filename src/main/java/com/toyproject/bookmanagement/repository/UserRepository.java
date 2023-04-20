package com.toyproject.bookmanagement.repository;

import org.apache.ibatis.annotations.Mapper;

import com.toyproject.bookmanagement.dto.auth.JwtRespDto;
import com.toyproject.bookmanagement.dto.auth.LoginReqDto;
import com.toyproject.bookmanagement.entity.Authority;
import com.toyproject.bookmanagement.entity.User;

@Mapper
public interface UserRepository {
	// 이메일 중복확인
	public User findUserByEmail(String email);
	
	// 유저 등록
	public int saveUser (User user);
	public int saveAuthority(Authority authority);
	
	public JwtRespDto login(LoginReqDto loginReqDto);
	
}
