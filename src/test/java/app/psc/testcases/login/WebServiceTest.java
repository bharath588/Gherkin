package app.psc.testcases.login;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import webservices.util.JsonManager;
import webservices.util.JsonManager.JsonInputType;
import webservices.util.JsonManager.JsonType;







public class WebServiceTest {
	
 JsonManager jsonManager;
	 
	 Map<String,String> jSonHashMap = new HashMap<String, String>();
	 
	 
	 public static void main(String a[]) {
		 new WebServiceTest().readJsonFileForPutOperation();
	 }
	 
	 public void readJsonFileForPutOperation() {
		
		 String fileName =System.getProperty("user.dir")+"//"+"test2.json";
	

	 	jsonManager = JsonManager.getInstance(fileName,JsonType.JSONARRAY,JsonInputType.FILE);
	
		  JSONArray jsonArrays =  jsonManager.getJsonArray();
		  for(Object indexObj :jsonArrays) {
			   JSONObject obj = (JSONObject) indexObj;		   
			   JSONObject obj2 = obj.getJSONObject("myRelatedIndividual");
			   JSONArray jsonArrays2 = obj.getJSONArray("myAddresses");
			  System.out.println( obj2.get("indId"));
	  }
	  }


	 
	 
	 
	 public void readJsonFileForGetOperation() throws Exception{
		 String responseString = "Http response";

		 	jsonManager = JsonManager.getInstance(responseString,JsonType.JSONOBJECT,JsonInputType.HTTPRESPONSE);
		
		  JSONArray jsonArrays =  jsonManager.getJsonArrayFromKey("grpLoanStrucLoanReasons");
		  for(Object indexObj :jsonArrays) {
			  JSONObject obj = (JSONObject) indexObj;						 
			  if(obj.getString("loanReasonCode").equalsIgnoreCase("loanReasonCode")){
			    	 for (Object key : obj.keySet()) {
			    	  String keyStr = key.toString();				    	
					  String keyvalue = (String)obj.get(keyStr).toString();
			    	 jSonHashMap.put(keyStr, keyvalue);
			      
			    	 }
			     }			   
			  
		  }

	}
}
