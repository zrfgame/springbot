package com.csdn.demo.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csdn.demo.common.security.CustomPasswordEncoder;
import com.csdn.demo.sys.dao.UserDao;
import com.csdn.demo.sys.entity.User;
import com.csdn.demo.sys.entity.UserAssociateRole;
/***
 * 在 Spring 的 AOP 代理下，
 * 只有目标方法由外部调用，
 * 目标方法才由 Spring 生成的代理对象来管理，
 * 这会造成自调用问题。若同一类中的其他没有@Transactional 注解的方法内部调用有@Transactional 注解的方法，
 * 有@Transactional 注解的方法的事务被忽略，不会发生回滚。
 * @author 98790
 *
 */
@Service
public class UserService {
	@Autowired
	private UserDao userdao;
	@Autowired
	private CustomPasswordEncoder customPasswordEncoder;
	/**
	 * 事务传播机制:PROPAGATION_REQUIRED:如果没有事务则添加事务，如果方法被调用时，
	 * 调用方法的方法已经在一个事务中那么就加入到该事务，否则为自己创建一个新的事务。
	 * @param user
	 * @param associateRole
	 */
	@Transactional( propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
	public void insertLogin(User user,UserAssociateRole associateRole){
		try {
			String password=customPasswordEncoder.encode(user.getPassword());
			user.setPassword(password);
			int a=userdao.insertLogin(user);
			System.out.println("添加条数"+a);
			System.out.println("主键id:"+user.getId());
			UserAssociateRole userAssociateRole = new UserAssociateRole();
			userAssociateRole.setUserId(user.getId());
			userAssociateRole.setRoleId(associateRole.getRoleId());
			this.insertUserassociaterole(userAssociateRole);
			//throw new Exception("抛出异常");
		} catch (Exception e) {
			throw e;
			
		}
	}
	/**
	 * 添加设置用户权限中间表
	 * @param userAssociateRole
	 * 注册不可能是多个集合
	 */ 
	@Transactional( propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
	public void save(User user){
		try {
			String password=customPasswordEncoder.encode(user.getPassword());
			user.setPassword(password);
			userdao.save(user);
			UserAssociateRole userAssociateRole = new UserAssociateRole();
			userAssociateRole.setUserId(user.getId());
			userAssociateRole.setRoleId(user.getRoles().get(0).getId());
			this.savetUserassociaterole(userAssociateRole);
			//throw new Exception("抛出异常");
		} catch (Exception e) {
			throw e;
			
		}
	}
	@Transactional( propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
	public void savetUserassociaterole(UserAssociateRole userAssociateRole){
		try {
			userdao.insertUserassociaterole(userAssociateRole);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void insertUserassociaterole(UserAssociateRole userAssociateRole){
		try {
			userdao.insertUserassociaterole(userAssociateRole);
		} catch (Exception e) {
			throw e;
		}
	}
}
