package com.wisdom.web.api.controller;

import java.io.Reader;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisdom.user.service.IUserService;
import com.wisdom.utils.SessionConstant;

import net.sf.json.JSONArray;

import com.wisdom.common.model.Test;



import com.wisdom.common.mapper.TestMapper;

@Controller
public class UserController {
	private TestMapper testMapper;
	
	@Autowired
	private IUserService userService;


    
	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);
	
	  public void setTestMapper(TestMapper testMapper) {
		    this.testMapper = testMapper;
		  }
	@RequestMapping("/test")
	@ResponseBody
	public Map<String, String> getAllRecommender() {
		logger.debug("enter getAllRecommender");
		Map<String, String> retMap = new HashMap<>();
		retMap.put("message", "ok");
		logger.debug("finish getAllRecommender");	
		Test result = testMapper.getUser(1);
		retMap.put("data", result.getName());
		return retMap;
	}
	
	
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, String> login(HttpServletRequest request){
		logger.debug("enter Login");
		Map<String, String> retMap = new HashMap<>();
		String uid = request.getParameter("id");
		String password = request.getParameter("password");
		if(userService.checkUserValidate(uid, password)){
			retMap.put("status", "ok");
		}
		else {
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/user/addUser")
	@ResponseBody
	public Map<String, String> addUser(HttpServletRequest request){
		logger.debug("enter addUser");
		Map<String, String> retMap = new HashMap<>();
		String name = request.getParameter("name");
		String company = request.getParameter("company");
		if(userService.addUser(name, company)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/user/deleteUser")
	@ResponseBody
	public Map<String, String>deleteUser(HttpServletRequest request){
		//Integer uid = Integer.parseInt((String) request.getSession().getAttribute(SessionConstant.SESSION_USER_ID));
		//TODO Permission check
		Integer deleteUID = Integer.parseInt(request.getParameter("uid"));
		Map<String, String> retMap = new HashMap<>();
		if(userService.deleteUser(deleteUID)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/user/getAllUsers")
	@ResponseBody
	public Map<String, String> getAllUsers(HttpServletRequest request){
		List<Map<String, String>> users = userService.getAllUsers();
		Map<String, String>retMap = new HashMap<>();
		String data = JSONArray.fromObject(users).toString();
		retMap.put("data", data);
		return retMap;
	}
	
	@RequestMapping("/user/getUsersByCompany")
	@ResponseBody
	public Map<String, String> getUsersByCompany(HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("user/getUsersByStatus")
	@ResponseBody
	public Map<String, String>getUsersByStatus(HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/user/addRoleToUser")
	@ResponseBody
	public Map<String, String>addRoleToUser(HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/user/RemoveRoleFromUser")
	@ResponseBody
	public Map<String, String>removeRoleFromUser(HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/user/getUsersByRole")
	@ResponseBody
	public Map<String, String>getUsersByRole(HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/user/updateUser")
	@ResponseBody
	public Map<String, String>updateUser(HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/user/updateUserPassword")
	@ResponseBody
	public Map<String, String>updateUserPassword(HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/user/activateUser")
	@ResponseBody
	public Map<String, String>activateUser(HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/user/deactivateUser")
	@ResponseBody
	public Map<String, String>deactivateUser(HttpServletRequest request){
		return null;
	}
}