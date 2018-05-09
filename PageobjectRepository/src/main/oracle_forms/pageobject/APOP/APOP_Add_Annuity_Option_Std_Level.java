package pageobject.APOP;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class APOP_Add_Annuity_Option_Std_Level {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/APOP_Add_Annuity_Option_Standard_Level";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;	
	ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String ANNUITY_PAYOUT_OPTION_TYPE_CODE_1;
	String ANNUITY_PAYOUT_OPTION_PAY_FREQ_CODE_1;
	String ANNUITY_PAYOUT_OPTION_DESCRIPTION_1;
	String ANNUITY_PAYOUT_OPTION_TIMING_CODE_1;
	String ANNUITY_PAYOUT_OPTION_DISPLAY_ORDER_1;
	String ANNUITY_PAYOUT_OPTION_EFFDATE_1;
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
	public void setANNUITY_PAYOUT_OPTION_TYPE_CODE_1(
			String aNNUITY_PAYOUT_OPTION_TYPE_CODE_1) {
		ANNUITY_PAYOUT_OPTION_TYPE_CODE_1 = aNNUITY_PAYOUT_OPTION_TYPE_CODE_1;
	}
	public void setANNUITY_PAYOUT_OPTION_PAY_FREQ_CODE_1(
			String aNNUITY_PAYOUT_OPTION_PAY_FREQ_CODE_1) {
		ANNUITY_PAYOUT_OPTION_PAY_FREQ_CODE_1 = aNNUITY_PAYOUT_OPTION_PAY_FREQ_CODE_1;
	}
	public void setANNUITY_PAYOUT_OPTION_DESCRIPTION_1(
			String aNNUITY_PAYOUT_OPTION_DESCRIPTION_1) {
		ANNUITY_PAYOUT_OPTION_DESCRIPTION_1 = aNNUITY_PAYOUT_OPTION_DESCRIPTION_1;
	}
	public void setANNUITY_PAYOUT_OPTION_TIMING_CODE_1(
			String aNNUITY_PAYOUT_OPTION_TIMING_CODE_1) {
		ANNUITY_PAYOUT_OPTION_TIMING_CODE_1 = aNNUITY_PAYOUT_OPTION_TIMING_CODE_1;
	}
	public void setANNUITY_PAYOUT_OPTION_DISPLAY_ORDER_1(
			String aNNUITY_PAYOUT_OPTION_DISPLAY_ORDER_1) {
		ANNUITY_PAYOUT_OPTION_DISPLAY_ORDER_1 = aNNUITY_PAYOUT_OPTION_DISPLAY_ORDER_1;
	}
	public void setANNUITY_PAYOUT_OPTION_EFFDATE_1(
			String aNNUITY_PAYOUT_OPTION_EFFDATE_1) {
		ANNUITY_PAYOUT_OPTION_EFFDATE_1 = aNNUITY_PAYOUT_OPTION_EFFDATE_1;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setANNUITY_PAYOUT_OPTION_DESCRIPTION_1(Stock.GetParameterValue("ANNUITY_PAYOUT_OPTION_DESCRIPTION_1"));
		this.setANNUITY_PAYOUT_OPTION_DISPLAY_ORDER_1(Stock.GetParameterValue("ANNUITY_PAYOUT_OPTION_DISPLAY_ORDER_1"));
//		this.setANNUITY_PAYOUT_OPTION_EFFDATE_1(Stock.GetParameterValue("ANNUITY_PAYOUT_OPTION_EFFDATE_1"));
		this.setANNUITY_PAYOUT_OPTION_PAY_FREQ_CODE_1(Stock.GetParameterValue("ANNUITY_PAYOUT_OPTION_PAY_FREQ_CODE_1"));
		this.setANNUITY_PAYOUT_OPTION_TIMING_CODE_1(Stock.GetParameterValue("ANNUITY_PAYOUT_OPTION_TIMING_CODE_1"));
		this.setANNUITY_PAYOUT_OPTION_TYPE_CODE_1(Stock.GetParameterValue("ANNUITY_PAYOUT_OPTION_TYPE_CODE_1"));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		String currentDate=dateFormat.format(date);
		
		System.out.println("Current Date: "+currentDate);
		
		this.setANNUITY_PAYOUT_OPTION_EFFDATE_1(currentDate);
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&ANNUITY_PAYOUT_OPTION_TYPE_CODE_1="+ANNUITY_PAYOUT_OPTION_TYPE_CODE_1+"&ANNUITY_PAYOUT_OPTION_PAY_FREQ_CODE_1="+ANNUITY_PAYOUT_OPTION_PAY_FREQ_CODE_1+
				"&ANNUITY_PAYOUT_OPTION_DESCRIPTION_1="+ANNUITY_PAYOUT_OPTION_DESCRIPTION_1+"&ANNUITY_PAYOUT_OPTION_TIMING_CODE_1="+ANNUITY_PAYOUT_OPTION_TIMING_CODE_1+
				"&ANNUITY_PAYOUT_OPTION_DISPLAY_ORDER_1="+ANNUITY_PAYOUT_OPTION_DISPLAY_ORDER_1+"&ANNUITY_PAYOUT_OPTION_EFFDATE_1="+ANNUITY_PAYOUT_OPTION_EFFDATE_1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for APOP service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run APOP service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run APOP service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		NodeList list = doc.getElementsByTagName("APOP_Add_Annuity_Option_Standard_LevelArrayItem");
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);		
			Reporter.logEvent(Status.INFO, "Values from response", "ANNUITY_PAYOUT_OPTION_TYPE_CODE_0: " +
			doc.getElementsByTagName("ANNUITY_PAYOUT_OPTION_TYPE_CODE_0").item(0).getTextContent()+
			"\nTotal no of items: "+ list.getLength(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase() throws SQLException{
		String type_code = this.ANNUITY_PAYOUT_OPTION_TYPE_CODE_1;
		String type_code_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAPOPAdd")[1], this.ANNUITY_PAYOUT_OPTION_EFFDATE_1);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record in DB", "Records inserted in DB\nTableName: ANNUITY_PAYOUT_OPTION", false);
			while(queryResultSet.next()){
				type_code_db = queryResultSet.getString("TYPE_CODE");
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nTYPE_CODE: "+queryResultSet.getString("TYPE_CODE")+
						"\nDESCR: "+queryResultSet.getString("DESCR"), false);
			}
			if(type_code.equalsIgnoreCase(type_code_db)){
				Reporter.logEvent(Status.PASS, "Comparing TYPE CODE field from Input and Database", "Both the value are same as expected"+
			"\nFrom Input: "+type_code+"\nFrom DB: "+type_code_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing TYPE CODE field from Input and Database", "Both the value are not same "+
						"\nFrom Input: "+type_code+"\nFrom DB: "+type_code_db, false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record in DB", "Records not inserted DB\nTableName: MODULE", false);
		}
		
	}
	
	
	public void resetData() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAPOPReset")[1], this.ANNUITY_PAYOUT_OPTION_EFFDATE_1);
	}
}
