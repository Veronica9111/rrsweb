package com.wisdom.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;
import net.sf.json.JSONArray;

public class TestRole extends TestCase{
	public String handleResponse(HttpResponse response) throws UnsupportedOperationException, IOException{
		// Get the response
		String line = "";
		String responseString = "";
		BufferedReader rd = new BufferedReader
		  (new InputStreamReader(response.getEntity().getContent()));
		while (( line = rd.readLine()) != null) {
		  responseString += line;  
		} 
		return responseString;
	}
	
	@Test
	public void testAddAndRemoveRole() throws ClientProtocolException, IOException{
		//Add Role first
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/role/addRole");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("name", "manage2"));
		nvps.add(new BasicNameValuePair("permissions", "test permission,test permission1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertEquals("ok", map.get("status"));
		response.close();
		//Check if role and relationship is added
		httpPost = new HttpPost("http://localhost:8080/permission/getPermissionsByRole");
		nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("name", "manage2"));
		nvps.add(new BasicNameValuePair("permissions", "test permission,test permission1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		response = httpclient.execute(httpPost);
		result = handleResponse(response);
		System.out.println(result);
		mapper = new ObjectMapper();
		map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertTrue(map.values().contains("test permission"));
		response.close();
		//Then remove the role
		httpPost = new HttpPost("http://localhost:8080/role/deleteRole");
		nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("name", "manage2"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		response = httpclient.execute(httpPost);
		response.close();
	}
	
	@Test
	public void testGetAllRoles() throws JsonParseException, JsonMappingException, IOException{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet("http://localhost:8080/role/getAllRoles");
		CloseableHttpResponse response = client.execute(request);
		String result = handleResponse(response);
		System.out.println(result);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		map = mapper.readValue(result, new TypeReference<Map<String, Map<String, String>>>(){});
		Assert.assertEquals("管理", map.get("data").get("1"));
		response.close();
	}

	@Test
	public void testUpdateRole() throws UnsupportedOperationException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/role/updateRole");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("oldName", "manage"));
		nvps.add(new BasicNameValuePair("newName", "manage3"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertEquals("ok", map.get("status"));
		response.close();
		//Then change it back
		httpclient = HttpClients.createDefault();
		httpPost = new HttpPost("http://localhost:8080/role/updateRole");
		nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("oldName", "manage3"));
		nvps.add(new BasicNameValuePair("newName", "manage"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		response = httpclient.execute(httpPost);
		result = handleResponse(response);
		mapper = new ObjectMapper();
		map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertEquals("ok", map.get("status"));
		response.close();
	}
	
	@Test
	public void testUpdateRolePermissions() throws JsonParseException, JsonMappingException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/role/updateRolePermissions");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("roleName", "manage"));
		nvps.add(new BasicNameValuePair("permissions", "test permission,test permission1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertEquals("ok", map.get("status"));
		response.close();
	}

}
