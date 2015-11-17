package com.wisdom.permission.service;

import java.util.List;
import java.util.Map;

import com.wisdom.common.model.Permission;

public interface IPermissionService {

	//Add
		public boolean addPermission(Permission permission);
		//Delete
		public boolean deletePermission(Permission permission);
		//Select All
		public List<Map<String,String>> getAllPermission();
		//Select By name
		public List<Map<String,String>> getPermissionByName(String name);
}
