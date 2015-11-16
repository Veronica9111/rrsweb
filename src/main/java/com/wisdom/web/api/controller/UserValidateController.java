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
import com.wisdom.common.model.Test;



import com.wisdom.common.mapper.TestMapper;

@Controller
public class UserValidateController {
	private TestMapper testMapper;
	
	@Autowired
	private IUserService userService;


    
	private static final Logger logger = LoggerFactory
			.getLogger(UserValidateController.class);
	
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
	
}
