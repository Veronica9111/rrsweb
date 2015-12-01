package com.wisdom.web.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisdom.role.service.IRoleService;
import com.wisdom.user.service.IUserService;
import com.wisdom.utils.SessionConstant;

import net.sf.json.JSONArray;



@Controller
public class RoleController {

	@Autowired IRoleService roleService;
	
	@Autowired IUserService userService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(RoleController.class);
	
	
	@RequestMapping("/role/addRole")
	@ResponseBody
	public Map<String, String> addRole(HttpServletRequest request){
		logger.debug("enter addRole");
		Map<String, String> retMap = new HashMap<>();
		String name = request.getParameter("name");
		String permission = request.getParameter("permissions");
		String[] permissions = permission.split(",");
		if (roleService.addRole(name, permissions)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/role/getAllRoles")
	@ResponseBody
	public Map<String, Map<String, String>>getAllRoles(HttpSession httpSession, HttpServletRequest request){
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Map<String, Map<String,String>> retMap = new HashMap<>();
		Boolean result = userService.isUserValidForPermission(uid, "visit page");
		Map<String, String>tmp = new HashMap<>();
		if(!result){
			tmp.put("message", "nok");
			retMap.put("status", tmp);
			return retMap;
		}
		Map<String,String>roles = roleService.getAllRoles();
		tmp.put("message", "ok");
		retMap.put("status", tmp);
		retMap.put("data", roles);
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
		boolean boo=false;
		Map<String, String>retMap = new HashMap<>();
		String roleName = request.getParameter("role_name");
		String permissionName = request.getParameter("permission_name");
		if(roleService.addPermissionToRole(roleName, permissionName)){
			retMap.put("status", "ok");
		}else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/role/removePermissionFromRole")
	@ResponseBody
	public Map<String, String>removePermissionFromRole(HttpServletRequest request){
		Map<String, String>retMap = new HashMap<>();
		String roleName=request.getParameter("role_name");
		String permissionName=request.getParameter("permission_name");
		if(roleService.removePermissionFromRole(roleName, permissionName)){
			retMap.put("status", "ok");
		}else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	/*
	@RequestMapping("/role/addRolePermission")
	@ResponseBody
	public Map<String, String>addRolePermission(HttpServletRequest request){
		Map<String, String>retMap = new HashMap<>();
		boolean boo=false;
		String rName=request.getParameter("role_name");
		String pList=request.getParameter("p_list");
		String[]pName=pList.split(";");
		List list=Arrays.asList(pName);
		boo=roleService.addRolePermission(rName, list);
		if(boo==true){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		
		return retMap;
	}
	*/
	
	@RequestMapping("/role/updateRolePermissions")
	@ResponseBody
	public Map<String, String>updateRolePermissions(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		String roleName = request.getParameter("roleName");
		String permissions = request.getParameter("permissions");
		String[] permissionNames = permissions.split(",");
		if(roleService.updateRolePermissions(roleName, permissionNames)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/role/getAllRolesPermissions")
	@ResponseBody
	public Map<String, List<String>>getAllRolesPermissions(HttpServletRequest request){
		Map<String, List<String>> retMap = new HashMap<>();
		retMap = roleService.getAllRolesPermissions();
		return retMap;
	}

}

