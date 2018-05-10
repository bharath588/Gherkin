package pageobject.REVC;

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

public class REVC_ReverseCash_Event {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/REVC_reverse_evnt";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet1;
	private ResultSet queryResultSet2;
	private ResultSet queryResultSet3;
	
	
	String CONTROL_SELECTION_0;
	String CONTROL_EV_ID_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME; 
	
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}

	public void setCONTROL_EV_ID_0(String cONTROL_EV_ID_0) {
		CONTROL_EV_ID_0 = cONTROL_EV_ID_0;
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
			//System.out.println(serviceURL);
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run REVC Reverse Cash Event service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run REVC Reverse Cash Event", "Running Failed:", false);
		}
		
	}
	
	public void setServiceParameters()
	{
		
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_EV_ID_0(Stock.GetParameterValue("CONTROL_EV_ID_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		
		queryString ="?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_EV_ID_0="+CONTROL_EV_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		/*Reporter.logEvent(Status.INFO, "Preparing Data For TestCase", "CONTROL_EV_ID_0: "+CONTROL_EV_ID_0+
				"\n", false);*/
		
		Reporter.logEvent(Status.INFO, "Prepare test data for REVC service", this.queryString.replaceAll("&", "\n"), false);
	
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
		String ps_code1 = null;
		queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTogetStatusCodeRevc1EVNT")[1], CONTROL_EV_ID_0);
		
		if(DB.getRecordSetCount(queryResultSet1)>0){
			while(queryResultSet1.next())
				ps_code1 = queryResultSet1.getString("PROCESS_STATUS_CODE");
			System.out.println("PROCESS STATUS CODE: "+ps_code1);
			Reporter.logEvent(Status.PASS, "Fetching Process Status Code from ag_remit", "PROCESS STATUS CODE: "+ps_code1, false);
		}else{
			Reporter.logEvent(Status.FAIL, "Fetching Process Status Code from ag_remit", "PROCESS STATUS CODE: "+ps_code1, false);
		}
		
		queryResultSet2 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTogetStatusCodeRevc2EVNT")[1]);
		if(DB.getRecordSetCount(queryResultSet2)>0){
			if(queryResultSet2.next()){
				Reporter.logEvent(Status.PASS, "Validating whether an event is present with code: REVERSE CASH", "Records Exists with code: REVERSE CASH", false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating whether an event is present with code: REVERSE CASH", "Records doesn't Exists with code: REVERSE CASH", false);
		}
	}
}
