package pageobject.EXCO;

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

public class EXCO_Create {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/EXCO_Create";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String EXCHANGE_CO_COMPANY_NAME_0;
	String EXCHANGE_CO_FIRST_LINE_MAILING_0;
	String EXCHANGE_CO_CITY_0;
	String EXCHANGE_CO_STATE_CODE_0;
	String EXCHANGE_CO_ZIP_CODE_0;
	String EXCHANGE_CO_COUNTRY_LOV2;
	String Editor;
	
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
	public void setEXCHANGE_CO_COMPANY_NAME_0(String eXCHANGE_CO_COMPANY_NAME_0) {
		EXCHANGE_CO_COMPANY_NAME_0 = eXCHANGE_CO_COMPANY_NAME_0;
	}
	public void setEXCHANGE_CO_FIRST_LINE_MAILING_0(
			String eXCHANGE_CO_FIRST_LINE_MAILING_0) {
		EXCHANGE_CO_FIRST_LINE_MAILING_0 = eXCHANGE_CO_FIRST_LINE_MAILING_0;
	}
	public void setEXCHANGE_CO_CITY_0(String eXCHANGE_CO_CITY_0) {
		EXCHANGE_CO_CITY_0 = eXCHANGE_CO_CITY_0;
	}
	public void setEXCHANGE_CO_STATE_CODE_0(String eXCHANGE_CO_STATE_CODE_0) {
		EXCHANGE_CO_STATE_CODE_0 = eXCHANGE_CO_STATE_CODE_0;
	}
	public void setEXCHANGE_CO_ZIP_CODE_0(String eXCHANGE_CO_ZIP_CODE_0) {
		EXCHANGE_CO_ZIP_CODE_0 = eXCHANGE_CO_ZIP_CODE_0;
	}
	public void setEXCHANGE_CO_COUNTRY_LOV2(String eXCHANGE_CO_COUNTRY_LOV2) {
		EXCHANGE_CO_COUNTRY_LOV2 = eXCHANGE_CO_COUNTRY_LOV2;
	}
	public void setEditor(String editor) {
		Editor = editor;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setEditor(Stock.GetParameterValue("Editor"));
		this.setEXCHANGE_CO_CITY_0(Stock.GetParameterValue("EXCHANGE_CO_CITY_0"));
		this.setEXCHANGE_CO_COMPANY_NAME_0(Stock.GetParameterValue("EXCHANGE_CO_COMPANY_NAME_0"));
		this.setEXCHANGE_CO_COUNTRY_LOV2(Stock.GetParameterValue("EXCHANGE_CO_COUNTRY_LOV2"));
		this.setEXCHANGE_CO_FIRST_LINE_MAILING_0(Stock.GetParameterValue("EXCHANGE_CO_FIRST_LINE_MAILING_0"));
		this.setEXCHANGE_CO_STATE_CODE_0(Stock.GetParameterValue("EXCHANGE_CO_STATE_CODE_0"));
		this.setEXCHANGE_CO_ZIP_CODE_0(Stock.GetParameterValue("EXCHANGE_CO_ZIP_CODE_0"));
				
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&EXCHANGE_CO_COMPANY_NAME_0="+EXCHANGE_CO_COMPANY_NAME_0+"&EXCHANGE_CO_FIRST_LINE_MAILING_0="+EXCHANGE_CO_FIRST_LINE_MAILING_0+"&EXCHANGE_CO_CITY_0="+EXCHANGE_CO_CITY_0+
				"&EXCHANGE_CO_STATE_CODE_0="+EXCHANGE_CO_STATE_CODE_0+"&EXCHANGE_CO_ZIP_CODE_0="+EXCHANGE_CO_ZIP_CODE_0+"&EXCHANGE_CO_COUNTRY_LOV2="+EXCHANGE_CO_COUNTRY_LOV2+"&Editor="+Editor+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for EXCO service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run EXCO service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run EXCO service", "Running Failed:", false);
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
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForEXCO")[1], this.EXCHANGE_CO_COMPANY_NAME_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			while(queryResultSet.next()){
				
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists for Event", false);			
				Reporter.logEvent(Status.PASS, "From DATABASE: \nTable Name: EXCHANGE_COMPANY", "ID: "+queryResultSet.getString("ID")+
						"\nCOMPANY_NAME: "+queryResultSet.getString("COMPANY_NAME")+
						"\nFIRST_LINE_MAILING: "+queryResultSet.getString("FIRST_LINE_MAILING")+
						"\nCITY: "+queryResultSet.getString("CITY")+
						"\nZIP_CODE: "+queryResultSet.getString("ZIP_CODE")+
						"\nCOUNTRY: "+queryResultSet.getString("COUNTRY")+
						"\nSTATE_CODE: "+queryResultSet.getString("STATE_CODE")+
						"\nMAILING_NAME: "+queryResultSet.getString("MAILING_NAME_1"), false);
			}
		}	
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
	}
}
