package com.wisdom.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wisdom.common.model.Sales;;

public interface SalesMapper {
	
	Integer addSalesRecord(Sales sales);
	
	List<Sales> getSales();
	
	void updateSales(@Param("id")Integer id, @Param("accountant")String accountant, @Param("updated_time")String updated_time, @Param("comment")String comment,@Param("status")String status);

}

