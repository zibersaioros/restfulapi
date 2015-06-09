package com.rs.test.restfulapi.common.mapper;

import java.util.List;

import com.rs.test.restfulapi.common.domain.Book;


public interface BookMapper {

	List<Book> select();
	
	Book selectByPrimaryKey(Long id);
	
	int insert(Book book);
	
	int updateByPrimaryKey(Book book);
	
	int deleteByPrimaryKey(Long id);
}
