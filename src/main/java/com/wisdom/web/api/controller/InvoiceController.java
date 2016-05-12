package com.wisdom.web.api.controller;

import java.io.File;
import java.util.ArrayList;
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

import com.wisdom.common.model.Candidate;
import com.wisdom.common.utils.ReadingXML;
import com.wisdom.invoice.service.IInvoiceService;
import com.wisdom.permission.service.IPermissionService;
import com.wisdom.phrase.service.IPhraseService;
import com.wisdom.user.service.IUserService;
import com.wisdom.utils.SessionConstant;
import com.wisdom.utils.SystemSetting;

import net.sf.json.JSONArray;

@Controller
public class InvoiceController {

	@Autowired IInvoiceService invoiceService;
	
	@Autowired IUserService userService;
	
	@Autowired IPhraseService phraseService;
	
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
		Integer invoiceId = Integer.parseInt(request.getParameter("invoice_id"));
		Integer companyId = Integer.parseInt(request.getParameter("company_id"));
		Integer priority = 10;
		//TODO get company from uid in session
		String company = "company";
		if(invoiceService.addInvoice(priority, name, path, company, invoiceId, companyId)){
			retMap.put("status", "ok");
		}
		else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/invoice/sendJson")
	@ResponseBody
	public Map<String, String> sendJson(HttpServletRequest request){
		Map<String, String>retMap = new HashMap<>();
		String id = request.getParameter("id");
		String path = request.getSession().getServletContext()
				.getRealPath("/WEB-INF").substring(0)+File.separator+id+".xml";
		String json=invoiceService.readingeXML(path);
		if(json!=null){
			retMap.put("data", json);
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
	public Map<String, String> getInvoicesByStatus(HttpSession httpSession, HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Boolean result = userService.isUserValidForPermission(uid, "manage invoice");
		if(!result){
			retMap.put("status", "nok");
			return retMap;
		}
		String status = request.getParameter("status");
		List<Map<String,String>> invoices = invoiceService.getInvoicesByStatus(status);
		String data = JSONArray.fromObject(invoices).toString();
		retMap.put("data", data);
		retMap.put("status", "ok");
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
		String action = request.getParameter("action");
		String[] invoices = request.getParameter("invoices").split(",");
		if(action.equals("increase")){
			invoiceService.increaseInvoicesPriority(invoices);
		}else if(action.equals("decrease")){
			invoiceService.decreaseInvoicesPriority(invoices);
		}else{
			retMap.put("status", "nok");
		}
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
	
	@RequestMapping("/invoice/getInvoiceForUser")
	@ResponseBody
	public Map<String, String>getInvoiceForUserByStatus(HttpSession httpSession, HttpServletRequest request){
		Map<String, String>retMap = new HashMap<>();
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Boolean result = userService.isUserValidForPermission(uid, "recognize invoice");
		if(!result){
			retMap.put("status", "nok");
			return retMap;
		}
		String status = request.getParameter("status");
		Map<String, String> invoice = invoiceService.getInvoiceForUserByStatus(uid, status);
		String data = "";
		if (invoice != null){
			data = JSONArray.fromObject(invoice).toString();			
		}
		retMap.put("data", data);
		return retMap;
		
	}
	
	@RequestMapping("/invoice/updateInvoiceContent")
	@ResponseBody
	public Map<String, String>updateInvoiceContent(HttpSession httpSession, HttpServletRequest request){
		Map<String, String>retMap = new HashMap<>();
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Boolean result = userService.isUserValidForPermission(uid, "recognize invoice");
		if(!result){
			retMap.put("status", "nok");
			return retMap;
		}
		String data = request.getParameter("data");
		String FA = request.getParameter("FA");
		String id = request.getParameter("id");
		String realPath = request.getSession().getServletContext()
				.getRealPath("/WEB-INF").substring(0);
		if(invoiceService.updateInvoiceContent(realPath, data, FA, id, uid)){
			retMap.put("status", "ok");
		}else{
			retMap.put("status", "nok");
		}
		return retMap;
	}
	
	@RequestMapping("/invoice/getUsersCurrentWork")
	@ResponseBody
	public Map<String, String>getUsersCurrentWork(HttpSession httpSession, HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Boolean result = userService.isUserValidForPermission(uid, "manage invoice");
		if(!result){
			retMap.put("status", "nok");
			return retMap;
		}
		String role = request.getParameter("role");
		Map<String, Integer> users = userService.getUsersCurrentWork(role);
		String data = JSONArray.fromObject(users).toString();
		retMap.put("data", data);
		retMap.put("status", "ok");
		return retMap;
	}
	
	@RequestMapping("/invoice/updateInvoicesStatus")
	@ResponseBody
	public Map<String, String>updateInvoicesStatus(HttpSession httpSession, HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		Integer uid = (Integer) httpSession.getAttribute(SessionConstant.SESSION_USER_ID);
		Boolean result = userService.isUserValidForPermission(uid, "manage invoice");
		if(!result){
			retMap.put("status", "nok");
			return retMap;
		}
		String[] invoiceIds = request.getParameter("invoiceIds").split(",");
		String status = request.getParameter("status");
		for(String invoiceId: invoiceIds){
			if(!invoiceId.equals("") && invoiceId != null){
				invoiceService.updateInvoiceStatusWithInvoiceId(Integer.valueOf(invoiceId), status);
			}
		}
		retMap.put("status", "ok");
		return retMap;
	}
	
	@RequestMapping("/invoice/getCandidatePhrase")
	@ResponseBody
	public Map<String, Integer>getCandidatePhrase(HttpSession httpSession, HttpServletRequest request){
		Map<String, Integer> retMap = new HashMap<>();
		String phrase = request.getParameter("phrase");
		retMap = phraseService.getCandidatePhrases(phrase);
		return retMap;
		
	}
	

	@RequestMapping("/invoice/addCandidatePhrase")
	@ResponseBody
	public Map<String, String>addCandidatePhrase(HttpSession httpSession, HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		String value = request.getParameter("value");
		String type = request.getParameter("type");
		Integer confidence = Integer.valueOf(request.getParameter("confidence"));
		Integer invoiceId = Integer.valueOf(request.getParameter("invoice_id"));
		phraseService.addCandidate(value, type, confidence, invoiceId);
		retMap.put("status", "ok");
		return retMap;
	}
	
	@RequestMapping("/invoice/getCalculatedCandidatePhrase")
	@ResponseBody
	public List<String> getCalculatedCandidatePhrase(HttpSession httpSession, HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		String type = request.getParameter("type");
		Integer invoiceId = Integer.valueOf(request.getParameter("invoice_id"));
		List<Candidate> candidates = phraseService.getCalculatedCandidatePhrases(type, invoiceId);
		List<String> retList = new ArrayList<>();
		for(Candidate candidate: candidates){
			retList.add(candidate.getValue());
		}
		return retList;
	}
	
	@RequestMapping("/invoice/updatePhrase")
	@ResponseBody
	public Map<String, String> updatePhrase(HttpSession httpSession, HttpServletRequest request){
		Map<String, String> retMap = new HashMap<>();
		String phrase = request.getParameter("phrase");
		Integer hit = SystemSetting.INCREASE_HIT;
		if(phraseService.isPhraseExist(phrase)){
			phraseService.updatePhraseHit(phrase, hit);
		}else{
			phraseService.addPhrase(phrase, hit);
		}
		retMap.put("status", "ok");
		return retMap;
		
	}
	
}
