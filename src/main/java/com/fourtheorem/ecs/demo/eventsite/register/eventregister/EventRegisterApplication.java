package com.fourtheorem.ecs.demo.eventsite.register.eventregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fourtheorem.ecs.demo.eventsite"})
public class EventRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventRegisterApplication.class, args);
	}

}
