package com.redisdatabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisDbApplication.class, args);
	}

}
