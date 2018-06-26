package com.xupt.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication   //注解是SpringBoot项目的核心注解，主要作用是开启Spring自动配置
public class SpringbootMybatisApplication {

	public static void main(String[] args) {
		//启动了SpringBoot程序，启动Spring容器，启动内嵌的Tomcat服务器
		SpringApplication.run(SpringbootMybatisApplication.class, args);
	}
}
