package com.wisdom.web.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisdom.invoice.service.IInvoiceService;
import com.wisdom.permission.service.IPermissionService;
import com.wisdom.utils.SessionConstant;

import net.sf.json.JSONArray;

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
	
	@RequestMapping("/invoice/addInvoice")
	@ResponseBody
	public Map<String, String> addInvoice(HttpServletRequest request){
		Map<String, String>retMap = new HashMap<>();
		String name = request.getParameter("name");
		String path = request.getParameter("path");
		//TODO get company from uid in session
		String company = "company";
		if(invoiceService.addInvoice(name, path, company)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/invoice/updateInvoice")
	@ResponseBody
	public Map<String, String> updateInvoice(HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/invoice/getInvoicesByCompany")
	@ResponseBody
	public Map<String, String> getInvoicesByCompany(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		String company = request.getParameter("company");
		List<Map<String,String>> invoices = invoiceService.getInvoicesByCompany(company);
		String data = JSONArray.fromObject(invoices).toString();
		retMap.put("data", data);
		return retMap;
	}
	
	@RequestMapping("/invoice/getInvoicesByStatus")
	@ResponseBody
	public Map<String, String> getInvoicesByStatus(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		String status = request.getParameter("status");
		List<Map<String,String>> invoices = invoiceService.getInvoicesByStatus(status);
		String data = JSONArray.fromObject(invoices).toString();
		retMap.put("data", data);
		return retMap;
	}
	
	@RequestMapping("/invoice/getUnexportedInvoices")
	@ResponseBody
	public Map<String, String>getUnexportedInvoices(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		List<Map<String,String>> invoices = invoiceService.getUnexportedInvoices();
		String data = JSONArray.fromObject(invoices).toString();
		retMap.put("data", data);
		return retMap;
	}
	
	@RequestMapping("/invoice/setInvoicesExported")
	@ResponseBody
	public Map<String, String>setInvoicesExported(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		String[] invoices = request.getParameter("invoices").split(",");
		if(invoiceService.setInvoicesExported(invoices)){
			retMap.put("status", "ok");
		}else{
			retMap.put("status", "nok");
		}
		
		return retMap;
	}
	
	@RequestMapping("/invoice/updateInvoiceStatus")
	@ResponseBody
	public Map<String, String>updateInvoiceStatus(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		String invoice = request.getParameter("id");
		String status = request.getParameter("status");
		if(invoiceService.updateInvoiceStatus(invoice, status)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/invoice/updateInvoiceOwner")
	@ResponseBody
	public Map<String,String>updateInvoiceOwner(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		String uid = request.getParameter("uid");
		String invoice = request.getParameter("id");
		if(invoiceService.updateInvoiceOwner(invoice, Integer.parseInt(uid))){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;		
	}
	//TODO
	@RequestMapping("/invoice/updateInvoicePriority")
	@ResponseBody
	public Map<String, String>updateInvoicePriority(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		return retMap;
	}
	
	@RequestMapping("/invoice/updateInvoiceStatusWithUser")
	@ResponseBody
	public Map<String, String>updateInvoiceStatusWithUser(HttpServletRequest request){
		Map<String, String>retMap = new HashMap<>();
		String invoice = request.getParameter("id");
		//TODO get uid from session
		Integer uid = 1;
		String action = request.getParameter("action");
		
		if(invoiceService.addModifyInvoiceRecord(uid, invoice, action)){
			retMap.put("status", "ok");
		}else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
<<<<<<< d175d06beec51b6abc970f5cbda3586a11b7dd79
	@RequestMapping("/invoice/getInvoiceForUser")
	@ResponseBody
	public Map<String, String>getInvoiceForUserByStatus(HttpSession httpSession, HttpServletRequest request){
		Map<String, String>retMap = new HashMap<>();
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		String status = request.getParameter("status");
		Map<String, String> invoice = invoiceService.getInvoiceForUserByStatus(uid, status);
		String data = JSONArray.fromObject(invoice).toString();
		retMap.put("data", data);
		return retMap;
		
	}
	
	@RequestMapping("/invoice/updateInvoiceContent")
	@ResponseBody
	public Map<String, String>updateInvoiceContent(HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		String data = request.getParameter("data");
		String FA = request.getParameter("FA");
		String id = request.getParameter("id");
		logger.info("Need to write to xml");
		logger.info(data);
		retMap.put("status", "ok");
		return retMap;
	}
=======
	
>>>>>>> xiaoming add xml
}
