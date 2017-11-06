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
import com.wisdom.common.mapper.ArtifactMapper;
import com.wisdom.common.model.Invoice;
import com.wisdom.invoice.service.IInvoiceService;


@Service
public class DefaultMessageDelegate implements MessageDelegate {

	@Autowired
	IInvoiceService invoiceService;

	@Autowired
	private ArtifactMapper artifactMapper;
	
	@Override
	public synchronized void handleMessage(String message)
			throws JsonParseException, JsonMappingException, IOException {
		System.out.println(message);
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		TypeReference<List<HashMap<String, Object>>> typeRef = new TypeReference<List<HashMap<String, Object>>>() {
		};

		List<HashMap<String, Object>> list = mapper.readValue(message, typeRef);
		HashMap<String, Object> data = list.get(0);
		System.out.println(data);
		String path = (String) data.get("path");
		System.out.println(path);
		String name = (String) data.get("path");
		String company = (String) data.get("company");
		Integer priority = 10;
		
		String invoiceId = (String) data.get("invoice_id");
		long companyId = Long.parseLong((String) data.get("company_id"));
		//String detail = (String) data.get("content");
		List<HashMap<String, Object>> content = mapper.readValue(data.get("content").toString(), typeRef);
		if (content != null && content.size() != 0) {
			for (HashMap<String, Object> item : content) {
				if(item.isEmpty()) continue;
				String supplier = (String) item.get("supplierName");
				String type = (String) item.get("description");
				
				String amount_str = (String) item.get("amount");
				Double amount = 0.0;
				if(amount_str != null && !amount_str.isEmpty() ) {
					amount = Double.parseDouble(amount_str);
				}
				
				String tax_str = (String) item.get("tax");
				Double tax = 0.0;
				if(tax_str != null && !tax_str.isEmpty() ) {
					tax = Double.parseDouble(tax_str);
				}
				
				String number_str = (String) item.get("number");
				int number = 1;
				if(number_str != null && !number_str.isEmpty() ) {
					number = Integer.parseInt(number_str);
				}
				
				int isAccurate = 0;
				try{
					String isAccurate_str = (String) item.get("isAccurate");
					if("true".equals(isAccurate_str)) {
						isAccurate = 1;
					}
				}catch(Exception e) {
					boolean accurate = (boolean)item.get("isAccurate");
					if(accurate) {
						isAccurate = 1;
					}
				}
				
				
				
				Integer isFa = 0;

				artifactMapper.addArtifact(Integer.valueOf(invoiceId), supplier, type, tax, amount, number, isFa, isAccurate);
			}
		}
		
		
		
		invoiceService.addInvoice(priority, name, path, company, Long.valueOf(invoiceId), companyId);
		Integer uid = 1;
		if (companyId == 1680) {
			// 'supplier': supplier.val(), 'description':description.val(),
			// 'amount':amount.val(), 'tax':tax.val(), 'number':number.val()
			Map<String, String> demoMap = new HashMap<>();
			demoMap.put("supplier", "上海威武文化传播有限公司");
			demoMap.put("amount", "19000");
			demoMap.put("number", "1");
			demoMap.put("tax", "0");
			demoMap.put("description", "设计服务费");
			String demoStr = "[{\"supplier\":\"上海石油\",\"description\":\"加油费\",\"amount\":\"500\",\"tax\":\"0\",\"number\":\"1\"}]";
			// String demoStr = JSONArray.fromObject(demoMap).toString();
			// String dataStr =
			// "{'supplier':'上海威武文化传播有限公司','amount':'19000','number':'1','tax':'0','description':'设计服务'}";
			Invoice invoice = invoiceService.getInvoiceByInvoiceId(Long.valueOf(invoiceId));
			String id = invoice.getId();
			invoiceService.updateInvoiceContent(path, demoStr, "no", id, uid);
		}

	}

}
