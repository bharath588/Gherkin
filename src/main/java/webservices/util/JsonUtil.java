package webservices.util;

import java.io.FileReader;
import java.io.IOException;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonSyntaxException;


/**<pre>
 * This class contains the utilities to work with JSON document</pre> 
 * @author krsbhr
 *
 */
public class JsonUtil { 

	public static JSONParser jsonparser;
	public static JSONObject jsonObject;
	public static ObjectMapper mapper = null;
	public static Object obj;
	private final static String remove = "REMOVE";
	private final static String blank = "BLANK";

	/**<pre>
	 * This method reads an external json file and returns the json string
	 * </pre>
	 * @param filepath path of external json file
	 * @return Json string
	 */
	public static String readFromExtJsonFile(String filepath) {
		jsonparser = new JSONParser();
		try {
			Object object = jsonparser.parse(new FileReader(filepath));
			jsonObject = (JSONObject) object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject.toJSONString();
	}

	/**<pre>
	 * Writes the object to json and returns json string.
	 * Object should not contain null values.
	 * </pre>
	 * @param object which is needed as Json string
	 * @return json string
	 * @throws Exception
	 */
	public static String writeToJson(Object object) throws Exception {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_DEFAULT);
		String jsonString = mapper.writeValueAsString(object);
		return jsonString;
	}
	
	/**<pre>
	 * Writes the object to json and returns json string.
	 * Object can contain null values.
	 * </pre>
	 * @param object which is needed as Json string
	 * @return json string
	 * @throws Exception
	 */
	public static String writeToJsonInclNull(Object object) throws Exception {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.ALWAYS);
		String jsonString = mapper.writeValueAsString(object);
		return jsonString;
	}

	/**<pre>
	 * Converts json string to json node.
	 * </pre>
	 * @param json
	 * @return root node
	 * @throws JsonSyntaxException
	 * @throws ClassNotFoundException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static JsonNode convertJsonStringtoJsonNode(String json)
			throws JsonSyntaxException, ClassNotFoundException,
			JsonProcessingException, IOException {
		mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		return rootNode;
	}

	/**<pre>
	 * Converts json string to java object
	 * @param json
	 * @param className
	 * @return java object
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Object convertJsonStringtoJavaObject(String json,
			String className) throws JsonParseException, JsonMappingException,
			ClassNotFoundException, IOException {
		mapper = new ObjectMapper();
		obj = mapper.readValue(json, Class.forName(className));
		return obj;
	}
	
	/**<pre>
	 * It returns the request url
	 * @param baseURL of the request url mostly static part of the url.
	 * @param params other parameters (mandatory or optional) of the request url
	 * @return string url
	 */
	public static String formRequestURL(String baseURL, String...params)
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
