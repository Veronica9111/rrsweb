package com.wisdom.sales.service;

import java.util.List;

import com.wisdom.common.model.Sales;

public interface ISalesService {

	Boolean addSalesRecord(Sales sales);
	
	List<Sales> getSales();
	
	Boolean updateSales(Integer id, String comment, String accountant, String updatedTime, String status);
}
