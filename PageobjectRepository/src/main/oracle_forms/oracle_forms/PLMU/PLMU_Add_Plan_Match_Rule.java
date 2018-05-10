package pageobject.PLMU;

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

public class PLMU_Add_Plan_Match_Rule {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PLMU_Add_Plan_Match_Rule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String PLAN_MATCH_RULE_DESCR_0;
	String PLAN_MATCH_RULE_CALC_SOURCE_0;
	String PLAN_MATCH_RULE_CALC_TYPE_0;
	String PLAN_MATCH_RULE_CALC_FREQUENCY_0;
	String PLMR_CALC_VERIFY_LOV;
	String PLMR_SDMT_CODE_LOV;
	String PLAN_MATCH_RULE_CRIT_MATCH_CALCULATED_ON_0;
	String PLMR_RATE_TIER_BASED_ON_LOV;
	String PLAN_MATCH_RULE_RATE_MATCH_PERCENT_0;
	String PLMR_RATE_TIER_BASED_ON_LOV_X1;
	
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
	public void setPLAN_MATCH_RULE_DESCR_0(String pLAN_MATCH_RULE_DESCR_0) {
		PLAN_MATCH_RULE_DESCR_0 = pLAN_MATCH_RULE_DESCR_0;
	}
	public void setPLAN_MATCH_RULE_CALC_SOURCE_0(
			String pLAN_MATCH_RULE_CALC_SOURCE_0) {
		PLAN_MATCH_RULE_CALC_SOURCE_0 = pLAN_MATCH_RULE_CALC_SOURCE_0;
	}
	public void setPLAN_MATCH_RULE_CALC_TYPE_0(String pLAN_MATCH_RULE_CALC_TYPE_0) {
		PLAN_MATCH_RULE_CALC_TYPE_0 = pLAN_MATCH_RULE_CALC_TYPE_0;
	}
	public void setPLAN_MATCH_RULE_CALC_FREQUENCY_0(
			String pLAN_MATCH_RULE_CALC_FREQUENCY_0) {
		PLAN_MATCH_RULE_CALC_FREQUENCY_0 = pLAN_MATCH_RULE_CALC_FREQUENCY_0;
	}
	public void setPLMR_CALC_VERIFY_LOV(String pLMR_CALC_VERIFY_LOV) {
		PLMR_CALC_VERIFY_LOV = pLMR_CALC_VERIFY_LOV;
	}
	public void setPLMR_SDMT_CODE_LOV(String pLMR_SDMT_CODE_LOV) {
		PLMR_SDMT_CODE_LOV = pLMR_SDMT_CODE_LOV;
	}
	public void setPLAN_MATCH_RULE_CRIT_MATCH_CALCULATED_ON_0(
			String pLAN_MATCH_RULE_CRIT_MATCH_CALCULATED_ON_0) {
		PLAN_MATCH_RULE_CRIT_MATCH_CALCULATED_ON_0 = pLAN_MATCH_RULE_CRIT_MATCH_CALCULATED_ON_0;
	}
	public void setPLMR_RATE_TIER_BASED_ON_LOV(String pLMR_RATE_TIER_BASED_ON_LOV) {
		PLMR_RATE_TIER_BASED_ON_LOV = pLMR_RATE_TIER_BASED_ON_LOV;
	}
	public void setPLAN_MATCH_RULE_RATE_MATCH_PERCENT_0(
			String pLAN_MATCH_RULE_RATE_MATCH_PERCENT_0) {
		PLAN_MATCH_RULE_RATE_MATCH_PERCENT_0 = pLAN_MATCH_RULE_RATE_MATCH_PERCENT_0;
	}
	public void setPLMR_RATE_TIER_BASED_ON_LOV_X1(
			String pLMR_RATE_TIER_BASED_ON_LOV_X1) {
		PLMR_RATE_TIER_BASED_ON_LOV_X1 = pLMR_RATE_TIER_BASED_ON_LOV_X1;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setPLAN_MATCH_RULE_CALC_FREQUENCY_0(Stock.GetParameterValue("PLAN_MATCH_RULE_CALC_FREQUENCY_0"));
		this.setPLAN_MATCH_RULE_CALC_SOURCE_0(Stock.GetParameterValue("PLAN_MATCH_RULE_CALC_SOURCE_0"));
		this.setPLAN_MATCH_RULE_CALC_TYPE_0(Stock.GetParameterValue("PLAN_MATCH_RULE_CALC_SOURCE_0"));
		this.setPLAN_MATCH_RULE_CRIT_MATCH_CALCULATED_ON_0(Stock.GetParameterValue("PLAN_MATCH_RULE_CRIT_MATCH_CALCULATED_ON_0"));
		this.setPLAN_MATCH_RULE_DESCR_0(Stock.GetParameterValue("PLAN_MATCH_RULE_DESCR_0"));
		this.setPLAN_MATCH_RULE_RATE_MATCH_PERCENT_0(Stock.GetParameterValue("PLAN_MATCH_RULE_RATE_MATCH_PERCENT_0"));
		this.setPLMR_CALC_VERIFY_LOV(Stock.GetParameterValue("PLMR_CALC_VERIFY_LOV"));
		this.setPLMR_RATE_TIER_BASED_ON_LOV(Stock.GetParameterValue("PLMR_RATE_TIER_BASED_ON_LOV"));
		this.setPLMR_RATE_TIER_BASED_ON_LOV_X1(Stock.GetParameterValue("PLMR_RATE_TIER_BASED_ON_LOV_X1"));
		this.setPLMR_SDMT_CODE_LOV(Stock.GetParameterValue("PLMR_SDMT_CODE_LOV"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&PLAN_MATCH_RULE_DESCR_0="+PLAN_MATCH_RULE_DESCR_0+"&PLAN_MATCH_RULE_CALC_SOURCE_0="+PLAN_MATCH_RULE_CALC_SOURCE_0+
				"&PLAN_MATCH_RULE_CALC_TYPE_0="+PLAN_MATCH_RULE_CALC_TYPE_0+"&PLAN_MATCH_RULE_CALC_FREQUENCY_0="+PLAN_MATCH_RULE_CALC_FREQUENCY_0+"&PLMR_CALC_VERIFY_LOV="+PLMR_CALC_VERIFY_LOV+
				"&PLMR_SDMT_CODE_LOV="+PLMR_SDMT_CODE_LOV+"&PLAN_MATCH_RULE_CRIT_MATCH_CALCULATED_ON_0="+PLAN_MATCH_RULE_CRIT_MATCH_CALCULATED_ON_0+"&PLMR_RATE_TIER_BASED_ON_LOV="+PLMR_RATE_TIER_BASED_ON_LOV+
				"&PLAN_MATCH_RULE_RATE_MATCH_PERCENT_0="+PLAN_MATCH_RULE_RATE_MATCH_PERCENT_0+"&PLMR_RATE_TIER_BASED_ON_LOV_X1="+PLMR_RATE_TIER_BASED_ON_LOV_X1+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for PLMU Record Employer Contribution Rule Setup service", this.queryString.replaceAll("&", "\n"), false);
		
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
			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run PLMU service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PLMU service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "GA_ID: "+ doc.getElementsByTagName("GROUP_HEADER1_N_GA_ID_0").item(0).getTextContent()+
					"\nGC NAME: "+ doc.getElementsByTagName("GROUP_HEADER1_N_GC_ID_0").item(0).getTextContent()+
					"\nPROD_ID: "+ doc.getElementsByTagName("GROUP_HEADER1_N_PROD_ID_0").item(0).getTextContent()+
					"\nPLAN_ID: "+ doc.getElementsByTagName("GROUP_HEADER1_PLAN_ID_0").item(0).getTextContent()+
					"\nEFFDATE: "+ doc.getElementsByTagName("PLAN_MATCH_RULE_EFFDATE_0").item(0).getTextContent()+
					"\nRULE_ID: "+ doc.getElementsByTagName("GROUP_HEADER1_PREV_RULE_ID_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String PLAN_ID = doc.getElementsByTagName("GROUP_HEADER1_PLAN_ID_0").item(0).getTextContent();
		String plan_id = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPlmu")[1],  this.GET_GA_GA_ID_0);
		
		if(queryResultSet.next()){
			plan_id = queryResultSet.getString("PLAN_ID");
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists in Database", false);
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "Table: PLAN_MATCH_RULE\nID: "+queryResultSet.getString("ID")+
					"\nPLAN_ID: "+queryResultSet.getString("PLAN_ID")+	
					"\nPROD_ID: "+queryResultSet.getString("PROD_ID")+	
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+			
					"\nSTATUS_CODE: "+queryResultSet.getString("STATUS_CODE"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(PLAN_ID.equalsIgnoreCase(plan_id)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Plan ID from Response and Database", "Both the values are same as Expected"+
					"\nPlan ID: "+"From Response: "+PLAN_ID+"\nFrom Database: "+plan_id, false);
		}else{
			Reporter.logEvent(Status.FAIL, "Comparing and Validating Plan ID from Response and Database", "Both the values are not same as Expected"+
					"\nPlan ID: "+"From Response: "+PLAN_ID+"\nFrom Database: "+plan_id, false);
		}
	}
}
