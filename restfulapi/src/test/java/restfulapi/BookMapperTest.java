package restfulapi;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rs.test.restfulapi.common.config.AppConfig;
import com.rs.test.restfulapi.common.domain.Book;
import com.rs.test.restfulapi.common.mapper.BookMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class})
public class BookMapperTest {
	Logger logger = LoggerFactory.getLogger(BookMapperTest.class);
	
	@Autowired 
	private BookMapper bookMapper;
	
	@Test
	public void testBookMapper(){
		List<Book> books = bookMapper.select();
		
		assertEquals(3, books.size());
		
		for (Book book : books){
			logger.info("book={}", book);
		}
		
		Book newBook = new Book(10L, "스칼라 프로그래밍", "케이 호스트만", "프로그래밍 언어", new Date());
		bookMapper.insert(newBook);
		
		books = bookMapper.select();
		assertEquals(4, books.size());
		

		Book selectedBook = bookMapper.selectByPrimaryKey(10L);
		logger.info("i.selectedBook = {}", selectedBook);
		assertEquals(newBook,  selectedBook);
		assertEquals("케이 호스트만", selectedBook.getCreator());
		
		
		newBook.setCreator("나잘난");
		bookMapper.updateByPrimaryKey(newBook);
		selectedBook = bookMapper.selectByPrimaryKey(10L);
		logger.info("u.selectedBook = {}", selectedBook);
		assertEquals("나잘난", selectedBook.getCreator());
		

		bookMapper.deleteByPrimaryKey(10L);
		selectedBook = bookMapper.selectByPrimaryKey(10L);
		logger.info("d.selectedBook = {}", selectedBook);
		assertNull(selectedBook);
	}
}
