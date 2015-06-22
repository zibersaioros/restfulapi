package com.rs.test.restfulapi.common.service;

import java.util.List;

import com.rs.test.restfulapi.common.domain.Book;

public interface BookService {

	List<Book> getBooks();
	
	Book getBook(Long id);
	
	int createBook(Book book);
	
	int updateBook(Book book);
	
	int deleteBook(Long id);
}
