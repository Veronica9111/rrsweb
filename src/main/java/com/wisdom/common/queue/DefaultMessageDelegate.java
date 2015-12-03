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
	public void handleMessage(String message) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		//System.out.println(message);
	    JsonFactory factory = new JsonFactory(); 
	    ObjectMapper mapper = new ObjectMapper(factory);
	    TypeReference<HashMap<String,Object>> typeRef 
        = new TypeReference<HashMap<String,Object>>() {};

    HashMap<String,Object> o = mapper.readValue(message, typeRef); 
	System.out.println(o);
	String path = (String) o.get("path");
	System.out.println(path);
	String name = (String)o.get("name");
	String company = (String)o.get("company");
	invoiceService.addInvoice(name, path, company);
	}

}
