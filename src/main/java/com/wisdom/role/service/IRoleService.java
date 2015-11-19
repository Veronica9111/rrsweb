package com.wisdom.role.service;

import java.util.List;
import java.util.Map;

import com.wisdom.common.model.Role;

public interface IRoleService {

	Boolean addRole(String roleName, List<String>permissionNames);
	
	Map<String, String> getAllRoles();
	
	Boolean deleteRoleByName(String roleName);
	
	Boolean updateRole(String oldRoleName, String newRoleName);
	
	Boolean addPermissionToRole(String roleName, List<String>permissionNames);
	
	Boolean removePermissionFromRole(String roleName, List<String>permissionNames);
	
	boolean addRolePermission(String roleName,List<String>permissionNames);
}
