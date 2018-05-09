package pageobject.PACS;

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

public class PACS_Query_Ppt_Info_Menu_Items {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PACS_Query_Inquire_Participant_Information_Menu_Items";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String IND_HEADER1_SSN_0;
	String IND_HEADER1_SSN_0_X1;
	
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
	public void setIND_HEADER1_SSN_0(String iND_HEADER1_SSN_0) {
		IND_HEADER1_SSN_0 = iND_HEADER1_SSN_0;
	}
	public void setIND_HEADER1_SSN_0_X1(String iND_HEADER1_SSN_0_X1) {
		IND_HEADER1_SSN_0_X1 = iND_HEADER1_SSN_0_X1;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setIND_HEADER1_SSN_0(Stock.GetParameterValue("IND_HEADER1_SSN_0"));
		this.setIND_HEADER1_SSN_0_X1(Stock.GetParameterValue("IND_HEADER1_SSN_0_X1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&IND_HEADER1_SSN_0="+IND_HEADER1_SSN_0+"&IND_HEADER1_SSN_0_X1="+IND_HEADER1_SSN_0_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for PACS service", this.queryString.replaceAll("&", "\n"), false);
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
			//System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run PACS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PACS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", 
					"\nGA_ID: "+doc.getElementsByTagName("IND_HEADER1_GA_ID_0").item(0).getTextContent()+
					"\nSSN: "+doc.getElementsByTagName("IND_HEADER_SSN_0").item(0).getTextContent()+
					"\nEFFDATE: "+doc.getElementsByTagName("PABD_HDR1_EFFDATE_0").item(0).getTextContent()+
					"\nSS_BCC_EFFDATE_SS_BCC_0: "+doc.getElementsByTagName("SS_BCC_EFFDATE_SS_BCC_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String gc_id = doc.getElementsByTagName("IND_HEADER1_GA_ID_0").item(0).getTextContent().split("-")[0];
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPACSInd")[1], this.IND_HEADER1_SSN_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating whether records exists in Database", "Records exists in DB\nTableName: INDIVIDUAL", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nFIRST NAME: "+queryResultSet.getString("FIRST_NAME")+
						"\nSSN: "+queryResultSet.getString("SSN"), false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Validating whether records exists in Database", "Records doesn't exists in DB\nTableName: INDIVIDUAL", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPACSPlan")[1], gc_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating whether records exists in Database", "Records exists in DB\nTableName: PLAN", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "PLAN_ID: "+queryResultSet.getString("ID")+
						"\nNAME: "+queryResultSet.getString("NAME")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE"), false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Validating whether records exists in Database", "Records doesn't exists in DB\nTableName: INDIVIDUAL", false);
		}
	}
	
}
