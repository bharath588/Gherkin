package pageobject.MPBP;

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

public class MPBP_Address_banking_info_view {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MPBP_Address_banking_info_view";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SELECTION_CSV_ID_0;
	String SELECTION_SAP_ADDRESS_ID_0;
	String param9420;
	String SELECTION_SAP_BANKING_DETAIL_ID_0;
	String param9421;
	
	public void setSELECTION_CSV_ID_0(String sELECTION_CSV_ID_0) {
		SELECTION_CSV_ID_0 = sELECTION_CSV_ID_0;
	}
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
	public void setSELECTION_SAP_ADDRESS_ID_0(String sELECTION_SAP_ADDRESS_ID_0) {
		SELECTION_SAP_ADDRESS_ID_0 = sELECTION_SAP_ADDRESS_ID_0;
	}
	public void setParam9420(String param9420) {
		this.param9420 = param9420;
	}
	public void setSELECTION_SAP_BANKING_DETAIL_ID_0(
			String sELECTION_SAP_BANKING_DETAIL_ID_0) {
		SELECTION_SAP_BANKING_DETAIL_ID_0 = sELECTION_SAP_BANKING_DETAIL_ID_0;
	}
	public void setParam9421(String param9421) {
		this.param9421 = param9421;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSELECTION_CSV_ID_0(Stock.GetParameterValue("SELECTION_CSV_ID_0"));
		this.setSELECTION_SAP_ADDRESS_ID_0(Stock.GetParameterValue("SELECTION_SAP_ADDRESS_ID_0"));
		this.setSELECTION_SAP_BANKING_DETAIL_ID_0(Stock.GetParameterValue("SELECTION_SAP_BANKING_DETAIL_ID_0"));
		this.setParam9420(Stock.GetParameterValue("param9420"));
		this.setParam9421(Stock.GetParameterValue("param9421"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SELECTION_CSV_ID_0="+SELECTION_CSV_ID_0+"&SELECTION_SAP_ADDRESS_ID_0="+SELECTION_SAP_ADDRESS_ID_0+
				"&param9420="+param9420+"&SELECTION_SAP_BANKING_DETAIL_ID_0="+SELECTION_SAP_BANKING_DETAIL_ID_0+
				"&param9421="+param9421+"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for MPBP View service", this.queryString.replaceAll("&", "\n"), false);
		
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println("serviceURL: " + serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			// System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run MPBP service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run MPBP service",
					"Running Failed:", false);
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
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForMPBPView")[1], this.SELECTION_CSV_ID_0, this.SELECTION_SAP_ADDRESS_ID_0, this.SELECTION_SAP_BANKING_DETAIL_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking whether records exists in Database", "Records are updated in DB:\nTableName: supp_bp_pay_info", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "CSV_ID: "+queryResultSet.getString("CSV_ID")+
						"\nSAP_BP_ID: "+queryResultSet.getString("SAP_BP_ID")+
						"\nSAP_BP_ID: "+queryResultSet.getString("SAP_BP_ID")+
						"\nMAILING_NAME_LINE_1: "+queryResultSet.getString("MAILING_NAME_LINE_1")+
						"\nCITY: "+queryResultSet.getString("CITY")+
						"\nSAP_ADDRESS_ID: "+queryResultSet.getString("SAP_ADDRESS_ID")+
						"\nZIPCODE: "+queryResultSet.getString("ZIP_CODE")+
						"\nREGION: "+queryResultSet.getString("REGION")+
						"\nCOUNTRY_CODE: "+queryResultSet.getString("COUNTRY_CODE"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking whether records exists in Database", "Records are not updated in DB:\nTableName: supp_bp_pay_info", false);
		}
		
	}
}
