package com.iremaksoy.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan(basePackages = {"com.iremaksoy"})
@EntityScan(basePackages= {"com.iremaksoy"})
@EnableJpaRepositories(basePackages = {"com.iremaksoy"})

public class StajprojeApplication {

	public static void main(String[] args) {
		SpringApplication.run(StajprojeApplication.class, args);
	}

}
