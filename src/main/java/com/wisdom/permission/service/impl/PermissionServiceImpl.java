package com.wisdom.permission.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.common.mapper.PermissionMapper;
import com.wisdom.common.mapper.UserMapper;
import com.wisdom.common.model.Permission;
import com.wisdom.common.model.Role;
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
	/*
	 * Insert
	 * 	 * @see com.wisdom.permission.service.IPermissionService#addPermission(com.wisdom.common.model.Permission)
	 */
	@Override
	public boolean addPermission(Permission permission) {
		try {
			permissionMapper.addPermission(permission);
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
/*
 * Delete
 *  * @see com.wisdom.permission.service.IPermissionService#deletePermission(com.wisdom.common.model.Permission)
 */
	@Override
	public boolean deletePermission(String name) {
		try{
			Permission permission = permissionMapper.getPermissionByName(name);
			permissionMapper.deletePermission(name);
			permissionMapper.deletePermissionFromRoles(permission.getId());
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	/*
	 * Get AllPermission
	 * @see com.wisdom.permission.service.IPermissionService#getAllPermission()
	 */
	@Override
	public Map<String, String> getAllPermission() {
		Map<String, String> retMap = new HashMap<>();
		List<Permission>permissions = permissionMapper.getAllPermission();
		
		for(Permission permission: permissions){
			retMap.put(permission.getId().toString(), permission.getName());
		}
		
		return retMap;
	}
/*
 * Get Permission ByName
 * @see com.wisdom.permission.service.IPermissionService#getPermissionByName(java.lang.String)
 */
	@Override
	public Map<String, String> getPermissionByName(String name) {
		Permission permission =permissionMapper.getPermissionByName(name);
		Map<String, String> retMap = new HashMap<>();
		retMap.put("id", permission.getId().toString());
		retMap.put("name", permission.getName());
		retMap.put("invoke_name", permission.getInvokeName());

		return retMap;
	}
/*
 * Get Permission By RoleName
 * @see com.wisdom.permission.service.IPermissionService#getPermissionByRole(com.wisdom.common.model.Role)
 */
	@Override
	public List<Map<String, String>> getPermissionByRole(String name) {
		List<Permission>pList=permissionMapper.getPermissionByRole(name);
		List<Map<String, String>> gapList = new ArrayList<>();
		Map newMap = new HashMap<>();
		for(Permission per:pList){
			newMap.put(per.getId(), per.getName());
		}
		gapList.add(newMap);
		return gapList;
	}
	
	@Override
	public boolean updatePermission(Integer id, String name, String invokeName) {
		try{
			permissionMapper.updatePermission(id, name, invokeName);
		}catch(Exception e){
			return false;
		}
		return true;
	}

}
