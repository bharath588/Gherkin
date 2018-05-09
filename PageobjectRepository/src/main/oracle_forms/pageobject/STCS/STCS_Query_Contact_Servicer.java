package pageobject.STCS;

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

public class STCS_Query_Contact_Servicer {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STCS_Query_Contact_Servicer";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	
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
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for STCS service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run SSPU service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SSPU service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from response", "CSV1_CITY_0: "+doc.getElementsByTagName("CSV1_CITY_0").item(0).getTextContent()+
					"\nCSV1_COMPANY_NAME_0: "+doc.getElementsByTagName("CSV1_COMPANY_NAME_0").item(0).getTextContent()+
					"\nDESCR: "+doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String city = doc.getElementsByTagName("CSV1_CITY_0").item(0).getTextContent();
		String name = doc.getElementsByTagName("CSV1_COMPANY_NAME_0").item(0).getTextContent();
		String name_db = null;
		String id = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTCS")[1], city, name);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database\nTable Name: CONTACT_SERVICER", false);
			
			while(queryResultSet.next()){
				id = queryResultSet.getString("ID");
				name_db = queryResultSet.getString("COMPANY_NAME");
				
				Reporter.logEvent(Status.INFO, "Values from Database", 
						"\nID: "+queryResultSet.getString("ID")+
						"\nNAME: "+queryResultSet.getString("COMPANY_NAME"), false);
			}
			if(name.equalsIgnoreCase(name_db)){
				Reporter.logEvent(Status.PASS, "Comparing and validating COMPANY NAME from Response and Database", "Both the values are same as expected"+
			"\nFrom Response: "+name+"\nFrom Database: "+name_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and validating COMPANY_NAME from Response and Database", "Both the values are not same "+
						"\nFrom Response: "+name+"\nFrom Database: "+name_db, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTCS2")[1], id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database\nTable Name: CONTACT_SERVICER_HISTORY", false);
			
			while(queryResultSet.next()){
				
				Reporter.logEvent(Status.INFO, "Values from Database", 
						"\nUACL_ID: "+queryResultSet.getString("UACL_ID")+
						"\nNAME: "+queryResultSet.getString("COMPANY_NAME")+
						"\nID: "+queryResultSet.getString("ID"), false);
			}
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
				
	}
}
