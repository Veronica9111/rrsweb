package com.wisdom.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wisdom.common.model.Permission;

public interface PermissionMapper {

	Integer addPermission(Permission permission);
	//Delete
	Integer deletePermission(@Param("name")String name);
	//Update
	void updatePermission(@Param("name")String name,@Param("id")Integer id,@Param("invoke_name")String invoke_name);
	//Select All Information
	List<Permission> getAllPermission();
	//Select Information By Name
	List<Permission> getPermissionByName(@Param("name")String name);
	//Select Information By Role
	List<Permission>getPermissionByRole(@Param("name")String name);
}
