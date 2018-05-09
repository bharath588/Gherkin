package pageobject.SUBS;

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

public class SUBS_Insert_Group_Client_Subsets {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SUBS_INSERT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0 ;
	String GCS1_DEFAULT_IND_0; 
	String GCS1_BASIS_0 ;
	String GCS1_VALUE_0;
	String GCS1_NAME_0 ;
	String GCS1_EFFDATE_0 ;
	String GCS1_ONLINE_ENROLL_CODE_0; 
	String GCS1_OWNING_INFO_0 ;
	String GCS1_MAILING_NAME_1_0;
	String GCS1_FIRST_LINE_MAILING_0; 
	String GCS1_CITY_0 ;
	String GCS1_STATE_CODE_0; 
	String GCS1_ZIP_CODE_0 ;
	String GCS1_COUNTRY_0 ;
	String GCS1_ONLINE_ENROLL_WEEKEND_HOL_CODE_0; 
	String GCS1_OE_ENROLL_INVITE_CODE_0;  
	String GCS1_HARDSHIP_PROCESSING_CODE_0;
	
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
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	public void setGCS1_DEFAULT_IND_0(String gCS1_DEFAULT_IND_0) {
		GCS1_DEFAULT_IND_0 = gCS1_DEFAULT_IND_0;
	}
	public void setGCS1_BASIS_0(String gCS1_BASIS_0) {
		GCS1_BASIS_0 = gCS1_BASIS_0;
	}
	public void setGCS1_VALUE_0(String gCS1_VALUE_0) {
		GCS1_VALUE_0 = gCS1_VALUE_0;
	}
	public void setGCS1_NAME_0(String gCS1_NAME_0) {
		GCS1_NAME_0 = gCS1_NAME_0;
	}
	public void setGCS1_EFFDATE_0(String gCS1_EFFDATE_0) {
		GCS1_EFFDATE_0 = gCS1_EFFDATE_0;
	}
	public void setGCS1_ONLINE_ENROLL_CODE_0(String gCS1_ONLINE_ENROLL_CODE_0) {
		GCS1_ONLINE_ENROLL_CODE_0 = gCS1_ONLINE_ENROLL_CODE_0;
	}
	public void setGCS1_OWNING_INFO_0(String gCS1_OWNING_INFO_0) {
		GCS1_OWNING_INFO_0 = gCS1_OWNING_INFO_0;
	}
	public void setGCS1_MAILING_NAME_1_0(String gCS1_MAILING_NAME_1_0) {
		GCS1_MAILING_NAME_1_0 = gCS1_MAILING_NAME_1_0;
	}
	public void setGCS1_FIRST_LINE_MAILING_0(String gCS1_FIRST_LINE_MAILING_0) {
		GCS1_FIRST_LINE_MAILING_0 = gCS1_FIRST_LINE_MAILING_0;
	}
	public void setGCS1_CITY_0(String gCS1_CITY_0) {
		GCS1_CITY_0 = gCS1_CITY_0;
	}
	public void setGCS1_STATE_CODE_0(String gCS1_STATE_CODE_0) {
		GCS1_STATE_CODE_0 = gCS1_STATE_CODE_0;
	}
	public void setGCS1_ZIP_CODE_0(String gCS1_ZIP_CODE_0) {
		GCS1_ZIP_CODE_0 = gCS1_ZIP_CODE_0;
	}
	public void setGCS1_COUNTRY_0(String gCS1_COUNTRY_0) {
		GCS1_COUNTRY_0 = gCS1_COUNTRY_0;
	}
	public void setGCS1_ONLINE_ENROLL_WEEKEND_HOL_CODE_0(
			String gCS1_ONLINE_ENROLL_WEEKEND_HOL_CODE_0) {
		GCS1_ONLINE_ENROLL_WEEKEND_HOL_CODE_0 = gCS1_ONLINE_ENROLL_WEEKEND_HOL_CODE_0;
	}
	public void setGCS1_OE_ENROLL_INVITE_CODE_0(String gCS1_OE_ENROLL_INVITE_CODE_0) {
		GCS1_OE_ENROLL_INVITE_CODE_0 = gCS1_OE_ENROLL_INVITE_CODE_0;
	}
	public void setGCS1_HARDSHIP_PROCESSING_CODE_0(
			String gCS1_HARDSHIP_PROCESSING_CODE_0) {
		GCS1_HARDSHIP_PROCESSING_CODE_0 = gCS1_HARDSHIP_PROCESSING_CODE_0;
	} 

	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setGCS1_BASIS_0(Stock.GetParameterValue("GCS1_BASIS_0"));
		 this.setGCS1_CITY_0(Stock.GetParameterValue("GCS1_CITY_0"));
		 this.setGCS1_COUNTRY_0(Stock.GetParameterValue("GCS1_COUNTRY_0"));
		 this.setGCS1_DEFAULT_IND_0(Stock.GetParameterValue("GCS1_DEFAULT_IND_0"));
		 this.setGCS1_EFFDATE_0(Stock.GetParameterValue("GCS1_EFFDATE_0"));
		 this.setGCS1_FIRST_LINE_MAILING_0(Stock.GetParameterValue("GCS1_FIRST_LINE_MAILING_0"));
		 this.setGCS1_HARDSHIP_PROCESSING_CODE_0(Stock.GetParameterValue("GCS1_HARDSHIP_PROCESSING_CODE_0"));
		 this.setGCS1_MAILING_NAME_1_0(Stock.GetParameterValue("GCS1_MAILING_NAME_1_0"));
		 this.setGCS1_NAME_0(Stock.GetParameterValue("GCS1_NAME_0"));
		 this.setGCS1_OE_ENROLL_INVITE_CODE_0(Stock.GetParameterValue("GCS1_OE_ENROLL_INVITE_CODE_0"));
		 this.setGCS1_ONLINE_ENROLL_CODE_0(Stock.GetParameterValue("GCS1_ONLINE_ENROLL_CODE_0"));
		 this.setGCS1_ONLINE_ENROLL_WEEKEND_HOL_CODE_0(Stock.GetParameterValue("GCS1_ONLINE_ENROLL_WEEKEND_HOL_CODE_0"));
		 this.setGCS1_OWNING_INFO_0(Stock.GetParameterValue("GCS1_OWNING_INFO_0"));
		 this.setGCS1_STATE_CODE_0(Stock.GetParameterValue("GCS1_STATE_CODE_0"));
		 this.setGCS1_VALUE_0(Stock.GetParameterValue("GCS1_VALUE_0"));
		 this.setGCS1_ZIP_CODE_0(Stock.GetParameterValue("GCS1_ZIP_CODE_0"));
		 this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
	
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0
		 		+ "&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&GCS1_DEFAULT_IND_0="+GCS1_DEFAULT_IND_0+"GCS1_BASIS_0="+GCS1_BASIS_0+"GCS1_VALUE_0="+GCS1_VALUE_0
		 		+ "&GCS1_NAME_0="+GCS1_NAME_0+"&GCS1_EFFDATE_0="+GCS1_EFFDATE_0+"&GCS1_ONLINE_ENROLL_CODE_0="+GCS1_ONLINE_ENROLL_CODE_0
		 		+ "&GCS1_OWNING_INFO_0="+GCS1_OWNING_INFO_0+"GCS1_MAILING_NAME_1_0="+GCS1_MAILING_NAME_1_0+"GCS1_FIRST_LINE_MAILING_0="+GCS1_FIRST_LINE_MAILING_0
		 		+ "&GCS1_CITY_0="+GCS1_CITY_0+"&GCS1_STATE_CODE_0="+GCS1_STATE_CODE_0+"&GCS1_ZIP_CODE_0="+GCS1_ZIP_CODE_0+"&GCS1_COUNTRY_0="+GCS1_COUNTRY_0
		 		+ "GCS1_ONLINE_ENROLL_WEEKEND_HOL_CODE_0="+GCS1_ONLINE_ENROLL_WEEKEND_HOL_CODE_0+"&GCS1_OE_ENROLL_INVITE_CODE_0="+GCS1_OE_ENROLL_INVITE_CODE_0
		 		+ "&GCS1_HARDSHIP_PROCESSING_CODE_0="+GCS1_HARDSHIP_PROCESSING_CODE_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for SUBS service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run  SUBS_INSERT service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run SUBS_INSERT service", "Running Failed:", false);
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
	
		Reporter.logEvent(Status.INFO, "Values From response: ","CLASS NAME: "+doc.getElementsByTagName("CFG_CONTROL_CLASS_NAME_0").item(0).getTextContent()+
				"\nGCS_BASIS: "+doc.getElementsByTagName("GDMT_hash_GCS_CLASS_GCS_BASIS_0").item(0).getTextContent()+
				"\nGCS VALUE: "+doc.getElementsByTagName("GDMT_hash_GCS_CLASS_GCS_VALUE_0").item(0).getTextContent()+
				"\n",false);
	}
	
	public void validateInDatabase() throws SQLException{
		String BASIS = doc.getElementsByTagName("GDMT_hash_GCS_CLASS_GCS_BASIS_0").item(0).getTextContent();
		String basis=null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToDispSUBS")[1], (GET_GA_GA_ID_0.split("-"))[0]);
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database",false);
			basis = queryResultSet.getString("BASIS");
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "GC_ID: "+ queryResultSet.getString("GC_ID")+
					"\nNAME: "+queryResultSet.getString("NAME")+
					"\nBASIS: "+queryResultSet.getString("BASIS")+
					"\nEFF DATE: "+queryResultSet.getString("EFFDATE"), false);
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
		}
		if(BASIS.equalsIgnoreCase(basis)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating BASIS value from Response and Database", "Both the values are same as Expected"+
					"\nBASIS: "+"From Response: "+BASIS+"\nFrom Database: "+basis, false);
		}
		
	} 
	
}
