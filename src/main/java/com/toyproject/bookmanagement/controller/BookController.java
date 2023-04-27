package com.toyproject.bookmanagement.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toyproject.bookmanagement.dto.book.SearchBookReqDto;
import com.toyproject.bookmanagement.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookController {
	
	private final BookService bookService;
	
	@GetMapping("/book/{bookId}")
	public ResponseEntity<?> getBooks(@PathVariable int bookId){
		
		return ResponseEntity.ok().body(bookService.getBook(bookId));
	}
	
	
	
	@GetMapping("/books")
	public ResponseEntity<?> searchBooks(SearchBookReqDto searchBookReqDto){
		
		return ResponseEntity.ok().body(bookService.searchBooks(searchBookReqDto));
	}
	
	@GetMapping("/categories")
	public ResponseEntity<?> categories(){
		return ResponseEntity.ok().body(bookService.getCategories());
	}
	
	@GetMapping("/book/{bookId}/like")
	public ResponseEntity<?> getLikeCount(@PathVariable int bookId){
		return ResponseEntity.ok().body(bookService.getLikeCount(bookId));
	}
	
	@GetMapping("/book/{bookId}/like/status")
	public ResponseEntity<?> getLikeStatus(@PathVariable int bookId, @RequestParam int userId){
		return ResponseEntity.ok().body(bookService.getLikeStatus(bookId,userId));
	}
	
	@PostMapping("/book/{bookId}/like")
	public ResponseEntity<?> setLike(@PathVariable int bookId,@RequestBody Map<String, Integer> requestMap){
		return ResponseEntity.ok().body(bookService.setLike(bookId, requestMap.get("userId")));
	}
	
	@DeleteMapping("/book/{bookId}/like")
	public ResponseEntity<?> disLike(@PathVariable int bookId, int userId){
		return ResponseEntity.ok().body(bookService.disLike(bookId, userId));
	}
	
	@GetMapping("/book/{bookId}/rental/list")
	public ResponseEntity<?> getRentalListByBookId(@PathVariable int bookId){
		return ResponseEntity.ok().body(bookService.getRentalListByBookId(bookId));
	}
	
}
