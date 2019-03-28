package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetApiTest extends TestBase {
	
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
	public void getTestWithoutHeaders() throws ClientProtocolException, IOException {
		rc= new RestClient();
		closeableHttpResponse =rc.getMethod(URL);
		// a. Status Code:
		int statusCode= closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code---->" + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status Code is not 200 and received: "+statusCode);
		
		// b. Json String:
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject responsejson = new JSONObject(responseString);
		System.out.println("Response JSON from API--->" + responsejson);
		
		//per_page attribute:
		String perPageValue = TestUtil.getValueByJPath(responsejson, "/per_page");
		System.out.println("Value of per page is--->" +perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		//get the value from JSON Array:
		String Lastname = TestUtil.getValueByJPath(responsejson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responsejson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responsejson, "/data[0]/avatar");
		String firstname = TestUtil.getValueByJPath(responsejson, "/data[0]/first_name");
		
		System.out.println("Lastname is: "+Lastname);
		System.out.println("id is: "+id);
		System.out.println("avatar is: " +avatar);
		System.out.println("Firstname is: "+firstname);
		
		// c. All Headers:
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap <String, String> allHeaders = new HashMap <String, String>();
		for(Header header: headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array ----->" +allHeaders);
		}

	// GetApi test with headers
		@Test (priority = 2)
		public void getTestWithHeaders() throws ClientProtocolException, IOException {
			rc= new RestClient();
			HashMap<String, String> headermap = new HashMap<String, String>();
			headermap.put("Content-Type", "application/json");
			closeableHttpResponse =rc.getMethod(URL, headermap);
			// a. Status Code:
			int statusCode= closeableHttpResponse.getStatusLine().getStatusCode();
			System.out.println("Status Code---->" + statusCode);
			Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status Code is not 200");
			
			// b. Json String:
			String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
			JSONObject responsejson = new JSONObject(responseString);
			System.out.println("Response JSON from API--->" + responsejson);
			
			//per_page attribute:
			String perPageValue = TestUtil.getValueByJPath(responsejson, "/per_page");
			System.out.println("Value of per page is--->" +perPageValue);
			Assert.assertEquals(Integer.parseInt(perPageValue), 3);
			
			//get the value from JSON Array:
			String Lastname = TestUtil.getValueByJPath(responsejson, "/data[0]/last_name");
			String id = TestUtil.getValueByJPath(responsejson, "/data[0]/id");
			String avatar = TestUtil.getValueByJPath(responsejson, "/data[0]/avatar");
			String firstname = TestUtil.getValueByJPath(responsejson, "/data[0]/first_name");
			
			System.out.println(Lastname);
			System.out.println(id);
			System.out.println(avatar);
			System.out.println(firstname);
			
			// c. All Headers:
			Header[] headersArray = closeableHttpResponse.getAllHeaders();
			HashMap <String, String> allHeaders = new HashMap <String, String>();
			for(Header header: headersArray) {
				allHeaders.put(header.getName(), header.getValue());
			}
			System.out.println("Headers Array ----->" +allHeaders);
			}

}
 
