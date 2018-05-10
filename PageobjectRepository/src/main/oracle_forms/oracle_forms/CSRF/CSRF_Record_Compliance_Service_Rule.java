package pageobject.CSRF;

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

public class CSRF_Record_Compliance_Service_Rule {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CSRF_Create_Rule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GROUP_HEADER1_N_GA_ID_0;
	String SERVICE_TYPE_LOV;
	String COMPLIANCE_SERVICE_RULE_EFFDATE_0;
	
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
	public void setGROUP_HEADER1_N_GA_ID_0(String gROUP_HEADER1_N_GA_ID_0) {
		GROUP_HEADER1_N_GA_ID_0 = gROUP_HEADER1_N_GA_ID_0;
	}
	public void setSERVICE_TYPE_LOV(String sERVICE_TYPE_LOV) {
		SERVICE_TYPE_LOV = sERVICE_TYPE_LOV;
	}
	public void setCOMPLIANCE_SERVICE_RULE_EFFDATE_0(
			String cOMPLIANCE_SERVICE_RULE_EFFDATE_0) {
		COMPLIANCE_SERVICE_RULE_EFFDATE_0 = cOMPLIANCE_SERVICE_RULE_EFFDATE_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCOMPLIANCE_SERVICE_RULE_EFFDATE_0(Stock.GetParameterValue("COMPLIANCE_SERVICE_RULE_EFFDATE_0"));
		this.setGROUP_HEADER1_N_GA_ID_0(Stock.GetParameterValue("GROUP_HEADER1_N_GA_ID_0"));
		this.setSERVICE_TYPE_LOV(Stock.GetParameterValue("SERVICE_TYPE_LOV"));
	
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GROUP_HEADER1_N_GA_ID_0="+GROUP_HEADER1_N_GA_ID_0+"&SERVICE_TYPE_LOV="+SERVICE_TYPE_LOV+"&COMPLIANCE_SERVICE_RULE_EFFDATE_0="+COMPLIANCE_SERVICE_RULE_EFFDATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for CSRF service", this.queryString.replaceAll("&", "\n"), false);
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
//			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run CSRF service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CSRF service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From Response: ", "SERVICE RULE SERVICE TYPE: "+ doc.getElementsByTagName("COMPLIANCE_SERVICE_RULE_SERVICE_TYPE_0").item(0).getTextContent()+
					"\nSERVICE RULE DESCR: "+ doc.getElementsByTagName("COMPLIANCE_SERVICE_RULE_ST_DESCRIPTION_0").item(0).getTextContent()+					
					"\nPLAN NAME: "+ doc.getElementsByTagName("GROUP_HEADER1_N_PLAN_NAME_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String service_type_resp = doc.getElementsByTagName("COMPLIANCE_SERVICE_RULE_SERVICE_TYPE_0").item(0).getTextContent();
		String service_type_db = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSRF")[1],  this.GROUP_HEADER1_N_GA_ID_0);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Account Transaction\nTable Name: COMPLIANCE_SERVICE_RULE", false);
			service_type_db = queryResultSet.getString("SERVICE_TYPE");
			Reporter.logEvent(Status.INFO, "From DATABASE: ", "GA ID: "+queryResultSet.getString("GA_ID")+
					"\nSERVICE TYPE: "+queryResultSet.getString("SERVICE_TYPE")+					
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE"), false);			
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(service_type_resp.equalsIgnoreCase(service_type_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating SERVICE TYPE from Response and Database", "Both the values are same as Expected"+
					"\nService Type: "+"From Response: "+service_type_resp+"\nFrom Database: "+service_type_db, false);
		
		}
	}
	
}
