package pageobject.REVC;

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

public class REVC_ReverseCash_SSN {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/REVC_Reverse_Ssn";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet1;
	private ResultSet queryResultSet2;
	private ResultSet queryResultSet3;
	
	
	String AG_REMIT_SELECTION_1;
	String CONTROL_SELECTION_0;
	String CONTROL_SSN_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME; 
		
	String  IND_REMIT_SELECTION_0;
	
	public String getIND_REMIT_SELECTION_0() {
		return IND_REMIT_SELECTION_0;
	}


	public void setAG_REMIT_SELECTION_1(String aG_REMIT_SELECTION_1) {
		AG_REMIT_SELECTION_1 = aG_REMIT_SELECTION_1;
	}

	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}

	public void setCONTROL_SSN_0(String cONTROL_SSN_0) {
		CONTROL_SSN_0 = cONTROL_SSN_0;
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
			Reporter.logEvent(Status.PASS, "Run REVC Reverse Cash SSN Account Maintainance service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run REVC Reverse Cash SSN", "Running Failed:", false);
		}
		
	}
	
	public void setServiceParameters()
	{
		this.setAG_REMIT_SELECTION_1(Stock.GetParameterValue("AG_REMIT_SELECTION_1"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SSN_0(Stock.GetParameterValue("CONTROL_SSN_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SSN_0="+CONTROL_SSN_0+"&AG_REMIT_SELECTION_1="+AG_REMIT_SELECTION_1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Preparing Data For TestCase", "CONTROL_SSN_0: "+CONTROL_SSN_0+
				"\nAG_REMIT_SELECTION_1: "+AG_REMIT_SELECTION_1, false);
	
	}
	
	public void validateOutput(){
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} 
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet1 = DB.executeQuery(Stock.getTestQuery("queryTogetStatusCodeRevc1SSN")[0], Stock.getTestQuery("queryTogetStatusCodeRevc1SSN")[1]);
		while(queryResultSet1.next()){
		String ps_code1 = queryResultSet1.getString("PROCESS_STATUS_CODE");
		System.out.println(ps_code1);
		Reporter.logEvent(Status.PASS, "Fetching Process Status Code from ag_remit", "PROCESS STATUS CODE: "+ps_code1, false);
		}
		
		queryResultSet2 = DB.executeQuery(Stock.getTestQuery("queryTogetStatusCodeRevc2SSN")[0], Stock.getTestQuery("queryTogetStatusCodeRevc2SSN")[1]);
		while(queryResultSet2.next()){
		String ps_code2 = queryResultSet2.getString("PROCESS_STATUS_CODE");
		System.out.println(ps_code2);
		Reporter.logEvent(Status.PASS, "Fetching Process Status Code from ind_remit", "PROCESS STATUS CODE: "+ps_code2, false);
		}
		
		queryResultSet3 = DB.executeQuery(Stock.getTestQuery("queryTogetStatusCodeRevc3SSN")[0], Stock.getTestQuery("queryTogetStatusCodeRevc3SSN")[1] );
		if(queryResultSet3.next()){
		Reporter.logEvent(Status.PASS, "Validating whether an event is present with code: REVERS CASH", "Record Exists", false);
		Reporter.logEvent(Status.PASS, "Record From DATABASE: ", "ID: "+queryResultSet3.getString("ID")+
				"\nMASTER EV ID: "+queryResultSet3.getString("MASTER_EV_ID")+
				"\nEVTY CODE: "+queryResultSet3.getString("EVTY_CODE")+
				"\nRESP USER LOGON ID: "+queryResultSet3.getString("RESP_USER_LOGON_ID"), false);
		}
	}
	
	
}