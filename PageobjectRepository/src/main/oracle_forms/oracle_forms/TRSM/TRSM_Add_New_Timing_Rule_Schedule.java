package pageobject.TRSM;

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

public class TRSM_Add_New_Timing_Rule_Schedule {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/TRSM_Add_New_Timing_Rule_Schedule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String LOGON_DATABASE_X1;
	String LOGON_PASSWORD_X1;
	String LOGON_USERNAME_X1;
	String TIMING_RULE_SCHEDULE_PROV_COMPANY_0;
	String TIMING_RULE_SCHEDULE_SCHEDULE_DATE_0;
	String TIMING_RULE_SCHEDULE_DB_CHK_DETL_RPT_DAYS_0;
	String TIMING_RULE_SCHEDULE_DB_CASH_DROP_RUN_DAYS_0;
	String TIMING_RULE_SCHEDULE_DB_CHK_REGISTER_RPT_DAYS_0;
	
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
	public void setLOGON_DATABASE_X1(String lOGON_DATABASE_X1) {
		LOGON_DATABASE_X1 = lOGON_DATABASE_X1;
	}
	public void setLOGON_PASSWORD_X1(String lOGON_PASSWORD_X1) {
		LOGON_PASSWORD_X1 = lOGON_PASSWORD_X1;
	}
	public void setLOGON_USERNAME_X1(String lOGON_USERNAME_X1) {
		LOGON_USERNAME_X1 = lOGON_USERNAME_X1;
	}
	public void setTIMING_RULE_SCHEDULE_PROV_COMPANY_0(
			String tIMING_RULE_SCHEDULE_PROV_COMPANY_0) {
		TIMING_RULE_SCHEDULE_PROV_COMPANY_0 = tIMING_RULE_SCHEDULE_PROV_COMPANY_0;
	}
	public void setTIMING_RULE_SCHEDULE_SCHEDULE_DATE_0(
			String tIMING_RULE_SCHEDULE_SCHEDULE_DATE_0) {
		TIMING_RULE_SCHEDULE_SCHEDULE_DATE_0 = tIMING_RULE_SCHEDULE_SCHEDULE_DATE_0;
	}
	public void setTIMING_RULE_SCHEDULE_DB_CHK_DETL_RPT_DAYS_0(
			String tIMING_RULE_SCHEDULE_DB_CHK_DETL_RPT_DAYS_0) {
		TIMING_RULE_SCHEDULE_DB_CHK_DETL_RPT_DAYS_0 = tIMING_RULE_SCHEDULE_DB_CHK_DETL_RPT_DAYS_0;
	}
	public void setTIMING_RULE_SCHEDULE_DB_CASH_DROP_RUN_DAYS_0(
			String tIMING_RULE_SCHEDULE_DB_CASH_DROP_RUN_DAYS_0) {
		TIMING_RULE_SCHEDULE_DB_CASH_DROP_RUN_DAYS_0 = tIMING_RULE_SCHEDULE_DB_CASH_DROP_RUN_DAYS_0;
	}
	public void setTIMING_RULE_SCHEDULE_DB_CHK_REGISTER_RPT_DAYS_0(
			String tIMING_RULE_SCHEDULE_DB_CHK_REGISTER_RPT_DAYS_0) {
		TIMING_RULE_SCHEDULE_DB_CHK_REGISTER_RPT_DAYS_0 = tIMING_RULE_SCHEDULE_DB_CHK_REGISTER_RPT_DAYS_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE_X1(Stock.GetParameterValue("LOGON_DATABASE_X1"));
		this.setLOGON_PASSWORD_X1(Stock.GetParameterValue("LOGON_PASSWORD_X1"));
		this.setLOGON_USERNAME_X1(Stock.GetParameterValue("LOGON_USERNAME_X1"));
		this.setTIMING_RULE_SCHEDULE_DB_CASH_DROP_RUN_DAYS_0(Stock.GetParameterValue("TIMING_RULE_SCHEDULE_DB_CASH_DROP_RUN_DAYS_0"));
		this.setTIMING_RULE_SCHEDULE_DB_CHK_DETL_RPT_DAYS_0(Stock.GetParameterValue("TIMING_RULE_SCHEDULE_DB_CHK_DETL_RPT_DAYS_0"));
		this.setTIMING_RULE_SCHEDULE_DB_CHK_REGISTER_RPT_DAYS_0(Stock.GetParameterValue("TIMING_RULE_SCHEDULE_DB_CHK_REGISTER_RPT_DAYS_0"));
		this.setTIMING_RULE_SCHEDULE_PROV_COMPANY_0(Stock.GetParameterValue("TIMING_RULE_SCHEDULE_PROV_COMPANY_0"));
		this.setTIMING_RULE_SCHEDULE_SCHEDULE_DATE_0(Stock.GetParameterValue("TIMING_RULE_SCHEDULE_SCHEDULE_DATE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				"&LOGON_USERNAME_X1="+LOGON_USERNAME_X1+"&LOGON_PASSWORD_X1="+LOGON_PASSWORD_X1+"&LOGON_DATABASE_X1="+LOGON_DATABASE_X1+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&TIMING_RULE_SCHEDULE_PROV_COMPANY_0="+TIMING_RULE_SCHEDULE_PROV_COMPANY_0+"&TIMING_RULE_SCHEDULE_SCHEDULE_DATE_0="+TIMING_RULE_SCHEDULE_SCHEDULE_DATE_0+
				"&TIMING_RULE_SCHEDULE_DB_CHK_DETL_RPT_DAYS_0="+TIMING_RULE_SCHEDULE_DB_CHK_DETL_RPT_DAYS_0+"&TIMING_RULE_SCHEDULE_DB_CASH_DROP_RUN_DAYS_0="+TIMING_RULE_SCHEDULE_DB_CASH_DROP_RUN_DAYS_0+
				"&TIMING_RULE_SCHEDULE_DB_CHK_REGISTER_RPT_DAYS_0="+TIMING_RULE_SCHEDULE_DB_CHK_REGISTER_RPT_DAYS_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for TRSM service", this.queryString.replaceAll("&", "\n"), false);
		
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
//			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run TRSM service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run TRSM service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			}
		else {
		//	Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String prov_company_input = this.TIMING_RULE_SCHEDULE_PROV_COMPANY_0;
		String prov_company_Db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTRSMAdd")[1],  this.TIMING_RULE_SCHEDULE_PROV_COMPANY_0, this.TIMING_RULE_SCHEDULE_SCHEDULE_DATE_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			while(queryResultSet.next()){
				prov_company_Db = queryResultSet.getString("PROV_COMPANY");
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists for Event", false);			
				Reporter.logEvent(Status.PASS, "From DATABASE: \nTable Name: TIMING_RULE_SCHEDULE", "PROV_COMPANY: "+queryResultSet.getString("PROV_COMPANY")+
						"\nGA_ID: "+queryResultSet.getString("GA_ID")+
						"\nSCHEDULE_DATE: "+queryResultSet.getString("SCHEDULE_DATE"), false);
			}
			if(prov_company_input.equalsIgnoreCase(prov_company_Db)){
				Reporter.logEvent(Status.PASS, "Comparing and Validating providing Company from Input and database", "Both the values are same as expected"+
						"\nInput: "+prov_company_input+
						"\nDatabase: "+prov_company_Db, false);	
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating providing Company from Input and database", "Both the values are not same"+
						"\nInput: "+prov_company_input+
						"\nDatabase: "+prov_company_Db, false);	
			}
		}	
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
	}

}
