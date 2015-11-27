package com.wisdom.invoice.service.impl;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.common.mapper.InvoiceMapper;
import com.wisdom.common.mapper.PermissionMapper;
import com.wisdom.common.mapper.RecordMapper;
import com.wisdom.common.model.Invoice;
import com.wisdom.common.model.Record;
import com.wisdom.invoice.service.IInvoiceService;
import com.wisdom.common.utils.ReadingXML;
import com.wisdom.common.utils.WriteXML;

@Service("invoiceService")
public class InvoiceServiceImpl implements IInvoiceService {


	  @Autowired
	  private	InvoiceMapper invoiceMapper;
	  
	  @Autowired
	  private RecordMapper recordMapper;



	private static final Logger logger = LoggerFactory
			.getLogger(InvoiceServiceImpl.class);
	
	
	public void setInvoiceMapper(InvoiceMapper invoiceMapper) {
		    this.invoiceMapper = invoiceMapper;
	}
	
	public void setRecordMapper(RecordMapper recordMapper){
		this.recordMapper = recordMapper;
	}
	
	@Override
	public List<Map<String, String>> getAllInvoices() {
		List<Invoice> invoices = invoiceMapper.getAllInvoices();
		List<Map<String, String>> retList = new ArrayList<>();
		for (Invoice invoice: invoices){
			Map<String, String> temp = new HashMap<>();
			temp.put(invoice.getId(), invoice.getCreatedTime().toString());
			retList.add(temp);
			
		}
		
		return retList;
	}

	@Override
	public Boolean addInvoice(String name, String path, String company) {
		try{
			java.util.Date date= new java.util.Date();
			Timestamp now = new Timestamp(date.getTime());
			String uuid  =  UUID.randomUUID().toString(); 
			invoiceMapper.addInvoice(uuid, name, now, path, company);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public List<Map<String, String>> getInvoicesByCompany(String company) {
		List<Invoice> invoices = invoiceMapper.getInvoicesByCompany(company);
		List<Map<String, String>> retList = new ArrayList<>();
		String modified_time;
		String uid;
		String path;
		
		for(Invoice invoice: invoices){
			Map<String, String> temp = new HashMap<>();
			temp.put("id", invoice.getId());
			temp.put("name", invoice.getName());
			temp.put("created_time", invoice.getCreatedTime().toString());
			if (invoice.getModifiedTime() == null){
				modified_time = "null";
			}
			else{
				modified_time = invoice.getModifiedTime().toString();
			}
			temp.put("modified_time", modified_time);
			temp.put("priority", invoice.getPriority().toString());
			if (invoice.getPath()== null){
				path = "null";
			}
			else{
				path = invoice.getPath().toString();
			}			
			temp.put("path", invoice.getPath());
			temp.put("company", invoice.getCompany());
			temp.put("exported", invoice.getExported().toString());
			if(invoice.getUID() == null){
				uid = "null";
			}
			else{
				uid = invoice.getUID().toString();
			}
			temp.put("uid", uid);
			temp.put("document", invoice.getDocument());
			temp.put("status", invoice.getStatus());
			retList.add(temp);
		}
		return retList;
	}

	@Override
	public List<Map<String, String>> getInvoicesByStatus(String status) {
		List<Invoice> invoices = invoiceMapper.getInvoicesByStatus(status);
		List<Map<String, String>> retList = new ArrayList<>();
		String modified_time;
		String uid;
		String path;
		
		for(Invoice invoice: invoices){
			Map<String, String> temp = new HashMap<>();
			temp.put("id", invoice.getId());
			temp.put("name", invoice.getName());
			temp.put("created_time", invoice.getCreatedTime().toString());
			if (invoice.getModifiedTime() == null){
				modified_time = "null";
			}
			else{
				modified_time = invoice.getModifiedTime().toString();
			}
			temp.put("modified_time", modified_time);
			temp.put("priority", invoice.getPriority().toString());
			if (invoice.getPath()== null){
				path = "null";
			}
			else{
				path = invoice.getPath().toString();
			}			
			temp.put("path", invoice.getPath());
			temp.put("company", invoice.getCompany());
			temp.put("exported", invoice.getExported().toString());
			if(invoice.getUID() == null){
				uid = "null";
			}
			else{
				uid = invoice.getUID().toString();
			}
			temp.put("uid", uid);
			temp.put("document", invoice.getDocument());
			temp.put("status", invoice.getStatus());
			retList.add(temp);
		}
		return retList;
	}

	@Override
	public List<Map<String, String>> getUnexportedInvoices() {
		List<Invoice> invoices = invoiceMapper.getUnexportedInvoices();
		List<Map<String, String>> retList = new ArrayList<>();
		String modified_time;
		String uid;
		String path;
		
		for(Invoice invoice: invoices){
			Map<String, String> temp = new HashMap<>();
			temp.put("id", invoice.getId());
			temp.put("name", invoice.getName());
			temp.put("created_time", invoice.getCreatedTime().toString());
			if (invoice.getModifiedTime() == null){
				modified_time = "null";
			}
			else{
				modified_time = invoice.getModifiedTime().toString();
			}
			temp.put("modified_time", modified_time);
			temp.put("priority", invoice.getPriority().toString());
			if (invoice.getPath()== null){
				path = "null";
			}
			else{
				path = invoice.getPath().toString();
			}			
			temp.put("path", invoice.getPath());
			temp.put("company", invoice.getCompany());
			temp.put("exported", invoice.getExported().toString());
			if(invoice.getUID() == null){
				uid = "null";
			}
			else{
				uid = invoice.getUID().toString();
			}
			temp.put("uid", uid);
			temp.put("document", invoice.getDocument());
			temp.put("status", invoice.getStatus());
			retList.add(temp);
		}
		return retList;
	}

	@Override
	public Boolean setInvoicesExported(String[] invoices) {
		try {
			for(String invoice: invoices){
				invoiceMapper.setInvoiceExported(invoice);
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public Boolean updateInvoiceStatus(String id, String status) {
		try{
			invoiceMapper.updateInvoiceStatus(id, status);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public Boolean updateInvoiceOwner(String id, Integer uid) {
		try{
			invoiceMapper.updateInvoiceOwner(id, uid);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public Boolean addModifyInvoiceRecord(Integer uid, String invoiceId, String action) {
		java.util.Date date= new java.util.Date();
		Timestamp now = new Timestamp(date.getTime());
		try{
			invoiceMapper.addModifyInvoiceRecord(uid, invoiceId, now, action);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	
	@Override
	public Map<String, String> getInvoiceForUserByStatus(Integer uid, String status) {
		//If the user already have one, then just return it
		Invoice invoice = invoiceMapper.getInvoiceByUserAndStatus(uid, status);
		if(invoice == null){
			//Get an avaiable invoice
			invoice = invoiceMapper.getInvoiceForUserByStatus(status);
			if(invoice == null){
				return null;
			}
			//Then set the owner
			invoiceMapper.updateInvoiceOwner(invoice.getId(), uid);
		}
		Map<String, String> temp = new HashMap<>();
		String modified_time;
		String path;
		String userid;
		if (invoice == null){
			return null;
		}else{
			temp.put("id", invoice.getId());
			temp.put("name", invoice.getName());
			temp.put("created_time", invoice.getCreatedTime().toString());
			if (invoice.getModifiedTime() == null){
				modified_time = "null";
			}
			else{
				modified_time = invoice.getModifiedTime().toString();
			}
			temp.put("modified_time", modified_time);
			temp.put("priority", invoice.getPriority().toString());
			if (invoice.getPath()== null){
				path = "null";
			}
			else{
				path = invoice.getPath().toString();
			}			
			temp.put("path", invoice.getPath());
			temp.put("company", invoice.getCompany());
			temp.put("exported", invoice.getExported().toString());
			if(invoice.getUID() == null){
				userid = "null";
			}
			else{
				userid = invoice.getUID().toString();
			}
			temp.put("uid", userid);
			temp.put("document", invoice.getDocument());
			temp.put("status", invoice.getStatus());
		}
		return temp;
	}

	@Override
	public Boolean updateInvoiceContent(String path, String data, String FA, String id, Integer uid) {
		//TODO set the status to UNCHECK, remove the uid
		updateInvoiceStatus(id, "UNCHECK");
		updateInvoiceOwner(id, 0);
		//Set the work record
		Record record = new Record();
		record.setInvoice_id(id);
		record.setUid(uid);
		record.setAction("RECOGNIZE");
		recordMapper.addRecord(record);
		
		try{
			WriteXML.WriteXML(path, data, FA, id);
		}catch(Exception e){
			logger.error(e.toString());
			return false;
		}
		return true;
	}

	@Override
	public String readingeXML(String path) {
		ReadingXML rx=new ReadingXML();
		return rx.read(path) ;
	}

}
