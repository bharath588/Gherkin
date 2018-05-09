package pageobject.ADPART;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class ADPART_Add_Ppt {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ADPART_add_ppt";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0 = null; 
	String CONTROL_SSN_DISPL_0_X1 = null; 
	String CONTROL_GA_ID_0 ;
	String INDIVIDUAL_LAST_NAME_0 ;
	String INDIVIDUAL_FIRST_NAME_0;
	String INDIVIDUAL_MIDDLE_NAME_0;
	String ADDRESS_FIRST_LINE_MAILING_0;
	String ADDRESS_CITY_0;
	String ADDRESS_STATE_CODE_0;
	String ADDRESS_ZIP_CODE_0;
	String INDIVIDUAL_BIRTH_DATE_DISPLAY_0;
	String INDIVIDUAL_SEX_0;
	String INDIVIDUAL_MARITAL_STATUS_0;
	String INDIVIDUAL_HOME_PHONE_AREA_CODE_0;
	String INDIVIDUAL_HOME_PHONE_AREA_CODE_0_X1;
	String INDIVIDUAL_HOME_PHONE_NBR_0;
	String INDIVIDUAL_HOME_PHONE_NBR_0_X1;
	String  INDIVIDUAL_WORK_PHONE_AREA_CODE_0;
	String INDIVIDUAL_WORK_PHONE_AREA_CODE_0_X1;
	String INDIVIDUAL_WORK_PHONE_NBR_0;
	String INDIVIDUAL_WORK_PHONE_NBR_0_X1;
	String EMPLOYMENT_HIRE_DATE_DISPLAY_0;
	String IND_ELIGIBILITY_ELIGIBILITY_IND_0;
	String IND_ELIGIBILITY_PARTICIPATION_DATE_DISP_0;
	
	
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
	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
	}
	public void setCONTROL_SSN_DISPL_0_X1(String cONTROL_SSN_DISPL_0_X1) {
		CONTROL_SSN_DISPL_0_X1 = cONTROL_SSN_DISPL_0_X1;
	}
	public void setCONTROL_GA_ID_0(String cONTROL_GA_ID_0) {
		CONTROL_GA_ID_0 = cONTROL_GA_ID_0;
	}
	public void setINDIVIDUAL_LAST_NAME_0(String iNDIVIDUAL_LAST_NAME_0) {
		INDIVIDUAL_LAST_NAME_0 = iNDIVIDUAL_LAST_NAME_0;
	}
	public void setINDIVIDUAL_FIRST_NAME_0(String iNDIVIDUAL_FIRST_NAME_0) {
		INDIVIDUAL_FIRST_NAME_0 = iNDIVIDUAL_FIRST_NAME_0;
	}
	public void setINDIVIDUAL_MIDDLE_NAME_0(String iNDIVIDUAL_MIDDLE_NAME_0) {
		INDIVIDUAL_MIDDLE_NAME_0 = iNDIVIDUAL_MIDDLE_NAME_0;
	}
	public void setADDRESS_FIRST_LINE_MAILING_0(String aDDRESS_FIRST_LINE_MAILING_0) {
		ADDRESS_FIRST_LINE_MAILING_0 = aDDRESS_FIRST_LINE_MAILING_0;
	}
	public void setADDRESS_CITY_0(String aDDRESS_CITY_0) {
		ADDRESS_CITY_0 = aDDRESS_CITY_0;
	}
	public void setADDRESS_STATE_CODE_0(String aDDRESS_STATE_CODE_0) {
		ADDRESS_STATE_CODE_0 = aDDRESS_STATE_CODE_0;
	}
	public void setADDRESS_ZIP_CODE_0(String aDDRESS_ZIP_CODE_0) {
		ADDRESS_ZIP_CODE_0 = aDDRESS_ZIP_CODE_0;
	}
	public void setINDIVIDUAL_BIRTH_DATE_DISPLAY_0(
			String iNDIVIDUAL_BIRTH_DATE_DISPLAY_0) {
		INDIVIDUAL_BIRTH_DATE_DISPLAY_0 = iNDIVIDUAL_BIRTH_DATE_DISPLAY_0;
	}
	public void setINDIVIDUAL_SEX_0(String iNDIVIDUAL_SEX_0) {
		INDIVIDUAL_SEX_0 = iNDIVIDUAL_SEX_0;
	}
	public void setINDIVIDUAL_MARITAL_STATUS_0(String iNDIVIDUAL_MARITAL_STATUS_0) {
		INDIVIDUAL_MARITAL_STATUS_0 = iNDIVIDUAL_MARITAL_STATUS_0;
	}
	public void setINDIVIDUAL_HOME_PHONE_AREA_CODE_0(
			String iNDIVIDUAL_HOME_PHONE_AREA_CODE_0) {
		INDIVIDUAL_HOME_PHONE_AREA_CODE_0 = iNDIVIDUAL_HOME_PHONE_AREA_CODE_0;
	}
	public void setINDIVIDUAL_HOME_PHONE_AREA_CODE_0_X1(
			String iNDIVIDUAL_HOME_PHONE_AREA_CODE_0_X1) {
		INDIVIDUAL_HOME_PHONE_AREA_CODE_0_X1 = iNDIVIDUAL_HOME_PHONE_AREA_CODE_0_X1;
	}
	public void setINDIVIDUAL_HOME_PHONE_NBR_0(String iNDIVIDUAL_HOME_PHONE_NBR_0) {
		INDIVIDUAL_HOME_PHONE_NBR_0 = iNDIVIDUAL_HOME_PHONE_NBR_0;
	}
	public void setINDIVIDUAL_HOME_PHONE_NBR_0_X1(
			String iNDIVIDUAL_HOME_PHONE_NBR_0_X1) {
		INDIVIDUAL_HOME_PHONE_NBR_0_X1 = iNDIVIDUAL_HOME_PHONE_NBR_0_X1;
	}
	public void setINDIVIDUAL_WORK_PHONE_AREA_CODE_0(
			String iNDIVIDUAL_WORK_PHONE_AREA_CODE_0) {
		INDIVIDUAL_WORK_PHONE_AREA_CODE_0 = iNDIVIDUAL_WORK_PHONE_AREA_CODE_0;
	}
	public void setINDIVIDUAL_WORK_PHONE_AREA_CODE_0_X1(
			String iNDIVIDUAL_WORK_PHONE_AREA_CODE_0_X1) {
		INDIVIDUAL_WORK_PHONE_AREA_CODE_0_X1 = iNDIVIDUAL_WORK_PHONE_AREA_CODE_0_X1;
	}
	public void setINDIVIDUAL_WORK_PHONE_NBR_0(String iNDIVIDUAL_WORK_PHONE_NBR_0) {
		INDIVIDUAL_WORK_PHONE_NBR_0 = iNDIVIDUAL_WORK_PHONE_NBR_0;
	}
	public void setINDIVIDUAL_WORK_PHONE_NBR_0_X1(
			String iNDIVIDUAL_WORK_PHONE_NBR_0_X1) {
		INDIVIDUAL_WORK_PHONE_NBR_0_X1 = iNDIVIDUAL_WORK_PHONE_NBR_0_X1;
	}
	public void setEMPLOYMENT_HIRE_DATE_DISPLAY_0(
			String eMPLOYMENT_HIRE_DATE_DISPLAY_0) {
		EMPLOYMENT_HIRE_DATE_DISPLAY_0 = eMPLOYMENT_HIRE_DATE_DISPLAY_0;
	}
	public void setIND_ELIGIBILITY_ELIGIBILITY_IND_0(
			String iND_ELIGIBILITY_ELIGIBILITY_IND_0) {
		IND_ELIGIBILITY_ELIGIBILITY_IND_0 = iND_ELIGIBILITY_ELIGIBILITY_IND_0;
	}
	public void setIND_ELIGIBILITY_PARTICIPATION_DATE_DISP_0(
			String iND_ELIGIBILITY_PARTICIPATION_DATE_DISP_0) {
		IND_ELIGIBILITY_PARTICIPATION_DATE_DISP_0 = iND_ELIGIBILITY_PARTICIPATION_DATE_DISP_0;
	}
	
	
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setADDRESS_CITY_0(Stock.GetParameterValue("ADDRESS_CITY_0"));
		 this.setADDRESS_FIRST_LINE_MAILING_0(Stock.GetParameterValue("ADDRESS_FIRST_LINE_MAILING_0"));
		 this.setADDRESS_STATE_CODE_0(Stock.GetParameterValue("ADDRESS_STATE_CODE_0"));
		 this.setADDRESS_ZIP_CODE_0(Stock.GetParameterValue("ADDRESS_ZIP_CODE_0"));
		 this.setCONTROL_GA_ID_0(Stock.GetParameterValue("CONTROL_GA_ID_0"));
		 this.setEMPLOYMENT_HIRE_DATE_DISPLAY_0(Stock.GetParameterValue("EMPLOYMENT_HIRE_DATE_DISPLAY_0"));
		 this.setIND_ELIGIBILITY_ELIGIBILITY_IND_0(Stock.GetParameterValue("IND_ELIGIBILITY_ELIGIBILITY_IND_0"));
		 this.setIND_ELIGIBILITY_PARTICIPATION_DATE_DISP_0(Stock.GetParameterValue("IND_ELIGIBILITY_PARTICIPATION_DATE_DISP_0"));
		 this.setINDIVIDUAL_BIRTH_DATE_DISPLAY_0(Stock.GetParameterValue("INDIVIDUAL_BIRTH_DATE_DISPLAY_0"));
		 this.setINDIVIDUAL_FIRST_NAME_0(Stock.GetParameterValue("INDIVIDUAL_FIRST_NAME_0"));
		 this.setINDIVIDUAL_HOME_PHONE_AREA_CODE_0(Stock.GetParameterValue("INDIVIDUAL_HOME_PHONE_AREA_CODE_0"));
		 this.setINDIVIDUAL_HOME_PHONE_AREA_CODE_0_X1(Stock.GetParameterValue("INDIVIDUAL_HOME_PHONE_AREA_CODE_0_X1"));
		 this.setINDIVIDUAL_LAST_NAME_0(Stock.GetParameterValue("INDIVIDUAL_LAST_NAME_0"));
		 this.setINDIVIDUAL_HOME_PHONE_NBR_0(Stock.GetParameterValue("INDIVIDUAL_HOME_PHONE_NBR_0"));
		 this.setINDIVIDUAL_HOME_PHONE_NBR_0_X1(Stock.GetParameterValue("INDIVIDUAL_HOME_PHONE_NBR_0_X1"));
		 this.setINDIVIDUAL_MARITAL_STATUS_0(Stock.GetParameterValue("INDIVIDUAL_MARITAL_STATUS_0"));
		 this.setINDIVIDUAL_MIDDLE_NAME_0(Stock.GetParameterValue("INDIVIDUAL_MIDDLE_NAME_0"));
		 this.setINDIVIDUAL_SEX_0(Stock.GetParameterValue("INDIVIDUAL_SEX_0"));
		 this.setINDIVIDUAL_WORK_PHONE_AREA_CODE_0(Stock.GetParameterValue("INDIVIDUAL_WORK_PHONE_AREA_CODE_0"));
		 this.setINDIVIDUAL_WORK_PHONE_AREA_CODE_0_X1(Stock.GetParameterValue("INDIVIDUAL_WORK_PHONE_AREA_CODE_0_X1"));
		 this.setINDIVIDUAL_WORK_PHONE_NBR_0(Stock.GetParameterValue("INDIVIDUAL_WORK_PHONE_NBR_0"));
		 this.setINDIVIDUAL_WORK_PHONE_NBR_0_X1(Stock.GetParameterValue("INDIVIDUAL_WORK_PHONE_NBR_0_X1"));

		 while(this.CONTROL_SSN_DISPL_0 == null){
			Random rnd = new Random();
			int n = 100000000 + rnd.nextInt(900000000);
				
			queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForADPART")[1], String.valueOf(n));
				
			if(!(DB.getRecordSetCount(queryResultSet)>0)){
				this.setCONTROL_SSN_DISPL_0(String.valueOf(n));
				this.setCONTROL_SSN_DISPL_0_X1(String.valueOf(n));
			}
		}
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&CONTROL_SSN_DISPL_0_X1="+CONTROL_SSN_DISPL_0_X1+"&CONTROL_GA_ID_0="+CONTROL_GA_ID_0+
				 "&INDIVIDUAL_LAST_NAME_0="+INDIVIDUAL_LAST_NAME_0+"&INDIVIDUAL_FIRST_NAME_0="+INDIVIDUAL_FIRST_NAME_0+"&INDIVIDUAL_MIDDLE_NAME_0="+INDIVIDUAL_MIDDLE_NAME_0+
				 "&ADDRESS_FIRST_LINE_MAILING_0="+ADDRESS_FIRST_LINE_MAILING_0+"&ADDRESS_CITY_0="+ADDRESS_CITY_0+"&ADDRESS_STATE_CODE_0="+ADDRESS_STATE_CODE_0+
				 "&ADDRESS_ZIP_CODE_0="+ADDRESS_ZIP_CODE_0+"&INDIVIDUAL_BIRTH_DATE_DISPLAY_0="+INDIVIDUAL_BIRTH_DATE_DISPLAY_0+"&INDIVIDUAL_SEX_0="+INDIVIDUAL_SEX_0+
				 "&INDIVIDUAL_MARITAL_STATUS_0="+INDIVIDUAL_MARITAL_STATUS_0+"&INDIVIDUAL_HOME_PHONE_AREA_CODE_0="+INDIVIDUAL_HOME_PHONE_AREA_CODE_0+"&INDIVIDUAL_HOME_PHONE_AREA_CODE_0_X1="+INDIVIDUAL_HOME_PHONE_AREA_CODE_0_X1+
				 "&INDIVIDUAL_HOME_PHONE_NBR_0="+INDIVIDUAL_HOME_PHONE_NBR_0+"&INDIVIDUAL_HOME_PHONE_NBR_0_X1="+INDIVIDUAL_HOME_PHONE_NBR_0_X1+"&INDIVIDUAL_WORK_PHONE_AREA_CODE_0="+INDIVIDUAL_WORK_PHONE_AREA_CODE_0+
				 "&INDIVIDUAL_WORK_PHONE_AREA_CODE_0_X1="+INDIVIDUAL_WORK_PHONE_AREA_CODE_0_X1+"&INDIVIDUAL_WORK_PHONE_NBR_0="+INDIVIDUAL_WORK_PHONE_NBR_0+"&INDIVIDUAL_WORK_PHONE_NBR_0_X1="+INDIVIDUAL_WORK_PHONE_NBR_0_X1+
				 "&EMPLOYMENT_HIRE_DATE_DISPLAY_0="+EMPLOYMENT_HIRE_DATE_DISPLAY_0+"&IND_ELIGIBILITY_ELIGIBILITY_IND_0="+IND_ELIGIBILITY_ELIGIBILITY_IND_0+"&IND_ELIGIBILITY_PARTICIPATION_DATE_DISP_0="+IND_ELIGIBILITY_PARTICIPATION_DATE_DISP_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for ADPART service", this.queryString.replaceAll("&", "\n"), false);
		 
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
			Reporter.logEvent(Status.PASS, "Run ADPART service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ADPART service", "Running Failed:", false);
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
	
	public void validateInDatabase()throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForADPART")[1], this.CONTROL_SSN_DISPL_0);
		if(DB.getRecordSetCount(queryResultSet)>0){			
				Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database\nTable Name: INDIVIDUAL", false);
				while(queryResultSet.next()){								
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
					"\nSSN: "+queryResultSet.getString("SSN")+
					"\nFIRST_NAME: "+queryResultSet.getString("FIRST_NAME")+
					"\nLAST_NAME: "+queryResultSet.getString("LAST_NAME")+
					"\nBIRTHDATE: "+queryResultSet.getString("BIRTH_DATE"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}	
		
	}
}
