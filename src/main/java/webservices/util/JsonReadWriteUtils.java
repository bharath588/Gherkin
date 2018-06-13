package webservices.util;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.Reporter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Workbook;

import com.aventstack.extentreports.Status;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
@SuppressWarnings("deprecation")
public class JsonReadWriteUtils {

	FileInputStream inp;
	Workbook workbook;
	static DocumentContext jsonDocContext;
	static String actualValue;
	static String jsonPath = null;

	public static String getNodeValueUsingJpath(String jsonString, String jpath) {
		List<?> list = null;
		Object val = null;
		String val2;
		jsonDocContext = JsonPath.parse(jsonString);
		list = jsonDocContext
				.read(JsonPath.compile(jpath));
		if(list.size() > 0)
		{
			val = list.get(0);
			if(val == null )
			{
				val2 = "";
			}else{
				val2 = val.toString();
			}
			return val2;
		}
		else
			Reporter.logEvent(Status.INFO, "Matching Json Node", "There are no matches found for the current JsonPath or it gives a null value", false);
		return " ";
	}

	public static String getOneNodeValueUsingJpath(String jsonString, String jpath) {
		String list = null;
		jsonDocContext = JsonPath.parse(jsonString);
		list = jsonDocContext
				.read(JsonPath.compile(jpath)).toString();
		if(list != null)
		{
			return list;
		}
		else
		{
			Reporter.logEvent(Status.INFO, "Matching Json Node", "There are no matches found for the current JsonPath or it gives a null value", false);
			return " ";
		}
	}
	
	public static boolean verifyAllocation(String jsonString, String jpath) {
		List<?> list = null;
		int sum=0;
		jsonDocContext = JsonPath.parse(jsonString);
		list = jsonDocContext
				.read(JsonPath.compile(jpath));
		for(int i=0;i<list.size();i++)
		{
			sum+=Double.parseDouble(list.get(i).toString());
		}
		if(sum==100)
		{
			Reporter.logEvent(Status.PASS, "Funds Allocation Verification", "Sum of Fund Allocations are equal to 100", false);
			return true;
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Funds Allocation Verification", "Sum of Fund Allocations are NOT equal to 100", false);
			return false;
		}
	}

	public static void setJsonNodeValues(Map<String, String> testdata,
			String jsonString) {
		jsonDocContext = JsonPath.parse(jsonString);

		for (Map.Entry<String, String> jsonNodeValues : testdata.entrySet()) {

			if (StringUtils
					.containsIgnoreCase(jsonNodeValues.getKey(), "INPUT"))

				jsonDocContext.set(JsonPath.compile(jsonNodeValues.getKey()),
						jsonNodeValues.getValue());

		}
	}

	public static void verifyJsonNodeValues(Map<String, String> testdata,
			String jsonString) {
		jsonDocContext = JsonPath.parse(jsonString);
		List<String> list = null;
		for (Map.Entry<String, String> jsonNodeValues : testdata.entrySet()) {

			if (StringUtils.containsIgnoreCase(jsonNodeValues.getKey(),
					"VERIFY"))
			{
				jsonPath = (jsonNodeValues.getKey()).substring(9);
				list = jsonDocContext
						.read(JsonPath.compile(jsonPath));
				Reporter.logEvent(Status.INFO, "The number of matching nodes for "+jsonPath, "The number of matches are "+list.size(), false);
				actualValue = list.get(0);

				if (actualValue.equalsIgnoreCase(jsonNodeValues.getValue())) {
					Reporter.logEvent(Status.PASS, "Verify if the expected value matches with the actual value in the json response", "The expected and the actual values are same "+"Expected : "+jsonNodeValues.getValue()+"Actual : "+ actualValue,
							false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify if the expected value matches with the actual value in the json response", "The expected and the actual values are different "+"Expected : "+jsonNodeValues.getValue()+"Actual : "+ actualValue,
							false);
				}
			}
		}
	}

	public static int getNumberOfMatchingNodes(String jsonString, String jpath) {
		jsonDocContext = JsonPath.parse(jsonString);
		List<?> nodesList = jsonDocContext.read(jpath);
		return nodesList.size();
	}

	public static List<?> getAllMatchingNodeValues(String jsonString,
			String jpath) {
		jsonDocContext = JsonPath.parse(jsonString);
		List<?> nodesList = jsonDocContext.read(jpath);
		return nodesList;
	}

	public static Map<String,String> getMatchingNodes(String jsonString,String jpath) 
	{
		jsonDocContext = JsonPath.parse(jsonString);
		net.minidev.json.JSONArray  arr = jsonDocContext.read(jpath);
		List<?> nodeList = arr;
		return null;
	}

	public static void putJsonValuesInMap(String jsonString,String jpath)
	{
		jsonDocContext = JsonPath.parse(jsonString);
	}

	private static  boolean compareTwoStrings(String actual,String expected)
	{
		if(StringUtils.isBlank(actual))
		{
			actual = " ";
		}
		if(StringUtils.isBlank(expected))
		{
			expected = " ";
		}
		if(actual.equalsIgnoreCase(expected))
		{
			return true;
		}else{
			return false;
		}
	}
	public static void compareValueAndLogReport(String actual,String expected,String nodeName)
	{

		try {
			if(compareTwoStrings(actual,expected))
			{
				Reporter.logEvent(Status.PASS, "Comparing reponse values with database for :\n"+nodeName, "In Response : \n" + actual +"\nIn Database :"+ expected , false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing reponse values with database for :\n"+nodeName, "In Response : \n" + actual +"\nIn Database :"+ expected , false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void compareValueAndLogReport(Set<String> actual,String expected,String nodeName)
	{

		try {
			if(actual.contains(expected))
			{
				Reporter.logEvent(Status.PASS, "Comparing reponse values with database for :\n"+nodeName, "In Response : \n" + actual +"\nIn Database :"+ expected , false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing reponse values with database for :\n"+nodeName, "In Response : \n" + actual +"\nIn Database :"+ expected , false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void compareDbWithDateInResponse(String actualDate,Date expectedDate,String nodeName) throws ParseException
	{
		String expctdDt = null;
		if(expectedDate == null)
		{
			expctdDt = "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			expctdDt = sdf.format(expectedDate);
		}
		if(actualDate.equalsIgnoreCase(expctdDt))
		{
			Reporter.logEvent(Status.PASS, "Comparing reponse values with database for : \n"+nodeName, "In Response : \n" + actualDate +"\nIn Database "+ expectedDate , false);
		}else{
			Reporter.logEvent(Status.FAIL, "Comparing reponse values with database for : \n"+nodeName, "In Response : \n" + actualDate +"\nIn Database "+ expectedDate , false);
		}
	}
	public static boolean compareDbWithDateInResponse(String actualDate,Date expectedDate) throws ParseException
	{
		String expctdDt = null;
		if(expectedDate == null)
		{
			expctdDt = "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			expctdDt = sdf.format(expectedDate);
		}
		if(actualDate.equalsIgnoreCase(expctdDt))
		{
			return true;
		}else{
			return false;
		}
	}

	/**Method to compare two string values if the string consists of only numbers
	 * @param a First string which contains only numbers
	 * @param b Second string which contains only numbers
	 * @param nodeName Field which values are to be compared (Webservice response Vs. DB value)
	 * @return true if passed values are equal otherwise false.
	 */

	public static boolean compareNumericValues(String a, String b, String nodeName){
		if(NumberUtils.isNumber(a)&&NumberUtils.isNumber(b)){
			float actual = Float.valueOf(a);
			float expected = Float.valueOf(b);
			NumberComparator numbrComp = new JsonReadWriteUtils().new NumberComparator();
			if(numbrComp.compare(actual, expected)==0){
				Reporter.logEvent(Status.PASS, "Comparing response values with database for: \n"+nodeName, "In Response:\n"+a+"\n In Database:\n"+b, false);
				return true;
			}
			else{
				Reporter.logEvent(Status.FAIL, "Comparing response values with database for: \n"+nodeName, "In Response:\n"+a+"\n In Database:\n"+b, false);
				return false;
			}
		}
		else{
			throw new IllegalArgumentException ("Passed arguments are not numeric");		}
	}

	/**Inner class which implements comparator interface to compare two numbers
	 * @author rvpndy
	 *
	 */
	class NumberComparator implements Comparator<Number>{

		@Override
		public int compare(Number a, Number b) {
			// TODO Auto-generated method stub
			return new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString()));

		}

	}
}