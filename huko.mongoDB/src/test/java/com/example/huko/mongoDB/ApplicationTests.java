package com.example.huko.mongoDB;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootApplication
@SpringBootTest
@TestPropertySource(properties = {"DATABASE_URL=mongodb+srv://Tejashree:Tejashree@cluster0.efbz1eq.mongodb.net/HUKO?retryWrites=true&w=majority&appName=Cluster0"})
class ApplicationTests {

	@Value("${DATABASE_URL}")
	private String databaseUrl;

	@Test
	void contextLoads() {
		System.out.println("DATABASE_URL: " + databaseUrl);
	}
}
