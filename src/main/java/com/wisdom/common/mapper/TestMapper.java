package com.wisdom.common.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.wisdom.common.model.Test;
 
//userMapper只能传入一个参数,多个的话需要注解
public interface TestMapper {

 
    List<Test> getAllTests();
}