package com.csdn.demo.sys.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csdn.demo.sys.entity.User;
import com.csdn.demo.sys.entity.UserAssociateRole;

import com.csdn.demo.sys.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping(value="/register/insertLogin",method=RequestMethod.POST)
	public String insertLogin(@ModelAttribute("user") User user,@ModelAttribute("role") UserAssociateRole role){
		try{
			userService.insertLogin(user,role);
		}catch(Exception e){
			e.printStackTrace();
			return "redirect:../error";
		}
		return "redirect:../about";
	}
	@RequestMapping(value = "/save",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> save(User user){
		try{
			userService.save(user);
		}catch(Exception e){
			e.printStackTrace();
		}
		Map<String,Object> result = new HashMap<String, Object>();
	    result.put("success",true);
	    result.put("msg","增加数据成功！");
		return result;
	}
}
