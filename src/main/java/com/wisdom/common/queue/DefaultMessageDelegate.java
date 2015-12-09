package com.wisdom.common.queue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisdom.common.model.Model;
import com.wisdom.invoice.service.IInvoiceService;

@Service
public class DefaultMessageDelegate implements MessageDelegate {
	
	@Autowired IInvoiceService invoiceService;

	@Override
	public synchronized void handleMessage(String message) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		//System.out.println(message);
	    JsonFactory factory = new JsonFactory(); 
	    ObjectMapper mapper = new ObjectMapper(factory);
	    TypeReference<List<HashMap<String,Object>>> typeRef 
        = new TypeReference<List<HashMap<String,Object>>>() {};


      //  "UNRECOGNIZED_INVOICE" "[{\"path\":\"jzzbeyond001@163.com1449556610603677.png\",\"company_id\":\"40\",\"invoice_id\":\"83\",\"company\":\"\xe5\x85\x83\xe5\x8d\x87\"}]"
    List<HashMap<String,Object>> list = mapper.readValue(message, typeRef);
    HashMap<String, Object> data = list.get(0);
	System.out.println(data);
	String path = (String) data.get("path");
	System.out.println(path);
	String name = (String)data.get("path");
	String company = (String)data.get("company");
	Integer priority = 10;
	long invoiceId = Long.parseLong((String) data.get("invoice_id"));
	long companyId = Long.parseLong((String)data.get("company_id"));
	invoiceService.addInvoice(priority, name, path, company, invoiceId, companyId);
	}

}
