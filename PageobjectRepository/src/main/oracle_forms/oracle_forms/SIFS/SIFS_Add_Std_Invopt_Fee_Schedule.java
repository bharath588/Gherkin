package pageobject.SIFS;

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

public class SIFS_Add_Std_Invopt_Fee_Schedule {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SIFS_Add_New_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String STD_RULE_LOV;
/*	String STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0;
	String STD_INVOPT_FEE_SCHEDULE_TERMDATE_0;
	String STD_INVOPT_FEE_SCHEDULE_TERMDATE_0_X1;
	String STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0_X1;
	String STD_INVOPT_FEE_SCHEDULE_SDIO_ID_1;
	String STD_INVOPT_FEE_SCHEDULE_EFFDATE_1;
	String STD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_1;
	String STD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_1;
	String TXN_TYPE_LOV;
	String STD_REDEMPTION_FEE_RULE_EFFDATE_3;
	*/
	String Standard_Investment_Options;
	String STD_INVOPT_FEE_SCHEDULE_EFFDATE_9;
	String STD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_9;
	String STD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_9;
	
	public void setStandard_Investment_Options(String standard_Investment_Options) {
		Standard_Investment_Options = standard_Investment_Options;
	}
	public void setSTD_INVOPT_FEE_SCHEDULE_EFFDATE_9(
			String sTD_INVOPT_FEE_SCHEDULE_EFFDATE_9) {
		STD_INVOPT_FEE_SCHEDULE_EFFDATE_9 = sTD_INVOPT_FEE_SCHEDULE_EFFDATE_9;
	}
	public void setSTD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_9(
			String sTD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_9) {
		STD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_9 = sTD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_9;
	}
	public void setSTD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_9(
			String sTD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_9) {
		STD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_9 = sTD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_9;
	}
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
	public void setSTD_RULE_LOV(String sTD_RULE_LOV) {
		STD_RULE_LOV = sTD_RULE_LOV;
	}
/*	public void setSTD_INVOPT_FEE_SCHEDULE_SDIO_ID_0(
			String sTD_INVOPT_FEE_SCHEDULE_SDIO_ID_0) {
		STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0 = sTD_INVOPT_FEE_SCHEDULE_SDIO_ID_0;
	}
	public void setSTD_INVOPT_FEE_SCHEDULE_TERMDATE_0(
			String sTD_INVOPT_FEE_SCHEDULE_TERMDATE_0) {
		STD_INVOPT_FEE_SCHEDULE_TERMDATE_0 = sTD_INVOPT_FEE_SCHEDULE_TERMDATE_0;
	}
	public void setSTD_INVOPT_FEE_SCHEDULE_TERMDATE_0_X1(
			String sTD_INVOPT_FEE_SCHEDULE_TERMDATE_0_X1) {
		STD_INVOPT_FEE_SCHEDULE_TERMDATE_0_X1 = sTD_INVOPT_FEE_SCHEDULE_TERMDATE_0_X1;
	}
	public void setSTD_INVOPT_FEE_SCHEDULE_SDIO_ID_0_X1(
			String sTD_INVOPT_FEE_SCHEDULE_SDIO_ID_0_X1) {
		STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0_X1 = sTD_INVOPT_FEE_SCHEDULE_SDIO_ID_0_X1;
	}
	public void setSTD_INVOPT_FEE_SCHEDULE_SDIO_ID_1(
			String sTD_INVOPT_FEE_SCHEDULE_SDIO_ID_1) {
		STD_INVOPT_FEE_SCHEDULE_SDIO_ID_1 = sTD_INVOPT_FEE_SCHEDULE_SDIO_ID_1;
	}
	public void setSTD_INVOPT_FEE_SCHEDULE_EFFDATE_1(
			String sTD_INVOPT_FEE_SCHEDULE_EFFDATE_1) {
		STD_INVOPT_FEE_SCHEDULE_EFFDATE_1 = sTD_INVOPT_FEE_SCHEDULE_EFFDATE_1;
	}
	public void setSTD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_1(
			String sTD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_1) {
		STD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_1 = sTD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_1;
	}
	public void setSTD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_1(
			String sTD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_1) {
		STD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_1 = sTD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_1;
	}
	public void setTXN_TYPE_LOV(String tXN_TYPE_LOV) {
		TXN_TYPE_LOV = tXN_TYPE_LOV;
	}
	public void setSTD_REDEMPTION_FEE_RULE_EFFDATE_3(
			String sTD_REDEMPTION_FEE_RULE_EFFDATE_3) {
		STD_REDEMPTION_FEE_RULE_EFFDATE_3 = sTD_REDEMPTION_FEE_RULE_EFFDATE_3;
	}
	*/
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSTD_RULE_LOV(Stock.GetParameterValue("STD_RULE_LOV"));
		this.setStandard_Investment_Options(Stock.GetParameterValue("Standard_Investment_Options"));
		this.setSTD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_9(Stock.GetParameterValue("STD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_9"));
		this.setSTD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_9(Stock.GetParameterValue("STD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_9"));
		this.setSTD_INVOPT_FEE_SCHEDULE_EFFDATE_9(Stock.GetParameterValue("STD_INVOPT_FEE_SCHEDULE_EFFDATE_9"));
	/*	this.setSTD_INVOPT_FEE_SCHEDULE_EFFDATE_1(Stock.GetParameterValue("STD_INVOPT_FEE_SCHEDULE_EFFDATE_1"));
		this.setSTD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_1(Stock.GetParameterValue("STD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_1"));
		this.setSTD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_1(Stock.GetParameterValue("STD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_1"));
		this.setSTD_INVOPT_FEE_SCHEDULE_SDIO_ID_0(Stock.GetParameterValue("STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0"));
		this.setSTD_INVOPT_FEE_SCHEDULE_SDIO_ID_0_X1(Stock.GetParameterValue("STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0_X1"));
		this.setSTD_INVOPT_FEE_SCHEDULE_SDIO_ID_1(Stock.GetParameterValue("STD_INVOPT_FEE_SCHEDULE_SDIO_ID_1"));
		this.setSTD_INVOPT_FEE_SCHEDULE_TERMDATE_0(Stock.GetParameterValue("STD_INVOPT_FEE_SCHEDULE_TERMDATE_0"));
		this.setSTD_INVOPT_FEE_SCHEDULE_TERMDATE_0_X1(Stock.GetParameterValue("STD_INVOPT_FEE_SCHEDULE_TERMDATE_0_X1"));
	*/	
		/*queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&STD_RULE_LOV="+STD_RULE_LOV+"&STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0="+STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0+"&STD_INVOPT_FEE_SCHEDULE_TERMDATE_0="+STD_INVOPT_FEE_SCHEDULE_TERMDATE_0+
				"&STD_INVOPT_FEE_SCHEDULE_TERMDATE_0_X1="+STD_INVOPT_FEE_SCHEDULE_TERMDATE_0_X1+"&STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0_X1="+STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0_X1+
				"&STD_INVOPT_FEE_SCHEDULE_SDIO_ID_1="+STD_INVOPT_FEE_SCHEDULE_SDIO_ID_1+"&STD_INVOPT_FEE_SCHEDULE_EFFDATE_1="+STD_INVOPT_FEE_SCHEDULE_EFFDATE_1+
				"&STD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_1="+STD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_1+"&STD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_1="+STD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_1+
				"&TXN_TYPE_LOV="+TXN_TYPE_LOV+"&STD_REDEMPTION_FEE_RULE_EFFDATE_3="+STD_REDEMPTION_FEE_RULE_EFFDATE_3+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		*/
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&STD_RULE_LOV="+STD_RULE_LOV+"&Standard_Investment_Options="+Standard_Investment_Options+"&STD_INVOPT_FEE_SCHEDULE_EFFDATE_9="+STD_INVOPT_FEE_SCHEDULE_EFFDATE_9+
				"&STD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_9="+STD_INVOPT_FEE_SCHEDULE_FEE_PERCENT_9+"&STD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_9="+STD_INVOPT_FEE_SCHEDULE_MINIMUM_HOLDING_DAYS_9+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
				Reporter.logEvent(Status.INFO, "Prepare test data for SIFS service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run SIFS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SIFS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response", "LEGAL NAME: "+doc.getElementsByTagName("STD_INVOPT_FEE_SCHEDULE_LEGAL_NAME_0").item(0).getTextContent()+
					"\nTYPE_CODE: "+doc.getElementsByTagName("STD_INVOPT_FEE_SCHEDULE_TYPE_CODE_0").item(0).getTextContent()+
					"\nRULE_ID: "+doc.getElementsByTagName("STD_RULE_ID_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String sdio_id = this.Standard_Investment_Options;
		String rule_id = doc.getElementsByTagName("STD_RULE_ID_0").item(0).getTextContent();
//		String txn_type = doc.getElementsByTagName("STD_REDEMPTION_FEE_RULE_TXN_TYPE_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSIFS1")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: STD_RULE", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nDESCR: "+queryResultSet.getString("DESCR")+
						"\nRULE_TYPE: "+queryResultSet.getString("RULE_TYPE"), false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSIFS2")[1],  sdio_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: STD_INVOPT_FEE_SCHEDULE", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "SDIO_ID: "+queryResultSet.getString("SDIO_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nTERMDATE: "+queryResultSet.getString("TERMDATE"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSIFS3")[1],  rule_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: STD_REDEMPTION_FEE_RULE", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "STD_RULE_ID: "+queryResultSet.getString("STD_RL_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nTERMDATE: "+queryResultSet.getString("TERMDATE")+
						"\nTXN_TYPE: "+queryResultSet.getString("TXN_TYPE"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
	}
	
	public void FlushData(){
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSIFS1")[1]);
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSIFS2")[1],  this.Standard_Investment_Options);
	//	queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSIFS3")[1],  rule_id);
	}
}
