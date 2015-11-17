package com.wisdom.role.service;

import java.util.List;

import com.wisdom.common.model.Role;

public interface IRoleService {

	Boolean addRole(String roleName, List<String>permissionNames);
	
	List<String> getAllRoles();
	
	Boolean deleteRoleByName(String roleName);
	
	Boolean updateRole(String oldRoleName, String newRoleName);
	
	Boolean addPermissionToRole(String roleName, List<String>permissionNames);
	
	Boolean removePermissionFromRole(String roleName, List<String>permissionNames);
}
