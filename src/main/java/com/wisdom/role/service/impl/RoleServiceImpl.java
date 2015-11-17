package com.wisdom.role.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.common.mapper.PermissionMapper;
import com.wisdom.common.mapper.RoleMapper;
import com.wisdom.common.mapper.UserMapper;
import com.wisdom.role.service.IRoleService;


@Service("roleService")
public class RoleServiceImpl implements IRoleService{

	  @Autowired
	  private	PermissionMapper permissionMapper;

	  @Autowired
	  private RoleMapper roleMapper;

  
	private static final Logger logger = LoggerFactory
			.getLogger(RoleServiceImpl.class);
	
	public void setPermissionMapper(PermissionMapper permissionMapper) {
		    this.permissionMapper = permissionMapper;
	}
	
	public void setRoleMapper(RoleMapper roleMapper){
		this.roleMapper = roleMapper;
	}

	@Override
	public Boolean addRole(String roleName, List<String> permissionNames) {
		
		try{
			roleMapper.addRole(roleName);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	


}