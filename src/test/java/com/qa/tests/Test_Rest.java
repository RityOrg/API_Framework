package com.qa.tests;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;

public class Test_Rest {
	static String url ="https://reqres.in/api/users";
	static CloseableHttpResponse chp;
	public static void main(String[] args) throws IOException {
		
		//RestClient restClient = new RestClient();
		//String s = restClient
		chp = getMeth(url);
		// a. Status Code:
		
		int statusCode= chp.getStatusLine().getStatusCode();
		System.out.println("Status Code---->" + statusCode);
		Assert.assertEquals(statusCode, 200, "Status Code is not 200");
		
		// b. Json String:
		String responseString = EntityUtils.toString(chp.getEntity(),"UTF-8");/* UTF-8 is a character format 
																			(if there is any special character or anything 
																			please correct it and give me the pure string)*/
		JSONObject responsejson = new JSONObject(responseString); // convert the response string into JSON object (JSON Format)
		System.out.println("Response JSON from API--->" + responsejson);
	}
	public static CloseableHttpResponse getMeth(String url) throws IOException
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); //http get request
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget); // Hit the get URL
		return closeableHttpResponse;
	}
	
	
}