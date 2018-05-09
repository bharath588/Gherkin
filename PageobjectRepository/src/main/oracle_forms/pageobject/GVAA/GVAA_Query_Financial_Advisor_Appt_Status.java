package pageobject.GVAA;

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

public class GVAA_Query_Financial_Advisor_Appt_Status {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GVAA_Query_Financial_Advisor_Appt_Status";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0_X1;
	String EXT_GVA_FINANCIAL_ADVISOR_EGBD_ID_0;
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
	public void setCONTROL_SELECTION_0_X1(String cONTROL_SELECTION_0_X1) {
		CONTROL_SELECTION_0_X1 = cONTROL_SELECTION_0_X1;
	}
	public void setEXT_GVA_FINANCIAL_ADVISOR_EGBD_ID_0(
			String eXT_GVA_FINANCIAL_ADVISOR_EGBD_ID_0) {
		EXT_GVA_FINANCIAL_ADVISOR_EGBD_ID_0 = eXT_GVA_FINANCIAL_ADVISOR_EGBD_ID_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SELECTION_0_X1(Stock.GetParameterValue("CONTROL_SELECTION_0_X1"));
		this.setEXT_GVA_FINANCIAL_ADVISOR_EGBD_ID_0(Stock.GetParameterValue("EXT_GVA_FINANCIAL_ADVISOR_EGBD_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_DATABASE="+LOGON_DATABASE+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_SELECTION_0_X1="+CONTROL_SELECTION_0_X1+
				"&EXT_GVA_FINANCIAL_ADVISOR_EGBD_ID_0="+EXT_GVA_FINANCIAL_ADVISOR_EGBD_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for GVAA service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run GVAA service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run GVAA service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response\nExpected: FORM returns three rows of appointments for 01-FEB-2018 for State Codes CO, KS, MO", "EXT_GVA_APPOINTMENT_STATE_CODE_0: "+doc.getElementsByTagName("EXT_GVA_APPOINTMENT_STATE_CODE_0").item(0).getTextContent()+
					"\nEXT_GVA_APPOINTMENT_STATE_CODE_0: "+doc.getElementsByTagName("EXT_GVA_APPOINTMENT_STATE_CODE_0").item(1).getTextContent()+
					"\nEXT_GVA_APPOINTMENT_STATE_CODE_0: "+doc.getElementsByTagName("EXT_GVA_APPOINTMENT_STATE_CODE_0").item(2).getTextContent()+
					"\nEXT_GVA_FINANCIAL_ADVISOR_COMPANY_NAME_0: "+doc.getElementsByTagName("EXT_GVA_FINANCIAL_ADVISOR_COMPANY_NAME_0").item(0).getTextContent()+
					"\nEXT_GVA_FINANCIAL_ADVISOR_ID_0: "+doc.getElementsByTagName("EXT_GVA_FINANCIAL_ADVISOR_ID_0").item(0).getTextContent()+
					"\nEXT_GVA_FINANCIAL_ADVISOR_LAST_NAME_0: "+doc.getElementsByTagName("EXT_GVA_FINANCIAL_ADVISOR_LAST_NAME_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGVAA")[1], this.EXT_GVA_FINANCIAL_ADVISOR_EGBD_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: EXT_GVA_APPOINTMENT\nEXT_GVA_BROKER_DEALER\nEXT_GVA_FINANCIAL_ADVISOR", "3 Records exists in the Database"+
					"\nExpected: 3 records exists in DB\nTotal no. of records: "+DB.getRecordSetCount(queryResultSet), false);
			while(queryResultSet.next()){
				
				Reporter.logEvent(Status.INFO, "Values from DB", "EGBD_ID: "+queryResultSet.getString("EGBD_ID")+
						"\nCOMPANY_NAME: "+queryResultSet.getString("COMPANY_NAME")+
						"\nEGFA_ID: "+queryResultSet.getString("EGFA_ID")+
						"\nLAST_NAME: "+queryResultSet.getString("LAST_NAME")+
						"\nSTATE_CODE: "+queryResultSet.getString("STATE_CODE")+
						"\nEFFDATE: "+queryResultSet.getString("STATUS_EFFDATE"), false);	
			}			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
	
}