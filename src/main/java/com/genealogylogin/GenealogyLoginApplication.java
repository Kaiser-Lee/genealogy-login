package com.genealogylogin;

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
public class GenealogyLoginApplication {

	public static void main(String[] args) {

		SpringApplication.run(GenealogyLoginApplication.class, args);
		System.out.println("main thread waiting");
	}
}
