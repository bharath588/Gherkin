package pageobject.MSGT;

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

public class MSGT_Create_Message_Type {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MSGT_Create_message_type";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String MESSAGE_TYPE_PGM_NAME_0;
	String MESSAGE_TYPE_PGM_DESCR_0;
	String MESSAGE_TYPE_MSG_CODE_0;
	String MESSAGE_TYPE_UNCLEARED_MSG_RETENTION_DAYS_0;
	String MESSAGE_TYPE_CLEARED_MSG_RETENTION_DAYS_0;
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
	public void setMESSAGE_TYPE_PGM_NAME_0(String mESSAGE_TYPE_PGM_NAME_0) {
		MESSAGE_TYPE_PGM_NAME_0 = mESSAGE_TYPE_PGM_NAME_0;
	}
	public void setMESSAGE_TYPE_PGM_DESCR_0(String mESSAGE_TYPE_PGM_DESCR_0) {
		MESSAGE_TYPE_PGM_DESCR_0 = mESSAGE_TYPE_PGM_DESCR_0;
	}
	public void setMESSAGE_TYPE_MSG_CODE_0(String mESSAGE_TYPE_MSG_CODE_0) {
		MESSAGE_TYPE_MSG_CODE_0 = mESSAGE_TYPE_MSG_CODE_0;
	}
	public void setMESSAGE_TYPE_UNCLEARED_MSG_RETENTION_DAYS_0(
			String mESSAGE_TYPE_UNCLEARED_MSG_RETENTION_DAYS_0) {
		MESSAGE_TYPE_UNCLEARED_MSG_RETENTION_DAYS_0 = mESSAGE_TYPE_UNCLEARED_MSG_RETENTION_DAYS_0;
	}
	public void setMESSAGE_TYPE_CLEARED_MSG_RETENTION_DAYS_0(
			String mESSAGE_TYPE_CLEARED_MSG_RETENTION_DAYS_0) {
		MESSAGE_TYPE_CLEARED_MSG_RETENTION_DAYS_0 = mESSAGE_TYPE_CLEARED_MSG_RETENTION_DAYS_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setMESSAGE_TYPE_CLEARED_MSG_RETENTION_DAYS_0(Stock.GetParameterValue("MESSAGE_TYPE_CLEARED_MSG_RETENTION_DAYS_0"));
		this.setMESSAGE_TYPE_MSG_CODE_0(Stock.GetParameterValue("MESSAGE_TYPE_MSG_CODE_0"));
		this.setMESSAGE_TYPE_PGM_DESCR_0(Stock.GetParameterValue("MESSAGE_TYPE_PGM_DESCR_0"));
		this.setMESSAGE_TYPE_PGM_NAME_0(Stock.GetParameterValue("MESSAGE_TYPE_PGM_NAME_0"));
		this.setMESSAGE_TYPE_UNCLEARED_MSG_RETENTION_DAYS_0(Stock.GetParameterValue("MESSAGE_TYPE_UNCLEARED_MSG_RETENTION_DAYS_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_DATABASE="+LOGON_DATABASE+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&MESSAGE_TYPE_PGM_NAME_0="+MESSAGE_TYPE_PGM_NAME_0+
				"&MESSAGE_TYPE_PGM_DESCR_0="+MESSAGE_TYPE_PGM_DESCR_0+
				"&MESSAGE_TYPE_MSG_CODE_0="+MESSAGE_TYPE_MSG_CODE_0+
				"&MESSAGE_TYPE_UNCLEARED_MSG_RETENTION_DAYS_0="+MESSAGE_TYPE_UNCLEARED_MSG_RETENTION_DAYS_0+
				"&MESSAGE_TYPE_CLEARED_MSG_RETENTION_DAYS_0="+MESSAGE_TYPE_CLEARED_MSG_RETENTION_DAYS_0+				
				"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for MSGT service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() throws SQLException {
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
				Reporter.logEvent(Status.PASS, "Run MSGT service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run MSGT service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response: ", "MESSAGE_TYPE_MSG_CLEAR_METHOD_0: "+doc.getElementsByTagName("MESSAGE_TYPE_MSG_CLEAR_METHOD_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForMSGT")[1], this.MESSAGE_TYPE_PGM_NAME_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record insertion into DB", "Records inserted into DB successfully\nTable Name: MESSAGE_TYPE", false);
			while(queryResultSet.next()){
				
				Reporter.logEvent(Status.INFO, "Values from DB", "PGM_NAME: "+queryResultSet.getString("PGM_NAME")+
						"\nMSG_CODE: "+queryResultSet.getString("MSG_CODE")+
						"\nMSG_CLEAR_METHOD: "+queryResultSet.getString("MSG_CLEAR_METHOD")+
						"\n", false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record insertion into DB", "Records not inserted into DB \nTable Name: MESSAGE_TYPE", false);
		}
	}
	
	public void dataReset() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForMSGTreset")[1], this.MESSAGE_TYPE_PGM_NAME_0);
	}
	
}