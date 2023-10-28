package com.userfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserServiceFeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceFeignApplication.class, args);
	}

}
