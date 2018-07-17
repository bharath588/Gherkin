package appUtils;

import java.util.ArrayList;

import lib.Stock;
import webservices.util.JsonReadWriteUtils;
import webservices.util.WebserviceUtil;

public class WebService_PPT {

	public String getJSONResponse(String URL) throws Exception
	{
		WebserviceUtil web = new WebserviceUtil();

		WebserviceMethods webMethods = new WebserviceMethods();
		
		String authCode = webMethods.runAuthWebservice();

		String requestURL = Common.formRequestURLPPTWeb(URL, 
		            Stock.GetParameterValue("Db_Instance"),
		            Stock.GetParameterValue("groupId"),
		            Stock.GetParameterValue("Ind_Id_Service"));
		        
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
}
