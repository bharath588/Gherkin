package pageobject.GEEV;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class Geev_Ppt_History {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GEEV_Ppt_History";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;	
	public ResultSet queryResultSet1 = null;	
	public ResultSet queryResultSet2 = null;	
	String subject_id = null;
	String ev_id=null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String STEP_TYPE_LOV;
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
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&LOGON_DATABASE="+LOGON_DATABASE+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+"&STEP_TYPE_LOV="+STEP_TYPE_LOV+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for GEEV service", this.queryString.replaceAll("&", "\n"), false);
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
			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run GEEV service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run GEEV service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
	
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response: ", 
					"EV_ID: "+ doc.getElementsByTagName("EV1_ID_0").item(0).getTextContent()+
					"\nSTART_DATE: "+ doc.getElementsByTagName("SEL_START_DATE_0").item(0).getTextContent()
					, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGEEVSSN")[1], CONTROL_SSN_DISPL_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Subject ID exists in Databse", "SUBJECT ID exists in Database\nTable Name: EVENT", false);
			while(queryResultSet.next()){
				subject_id = queryResultSet.getString("ID");
				Reporter.logEvent(Status.INFO, "Values from Database", "Subject_ID:"+queryResultSet.getString("ID"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Subject ID exists in Databse", "Subject ID doesn't exists in Database\nTable Name: EVENT", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGEEV")[1], subject_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record exists in Databse", "Records exists in Database\nTable Name: EVENT", false);
			while(queryResultSet.next()){
				ev_id = queryResultSet.getString("ID");
				Reporter.logEvent(Status.INFO, "Values from Database", "ID:"+queryResultSet.getString("ID")+
						"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+
						"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record exists in Databse", "Records doesn't exists in Database\nTable Name: EVENT", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGEEV1")[1], ev_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record exists in Databse", "Records exists in Database\nTable Name: STEP", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "EV_ID:"+queryResultSet.getString("EV_ID")+
						"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+
						"\nSETY_CODE: "+queryResultSet.getString("SETY_CODE"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record exists in Databse", "Records doesn't exists in Database\nTable Name: STEP", false);
		}
	}

}
