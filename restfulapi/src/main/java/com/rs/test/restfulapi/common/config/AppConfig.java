package com.rs.test.restfulapi.common.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class AppConfig {

	@Bean
	public DataSource dataSource(){
		return new EmbeddedDatabaseBuilder()
			.setName("testdb") // DB 이름 설정
			.setType(EmbeddedDatabaseType.HSQL) //DB 종류 설정
			.addScript("schema.sql")
			.addScript("data.sql")
			.build();
	}
}
