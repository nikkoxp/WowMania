package com.wowmania;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.wowmania")
public class WoWManiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WoWManiaApplication.class, args);
	}

}
