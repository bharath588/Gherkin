package pageobject.SGHI;

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

public class SGHI_Stmt_Group_Hold {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SGHI_STMT_GROUP_HOLD";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SELECTION_PROV_CO_0;
	
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
	public void setSELECTION_PROV_CO_0(String sELECTION_PROV_CO_0) {
		SELECTION_PROV_CO_0 = sELECTION_PROV_CO_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSELECTION_PROV_CO_0(Stock.GetParameterValue("SELECTION_PROV_CO_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SELECTION_PROV_CO_0="+SELECTION_PROV_CO_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for SGHI service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run SGHI service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SGHI service", "Running Failed:", false);
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
	
	public void validateInDatabase() throws SQLException{
		String ga_id = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSGHIgaId")[1], this.SELECTION_PROV_CO_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			while(queryResultSet.next()){
				ga_id = queryResultSet.getString("GA_ID");
			}
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSGHI1")[1], ga_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking whether records exists in DB", "Records exists in DB\nTablename: GROUP_ACCOUNT\nPRODUCT", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "ID: "+queryResultSet.getString("ID")+
						"\nPROV_COMPANY: "+queryResultSet.getString("PROV_COMPANY"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Checking whether records exists in DB", "Records doesn't exists in DB\nTablename: GROUP_ACCOUNT\nPRODUCT", false);
			
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSGHI2")[1], ga_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking whether records exists in DB", "Records exists in DB\nTablename: STMT_GROUP_HOLD", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "GA_ID: "+queryResultSet.getString("GA_ID")+
						"\nAREA_REASON: "+queryResultSet.getString("AREA_REASON")+
						"\nSYSTEM_DATE: "+queryResultSet.getString("SYSTEM_DATE")+
						"\nHOLD_REMOVAL_DATE: "+queryResultSet.getString("HOLD_REMOVAL_DATE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Checking whether records exists in DB", "Records doesn't exists in DB\nTablename: STMT_GROUP_HOLD", false);
			
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSGHI3")[1], ga_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking whether records exists in DB", "Records exists in DB\nTablename: LIFE_COUNT"+
		"\nTotal no of records: "+DB.getRecordSetCount(queryResultSet), false);
			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking whether records exists in DB", "Records doesn't exists in DB\nTablename: LIFE_COUNT", false);
			
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSGHI4")[1], ga_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking whether records exists in DB", "Records exists in DB\nTablename: AG_INCL", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "AG_ID: "+queryResultSet.getString("AG_ID")+
						"\nGA_ID: "+queryResultSet.getString("GA_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nDESIGNATED_GA_IND: "+queryResultSet.getString("DESIGNATED_GA_IND"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Checking whether records exists in DB", "Records doesn't exists in DB\nTablename: AG_INCL", false);
			
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSGHI5")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking whether records exists in DB", "Records exists in DB\nTablename: ACCT_GRPG", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "ID: "+queryResultSet.getString("ID")+
						"\nTYPE_CODE: "+queryResultSet.getString("TYPE_CODE")+
						"\nSTATUS_CODE: "+queryResultSet.getString("STATUS_CODE")+
						"\nSTATUS_EFFDATE: "+queryResultSet.getString("STATUS_EFFDATE")+
						"\nSTMT_PROC_CODE: "+queryResultSet.getString("STMT_PROC_CODE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Checking whether records exists in DB", "Records doesn't exists in DB\nTablename: ACCT_GRPG", false);
			
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSGHI6")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking whether records exists in DB", "Records exists in DB\nTablename: ISIS_REF_VALUES"+
		"\nTotal no of records: "+DB.getRecordSetCount(queryResultSet), false);
			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking whether records exists in DB", "Records doesn't exists in DB\nTablename: ISIS_REF_VALUES", false);
			
		}
	}
}
