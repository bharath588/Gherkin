package pageobject.VSCF;

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

public class VSCF_Upload_Hours {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/VSCF_Upload_Hours";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String STEP_TYPE_LOV;
	String VESTING_CMPUT_DETL_CREDITED_HOURS_0;
	String VESTING_CMPUT_DETL_EFFDATE_0;
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
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}
	public void setVESTING_CMPUT_DETL_CREDITED_HOURS_0(
			String vESTING_CMPUT_DETL_CREDITED_HOURS_0) {
		VESTING_CMPUT_DETL_CREDITED_HOURS_0 = vESTING_CMPUT_DETL_CREDITED_HOURS_0;
	}
	public void setVESTING_CMPUT_DETL_EFFDATE_0(String vESTING_CMPUT_DETL_EFFDATE_0) {
		VESTING_CMPUT_DETL_EFFDATE_0 = vESTING_CMPUT_DETL_EFFDATE_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		this.setVESTING_CMPUT_DETL_CREDITED_HOURS_0(Stock.GetParameterValue("VESTING_CMPUT_DETL_CREDITED_HOURS_0"));
		this.setVESTING_CMPUT_DETL_EFFDATE_0(Stock.GetParameterValue("VESTING_CMPUT_DETL_EFFDATE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_DATABASE="+LOGON_DATABASE+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+
				"&STEP_TYPE_LOV="+STEP_TYPE_LOV+
				"&VESTING_CMPUT_DETL_CREDITED_HOURS_0="+VESTING_CMPUT_DETL_CREDITED_HOURS_0+
				"&VESTING_CMPUT_DETL_EFFDATE_0="+VESTING_CMPUT_DETL_EFFDATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for VSCF service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run VSCF service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run VSCF service", "Running Failed:", false);
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
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForVSCF1")[1], this.CONTROL_SSN_DISPL_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: VESTING_COMPUTATION_PERD", "Records exists in the Database", false);
			while(queryResultSet.next()){				
				Reporter.logEvent(Status.INFO, "Values from DB", "Updated Values in DB\n"+"INFO_CHG_DPDATE_TIME: "+queryResultSet.getString("INFO_CHG_DPDATE_TIME")+
						"\nCREDITED_HOURS: "+queryResultSet.getString("CREDITED_HOURS")+
						"\nSERVICE_YEARS: "+queryResultSet.getString("SERVICE_YEARS"), false);	
			}			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForVSCF2")[1], this.CONTROL_SSN_DISPL_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: VESTING_COMPUTATION_DETL", "Records inserted into the Database", false);
			while(queryResultSet.next()){				
				Reporter.logEvent(Status.INFO, "Values from DB", "PLAN_ID: "+queryResultSet.getString("PLAN_ID")+
						"\nEV_ID: "+queryResultSet.getString("EV_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME"), false);	
			}			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
	
	public void setUpData() throws SQLException{
		
		try {
			int updatedRows = DB.executeUpdate(General.dbInstance, Stock.getTestQuery("queryForVSCFUpdate")[1], this.CONTROL_SSN_DISPL_0);
			System.out.println("No of rows updated: "+updatedRows);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
}