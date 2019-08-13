package com.csdn.demo.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 实现配置类
 * 
 * @author 98790
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	UserDetailsService customUserService() {
		return new CustomUserService();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new CustomPasswordEncoder();
	}
	 @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());
	 
	}
	 
	    @Override
	    protected AuthenticationManager authenticationManager() throws Exception {
	        return super.authenticationManager();
	    }
	 
	    /**
	     * 描述：csrf().disable()为了关闭跨域访问的限制，若不关闭则websocket无法与后台进行连接
	     * @param http
	     * @throws Exception
	     */
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.headers().frameOptions().disable();
	        http.csrf().disable() // 关闭csrf防护
	        		.authorizeRequests()// 定义哪些URL需要被保护、哪些不需要被保护
	        		.antMatchers("/register/**","/register","/about","/error").permitAll()
	                .anyRequest().authenticated()  // 任何请求,登录后可以访问
	                .and()
	                .formLogin()  //  定义当需要用户登录时候，转到的登录页面。
	                .loginPage("/login")// 设置登录页面
	                .defaultSuccessUrl("/main")
	                .failureUrl("/login?error=true")
	                .permitAll() // 设置所有人都可以访问登录页面
	                .and()
	                .logout()
	                .logoutSuccessUrl("/login").
	                permitAll();
	    }
}
