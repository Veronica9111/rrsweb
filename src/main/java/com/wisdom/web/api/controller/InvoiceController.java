package com.wisdom.web.api.controller;

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

import com.wisdom.invoice.service.IInvoiceService;
import com.wisdom.permission.service.IPermissionService;

@Controller
public class InvoiceController {

	@Autowired IInvoiceService invoiceService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(InvoiceController.class);
	
	
	@RequestMapping("/invoice/getAllInvoices")
	@ResponseBody
	public Map<String, String> getAllInvoices(HttpServletRequest request){
		logger.debug("enter getAllInvoices");
		Map<String, String> retMap = new HashMap<>();
		List<Map<String,String>> invoices = invoiceService.getAllInvoices();
		String data = invoices.toString();
		if (data != null){
			retMap.put("status", "ok");
			retMap.put("data", data);
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
}
