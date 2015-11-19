package com.wisdom.user.service.impl;



import java.security.NoSuchAlgorithmException;
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
import com.wisdom.common.model.User;
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
	public Boolean checkUserValidate(String id, String password) {
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
				return true;
			}
		}
		else {
			try{
				user = userMapper.getUserById(Integer.parseInt(id));
			}
			catch(NumberFormatException e){
				logger.debug("The input user id is neither correct mail nor id");
				return false;
			}
			if (user.getPassword().equals(cryptedPassword)){
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean addUser(String name, String company) {
		String cryptedPassword = null;
		try {
			cryptedPassword = GenerateMD5.generateMD5("123456");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer active = 0;
		try {
			userMapper.addUser(name, cryptedPassword, company,active);
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
			tmp.put("active", Boolean.toString(user.getActive()));
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
			tmp.put("active", Boolean.toString(u.getActive()));
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
			tmp.put("active", Boolean.toString(u.getActive()));
			tmp.put("company", u.getCompany());
			retList.add(tmp);
		}
		return retList;
	}

	@Override
	public boolean addRoleToUser(Integer uId, String pName) {
		try{
			userMapper.addRoleToUser(uId, pName);
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
			tmp.put("active", Boolean.toString(u.getActive()));
			tmp.put("company", u.getCompany());
			retList.add(tmp);
		}
		return retList;
	}

	@Override
	public boolean updateUser(Integer id, String email, String name, String company) {
		try{
			userMapper.updateUser(name, email, company, id);
		}catch(Exception e){
			return false;
		}
		return true;
			
	}

	@Override
	public boolean updateUserPassword(Integer id, String oldPassword, String newPassword) {
		String oldPass=userMapper.booleanUserOldPassword(id);
		if(oldPassword==oldPass){
			try{
				userMapper.updateUserPassword(id, newPassword);
			}catch(Exception e){
				return false;
			}
			return true;
		}else{
			return false;
		}
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

}
