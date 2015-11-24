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

public class TestUser extends TestCase {
	
	private String uid;
	
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
	public void testOpenPage() throws IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		String responseString = "";
		String line = "";
		HttpGet request = new HttpGet("http://localhost:8080/views/frontviews/index.html");
		HttpResponse response = client.execute(request);
		String result = handleResponse(response);
		Assert.assertNotNull(result);

	}
	
	@Test
	public void testLoginSuccess() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/login");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "1"));
		nvps.add(new BasicNameValuePair("password", "111111"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		Map<String, String> expectedMap = new HashMap<>();
		expectedMap.put("status", "ok");
		String expected = JSONArray.fromObject(expectedMap).toString();
		Assert.assertThat(expected, CoreMatchers.containsString(result));
		response.close();
	}
	
	@Test
	public void testLoginSuccess2() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/login");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "johnnysangel@163.com"));
		nvps.add(new BasicNameValuePair("password", "111111"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		Map<String, String> expectedMap = new HashMap<>();
		expectedMap.put("status", "ok");
		String expected = JSONArray.fromObject(expectedMap).toString();
		Assert.assertThat(expected, CoreMatchers.containsString(result));
		response.close();
	}
	
	@Test
	public void testLoginWithoutID() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/login");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("password", "222222"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		Map<String, String> expectedMap = new HashMap<>();
		expectedMap.put("status", "nok");
		String expected = JSONArray.fromObject(expectedMap).toString();
		Assert.assertThat(expected, CoreMatchers.containsString(result));
		response.close();
	}

	
	@Test
	public void testLoginWithWrongPassword() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/login");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "johnnysangel@163.com"));
		nvps.add(new BasicNameValuePair("password", "333333"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		Map<String, String> expectedMap = new HashMap<>();
		expectedMap.put("status", "nok");
		String expected = JSONArray.fromObject(expectedMap).toString();
		Assert.assertThat(expected, CoreMatchers.containsString(result));
		response.close();
	}
	
	@Test
	public void testLoginWithNotExistedMail() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/login");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "notexisted@163.com"));
		nvps.add(new BasicNameValuePair("password", "222222"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		Map<String, String> expectedMap = new HashMap<>();
		expectedMap.put("status", "nok");
		String expected = JSONArray.fromObject(expectedMap).toString();
		Assert.assertThat(expected, CoreMatchers.containsString(result));
		response.close();
	}
	
	@Test
	public void testLoginWithNotExisteUID() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/login");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "999999"));
		nvps.add(new BasicNameValuePair("password", "222222"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		Map<String, String> expectedMap = new HashMap<>();
		expectedMap.put("status", "nok");
		String expected = JSONArray.fromObject(expectedMap).toString();
		Assert.assertThat(expected, CoreMatchers.containsString(result));
		response.close();
	}
	

	@Test
	public void testLoginWithoutPassword() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/login");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "johnnysangel@163.com"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		Map<String, String> expectedMap = new HashMap<>();
		expectedMap.put("status", "nok");
		String expected = JSONArray.fromObject(expectedMap).toString();
		Assert.assertThat(expected, CoreMatchers.containsString(result));
		response.close();
	}
	
	@Test
	public void testGetAllUsersWithRecord() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseString = "";
		String line = "";
		HttpGet request = new HttpGet("http://localhost:8080/user/getAllUsersWithWorkRecords");
		HttpResponse response = httpclient.execute(request);
		String result = handleResponse(response);
		Assert.assertNotNull(result);
		String expected = "毛毛兔";
		Assert.assertThat(result, CoreMatchers.containsString(expected));
	}
	
	@Test
	public void testAddUser() throws JsonParseException, JsonMappingException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/user/addUser");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("name", "test user"));
		nvps.add(new BasicNameValuePair("company", "test company"));
		nvps.add(new BasicNameValuePair("role", "管理,管理员"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertNotEquals(-1, map.get("uid"));
		Assert.assertEquals("ok", map.get("status"));
	}
	
	@Test
	public void testDeleteUser() throws JsonParseException, JsonMappingException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/user/addUser");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("name", "test user"));
		nvps.add(new BasicNameValuePair("company", "test company"));
		nvps.add(new BasicNameValuePair("role", "管理,管理员"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertNotEquals(-1, map.get("uid"));
		Assert.assertEquals("ok", map.get("status"));
		String uid = (String) map.get("uid");
		httpclient = HttpClients.createDefault();
		httpPost = new HttpPost("http://localhost:8080/user/deleteUser");
		nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("uid", uid));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		response = httpclient.execute(httpPost);
		result = handleResponse(response);
		mapper = new ObjectMapper();
		map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertEquals("ok", map.get("status"));
	}
	
	@Test
	public void testGetAllUsers() throws JsonParseException, JsonMappingException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet request = new HttpGet("http://localhost:8080/user/getAllUsers");
		HttpResponse response = httpclient.execute(request);
		String result = handleResponse(response);
		String expected = "毛毛";
		Assert.assertThat(result, CoreMatchers.containsString(expected));
		
	}
	
	@Test
	public void testGetAllUsersByCompany() throws UnsupportedOperationException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/user/getUsersByCompany");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("company", "company"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		Integer lastIndex = 0;
		Integer count = 0;
		Integer expected = 8;
		while(lastIndex != -1){
		    lastIndex = result.indexOf("company",lastIndex);
		    if(lastIndex != -1){
		        count ++;
		        lastIndex += "company".length();
		    }
		}
		Assert.assertTrue(count >= 8);;
	}
	
	@Test
	public void testGetUsersByActive() throws UnsupportedOperationException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/user/getUsersByActive");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("active", "1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		Assert.assertThat(result, CoreMatchers.containsString("毛毛"));
	}
	
	@Test
	public void testUpdateUser() throws UnsupportedOperationException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/login");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "1"));
		nvps.add(new BasicNameValuePair("password", "222222"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		httpPost = new HttpPost("http://localhost:8080/user/updateUser");
		nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("email", "johnnysangel@163.com"));
		nvps.add(new BasicNameValuePair("name", "毛毛"));
		nvps.add(new BasicNameValuePair("company", "company"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		response = httpclient.execute(httpPost);
		httpPost = new HttpPost("http://localhost:8080/user/getUserById");
		nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		System.out.println(result);
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, Map<String, String>>>(){});
		Assert.assertEquals("company", map.get("company"));
	}
	
	@Test
	public void testUpdateUserPassword() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/login");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "1"));
		nvps.add(new BasicNameValuePair("password", "111111"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		httpPost = new HttpPost("http://localhost:8080/user/updateUserPassword");
		nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("oldPassword", "111111"));
		nvps.add(new BasicNameValuePair("newPassword", "222222"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertEquals("ok", map.get("status"));
		httpPost = new HttpPost("http://localhost:8080/user/updateUserPassword");
		nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("oldPassword", "222222"));
		nvps.add(new BasicNameValuePair("newPassword", "111111"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		response = httpclient.execute(httpPost);	
	}
	
	@Test
	public void testActivateUser() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/user/activateUser");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "5"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertEquals("ok", map.get("status"));
	}
	
	@Test
	public void testDeactivateUser() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/user/deactivateUser");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "5"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, String>>(){});
		Assert.assertEquals("ok", map.get("status"));
	}
	
	@Test
	public void  testGetUserWithWorkRecords() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/login");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "1"));
		nvps.add(new BasicNameValuePair("password", "111111"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		httpPost = new HttpPost("http://localhost:8080/user/getUserWithWorkRecords");
		nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		Assert.assertThat(result, CoreMatchers.containsString("毛毛"));
	}
	
	@Test
	public void testGetUserRoles() throws UnsupportedOperationException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/user/getUserRoles");
		ArrayList<BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		Assert.assertThat(result, CoreMatchers.containsString("管理"));
		Assert.assertThat(result, CoreMatchers.containsString("管理员"));
	}
	
	@Test
	public void testGetUserById() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/user/getUserById");
		ArrayList<BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		System.out.println(result);
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(result, new TypeReference<Map<String, Map<String, String>>>(){});
		Assert.assertEquals("毛毛", map.get("name"));
	}
}
