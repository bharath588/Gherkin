package pageobject.AUEF;

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

public class AUEF_Authorize_EFT_Disb_Reversals {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/AUEF_Authorize_EFT_Disb_Reversals";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SEARCH_BLOCK_EVENT_ID_0;
	String DSBA1_AUTHORIZE_0;
	
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
	public void setSEARCH_BLOCK_EVENT_ID_0(String sEARCH_BLOCK_EVENT_ID_0) {
		SEARCH_BLOCK_EVENT_ID_0 = sEARCH_BLOCK_EVENT_ID_0;
	}
	public void setDSBA1_AUTHORIZE_0(String dSBA1_AUTHORIZE_0) {
		DSBA1_AUTHORIZE_0 = dSBA1_AUTHORIZE_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setDSBA1_AUTHORIZE_0(Stock.GetParameterValue("DSBA1_AUTHORIZE_0"));
		this.setSEARCH_BLOCK_EVENT_ID_0(Stock.GetParameterValue("SEARCH_BLOCK_EVENT_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SEARCH_BLOCK_EVENT_ID_0="+SEARCH_BLOCK_EVENT_ID_0+"&DSBA1_AUTHORIZE_0="+DSBA1_AUTHORIZE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for AUEF service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run AUEF service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run AUEF service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "DSMD_CODE: "+doc.getElementsByTagName("DSBA1_DSMD_CODE_0").item(0).getTextContent()+
					"\nDSRS_CODE: "+doc.getElementsByTagName("DSBA1_DSRS_CODE_0").item(0).getTextContent()+
					"\nEVTY_CODE: "+doc.getElementsByTagName("DSBA1_EVTY_CODE_0").item(0).getTextContent()+
					"\nEV_ID: "+doc.getElementsByTagName("DSBA1_EV_ID_0").item(0).getTextContent()+
					"\nGC_ID: "+doc.getElementsByTagName("DSBA1_GC_ID_0").item(0).getTextContent()+
					"\nSTATUS_CODE: "+doc.getElementsByTagName("DSBA1_STATUS_CODE_0").item(0).getTextContent()+
					"\nSSN: "+doc.getElementsByTagName("SEARCH_BLOCK_SSN_0").item(0).getTextContent(), false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String ev_id = doc.getElementsByTagName("DSBA1_EV_ID_0").item(0).getTextContent();
		String user_name = this.LOGON_USERNAME;
		String user_id = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAUEF1")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: DISB_RECVR",false);
			
			queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAUEF2")[1], ev_id);
			if(DB.getRecordSetCount(queryResultSet)>0){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: ACH_DEBIT_AUTH",false);						
					while(queryResultSet.next()){
						user_id = queryResultSet.getString("ACH_DEBIT_AUTH");
						Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ACH_DEBIT_AUTH: "+ queryResultSet.getString("ACH_DEBIT_AUTH"), false);
					}
					
					if(user_name.equalsIgnoreCase(user_id)){
						Reporter.logEvent(Status.PASS, "verify the storage of the user's id to disb_basic.ach_debit_auth", "User id has been stored into Database"+
					"\nLOGON USERNAME: "+ user_name+"\nFrom Database: "+user_id, false);
					}
					else{
						Reporter.logEvent(Status.FAIL, "verify the storage of the user's id to disb_basic.ach_debit_auth", "User id hasn't been stored into Database"+
								"\nLOGON USERNAME: "+ user_name+"\nFrom Database: "+user_id, false);
					}
			}
			else{
				Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database\nTable name: ACH_DEBIT_AUTH",false);
			}
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
		}
	}
	
}
