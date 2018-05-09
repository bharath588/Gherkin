package pageobject.WKUN;

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

public class WKUN_Work_Unit_Info {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/WKUN_Work_Unit_information";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String WKUN1_SHORT_NAME_0;
	String WKUN1_DESCR_0;
	String WKUN1_DISP_LOGON_ID_0;
	String WKUN1_DISP_LOGON_ID_0_X1;
	String WKUN1_DISP_LOGON_ID_0_X2;
	String WKUN1_DISP_LOGON_ID_0_X3;
	String WKUN1_WKUN_SHORT_NAME_0;
	String WKUN1_WKUN_SHORT_NAME_0_X1;
	String WKUN1_WK_UNIT_NBR_0;
	String WKUN1_INTRNL_MAIL_ADDR_0;
	String WKUN1_FIRST_LINE_MAILING_0;
	String WKUN1_SCND_LINE_MAILING_0;
	String WKUN1_CITY_0;
	String WKUN1_STATE_CODE_0;
	String WKUN1_ZIP_CODE_0;
	String WKUN1_COUNTRY_0;
	String WKUN1_COUNTRY_0_X1;
	
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
	public void setWKUN1_SHORT_NAME_0(String wKUN1_SHORT_NAME_0) {
		WKUN1_SHORT_NAME_0 = wKUN1_SHORT_NAME_0;
	}
	public void setWKUN1_DESCR_0(String wKUN1_DESCR_0) {
		WKUN1_DESCR_0 = wKUN1_DESCR_0;
	}
	public void setWKUN1_DISP_LOGON_ID_0(String wKUN1_DISP_LOGON_ID_0) {
		WKUN1_DISP_LOGON_ID_0 = wKUN1_DISP_LOGON_ID_0;
	}
	public void setWKUN1_DISP_LOGON_ID_0_X1(String wKUN1_DISP_LOGON_ID_0_X1) {
		WKUN1_DISP_LOGON_ID_0_X1 = wKUN1_DISP_LOGON_ID_0_X1;
	}
	public void setWKUN1_DISP_LOGON_ID_0_X2(String wKUN1_DISP_LOGON_ID_0_X2) {
		WKUN1_DISP_LOGON_ID_0_X2 = wKUN1_DISP_LOGON_ID_0_X2;
	}
	public void setWKUN1_DISP_LOGON_ID_0_X3(String wKUN1_DISP_LOGON_ID_0_X3) {
		WKUN1_DISP_LOGON_ID_0_X3 = wKUN1_DISP_LOGON_ID_0_X3;
	}
	public void setWKUN1_WKUN_SHORT_NAME_0(String wKUN1_WKUN_SHORT_NAME_0) {
		WKUN1_WKUN_SHORT_NAME_0 = wKUN1_WKUN_SHORT_NAME_0;
	}
	public void setWKUN1_WKUN_SHORT_NAME_0_X1(String wKUN1_WKUN_SHORT_NAME_0_X1) {
		WKUN1_WKUN_SHORT_NAME_0_X1 = wKUN1_WKUN_SHORT_NAME_0_X1;
	}
	public void setWKUN1_WK_UNIT_NBR_0(String wKUN1_WK_UNIT_NBR_0) {
		WKUN1_WK_UNIT_NBR_0 = wKUN1_WK_UNIT_NBR_0;
	}
	public void setWKUN1_INTRNL_MAIL_ADDR_0(String wKUN1_INTRNL_MAIL_ADDR_0) {
		WKUN1_INTRNL_MAIL_ADDR_0 = wKUN1_INTRNL_MAIL_ADDR_0;
	}
	public void setWKUN1_FIRST_LINE_MAILING_0(String wKUN1_FIRST_LINE_MAILING_0) {
		WKUN1_FIRST_LINE_MAILING_0 = wKUN1_FIRST_LINE_MAILING_0;
	}
	public void setWKUN1_SCND_LINE_MAILING_0(String wKUN1_SCND_LINE_MAILING_0) {
		WKUN1_SCND_LINE_MAILING_0 = wKUN1_SCND_LINE_MAILING_0;
	}
	public void setWKUN1_CITY_0(String wKUN1_CITY_0) {
		WKUN1_CITY_0 = wKUN1_CITY_0;
	}
	public void setWKUN1_STATE_CODE_0(String wKUN1_STATE_CODE_0) {
		WKUN1_STATE_CODE_0 = wKUN1_STATE_CODE_0;
	}
	public void setWKUN1_ZIP_CODE_0(String wKUN1_ZIP_CODE_0) {
		WKUN1_ZIP_CODE_0 = wKUN1_ZIP_CODE_0;
	}
	public void setWKUN1_COUNTRY_0(String wKUN1_COUNTRY_0) {
		WKUN1_COUNTRY_0 = wKUN1_COUNTRY_0;
	}
	public void setWKUN1_COUNTRY_0_X1(String wKUN1_COUNTRY_0_X1) {
		WKUN1_COUNTRY_0_X1 = wKUN1_COUNTRY_0_X1;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setWKUN1_CITY_0(Stock.GetParameterValue("WKUN1_CITY_0"));
		 this.setWKUN1_COUNTRY_0(Stock.GetParameterValue("WKUN1_COUNTRY_0"));
		 this.setWKUN1_COUNTRY_0_X1(Stock.GetParameterValue("WKUN1_COUNTRY_0_X1"));
		 this.setWKUN1_DESCR_0(Stock.GetParameterValue("WKUN1_DESCR_0"));
		 this.setWKUN1_DISP_LOGON_ID_0(Stock.GetParameterValue("WKUN1_DISP_LOGON_ID_0"));
		 this.setWKUN1_DISP_LOGON_ID_0_X1(Stock.GetParameterValue("WKUN1_COUNTRY_0_X1"));
		 this.setWKUN1_DISP_LOGON_ID_0_X2(Stock.GetParameterValue("WKUN1_DISP_LOGON_ID_0_X2"));
		 this.setWKUN1_DISP_LOGON_ID_0_X3(Stock.GetParameterValue("WKUN1_DISP_LOGON_ID_0_X3"));
		 this.setWKUN1_FIRST_LINE_MAILING_0(Stock.GetParameterValue("WKUN1_FIRST_LINE_MAILING_0"));
		 this.setWKUN1_INTRNL_MAIL_ADDR_0(Stock.GetParameterValue("WKUN1_INTRNL_MAIL_ADDR_0"));
		 this.setWKUN1_SCND_LINE_MAILING_0(Stock.GetParameterValue("WKUN1_SCND_LINE_MAILING_0"));
		 this.setWKUN1_SHORT_NAME_0(Stock.GetParameterValue("WKUN1_SHORT_NAME_0"));
		 this.setWKUN1_STATE_CODE_0(Stock.GetParameterValue("WKUN1_STATE_CODE_0"));
		 this.setWKUN1_WK_UNIT_NBR_0(Stock.GetParameterValue("WKUN1_WK_UNIT_NBR_0"));
		 this.setWKUN1_WKUN_SHORT_NAME_0(Stock.GetParameterValue("WKUN1_WKUN_SHORT_NAME_0"));
		 this.setWKUN1_WKUN_SHORT_NAME_0_X1(Stock.GetParameterValue("WKUN1_WKUN_SHORT_NAME_0_X1"));
		 this.setWKUN1_ZIP_CODE_0(Stock.GetParameterValue("WKUN1_ZIP_CODE_0"));
		 
	//	 queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToDeleteWorkUnitInfo")[1], WKUN1_SHORT_NAME_0);
	//	 if(queryResultSet.rowDeleted()){		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&WKUN1_SHORT_NAME_0="+WKUN1_SHORT_NAME_0+"&WKUN1_DESCR_0="+WKUN1_DESCR_0+"&WKUN1_DISP_LOGON_ID_0="+WKUN1_DISP_LOGON_ID_0+"&WKUN1_DISP_LOGON_ID_0_X1="+WKUN1_DISP_LOGON_ID_0_X1+
				 "&WKUN1_DISP_LOGON_ID_0_X2="+WKUN1_DISP_LOGON_ID_0_X2+"&WKUN1_DISP_LOGON_ID_0_X3="+WKUN1_DISP_LOGON_ID_0_X3+"&WKUN1_WKUN_SHORT_NAME_0="+WKUN1_WKUN_SHORT_NAME_0+
				 "&WKUN1_WKUN_SHORT_NAME_0_X1="+WKUN1_WKUN_SHORT_NAME_0_X1+"&WKUN1_WK_UNIT_NBR_0="+WKUN1_WK_UNIT_NBR_0+"&WKUN1_INTRNL_MAIL_ADDR_0="+WKUN1_INTRNL_MAIL_ADDR_0+
				 "&WKUN1_FIRST_LINE_MAILING_0="+WKUN1_FIRST_LINE_MAILING_0+"&WKUN1_SCND_LINE_MAILING_0="+WKUN1_SCND_LINE_MAILING_0+"&WKUN1_CITY_0="+WKUN1_CITY_0+"&WKUN1_STATE_CODE_0="+WKUN1_STATE_CODE_0+
				 "&WKUN1_ZIP_CODE_0="+WKUN1_ZIP_CODE_0+"&WKUN1_COUNTRY_0="+WKUN1_COUNTRY_0+"&WKUN1_COUNTRY_0_X1="+WKUN1_COUNTRY_0_X1+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for WKUN service", this.queryString.replaceAll("&", "\n"), false);
		 
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
				Reporter.logEvent(Status.PASS, "Run  WKUN service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run WKUN service", "Running Failed:", false);
			}
			
	}

	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From response: ","TODAYS DATE: "+doc.getElementsByTagName("WKUN1_EFFDATE_0").item(0).getTextContent()+
					"\nCOUNTRY DESCR: "+doc.getElementsByTagName("WKUN1_COUNTRY_DESCR_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		
	}
	
	public void validateInDatabase() throws SQLException{
		String wkun_short_name = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTogetWorkUnitInfo")[1], WKUN1_SHORT_NAME_0);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating from database: ", "Records exists in database", false);
			wkun_short_name = queryResultSet.getString("SHORT_NAME");
			Reporter.logEvent(Status.INFO, "Values From database\nTable Name: WK_UNIT", "SHORT NAME: "+ queryResultSet.getString("SHORT_NAME")+
					"\nUNIT NBR: "+queryResultSet.getString("WK_UNIT_NBR")+
					"\nEFF DATE: "+queryResultSet.getString("EFFDATE")+
					"\nDESCR: "+queryResultSet.getString("DESCR"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating from database: ", "No record found in databse", false);
		}
		if(this.WKUN1_SHORT_NAME_0.equalsIgnoreCase(wkun_short_name)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating WKUN NAME from Input and Database", "Both the values are same as Expected"+
					"\nWKUN NAME: "+"From Response: "+this.WKUN1_SHORT_NAME_0+"\nFrom Database: "+wkun_short_name, false);
		}
  
	}
	
}
