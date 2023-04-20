package com.toyproject.bookmanagement.dto;



import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseDto {
	private final boolean success;
	private final int code;
	private final String message;
	
	
	public static ResponseDto ofDefault() {
		
		return new ResponseDto(true, 200, "Successfully");
	}
	public static ResponseDto of(boolean success, int code, String message) {
		return new ResponseDto(success, code,message);
	}
}
