package pageobject.STPR;

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

public class STPR_Generate_Stmt_Producer_Copy {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STPR_Generate_Statement_Producer_Copy";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String PROV_CO_ENTRY_SELECT_ID_0;
	String CONTROL_BUTTONS_INCLUDE_EXCLUDE_PROV_COS_INCLUDE_CHK_0;
	
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
	public void setPROV_CO_ENTRY_SELECT_ID_0(String pROV_CO_ENTRY_SELECT_ID_0) {
		PROV_CO_ENTRY_SELECT_ID_0 = pROV_CO_ENTRY_SELECT_ID_0;
	}
	public void setCONTROL_BUTTONS_INCLUDE_EXCLUDE_PROV_COS_INCLUDE_CHK_0(
			String cONTROL_BUTTONS_INCLUDE_EXCLUDE_PROV_COS_INCLUDE_CHK_0) {
		CONTROL_BUTTONS_INCLUDE_EXCLUDE_PROV_COS_INCLUDE_CHK_0 = cONTROL_BUTTONS_INCLUDE_EXCLUDE_PROV_COS_INCLUDE_CHK_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_BUTTONS_INCLUDE_EXCLUDE_PROV_COS_INCLUDE_CHK_0(Stock.GetParameterValue("CONTROL_BUTTONS_INCLUDE_EXCLUDE_PROV_COS_INCLUDE_CHK_0"));
		this.setPROV_CO_ENTRY_SELECT_ID_0(Stock.GetParameterValue("PROV_CO_ENTRY_SELECT_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&PROV_CO_ENTRY_SELECT_ID_0="+PROV_CO_ENTRY_SELECT_ID_0+"&CONTROL_BUTTONS_INCLUDE_EXCLUDE_PROV_COS_INCLUDE_CHK_0="+CONTROL_BUTTONS_INCLUDE_EXCLUDE_PROV_COS_INCLUDE_CHK_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for STPR service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run STPR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STPR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response ", "PROV CO NAME: "+doc.getElementsByTagName("PROV_CO_ENTRY_NAME_0").item(0).getTextContent(), false);
		}
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String prov_co = doc.getElementsByTagName("PROV_CO_ENTRY_NAME_0").item(0).getTextContent().trim();
			
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTPR")[1], prov_co);		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in DB", "Records exists in DB", false);
			while(queryResultSet.next()){				
				
				Reporter.logEvent(Status.PASS, "From DATABASE: \nTable Name: PROV_COMPANY", "CODE: "+queryResultSet.getString("CODE")+
					"\nNAME: "+queryResultSet.getString("NAME")+					
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nFIRST_LINE_MAILING: "+queryResultSet.getString("FIRST_LINE_MAILING")+
					"\nSTATE_CODE: "+queryResultSet.getString("STATE_CODE"), false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
}
