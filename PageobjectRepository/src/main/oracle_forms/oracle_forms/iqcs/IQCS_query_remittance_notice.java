package pageobject.iqcs;

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

public class IQCS_query_remittance_notice {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/IQCS";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String REMIT_NOTICE_ID_0;
	
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
	
	String 	CONTROL_HEADER_0;
	String 	CONTROL_MENU_TITLE_0;
	String  TRANSACTION_CODE_0;
	String  TRANSACTION_DESCR_0;
	String TRANSACTION_MARKER_0;

	public String getCONTROL_HEADER_0(){
		return CONTROL_HEADER_0;
	}
	
	public String getCONTROL_MENU_TITLE_0(){
		return  CONTROL_MENU_TITLE_0;
	}
	
	public String getTRANSACTION_CODE_0(){
		return  TRANSACTION_CODE_0; 
	}
	
	public String getTRANSACTION_DESCR_0(){
		return  TRANSACTION_DESCR_0;
	}

	public String getTRANSACTION_MARKER_0(){
		return  TRANSACTION_MARKER_0;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setREMIT_NOTICE_ID_0(Stock.GetParameterValue("REMIT_NOTICE_ID_0"));
		
		queryString = "?CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 + "&LOGON_DATABASE=" + LOGON_DATABASE +
				"&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_USERNAME=" + LOGON_USERNAME +
				"&REMIT_NOTICE_ID_0=" + REMIT_NOTICE_ID_0 +
				"&numOfRowsInTable=20&json=false&handlePopups=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for IQCS service", this.queryString.replaceAll("&", "\n"), false);
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
		
			Reporter.logEvent(Status.PASS, "Run IQCS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run IQCS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From response", "Transaction Code: "+  doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent()+
					"\nTransaction Descr: "+ doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("remitNoticeQuery")[1], this.REMIT_NOTICE_ID_0);
		
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS,"Validations in database","Validations of event Id generated in database to check if any row is generated",false);
			String id = queryResultSet.getString("ID");
			String ev_id = queryResultSet.getString("EV_ID");
			String amt = queryResultSet.getString("AMOUNT");
			System.out.println("ID = " + id+ "\nEV_ID = " + ev_id + "\nAMOUNT= " + amt);
			Reporter.logEvent(Status.INFO,"Values From database\nTable Name: Remit Notice","EV_ID: "+ev_id+"\nAMOUNT: "+amt+
					"\nTOTAL OUT OF BAL: "+queryResultSet.getString("OUT_OF_BAL_AMT")+
					"\nSTATUS CODE: "+queryResultSet.getString("BALANCE_STATUS_CODE"),false);
			
		}	
	
	}		
	
	

}
