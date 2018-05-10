package pageobject.PCWK;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.jsoup.select.Evaluator.IsEmpty;
import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class PCWK_Prov_CO_Work_Unit_Assign {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PCWK_PROVIDING_CO_WORK_UNIT_ASSIGN";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String WK_UNIT_SHORT_NAME_0;
	String CRITERIA_GROUP_OFFICE_0;
	String CRITERIA_PRODUCER_0;
	String CRITERIA_PRODUCER_LOV2;
	
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
	public void setWK_UNIT_SHORT_NAME_0(String wK_UNIT_SHORT_NAME_0) {
		WK_UNIT_SHORT_NAME_0 = wK_UNIT_SHORT_NAME_0;
	}
	public void setCRITERIA_GROUP_OFFICE_0(String cRITERIA_GROUP_OFFICE_0) {
		CRITERIA_GROUP_OFFICE_0 = cRITERIA_GROUP_OFFICE_0;
	}
	public void setCRITERIA_PRODUCER_0(String cRITERIA_PRODUCER_0) {
		CRITERIA_PRODUCER_0 = cRITERIA_PRODUCER_0;
	}
	public void setCRITERIA_PRODUCER_LOV2(String cRITERIA_PRODUCER_LOV2) {
		CRITERIA_PRODUCER_LOV2 = cRITERIA_PRODUCER_LOV2;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCRITERIA_GROUP_OFFICE_0(Stock.GetParameterValue("CRITERIA_GROUP_OFFICE_0"));
		this.setCRITERIA_PRODUCER_0(Stock.GetParameterValue("CRITERIA_PRODUCER_0"));
		this.setCRITERIA_PRODUCER_LOV2(Stock.GetParameterValue("CRITERIA_PRODUCER_LOV2"));
		this.setWK_UNIT_SHORT_NAME_0(Stock.GetParameterValue("WK_UNIT_SHORT_NAME_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&WK_UNIT_SHORT_NAME_0="+WK_UNIT_SHORT_NAME_0+"&CRITERIA_GROUP_OFFICE_0="+CRITERIA_GROUP_OFFICE_0+"&CRITERIA_PRODUCER_0="+CRITERIA_PRODUCER_0+"&CRITERIA_PRODUCER_LOV2="+CRITERIA_PRODUCER_LOV2+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for PCWK service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run PCWK service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PCWK service", "Running Failed:", false);
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
		String effdate = null;
		String termdate = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPCWK")[1], this.WK_UNIT_SHORT_NAME_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database\nTable Name", false);
			
			while(queryResultSet.next()){
				effdate = queryResultSet.getString("EFFDATE");
				termdate = queryResultSet.getString("TERMDATE");
				
				Reporter.logEvent(Status.INFO, "Values from Database: ", "PROV_COMPANY: "+queryResultSet.getString("PROV_COMPANY")+
						"\nWKUN_SHORT_NAME: "+queryResultSet.getString("WKUN_SHORT_NAME")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nTERMDATE: "+queryResultSet.getString("TERMDATE"), false);
			}
			if(!effdate.isEmpty()){
				Reporter.logEvent(Status.PASS, "Expected: Effdate or Termdate exists", "Effdate and Termdate columns Exists in DB", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Expected: Effdate or Termdate exists", "Effdate and Termdate columns doesn't Exists in DB", false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
				
	}
}
