package pageobject.DSBM;

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

public class DSBM_Disb_Cross_Ref {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DSBM_disb_cross_ref";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String DISB_REASONS_AVAILABLE_0;
	String SELECT_EVENT_EVTY_CODE_0;
	String IRS_RULES_AVAILABLE_0;
	String PROV_CO_AVAILABLE_0;
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
	public void setDISB_REASONS_AVAILABLE_0(String dISB_REASONS_AVAILABLE_0) {
		DISB_REASONS_AVAILABLE_0 = dISB_REASONS_AVAILABLE_0;
	}
	public void setSELECT_EVENT_EVTY_CODE_0(String sELECT_EVENT_EVTY_CODE_0) {
		SELECT_EVENT_EVTY_CODE_0 = sELECT_EVENT_EVTY_CODE_0;
	}
	public void setIRS_RULES_AVAILABLE_0(String iRS_RULES_AVAILABLE_0) {
		IRS_RULES_AVAILABLE_0 = iRS_RULES_AVAILABLE_0;
	}
	public void setPROV_CO_AVAILABLE_0(String pROV_CO_AVAILABLE_0) {
		PROV_CO_AVAILABLE_0 = pROV_CO_AVAILABLE_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setDISB_REASONS_AVAILABLE_0(Stock.GetParameterValue("DISB_REASONS_AVAILABLE_0"));
		this.setSELECT_EVENT_EVTY_CODE_0(Stock.GetParameterValue("SELECT_EVENT_EVTY_CODE_0"));
		this.setIRS_RULES_AVAILABLE_0(Stock.GetParameterValue("IRS_RULES_AVAILABLE_0"));
		this.setPROV_CO_AVAILABLE_0(Stock.GetParameterValue("PROV_CO_AVAILABLE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&DISB_REASONS_AVAILABLE_0="+DISB_REASONS_AVAILABLE_0+"&SELECT_EVENT_EVTY_CODE_0="+SELECT_EVENT_EVTY_CODE_0+
				"&IRS_RULES_AVAILABLE_0="+IRS_RULES_AVAILABLE_0+"&PROV_CO_AVAILABLE_0="+PROV_CO_AVAILABLE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for DSBM service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() throws SQLException {
		try {
				this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
				serviceURL += this.queryString;
				docBuilderFactory = DocumentBuilderFactory.newInstance();
				docBuilderFactory.setIgnoringComments(true);
				docBuilderFactory.setIgnoringElementContentWhitespace(true);
				docBuilderFactory.setNamespaceAware(true);
				docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.parse(serviceURL);
		//		System.out.println(serviceURL);
				doc.getDocumentElement().normalize();
				Reporter.logEvent(Status.PASS, "Run DSBM service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run DSBM service", "Running Failed:", false);
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
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDSBM")[1], this.SELECT_EVENT_EVTY_CODE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: EVTY#DSRS#PROVCO#IRS_RULE", "Record exists in the Database", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "EVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+
						"\nDSRS_CODE: "+queryResultSet.getString("DSRS_CODE")+
						"\nPROV_CO: "+queryResultSet.getString("PROV_CO")+
						"\nIRS_RULE: "+queryResultSet.getString("IRS_RULE"), false);	
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
}