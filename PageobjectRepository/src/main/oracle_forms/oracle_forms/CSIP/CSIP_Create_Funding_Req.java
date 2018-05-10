package pageobject.CSIP;

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

public class CSIP_Create_Funding_Req {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CSIP_CREATE_FUNDING_REQUEST";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String REMIT_NOTICE_ID_0;
	String CONTROL_TOTALS_AMOUNT_0;
	String CONTROL_TOTALS_AMOUNT_1;
	String CONTROL_TOTALS_AMOUNT_2;
	String CONTROL_TOTALS_AMOUNT_3;
	String CFG_CTRL_WIRE_REQUEST_WIRE_REQUEST_YES_0;
	
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
	public void setREMIT_NOTICE_ID_0(String rEMIT_NOTICE_ID_0) {
		REMIT_NOTICE_ID_0 = rEMIT_NOTICE_ID_0;
	}
	public void setCONTROL_TOTALS_AMOUNT_0(String cONTROL_TOTALS_AMOUNT_0) {
		CONTROL_TOTALS_AMOUNT_0 = cONTROL_TOTALS_AMOUNT_0;
	}
	public void setCONTROL_TOTALS_AMOUNT_1(String cONTROL_TOTALS_AMOUNT_1) {
		CONTROL_TOTALS_AMOUNT_1 = cONTROL_TOTALS_AMOUNT_1;
	}
	public void setCONTROL_TOTALS_AMOUNT_2(String cONTROL_TOTALS_AMOUNT_2) {
		CONTROL_TOTALS_AMOUNT_2 = cONTROL_TOTALS_AMOUNT_2;
	}
	public void setCONTROL_TOTALS_AMOUNT_3(String cONTROL_TOTALS_AMOUNT_3) {
		CONTROL_TOTALS_AMOUNT_3 = cONTROL_TOTALS_AMOUNT_3;
	}
	public void setCFG_CTRL_WIRE_REQUEST_WIRE_REQUEST_YES_0(
			String cFG_CTRL_WIRE_REQUEST_WIRE_REQUEST_YES_0) {
		CFG_CTRL_WIRE_REQUEST_WIRE_REQUEST_YES_0 = cFG_CTRL_WIRE_REQUEST_WIRE_REQUEST_YES_0;
	}
	
	public void setServiceParameters(String remit_id)	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setREMIT_NOTICE_ID_0(remit_id);
		this.setCFG_CTRL_WIRE_REQUEST_WIRE_REQUEST_YES_0(Stock.GetParameterValue("CFG_CTRL_WIRE_REQUEST_WIRE_REQUEST_YES_0"));
		this.setCONTROL_TOTALS_AMOUNT_0(Stock.GetParameterValue("CONTROL_TOTALS_AMOUNT_0"));
		this.setCONTROL_TOTALS_AMOUNT_1(Stock.GetParameterValue("CONTROL_TOTALS_AMOUNT_1"));
		this.setCONTROL_TOTALS_AMOUNT_2(Stock.GetParameterValue("CONTROL_TOTALS_AMOUNT_2"));
		this.setCONTROL_TOTALS_AMOUNT_3(Stock.GetParameterValue("CONTROL_TOTALS_AMOUNT_3"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&REMIT_NOTICE_ID_0="+REMIT_NOTICE_ID_0+"&CONTROL_TOTALS_AMOUNT_0="+CONTROL_TOTALS_AMOUNT_0+"&CONTROL_TOTALS_AMOUNT_1="+CONTROL_TOTALS_AMOUNT_1+
				"&CONTROL_TOTALS_AMOUNT_2="+CONTROL_TOTALS_AMOUNT_2+"&CONTROL_TOTALS_AMOUNT_3="+CONTROL_TOTALS_AMOUNT_3+"&CFG_CTRL_WIRE_REQUEST_WIRE_REQUEST_YES_0="+CFG_CTRL_WIRE_REQUEST_WIRE_REQUEST_YES_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for CSIP service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run CSIP service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CSIP service", "Running Failed:", false);
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
		String rmnc_id = this.REMIT_NOTICE_ID_0;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSIPCreateFundingReq")[1], rmnc_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database\nTable Name: REMIT_NOTICE", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "From Database ", "RMNC_ID: "+queryResultSet.getString("ID")+
						"\nEV_ID: "+queryResultSet.getString("EV_ID")+
						"\nBAL_STATUS_CODE: "+queryResultSet.getString("BALANCE_STATUS_CODE")+
						"\nAMOUNT: "+queryResultSet.getString("AMOUNT"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "Record doesn't exists in the Database", false);
		}
	}
}
