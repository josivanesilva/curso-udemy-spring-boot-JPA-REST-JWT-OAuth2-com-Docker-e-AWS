package com.vaninhosoftware.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing		//Necessário para as @LastModifiedDate  @CreatedDate funcionarem
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

//parei na aula 90