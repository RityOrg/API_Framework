package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostApiTest {

	public class PostAPITest extends TestBase {
		
		TestBase testBase;
		String ServiceURL;
		String ApiURL;
		String URL;
		RestClient rc;
		CloseableHttpResponse closeableHttpResponse;
		
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		testBase = new TestBase();
		ServiceURL = prop.getProperty("URL");
		ApiURL = prop.getProperty("serviceURL");
		URL = ServiceURL + ApiURL;
		RestClient rc= new RestClient();
		rc.getMethod(URL);	
		}
	
		 // GetApi test without headers
	@Test (priority = 1)
	public void postTest() throws JsonGenerationException, JsonMappingException, IOException {
			rc = new RestClient();
			HashMap<String, String> headermap = new HashMap<String, String>();
			headermap.put("Content-Type", "application/json");
			
		// jackson  API: to convert java object (POJO) to Json object
			// beacause we need to send the data in json format which is present in Users.java in form of java object
			ObjectMapper mapper = new ObjectMapper();
			Users us = new Users("Morpheus","Leader"); // Expected user object
			
		//java Object to json file conversion (Marshalling)
		// at the run time mapper.writeValue will create a json file and json object into the users.json	
			mapper.writeValue(new File("C:\\RityEclip\\Projects\\Workspace\\restapi\\src\\main\\java\\com\\qa\\data\\Users.json"), us);
			
		//json object to json in string
			String usersJsonString = mapper.writeValueAsString(us);
			System.out.println(usersJsonString);
			closeableHttpResponse = rc.postMethod(URL, usersJsonString, headermap);   // call the API
			
	// Validate response from API:					
	//1. Status Code:
			int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
			Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_201);
	//2. Json String:
			
			String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");//this will give raw data including status code etc..
			JSONObject responseJson = new JSONObject(responseString);// purpose of doing this is to get only json object in json format
			System.out.println("The response from API is: "+ responseJson);
			
	// Json to Java object (unmarshalling)	
			Users userResObj = mapper.readValue(responseString, Users.class); // actual users object got as response
			System.out.println("Json to Java Object is: "+userResObj);
			Assert.assertTrue(us.getName().equals(userResObj.getName()));
			Assert.assertTrue(us.getJob().equals(userResObj.getJob()));
			System.out.println(userResObj.getId());
			System.out.println(userResObj.getCreatedAt());
	}
}
	}