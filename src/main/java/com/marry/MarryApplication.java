package com.marry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@MapperScan(basePackages = {"com.marry.mapper"})
public class MarryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarryApplication.class, args);
	}
}
