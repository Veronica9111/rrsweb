package com.wisdom.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import junit.framework.TestCase;
import net.sf.json.JSONArray;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestPermission {


	
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
	public void testGetAllPermissions() throws ClientProtocolException, IOException{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet("http://localhost:8080/permission/getAllPermission");
		CloseableHttpResponse response = client.execute(request);
		String result = handleResponse(response);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertEquals("管理发票", map.get("1"));
		response.close();
	}

	@Test
	public void testGetPermissionByRole() throws UnsupportedOperationException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/permission/getPermissionByRole");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("name", "manage"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertEquals("管理发票", map.get("1"));
		response.close();
	}
	
	@Test
	public void testAddPermission() throws UnsupportedOperationException, IOException{
	//TODO add permission	
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/permission/addPermission");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("name", "test permission1"));
		nvps.add(new BasicNameValuePair("invoke_name", "test invoke name1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertEquals("ok", map.get("status"));
		httpPost = new HttpPost("http://localhost:8080/permission/deletePermission");
		nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("name", "test permission1"));
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
	public void testDeletePermission(){
		// TODO Add permission, add it to role, delete it, it should be remove from role.
	}
	
}
