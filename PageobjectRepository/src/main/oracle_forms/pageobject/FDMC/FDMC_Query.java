package pageobject.FDMC;

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

public class FDMC_Query {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/FDMC_QUERY";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String SELECTION_DMTY_CODE_0;
	String SELECTION_START_GA_ID_0;
	String SELECTION_START_EFFDATE_0;
	String SELECTION_END_EFFDATE_0;
	
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
	public void setSELECTION_DMTY_CODE_0(String sELECTION_DMTY_CODE_0) {
		SELECTION_DMTY_CODE_0 = sELECTION_DMTY_CODE_0;
	}
	public void setSELECTION_START_GA_ID_0(String sELECTION_START_GA_ID_0) {
		SELECTION_START_GA_ID_0 = sELECTION_START_GA_ID_0;
	}
	public void setSELECTION_START_EFFDATE_0(String sELECTION_START_EFFDATE_0) {
		SELECTION_START_EFFDATE_0 = sELECTION_START_EFFDATE_0;
	}
	public void setSELECTION_END_EFFDATE_0(String sELECTION_END_EFFDATE_0) {
		SELECTION_END_EFFDATE_0 = sELECTION_END_EFFDATE_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setSELECTION_DMTY_CODE_0(Stock.GetParameterValue("SELECTION_DMTY_CODE_0"));
		 this.setSELECTION_END_EFFDATE_0(Stock.GetParameterValue("SELECTION_END_EFFDATE_0"));
		 this.setSELECTION_START_EFFDATE_0(Stock.GetParameterValue("SELECTION_START_EFFDATE_0"));
		 this.setSELECTION_START_GA_ID_0(Stock.GetParameterValue("SELECTION_START_GA_ID_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&SELECTION_DMTY_CODE_0="+SELECTION_DMTY_CODE_0+"&SELECTION_START_GA_ID_0="+SELECTION_START_GA_ID_0+"&SELECTION_START_EFFDATE_0="+SELECTION_START_EFFDATE_0+
				 "&SELECTION_END_EFFDATE_0="+SELECTION_END_EFFDATE_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false" ;
	
		 Reporter.logEvent(Status.INFO, "Prepare test data for FDMC service", this.queryString.replaceAll("&", "\n"), false);
		 
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
//		System.out.println(serviceURL);
		doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run FDMC service", "Running Failed:", false);
		}
	}
	
	public void validateOutput(){
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			System.out.println(doc.getElementsByTagName("GROUP_ACCOUNT_DMTY_CODE_0").item(0).getTextContent());
			Reporter.logEvent(Status.INFO, " Values From Response of Service ","DMTY_CODE: "+doc.getElementsByTagName("GROUP_ACCOUNT_DMTY_CODE_0").item(0).getTextContent()+
					"\nSTATUS CODE: "+doc.getElementsByTagName("GROUP_ACCOUNT_DPX_STATUS_CODE_0").item(0).getTextContent()+
					"\nEFF DATE: "+doc.getElementsByTagName("GROUP_ACCOUNT_EFFDATE_0").item(0).getTextContent()+
					"\nGA ID: "+doc.getElementsByTagName("GROUP_ACCOUNT_GA_ID_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		
	}
	
	public void validateInDatabase() throws SQLException{
		String DMTY_CODE = doc.getElementsByTagName("GROUP_ACCOUNT_DMTY_CODE_0").item(0).getTextContent();
		String dmtyString = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToVerifyFDMC")[1], SELECTION_START_GA_ID_0, SELECTION_DMTY_CODE_0, SELECTION_START_EFFDATE_0, SELECTION_END_EFFDATE_0);
	
		if(queryResultSet.next())
		{
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Record has been created", false);
			String GA_ID= queryResultSet.getString("GA_ID");
			dmtyString = queryResultSet.getString("DMTY_CODE");
			String DOC_ID = queryResultSet.getString("DOC_ID");
			String EFF_DATE = queryResultSet.getString("EFFDATE");
			Reporter.logEvent(Status.INFO, "Values From DATABASE:" , "GA_ID: " + GA_ID+
					 "\nDMTY_CODE: " + dmtyString+"\nDOC_ID: "+ DOC_ID+ "\nEFF DATE: "+EFF_DATE, false);
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(DMTY_CODE.equalsIgnoreCase(dmtyString)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating DMTY CODE from Response and Database", "Both the values are same as Expected"+
					"\nDMTY CODE: "+"From Response: "+DMTY_CODE+"\nFrom Database: "+dmtyString, false);
		}

	}
}
