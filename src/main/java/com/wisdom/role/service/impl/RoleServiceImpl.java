package com.wisdom.role.service.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.common.mapper.PermissionMapper;
import com.wisdom.common.mapper.RoleMapper;
import com.wisdom.common.mapper.UserMapper;
import com.wisdom.common.model.Role;
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

	@Override
	public List<String> getAllRoles() {
		List<Role> roles = new ArrayList<>();
		roles = roleMapper.getAllRoles();
		List<String> result = new ArrayList<>();
		for(Role role: roles){
			result.add(role.getName());
		}
		return result;
	}

	@Override
	public Boolean deleteRoleByName(String roleName) {
		try{
			roleMapper.deleteRoleByName(roleName);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public Boolean updateRole(String oldRoleName, String newRoleName) {
		Role role = roleMapper.getRoleByName(oldRoleName);
		try{
			roleMapper.updateRole(role.getId(), newRoleName);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public Boolean addPermissionToRole(String roleName, List<String> permissionNames) {
		//TODO
		/*for(String permissionName:permissionNames){
		}*/
		return null;
	}

	@Override
	public Boolean removePermissionFromRole(String roleName, List<String> permissionNames) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	


}