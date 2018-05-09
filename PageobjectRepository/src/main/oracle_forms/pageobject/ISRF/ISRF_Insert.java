package pageobject.ISRF;

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

public class ISRF_Insert {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ISRF_INSERT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String ISIS_REF_VALUES_RV_TABLE_0; 
	String ISIS_REF_VALUES_RV_COLUMN_0;
	String ISIS_REF_VALUES_RV_LOW_VALUE_0; 
	String ISIS_REF_VALUES_RV_HIGH_VALUE_0; 
	String ISIS_REF_VALUES_RV_DOMAIN_0; 
	String ISIS_REF_VALUES_RV_MEANING_0;
	
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
	public void setISIS_REF_VALUES_RV_TABLE_0(String iSIS_REF_VALUES_RV_TABLE_0) {
		ISIS_REF_VALUES_RV_TABLE_0 = iSIS_REF_VALUES_RV_TABLE_0;
	}
	public void setISIS_REF_VALUES_RV_COLUMN_0(String iSIS_REF_VALUES_RV_COLUMN_0) {
		ISIS_REF_VALUES_RV_COLUMN_0 = iSIS_REF_VALUES_RV_COLUMN_0;
	}
	public void setISIS_REF_VALUES_RV_LOW_VALUE_0(
			String iSIS_REF_VALUES_RV_LOW_VALUE_0) {
		ISIS_REF_VALUES_RV_LOW_VALUE_0 = iSIS_REF_VALUES_RV_LOW_VALUE_0;
	}
	public void setISIS_REF_VALUES_RV_HIGH_VALUE_0(
			String iSIS_REF_VALUES_RV_HIGH_VALUE_0) {
		ISIS_REF_VALUES_RV_HIGH_VALUE_0 = iSIS_REF_VALUES_RV_HIGH_VALUE_0;
	}
	public void setISIS_REF_VALUES_RV_DOMAIN_0(String iSIS_REF_VALUES_RV_DOMAIN_0) {
		ISIS_REF_VALUES_RV_DOMAIN_0 = iSIS_REF_VALUES_RV_DOMAIN_0;
	}
	public void setISIS_REF_VALUES_RV_MEANING_0(String iSIS_REF_VALUES_RV_MEANING_0) {
		ISIS_REF_VALUES_RV_MEANING_0 = iSIS_REF_VALUES_RV_MEANING_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setISIS_REF_VALUES_RV_COLUMN_0(Stock.GetParameterValue("ISIS_REF_VALUES_RV_COLUMN_0"));
		this.setISIS_REF_VALUES_RV_DOMAIN_0(Stock.GetParameterValue("ISIS_REF_VALUES_RV_DOMAIN_0"));
		this.setISIS_REF_VALUES_RV_HIGH_VALUE_0(Stock.GetParameterValue("ISIS_REF_VALUES_RV_HIGH_VALUE_0"));
		this.setISIS_REF_VALUES_RV_LOW_VALUE_0(Stock.GetParameterValue("ISIS_REF_VALUES_RV_LOW_VALUE_0"));
		this.setISIS_REF_VALUES_RV_MEANING_0(Stock.GetParameterValue("ISIS_REF_VALUES_RV_MEANING_0"));
		this.setISIS_REF_VALUES_RV_TABLE_0(Stock.GetParameterValue("ISIS_REF_VALUES_RV_TABLE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&ISIS_REF_VALUES_RV_TABLE_0="+ISIS_REF_VALUES_RV_TABLE_0+"&ISIS_REF_VALUES_RV_COLUMN_0="+ISIS_REF_VALUES_RV_COLUMN_0+"&ISIS_REF_VALUES_RV_LOW_VALUE_0="+ISIS_REF_VALUES_RV_LOW_VALUE_0+
				"&ISIS_REF_VALUES_RV_HIGH_VALUE_0="+ISIS_REF_VALUES_RV_HIGH_VALUE_0+"&ISIS_REF_VALUES_RV_DOMAIN_0="+ISIS_REF_VALUES_RV_DOMAIN_0+"&ISIS_REF_VALUES_RV_MEANING_0="+ISIS_REF_VALUES_RV_MEANING_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for ISRF INSERT service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run ISRF INSERT service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ISRF INSERT service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "DATE when insert is Done: "+ doc.getElementsByTagName("CFG_CONTROL_TODAYS_DATE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException{
		String rv_low_resp = this.ISIS_REF_VALUES_RV_LOW_VALUE_0;
		String rv_low_db = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForISRFinsert")[1], ISIS_REF_VALUES_RV_LOW_VALUE_0, ISIS_REF_VALUES_RV_HIGH_VALUE_0, ISIS_REF_VALUES_RV_TABLE_0, ISIS_REF_VALUES_RV_COLUMN_0, ISIS_REF_VALUES_RV_DOMAIN_0, ISIS_REF_VALUES_RV_MEANING_0);
		
		if(queryResultSet.next()){
			rv_low_db = queryResultSet.getString("RV_LOW_VALUE");
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records inserted into Database", false);
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "RV_LOW_VALUE: "+queryResultSet.getString("RV_LOW_VALUE")+
					"\nRV_HIGH_VALUE: "+queryResultSet.getString("RV_HIGH_VALUE")+
					"\nTABLE: "+queryResultSet.getString("RV_TABLE")+
					"\nCOLUMN: "+queryResultSet.getString("RV_COLUMN")+
					"\nDOMAIN: "+queryResultSet.getString("RV_DOMAIN"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(rv_low_resp.equalsIgnoreCase(rv_low_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating RV_LOW_VALUE from Input and Database", "Both the values are same as Expected"+
					"\nRV_LOW_VALUE: "+"From Input: "+rv_low_resp+"\nFrom Database: "+rv_low_db, false);
		}
  
	}
	
	public void FlushDataCreated(){
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForISRFinsertClearData")[1], ISIS_REF_VALUES_RV_LOW_VALUE_0, ISIS_REF_VALUES_RV_HIGH_VALUE_0, ISIS_REF_VALUES_RV_TABLE_0, ISIS_REF_VALUES_RV_COLUMN_0, ISIS_REF_VALUES_RV_DOMAIN_0, ISIS_REF_VALUES_RV_MEANING_0);
	/*	if(DB.getRecordSetCount(queryResultSet)==0){
			System.out.println("Data Cleared from Database");
		}*/
	}

}
