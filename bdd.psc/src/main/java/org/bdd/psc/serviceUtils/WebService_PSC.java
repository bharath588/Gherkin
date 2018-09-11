package org.bdd.psc.serviceUtils;

import java.util.ArrayList;

import org.testng.Assert;

import lib.Stock;
import webservices.util.JsonReadWriteUtils;


public class WebService_PSC {
	private static String remove = "REMOVE";

    private static String blank = "BLANK";



	public String getJSONResponse(String URL,String... data) throws Exception
	{
		WebserviceMethods webMethods = new WebserviceMethods();
		
		String authCode = webMethods.runAuthWebservice();
		
		URL="http://fss-dapps1:"+WebserviceMethods.getSerToProj()+"/"+URL;
		
		String requestURL = formRequestURLPPTWeb(URL, 
	            "db/"+data[0],
	            "gaId/"+data[1],
	            "indId/"+data[2]+"/application/PSC");

		/*String requestURL = formRequestURLPPTWeb(URL, 
		            "db/inst",
		            "gaId/194007-01",
		            "indId/13857250/application/PSC");*/
		        
		String jsonResponseString = webMethods.runAdvServiceProfile(requestURL, authCode);
		
		return jsonResponseString;
	}
	public static ArrayList<String> getPayPeriodsRemainingInYear(String jsonResponseString) throws Exception
	{
		ArrayList<String> ar = new ArrayList<String>();
		String payPeriod = null,payPeriodYearOrMonth= null,sServiceLevel= null;
		
		
		sServiceLevel = JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$..gaServiceLevel").trim();
		if(!sServiceLevel.equalsIgnoreCase("ADJRUN_PAYROLLDATE"))
			payPeriod = JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$..payPeriodsRemainingInYear").trim();
		else
			payPeriod = JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$..numPayPeriods").trim();
		
		payPeriodYearOrMonth = JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$..payrollFreqCode").trim();
		
		if(payPeriodYearOrMonth.equalsIgnoreCase("BW"))
		{
			payPeriodYearOrMonth="26";
		}
		else if(payPeriodYearOrMonth.equalsIgnoreCase("SM"))
		{
			payPeriodYearOrMonth="24";
		}
		Assert.assertFalse(payPeriodYearOrMonth.equalsIgnoreCase("null"));
		Assert.assertFalse(payPeriod.equalsIgnoreCase("null"));
		
		ar.add(payPeriod);
		ar.add(payPeriodYearOrMonth);
		return ar;
	}
	public String getEmployeeValue(String jsonResponseString) throws Exception
	{
		String sServiceLevel= null;
		System.out.println("JSON Resp "+jsonResponseString);
		sServiceLevel = JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$..[?(@.deferralTypeCode==\"BEFORE\")].['employeeValue']").trim();
		System.out.println("From Service "+sServiceLevel);
		return sServiceLevel;
	}
	public String getPayRollType(String jsonResponseString) throws Exception
	{
		String sPayRollType= null;
		System.out.println("JSON Resp "+jsonResponseString);
		sPayRollType = JsonReadWriteUtils.getNodeValueUsingJpath(jsonResponseString, "$..gaServiceLevel").trim();
		System.out.println("From Service "+sPayRollType);
		return sPayRollType;
	}
	
	/**<pre>
     * It returns the request url
     * @param baseURL of the request url mostly static part of the url.
     * @param params other parameters (mandatory or optional) of the request url
     * @return string url
     */
    public static String formRequestURLPPTWeb(String baseURL, String...params)
    {
           String requestURL = baseURL;
           try
           {
                  if(params.length>0)
                  {
                        for(String param : params)
                        {
                               if(!(param.contains(remove)||param.contains(blank)||param.contains("?")))
                               {
                                      if(requestURL.endsWith("/")){
                                             requestURL = requestURL+param;}
                                      else if(requestURL.endsWith("&")){
                                             requestURL=requestURL+param;
                                      }
                                      else{
                                             requestURL = requestURL+"/"+param;
                                      }
                               }
                               if(param.contains(blank))
                               {
                                      param = param.replace(blank, "");
                                      requestURL = requestURL+"/"+param;
                               }
                               if(param.contains("?"))
                               {
                                      if(requestURL.endsWith("/")){
                                             requestURL = requestURL.substring(0,requestURL.length()-1).trim();
                                      }
                                      requestURL = requestURL+param;
                               }
                        }
                        if(requestURL.endsWith("/")){
                               requestURL = requestURL.substring(0,requestURL.length()-1).trim();
                        }
                  }
           }
           catch(Exception e)
           {
                  e.printStackTrace();
           }
           return requestURL;
    }

}
