package com.himansh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoAppApplication {//implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ToDoAppApplication.class, args);
	}
	/*
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**").allowedMethods("GET",
	 * "POST","PUT","DELETE").allowCredentials(true); }
	 */
}
