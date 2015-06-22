package com.rs.test.restfulapi.rest.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rs.test.restfulapi.common.config.AppConfig;
import com.rs.test.restfulapi.common.domain.Book;
import com.rs.test.restfulapi.rest.config.RestAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class, RestAppConfig.class})
@WebAppConfiguration
public class BookControllerTest {
	
	Logger logger = LoggerFactory.getLogger(BookControllerTest.class);
	
	private MockMvc mockMvc;
	
	@Autowired
	BookController bookController;
	
	@Before
	public void initMockMvc(){
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		mockMvc = MockMvcBuilders.standaloneSetup(bookController)
				.addFilter(filter)
				.build();
	}
	
//	@Test
//	public void testBook() throws Exception{
//		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
//				.get("/books/1")
//				.accept(MediaType.valueOf("text/plain;charset=UTF-8"));
//		this.mockMvc.perform(requestBuilder)
//			.andDo(MockMvcResultHandlers.print())
//			.andExpect(MockMvcResultMatchers.status().isOk()); //200
//	}
	
	
	@Test
	public void testGetBooks() throws Exception{
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/books")
				.accept(MediaType.APPLICATION_JSON);
		
		this.mockMvc.perform(builder)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].title", is("명예의 조각들")))
			.andExpect(jsonPath("$[0].creator", is("로이스 맥마스터 부졸드")))
			.andExpect(jsonPath("$[1].id", is(2)))
			.andExpect(jsonPath("$[1].title", is("바라야 내전")))
			.andExpect(jsonPath("$[1].creator", is("로이스 맥마스터 부졸드")))
			;
	}
	
	
	@Test
	public void testGetBook() throws Exception{
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/books/2")
				.accept(MediaType.APPLICATION_JSON);
				
		this.mockMvc.perform(requestBuilder).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(2)))
			.andExpect(jsonPath("$.title", is("바라야 내전")))
			.andExpect(jsonPath("$.creator", is("로이스 맥마스터 부졸드")));
	}
	
	
	@Test
	public void testCreateBook() throws Exception{
		
		Book newBook = new Book(100L, "바라야 내전", "로이스 맥마스터 부졸드", "외국판타지소설", new Date());
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(newBook);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content)
				.accept(MediaType.APPLICATION_JSON);
		
		
		this.mockMvc.perform(requestBuilder)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	
	@Test
	public void testUpdateBook() throws Exception{
		
		Book updateBook = new Book(3L, "어스시의 마법사", "어슐러 K. 르귄", "판타지소설", new Date());
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(updateBook);
		logger.info("content = {}", content);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
			.put("/books/3")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content)
			.accept(MediaType.APPLICATION_JSON);
		
		this.mockMvc.perform(builder).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(3)))
			.andExpect(jsonPath("$.title", is("어스시의 마법사")))
			.andExpect(jsonPath("$.creator", is("어슐러 K. 르귄")));
	}
	
	
	@Test
	public void testDeleteBook() throws Exception{
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/books/3")
				.accept(MediaType.APPLICATION_JSON);
		
		this.mockMvc.perform(requestBuilder)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(3)));
	}
}
