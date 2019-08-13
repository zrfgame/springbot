package com.csdn.demo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration //注入该类
@EnableSwagger2  //开启Swagger2
public class SwaggerConfig {
	@Autowired
	private Environment environment;// 获取application。yml配置的环境

	@Bean
	public Docket createRestApi() {
		Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
			@Override
			public boolean apply(RequestHandler handler) {
				// 除非是在开发环境中否则不开启swagger2
				String active = environment.getProperty("spring.profiles.active");
				// 判断参数是否是开发环境
				if (!active.equalsIgnoreCase("dev")) {
					return false;
				}
				Class<?> declaringClass = handler.declaringClass();// 获取当前所在的类
				if (declaringClass == BasicErrorController.class) {// 报BasicErrorController错的时候
																	// 排除
					return false;
				}
				if (declaringClass.isAnnotationPresent(RestController.class)) { // 被注解的类
					return true;
				}
				if (handler.isAnnotatedWith(ResponseBody.class)) { // 被注解的方法
					return true;
				}
				return false;
			}
		};
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).useDefaultResponseMessages(false).select()
				.apis(predicate).build(); 
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("swagger2 接口测试专用页面！")// 大标题
				.description("springboot swagger2")
				.version("1.0")// 版本
				.build();
	}
}
