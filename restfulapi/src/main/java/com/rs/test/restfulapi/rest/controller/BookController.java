package com.rs.test.restfulapi.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rs.test.restfulapi.common.domain.Book;
import com.rs.test.restfulapi.common.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	BookService bookService;

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Book getBook(@PathVariable("id") Long id){
		Book book = bookService.getBook(id);
		return book;
	}
	
	

	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public List<Book> getBooks(){
		List<Book> books = bookService.getBooks();
		return books;
	}
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public Book createBook(@RequestBody Book book){
		bookService.createBook(book);
		Book selectedBook = bookService.getBook(book.getId());
		return selectedBook;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public Book updateBook(@PathVariable Long id, @RequestBody Book book){
		bookService.updateBook(book);
		Book selectedBook = bookService.getBook(book.getId());
		return selectedBook;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public Book deleteBook(@PathVariable("id") Long id){
		bookService.deleteBook(id);
		Book deletedBook = new Book();
		deletedBook.setId(id);
		return deletedBook;
	}
}
