package com.toyproject.bookmanagement.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.toyproject.bookmanagement.entity.Book;

@Mapper
public interface BookRepository {
	
	public List<Book> searchBooks(Map<String, Object> map);

}
