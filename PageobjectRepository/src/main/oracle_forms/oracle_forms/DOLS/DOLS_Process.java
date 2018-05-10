package pageobject.DOLS;

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

public class DOLS_Process {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DOLS_Process_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private String eventId;
	private ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String STEP1_QRY_EV_ID_0;
	
	
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
	
	
	
	
	public void setServiceParameters()
	{		
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSTEP1_QRY_EV_ID_0(Stock.GetParameterValue("STEP1_QRY_EV_ID_0"));		
		
		queryString = "?CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
				+"&LOGON_DATABASE=" + LOGON_DATABASE 
				+ "&LOGON_PASSWORD=" + LOGON_PASSWORD 
				+"&LOGON_USERNAME=" + LOGON_USERNAME
				+"&STEP1_QRY_EV_ID_0=" + STEP1_QRY_EV_ID_0 
				+ "&numOfRowsInTable=20&json=false&handlePopups=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for DOLS service", this.queryString.replaceAll("&", "\n"), false);
	}		
	public void runService()
	{
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run DOLS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DOLS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if(error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Checking if error tag is empty", "Error tag is empty", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if error tag is empty", "Error tag is not empty:"+error, false);
		}
	}
	
	public void validateInDatabase() throws SQLException
	{
		String eventId = doc.getElementsByTagName("STEP2_EV_ID_0").item(0).getTextContent();
		System.out.println("EV_ID: "+eventId);
	queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("checkInEvnttableQuery")[1], eventId);
	if(queryResultSet!=null)
	{
		if(queryResultSet.next())
		{
			Reporter.logEvent(Status.PASS, "Check if after the DOLS event row is populated in event table", "The row is populdated for eventid in event table", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Check if after the DOLS event row is populated in event table", "The row is not populdated for eventid in event table", false);
		}
	}
	
	queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("checkInSteptableQuery")[1], eventId);
	if(queryResultSet!=null)
	{
		if(queryResultSet.next())
		{
			Reporter.logEvent(Status.PASS, "Check if after the DOLS event row is populated in Step table", "The row is populdated for eventid in step table", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Check if after the DOLS event row is populated in Step table", "The row is not populdated for eventid in step table", false);
		}
	}
	}
}
