package com.wisdom.invoice.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface IInvoiceService {

	public List<Map<String, String>>  getAllInvoices();
	
	public Boolean addInvoice(String name, String path, String company);
	
	public List<Map<String, String>> getInvoicesByCompany(String company);
	
	public List<Map<String, String>> getInvoicesByStatus(String status);
	
	public List<Map<String, String>> getUnexportedInvoices();
	
	public Boolean setInvoicesExported(String[] invoices);
	
	public Boolean updateInvoiceStatus(String id, String status);
	
	public Boolean updateInvoiceOwner(String id, Integer uid);
	
	public Boolean addModifyInvoiceRecord(Integer uid, String invoiceId, String action);
	
	public Map<String, String> getInvoiceForUserByStatus(Integer uid, String status);
	
	public Boolean updateInvoiceContent(String path, String data, String FA, String id, Integer uid);
	
	public String readingeXML(String path);
}
