package com.wisdom.invoice.service.impl;
import static java.lang.Math.toIntExact;

import java.io.IOException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisdom.common.mapper.ArtifactMapper;
import com.wisdom.common.mapper.InvoiceMapper;
import com.wisdom.common.mapper.PermissionMapper;
import com.wisdom.common.mapper.RecordMapper;
import com.wisdom.common.model.Invoice;
import com.wisdom.common.model.Record;
import com.wisdom.invoice.service.IInvoiceService;
import com.wisdom.utils.RedisSetting;
import com.wisdom.utils.SystemSetting;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.wisdom.common.utils.ReadingXML;
import com.wisdom.common.utils.WriteXML;

@Service("invoiceService")
public class InvoiceServiceImpl implements IInvoiceService {


	  @Autowired
	  private	InvoiceMapper invoiceMapper;
	  
	  @Autowired
	  private RecordMapper recordMapper;
	  
	  @Autowired
	  private ArtifactMapper artifactMapper;




	private static final Logger logger = LoggerFactory
			.getLogger(InvoiceServiceImpl.class);
	
	
	public void setInvoiceMapper(InvoiceMapper invoiceMapper) {
		    this.invoiceMapper = invoiceMapper;
	}
	
	public void setRecordMapper(RecordMapper recordMapper){
		this.recordMapper = recordMapper;
	}
	
	public void setArtifactMapper(ArtifactMapper artifactMapper){
		this.artifactMapper = artifactMapper;
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
	public Boolean addInvoice(Integer priority, String name, String path, String company, long invoiceId, long companyId) {
		try{
			java.util.Date date= new java.util.Date();
			Timestamp now = new Timestamp(date.getTime());
			String uuid  =  UUID.randomUUID().toString(); 
			invoiceMapper.addInvoice(uuid, name, now, priority, path, company, invoiceId, companyId);


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
			temp.put("invoice_id", String.valueOf(invoice.getInvoice_id()));
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
			//publish message if the invoice is invalid
			if(status.equals("INVALID")){
				Invoice invoice = invoiceMapper.getInvoiceById(id);
				long invoiceId = invoice.getInvoice_id();
				
				JedisPoolConfig poolConfig = new JedisPoolConfig();
				poolConfig.setMaxIdle(RedisSetting.MAX_IDLE);
				poolConfig.setMinIdle(RedisSetting.MIN_IDLE);
				poolConfig.setTestOnBorrow(RedisSetting.TEST_ON_BORROW);
				poolConfig.setNumTestsPerEvictionRun(RedisSetting.NUM_TESTS_PER_EVICTION_RUN);
				poolConfig.setTimeBetweenEvictionRunsMillis(RedisSetting.TIME_BETWEEN_EVICTION_RUNS_MILLIS);
				poolConfig.setMaxWaitMillis(RedisSetting.MAX_WAIT_MILLIS);
				//poolConfig.setBlockWhenExhausted(org.apache.commons.pool.impl.GenericObjectPool.WHEN_EXHAUSTED_FAIL);


				// Timeout is set larger to the deploy environment
				JedisPool jedisPool = new JedisPool(poolConfig,RedisSetting.ADDRESS, RedisSetting.PORT, 10000, RedisSetting.PASSWORD);
				
			   ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
			    newFixedThreadPool.submit(new Runnable() {

			        @Override
			        public void run() {

			                
			                Jedis jedis = jedisPool.getResource();
			                try {
			                   jedis.publish("INVALID_INVOICE",Long.toString(invoiceId));
			                } catch (Exception e) {
			                   e.printStackTrace();
			                } finally {
			                   jedisPool.returnResource(jedis);
			                }
			            

			        }
			    });
			}
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
		String path = SystemSetting.INVOICE_IMAGE_PREFIX;
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
			path = path + invoice.getPath();
			temp.put("path", path);
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
			temp.put("invoice_id", String.valueOf(invoice.getInvoice_id()));
		}
		return temp;
	}

	@Override
	public Boolean updateInvoiceContent(String path, String data, String FA, String id, Integer uid) {
		

		//Add the invoice to queue
		
		List<Map<String, String>> exportData = new ArrayList<>();
		Map<String, String> exportedData = new HashMap<>();
		exportedData.put("fa", FA);
		Invoice invoice = invoiceMapper.getInvoiceById(id);
		String invoiceId = Long.toString(invoice.getInvoice_id());
		exportedData.put("id", invoiceId);
		exportedData.put("data", data);
		String exportDataStr = JSONArray.fromObject(exportedData).toString();
		
		//store the record
		storeInvoiceContent( path,  data,  FA,  invoiceId);
			
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(RedisSetting.MAX_IDLE);
		poolConfig.setMinIdle(RedisSetting.MIN_IDLE);
		poolConfig.setTestOnBorrow(RedisSetting.TEST_ON_BORROW);
		poolConfig.setNumTestsPerEvictionRun(RedisSetting.NUM_TESTS_PER_EVICTION_RUN);
		poolConfig.setTimeBetweenEvictionRunsMillis(RedisSetting.TIME_BETWEEN_EVICTION_RUNS_MILLIS);
		poolConfig.setMaxWaitMillis(RedisSetting.MAX_WAIT_MILLIS);
		//poolConfig.setBlockWhenExhausted(org.apache.commons.pool.impl.GenericObjectPool.WHEN_EXHAUSTED_FAIL);


		// Timeout is set larger to the deploy environment
		JedisPool jedisPool = new JedisPool(poolConfig,RedisSetting.ADDRESS, RedisSetting.PORT, 10000, RedisSetting.PASSWORD);
		
	   ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
	    newFixedThreadPool.submit(new Runnable() {

	        @Override
	        public void run() {

	                
	                Jedis jedis = jedisPool.getResource();
	                try {
	                   jedis.publish("RECOGNIZED_INVOICE", exportDataStr);
	                } catch (Exception e) {
	                   e.printStackTrace();
	                } finally {
	                   jedisPool.returnResource(jedis);
	                }
	            

	        }
	    });

		updateInvoiceStatus(id, "RECOGNIZED");
		updateInvoiceOwner(id, 0);
		//Set the work record
		Record record = new Record();
		record.setInvoice_id(id);
		record.setUid(uid);
		record.setAction("RECOGNIZE");
		recordMapper.addRecord(record);

		
	/*	try{
			WriteXML.WriteXML(path, data, FA, id);
		}catch(Exception e){
			logger.error(e.toString());
			return false;
		}*/
		return true;
	}

	@Override
	public String readingeXML(String path) {
		ReadingXML rx=new ReadingXML();
		return rx.read(path) ;
	}

	@Override
	public Boolean increaseInvoicesPriority(String[] invoices) {
		for(String id : invoices){
			invoiceMapper.increaseInvoicePriority(id);
		}
		return true;
	}

	@Override
	public Boolean decreaseInvoicesPriority(String[] invoices) {
		for(String id: invoices){
			invoiceMapper.decreaseInvoicePriority(id);
		}
		return true;
	}

	@Override
	public Boolean deleteInvoice(long invoiceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invoice getInvoiceByInvoiceId(long invoiceId) {
		return invoiceMapper.getInvoiceByInvoiceId(invoiceId);
	}

	@Override
	public Boolean updateInvoiceStatusWithInvoiceId(Integer invoiceId, String status) {

		invoiceMapper.updateInvoiceStatusWithInvoiceId(invoiceId, status);
		return true;
	}

	@Override
	public Boolean storeInvoiceContent(String path, String data, String FA, String id) {
		// TODO Auto-generated method stub
		JsonFactory factory2 = new JsonFactory();        
	    ObjectMapper mapper2 = new ObjectMapper(factory2);
	    TypeReference<List<HashMap<String,Object>>> typeRef2
        = new TypeReference<List<HashMap<String,Object>>>() {};

        try {
			List<Map<String,String>> content = mapper2.readValue(data, typeRef2);
			for(Map<String,String> record: content){
				String supplier = record.get("supplier");
				String type = record.get("description");
				Double amount = Double.parseDouble(record.get("amount"));
				Double tax = Double.parseDouble(record.get("tax"));
				Integer number = Integer.parseInt(record.get("number"));
				Integer isFa = 0;
				if(FA.equals("yes")){
					isFa = 1;
				}
				artifactMapper.addArtifact(Integer.valueOf(id), supplier, type, tax, amount, number, isFa);
			}
			
			
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}


	

}
