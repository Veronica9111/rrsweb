package com.wisdom.permission.service;

import java.util.List;
import java.util.Map;

import com.wisdom.common.model.Permission;
import com.wisdom.common.model.Role;

public interface IPermissionService {

	//Add
		public boolean addPermission(Permission permission);
		//Delete
		public boolean deletePermission(String name);
		//Select All
		public Map<String,String> getAllPermission();
		//Select By name
		public Map<String,String> getPermissionByName(String name);
		//get Permission by  role
		public List<Map<String,String>> getPermissionByRole(String string);
		
		public boolean updatePermission(Integer id, String name, String invokeName);
}
