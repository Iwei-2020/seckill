package com.smile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.smile.mapper")
public class SeckillDemo2Application {
	public static void main(String[] args) {
		SpringApplication.run(SeckillDemo2Application.class, args);
	}
}
