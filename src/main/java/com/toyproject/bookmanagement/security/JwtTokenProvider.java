package com.toyproject.bookmanagement.security;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.toyproject.bookmanagement.dto.auth.JwtRespDto;
import com.toyproject.bookmanagement.entity.Authority;
import com.toyproject.bookmanagement.exception.CustomException;

import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

@Slf4j
@Component
public class JwtTokenProvider {
	private final Key key;
	
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
	
		key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
	
	public JwtRespDto generateToken(Authentication authentication) {
		
		StringBuilder builder = new StringBuilder();
		
		authentication.getAuthorities().forEach(authority -> {
			builder.append(authority.getAuthority() +",");
		});
		
		builder.delete(builder.length() -1, builder.length()); // 마지막 쉼표 삭제 
		
		
		String authorities = builder.toString();
		Date tokenExpiresDate = new Date(new Date().getTime()+ (1000 * 60 * 60 * 24)); // 현재시간 + 하루 
		
		
		
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName()) 				// 토큰의 이름 (email)
				.claim("auth", authorities)             			// auth
				.setExpiration(tokenExpiresDate) 					// 토큰 만료 시간
				.signWith(key, SignatureAlgorithm.HS256) 			// 토큰 암호화
				.compact();
		
		return JwtRespDto.builder()
				.grantType("Bearer")
				.accessToken(accessToken)
				.build();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
			
			return true;
		}catch(SecurityException | MalformedJwtException e) {
			// Security 라이브러리에 오유가 있거나, JSON의 포맷이 잘못된 형식의 JWT가 들어왔을 때 예외
			// SignatureException이 포함되어 있음
			log.info("Invalid JWT Token", e);
		}catch (ExpiredJwtException e) {
			// 토큰의 유효기간이 만료된 경우의 예외
			log.info("Expired JWT Token", e);
		}catch (UnsupportedJwtException e) {
			// jwt의 형식을 지키지 않은 경우 (Header.Payload.Signature)
			log.info("Unsupported JWT Token", e);
		}catch (IllegalArgumentException e) {
			// jwt 토큰이 없을때
			log.info("IllegalArgument JWT Token", e);
		}catch (Exception e) {
			log.info("JWT Token", e);
		}
		return false;
	}
	
	public Authentication getAuthentication(String accessToken) {
		
		Claims claims = parseClaims(accessToken);
		
		Object roles = claims.get("auth");
		
		if(roles == null) {
			throw new CustomException("권한 정보가 없는 토큰입니다.");
		}
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		String[] rolesArray = roles.toString().split(",");
		Arrays.asList(rolesArray).forEach(role -> {
			
			authorities.add(new SimpleGrantedAuthority(role));
		});
		
		UserDetails userDetails = new User(claims.getSubject(),"",authorities);
		
		return new UsernamePasswordAuthenticationToken(userDetails,"",authorities);
	}
	
	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(accessToken)
					.getBody();
		}catch (ExpiredJwtException e) {
			return e.getClaims();
		}
		
	}
}
