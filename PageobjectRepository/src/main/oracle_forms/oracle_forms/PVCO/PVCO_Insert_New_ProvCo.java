package pageobject.PVCO;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class PVCO_Insert_New_ProvCo {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PVCO_New_Record_for_New_Providing_Company";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String PC1_CODE_0;
	String PC1_NAME_0;
	String PC1_NAME_0_X1;
	String PC1_SHORT_NAME_0;
	String PC1_MAILING_NAME_1_0;
	String PC1_FIRST_LINE_MAILING_0;
	String PC1_CITY_0;
	String PC1_STATE_CODE_0;
	String PC1_ZIP_CODE_0;
	String PC1_COUNTRY_0;
	String PC1_EFFDATE_0;
	String PC1_DISB_BATCH_GROUP_0;
	String PC1_ACH_PC_TRANSLATION_CODE_0;
	String PC1_ACH_CR_APPLN_CODE_0;
	String PC1_ACH_DR_APPLN_CODE_0;
	String PC1_EXT_TRUSTEE_DAILY_WIRE_IND_0;
	
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
	public void setPC1_CODE_0(String pC1_CODE_0) {
		PC1_CODE_0 = pC1_CODE_0;
	}
	public void setPC1_NAME_0(String pC1_NAME_0) {
		PC1_NAME_0 = pC1_NAME_0;
	}
	public void setPC1_NAME_0_X1(String pC1_NAME_0_X1) {
		PC1_NAME_0_X1 = pC1_NAME_0_X1;
	}
	public void setPC1_SHORT_NAME_0(String pC1_SHORT_NAME_0) {
		PC1_SHORT_NAME_0 = pC1_SHORT_NAME_0;
	}
	public void setPC1_MAILING_NAME_1_0(String pC1_MAILING_NAME_1_0) {
		PC1_MAILING_NAME_1_0 = pC1_MAILING_NAME_1_0;
	}
	public void setPC1_FIRST_LINE_MAILING_0(String pC1_FIRST_LINE_MAILING_0) {
		PC1_FIRST_LINE_MAILING_0 = pC1_FIRST_LINE_MAILING_0;
	}
	public void setPC1_CITY_0(String pC1_CITY_0) {
		PC1_CITY_0 = pC1_CITY_0;
	}
	public void setPC1_STATE_CODE_0(String pC1_STATE_CODE_0) {
		PC1_STATE_CODE_0 = pC1_STATE_CODE_0;
	}
	public void setPC1_ZIP_CODE_0(String pC1_ZIP_CODE_0) {
		PC1_ZIP_CODE_0 = pC1_ZIP_CODE_0;
	}
	public void setPC1_COUNTRY_0(String pC1_COUNTRY_0) {
		PC1_COUNTRY_0 = pC1_COUNTRY_0;
	}
	public void setPC1_EFFDATE_0(String pC1_EFFDATE_0) {
		PC1_EFFDATE_0 = pC1_EFFDATE_0;
	}
	public void setPC1_DISB_BATCH_GROUP_0(String pC1_DISB_BATCH_GROUP_0) {
		PC1_DISB_BATCH_GROUP_0 = pC1_DISB_BATCH_GROUP_0;
	}
	public void setPC1_ACH_PC_TRANSLATION_CODE_0(
			String pC1_ACH_PC_TRANSLATION_CODE_0) {
		PC1_ACH_PC_TRANSLATION_CODE_0 = pC1_ACH_PC_TRANSLATION_CODE_0;
	}
	public void setPC1_ACH_CR_APPLN_CODE_0(String pC1_ACH_CR_APPLN_CODE_0) {
		PC1_ACH_CR_APPLN_CODE_0 = pC1_ACH_CR_APPLN_CODE_0;
	}
	public void setPC1_ACH_DR_APPLN_CODE_0(String pC1_ACH_DR_APPLN_CODE_0) {
		PC1_ACH_DR_APPLN_CODE_0 = pC1_ACH_DR_APPLN_CODE_0;
	}
	public void setPC1_EXT_TRUSTEE_DAILY_WIRE_IND_0(
			String pC1_EXT_TRUSTEE_DAILY_WIRE_IND_0) {
		PC1_EXT_TRUSTEE_DAILY_WIRE_IND_0 = pC1_EXT_TRUSTEE_DAILY_WIRE_IND_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setPC1_ACH_CR_APPLN_CODE_0(Stock.GetParameterValue("PC1_ACH_CR_APPLN_CODE_0"));
		this.setPC1_ACH_DR_APPLN_CODE_0(Stock.GetParameterValue("PC1_ACH_DR_APPLN_CODE_0"));
		this.setPC1_ACH_PC_TRANSLATION_CODE_0(Stock.GetParameterValue("PC1_ACH_PC_TRANSLATION_CODE_0"));
		this.setPC1_CITY_0(Stock.GetParameterValue("PC1_CITY_0"));
		this.setPC1_CODE_0(Stock.GetParameterValue("PC1_CODE_0"));
		this.setPC1_COUNTRY_0(Stock.GetParameterValue("PC1_COUNTRY_0"));
		this.setPC1_DISB_BATCH_GROUP_0(Stock.GetParameterValue("PC1_DISB_BATCH_GROUP_0"));
		this.setPC1_EFFDATE_0(Stock.GetParameterValue("PC1_EFFDATE_0"));
		this.setPC1_EXT_TRUSTEE_DAILY_WIRE_IND_0(Stock.GetParameterValue("PC1_EXT_TRUSTEE_DAILY_WIRE_IND_0"));
		this.setPC1_FIRST_LINE_MAILING_0(Stock.GetParameterValue("PC1_FIRST_LINE_MAILING_0"));
		this.setPC1_MAILING_NAME_1_0(Stock.GetParameterValue("PC1_MAILING_NAME_1_0"));
		this.setPC1_NAME_0(Stock.GetParameterValue("PC1_NAME_0"));
		this.setPC1_NAME_0_X1(Stock.GetParameterValue("PC1_NAME_0_X1"));
		this.setPC1_SHORT_NAME_0(Stock.GetParameterValue("PC1_SHORT_NAME_0"));
		this.setPC1_STATE_CODE_0(Stock.GetParameterValue("PC1_STATE_CODE_0"));
		this.setPC1_ZIP_CODE_0(Stock.GetParameterValue("PC1_ZIP_CODE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&PC1_CODE_0="+PC1_CODE_0+"&PC1_NAME_0="+PC1_NAME_0+"&PC1_NAME_0_X1="+PC1_NAME_0_X1+"&PC1_SHORT_NAME_0="+PC1_SHORT_NAME_0+"&PC1_MAILING_NAME_1_0="+PC1_MAILING_NAME_1_0+
				"&PC1_FIRST_LINE_MAILING_0="+PC1_FIRST_LINE_MAILING_0+"&PC1_CITY_0="+PC1_CITY_0+"&PC1_STATE_CODE_0="+PC1_STATE_CODE_0+"&PC1_ZIP_CODE_0="+PC1_ZIP_CODE_0+
				"&PC1_COUNTRY_0="+PC1_COUNTRY_0+"&PC1_EFFDATE_0="+PC1_EFFDATE_0+"&PC1_DISB_BATCH_GROUP_0="+PC1_DISB_BATCH_GROUP_0+"&PC1_ACH_PC_TRANSLATION_CODE_0="+PC1_ACH_PC_TRANSLATION_CODE_0+
				"&PC1_ACH_CR_APPLN_CODE_0="+PC1_ACH_CR_APPLN_CODE_0+"&PC1_ACH_DR_APPLN_CODE_0="+PC1_ACH_DR_APPLN_CODE_0+"&PC1_EXT_TRUSTEE_DAILY_WIRE_IND_0="+PC1_EXT_TRUSTEE_DAILY_WIRE_IND_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for PVCO service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run PVCO service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PVCO service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		//	Reporter.logEvent(Status.INFO, "Values From Response: ", "INTEREST RATE START DATE: "+ doc.getElementsByTagName("INTEREST_RATE_START_DATE_2").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String code_db = null;
		String code_input = this.PC1_CODE_0;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPVCOInsert")[1], code_input);
		if(DB.getRecordSetCount(queryResultSet)>0){
			while(queryResultSet.next()){
				Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
				code_db = queryResultSet.getString("CODE");
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: PROV_COMPANY", "CODE: "+queryResultSet.getString("CODE")+
					"\nNAME: "+queryResultSet.getString("NAME")+
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nSHORT_NAME: "+queryResultSet.getString("SHORT_NAME")+
					"\nZIP_CODE: "+queryResultSet.getString("ZIP_CODE")+
					"\nFIRST_LINE_MAILING: "+queryResultSet.getString("FIRST_LINE_MAILING")+
					"\nSTATE_CODE: "+queryResultSet.getString("STATE_CODE"), false);
				
				if(code_input.equalsIgnoreCase(code_db)){
					Reporter.logEvent(Status.PASS, "Comapring and Validating CODE from Input and Database", "Both the values are same as expected"+
				"\nFrom Input: "+code_input+"\nFrom Database: "+code_db, false);
				}else{
					Reporter.logEvent(Status.FAIL, "Comapring and Validating CODE from Input and Database", "Values are not same as expected"+
							"\nFrom Input: "+code_input+"\nFrom Database: "+code_db, false);
				}
			}	
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
		
		
	}
	
	public void FlushDataCreated(){

		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPVCOClearData")[1], this.PC1_CODE_0);
	
/*		if(DB.getRecordSetCount(queryResultSet)==0){
			System.out.println("Data Cleared from Database");
		}
*/	}
	
}
