package com.wisdom.common.mapper;

import org.apache.ibatis.annotations.Param;

public interface PermissionMapper {

	void addPermission(@Param("name")String name, @Param("invoke_name")String invokeName);
}
