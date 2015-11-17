package com.wisdom.web.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisdom.role.service.IRoleService;



@Controller
public class RoleController {

	@Autowired IRoleService roleService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(RoleController.class);
	
	
	@RequestMapping("/role/addRole")
	@ResponseBody
	public Map<String, String> addRole(HttpServletRequest request){
		logger.debug("enter addRole");
		Map<String, String> retMap = new HashMap<>();
		String name = request.getParameter("name");
		List<String>permissionList = new ArrayList<>();
		permissionList.add("permission");
		if (roleService.addRole(name, permissionList)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
}

