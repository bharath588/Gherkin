package pageobject.INPR;

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

public class INPR_Invest_Portfolio_Basic_Info {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/INPR_Investment_Portfolio_Basic_Information";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INVEST_PORT_ID_0;
	
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
	public void setINVEST_PORT_ID_0(String iNVEST_PORT_ID_0) {
		INVEST_PORT_ID_0 = iNVEST_PORT_ID_0;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINVEST_PORT_ID_0(Stock.GetParameterValue("INVEST_PORT_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&INVEST_PORT_ID_0="+INVEST_PORT_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for INPR service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run INPR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run INPR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "PORT_NAME: "+ doc.getElementsByTagName("INVEST_PORT_NAME_0").item(0).getTextContent()+
					"\nINVEST_PORT_RULE_INVEST_ID_0: "+ doc.getElementsByTagName("INVEST_PORT_RULE_INVEST_ID_0").item(0).getTextContent()+
					"\nINVEST_PORT_RULE_PC_NAME_0: "+ doc.getElementsByTagName("INVEST_PORT_RULE_PC_NAME_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	public void validateInDatabase() throws SQLException{
		
		String invest_port_id = doc.getElementsByTagName("INVEST_PORT_RULE_INVEST_ID_0").item(0).getTextContent();
		String invest_port_id_db = null;
		String invest_port_name=doc.getElementsByTagName("INVEST_PORT_NAME_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForINPR")[1],this.INVEST_PORT_ID_0);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Account Transaction\nTable Name: GA_ASSET_ALLOC_MODEL\nGROUP_ACCOUNT\nPRODUCT", false);
			invest_port_id_db = queryResultSet.getString("INVEST_ID");
			
			Reporter.logEvent(Status.INFO, "Values From Database: ", 					
					"INVEST_ID: "+queryResultSet.getString("INVEST_ID")+					
					"\nNAME: "+queryResultSet.getString("NAME"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(invest_port_id.equalsIgnoreCase(invest_port_id_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating INVEST ID from Response and Database", "Both the values are same as Expected"+
					"\nFrom Response: "+invest_port_id+"\nFrom Database: "+invest_port_id_db, false);
		}else{
			Reporter.logEvent(Status.FAIL, "Comparing and Validating INVEST ID from Response and Database", "Both the values are not same"+
					"\nFrom Response: "+invest_port_id+"\nFrom Database: "+invest_port_id_db, false);
		}
	}
	
}
