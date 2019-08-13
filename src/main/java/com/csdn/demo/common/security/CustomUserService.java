package com.csdn.demo.common.security;

import javax.inject.Inject;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.csdn.demo.sys.dao.UserDao;
import com.csdn.demo.sys.entity.User;

/**
 * 登陆逻辑重写类
 * @author 98790
 *
 */
@Component
public class CustomUserService implements UserDetailsService {
	@Inject
    private UserDao userDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user = userDao.findByLogin(username);
		 if(user == null){
			 throw new UsernameNotFoundException("用户名不存在");
		 }
		 if(user.getState().equalsIgnoreCase("0")){
	            throw new LockedException("用户账号被冻结，无法登陆请联系管理员！");
	        }
	        return user;
	}

}
