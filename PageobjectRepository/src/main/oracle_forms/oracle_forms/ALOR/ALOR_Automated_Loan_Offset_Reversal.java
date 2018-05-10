package pageobject.ALOR;

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

public class ALOR_Automated_Loan_Offset_Reversal {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ALOR_Automated_Loan_Offset_Reversal";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String QUERY_BY_GA_ID_0;
	String QUERY_BY_MASTER_EV_ID_0;
	
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
	public void setQUERY_BY_GA_ID_0(String qUERY_BY_GA_ID_0) {
		QUERY_BY_GA_ID_0 = qUERY_BY_GA_ID_0;
	}
	public void setQUERY_BY_MASTER_EV_ID_0(String qUERY_BY_MASTER_EV_ID_0) {
		QUERY_BY_MASTER_EV_ID_0 = qUERY_BY_MASTER_EV_ID_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setQUERY_BY_GA_ID_0(Stock.GetParameterValue("QUERY_BY_GA_ID_0"));
		this.setQUERY_BY_MASTER_EV_ID_0(Stock.GetParameterValue("QUERY_BY_MASTER_EV_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&QUERY_BY_GA_ID_0="+QUERY_BY_GA_ID_0+"&QUERY_BY_MASTER_EV_ID_0="+QUERY_BY_MASTER_EV_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for ALOR service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run ALOR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ALOR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
				
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From Response",
					"EVENT_LIST_EV_ID_0: "+doc.getElementsByTagName("EVENT_LIST_EV_ID_0").item(0).getTextContent()+
					"\nTRANSACTION_CODE_0: "+doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent()+
					"\nTRANSACTION_DESCR_0: "+doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForALOR")[1], this.QUERY_BY_MASTER_EV_ID_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of records in Database", "Records exists in Database with EVTY_CODE: REVRS OFF\nTable Name: EVENT", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+
						"\nLOGON_ID: "+queryResultSet.getString("LOG_USER_LOGON_ID")+
						"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of records in Database", "Records doesn't exists in Database\nTable Name: EVENT", false);
		}
	}
}
