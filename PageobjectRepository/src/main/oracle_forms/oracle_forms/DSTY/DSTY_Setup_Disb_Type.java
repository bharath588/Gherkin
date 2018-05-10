package pageobject.DSTY;

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

public class DSTY_Setup_Disb_Type {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DSTY_Setup_Disb_Type";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String DISB_TYPE_EVTY_CODE_9;
	String DISB_TYPE_DISB_TYPE_9;
	String DISB_TYPE_TXN_CODE_9;
	
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
	public void setDISB_TYPE_EVTY_CODE_9(String dISB_TYPE_EVTY_CODE_9) {
		DISB_TYPE_EVTY_CODE_9 = dISB_TYPE_EVTY_CODE_9;
	}
	public void setDISB_TYPE_DISB_TYPE_9(String dISB_TYPE_DISB_TYPE_9) {
		DISB_TYPE_DISB_TYPE_9 = dISB_TYPE_DISB_TYPE_9;
	}
	public void setDISB_TYPE_TXN_CODE_9(String dISB_TYPE_TXN_CODE_9) {
		DISB_TYPE_TXN_CODE_9 = dISB_TYPE_TXN_CODE_9;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setDISB_TYPE_DISB_TYPE_9(Stock.GetParameterValue("DISB_TYPE_DISB_TYPE_9"));
		this.setDISB_TYPE_EVTY_CODE_9(Stock.GetParameterValue("DISB_TYPE_EVTY_CODE_9"));
		this.setDISB_TYPE_TXN_CODE_9(Stock.GetParameterValue("DISB_TYPE_TXN_CODE_9"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&DISB_TYPE_EVTY_CODE_9="+DISB_TYPE_EVTY_CODE_9+"&DISB_TYPE_DISB_TYPE_9="+DISB_TYPE_DISB_TYPE_9+"&DISB_TYPE_TXN_CODE_9="+DISB_TYPE_TXN_CODE_9+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for DSTY service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run DSTY service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DSTY service", "Running Failed:", false);
		}
	}
	
	public String validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response", "EVTY CODE: "+doc.getElementsByTagName("DISB_TYPE_EVTY_CODE_0").item(0).getTextContent()
					, false);
			
			return doc.getElementsByTagName("DISB_TYPE_EVTY_CODE_0").item(0).getTextContent();
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
			return null;
		}
		
	}
	
	public void validateInDatabase() throws SQLException{
		String evty_code = doc.getElementsByTagName("DISB_TYPE_EVTY_CODE_0").item(0).getTextContent();
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDSTYSetup")[1], evty_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: DISB_TYPE", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "EVTY CODE: "+queryResultSet.getString("EVTY_CODE")+
						"\nDISB TYPE: "+queryResultSet.getString("DISB_TYPE")+
						"\nTXN CODE: "+queryResultSet.getString("TXN_CODE"), false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
	}
	
	public void FlushData(){
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDSTYDelete")[1], this.DISB_TYPE_EVTY_CODE_9);
	}
	
}
