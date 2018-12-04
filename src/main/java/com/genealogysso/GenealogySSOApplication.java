package com.genealogysso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath:/spring-dubbo.xml"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
@ServletComponentScan
public class GenealogySSOApplication {

	public static void main(String[] args) {

		SpringApplication.run(GenealogySSOApplication.class, args);
		System.out.println("main thread waiting");
	}
}
