package com.nestify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NestifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NestifyApplication.class, args);
	}

}
