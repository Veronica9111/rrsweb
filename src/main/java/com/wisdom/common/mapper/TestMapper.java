package com.wisdom.common.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wisdom.common.model.Test;

public interface TestMapper {
  @Select("SELECT * FROM test WHERE id = #{testId}")
  Test getUser(@Param("testId") Integer testId);
} 