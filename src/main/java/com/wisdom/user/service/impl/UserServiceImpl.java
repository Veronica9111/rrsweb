package com.wisdom.user.service.impl;



import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.user.service.IUserService;
import com.wisdom.web.api.controller.UserController;
import com.wisdom.common.mapper.PermissionMapper;
import com.wisdom.common.mapper.RoleMapper;
import com.wisdom.common.mapper.TestMapper;
import com.wisdom.common.mapper.UserMapper;
import com.wisdom.common.model.Permission;
import com.wisdom.common.model.Role;
import com.wisdom.common.model.User;
import com.wisdom.common.model.UserRecord;
import com.wisdom.common.model.UserRole;
import com.wisdom.common.utils.JavaMail;
import com.wisdom.common.utils.JavaMailService;
import com.wisdom.utils.GenerateMD5;

@Service("userService")
public class UserServiceImpl implements IUserService{

	  @Autowired
	  private UserMapper userMapper;
	  
	  @Autowired
	  private RoleMapper roleMapper;
	  
	  @Autowired
	  private PermissionMapper permissionMapper;
	  
	  @Autowired
	  private JavaMailService javaMailService;


    
	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);
	
	public void setUserMapper(UserMapper userMapper) {
		    this.userMapper = userMapper;
	}
	
	@Override
	public Integer checkUserValidate(String id, String password) {
		// TODO Auto-generated method stub
		//String cryptedPassword = DigestUtils.md5(password).toString();
		String cryptedPassword = null;
		try {
			cryptedPassword = GenerateMD5.generateMD5(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		User user = userMapper.getUserByMail(id);
		if(user != null){
			if (user.getPassword().equals(cryptedPassword)){
				return user.getId();
			}
		}
		else {
			try{
				user = userMapper.getUserById(Integer.parseInt(id));
			}
			catch(NumberFormatException e){
				logger.debug("The input user id is neither correct mail nor id");
				return 0;
			}
			if (user != null && user.getPassword().equals(cryptedPassword)){
				return user.getId();
			}
		}
		return 0;
	}

	@Override
	public Integer addUser(String name, String company, String[] roles) {
		String cryptedPassword = null;
		Integer id = null;
		try {
			cryptedPassword = GenerateMD5.generateMD5("123456");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer active = 0;
		try {
			User user = new User();
			user.setCompany(company);
			user.setName(name);
			user.setPassword(cryptedPassword);
			user.setActive(0);
			Integer idtemp = userMapper.addUser(user);
			id = user.getId();
			for(String roleName: roles){
				userMapper.addRoleToUser(id, roleName);
			}
		}
		catch(Exception e){
			return -1;
		}
		return id;
	}

	@Override
	public Boolean deleteUser(Integer id) {
		try{
			userMapper.deleteUserById(id);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public List<UserRole> getAllUsersWithRoles() {
		
		List<UserRole> result = userMapper.getAllUsersWithRoles();
		return result;
	}

	@Override
	public List< Map<String, String>> getAllUsers() {
		List<User> users = userMapper.getAllUsers();
		List<Map<String, String>> retList = new ArrayList<>();
		for(User user:users){
			Map<String, String> tmp = new HashMap<>();
			tmp.put("id", Integer.toString(user.getId()));
			tmp.put("name", user.getName());
			tmp.put("mail", user.getMail());
			tmp.put("active", user.getActive().toString());
			tmp.put("company", user.getCompany());
			retList.add(tmp);
		}
		return retList;
	}

	@Override
	public List<Map<String, String>> getUsersByCompany(String company) {
		List<User> users = userMapper.getUsersByCompany(company);
		List<Map<String, String>> retList = new ArrayList<>();
		for(User u:users){
			Map<String, String> tmp = new HashMap<>();
			tmp.put("id", Integer.toString(u.getId()));
			tmp.put("name", u.getName());
			tmp.put("mail", u.getMail());
			tmp.put("active", u.getActive().toString());
			tmp.put("company", u.getCompany());
			retList.add(tmp);
		}
		return retList;
	}

	@Override
	public List<Map<String, String>> getUsersByActive(String active) {
		List<User> users = userMapper.getUsersByActive(active);
		List<Map<String, String>> retList = new ArrayList<>();
		for(User u:users){
			Map<String, String> tmp = new HashMap<>();
			tmp.put("id", Integer.toString(u.getId()));
			tmp.put("name", u.getName());
			tmp.put("mail", u.getMail());
			tmp.put("active", u.getActive().toString());
			tmp.put("company", u.getCompany());
			retList.add(tmp);
		}
		return retList;
	}

	@Override
	public boolean addRoleToUser(Integer uId, String rName) {
		try{
			userMapper.addRoleToUser(uId, rName);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public boolean removeRoleFromUser(Integer uId, String pName) {
		try{
			userMapper.addRoleToUser(uId, pName);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public List<Map<String, String>> getUsersByPname(String pName) {
		List<User> users = userMapper.getUsersByPname(pName);
		List<Map<String, String>> retList = new ArrayList<>();
		for(User u:users){
			Map<String, String> tmp = new HashMap<>();
			tmp.put("id", Integer.toString(u.getId()));
			tmp.put("name", u.getName());
			tmp.put("mail", u.getMail());
			tmp.put("active", u.getActive().toString());
			tmp.put("company", u.getCompany());
			retList.add(tmp);
		}
		return retList;
	}

	@Override
	public boolean updateUser(Integer id, String email, String name, String company) {
		try{
			userMapper.updateUser(id, name, email, company);
		}catch(Exception e){
			return false;
		}
		return true;
			
	}

	@Override
	public boolean updateUserPassword(Integer id, String oldPassword, String newPassword) {
		Integer uid = checkUserValidate(id.toString(), oldPassword);
		if (id.equals(checkUserValidate(id.toString(), oldPassword))){
			
			try{
				String cryptedPassword = GenerateMD5.generateMD5(newPassword);
				userMapper.updateUserPassword(id, cryptedPassword);
				return true;
			}catch(Exception e){
				return false;
			}
			
		}
		return false;
	}

	@Override
	public boolean activateUser(Integer id) {
		try{
			userMapper.updateUserActiveStatus(id, 1);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public boolean deactivateUser(Integer id) {
		try{
			userMapper.updateUserActiveStatus(id, 0);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public List<List<String>> getAllUsersWithWorkRecords() {
		List<List<String>> retList = new ArrayList<>();
		List<UserRecord> result = userMapper.getAllUsersWithWorkRecords();
		for(UserRecord record:result){
			List<String> tempList = new ArrayList<>();
			tempList.add(record.getId().toString());
			tempList.add(record.getName());
			String mail = "";
			if(record.getMail() != null){
				mail = record.getMail().toString();
			}
			tempList.add(mail);
			tempList.add(record.getCompany());
			tempList.add(record.getRecognize().toString());
			tempList.add(record.getInspect().toString());
			String activate = "";
			if(record.getActive() == 0){
				tempList.add("未激活");
				activate = "<input type='button' value='激活' class='activate' id='activate-" + record.getId().toString() + "'/>";
			}else{
				tempList.add("已激活");
				activate = "<input type='button' disabled='disabled' value='激活' class='activate' id='activate-" + record.getId().toString() + "'/>";
			}
			String edit = "<input type='button' value='编辑' class='edit' id='edit-"+ record.getId().toString() +"'/>";
			String delete = "<input type='button' value='删除' class='delete' id='delete-"+ record.getId().toString() +"'/>";
			tempList.add(activate + edit + delete);
			retList.add(tempList);
		}
		return retList;
	}

	@Override
	public List<String> getUserWithWorkRecords(Integer uid) {
		List<String> retList = new ArrayList<>();
		UserRecord record = userMapper.getUserWithWorkRecord(uid);
		retList.add(record.getId().toString());
		retList.add(record.getName());
		retList.add(record.getCompany());
		retList.add(record.getMail());
		retList.add(record.getRecognize().toString());
		retList.add(record.getInspect().toString());
		return retList;
	}

	@Override
	public Map<String, String> getUserById(Integer uid) {
		Map<String, String> retMap = new HashMap<>();
		User user = userMapper.getUserById(uid);
		if(user != null){
			retMap.put("id", user.getId().toString());
			retMap.put("name", user.getName());
			retMap.put("mail", user.getMail());
			retMap.put("company", user.getCompany());
			retMap.put("active", user.getActive().toString());
		}
		return retMap;
	}

	@Override
	public Boolean isUserValidForPermission(Integer uid, String permissionInvokeName) {
		List<Permission> permissions = permissionMapper.getPermissionsByUser(uid);
		List<Object> check = new ArrayList<>();
		check.add(null);
		if (permissions.equals(check)){
			return false;
		}
		for(Permission permission: permissions){
			if(permission.getInvokeName().equals(permissionInvokeName)){
				return true;
			}
		}
		return false;
	}

	@Override
	public Map<String, Integer> getUsersCurrentWork(String role) {
		Map<String, Integer> retMap = new HashMap<>();
		List<User> users = userMapper.getUsersCurrentWork(role);
		for(User user: users){
			if(retMap.containsKey(user.getId().toString() + "," + user.getName())){
				Integer count = retMap.get(user.getId().toString() + "," + user.getName());
				count += 1;
				retMap.replace(user.getId().toString() + "," + user.getName(), count);
			}else{
				retMap.put(user.getId().toString() + "," + user.getName(), 1);
			}
		}
		return retMap;
	}

	@Override
	public Map<String, String> generateNewPassword(Integer uid) {
		Map<String, String> retMap = new HashMap<>();
		//Check if the mail address is available
		User user = userMapper.getUserById(uid);
		if(user.getMail() != null && !user.getMail().equals("")){
			Integer max = 999999;
			Integer min = 100000;
			String cryptedPassword = "";
			Integer password = (int) Math.round(Math.random() * (max - min + 1) + min);
			try {
				cryptedPassword = GenerateMD5.generateMD5(password.toString());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				retMap.put("status", "nok");
				retMap.put("message", "密码生成失败，请稍后再试！");
				return retMap;
			}
			// Update password
			userMapper.updateUserPassword(uid, cryptedPassword);
			//Send out mail
			Boolean result = javaMailService.sendMailOut(user.getMail(), "忘记密码", "密码"+password.toString(), "bbz@bangbangzhang.com");

			if(result){
				retMap.put("status", "ok");
				retMap.put("message", "密码发送成功，请查收邮箱！");
			}else{
				retMap.put("status", "nok");
				retMap.put("message", "密码发送失败，请稍后重试！");
			}
			
			
		}

		return retMap;
	}

	@Override
	public Boolean updateUserRoles(Integer uid, String[] roles) {
		List<Role> currentRoles = roleMapper.getUserRoles(uid);
		for (Role role: currentRoles){
			if(role == null){
				continue;
			}
			userMapper.removeRoleFromUser(uid, role.getName());
		}
		for(String role: roles){
			userMapper.addRoleToUser(uid, role);
		}
		return true;
	}



}
