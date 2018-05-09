package pageobject.STDS;

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

public class STDS_Create_Std_Service {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STDS_CREATE_STD_SERVICE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String STD_SERVICE_CODE_1;
	String STD_SERVICE_SUBCODE_1;
	String STD_SERVICE_DESCR_1;
	String SERVICE_LEVEL_LOV;
	
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
	public void setSTD_SERVICE_CODE_1(String sTD_SERVICE_CODE_1) {
		STD_SERVICE_CODE_1 = sTD_SERVICE_CODE_1;
	}
	public void setSTD_SERVICE_SUBCODE_1(String sTD_SERVICE_SUBCODE_1) {
		STD_SERVICE_SUBCODE_1 = sTD_SERVICE_SUBCODE_1;
	}
	public void setSTD_SERVICE_DESCR_1(String sTD_SERVICE_DESCR_1) {
		STD_SERVICE_DESCR_1 = sTD_SERVICE_DESCR_1;
	}
	public void setSERVICE_LEVEL_LOV(String sERVICE_LEVEL_LOV) {
		SERVICE_LEVEL_LOV = sERVICE_LEVEL_LOV;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSERVICE_LEVEL_LOV(Stock.GetParameterValue("SERVICE_LEVEL_LOV"));
		this.setSTD_SERVICE_CODE_1(Stock.GetParameterValue("STD_SERVICE_CODE_1"));
		this.setSTD_SERVICE_DESCR_1(Stock.GetParameterValue("STD_SERVICE_DESCR_1"));
		this.setSTD_SERVICE_SUBCODE_1(Stock.GetParameterValue("STD_SERVICE_SUBCODE_1"));
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDeletingSTDS")[1],  this.STD_SERVICE_CODE_1);
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&STD_SERVICE_CODE_1="+STD_SERVICE_CODE_1+"&STD_SERVICE_SUBCODE_1="+STD_SERVICE_SUBCODE_1+"&STD_SERVICE_DESCR_1="+STD_SERVICE_DESCR_1+"&SERVICE_LEVEL_LOV="+SERVICE_LEVEL_LOV+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		Reporter.logEvent(Status.INFO, "Prepare test data for STDS service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run STDS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STDS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			}
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String std_code = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTDS")[1],  this.STD_SERVICE_CODE_1);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Event", false);
			std_code = queryResultSet.getString("CODE");
			Reporter.logEvent(Status.PASS, "From DATABASE: \nTable Name: STD_SERVICE", "CODE: "+queryResultSet.getString("CODE")+
					"\nSUBCODE: "+queryResultSet.getString("SUBCODE")+					
					"\nDESCR: "+queryResultSet.getString("DESCR")+
					"\nSERVICE_LEVEL_CODE: "+queryResultSet.getString("SERVICE_LEVEL_CODE"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(this.STD_SERVICE_CODE_1.equalsIgnoreCase(std_code)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Service Code from Input and Database", "Both the values are same as Expected"+
					"\nService Code: "+"From Input: "+this.STD_SERVICE_CODE_1+"\nFrom Database: "+std_code, false);
		
		}
	}

}
