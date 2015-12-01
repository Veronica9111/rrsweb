package com.wisdom.role.service.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.common.mapper.PermissionMapper;
import com.wisdom.common.mapper.RoleMapper;
import com.wisdom.common.mapper.UserMapper;
import com.wisdom.common.model.Permission;
import com.wisdom.common.model.Role;
import com.wisdom.common.model.RolePermissions;
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
	public Boolean addRole(String roleName, String[] permissionNames) {
		
		try{
			Role role = new Role();
			role.setName(roleName);
			roleMapper.addRole(role);
			for(String permissionName: permissionNames){
				roleMapper.addPermissionToRole(roleName, permissionName);
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public Map<String, String> getAllRoles() {
		List<Role> roles = new ArrayList<>();
		roles = roleMapper.getAllRoles();
		Map<String,String> result = new HashMap<>();
		for(Role role: roles){
			result.put(role.getId().toString(), role.getName());
		}
		return result;
	}

	@Override
	public Boolean deleteRoleByName(String roleName) {
		try{
			Role role = roleMapper.getRoleByName(roleName);
			roleMapper.deleteRoleByName(roleName);
			roleMapper.deleteAllPermissionsOfRole(role.getId());
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
	public Boolean addPermissionToRole(String roleName, String permissionName) {
		try{
			roleMapper.addPermissionToRole(roleName, permissionName);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public Boolean removePermissionFromRole(String roleName, String permissionName) {
		try{
			roleMapper.removePermissionFromRole(roleName, permissionName);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public boolean addRolePermission(String roleName, List<String> permissionNames) {
		if(permissionNames.size()==0 || roleName==null ){
			return false;
		}else if(permissionNames.size()==1) {
			roleMapper.addRolePermission(roleName, permissionNames.get(0));
			return true;
		}else{
			for(int i=0;i<permissionNames.size();i++){
				roleMapper.addRolePermission(roleName, permissionNames.get(i));
			}
			return true;
		}
	}

	@Override
	public List<String> getUserRoles(Integer uid) {
		List<Role> roles = roleMapper.getUserRoles(uid);
		List<String> retList = new ArrayList();
		if(roles == null){
			return retList;
		}
		for(Role role: roles){
			if(role != null){
				retList.add(role.getName());
			}
		}
		return retList;
	}

	@Override
	public Boolean updateRolePermissions(String roleName, String[] permissionNames) {
		try {
			Role role = roleMapper.getRoleByName(roleName);
			roleMapper.deleteAllPermissionsOfRole(role.getId());
			for(String permissionName: permissionNames){
				roleMapper.addPermissionToRole(roleName, permissionName);
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public Map<String, List<String>> getAllRolesPermissions() {
		Map<String, List<String>> retMap = new HashMap<>();
		/*List<RolePermissions> rolePermissions = roleMapper.getAllRolesPermissions();
		for (RolePermissions rolePermission: rolePermissions){
			List<Permission>permissions = rolePermission.getPermissions();
			List<String>temp = new ArrayList<>();
			for (Permission permission: permissions){
				temp.add(permission.getName());
			}
			retMap.put(rolePermission.getName(), temp);
		}*/
		List<Role> roles = roleMapper.getAllRoles();
		for (Role role: roles){
			List<Permission> permissions = permissionMapper.getPermissionByRole(role.getName());
			List<String> temp = new ArrayList<>();
			for(Permission permission: permissions){
				if(permission != null){
					temp.add(permission.getName());
				}
			}
			retMap.put(role.getName(), temp);
		}
		return retMap;
	}
	
	 
	


}