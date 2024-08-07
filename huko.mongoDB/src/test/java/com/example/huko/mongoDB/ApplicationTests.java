package com.example.huko.mongoDB;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Value("${DATABASE_URL}")
	private String databaseUrl;

	@Test
	void contextLoads() {
		System.out.println("DATABASE_URL: " + databaseUrl);
	}
}
