package pageobject.CHPA;

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

public class CHPA_Change_Participant {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CHPA_NEW";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	
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
	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		 CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false"; 
		
		Reporter.logEvent(Status.INFO, "Prepare test data for CHPA service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run CHPA service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CHPA service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response:", "CONTROL_PLAN_NAME_0: "+doc.getElementsByTagName("CONTROL_PLAN_NAME_0").item(0).getTextContent()+
					"\nCONTROL_GA_ID_0: "+doc.getElementsByTagName("CONTROL_GA_ID_0").item(0).getTextContent()+
					"\nFIRST NAME: "+doc.getElementsByTagName("INDIVIDUAL_FIRST_NAME_0").item(0).getTextContent()+
					"\nBIRTH DATE: "+doc.getElementsByTagName("INDIVIDUAL_BIRTH_DATE_DISPLAY_0").item(0).getTextContent()+
					"\nPROD ID: "+doc.getElementsByTagName("CONTROL_PROD_ID_0").item(0).getTextContent()+
					"\nHIREDATE: "+doc.getElementsByTagName("EMPLOYMENT_LOA_LOA_HIREDATE_DISPLAY_0").item(0).getTextContent()+
					"\nSTATUS CODE:PART AGRMT "+doc.getElementsByTagName("PART_SUBSET_GCS_BASIS_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
		}
					
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCHPAIndividual")[1], this.CONTROL_SSN_DISPL_0);
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validate response from Database\nTable: INDIVIDUAL", "ID: "+queryResultSet.getString("ID")+
					"\nSSN: "+queryResultSet.getString("SSN")+
					"\nFIRST_NAME: "+queryResultSet.getString("FIRST_NAME"), false);
		}
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCHPAPartAgrmt")[1], this.CONTROL_SSN_DISPL_0);
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validate response from Database\nTable: PART_AGRMT", "GA_ID: "+queryResultSet.getString("GA_ID")+
					"\nIND ID: "+queryResultSet.getString("IND_ID")+
					"\nSTATUS CODE: "+queryResultSet.getString("STATUS_CODE")+
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE"), false);
		}
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCHPAAddress")[1], this.CONTROL_SSN_DISPL_0);
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validate response from Database\nTable: ADDRESS", "CITY: "+queryResultSet.getString("CITY")+
					"\nZIP CODE: "+queryResultSet.getString("ZIP_CODE")+
					"\nSTATE CODE: "+queryResultSet.getString("STATE_CODE")+
					"\nCOUNTRY: "+queryResultSet.getString("COUNTRY"), false);
		}
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCHPAEmployment")[1], this.CONTROL_SSN_DISPL_0);
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validate response from Database\nTable: EMPLOYMENT", "HIREDATE: "+queryResultSet.getString("HIRE_DATE"), false);
		}
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCHPAPartSubset")[1], this.CONTROL_SSN_DISPL_0);
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validate response from Database\nTable: PART_SUBSET", "STATUS: "+queryResultSet.getString("GCS_BASIS")+
					"\nGCS_VALUE: "+queryResultSet.getString("GCS_VALUE"), false);
		}
		
	}
}
