package com.wisdom.sales.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.common.mapper.PermissionMapper;
import com.wisdom.common.mapper.RoleMapper;
import com.wisdom.common.mapper.SalesMapper;
import com.wisdom.common.model.Sales;
import com.wisdom.role.service.impl.RoleServiceImpl;
import com.wisdom.sales.service.ISalesService;

@Service("salesService")
public class SalesServiceImpl implements ISalesService{

	 @Autowired
	  private	SalesMapper salesMapper;


	private static final Logger logger = LoggerFactory
			.getLogger(RoleServiceImpl.class);
	
	public void setSalesMapper(SalesMapper salesMapper) {
		    this.salesMapper = salesMapper;
	}
	
	@Override
	public Boolean addSalesRecord(Sales sales) {
		// TODO Auto-generated method stub
		salesMapper.addSalesRecord(sales);
		return true;
	}

	@Override
	public List<Sales> getSales() {
		// TODO Auto-generated method stub
		return salesMapper.getSales();
	}

	@Override
	public Boolean updateSales(Integer id, String comment, String accountant, String updatedTime, String status) {
		// TODO Auto-generated method stub
		salesMapper.updateSales(id, accountant, updatedTime, comment, status);
		return true;
	}

}
