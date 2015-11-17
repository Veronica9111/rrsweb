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
	
	@RequestMapping("/role/getAllRoles")
	@ResponseBody
	public Map<String, String>getAllRoles(HttpServletRequest request){
		Map<String,String>retMap = new HashMap<>();
		List<String>roles = roleService.getAllRoles();
		retMap.put("data", roles.toString());
		return retMap;
	}
	
	@RequestMapping("/role/deleteRole")
	@ResponseBody
	public Map<String,String> deleteRole(HttpServletRequest request){
		String roleName = request.getParameter("name");
		Map<String, String>retMap = new HashMap<>();
		if(roleService.deleteRoleByName(roleName)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/role/updateRole")
	@ResponseBody
	public Map<String, String>updateRole(HttpServletRequest request){
		String oldRoleName = request.getParameter("oldName");
		String newRoleName = request.getParameter("newName");
		Map<String, String>retMap = new HashMap<>();
		if(roleService.updateRole(oldRoleName, newRoleName)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/role/addPermissionToRole")
	@ResponseBody
	public Map<String, String>addPermissionToRole(HttpServletRequest request){
		Map<String, String>retMap = new HashMap<>();
		return retMap;
	}
	
	@RequestMapping("/role/removePermissionT=FromRole")
	@ResponseBody
	public Map<String, String>removePermissionFromRole(HttpServletRequest request){
		Map<String, String>retMap = new HashMap<>();
		return retMap;
	}
}

