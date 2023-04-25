package com.toyproject.bookmanagement.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	
	private final JwtTokenProvider jwtTokenProvider; //filter 는 IOC등록된 녀석이 아님 

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request; // 다운캐스팅
		String accessToken  = httpRequest.getHeader("Authorization"); //Authorization에 토큰 들어있음
		accessToken = jwtTokenProvider.getToken(accessToken);
		boolean validationFlag = jwtTokenProvider.validateToken(accessToken); // 유효성 검사 
		
		if(validationFlag) {   //
			Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authentication); // 여기에 등록이 되어야 로그인이 된것이다.
		}
		
		
		chain.doFilter(request, response);
	}

}
