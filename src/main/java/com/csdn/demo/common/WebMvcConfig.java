package com.csdn.demo.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	  /**
     * 重写方法描述：实现在url中输入相应的地址的时候直接跳转到某个地址
     * static和templates   springboot默认  static中放静态页面，而templates中放动态页面
     * 静态页面可以直接访问   动态页面需要先请求服务器，访问后台应用程序，然后再转向到页面 ，默认使用Thymeleaf来做动态页面。
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/about").setViewName("about");
   
    }
}
