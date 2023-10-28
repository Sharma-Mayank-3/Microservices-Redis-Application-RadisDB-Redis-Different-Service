package com.userredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisUserServiceApplication.class, args);
	}

}
