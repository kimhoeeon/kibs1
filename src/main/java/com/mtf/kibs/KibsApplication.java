package com.mtf.kibs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KibsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KibsApplication.class, args);
	}

}
