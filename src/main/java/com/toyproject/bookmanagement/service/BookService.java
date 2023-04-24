package com.toyproject.bookmanagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.toyproject.bookmanagement.dto.book.SearchBookReqDto;
import com.toyproject.bookmanagement.dto.book.SearchBookRespDto;
import com.toyproject.bookmanagement.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;
	
	public List<SearchBookRespDto> searchBooks(SearchBookReqDto searchBookReqDto){
		List<SearchBookRespDto> list = new ArrayList<>();
		
		int index = (searchBookReqDto.getPage() - 1) * 20; // 20 부분 수정 가능 
		Map<String, Object> map = new HashMap<>();
		
		map.put("index" , index);
		
		bookRepository.searchBooks(map).forEach(book -> {
			
			list.add(book.toDto());
		});
		
		return list;
	}
}
