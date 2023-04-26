package com.toyproject.bookmanagement.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.toyproject.bookmanagement.entity.Book;
import com.toyproject.bookmanagement.entity.Category;

@Mapper
public interface BookRepository {
	
	public Book getBook(int bookId);
	public List<Book> searchBooks(Map<String, Object> map);
	public int getTotalCount(Map<String, Object> map);
	public List<Category> getCategories();
	public int getLikeCount(int bookId);
	public int getLikeStatus(Map<String, Object> map);

}
