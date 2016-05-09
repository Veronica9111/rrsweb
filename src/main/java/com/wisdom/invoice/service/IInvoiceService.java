package com.wisdom.invoice.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.wisdom.common.model.Invoice;

public interface IInvoiceService {

	public List<Map<String, String>>  getAllInvoices();
	
	public Boolean addInvoice(Integer priority, String name, String path, String company, long invoiceId, long companyId);
	
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
	
	public Boolean increaseInvoicesPriority(String[] invoices);
	
	public Boolean decreaseInvoicesPriority(String[] invoices);
	
	public Boolean deleteInvoice(long invoiceId);
	
	public Invoice getInvoiceByInvoiceId(long invoiceId);
	
	public Boolean updateInvoiceStatusWithInvoiceId(Integer invoiceId, String status);
	
	public Boolean storeInvoiceContent(String paht, String data, String FA, String id);
}
