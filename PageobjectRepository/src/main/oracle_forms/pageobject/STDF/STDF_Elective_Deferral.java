package pageobject.STDF;

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

public class STDF_Elective_Deferral {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STDF_chg_deferral_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet1;
	private ResultSet queryResultSet2;
	


	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME; 
	String CONTROL_SSN_DISPL_0;
	String ELECTIVE_DEFERRAL_CHANGE_CODE_1;
	String ELECTIVE_DEFERRAL_PERCENTAGE_1;
	String STDT_CODE_LOV;
	String STEP_TYPE_LOV;
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
	public void setELECTIVE_DEFERRAL_CHANGE_CODE_1(
			String eLECTIVE_DEFERRAL_CHANGE_CODE_1) {
		ELECTIVE_DEFERRAL_CHANGE_CODE_1 = eLECTIVE_DEFERRAL_CHANGE_CODE_1;
	}
	public void setELECTIVE_DEFERRAL_PERCENTAGE_1(
			String eLECTIVE_DEFERRAL_PERCENTAGE_1) {
		ELECTIVE_DEFERRAL_PERCENTAGE_1 = eLECTIVE_DEFERRAL_PERCENTAGE_1;
	}
	public void setSTDT_CODE_LOV(String sTDT_CODE_LOV) {
		STDT_CODE_LOV = sTDT_CODE_LOV;
	}
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}
	
	String CONTROL_BUTTON_OTHER_FORMS_0;
	String ELECTIVE_DEFERRAL_STDT_CODE_0;
	
	public String getCONTROL_BUTTON_OTHER_FORMS_0() {
		return CONTROL_BUTTON_OTHER_FORMS_0;
	}
	public String getELECTIVE_DEFERRAL_STDT_CODE_0() {
		return ELECTIVE_DEFERRAL_STDT_CODE_0;
	}
	
	public void setServiceParameters()
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		 this.setELECTIVE_DEFERRAL_CHANGE_CODE_1(Stock.GetParameterValue("ELECTIVE_DEFERRAL_CHANGE_CODE_1"));
		 this.setELECTIVE_DEFERRAL_PERCENTAGE_1(Stock.GetParameterValue("ELECTIVE_DEFERRAL_PERCENTAGE_1"));
		 this.setSTDT_CODE_LOV(Stock.GetParameterValue("STDT_CODE_LOV"));
		 this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
	
	queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
			"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+
			"&STEP_TYPE_LOV="+STEP_TYPE_LOV+"&STDT_CODE_LOV="+STDT_CODE_LOV+"&ELECTIVE_DEFERRAL_CHANGE_CODE_1="+ELECTIVE_DEFERRAL_CHANGE_CODE_1+
			"&ELECTIVE_DEFERRAL_PERCENTAGE_1="+ELECTIVE_DEFERRAL_PERCENTAGE_1+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
	
	Reporter.logEvent(Status.INFO, "Prepare test data for STDF service", this.queryString.replaceAll("&", "\n"), false);
	
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
			Reporter.logEvent(Status.PASS, "Run STDF Change a participant elective deferral service ", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STDF Change a participant elective deferral service", "Running Failed:", false);
		}
		
	}
	
	public void validateOutput(){
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		
	}
	
	public void validateInDatabase() throws SQLException{
		
		String ev_id = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToVerifyNewRecordSTDF")[1], CONTROL_SSN_DISPL_0);
		if(queryResultSet.next()){
			String Plan_id = queryResultSet.getString("PLAN_ID");
			String Ind_id = queryResultSet.getString("IND_ID");
			String Stdt_code = queryResultSet.getString("STDT_CODE");
			String Ev_id = queryResultSet.getString("EV_ID");
			String Percentage = queryResultSet.getString("PERCENTAGE");
			String Amount = queryResultSet.getString("AMOUNT");
			Reporter.logEvent(Status.PASS,"Validating record existence in Database","Records exists in Database\nTable Name: ELECTIVE_DEFERRAL",false);
			Reporter.logEvent(Status.INFO, "From Database", "PLAN_ID: " + Plan_id + "\nIND_ID: " + Ind_id + "\nSTDT_CODE: " + Stdt_code + "\nEV_ID: " + Ev_id , false);
				
		}
		
		queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToVerifyNewRecordSTDFEvent")[1], CONTROL_SSN_DISPL_0);
		if(queryResultSet1.next()){
			ev_id = queryResultSet1.getString("ID");
			Reporter.logEvent(Status.PASS,"Validating record existence in Database","Records exists in Database\nTable Name: EVENT",false);
			Reporter.logEvent(Status.INFO, "From Database: ","ID: "+queryResultSet1.getString("ID")+
						"\nEVTY_CODE: "+queryResultSet1.getString("EVTY_CODE")+
						"\nDPDATE_TIME: "+queryResultSet1.getString("DPDATE_TIME"),false);
		}
		
		queryResultSet2 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToVerifyNewRecordSTDFStep")[1], ev_id);
		if(queryResultSet2.next()){
			Reporter.logEvent(Status.PASS,"Validating record existence in Database","Records exists in Database\nTable Name: STEP",false);
			Reporter.logEvent(Status.INFO,"From Database ","Records exists with event id: "+ev_id+"\nTotal number of records: "+DB.getRecordSetCount(queryResultSet2),false);
		}
				
	}
	
}
