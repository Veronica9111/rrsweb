package com.wisdom.user.service.impl;



import java.security.NoSuchAlgorithmException;
import java.util.List;

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

}
