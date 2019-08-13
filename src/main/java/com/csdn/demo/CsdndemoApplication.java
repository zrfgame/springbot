package com.csdn.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

@SpringBootApplication
@EnableAutoConfiguration //声明事务
public class CsdndemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsdndemoApplication.class, args);
	}
}
