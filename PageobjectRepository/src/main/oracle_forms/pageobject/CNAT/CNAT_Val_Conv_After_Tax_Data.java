package pageobject.CNAT;

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

public class CNAT_Val_Conv_After_Tax_Data {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CNAT_Validate_Conversion_After_Tax_data";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0;
	String MO1_DISP_DFT_PRIN_CODE_0;
	
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
	public void setCONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0(
			String cONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0) {
		CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0 = cONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0;
	}
	public void setMO1_DISP_DFT_PRIN_CODE_0(String mO1_DISP_DFT_PRIN_CODE_0) {
		MO1_DISP_DFT_PRIN_CODE_0 = mO1_DISP_DFT_PRIN_CODE_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0(Stock.GetParameterValue("CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0"));
		this.setMO1_DISP_DFT_PRIN_CODE_0(Stock.GetParameterValue("MO1_DISP_DFT_PRIN_CODE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0="+CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0+"&MO1_DISP_DFT_PRIN_CODE_0="+MO1_DISP_DFT_PRIN_CODE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for CNAT service", this.queryString.replaceAll("&", "\n"), false);		
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
			Reporter.logEvent(Status.PASS, "Run CNAT service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CNAT service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From Response: ", 
					"SSN: "+doc.getElementsByTagName("WORK_CONV_PART1_SSN_0").item(0).getTextContent(), false);
		
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String ssn_resp = doc.getElementsByTagName("WORK_CONV_PART1_SSN_0").item(0).getTextContent();
		String ssn_db = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCNAT")[1], ssn_resp);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: WORK_CONV_PART", "Record exists in the Database", false);
			
			while(queryResultSet.next()){
				ssn_db = queryResultSet.getString("SSN");
				Reporter.logEvent(Status.INFO, "Values from Database",
						"\nID: "+queryResultSet.getString("ID")+
						"\nEV_ID: "+queryResultSet.getString("EV_ID")+
						"\nLAST_NAME: "+queryResultSet.getString("LAST_NAME")+
						"\nSSN: "+queryResultSet.getString("SSN"), false);
			}
			if(ssn_resp.equalsIgnoreCase(ssn_db)){
				Reporter.logEvent(Status.PASS, "Comparing and validating SSn from response and Database", "Both the values are same as expected"+
						"\nFrom Response: "+ssn_resp+"\nFrom Database: "+ssn_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and validating SSn from response and Database", "Both the values are not same "+
						"\nFrom Response: "+ssn_resp+"\nFrom Database: "+ssn_db, false);
			}
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
				
	}
}
