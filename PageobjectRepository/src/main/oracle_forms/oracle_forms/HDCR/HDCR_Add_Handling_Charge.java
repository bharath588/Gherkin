package pageobject.HDCR;

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

public class HDCR_Add_Handling_Charge {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/HDCR_Add_Handling_Charge";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String HLCR1_AMOUNT_4;
	String HLCR1_START_DATE_4;
	
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
	public void setHLCR1_AMOUNT_4(String hLCR1_AMOUNT_4) {
		HLCR1_AMOUNT_4 = hLCR1_AMOUNT_4;
	}
	public void setHLCR1_START_DATE_4(String hLCR1_START_DATE_4) {
		HLCR1_START_DATE_4 = hLCR1_START_DATE_4;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setHLCR1_AMOUNT_4(Stock.GetParameterValue("HLCR1_AMOUNT_4"));
		this.setHLCR1_START_DATE_4(Stock.GetParameterValue("HLCR1_START_DATE_4"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&HLCR1_AMOUNT_4="+HLCR1_AMOUNT_4+"&HLCR1_START_DATE_4="+HLCR1_START_DATE_4+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for HDCR service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run HDCR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run HDCR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "HLCR CODE_0: "+ doc.getElementsByTagName("HLCR1_CODE_0").item(0).getTextContent()+
					"\nHLCR CODE_4: "+doc.getElementsByTagName("HLCR1_CODE_4").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		
		String start_date = this.HLCR1_START_DATE_4;
		String amount = this.HLCR1_AMOUNT_4;
		String stop_date = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForHDCRAddCharge")[1], start_date, amount);
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
			Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: HANDLING_CHARGES", "ID: "+queryResultSet.getString("ID")+
					"\nCODE: "+queryResultSet.getString("CODE")+
					"\nSTART_DATE: "+queryResultSet.getString("START_DATE")+
					"\nDESCR: "+queryResultSet.getString("DESCR")+
					"\nAMOUNT: "+queryResultSet.getString("AMOUNT")+
					"\nARS_NAME: "+queryResultSet.getString("ARS_NAME")+
					"\nTYPE_CODE: "+queryResultSet.getString("DOL_FEE_TYPE_CODE"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
	
	public void FlushDataCreated(){
		
		String start_date = this.HLCR1_START_DATE_4;
		String amount = this.HLCR1_AMOUNT_4;
		String stop_date = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForHDCRAddChargeClearData")[1], start_date, amount);
	
		if(DB.getRecordSetCount(queryResultSet)==0){
			System.out.println("Data Cleared from Database");
		}
	}
}
