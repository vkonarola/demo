package com.narola.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
// @formatter:off
@ComponentScan(basePackages = { "com.narola.core",
								"com.narola.authenticationservice", 
								"com.narola.taskservice" }) 
@EntityScan(basePackages = { "com.narola.core.authentication.entity", 
							 "com.narola.core.task.entity" })
@EnableJpaRepositories(basePackages = { "com.narola.taskservice.repository",
										"com.narola.core.authentication.repository" })
// @formatter:on
@EnableAsync
@Configuration
public class TaskserviceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {		
		SpringApplication.run(TaskserviceApplication.class, args);
	}
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
}
