package com.wisdom.web.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisdom.permission.service.IPermissionService;

@Controller
public class PermissionController {

	@Autowired IPermissionService permissionService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(PermissionController.class);
	
	
	@RequestMapping("/permission/addPermission")
	@ResponseBody
	public Map<String, String> addPermission(HttpServletRequest request){
		logger.debug("enter addPermission");
		Map<String, String> retMap = new HashMap<>();
		String name = request.getParameter("name");
		String invoke_name = request.getParameter("invoke_name");	
		if (permissionService.addPermission(name, invoke_name)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
}
