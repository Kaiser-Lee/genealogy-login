package com.genealogylogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenealogyLoginApplication {

	public static void main(String[] args) {

		SpringApplication.run(GenealogyLoginApplication.class, args);
		System.out.println("main thread waiting");
	}
}
