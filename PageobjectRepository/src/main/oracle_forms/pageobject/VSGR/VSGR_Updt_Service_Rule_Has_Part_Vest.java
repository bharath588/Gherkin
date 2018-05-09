package pageobject.VSGR;

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

public class VSGR_Updt_Service_Rule_Has_Part_Vest {

	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/VSGR_Updt_Service_Rule_Has_Part_Vest_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String GET_GA_GA_ID_0;
	String SERVICE_RULE_BREAK_IN_SRV_IND_0;
	String SERVICE_RULE_BREAK_IN_SRV_IND_0_X1;
	String SERVICE_RULE_BREAK_IN_SRV_HRS_0;
	String SERVICE_RULE_EARLY_RETIRE_AGE_0;
	String SERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0;
	String SERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0;
	String SERVICE_RULE_NORMAL_RETIRE_AGE_0;
	String SERVICE_RULE_NORMAL_RETIRE_YRS_OF_SRV_0;
	String SERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0;
	String SERVICE_RULE_FULL_VEST_EARLY_RETIRE_0;
	String SERVICE_RULE_FULL_VEST_DEATH_IND_0;
	String SERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0;
	String SERVICE_RULE_DISREGARD_SRV_PRIOR_PLAN_INCEP_0;
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
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	public void setSERVICE_RULE_BREAK_IN_SRV_IND_0(
			String sERVICE_RULE_BREAK_IN_SRV_IND_0) {
		SERVICE_RULE_BREAK_IN_SRV_IND_0 = sERVICE_RULE_BREAK_IN_SRV_IND_0;
	}
	public void setSERVICE_RULE_BREAK_IN_SRV_IND_0_X1(
			String sERVICE_RULE_BREAK_IN_SRV_IND_0_X1) {
		SERVICE_RULE_BREAK_IN_SRV_IND_0_X1 = sERVICE_RULE_BREAK_IN_SRV_IND_0_X1;
	}
	public void setSERVICE_RULE_BREAK_IN_SRV_HRS_0(
			String sERVICE_RULE_BREAK_IN_SRV_HRS_0) {
		SERVICE_RULE_BREAK_IN_SRV_HRS_0 = sERVICE_RULE_BREAK_IN_SRV_HRS_0;
	}
	public void setSERVICE_RULE_EARLY_RETIRE_AGE_0(
			String sERVICE_RULE_EARLY_RETIRE_AGE_0) {
		SERVICE_RULE_EARLY_RETIRE_AGE_0 = sERVICE_RULE_EARLY_RETIRE_AGE_0;
	}
	public void setSERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0(
			String sERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0) {
		SERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0 = sERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0;
	}
	public void setSERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0(
			String sERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0) {
		SERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0 = sERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0;
	}
	public void setSERVICE_RULE_NORMAL_RETIRE_AGE_0(
			String sERVICE_RULE_NORMAL_RETIRE_AGE_0) {
		SERVICE_RULE_NORMAL_RETIRE_AGE_0 = sERVICE_RULE_NORMAL_RETIRE_AGE_0;
	}
	public void setSERVICE_RULE_NORMAL_RETIRE_YRS_OF_SRV_0(
			String sERVICE_RULE_NORMAL_RETIRE_YRS_OF_SRV_0) {
		SERVICE_RULE_NORMAL_RETIRE_YRS_OF_SRV_0 = sERVICE_RULE_NORMAL_RETIRE_YRS_OF_SRV_0;
	}
	public void setSERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0(
			String sERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0) {
		SERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0 = sERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0;
	}
	public void setSERVICE_RULE_FULL_VEST_EARLY_RETIRE_0(
			String sERVICE_RULE_FULL_VEST_EARLY_RETIRE_0) {
		SERVICE_RULE_FULL_VEST_EARLY_RETIRE_0 = sERVICE_RULE_FULL_VEST_EARLY_RETIRE_0;
	}
	public void setSERVICE_RULE_FULL_VEST_DEATH_IND_0(
			String sERVICE_RULE_FULL_VEST_DEATH_IND_0) {
		SERVICE_RULE_FULL_VEST_DEATH_IND_0 = sERVICE_RULE_FULL_VEST_DEATH_IND_0;
	}
	public void setSERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0(
			String sERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0) {
		SERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0 = sERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0;
	}
	public void setSERVICE_RULE_DISREGARD_SRV_PRIOR_PLAN_INCEP_0(
			String sERVICE_RULE_DISREGARD_SRV_PRIOR_PLAN_INCEP_0) {
		SERVICE_RULE_DISREGARD_SRV_PRIOR_PLAN_INCEP_0 = sERVICE_RULE_DISREGARD_SRV_PRIOR_PLAN_INCEP_0;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setSERVICE_RULE_BREAK_IN_SRV_HRS_0(Stock.GetParameterValue("SERVICE_RULE_BREAK_IN_SRV_HRS_0"));
		this.setSERVICE_RULE_BREAK_IN_SRV_IND_0(Stock.GetParameterValue("SERVICE_RULE_BREAK_IN_SRV_IND_0"));
		this.setSERVICE_RULE_BREAK_IN_SRV_IND_0_X1(Stock.GetParameterValue("SERVICE_RULE_BREAK_IN_SRV_IND_0_X1"));
		this.setSERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0(Stock.GetParameterValue("SERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0"));
		this.setSERVICE_RULE_DISREGARD_SRV_PRIOR_PLAN_INCEP_0(Stock.GetParameterValue("SERVICE_RULE_DISREGARD_SRV_PRIOR_PLAN_INCEP_0"));
		this.setSERVICE_RULE_EARLY_RETIRE_AGE_0(Stock.GetParameterValue("SERVICE_RULE_EARLY_RETIRE_AGE_0"));
		this.setSERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0(Stock.GetParameterValue("SERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0"));
		this.setSERVICE_RULE_NORMAL_RETIRE_YRS_OF_SRV_0(Stock.GetParameterValue("SERVICE_RULE_NORMAL_RETIRE_YRS_OF_SRV_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&SERVICE_RULE_BREAK_IN_SRV_IND_0="+SERVICE_RULE_BREAK_IN_SRV_IND_0+"&SERVICE_RULE_BREAK_IN_SRV_IND_0_X1="+SERVICE_RULE_BREAK_IN_SRV_IND_0_X1+
				"&SERVICE_RULE_BREAK_IN_SRV_HRS_0="+SERVICE_RULE_BREAK_IN_SRV_HRS_0+"&SERVICE_RULE_EARLY_RETIRE_AGE_0="+SERVICE_RULE_EARLY_RETIRE_AGE_0+"&SERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0="+SERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0+
				"&SERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0="+SERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0+"&SERVICE_RULE_NORMAL_RETIRE_AGE_0="+SERVICE_RULE_NORMAL_RETIRE_AGE_0+
				"&SERVICE_RULE_NORMAL_RETIRE_YRS_OF_SRV_0="+SERVICE_RULE_NORMAL_RETIRE_YRS_OF_SRV_0+"&SERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0="+SERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0+
				"&SERVICE_RULE_FULL_VEST_EARLY_RETIRE_0="+SERVICE_RULE_FULL_VEST_EARLY_RETIRE_0+"&SERVICE_RULE_FULL_VEST_DEATH_IND_0="+SERVICE_RULE_FULL_VEST_DEATH_IND_0+
				"&SERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0="+SERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0+"&SERVICE_RULE_DISREGARD_SRV_PRIOR_PLAN_INCEP_0="+SERVICE_RULE_DISREGARD_SRV_PRIOR_PLAN_INCEP_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for VSGR Update Service Rule Has Part Vest service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run VSGR - VSGR Update Service Rule Has Part Vest service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run VSGR - VSGR Update Service Rule Has Part Vestr service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if(Error.isEmpty())
		{
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);	
			Reporter.logEvent(Status.INFO, "From Response ", "PLAN NAME: "+doc.getElementsByTagName("GROUP_HEADER_PLAN_NAME_0").item(0).getTextContent(),false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
	}
	
	public void validateInDatabase() throws SQLException{
		
		String planname = doc.getElementsByTagName("GROUP_HEADER_PLAN_NAME_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForVSGRUdtServiceRule1")[1], planname);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating data existence in Database", "Records present in Database\nTablename: SERVICE_RULE", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "From Database", "PLAN_ID: "+queryResultSet.getString("PLAN_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating data existence in Database", "Records doesn't exist in Database\nTablename: SERVICE_RULE", false);			
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForVSGRUdtServiceRule2")[1], this.GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)==0){
			Reporter.logEvent(Status.PASS, "Expected: Records should not be present in DB", "Records are not present in Database\nTablename: EVENT", false);
			
		}else{
			Reporter.logEvent(Status.PASS, "Expected: Records should not be present in DB", "Records present in Database\nTablename: EVENT", false);			
		}
	}
}
