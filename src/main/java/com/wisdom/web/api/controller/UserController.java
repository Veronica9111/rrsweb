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
import com.wisdom.common.model.User;
import com.wisdom.role.service.IRoleService;
import com.wisdom.common.mapper.TestMapper;

@Controller
public class UserController {
	private TestMapper testMapper;
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;

    
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
	public Map<String, String> login(HttpSession httpSession, HttpServletRequest request){
		logger.debug("enter Login");
		Map<String, String> retMap = new HashMap<>();
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		if (id==null || id.equals("")||password == null || password.equals("")){
			retMap.put("status", "nok");
			return retMap;
		}
		Integer uid = userService.checkUserValidate(id, password);
		if(uid != 0){
			retMap.put("status", "ok");
			List<String> roles = roleService.getUserRoles(uid);
			if (roles.contains("管理员")){
				retMap.put("url", "/views/webviews/user/manage.html");
			}else{
				retMap.put("url", "/views/webviews/user/setting.html");
			}
			httpSession.setAttribute(SessionConstant.SESSION_USER_ID, uid);
		}
		else {
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession httpSession, HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		try {
			httpSession.removeAttribute(SessionConstant.SESSION_USER_ID);
			retMap.put("status", "ok");
		}catch(Exception e){
			retMap.put("status", "nok");
		}
		return "redirect:/views/frontviews/index.html";
	}
	
	@RequestMapping("/user/addUser")
	@ResponseBody
	public Map<String, String> addUser(HttpServletRequest request){
		logger.debug("enter addUser");
		Map<String, String> retMap = new HashMap<>();
		String name = request.getParameter("name");
		String company = request.getParameter("company");
		String rolesStr = request.getParameter("role");
		String[] roles = rolesStr.split(",");
		Integer uid = userService.addUser(name, company, roles);
		if( uid != -1){
			retMap.put("status", "ok");
			retMap.put("uid", uid.toString());
			
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/user/deleteUser")
	@ResponseBody
	public Map<String, String>deleteUser(HttpSession httpSession, HttpServletRequest request){
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Map<String, String> retMap = new HashMap<>();
		Boolean result = userService.isUserValidForPermission(uid, "manage all users");
		if(!result){
			retMap.put("status", "nok");
			return retMap;
		}
		Integer deleteUID = Integer.parseInt(request.getParameter("uid"));
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
		String company=request.getParameter("company");
		Map<String, String> retMap = new HashMap<>();
		List<Map<String, String>>uList=userService.getUsersByCompany(company);
		String data = JSONArray.fromObject(uList).toString();
		retMap.put("data", data);
		return retMap;
	}
	
	@RequestMapping("user/getUsersByActive")
	@ResponseBody
	public Map<String, String>getUsersByActive(HttpServletRequest request){
		String active=request.getParameter("active");
		List<Map<String, String>>uList=userService.getUsersByActive(active);
		Map<String, String>retMap = new HashMap<>();
		String data = JSONArray.fromObject(uList).toString();
		retMap.put("data", data);
		return retMap;
	}
	
	// name email company id
	@RequestMapping("/user/updateUser")
	@ResponseBody
	public Map<String, String>updateUser(HttpSession httpSession, HttpServletRequest request){
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		String email=request.getParameter("email");
		String name=request.getParameter("name");
		String company=request.getParameter("company");
		boolean boo=userService.updateUser(uid,email,name,company);
		Map<String, String>retMap = new HashMap<>();
		if (boo){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	//id oldpassword new password
	@RequestMapping("/user/updateUserPassword")
	@ResponseBody
	public Map<String, String>updateUserPassword(HttpSession httpSession, HttpServletRequest request){
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		String oldPassword=request.getParameter("oldPassword");
		String newPassword=request.getParameter("newPassword");
		Map<String, String>retMap = new HashMap<>();
		boolean boo=userService.updateUserPassword(uid,oldPassword,newPassword);
		if (boo){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	//uid 0-->1
	@RequestMapping("/user/activateUser")
	@ResponseBody
	public Map<String, String>activateUser(HttpSession httpSession, HttpServletRequest request){
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Integer id = Integer.parseInt(request.getParameter("id"));
		Map<String, String> retMap = new HashMap<>();
		Boolean result = userService.isUserValidForPermission(uid, "manage all users");
		if(!result){
			retMap.put("status", "nok");
			return retMap;
		}
		boolean boo=userService.activateUser(id);
		if (boo){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	//1-->0
	@RequestMapping("/user/deactivateUser")
	@ResponseBody
	public Map<String, String>deactivateUser(HttpServletRequest request){
		Integer id=Integer.parseInt(request.getParameter("id"));
		Map<String, String>retMap = new HashMap<>();
		boolean boo=userService.deactivateUser(id);
		if (boo){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/user/getAllUsersWithWorkRecords")
	@ResponseBody
	public Map<String, String> getAllUsersWithWorkRecords(HttpSession httpSession, HttpServletRequest request){
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Map<String, String> retMap = new HashMap<>();
		Boolean result = userService.isUserValidForPermission(uid, "manage all users");
		if(!result){
			retMap.put("status", "nok");
			return retMap;
		}
		List<List<String>> records = userService.getAllUsersWithWorkRecords();
		String data = JSONArray.fromObject(records).toString();
		retMap.put("data", data);
		retMap.put("status", "ok");
		return retMap;
	}
	
	@RequestMapping("/user/getUserWithWorkRecords")
	@ResponseBody
	public Map<String, String> getUserWithWorkRecords(HttpSession httpSession, HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Boolean result = userService.isUserValidForPermission(uid, "visit page");
		if(!result){
			retMap.put("status", "nok");
			return retMap;
		}
		List<String> record = userService.getUserWithWorkRecords(uid);
		String data = JSONArray.fromObject(record).toString();
		retMap.put("data", data);
		retMap.put("status", "ok");
		return retMap;
	}
	
	@RequestMapping("/user/getUserRoles")
	@ResponseBody	
	public Map<String, String>getUserRoles(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		Integer uid = Integer.parseInt(request.getParameter("id"));
		List<String> roles = roleService.getUserRoles(uid);
		String data = JSONArray.fromObject(roles).toString();
		retMap.put("data", data);
		return retMap;
	}
	
	@RequestMapping("/user/getUserById")
	@ResponseBody
	public Map<String, Map<String, String>>getUserById(HttpServletRequest request){
		Map<String, Map<String, String>> retMap = new HashMap<>();
		Integer uid = Integer.parseInt(request.getParameter("id"));
		Map<String, String> result = userService.getUserById(uid);
		String data = JSONArray.fromObject(result).toString();
		retMap.put("data", result);
		return retMap;
	}
	
	@RequestMapping("/user/generateNewPassword")
	@ResponseBody
	public Map<String, String>generateNewPassword(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		Integer uid = null;
		try{
			uid = Integer.parseInt(request.getParameter("uid"));
		}catch(Exception e){
			retMap.put("status", "nok");
			retMap.put("message", "员工号输入错误！");
		}

		retMap = userService.generateNewPassword(uid);
		return retMap;
	}
	
	@RequestMapping("/user/updateUserByAdmin")
	@ResponseBody
	public Map<String, String>updateUserByAdmin(HttpSession httpSession, HttpServletRequest request){
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Map<String, String> retMap = new HashMap<>();
		Boolean result = userService.isUserValidForPermission(uid, "manage all users");
		if(!result){
			retMap.put("status", "nok");
			return retMap;
		}
		Integer id = Integer.parseInt(request.getParameter("id"));
		String email=request.getParameter("email");
		String name=request.getParameter("name");
		String company=request.getParameter("company");
		boolean boo=userService.updateUser(id,email,name,company);
		if (boo){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/user/updateUserRoles")
	@ResponseBody
	public Map<String, String>updateUserRoles(HttpSession httpSession, HttpServletRequest request){
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Integer id = Integer.parseInt(request.getParameter("id"));
		String[] roles = request.getParameter("roles").split(",");
		Map<String, String> retMap = new HashMap<>();
		Boolean result = userService.isUserValidForPermission(uid, "manage all users");
		if(!result){
			retMap.put("status", "nok");
			return retMap;
		}
		if(userService.updateUserRoles(id, roles)){
			retMap.put("status", "ok");
		}else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/user/getRoles")
	@ResponseBody	
	public Map<String, List<String>>getUserRoles(HttpSession httpSession, HttpServletRequest request){
		Map<String, List<String>> retMap = new HashMap<>();
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		List<String> roles = roleService.getUserRoles(uid);
		retMap.put("data", roles);
		return retMap;
	}
}
