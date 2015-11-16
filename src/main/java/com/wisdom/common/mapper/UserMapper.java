package com.wisdom.common.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wisdom.common.model.User;

public interface UserMapper {

	
  User getUserById(Integer uid);
  
  User getUserByMail(String mail);
  
  void addUser(@Param("name")String name, @Param("password")String password, @Param("company")String company, @Param("active")Integer active);
  
} 