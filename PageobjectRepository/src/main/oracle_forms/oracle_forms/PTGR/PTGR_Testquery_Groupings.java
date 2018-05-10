package pageobject.PTGR;

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

public class PTGR_Testquery_Groupings {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PTGR_Test_Query_Groupings_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	ResultSet queryResultSet;
	
	
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String GET_GA_GA_ID_0;
	String PART_GRPG_PURPOSE_LOV;
	
	
	public String getLOGON_DATABASE() {
		return LOGON_DATABASE;
	}
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public String getLOGON_PASSWORD() {
		return LOGON_PASSWORD;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public String getLOGON_USERNAME() {
		return LOGON_USERNAME;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public String getCONTROL_SELECTION_0() {
		return CONTROL_SELECTION_0;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public String getGET_GA_GA_ID_0() {
		return GET_GA_GA_ID_0;
	}
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	public String getPART_GRPG_PURPOSE_LOV() {
		return PART_GRPG_PURPOSE_LOV;
	}
	public void setPART_GRPG_PURPOSE_LOV(String pART_GRPG_PURPOSE_LOV) {
		PART_GRPG_PURPOSE_LOV = pART_GRPG_PURPOSE_LOV;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.CONTROL_SELECTION_0 = Stock.GetParameterValue("CONTROL_SELECTION_0");
		this.GET_GA_GA_ID_0 = Stock.GetParameterValue("GET_GA_GA_ID_0");
		this.PART_GRPG_PURPOSE_LOV = Stock.GetParameterValue("PART_GRPG_PURPOSE_LOV");
		
		queryString = "?CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 + "&GET_GA_GA_ID_0=" + GET_GA_GA_ID_0 + "&LOGON_DATABASE=" + LOGON_DATABASE + "&LOGON_PASSWORD=" + LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME
				+ "&PART_GRPG_PURPOSE_LOV=" + PART_GRPG_PURPOSE_LOV+"&numOfRowsInTable=20&json=false&handlePopups=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for PTGR service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run PTGR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PTGR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response", "GROUP_HEADER_GA_ID_0: "+doc.getElementsByTagName("GROUP_HEADER_GA_ID_0").item(0).getTextContent()+
					"\nGROUP_HEADER_PLAN_NAME_0: "+doc.getElementsByTagName("GROUP_HEADER_PLAN_NAME_0").item(0).getTextContent()+
					"\nGROUP_HEADER_PROD_ID_0: "+doc.getElementsByTagName("GROUP_HEADER_PROD_ID_0").item(0).getTextContent()+
					"\nPART_GRPG_PLAN_DESCRIPTION_0: "+doc.getElementsByTagName("PART_GRPG_PLAN_DESCRIPTION_0").item(0).getTextContent()+
					"\nPART_GRPG_PLAN_EFFDATE_0: "+doc.getElementsByTagName("PART_GRPG_PLAN_EFFDATE_0").item(0).getTextContent()+
					"\nPART_GRPG_PLAN_NAME_0: "+doc.getElementsByTagName("PART_GRPG_PLAN_NAME_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String effdate = doc.getElementsByTagName("PART_GRPG_PLAN_EFFDATE_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPTGRQueryGroupings1")[1], this.GET_GA_GA_ID_0, effdate);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: part_grpg_plan", false);
			while(queryResultSet.next())
			Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
					"\nNAME: "+queryResultSet.getString("NAME")+
					"\nDESCR: "+queryResultSet.getString("DESCRIPTION")+
					"\nSEQNBR: "+queryResultSet.getString("SEQUENCE_NUMBER"), false);
						
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPTGRQueryGroupings2")[1], this.GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: part_grpg_membership", false);
			while(queryResultSet.next())
			Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
					"\nPART_GRPG_SOURCE_CODE: "+queryResultSet.getString("PART_GRPG_SOURCE_CODE")+
					"\nSTATUS: "+queryResultSet.getString("STATUS_CODE")+
					"\nDESCR: "+queryResultSet.getString("DESCRIPTION"), false);
						
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPTGRQueryGroupings3")[1], this.GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: part_grpg_membership_rule", false);
			while(queryResultSet.next())
			Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("PART_GRPG_PLAN_ID")+
					"\nPART_GRPG_MEMBERSHIP_ID: "+queryResultSet.getString("PART_GRPG_MEMBERSHIP_ID")+
					"\nDESCR: "+queryResultSet.getString("DESCRIPTION"), false);
						
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
	}
	
}
