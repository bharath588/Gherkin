package pageobject.DRVL;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class DRVL_Query {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DRVL_QUERY";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String STEP_TYPE_LOV;
	String HEADER_RULE_DATE_0;
	String TXN_RULE_STD_RL_ID_LOV2;
	
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
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}
	public void setHEADER_RULE_DATE_0(String hEADER_RULE_DATE_0) {
		HEADER_RULE_DATE_0 = hEADER_RULE_DATE_0;
	}
	public void setTXN_RULE_STD_RL_ID_LOV2(String tXN_RULE_STD_RL_ID_LOV2) {
		TXN_RULE_STD_RL_ID_LOV2 = tXN_RULE_STD_RL_ID_LOV2;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		 this.setHEADER_RULE_DATE_0(Stock.GetParameterValue("HEADER_RULE_DATE_0"));
		 this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		 this.setTXN_RULE_STD_RL_ID_LOV2(Stock.GetParameterValue("TXN_RULE_STD_RL_ID_LOV2"));
		
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&STEP_TYPE_LOV="+STEP_TYPE_LOV+"&HEADER_RULE_DATE_0="+HEADER_RULE_DATE_0+
				 "&TXN_RULE_STD_RL_ID_LOV2="+TXN_RULE_STD_RL_ID_LOV2+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for CONH service", this.queryString.replaceAll("&", "\n"), false);
		 
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
				Reporter.logEvent(Status.PASS, "Run  DRVL_QUERY service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run DRVL_QUERY service", "Running Failed:", false);
			}
			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		Reporter.logEvent(Status.PASS, "From response: ","EV_ID: "+doc.getElementsByTagName("HEADER_EV_ID_0").item(0).getTextContent()+
				"\nGA_ID: "+doc.getElementsByTagName("HEADER_GA_ID_0").item(0).getTextContent()+
				"\nSSN: "+doc.getElementsByTagName("HEADER_SSN_0").item(0).getTextContent()+
				"\n",false);
	}
	
	/*	public void validateInDatabase() throws SQLException{
	
	queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToViewMsgs")[1], MESSAGE_MT_PGM_NAME_0);
	if(queryResultSet.next()){
		Reporter.logEvent(Status.PASS, "From DATABASE: ", "ID: "+ queryResultSet.getString("ID")+
				"\nPGM_NAME: "+queryResultSet.getString("MT_PGM_NAME")+
				"\nDESCRIPTION: "+queryResultSet.getString("DESCR"), false);
	}
	else{
		Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
	}
	
} */

}
