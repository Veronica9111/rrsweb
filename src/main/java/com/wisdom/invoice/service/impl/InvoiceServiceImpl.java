package com.wisdom.invoice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.common.mapper.InvoiceMapper;
import com.wisdom.common.mapper.PermissionMapper;
import com.wisdom.common.model.Invoice;
import com.wisdom.invoice.service.IInvoiceService;

@Service("invoiceService")
public class InvoiceServiceImpl implements IInvoiceService {


	  @Autowired
	  private	InvoiceMapper invoiceMapper;



	private static final Logger logger = LoggerFactory
			.getLogger(InvoiceServiceImpl.class);
	
	
	public void setInvoiceMapper(InvoiceMapper invoiceMapper) {
		    this.invoiceMapper = invoiceMapper;
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

}
