package com.inloco.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@SpringBootApplication
@EnableJpaAuditing
public class GatewayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApiApplication.class, args);
	}

	
}
