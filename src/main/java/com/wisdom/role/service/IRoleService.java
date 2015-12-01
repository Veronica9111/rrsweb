package com.wisdom.role.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wisdom.common.model.Role;
import com.wisdom.common.model.UserRole;

public interface IRoleService {

	Boolean addRole(String roleName, String[] permissionNames);
	
	Map<String, String> getAllRoles();
	
	Boolean deleteRoleByName(String roleName);
	
	Boolean updateRole(String oldRoleName, String newRoleName);
	
	Boolean addPermissionToRole(String roleName, String permissionName);
	
	Boolean removePermissionFromRole(String roleName, String permissionName);
	
	boolean addRolePermission(String roleName,List<String>permissionNames);
	
	 List<String> getUserRoles(Integer uid);
	 
	 Boolean updateRolePermissions(String roleName, String[] permissionNames);
	 
	 Map<String, List<String>> getAllRolesPermissions();
}
