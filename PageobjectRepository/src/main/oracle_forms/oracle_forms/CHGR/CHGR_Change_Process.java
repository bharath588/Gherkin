package pageobject.CHGR;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
//import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class CHGR_Change_Process {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CHGR_PROCESS";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String LOGON_DATABASE; 	
	String LOGON_PASSWORD;
	String LOGON_USERNAME; 
	String CONTROL_SELECTION_0;
	String GET_GA_GA_ID_0;
	
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	
	String CFG_CONTROL_TODAYS_DATE_0;
	String GET_GA_PLAN_NAME_0;
	String GET_GA_PROD_ID_0;
	String STEP_TYPE_CURSOR_0;
	String STEP_TYPE_DESCR_0;

	public String getCFG_CONTROL_TODAYS_DATE_0() {
		return CFG_CONTROL_TODAYS_DATE_0;
	}
	public String getGET_GA_PLAN_NAME_0() {
		return GET_GA_PLAN_NAME_0;
	}
	public String getGET_GA_PROD_ID_0() {
		return GET_GA_PROD_ID_0;
	}
	public String getSTEP_TYPE_CURSOR_0() {
		return STEP_TYPE_CURSOR_0;
	}
	public String getSTEP_TYPE_DESCR_0() {
		return STEP_TYPE_DESCR_0;
	}
	
	public void setServiceParameters()
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for CHGR service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run CHGR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CHGR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException, Exception{

		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		
		CFG_CONTROL_TODAYS_DATE_0 = doc.getElementsByTagName("CFG_CONTROL_TODAYS_DATE_0").item(0).getTextContent();
		GET_GA_PLAN_NAME_0 = doc.getElementsByTagName("GET_GA_PLAN_NAME_0").item(0).getTextContent();
		GET_GA_PROD_ID_0 = doc.getElementsByTagName("GET_GA_PROD_ID_0").item(0).getTextContent();
		String descr = doc.getElementsByTagName("STEP_TYPE_DESCR_0").item(0).getTextContent();
		
//		System.out.println("CFG_CONTROL_TODAYS_DATE_0: "+CFG_CONTROL_TODAYS_DATE_0+"\nGET_GA_PLAN_NAME_0: "+GET_GA_PLAN_NAME_0+"\nGET_GA_PROD_ID_0: "+GET_GA_PROD_ID_0);
		
		Reporter.logEvent(Status.INFO, "Validating From Response", "CFG_CONTROL_TODAYS_DATE_0: "+CFG_CONTROL_TODAYS_DATE_0+
				"\nGET_GA_PLAN_NAME_0: "+GET_GA_PLAN_NAME_0+"\nGET_GA_PROD_ID_0: "+GET_GA_PROD_ID_0+
				"\nSTEP_TYPE_DESCR_0: "+ descr, false);
	}
	
	public void validateOutputInDatabase() throws SQLException{
		String prod_id_resp = doc.getElementsByTagName("GET_GA_PROD_ID_0").item(0).getTextContent();
		String prod_id_db = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCHGR")[1], this.GET_GA_GA_ID_0);
		if(queryResultSet.next()){
			prod_id_db = queryResultSet.getString("PROD_ID");
			Reporter.logEvent(Status.PASS, "Validate response from Database", "Record exists in database: d_isis", false);
			Reporter.logEvent(Status.INFO, "From Database: \nTable: GROUP_ACCOUNT", "ID: "+queryResultSet.getString("ID")+
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nPROD_ID: "+queryResultSet.getString("PROD_ID")+
					"\nSTATUS CODE: "+queryResultSet.getString("STATUS_CODE"), false);
		}
		else{
			Reporter.logEvent(Status.PASS, "Validate response from Database", "No Record exists in database", false);
		}
		if(prod_id_resp.equalsIgnoreCase(prod_id_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating PROD_ID from Response and Database", "Both the values are same as Expected"+
					"\nPROD_ID: "+"From Response: "+prod_id_resp+"\nFrom Database: "+prod_id_db, false);
		}
	}
	
	
		
}
