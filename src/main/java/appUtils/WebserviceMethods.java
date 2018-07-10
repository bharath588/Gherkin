package appUtils;

import lib.Reporter;
import lib.Stock;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
		agntserv.setUsername(Stock.GetParameterValue("userName"));
		agntserv.setPassword(Stock.GetParameterValue("password"));
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
	 * This method executes the Advisory Service Profile webservice and verify the response
	 * </pre>
	 * @param getReq
	 * @return
	 * @throws Exception 
	 */
	public String runAdvServiceProfile(String requestUrl, String authCode) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(requestUrl);
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

}
