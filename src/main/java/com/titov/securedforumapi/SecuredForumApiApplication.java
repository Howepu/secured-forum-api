package com.titov.securedforumapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
public class SecuredForumApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuredForumApiApplication.class, args);
	}

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties("new.datasource")
	public DataSource newDataSource() {
		return DataSourceBuilder.create().build();
	}
}
