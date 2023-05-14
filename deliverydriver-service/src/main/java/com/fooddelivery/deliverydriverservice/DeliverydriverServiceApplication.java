package com.fooddelivery.deliverydriverservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DeliverydriverServiceApplication {

	@Bean
	RestTemplate restTemplate() {return new RestTemplate();}

	public static void main(String[] args) {
		SpringApplication.run(DeliverydriverServiceApplication.class, args);
	}

}
