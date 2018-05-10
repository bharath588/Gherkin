package pageobject.RCWV;

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

public class RCWV_Insert {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/RCWV_INSERT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String  LOGON_DATABASE ;
	String  LOGON_PASSWORD ;
	String  LOGON_USERNAME ;
	String CONTROL_SELECTION_0;
	String CONTROL_SSN_DISPL_0;
	String STEP_TYPE_LOV;
	String PART_RECOV_WVR_RECOV_ID_0;
	String PART_RECOV_WVR_EFFDATE_0;
	String PART_RECOV_WVR_RSN_DESCR_0;
	
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
	}
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}
	public void setPART_RECOV_WVR_RECOV_ID_0(String pART_RECOV_WVR_RECOV_ID_0) {
		PART_RECOV_WVR_RECOV_ID_0 = pART_RECOV_WVR_RECOV_ID_0;
	}
	public void setPART_RECOV_WVR_EFFDATE_0(String pART_RECOV_WVR_EFFDATE_0) {
		PART_RECOV_WVR_EFFDATE_0 = pART_RECOV_WVR_EFFDATE_0;
	}
	public void setPART_RECOV_WVR_RSN_DESCR_0(String pART_RECOV_WVR_RSN_DESCR_0) {
		PART_RECOV_WVR_RSN_DESCR_0 = pART_RECOV_WVR_RSN_DESCR_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		 this.setPART_RECOV_WVR_EFFDATE_0(Stock.GetParameterValue("PART_RECOV_WVR_EFFDATE_0"));
		 this.setPART_RECOV_WVR_RECOV_ID_0(Stock.GetParameterValue("PART_RECOV_WVR_RECOV_ID_0"));
		 this.setPART_RECOV_WVR_RSN_DESCR_0(Stock.GetParameterValue("PART_RECOV_WVR_RSN_DESCR_0"));
		 this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&STEP_TYPE_LOV="+STEP_TYPE_LOV+
				 "&PART_RECOV_WVR_RECOV_ID_0="+PART_RECOV_WVR_RECOV_ID_0+"&PART_RECOV_WVR_EFFDATE_0="+PART_RECOV_WVR_EFFDATE_0+
				 "&PART_RECOV_WVR_RSN_DESCR_0="+PART_RECOV_WVR_RSN_DESCR_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for RCWV service", this.queryString.replaceAll("&", "\n"), false);
		 
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
				Reporter.logEvent(Status.PASS, "Run  RCWV_INSERT service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run RCWV_INSERT service", "Running Failed:", false);
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
	
	//	Reporter.logEvent(Status.PASS, "From response: " , "",false);
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToChangeWaiverInfo")[1], CONTROL_SSN_DISPL_0);
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS,"Validating Record creation in database","Record is created int Database\nTable Name: PART_RECOV_WVR",false);
			Reporter.logEvent(Status.INFO, "From DATABASE: ", "RECOV_ID: "+ queryResultSet.getString("RECOV_ID")+
					"\nGA_ID: "+queryResultSet.getString("GA_ID")+
					"\nIND_ID: "+queryResultSet.getString("IND_ID")+
					"\nDESCRIPTION: "+queryResultSet.getString("RSN_DESCR")+
					"\nEFFECT DATE: "+queryResultSet.getString("EFFDATE"),false);
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
		}
		
	} 
}
