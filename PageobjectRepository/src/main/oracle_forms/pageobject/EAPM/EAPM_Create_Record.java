package pageobject.EAPM;

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

public class EAPM_Create_Record {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/EAPM_record";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String EAPACTY1_RSN_CODE_LOV2;
	String EAPACTY1_AMOUNT_11;
	
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
	public void setEAPACTY1_RSN_CODE_LOV2(String eAPACTY1_RSN_CODE_LOV2) {
		EAPACTY1_RSN_CODE_LOV2 = eAPACTY1_RSN_CODE_LOV2;
	}
	public void setEAPACTY1_AMOUNT_11(String eAPACTY1_AMOUNT_1) {
		EAPACTY1_AMOUNT_11 = eAPACTY1_AMOUNT_1;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setEAPACTY1_AMOUNT_11(Stock.GetParameterValue("EAPACTY1_AMOUNT_11"));
		this.setEAPACTY1_RSN_CODE_LOV2(Stock.GetParameterValue("EAPACTY1_RSN_CODE_LOV2"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&EAPACTY1_RSN_CODE_LOV2="+EAPACTY1_RSN_CODE_LOV2+"&EAPACTY1_AMOUNT_11="+EAPACTY1_AMOUNT_11+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for EAPM service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run EAPM service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run EAPM service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
//			Reporter.logEvent(Status.PASS, "Capturing Response after Execution", "Response:\n"+doc.getDocumentElement(), false);
			Reporter.logEvent(Status.INFO, "From Response: ", "EAPACTY1_RSN_CODE_11: "+ doc.getElementsByTagName("EAPACTY1_RSN_CODE_11").item(0).getTextContent()+
					"\nCONTROL_PROD_ID_0: "+ doc.getElementsByTagName("CONTROL_PROD_ID_0").item(0).getTextContent()+
					"\nCONTROL_PLAN_NAME_0: "+ doc.getElementsByTagName("CONTROL_PLAN_NAME_0").item(0).getTextContent()+
					"\nADDRESS_VALIDATION_SOURCE_CODE_0: "+ doc.getElementsByTagName("ADDRESS_VALIDATION_SOURCE_CODE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String rsn_code= doc.getElementsByTagName("EAPACTY1_RSN_CODE_11").item(0).getTextContent();
		String rsn_code_db=null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForEAPM")[1],  this.CONTROL_SSN_DISPL_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating records exists in DB", "Records are inserted into DB:\n Table: EAP_ACTY ", false);
			while(queryResultSet.next()){
				rsn_code_db = queryResultSet.getString("RSN_CODE");
				Reporter.logEvent(Status.INFO, "From DATABASE", "EV_ID: "+queryResultSet.getString("EV_ID")+
					"\nGA_ID: "+queryResultSet.getString("GA_ID")+					
					"\nIND_ID: "+queryResultSet.getString("IND_ID")+
					"\nREASON_CODE: "+queryResultSet.getString("RSN_CODE")+
					"\nSTATUS CHANGE EFFDATE: "+queryResultSet.getString("STATUS_CHG_EFFDATE"), false);
			}
			if(rsn_code.equalsIgnoreCase(rsn_code_db)){
				Reporter.logEvent(Status.PASS, "Comparing and Validating REASON_CODE from Response and Database", "Both the values are same as Expected"+
						"\nREASON_CODE: "+"From Response: "+rsn_code+"\nFrom Database: "+rsn_code_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating REASON_CODE from Response and Database", "Both the values are not same as Expected"+
						"\nREASON_CODE: "+"From Response: "+rsn_code+"\nFrom Database: "+rsn_code_db, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
}