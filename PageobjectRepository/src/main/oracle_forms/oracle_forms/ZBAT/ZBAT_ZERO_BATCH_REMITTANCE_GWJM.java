package pageobject.ZBAT;

import generallib.General;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class ZBAT_ZERO_BATCH_REMITTANCE_GWJM {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ZBAT_ZERO_BATCH_REMITTANCE_GWJM";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_ZBAT;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String BHRM1_BKA_CODE_0;
	String BHRM1_BHSX_ID_0;
	String RMNC1_GA_ID_0;
	String RMNC1_AMOUNT_0;

	String rmnc_id = null;
	String rmnc1_id = null;
	String effDate=null;
	
	public String getRmnc1_id() {
		return rmnc1_id;
	}
	public String getRmnc_id() {
		return rmnc_id;
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
	public void setRMNC1_GA_ID_0(String rMNC1_GA_ID_0) {
		RMNC1_GA_ID_0 = rMNC1_GA_ID_0;
	}
		
	public void setCONTROL_SELECTION_ZBAT(String cONTROL_SELECTION_ZBAT) {
		CONTROL_SELECTION_ZBAT = cONTROL_SELECTION_ZBAT;
	}

	public void setBHRM1_BKA_CODE_0(String bHRM1_BKA_CODE_0) {
		BHRM1_BKA_CODE_0 = bHRM1_BKA_CODE_0;
	}

	public void setBHRM1_BHSX_ID_0(String bHRM1_BHSX_ID_0) {
		BHRM1_BHSX_ID_0 = bHRM1_BHSX_ID_0;
	}

	public void setRMNC1_AMOUNT_0(String rMNC1_AMOUNT_0) {
		RMNC1_AMOUNT_0 = rMNC1_AMOUNT_0;
	}

	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_ZBAT(Stock.GetParameterValue("CONTROL_SELECTION_ZBAT"));
		this.setBHRM1_BKA_CODE_0(Stock.GetParameterValue("BHRM1_BKA_CODE_0"));
		this.setBHRM1_BHSX_ID_0(Stock.GetParameterValue("BHRM1_BHSX_ID_0"));
		this.setRMNC1_AMOUNT_0(Stock.GetParameterValue("RMNC1_AMOUNT_0"));

		this.setRMNC1_GA_ID_0(Stock.GetParameterValue("RMNC1_GA_ID_0"));
		
		queryString = "?LOGON_USERNAME="
		+LOGON_USERNAME
		+"&LOGON_PASSWORD="
		+LOGON_PASSWORD
		+"&LOGON_DATABASE="
		+LOGON_DATABASE
		+"&CONTROL_SELECTION_ZBAT="
		+CONTROL_SELECTION_ZBAT
		+"&BHRM1_BKA_CODE_0="
		+BHRM1_BKA_CODE_0
		+"&BHRM1_BHSX_ID_0="
		+BHRM1_BHSX_ID_0
		+"&RMNC1_AMOUNT_0="
		+RMNC1_AMOUNT_0
		+"&RMNC1_GA_ID_0="
		+RMNC1_GA_ID_0
		+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for ZBAT service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run ZBAT service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ZBAT service", "Running Failed:", false);
		}
	}
	
	
	public String validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();		
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response: ", "RMNC1_ID: "+doc.getElementsByTagName("RMNC1_ID_0").item(0).getTextContent(), false);
			//this.rmnc_id = doc.getElementsByTagName("RMNC_ID_0").item(0).getTextContent();
			this.rmnc1_id = doc.getElementsByTagName("RMNC1_ID_0").item(0).getTextContent();
			Reporter.logEvent(Status.INFO, "Remit ID which we are passing to CSIP: ", rmnc1_id , false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		
		}
		return this.rmnc1_id;
	}
	
	public String validateOutput_return_rmnc1_id() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();		
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response: ", "RMNC1_ID: "+doc.getElementsByTagName("RMNC1_ID_0").item(0).getTextContent(), false);
			//this.rmnc_id = doc.getElementsByTagName("RMNC_ID_0").item(0).getTextContent();
			this.rmnc1_id = doc.getElementsByTagName("RMNC1_ID_0").item(0).getTextContent();
			System.out.println("rmnc_id: "+rmnc_id);
			Reporter.logEvent(Status.INFO, "Remit ID which we are passing to CSIP: ", rmnc_id , false);
			System.out.println(Error);
		
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		
		}
		return this.rmnc1_id;
	}
	
	public String validateInDatabase()throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForZBAT")[1], rmnc1_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database\nTable Name: REMIT_NOTICE", false);
			while(queryResultSet.next()){

				effDate=queryResultSet.getString("CASH_EFFDATE");
				System.out.println("effDate: "+effDate);
				Reporter.logEvent(Status.INFO, "From Database ", "RMNC_ID: "+queryResultSet.getString("ID")+
						"\nEV_ID: "+queryResultSet.getString("EV_ID")+
						"\nCASH_EFFDATE: "+queryResultSet.getString("CASH_EFFDATE")+
						"\nAMOUNT: "+queryResultSet.getString("AMOUNT")+
						"\nBALANCE_STATUS_CODE: "+queryResultSet.getString("BALANCE_STATUS_CODE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "Record doesn't exists in the Database", false);
		}
		return effDate;
	}
}
