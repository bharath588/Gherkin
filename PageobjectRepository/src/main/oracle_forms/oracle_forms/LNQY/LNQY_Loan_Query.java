package pageobject.LNQY;

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

public class LNQY_Loan_Query {

	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/LNQY_LoanQuery";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String LOAN_QUERY_TRF_LOAN_ACCT_ID_0;
	
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
	public void setLOAN_QUERY_TRF_LOAN_ACCT_ID_0(
			String lOAN_QUERY_TRF_LOAN_ACCT_ID_0) {
		LOAN_QUERY_TRF_LOAN_ACCT_ID_0 = lOAN_QUERY_TRF_LOAN_ACCT_ID_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setLOAN_QUERY_TRF_LOAN_ACCT_ID_0(Stock.GetParameterValue("LOAN_QUERY_TRF_LOAN_ACCT_ID_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&LOAN_QUERY_TRF_LOAN_ACCT_ID_0="+LOAN_QUERY_TRF_LOAN_ACCT_ID_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Preparinf Data to run LNQY Form", "CONTROL_SELECTION_0: "+CONTROL_SELECTION_0+"\nLOGON_DATABASE: "+LOGON_DATABASE+
					"\nLOGON_PASSWORD: "+LOGON_PASSWORD+"\nLOGON_USERNAME: "+LOGON_USERNAME+"\nLOAN_QUERY_TRF_LOAN_ACCT_ID_0: "+LOAN_QUERY_TRF_LOAN_ACCT_ID_0, false);
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
				Reporter.logEvent(Status.PASS, "Run LNQY_Loan_Query service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run LNQY_Loan_Query service", "Running Failed:", false);
			}
			
		}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		System.out.println("Plan Name: "+doc.getElementsByTagName("CFG_CONTROL_PLAN_NAME_0").item(0).getTextContent());
		Reporter.logEvent(Status.INFO, "Values From response: ", "ADDRESS_EFFDATE_0: " +doc.getElementsByTagName("ADDRESS_EFFDATE_0").item(0).getTextContent()+
				"\nCFG_CONTROL_PLAN_NAME_0: "+doc.getElementsByTagName("CFG_CONTROL_PLAN_NAME_0").item(0).getTextContent()+"\nCFG_CONTROL_TODAY_0: "+doc.getElementsByTagName("CFG_CONTROL_TODAY_0").item(0).getTextContent()+
				"\nGA_ID_0: "+doc.getElementsByTagName("LOAN_QUERY_GA_ID_0").item(0).getTextContent()+"\nINDIVIDUAL_SSN_0: "+doc.getElementsByTagName("INDIVIDUAL_SSN_0").item(0).getTextContent(), false);
		
	}
	
	public void validateInDatabase() throws SQLException{
		String STAT_CODE = doc.getElementsByTagName("LOAN_QUERY_STATUS_CODE_0").item(0).getTextContent();
		String stat_code=null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForLNQY")[1], this.LOAN_QUERY_TRF_LOAN_ACCT_ID_0);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable Name: EVENT", false);
			Reporter.logEvent(Status.INFO, "Values From database\nTable Name: LOAN_ACCT", "GA_ID: "+ queryResultSet.getString("GA_ID")+
					"\nSEQNBR: "+queryResultSet.getString("INLNAG_SEQNBR")+
					"\nLOAN_AMT: "+queryResultSet.getString("LOAN_AMT")+
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nSTATUS_CODE: "+queryResultSet.getString("STATUS_CODE"), false);
			stat_code = queryResultSet.getString("STATUS_CODE");
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(STAT_CODE.equalsIgnoreCase(stat_code)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating STATUS CODE from Response and Database", "Both the values are same as Expected"+
					"\nSTATUS CODE: "+"From Response: "+STAT_CODE+"\nFrom Database: "+stat_code, false);
		}
  
	}
}
