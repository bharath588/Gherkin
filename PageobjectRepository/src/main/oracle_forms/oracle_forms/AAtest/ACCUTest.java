package pageobject.AAtest;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;
import oracleforms.common.OracleForms;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class ACCUTest {
	public static OracleForms of;
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ACCU";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public static String queryStringFinal;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String GET_GA_GA_ID_0_X1;
	String ServiceName;
	String queryStringPart1;
	
	static String inputParams[]={"CONTROL_SELECTION_0","LOGON_DATABASE","LOGON_PASSWORD","LOGON_USERNAME","GET_GA_GA_ID_0","GET_GA_GA_ID_0_X1"};
	static String outputParams[]={"ACCESS_CUST_VIEW_ACCESS_TYPE_CODE_0","ACCESS_CUST_VIEW_ACCU_CODE_0",
		"ACCESS_CUST_VIEW_AUDI_CODE_0","ACCESS_CUST_VIEW_PORTAL_0","ACCESS_CUST_VIEW_PUBLISHED_ACCESS_IND_0"};
	static String queryParams[]={Stock.GetParameterValue("GET_GA_GA_ID_0_X1")};
	
	public static void initializeValues() {
		//initializeValues();
		
		System.out.println("Executing intialize values method");
		of=new OracleForms();
		//of.initializeParams("CONTROL_SELECTION_0","LOGON_DATABASE","LOGON_PASSWORD","LOGON_USERNAME","GET_GA_GA_ID_0","GET_GA_GA_ID_0_X1");
		//of.initializeParams(inputParams);
		}
	public static void setToQueryString1(){
		System.out.println("setToQueryString");
		/*This also we can pass from Test data sheet.
		 * String queryString2="&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";*/
		OracleForms of=new OracleForms();
		String initialQueryString=of.setQueryString(inputParams);
		System.out.println("initialQueryString: "+initialQueryString);
		queryStringFinal="?"+initialQueryString+"numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		System.out.println("queryStringFinal: "+queryStringFinal);
		
		Reporter.logEvent(Status.INFO, "Prepare test data for ACCU service", queryStringFinal.replaceAll("&", "\n"), false);
		
	}
	
	//Just keeping these here. Else we can directly call from Test.java also like:
	//of.runService() or of.runServiceRemoveExtras
	public void runService(){
		of=new OracleForms();
		of.runService(Stock.GetParameterValue("ServiceName"), queryStringPart1,queryStringFinal);
		
		
	}
	
	public static void validateOutput(){
		of=new OracleForms();
		try {
			of.validateOutput();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runServiceRemoveExtra(){
		of=new OracleForms();
		//of.runService(Stock.GetParameterValue("CONTROL_SELECTION_0"), queryStringFinal);
		of.runService(Stock.GetParameterValue("ServiceName"),queryStringPart1, queryStringFinal);
		
	}
	
	
	
	public static void captureAuraPlayerTagValues(){
		of=new OracleForms();
		of.validateOutputCaptureTagValues(outputParams);
	}
	
	// this is when we are querying with one param in DB
	//Like this make it dynamic for 0,1,2 and so on params
	public void validateInDatabase() throws SQLException{
		of=new OracleForms();
		//of.validateInDatabase(Stock.GetParameterValue("GET_GA_GA_ID_0_X1"));
		
	}
	
	/*public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	public void setGET_GA_GA_ID_0_X1(String gET_GA_GA_ID_0_X1) {
		GET_GA_GA_ID_0_X1 = gET_GA_GA_ID_0_X1;
	}*/
	
	/*public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setGET_GA_GA_ID_0_X1(Stock.GetParameterValue("GET_GA_GA_ID_0_X1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&GET_GA_GA_ID_0_X1="+GET_GA_GA_ID_0_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for ACCU service", this.queryString.replaceAll("&", "\n"), false);
		
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run ACCU service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ACCU service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response ", "ACCESS TYPE CODE: "+doc.getElementsByTagName("ACCESS_CUST_VIEW_ACCESS_TYPE_CODE_0").item(0).getTextContent()+
					"\nACCU CODE: "+doc.getElementsByTagName("ACCESS_CUST_VIEW_ACCU_CODE_0").item(0).getTextContent()+
					"\nAUDI CODE: "+doc.getElementsByTagName("ACCESS_CUST_VIEW_AUDI_CODE_0").item(0).getTextContent()+
					"\nPORTAL: "+doc.getElementsByTagName("ACCESS_CUST_VIEW_PORTAL_0").item(0).getTextContent()+
					"\nACCESS IND: "+doc.getElementsByTagName("ACCESS_CUST_VIEW_PUBLISHED_ACCESS_IND_0").item(0).getTextContent(), false);
		}
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
			
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForACCU")[1], this.GET_GA_GA_ID_0_X1);		
		if(DB.getRecordSetCount(queryResultSet)>0){
			
				Reporter.logEvent(Status.PASS, "Validating Records exists in DB", "Records exists in DB", false);
				
				Reporter.logEvent(Status.PASS, "From DATABASE: \nTable Name: ACCESS_CUSTOMIZATION_VIEW", 
						"Total Records: "+DB.getRecordSetCount(queryResultSet), false);
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
*/	
}
