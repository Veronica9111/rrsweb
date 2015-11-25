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

import com.wisdom.common.model.Permission;
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
		Permission permission=new Permission();
		permission.setInvokeName(request.getParameter("invoke_name"));
		permission.setName(request.getParameter("name"));
		if (permissionService.addPermission(permission)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/permission/deletePermission")
	@ResponseBody
	public Map<String, String> deletePermission(HttpServletRequest request){
		logger.debug("enter addPermission");
		Map<String, String> retMap = new HashMap<>();
		String name = request.getParameter("name");
		if (permissionService.deletePermission(name)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	@RequestMapping("/permission/getAllPermission")
	@ResponseBody
	public Map<String, String> getAllPermission(HttpServletRequest request){
		logger.debug("enter addPermission");
		Map<String, String> retMap = new HashMap<>();
		List<Map<String,String>> list=new ArrayList<>();
		Permission permission=new Permission();
		list=permissionService.getAllPermission();
		Map<String, String> mMap=new HashMap<>();
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
			mMap=list.get(i);
		}
		if (mMap!=null){
			mMap.put("status", "ok");
		}
		else{
			mMap.put("status", "nok");
		}
		return mMap;
	}
	@RequestMapping("/permission/getPermissionByName")
	@ResponseBody
	public Map<String, Map<String, String>> getPermissionByName(HttpServletRequest request){
		logger.debug("enter addPermission");
		Map<String, Map<String, String>> retMap = new HashMap<>();
		String name=request.getParameter("name");
		Map<String, String>permission = permissionService.getPermissionByName(name);
		retMap.put("data", permission);
		return retMap;
	}
	
	@RequestMapping("/permission/getPermissionsByRole")
	@ResponseBody
	public Map<String, String> getPermissionsByRole(HttpServletRequest request){
		logger.debug("enter addPermission");
		Map<String, String> retMap = new HashMap<>();
		List<Map<String,String>> list=new ArrayList<>();
		String name=request.getParameter("name");
		list=permissionService.getPermissionByRole(name);
		Map<String, String> mMap=new HashMap<>();
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
			mMap=list.get(i);
		}
		if (mMap!=null){
			mMap.put("status", "ok");
		}
		else{
			mMap.put("status", "nok");
		}
		return mMap;
	}

}
