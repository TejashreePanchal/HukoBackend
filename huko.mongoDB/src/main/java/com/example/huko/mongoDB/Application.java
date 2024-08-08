package com.example.huko.mongoDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@ControllerAdvice
	public static class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

		@ExceptionHandler(Exception.class)
		public void handleAllExceptions(Exception ex) {
			ex.printStackTrace();
		}
	}
}
