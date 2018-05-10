package pageobject.IREF;

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

public class IREF_Refund_Ppt_Remit {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/IREF";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String LOGON_DATABASE_X1; 
	String LOGON_PASSWORD_X1; 
	String LOGON_USERNAME_X1;
	String CONTROL_GA_ID_0;
	String CONTROL_ID_0;
	
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
	public void setLOGON_DATABASE_X1(String lOGON_DATABASE_X1) {
		LOGON_DATABASE_X1 = lOGON_DATABASE_X1;
	}
	public void setLOGON_PASSWORD_X1(String lOGON_PASSWORD_X1) {
		LOGON_PASSWORD_X1 = lOGON_PASSWORD_X1;
	}
	public void setLOGON_USERNAME_X1(String lOGON_USERNAME_X1) {
		LOGON_USERNAME_X1 = lOGON_USERNAME_X1;
	}
	public void setCONTROL_GA_ID_0(String cONTROL_GA_ID_0) {
		CONTROL_GA_ID_0 = cONTROL_GA_ID_0;
	}
	public void setCONTROL_ID_0(String cONTROL_ID_0) {
		CONTROL_ID_0 = cONTROL_ID_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setLOGON_PASSWORD_X1(Stock.GetParameterValue("LOGON_PASSWORD_X1"));
		 this.setLOGON_USERNAME_X1(Stock.GetParameterValue("LOGON_USERNAME_X1"));
		 this.setCONTROL_GA_ID_0(Stock.GetParameterValue("CONTROL_GA_ID_0"));
		 this.setCONTROL_ID_0(Stock.GetParameterValue("CONTROL_ID_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_DATABASE_X1(Stock.GetParameterValue("LOGON_DATABASE_X1"));
	
		 queryString = "?CONTROL_GA_ID_0="+CONTROL_GA_ID_0+"&CONTROL_ID_0="+CONTROL_ID_0+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_DATABASE_X1="+LOGON_DATABASE_X1+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				 "&LOGON_PASSWORD_X1="+LOGON_PASSWORD_X1+"&LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_USERNAME_X1="+LOGON_USERNAME_X1+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for IREF service", this.queryString.replaceAll("&", "\n"), false);
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
				System.out.println(serviceURL);
				doc.getDocumentElement().normalize();
				Reporter.logEvent(Status.PASS, "Run  IREF Refund Participant Remittances service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run Refund Participant Remittances service", "Running Failed:", false);
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
	
	/*public void validateInDatabase() throws SQLException{
		String AMOUNT = doc.getElementsByTagName("APAC1_AMOUNT_0").item(0).getTextContent();
		String amount = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToRefundRemit")[1], CONTROL_ID_0);
		if(queryResultSet.next()){
			amount = queryResultSet.getString("AMOUNT");
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "RMNC ID: "+ queryResultSet.getString("RMNC_ID")+
					"\nSTATUS_CODE: "+queryResultSet.getString("PROCESS_STATUS_CODE")+
					"\nAMOUNT: "+queryResultSet.getString("AMOUNT"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "From DATABASE", "Record does not Exist", false);
		}
		if(AMOUNT.equalsIgnoreCase(amount)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Amount from Response and Database", "Both the values are same as Expected"+
					"\nAmount: "+"From Response: "+AMOUNT+"\nFrom Database: "+amount, false);
		}
		
	}*/

}
