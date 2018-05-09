package pageobject.RCVE;

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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.w3c.dom.Document;

import core.framework.Globals;

public class RCVE_Process {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/RCVE_Process";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private String eventId;
	private ResultSet queryResultSet = null;

	public String getCONTROL_SELECTION_0() {
		return CONTROL_SELECTION_0;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public String getLOGON_DATABASE() {
		return LOGON_DATABASE;
	}
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public String getLOGON_PASSWORD() {
		return LOGON_PASSWORD;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public String getLOGON_USERNAME() {
		return LOGON_USERNAME;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public String getSTEP1_QRY_EV_ID_0() {
		return STEP1_QRY_EV_ID_0;
	}
	public void setSTEP1_QRY_EV_ID_0(String sTEP1_QRY_EV_ID_0) {
		STEP1_QRY_EV_ID_0 = sTEP1_QRY_EV_ID_0;
	}
	public String getDISPLAY_RESULT4_YES4_0() {
		return DISPLAY_RESULT4_YES4_0;
	}
	public void setDISPLAY_RESULT4_YES4_0(String dISPLAY_RESULT4_YES4_0) {
		DISPLAY_RESULT4_YES4_0 = dISPLAY_RESULT4_YES4_0;
	}



	public String getNARRATIVE_NARR_LINE_1_0() {
		return NARRATIVE_NARR_LINE_1_0;
	}



	public void setNARRATIVE_NARR_LINE_1_0(String nARRATIVE_NARR_LINE_1_0) {
		NARRATIVE_NARR_LINE_1_0 = nARRATIVE_NARR_LINE_1_0;
	}



	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String STEP1_QRY_EV_ID_0;
	String DISPLAY_RESULT4_YES4_0;
	String NARRATIVE_NARR_LINE_1_0;
	
	
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setDISPLAY_RESULT4_YES4_0(Stock.GetParameterValue("DISPLAY_RESULT4_YES4_0"));
		this.setNARRATIVE_NARR_LINE_1_0(Stock.GetParameterValue("NARRATIVE_NARR_LINE_1_0"));
		this.setSTEP1_QRY_EV_ID_0(Stock.GetParameterValue("STEP1_QRY_EV_ID_0"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		
		queryString = "?CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
				+"&DISPLAY_RESULT4_YES4_0=" + DISPLAY_RESULT4_YES4_0 
				+ "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&LOGON_PASSWORD=" + LOGON_PASSWORD
				+"&LOGON_USERNAME=" + LOGON_USERNAME
				+"&NARRATIVE_NARR_LINE_1_0=" + NARRATIVE_NARR_LINE_1_0
				+"&STEP1_QRY_EV_ID_0="+STEP1_QRY_EV_ID_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for RCVE service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	
	
	public void runService()
	{
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			//WebDriver dr = new FirefoxDriver();
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run RCVE service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run RCVE service", "Running Failed:", false);
		}
	}
	
	
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
				
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRCVE")[1], this.STEP1_QRY_EV_ID_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of records in Database", "Records exists in Database\nTable Name: DISB_DETL", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+
						"\nLOGON_ID: "+queryResultSet.getString("LOG_USER_LOGON_ID")+
						"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of records in Database", "Records doesn't exists in Database\nTable Name: EVENT", false);
		}
	}
}
