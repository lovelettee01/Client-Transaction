package com.kakaopay.task;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomTransctionApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CustomTransctionApplication.class);
		application.run(args);
	}
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
