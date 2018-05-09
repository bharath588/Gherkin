package pageobject.DBSC;

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

public class DBSC_Pending_Disb {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DBSC_Pending_Disbursement";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String QUERY_BY_GA_ID_0;
	String QUERY_BY_DISB_STATUS_0;
	
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
	public void setQUERY_BY_DISB_STATUS_0(String qUERY_BY_DISB_STATUS_0) {
		QUERY_BY_DISB_STATUS_0 = qUERY_BY_DISB_STATUS_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setQUERY_BY_GA_ID_0(Stock.GetParameterValue("QUERY_BY_GA_ID_0"));
		this.setQUERY_BY_DISB_STATUS_0(Stock.GetParameterValue("QUERY_BY_DISB_STATUS_0"));
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_USERNAME="+LOGON_USERNAME+"&QUERY_BY_DISB_STATUS_0="+QUERY_BY_DISB_STATUS_0+"&QUERY_BY_GA_ID_0="+QUERY_BY_GA_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for DBSC service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run DBSC service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DBSC service", "Running Failed:", false);
		}
	}
	
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "EVENT ID_0: "+ doc.getElementsByTagName("EVENT_LIST_EV_ID_0").item(0).getTextContent()+
					"EVENT ID_1: "+ doc.getElementsByTagName("EVENT_LIST_EV_ID_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String ev_id = doc.getElementsByTagName("EVENT_LIST_EV_ID_0").item(0).getTextContent();		
		String ev_id_db = null;		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDBSC")[1], ev_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			while(queryResultSet.next()){
				Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
				ev_id_db = queryResultSet.getString("EV_ID");
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: DISB_BASIC", "ID: "+queryResultSet.getString("ID")+
					"\nIND_ID: "+queryResultSet.getString("IND_ID")+
					"\nGC_ID: "+queryResultSet.getString("GC_ID")+
					"\nEV_ID: "+queryResultSet.getString("EV_ID")+
					"\nDISB_TYPE: "+queryResultSet.getString("DISB_TYPE")+
					"\nSTATUS_CODE: "+queryResultSet.getString("STATUS_CODE"), false);
				if(ev_id.equalsIgnoreCase(ev_id_db)){
					Reporter.logEvent(Status.PASS, "Comapring and Validating EVENT ID from Response and Database", "Both the values are same as expected"+
							"\nFrom Response: "+ev_id+"\nFrom Database: "+ev_id_db, false);
				}else{
					Reporter.logEvent(Status.FAIL, "Comapring and Validating EVENT ID from Response and Database", "Values are not same as expected",false);
				}
			}	
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
		
		
	}
	
}
