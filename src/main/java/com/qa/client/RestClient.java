package com.qa.client;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class RestClient {

	//1. GET Method without headers
	public CloseableHttpResponse getMethod(String url) throws IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault(); /*-->CreateDefault method will create one client connection 
																		-->One http client gets created with object httpClient of type closeableHttpResponse*/
		HttpGet httpget = new HttpGet(url); //http get request (Created one get connection to the provided URL)
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget); // Hit the get URL (Like we click on send button on POSTMAN)
		return closeableHttpResponse;
}
	
	//2. GET Method with headers
	public CloseableHttpResponse getMethod(String url, HashMap<String, String> headermap) throws IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); //http get request
		for(Map.Entry<String, String> entry : headermap.entrySet()) {
			httpget.addHeader(entry.getKey(), entry.getValue());
			}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget); // Hit the get URL
		return closeableHttpResponse;
	}
	//3. Post Method:
	public CloseableHttpResponse postMethod(String url, String entityString, HashMap <String, String> headermap) throws ClientProtocolException, IOException {
		// entityString is Payload (JSON or XML)
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url); //http post request
		httppost.setEntity(new StringEntity(entityString)); // for payload [setEntity(entity) is to define payloads
			
	//for headers:
		for(Map.Entry<String, String> entry : headermap.entrySet()) {
			httppost.addHeader(entry.getKey(), entry.getValue());
			}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httppost); // Hit the post URL
		return closeableHttpResponse;
		}
}