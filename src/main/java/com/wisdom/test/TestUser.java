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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import junit.framework.TestCase;
import net.sf.json.JSONArray;

public class TestUser extends TestCase {
	
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
		nvps.add(new BasicNameValuePair("password", "222222"));
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
		nvps.add(new BasicNameValuePair("password", "222222"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String result = handleResponse(response);
		Map<String, String> expectedMap = new HashMap<>();
		expectedMap.put("status", "ok");
		String expected = JSONArray.fromObject(expectedMap).toString();
		System.out.println(result);
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
		System.out.println("hello");
		System.out.println(result);
		Assert.assertThat(expected, CoreMatchers.containsString(result));
		response.close();
	}
}
