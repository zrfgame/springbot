package com.csdn.demo.sys.dao;

import com.csdn.demo.sys.entity.User;
import com.csdn.demo.sys.entity.UserAssociateRole;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value="用户信息")
public interface UserDao {
	/**
	 * 功能描述: 根据账号来获取用户信息
	 * @param login
	 * @return
	 */
	@ApiOperation(value="根据用户名查询用户信息")
	User findByLogin(String login);
	/**
	 * 添加用户
	 * @param user
	 */
	@ApiOperation(value="添加用户信息")
	int  insertLogin(User user);
	/**
	 * 添加设置用户权限中间表
	 * @param userAssociateRole
	 */
	@ApiOperation(value="添加用户中间表")
	void insertUserassociaterole(UserAssociateRole userAssociateRole);
	/**
	 * 
	 * @param user
	 */
	@ApiOperation(value="添加用户信息")
	void save(User user);
}
