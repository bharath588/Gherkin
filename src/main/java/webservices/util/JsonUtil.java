package webservices.util;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lib.Stock;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JsonUtil { 

	public static JSONParser jsonparser;
	public static JSONObject jsonObject;
	public static ObjectMapper mapper = null;
	public static Object obj;

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

	public static String writeToJson(Object object) throws Exception {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_DEFAULT);
		String jsonString = mapper.writeValueAsString(object);
		return jsonString;
	}
	public static String writeToJsonInclNull(Object object) throws Exception {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.ALWAYS);
		String jsonString = mapper.writeValueAsString(object);
		return jsonString;
	}

	public static JsonNode convertJsonStringtoJsonNode(String json)
			throws JsonSyntaxException, ClassNotFoundException,
			JsonProcessingException, IOException {
		mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		return rootNode;
	}

	public static Object convertJsonStringtoJavaObject(String json,
			String className) throws JsonParseException, JsonMappingException,
			ClassNotFoundException, IOException {
		mapper = new ObjectMapper();
		obj = mapper.readValue(json, Class.forName(className));
		return obj;
	}
}
