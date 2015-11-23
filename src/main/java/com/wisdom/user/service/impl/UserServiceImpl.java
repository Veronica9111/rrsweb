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
import com.wisdom.common.mapper.TestMapper;
import com.wisdom.common.mapper.UserMapper;
import com.wisdom.common.model.Role;
import com.wisdom.common.model.User;
import com.wisdom.common.model.UserRecord;
import com.wisdom.common.model.UserRole;
import com.wisdom.utils.GenerateMD5;

@Service("userService")
public class UserServiceImpl implements IUserService{

	  @Autowired
	  private UserMapper userMapper;


    
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
	public Boolean addUser(String name, String company, String[] roles) {
		String cryptedPassword = null;
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
			Integer idtemp = userMapper.addUser(user);
			Integer id = user.getId();
			for(String roleName: roles){
				userMapper.addRoleToUser(id, roleName);
			}
		}
		catch(Exception e){
			return false;
		}
		return true;
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
			userMapper.activateUpdate(id, 1);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public boolean deactivateUser(Integer id) {
		try{
			userMapper.activateUpdate(id, 0);
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
			tempList.add(record.getCompany());
			tempList.add(record.getRecognize().toString());
			tempList.add(record.getInspect().toString());
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



}
