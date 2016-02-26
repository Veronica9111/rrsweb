package com.wisdom.web.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisdom.common.model.Permission;
import com.wisdom.common.model.Sales;
import com.wisdom.permission.service.IPermissionService;
import com.wisdom.sales.service.ISalesService;
import com.wisdom.user.service.IUserService;

@Controller
public class SalesController {

	@Autowired ISalesService salesService;
	

	
	private static final Logger logger = LoggerFactory
			.getLogger(SalesController.class);
	
	
	@RequestMapping("/sales/addSales")
	@ResponseBody
	public Map<String, String> addSales(HttpServletRequest request){
		//logger.debug("enter addPermission");
		Map<String, String> retMap = new HashMap<>();
		Sales sales = new Sales();
		sales.setUser_name(request.getParameter("user_name"));
		sales.setUser_company(request.getParameter("user_company"));
		sales.setUser_phone(request.getParameter("user_phone"));
		sales.setLatest_comment(request.getParameter("comment"));
		sales.setAccountant(request.getParameter("accountant"));
		sales.setSaller_account(request.getParameter("saller_account"));
		sales.setUpdated_time(request.getParameter("updated_time"));
		if (salesService.addSalesRecord(sales)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/sales/getSales")
	@ResponseBody
	public Map<String, List<List<String>>> getSales(HttpServletRequest request){
		//logger.debug("enter addPermission");
		List<List<String>> retList = new ArrayList<>();
		List<Sales> sales = salesService.getSales();
		for(Sales sale: sales){
			List<String> tmp = new ArrayList<>();
			tmp.add(sale.getSaller_account());
			tmp.add(sale.getUser_company());
			tmp.add(sale.getUser_name());
			tmp.add(sale.getUser_phone());
			tmp.add(sale.getLatest_comment());
			tmp.add(sale.getUpdated_time());
			tmp.add(sale.getAccountant());
			tmp.add(sale.getStatus());
			tmp.add(sale.getId().toString());
			tmp.add("<input type='button' value='编辑' id='edit-" + sale.getId().toString() + "' class='edit-btn'/>");
			retList.add(tmp);
		}
		Map<String, List<List<String>>> retMap = new HashMap<>();
		retMap.put("data", retList);
		return retMap;
	}
	

	@RequestMapping("/sales/updateSales")
	@ResponseBody
	public Map<String, String> updateSales(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		String accountant = request.getParameter("accountant");
		String status = request.getParameter("status");
		String comment = request.getParameter("comment");
		String updatedTime = request.getParameter("updated_time");
		Integer id = Integer.valueOf(request.getParameter("id"));
		if(salesService.updateSales(id, comment, accountant, updatedTime, status)){
			retMap.put("status", "ok");
		}else{
			retMap.put("status", "nok");
		}
		return retMap;
		
	}
		
	
}
