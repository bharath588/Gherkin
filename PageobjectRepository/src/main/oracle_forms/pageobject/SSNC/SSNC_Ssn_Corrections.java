package pageobject.SSNC;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class SSNC_Ssn_Corrections {
	
	public static String queryString;
	private static String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SSNC_Correct_SSN";
	private static DocumentBuilderFactory docBuilderFactory;
	private static DocumentBuilder docBuilder;
	private static Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet1;
	private ResultSet queryResultSet2;


	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME; 
	String CONTROL_SSN_0;
	String CORRECT_SSN_SSN_0;
	
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
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
	public void setCONTROL_SSN_0(String cONTROL_SSN_0) {
		CONTROL_SSN_0 = cONTROL_SSN_0;
	}
	public void setCORRECT_SSN_SSN_0(String cORRECT_SSN_SSN_0) {
		CORRECT_SSN_SSN_0 = cORRECT_SSN_SSN_0;
	}
	

	String INDIVIDUAL_BIRTH_DATE_0;
	String INDIVIDUAL_CITY_0;
	String INDIVIDUAL_FIRST_LINE_MAILING_0;
	String INDIVIDUAL_GA_ID_0;
	String INDIVIDUAL_ID_0;
	String INDIVIDUAL_MAILING_NAME_1_0;
	String INDIVIDUAL_POLICY_COUNT_0;
	String INDIVIDUAL_POLICY_NAME_0;
	String INDIVIDUAL_SEX_0;	
	String INDIVIDUAL_STATE_CODE_0;
	String INDIVIDUAL_ZIP_CODE_0;
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));		
		 this.setCORRECT_SSN_SSN_0(Stock.GetParameterValue("CORRECT_SSN_SSN_0"));
		 this.setCONTROL_SSN_0(Stock.GetParameterValue("CONTROL_SSN_0"));
		 
		queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTogetUpdatedSSN")[1], CONTROL_SSN_0);
		
		if(DB.getRecordSetCount(queryResultSet1) != 0){
			queryResultSet2 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTogetSSN")[1]);
			if(queryResultSet2.next()){
				this.setCONTROL_SSN_0(queryResultSet2.getString("SSN"));
			}
		}
		
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
			"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SSN_0="+CONTROL_SSN_0+"&CORRECT_SSN_SSN_0="+CORRECT_SSN_SSN_0+
			"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
	
	Reporter.logEvent(Status.INFO, "Prepare test data for SSNC service", this.queryString.replaceAll("&", "\n"), false);
		
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
		//	System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run SSNC Social Security Number Corrections service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SSNC Social Security Number Corrections service", "Running Failed:", false);
		}
		
	}
	
	public void validateOutput(){
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			INDIVIDUAL_GA_ID_0 = doc.getElementsByTagName("INDIVIDUAL_GA_ID_0").item(0).getTextContent();
			INDIVIDUAL_ID_0 = doc.getElementsByTagName("INDIVIDUAL_ID_0").item(0).getTextContent();
			
			Reporter.logEvent(Status.INFO, "From INPUT DATA Before EXECUTION:" , "INDIVIDUAL_SSN: " + CONTROL_SSN_0, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		
	}
	
	public void validateInDatabase() throws SQLException{
		String ssn = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTogetUpdatedSSN")[1], CORRECT_SSN_SSN_0);
	
		if(queryResultSet.next())
		{
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Record has been created", false);
			String INDIVIDUAL_GA_ID_01 = queryResultSet.getString("GA_ID");
			String INDIVIDUAL_ID_01 = queryResultSet.getString("IND_ID");
			ssn = queryResultSet.getString("SSN");
			
			Reporter.logEvent(Status.INFO, "From DATABASE:\nTable: PART_AGRMT " , "INDIVIDUAL_GA_ID_0: " + INDIVIDUAL_GA_ID_01+
					 "\nINDIVIDUAL_ID_0: " + INDIVIDUAL_ID_01+"\n SSN : "+ ssn, false);
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(this.CORRECT_SSN_SSN_0.equalsIgnoreCase(ssn)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Corrected SSN before and after Execution", "Both the values are same as Expected"+
					"\nSSN: "+"Expected: "+this.CORRECT_SSN_SSN_0+"\nActual: "+ssn, false);
		}
 
	}

	
}
