package com.wisdom.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wisdom.common.model.Role;
import com.wisdom.common.model.RolePermissions;
import com.wisdom.common.model.UserRole;

public interface RoleMapper {

	Integer addRole(Role role);
	
	List<Role> getAllRoles();
	
	void deleteRoleByName(@Param("name")String name);
	
	void deleteAllPermissionsOfRole(@Param("id")Integer id);
	
	Role getRoleByName(@Param("name")String name);
	
	void updateRole(@Param("id")Integer id, @Param("name")String name);
	
	void addPermissionToRole(@Param("r_name")String role_name, @Param("p_name")String permission_name);
	
	void removePermissionFromRole(@Param("r_name")String role_name, @Param("p_name")String permission_name);	
	
	void addRolePermission(@Param("r_name")String roleName,@Param("p_name")String permissionName);
	
	  List<Role> getUserRoles(@Param("uid")Integer uid);
	  
	List<RolePermissions> getAllRolesPermissions();
}