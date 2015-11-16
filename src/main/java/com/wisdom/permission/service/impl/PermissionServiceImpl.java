package com.wisdom.permission.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.common.mapper.PermissionMapper;
import com.wisdom.common.mapper.UserMapper;
import com.wisdom.permission.service.IPermissionService;

@Service("permissionService")
public class PermissionServiceImpl implements IPermissionService{

	  @Autowired
	  private	PermissionMapper permissionMapper;


  
	private static final Logger logger = LoggerFactory
			.getLogger(PermissionServiceImpl.class);
	
	public void setPermissionMapper(PermissionMapper permissionMapper) {
		    this.permissionMapper = permissionMapper;
	}
	
	@Override
	public Boolean addPermission(String name, String invokeName) {
		
		try{
			permissionMapper.addPermission(name, invokeName);
		}
		catch(Exception e){
			return false;
		}

		return true;
	}

}
