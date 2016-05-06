package com.wisdom.common.queue;

import java.io.IOException;
import java.util.ArrayList;
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
import com.wisdom.common.model.Invoice;
import com.wisdom.common.model.Model;
import com.wisdom.invoice.service.IInvoiceService;

import net.sf.json.JSONArray;

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
	Integer uid = 1;
	//if company id = 1680, just recognize it
	if(companyId == 1680){
		//'supplier': supplier.val(), 'description':description.val(), 'amount':amount.val(), 'tax':tax.val(), 'number':number.val()
		Map<String, String> demoMap = new HashMap<>();
		demoMap.put("supplier", "上海威武文化传播有限公司");
		demoMap.put("amount", "19000");
		demoMap.put("number", "1");
		demoMap.put("tax", "0");
		demoMap.put("description", "设计服务费");
		String demoStr = "[{\"supplier\":\"上海石油\",\"description\":\"加油费\",\"amount\":\"500\",\"tax\":\"0\",\"number\":\"1\"}]";
		//String demoStr = JSONArray.fromObject(demoMap).toString();
		//String dataStr = "{'supplier':'上海威武文化传播有限公司','amount':'19000','number':'1','tax':'0','description':'设计服务'}";
		Invoice invoice = invoiceService.getInvoiceByInvoiceId(invoiceId);
		String id = invoice.getId();
		invoiceService.updateInvoiceContent(path, demoStr, "no", id, uid);
	}
	
	}

}
