package com.wisdom.web.api.controller;

import java.util.ArrayList;
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

import com.wisdom.common.model.Permission;
import com.wisdom.permission.service.IPermissionService;
import com.wisdom.user.service.IUserService;
import com.wisdom.utils.SessionConstant;

import net.sf.json.JSONArray;

@Controller
public class PermissionController {

	@Autowired IPermissionService permissionService;
	
	@Autowired IUserService userService;
	
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
	public Map<String, String> getAllPermission(HttpSession httpSession, HttpServletRequest request){
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Map<String, String> retMap = new HashMap<>();
		Boolean result = userService.isUserValidForPermission(uid, "manage all users");
		if(!result){
			retMap.put("status", "nok");
			return retMap;
		}
		logger.debug("enter addPermission");
		

		Map<String, String> permissions = permissionService.getAllPermission();
		String data = JSONArray.fromObject(permissions).toString();
		retMap.put("data", data);
		retMap.put("status", "ok");
		return retMap;
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
	
	@RequestMapping("/permission/updatePermission")
	@ResponseBody
	public Map<String, String> updatePermission(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		Integer id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String invokeName = request.getParameter("invoke_name");
		if(permissionService.updatePermission(id, name, invokeName)){
			retMap.put("status", "ok");
		}else{
			retMap.put("status", "nok");
		}
		return retMap;
	}

}
