package pageobject.SBPS;

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

public class SBPS_Batch_Print_Status {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SBPS_BATCH_PRINT_STATUS";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SELECTION_GA_ID_0;
	
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
	public void setSELECTION_GA_ID_0(String sELECTION_GA_ID_0) {
		SELECTION_GA_ID_0 = sELECTION_GA_ID_0;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));		
		this.setSELECTION_GA_ID_0(Stock.GetParameterValue("SELECTION_GA_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SELECTION_GA_ID_0="+SELECTION_GA_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for SBPS service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run SBPS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SBPS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "STMT_BATCH_AG_ID: "+doc.getElementsByTagName("STMT_BATCH_AG_ID_0").item(0).getTextContent()+
					"\nSTMT_BATCH_DMTY_CODE: "+doc.getElementsByTagName("STMT_BATCH_DMTY_CODE_0").item(0).getTextContent()+
					"\nSTMT_BATCH_GA_ID: "+doc.getElementsByTagName("STMT_BATCH_GA_ID_0").item(0).getTextContent()+
					"\nSTMT_BATCH_GA_ID_0: "+doc.getElementsByTagName("STMT_BATCH_GA_ID_0").item(0).getTextContent(), false);			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String ag_id = doc.getElementsByTagName("STMT_BATCH_AG_ID_0").item(0).getTextContent();		
		String ga_id =  null;
		String prod_id = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSBPS1")[1], ag_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: AG_INCL",false);
			while(queryResultSet.next()){
				ga_id = queryResultSet.getString("GA_ID");
				Reporter.logEvent(Status.INFO, "Values from Database", "AG_ID: "+queryResultSet.getString("AG_ID")+
						"\nGA_ID: "+queryResultSet.getString("GA_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE"), false);	
			}			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSBPS2")[1], ga_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: GROUP_ACCOUNT",false);
			while(queryResultSet.next()){
				prod_id = queryResultSet.getString("PROD_ID");
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nSTATUS_CODE: "+queryResultSet.getString("STATUS_CODE")+
						"\nGC_ID: "+queryResultSet.getString("GC_ID")+
						"\nPLAN_ID: "+queryResultSet.getString("PLAN_ID")+
						"\nPROD_ID: "+queryResultSet.getString("PROD_ID")+
						"\nPLAN_TYPE: "+queryResultSet.getString("PLAN_TYPE")+
						"\nWKUN_SHORT_NAME: "+queryResultSet.getString("WKUN_SHORT_NAME"), false);	
			}			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSBPS3")[1], prod_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: PRODUCT",false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nIRSRL_CODE: "+queryResultSet.getString("IRSRL_CODE")+
						"\nNAME: "+queryResultSet.getString("NAME")+
						"\nPROV_COMPANY: "+queryResultSet.getString("PROV_COMPANY"), false);	
			}			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSBPS4")[1], ag_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: STMT_TO_DO",false);
			Reporter.logEvent(Status.PASS, "Expected: Morethan 1 record should exists", "Total number of records: "+DB.getRecordSetCount(queryResultSet), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSBPS5")[1], ag_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: STMT_HANDLING",false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "AG_ID: "+queryResultSet.getString("AG_ID")+
					"\nSTMT_HANDLING_CODE: "+queryResultSet.getString("STMT_HANDLING_CODE"), false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSBPS6")[1], ag_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: STMT_ACTY_PERD",false);
			Reporter.logEvent(Status.PASS, "Expected: Morethan 1 record should exists", "Total number of records: "+DB.getRecordSetCount(queryResultSet), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}
	}
	
	
}
