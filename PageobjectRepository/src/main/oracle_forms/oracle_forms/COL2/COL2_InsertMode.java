package pageobject.COL2;

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

public class COL2_InsertMode {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/COL2_INSERTMODE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String AC1_CODE_0;
	String AC1_DESCR_0;
	String SBA1_CODE_0;
	String SBA1_DESCR_0;
	
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
	public void setAC1_CODE_0(String aC1_CODE_0) {
		AC1_CODE_0 = aC1_CODE_0;
	}
	public void setAC1_DESCR_0(String aC1_DESCR_0) {
		AC1_DESCR_0 = aC1_DESCR_0;
	}
	public void setSBA1_CODE_0(String sBA1_CODE_0) {
		SBA1_CODE_0 = sBA1_CODE_0;
	}
	public void setSBA1_DESCR_0(String sBA1_DESCR_0) {
		SBA1_DESCR_0 = sBA1_DESCR_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setAC1_CODE_0(Stock.GetParameterValue("AC1_CODE_0"));
		this.setAC1_DESCR_0(Stock.GetParameterValue("AC1_DESCR_0"));
		this.setSBA1_CODE_0(Stock.GetParameterValue("SBA1_CODE_0"));
		this.setSBA1_DESCR_0(Stock.GetParameterValue("SBA1_DESCR_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&AC1_CODE_0="+AC1_CODE_0+"&AC1_DESCR_0="+AC1_DESCR_0+"&SBA1_CODE_0="+SBA1_CODE_0+"&SBA1_DESCR_0="+SBA1_DESCR_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for COL2 service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run COL2 service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run COL2 service", "Running Failed:", false);
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
		String ac1_descr = this.AC1_DESCR_0;
		String ac1_descr_db = null;
		
		String sba1_descr = this.SBA1_DESCR_0;
		String sba1_descr_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCOL2Acct")[1],  this.AC1_CODE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking if Database records are inserted or not", "Records inserted in Database\nTable Name: ACCOUNT", false);
			while(queryResultSet.next()){
				ac1_descr_db = queryResultSet.getString("DESCR");
				Reporter.logEvent(Status.INFO, "Values from Database", "CODE: "+queryResultSet.getString("CODE")+
						"\nDESCR: "+queryResultSet.getString("DESCR"), false);
			}
			if(ac1_descr.equalsIgnoreCase(ac1_descr_db)){
				Reporter.logEvent(Status.PASS, "Comparing and Validating DESCRIPTION from Input and Database", "Both the values are same as expected"+
						"\nFrom Input: "+ac1_descr+"\nFrom Database: "+ac1_descr_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating DESCRIPTION from Input and Database", "Both the values are not same"+
						"\nFrom Input: "+ac1_descr+"\nFrom Database: "+ac1_descr_db, false);
			}
		}	
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCOL2SubAcct")[1],  this.SBA1_CODE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking if Database records are inserted or not", "Records inserted in Database\nTable Name: SUB_ACCT", false);
			while(queryResultSet.next()){
				sba1_descr_db = queryResultSet.getString("DESCR");
				Reporter.logEvent(Status.INFO, "Values from Database", "CODE: "+queryResultSet.getString("CODE")+
						"\nDESCR: "+queryResultSet.getString("DESCR"), false);
			}
			if(sba1_descr.equalsIgnoreCase(sba1_descr_db)){
				Reporter.logEvent(Status.PASS, "Comparing and Validating DESCRIPTION from Input and Database", "Both the values are same as expected"+
						"\nFrom Input: "+sba1_descr+"\nFrom Database: "+sba1_descr_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating DESCRIPTION from Input and Database", "Both the values are not same"+
						"\nFrom Input: "+sba1_descr+"\nFrom Database: "+sba1_descr_db, false);
			}
		}
	}
	
	public void FlushData(){
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCOL2AcctDelete")[1],  this.AC1_CODE_0);
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCOL2SubAcctDelete")[1],  this.SBA1_CODE_0);
	}
}
