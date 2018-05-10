package pageobject.DSRM;

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

public class DSRM_Add_Disb_Rule {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DSRM_ADD_DISBURSEMENT_RULE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SELECT_PARM_DISB_TYPE_0;
	String SELECT_PARM_EVENT_TYPE_0;
	String DISB_REASONS_AVAILABLE_0;
	String DISB_STEPS_AVAILABLE_0;
	String DISB_STEPS_AVAILABLE_0_X1;
	String DISB_STEPS_AVAILABLE_0_X2;
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
	public void setSELECT_PARM_DISB_TYPE_0(String sELECT_PARM_DISB_TYPE_0) {
		SELECT_PARM_DISB_TYPE_0 = sELECT_PARM_DISB_TYPE_0;
	}
	public void setSELECT_PARM_EVENT_TYPE_0(String sELECT_PARM_EVENT_TYPE_0) {
		SELECT_PARM_EVENT_TYPE_0 = sELECT_PARM_EVENT_TYPE_0;
	}
	public void setDISB_REASONS_AVAILABLE_0(String dISB_REASONS_AVAILABLE_0) {
		DISB_REASONS_AVAILABLE_0 = dISB_REASONS_AVAILABLE_0;
	}
	public void setDISB_STEPS_AVAILABLE_0(String dISB_STEPS_AVAILABLE_0) {
		DISB_STEPS_AVAILABLE_0 = dISB_STEPS_AVAILABLE_0;
	}
	public void setDISB_STEPS_AVAILABLE_0_X1(String dISB_STEPS_AVAILABLE_0_X1) {
		DISB_STEPS_AVAILABLE_0_X1 = dISB_STEPS_AVAILABLE_0_X1;
	}
	public void setDISB_STEPS_AVAILABLE_0_X2(String dISB_STEPS_AVAILABLE_0_X2) {
		DISB_STEPS_AVAILABLE_0_X2 = dISB_STEPS_AVAILABLE_0_X2;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setDISB_REASONS_AVAILABLE_0(Stock.GetParameterValue("DISB_REASONS_AVAILABLE_0"));
		this.setDISB_STEPS_AVAILABLE_0(Stock.GetParameterValue("DISB_STEPS_AVAILABLE_0"));
		this.setDISB_STEPS_AVAILABLE_0_X1(Stock.GetParameterValue("DISB_STEPS_AVAILABLE_0_X1"));
		this.setDISB_STEPS_AVAILABLE_0_X2(Stock.GetParameterValue("DISB_STEPS_AVAILABLE_0_X2"));
		this.setSELECT_PARM_DISB_TYPE_0(Stock.GetParameterValue("SELECT_PARM_DISB_TYPE_0"));
		this.setSELECT_PARM_EVENT_TYPE_0(Stock.GetParameterValue("SELECT_PARM_EVENT_TYPE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SELECT_PARM_DISB_TYPE_0="+SELECT_PARM_DISB_TYPE_0+"&SELECT_PARM_EVENT_TYPE_0="+SELECT_PARM_EVENT_TYPE_0+"&DISB_REASONS_AVAILABLE_0="+DISB_REASONS_AVAILABLE_0+
				"&DISB_STEPS_AVAILABLE_0="+DISB_STEPS_AVAILABLE_0+"&DISB_STEPS_AVAILABLE_0_X1="+DISB_STEPS_AVAILABLE_0_X1+"&DISB_STEPS_AVAILABLE_0_X2="+DISB_STEPS_AVAILABLE_0_X2+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for DSRM service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run DSRM service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DSRM service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "DISB_TYPE: "+doc.getElementsByTagName("DISB_STEP_RULE_DISB_TYPE_0").item(0).getTextContent(), false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String disb_type = doc.getElementsByTagName("DISB_STEP_RULE_DISB_TYPE_0").item(0).getTextContent();
		String disb_type_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDSRM")[1], disb_type);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: DISB_STEP_RULE",false);
			Reporter.logEvent(Status.INFO, "Expected: Query sould return more than one record", "From Database: Total number of records:"+
			DB.getRecordSetCount(queryResultSet),false);					
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
		}
	}
}
