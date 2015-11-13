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

import com.wisdom.common.model.Test;



import com.wisdom.common.mapper.TestMapper;

@Controller
public class UserValidateController {
    @Autowired
    private TestMapper testmapper;
    
	private static final Logger logger = LoggerFactory
			.getLogger(UserValidateController.class);
	
	
	@RequestMapping("/test")
	@ResponseBody
	public Map<String, String> getAllRecommender() {
		logger.debug("enter getAllRecommender");
		Map<String, String> retMap = new HashMap<>();
		retMap.put("message", "ok");
		logger.debug("finish getAllRecommender");
		
		List<Test> result = testmapper.getAllTests();
		retMap.put("data", result.toString());
        



	    

		return retMap;
	    
		
		
	}
	
}
