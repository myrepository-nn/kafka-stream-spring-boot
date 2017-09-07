package com.nishant.spring.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class SpringKafkaStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaStreamApplication.class, args);
	}
}
