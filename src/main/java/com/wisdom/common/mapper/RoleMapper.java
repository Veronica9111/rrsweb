package com.wisdom.common.mapper;

import org.apache.ibatis.annotations.Param;

public interface RoleMapper {

	void addRole(@Param("name")String name);
}