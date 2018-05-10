package pageobject.MAIR;

import generallib.DateCompare;
import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class MAIR_Insert_New_Record_Rate {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MAIR_Insert_New_Record_Rate";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String RATE_TYPE_CODE_0;
	String INTEREST_RATE_STOP_DATE_2;
	String INTEREST_RATE_VALUE_2;
	String INTEREST_RATE_STOP_DATE_2_X1;
	
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
	public void setRATE_TYPE_CODE_0(String rATE_TYPE_CODE_0) {
		RATE_TYPE_CODE_0 = rATE_TYPE_CODE_0;
	}
	public void setINTEREST_RATE_STOP_DATE_2(String iNTEREST_RATE_STOP_DATE_2) {
		INTEREST_RATE_STOP_DATE_2 = iNTEREST_RATE_STOP_DATE_2;
	}
	public void setINTEREST_RATE_VALUE_2(String iNTEREST_RATE_VALUE_2) {
		INTEREST_RATE_VALUE_2 = iNTEREST_RATE_VALUE_2;
	}
	public void setINTEREST_RATE_STOP_DATE_2_X1(String iNTEREST_RATE_STOP_DATE_2_X1) {
		INTEREST_RATE_STOP_DATE_2_X1 = iNTEREST_RATE_STOP_DATE_2_X1;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));/*
		this.setINTEREST_RATE_STOP_DATE_2(Stock.GetParameterValue("INTEREST_RATE_STOP_DATE_2"));
		this.setINTEREST_RATE_STOP_DATE_2_X1(Stock.GetParameterValue("INTEREST_RATE_STOP_DATE_2_X1"));*/
		this.setINTEREST_RATE_VALUE_2(Stock.GetParameterValue("INTEREST_RATE_VALUE_2"));
		this.setRATE_TYPE_CODE_0(Stock.GetParameterValue("RATE_TYPE_CODE_0"));
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String curDate = sdf.format(cal.getTime());
		System.out.println("Current Date: "+curDate.toUpperCase());
		
		this.setINTEREST_RATE_STOP_DATE_2(curDate);
		this.setINTEREST_RATE_STOP_DATE_2_X1(DateCompare.GenerateFutureDateYear());
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&RATE_TYPE_CODE_0="+RATE_TYPE_CODE_0+"&INTEREST_RATE_STOP_DATE_2="+INTEREST_RATE_STOP_DATE_2+"&INTEREST_RATE_VALUE_2="+INTEREST_RATE_VALUE_2+"&INTEREST_RATE_STOP_DATE_2_X1="+INTEREST_RATE_STOP_DATE_2_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for MAIR service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run MAIR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run MAIR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "INTEREST RATE START DATE: "+ doc.getElementsByTagName("INTEREST_RATE_START_DATE_2").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String start_date_db = null;
		String start_date = doc.getElementsByTagName("INTEREST_RATE_START_DATE_2").item(0).getTextContent();
		System.out.println("start date:"+start_date+"\n");
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForMAIRInsert")[1], start_date);
		if(queryResultSet != null && DB.getRecordSetCount(queryResultSet)>0)
		{
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database\nTable Name: STD_ECON_RATES", false);
		while(queryResultSet.next()){
			
			Reporter.logEvent(Status.INFO, "Values from Database", "START_DATE: "+queryResultSet.getString("START_DATE")+
					"\nVALUE: "+queryResultSet.getString("VALUE")+
					"\nSDIRTY_CODE: "+queryResultSet.getString("SDIRTY_CODE")+
					"\nSTOP_DATE: "+queryResultSet.getString("STOP_DATE"), false);
			
			start_date_db = queryResultSet.getString("ID");
			if(start_date.equalsIgnoreCase(start_date_db)){
				Reporter.logEvent(Status.PASS, "Comapring and Validating START DATE from Response and Database", "Both the values are same as expected"+
			"\nFrom Response: "+start_date+"\nFrom Database: "+start_date_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comapring and Validating START DATE from Response and Database", "Both the values are same as expected"+
						"\nFrom Response: "+start_date+"\nFrom Database: "+start_date_db, false);
			}
		}
			
		}else{
			
		}
		}
		
	
	public void FlushDataCreated(){

		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForMAIRInsertClearData")[1], this.INTEREST_RATE_STOP_DATE_2.toUpperCase());
	
/*		if(DB.getRecordSetCount(queryResultSet)==0){
			System.out.println("Data Cleared from Database");
		}
*/	}
	
}
