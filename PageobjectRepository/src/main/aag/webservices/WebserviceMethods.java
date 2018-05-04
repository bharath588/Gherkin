package pageobjects.webservices;

import java.util.List;

import lib.Reporter;
import lib.Stock;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pageobjects.webservices.AgentService;
import webservices.util.JsonReadWriteUtils;
import webservices.util.JsonUtil;
import webservices.util.WebserviceUtil;

import com.aventstack.extentreports.Status;

public class WebserviceMethods {

	AgentService agntserv;
	WebserviceUtil web = null;
	String requestUrl;
	JSONParser parser = new JSONParser();
	JSONObject json=new JSONObject();
	String jsonRequestStringAuth = null;
	String jsonRequestString = null;
	String jsonResponseString = null;
	static String authCode;
	static int randomId=0;
	
	/**<pre>
	 * This method executes the Enrollment webservice and verify the success response
	 * </pre>
	 * @param getReq
	 * @return
	 * @throws Exception 
	 */
	public String runAuthWebservice() throws Exception{
		// TODO Auto-generated method stub
		web = new WebserviceUtil();
		agntserv= new AgentService();
		agntserv.setUsername("122714313ABC");
		agntserv.setPassword("Test@1234");
		agntserv.setAccuCode("Empower");
		agntserv.setAccuAccessTypeCode("PPT_WEB");
		jsonRequestStringAuth = JsonUtil.writeToJson(agntserv);
		
//		8516-Proj-1
//		8517-Proj-2
//		8615-Proj-10
		HttpResponse httpResponseForAuth = web.getResponseasJsonforPostRequest(web.getAuthURL(), jsonRequestStringAuth);
		
		System.out.println(httpResponseForAuth.toString());
		authCode = httpResponseForAuth.getFirstHeader("Authorization").getValue();
		System.out.println("AuthCode: " + authCode);
		Reporter.logEvent(Status.PASS, "Verify Authentication Code", "Authentication code has generated successfully\n"
				+ "Authentication Code : " + authCode, false);
		return authCode;
	}
	
	/**<pre>
	 * This method executes MAX Authentication service and verify the success response
	 * </pre>
	 * @param getReq
	 * @return
	 * @throws Exception 
	 */
	public void runMAXAuthWebservice(String maxAuthURL, String authCode, String ind_Id, String ga_Id) throws Exception{
		// TODO Auto-generated method stub
		web = new WebserviceUtil();
		agntserv= new AgentService();
		if(randomId==0)
			randomId=Integer.parseInt(Stock.GetParameterValue("randomId").toString());
		else
			randomId+=1;
		agntserv.setId(randomId+"");
		agntserv.setToken("JWT "+authCode);
		agntserv.setIndId(ind_Id);
		agntserv.setGaId(ga_Id);
		agntserv.setUserId(Stock.getConfigParam("USERID"));
		jsonRequestStringAuth = JsonUtil.writeToJson(agntserv);
		
		HttpResponse httpResponseForAuth = web.getResponseasJsonforPostRequest(maxAuthURL, jsonRequestStringAuth);
		
		System.out.println(httpResponseForAuth.toString());
		if(httpResponseForAuth.getStatusLine().getStatusCode() == Integer.parseInt(Stock.GetParameterValue("authStatus")))
			Reporter.logEvent(Status.PASS, "Verify the Cloud Authentication Response status", "Successfull Response: Code:201, Message:Success", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify the Cloud Authentication Response status", "NOT a Successfull Response: Code!=201, Message!=Success", false);
	}

	/**<pre>
	 * This method executes the Enrollment webservice and verify the success response
	 * </pre>
	 * @param getReq
	 * @return
	 * @throws Exception 
	 */
	public void runEnrolWebservice(String Ind_Id, List<String> gaList, String enrolChannel) throws Exception {
		// TODO Auto-generated method stub
		web = new WebserviceUtil();
		agntserv= new AgentService();
		agntserv.setId_type("IND_ID");
		agntserv.setInd_id(Ind_Id);
		agntserv.setGaIdList(gaList);
		agntserv.setApplication("PPT_WEB");
		agntserv.setDatabase("pnp");
		agntserv.setSignUpReason("OPT_IN");
		agntserv.setEnrollmentChannel(enrolChannel);
		agntserv.setAdvisoryServiceProvider("IBBOTSON");
		jsonRequestString = JsonUtil.writeToJson(agntserv);
		System.out.println(jsonRequestString);
		
		requestUrl = web.getEnrlURL();
		
		HttpPost httpPostReq = web.getPostRequest(requestUrl, jsonRequestString);
		httpPostReq.addHeader("Authorization", "JWT "+authCode);
		
		HttpResponse httpResponse = web.getResponse(httpPostReq);			
		System.out.println(httpResponse.toString());
		jsonResponseString = web.getHttpResponseAsString(httpResponse);
		System.out.println(jsonResponseString);
		Reporter.logEvent(Status.INFO, "Response form Balance Enrollment service", jsonResponseString, false);
		
		try{
			json = (JSONObject) parser.parse(jsonResponseString);
			Reporter.logEvent(Status.PASS, "Verify JSON Response", "The Response from the webservice is a Valid JSON", false);
			} 
		catch(Exception jse){
			System.out.println("Not a valid Json String:"+jse.getMessage());
			Reporter.logEvent(Status.FAIL, "Verify JSON Response", "The Response from the webservice is NOT a Valid JSON", true);
		}
		for(int i=0;i<gaList.size();i++)
		{
			if(JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$.status[?(@.gaId==\""+gaList.get(i)+"\")].investmentAdvice.['statusCode']").trim().equals("200")
					&& JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$.status[?(@.gaId==\""+gaList.get(i)+"\")].investmentAdvice.['statusMessage']").trim().equals("Success")
					&& JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$.status[?(@.gaId==\""+gaList.get(i)+"\")].statusCode").trim().equals("200")
					&& JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$.status[?(@.gaId==\""+gaList.get(i)+"\")].statusMessage").trim().equals("Success"))
				Reporter.logEvent(Status.PASS, "Verify the Response status", "Successfull Response: Code:200, Message:Success", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify the Response status", "NOt a successfull Response: Code!=200, Message!=Success", false);

			JsonReadWriteUtils.verifyAllocation(jsonResponseString, "$.status[?(@.gaId==\""+gaList.get(i)+"\")]..['percent']");
		}
	}

	/**<pre>
	 * This method executes the Enrollment webservice and verify the failure response
	 * </pre>
	 * @param getReq
	 * @return
	 * @throws Exception 
	 */
	public void runEnrolWebserviceForFailure(String Ind_Id, List<String> gaList, String enrolChannel) throws Exception {
		// TODO Auto-generated method stub
		web = new WebserviceUtil();
		agntserv= new AgentService();
		agntserv.setId_type("IND_ID");
		agntserv.setInd_id(Ind_Id);
		agntserv.setGaIdList(gaList);
		agntserv.setApplication("PPT_WEB");
		agntserv.setDatabase("pnp");
		agntserv.setSignUpReason("OPT_IN");
		agntserv.setEnrollmentChannel(enrolChannel);
		agntserv.setAdvisoryServiceProvider("IBBOTSON");
		jsonRequestString = JsonUtil.writeToJson(agntserv);
		System.out.println(jsonRequestString);
		
		requestUrl = web.getEnrlURL();
		
		HttpPost httpPostReq = web.getPostRequest(requestUrl, jsonRequestString);
		httpPostReq.addHeader("Authorization", "JWT "+authCode);
		
		HttpResponse httpResponse = web.getResponse(httpPostReq);			
		System.out.println(httpResponse.toString());
		jsonResponseString = web.getHttpResponseAsString(httpResponse);
		System.out.println(jsonResponseString);
		Reporter.logEvent(Status.INFO, "Response form Balance Enrollment service",jsonResponseString, false);
		
		try{
			json = (JSONObject) parser.parse(jsonResponseString);
			Reporter.logEvent(Status.PASS, "Verify JSON Response", "The Response from the webservice is a Valid JSON", false);
			} 
		catch(Exception jse){
			System.out.println("Not a valid Json String:"+jse.getMessage());
			Reporter.logEvent(Status.FAIL, "Verify JSON Response", "The Response from the webservice is NOT a Valid JSON", true);
		}
		for(int i=0;i<gaList.size();i++)
		{
			String statusCode = JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$.status[?(@.gaId=='"+gaList.get(0)+"')].statusCode");
			String statusMessage = JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$.status[?(@.gaId=='"+gaList.get(0)+"')].statusMessage");
			
			if(statusCode.equals("604") && statusMessage.equals("Error occured enrolling the Participant in Ibbotson"))
				Reporter.logEvent(Status.PASS, "Validating Status Code ","Expected: 604\nFrom Response: Status Code - "+statusCode+" , \nStatus Message - "+statusMessage,false);
			else
				Reporter.logEvent(Status.FAIL, "Validating Status Code ","Expected: 604\nFrom Response: "+statusCode+" , \nStatus Message - "+statusMessage,false);
		}
	}
	
	/**<pre>
	 * This method executes the Advisory Service Profile webservice and verify the response
	 * </pre>
	 * @param getReq
	 * @return
	 * @throws Exception 
	 */
	public String runAdvServiceProfile(String requestUrl) throws Exception {
		// TODO Auto-generated method stub
		
		HttpGet httpGetReq = new HttpGet(requestUrl);
		httpGetReq.addHeader("Authorization", "JWT "+authCode);
		
		HttpResponse httpGetResp = web.getResponseasJsonforGet(httpGetReq);
		
		System.out.println(httpGetResp.toString());
		jsonResponseString = web.getHttpResponseAsString(httpGetResp);
		System.out.println(jsonResponseString);
		Reporter.logEvent(Status.INFO, "Response form Advisory Services Profile", jsonResponseString, false);
		
		if(httpGetResp.getStatusLine().getStatusCode() == Integer.parseInt(Stock.GetParameterValue("status")))
			Reporter.logEvent(Status.PASS, "Verify the Response status", "Successfull Response: Code:200, Message:Success", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify the Response status", "NOT a Successfull Response: Code!=200, Message!=Success", false);
		
		return jsonResponseString;
	}

	/**<pre>
	 * This method executes the UnEnrollment webservice and verify the success response
	 * </pre>
	 * @param getReq
	 * @return jsonResponseString
	 * @throws Exception 
	 */
	public String runMAXUnEnrolWebservice(String Id, List<String> gaList, String sponsorId, boolean ueReason, String... database) throws Exception {
		// TODO Auto-generated method stub
		web = new WebserviceUtil();
		agntserv= new AgentService();
		agntserv.setIdType(Stock.GetParameterValue("IdType"));
		agntserv.setId(Id);
		if(gaList != null)
			agntserv.setGaIdList(gaList);
		if(sponsorId != null)
			agntserv.setSponsorId(sponsorId);
		if(database.length > 0)
			agntserv.setDatabase(database[0]);
		if(ueReason)
			agntserv.setUnenrollmentReason("Test for Unenrollment");
		jsonRequestString = JsonUtil.writeToJson(agntserv);
		System.out.println(jsonRequestString);
		Reporter.logEvent(Status.INFO, "Body Content for MAX UnEnrollment service", jsonRequestString, false);
		
		HttpPost httpPostReq = web.getPostRequest(Stock.GetParameterValue("serviceURL"), jsonRequestString);
		httpPostReq.addHeader("Authorization", "JWT "+authCode);
		
		HttpResponse httpResponse = web.getResponse(httpPostReq);			
		System.out.println(httpResponse.toString());
		jsonResponseString = web.getHttpResponseAsString(httpResponse);
		System.out.println(jsonResponseString);
		Reporter.logEvent(Status.INFO, "Response from MAX UnEnrollment service", jsonResponseString, false);
		
		try{
			json = (JSONObject) parser.parse(jsonResponseString);
			Reporter.logEvent(Status.PASS, "Verify JSON Response", "The Response from the webservice is a Valid JSON", false);
			} 
		catch(Exception jse){
			System.out.println("Not a valid Json String:"+jse.getMessage());
			Reporter.logEvent(Status.FAIL, "Verify JSON Response", "The Response from the webservice is NOT a Valid JSON", true);
		}
		if(gaList != null)
		{
			if(!Stock.GetParameterValue("respNo").equals("200"))
			{
				for(int i=0;i<gaList.size();i++)
				{
					String statusCode = JsonReadWriteUtils.getOneNodeValueUsingJpath(jsonResponseString, "$.status[0].statusCode");
					String statusMessage = JsonReadWriteUtils.getOneNodeValueUsingJpath(jsonResponseString, "$.status[0].statusMessage");
					
					if(statusCode.equals(Stock.GetParameterValue("respNo")) && statusMessage.equals(Stock.GetParameterValue("respValue")))
						Reporter.logEvent(Status.PASS, "Validating Status Code ","Expected: "+Stock.GetParameterValue("respNo")+
								"\nFrom Response: Status Code - "+statusCode+" , \nStatus Message - "+statusMessage,false);
					else
						Reporter.logEvent(Status.FAIL, "Validating Status Code ","Expected: "+Stock.GetParameterValue("respNo")+
								"\nFrom Response: "+statusCode+" , \nStatus Message - "+statusMessage,false);
				}
			}
			else
			{
				for(int i=0;i<gaList.size();i++)
				{
					if(JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$.status[?(@.gaId==\""+gaList.get(i)+"\")].statusCode").trim().equals(Stock.GetParameterValue("respNo"))
							&& JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$.status[?(@.gaId==\""+gaList.get(i)+"\")].statusMessage").trim().equals(Stock.GetParameterValue("respValue")))
						Reporter.logEvent(Status.PASS, "Verify the Response status", "Ga_Id : "+gaList.get(i)+" Successfull Response: Code:200, Message:Success", false);
					else
						Reporter.logEvent(Status.FAIL, "Verify the Response status", "Ga_Id : "+gaList.get(i)+" NOt a successfull Response: Code!=200, Message!=Success", false);
				}
			}
		}
		else
		{
			if(JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$.status[?(@.gaId==null)].statusCode").trim().equals(Stock.GetParameterValue("respNo"))
					&& JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$.status[?(@.gaId==null)].statusMessage").trim().equals(Stock.GetParameterValue("respValue")))
				Reporter.logEvent(Status.PASS, "Verify the Response status", " Successfull Response: Code:"+Stock.GetParameterValue("respNo")+", Message:"+Stock.GetParameterValue("respValue"), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify the Response status", " NOt a Successfull Response: Code != "+Stock.GetParameterValue("respNo")+", Message != "+Stock.GetParameterValue("respValue"), false);
		}
		
		return jsonResponseString;
	}



	/**<pre>
	 * This method executes the UnEnrollment webservice and verify the success response
	 * </pre>
	 * @param getReq
	 * @return jsonResponseString
	 * @throws Exception 
	 */
	public String runMAXService(String Id, List<String> gaList, String sponsorId, boolean ueReason, String... database) throws Exception {
		// TODO Auto-generated method stub
		web = new WebserviceUtil();
		agntserv= new AgentService();
		agntserv.setIdType(Stock.GetParameterValue("IdType"));
		agntserv.setId(Id);
		if(gaList != null)
			agntserv.setGaIdList(gaList);
		if(sponsorId != null)
			agntserv.setSponsorId(sponsorId);
		if(database.length > 0)
			agntserv.setDatabase(database[0]);
		if(ueReason)
			agntserv.setUnenrollmentReason("Test_M* for Unenrollment");
		
		//Converting Object into JSON
		jsonRequestString = JsonUtil.writeToJson(agntserv);
		System.out.println(jsonRequestString);
		Reporter.logEvent(Status.INFO, "Body Content for MAX UnEnrollment service", jsonRequestString, false);
		
		//Creating http request
		HttpPost httpPostReq = web.getPostRequest(Stock.GetParameterValue("serviceURL"), jsonRequestString);
		//Adding Authorization code
		httpPostReq.addHeader("Authorization", "JWT "+authCode);
		
		//Getting http response
		HttpResponse httpResponse = web.getResponse(httpPostReq);			
		System.out.println(httpResponse.toString());
		jsonResponseString = web.getHttpResponseAsString(httpResponse);
		System.out.println(jsonResponseString);
		Reporter.logEvent(Status.INFO, "Response from MAX UnEnrollment service", jsonResponseString, false);
		
		try{
			json = (JSONObject) parser.parse(jsonResponseString);
			Reporter.logEvent(Status.PASS, "Verify JSON Response", "The Response from the webservice is a Valid JSON", false);
			} 
		catch(Exception jse){
			System.out.println("Not a valid Json String:"+jse.getMessage());
			Reporter.logEvent(Status.FAIL, "Verify JSON Response", "The Response from the webservice is NOT a Valid JSON", true);
		}
		
		return jsonResponseString;
	}

	/**<pre>
	 * This method executes the MAX MA Enrollment webservice and verify the success response
	 * </pre>
	 * @param getReq
	 * @return
	 * @throws Exception 
	 */
	public String runMaxMAEnrolWebservice(String Id, List<String> gaList, String enrolChannel, String db) throws Exception {
		// TODO Auto-generated method stub
		web = new WebserviceUtil();
		agntserv= new AgentService();
		agntserv.setIdType(Stock.GetParameterValue("IdType"));
		agntserv.setId(Id);
		agntserv.setGaIdList(gaList);
		agntserv.setDatabase(db);
		agntserv.setEnrollmentChannel(enrolChannel);
		jsonRequestString = JsonUtil.writeToJson(agntserv);
		System.out.println(jsonRequestString);
		Reporter.logEvent(Status.INFO, "Body Content for MAX UnEnrollment service", jsonRequestString, false);
		
		HttpPost httpPostReq = web.getPostRequest(Stock.GetParameterValue("serviceURL"), jsonRequestString);
		httpPostReq.addHeader("Authorization", "JWT "+authCode);
		
		HttpResponse httpResponse = web.getResponse(httpPostReq);			
		System.out.println(httpResponse.toString());
		jsonResponseString = web.getHttpResponseAsString(httpResponse);
		System.out.println(jsonResponseString);
		Reporter.logEvent(Status.INFO, "Response form Balance Enrollment service", jsonResponseString, false);
		
		try{
			json = (JSONObject) parser.parse(jsonResponseString);
			Reporter.logEvent(Status.PASS, "Verify JSON Response", "The Response from the webservice is a Valid JSON", false);
			} 
		catch(Exception jse){
			System.out.println("Not a valid Json String:"+jse.getMessage());
			Reporter.logEvent(Status.FAIL, "Verify JSON Response", "The Response from the webservice is NOT a Valid JSON", true);
		}
		try{
			for(int i=0;i<gaList.size();i++)
			{
				if(JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$.status[?(@.gaId==\""+gaList.get(i)+"\")].investmentAdvice.['statusCode']").trim().equals(Stock.GetParameterValue("statusCode"))
						&& JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$.status[?(@.gaId==\""+gaList.get(i)+"\")].statusCode").trim().equals(Stock.GetParameterValue("statusCode")))
					Reporter.logEvent(Status.PASS, "Verify the Response status", "Successfull Response: Code:200", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify the Response status", "NOt a successfull Response: Code!="+Stock.GetParameterValue("statusCode"), false);
	
				JsonReadWriteUtils.verifyAllocation(jsonResponseString, "$.status[?(@.gaId==\""+gaList.get(i)+"\")]..['percent']");
			}
		}
		catch(Exception e)
		{
			Reporter.logEvent(Status.FAIL, "Exception Occured", "Exception Occured - "+ e.getMessage(), true);
		}
		return jsonResponseString;
	}
}
