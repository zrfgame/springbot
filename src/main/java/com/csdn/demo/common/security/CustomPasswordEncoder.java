package com.csdn.demo.common.security;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * spring-security登陆的密码进行SCrypt加密传到数据库
 * scrypt加密    莱特币也用的是SCrypt加密方式
 * @author 98790
 *密码加密类
 */
/***
 * 当然现在Md5已经被归入放弃的行列，当然被放弃的不但只是Md5,
 * 像ldap、MD4、SHA-1、SHA-256等都属于不建议使用的行列，
 * 现在已经是pbkdf2、bcrypt、scrypt的天下了。
 * 
 */
@Component
public class CustomPasswordEncoder implements PasswordEncoder {
	/**
	 * 加密
	 */
	@Override
	public String encode(CharSequence rawSequence) {
		PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
		return passwordEncoder.encode(rawSequence.toString());
	}
	/**
	 * 判断密码是否一致
	 */
	@Override
	public boolean matches(CharSequence charSequence, String password) {
		PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
		return passwordEncoder.matches(charSequence, password);
	}

}
