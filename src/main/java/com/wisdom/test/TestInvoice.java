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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;
import net.sf.json.JSONArray;

public class TestInvoice extends TestCase{
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
	public void testLoginSuccess() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/invoice/sendJson");
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "test"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String me=this.handleResponse(response);
		System.out.println(me);
//		String result = handleResponse(response);
//		Map<String, String> expectedMap = new HashMap<>();
//		expectedMap.put("status", "ok");
//		String expected = JSONArray.fromObject(expectedMap).toString();
//		Assert.assertThat(expected, CoreMatchers.containsString(result));
//		response.close();
	}
	
}
