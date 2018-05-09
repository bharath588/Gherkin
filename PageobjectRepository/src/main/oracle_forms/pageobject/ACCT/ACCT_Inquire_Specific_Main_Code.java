package pageobject.ACCT;

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

public class ACCT_Inquire_Specific_Main_Code {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ACCT_Inquire_Specific_Main_Code";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String VALID_ACCTG_COMBO_MAIN_CODE_0;
	
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
	public void setVALID_ACCTG_COMBO_MAIN_CODE_0(
			String vALID_ACCTG_COMBO_MAIN_CODE_0) {
		VALID_ACCTG_COMBO_MAIN_CODE_0 = vALID_ACCTG_COMBO_MAIN_CODE_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setVALID_ACCTG_COMBO_MAIN_CODE_0(Stock.GetParameterValue("VALID_ACCTG_COMBO_MAIN_CODE_0"));
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&VALID_ACCTG_COMBO_MAIN_CODE_0="+VALID_ACCTG_COMBO_MAIN_CODE_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for ACCT INQUIRE SPECIFIC service", this.queryString.replaceAll("&", "\n"), false);
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
		Reporter.logEvent(Status.PASS, "Run  ACCT INQUIRE SPECIFIC CODES service", "Execution Done!", false);
		}
		catch (Exception e) {
		e.printStackTrace();
		Globals.exception = e;
		Reporter.logEvent(Status.FAIL, "Run  ACCT INQUIRE SPECIFIC CODES service", "Running Failed:", false);
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
			
	}
	
	public void validateInDatabase()throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForACCTSpecific")[1], this.VALID_ACCTG_COMBO_MAIN_CODE_0);
		int a = DB.getRecordSetCount(queryResultSet);
		System.out.println(a);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable Name: VALID_ACCTG_COMBO", false);
			Reporter.logEvent(Status.PASS,"Expected Results: Displays/retrieves all valid accounting combos for specific Main Code-"+this.VALID_ACCTG_COMBO_MAIN_CODE_0,"Total Number of Records existing in Database for Main Code- "+this.VALID_ACCTG_COMBO_MAIN_CODE_0+
					": "+DB.getRecordSetCount(queryResultSet),false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
}
