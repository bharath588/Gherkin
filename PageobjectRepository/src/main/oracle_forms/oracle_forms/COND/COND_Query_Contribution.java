package pageobject.COND;

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

public class COND_Query_Contribution {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/COND_Query_Contrib";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String STEP_TYPE_LOV;
	
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
	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
	}
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV){
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		
		ResultSet queryResult = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForIndIdCOND")[1],  CONTROL_SSN_DISPL_0);
		if(queryResult.next()){

		String ind_id = queryResult.getString("ID");		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDeleteCOND")[1],  ind_id);
		}
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&STEP_TYPE_LOV="+STEP_TYPE_LOV+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for COND Cntribution service", this.queryString.replaceAll("&", "\n"), false);
	
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
			Reporter.logEvent(Status.PASS, "Run COND service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run COND service", "Running Failed:", false);
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
		String evty_code = null;
		ResultSet queryResult = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForIndIdCOND")[1],  CONTROL_SSN_DISPL_0);
		if(queryResult.next()){

		String ind_id = queryResult.getString("ID");		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCOND")[1],  ind_id);
		
			if(queryResultSet.next()){
				evty_code = queryResultSet.getString("EVTY_CODE");
				Reporter.logEvent(Status.INFO, "From DATABASE:\n Table: EVENT ", "EV_ID: "+queryResultSet.getString("ID")+
					"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+					
					"\nUSER_ID: "+queryResultSet.getString("RESP_USER_LOGON_ID")+
					"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME"), false);
			}
			else{
				Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
			}
			if(evty_code.equalsIgnoreCase("INQ NUPART")){
				Reporter.logEvent(Status.PASS, "Comparing and Validating EVTY CODE ", 
						"\nEVTY_CODE: "+"Expected: "+"INQ NUPART"+"\nFrom Database: "+evty_code, false);
			}
		}
	}
	
}
