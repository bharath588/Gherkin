
package webservices.util;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;




public class JsonManager {
	private JSONObject jsonObject;
	private JSONArray jsonArray;

	 public enum JsonType{
		 JSONOBJECT,JSONARRAY
	 }
	 
	 public enum JsonInputType{
		 FILE,HTTPRESPONSE
	 }
	
	private static JsonManager instance;
	public JsonManager(String jsonFileContent,JsonType jsonObjectType,JsonInputType jsonInputType) throws IOException {
		
		 JsonParser jsonParser = new JsonParser();
		 if(jsonInputType == JsonInputType.HTTPRESPONSE){
		 if(jsonObjectType == JsonType.JSONOBJECT){
			 jsonObject = jsonParser.getObjectFromJSON(jsonFileContent);
			 }else{
			 jsonArray = jsonParser.getJsonParsedObjectAsJsonArray(jsonFileContent);
			 }
		 }
		 else{
			 if(jsonObjectType == JsonType.JSONOBJECT){
				 jsonObject = jsonParser.readObjectFromJSON(jsonFileContent);
				 }else{
				 jsonArray = jsonParser.readJsonParsedObjectAsJsonArray(jsonFileContent);
				 }
			 
		 }
	}
	
	
	
	
	   public static JsonManager getInstance(String jsonContent,JsonType jsonObjectType,JsonInputType jsonInputType){
		  
		   
	        if (instance == null) {
	            try {
					instance = new JsonManager(jsonContent,jsonObjectType,jsonInputType);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        return instance;
	    }
	

	public String getJsonFileLocation(String fileName) {
	
		return  System.getProperty("user.dir")+"/"+fileName;
		

	}
//	public   void setJsonFileLocation(String sFileName) {
//		 this.sFileName = System.getProperty("user.dir")+"/"+sFileName;
//		
//		
//
//	}

	public JSONObject getJsonObjectFromKey(String key) {
		boolean hasKey = jsonObject.has(key);
		if (hasKey) {
			return (JSONObject) jsonObject.get(key);
		} else {
			return null;
		}
	}
	
	


	    public JSONArray getJsonArrayFromKey(String key) {
	        return jsonObject.getJSONArray(key);
	    }

	   public JSONArray getJsonArray(){
		   return jsonArray;
	   }

	   public JSONObject getJsonObject(){
		   return jsonObject;
	   }

}
