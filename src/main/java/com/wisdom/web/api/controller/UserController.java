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
			httpSession.setAttribute(SessionConstant.SESSION_USER_ID, uid);
		}
		else {
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, String>logout(HttpSession httpSession, HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		try {
			httpSession.removeAttribute(SessionConstant.SESSION_USER_ID);
			retMap.put("status", "ok");
		}catch(Exception e){
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
		String rolesStr = request.getParameter("role");
		String[] roles = rolesStr.split(",");
		if(userService.addUser(name, company, roles)){
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
		String company=request.getParameter("company");
		List<Map<String, String>>uList=userService.getUsersByCompany(company);
		Map<String, String>retMap = new HashMap<>();
		for(int i=0;i<uList.size();i++){
			System.out.println(uList.get(i));
			retMap=uList.get(i);
		}
		if (retMap!=null){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("user/getUsersByActive")
	@ResponseBody
	public Map<String, String>getUsersByActive(HttpServletRequest request){
		String active=request.getParameter("active");
		List<Map<String, String>>uList=userService.getUsersByActive(active);
		Map<String, String>retMap = new HashMap<>();
		for(int i=0;i<uList.size();i++){
			System.out.println(uList.get(i));
			retMap=uList.get(i);
		}
		if (retMap!=null){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	//get Uid Pname
	@RequestMapping("/user/addRoleToUser")
	@ResponseBody
	public Map<String, String>addRoleToUser(HttpServletRequest request){
		boolean boo=false;
		Integer uId=Integer.parseInt(request.getParameter("userid"));
		String pName=request.getParameter("permissionName");
		Map<String, String>retMap = new HashMap<>();
		boo=userService.addRoleToUser(uId,pName);
		if (boo){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	//get Uid Pname
	@RequestMapping("/user/RemoveRoleFromUser")
	@ResponseBody
	public Map<String, String>removeRoleFromUser(HttpServletRequest request){
		boolean boo=false;
		Integer uId=Integer.parseInt(request.getParameter("userid"));
		String pName=request.getParameter("permissionName");
		Map<String, String>retMap = new HashMap<>();
		boo=userService.removeRoleFromUser(uId, pName);
		if (boo){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	//Pname
	@RequestMapping("/user/getUsersByPname")
	@ResponseBody
	public Map<String, String>getUsersByPname(HttpServletRequest request){
		String pName=request.getParameter("pname");
		List<Map<String, String>>uList=userService.getUsersByPname(pName);
		Map<String, String>retMap = new HashMap<>();
		for(int i=0;i<uList.size();i++){
			System.out.println(uList.get(i));
			retMap=uList.get(i);
		}
		if (retMap!=null){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
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
	public Map<String, String>activateUser(HttpServletRequest request){
		Integer id=Integer.parseInt(request.getParameter("id"));
		Map<String, String>retMap = new HashMap<>();
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
	public Map<String, String> getAllUsersWithWorkRecords(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		List<List<String>> records = userService.getAllUsersWithWorkRecords();
		String data = JSONArray.fromObject(records).toString();
		retMap.put("data", data);
		return retMap;
	}
	
	@RequestMapping("/user/getUserWithWorkRecords")
	@ResponseBody
	public Map<String, String> getUserWithWorkRecords(HttpSession httpSession, HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		List<String> record = userService.getUserWithWorkRecords(uid);
		String data = JSONArray.fromObject(record).toString();
		retMap.put("data", data);
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
}
