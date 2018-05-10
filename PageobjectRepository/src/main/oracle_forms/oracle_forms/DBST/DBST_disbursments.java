package pageobject.DBST;

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

public class DBST_disbursments {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DBST_screen";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;


	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME; 
	String BUTTONS_QUERY_BY_RG_LOAN_RB_0;
	String QUERY_BY_USER_LOGON_ID_0;
	
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
	public void setBUTTONS_QUERY_BY_RG_LOAN_RB_0(String bUTTONS_QUERY_BY_RG_LOAN_RB_0) {
		BUTTONS_QUERY_BY_RG_LOAN_RB_0 = bUTTONS_QUERY_BY_RG_LOAN_RB_0;
	}
	public void setQUERY_BY_USER_LOGON_ID_0(String qUERY_BY_USER_LOGON_ID_0) {
		QUERY_BY_USER_LOGON_ID_0 = qUERY_BY_USER_LOGON_ID_0;
	}
	
	String BUTTONS_PAY_DATE_CB_0;
	String BUTTONS_USER_ID_CB_0;
	String EVENT_LIST_EV_ID_0;
	String EVENT_LIST_GA_ID_0;
	String EVENT_LIST_IS_GUIDED_EXPERIENCE_0;
	String EVENT_LIST_NAME_0;
	String EVENT_LIST_NEXT_SCHED_PAY_DUE_DATE_0;
	String EVENT_LIST_REQ_AMT_0;
	String EVENT_LIST_SPECIAL_HANDLING_0;
	String EVENT_LIST_SSN_0;
	String EVENT_LIST_USER_LOGON_ID_0;
	String QUERY_BY_PARENT_WORK_UNIT_0;
	String QUERY_BY_WKUN_SHORT_NAME_0;
	
	public String getBUTTONS_PAY_DATE_CB_0() {
		return BUTTONS_PAY_DATE_CB_0;
	}
	public String getBUTTONS_USER_ID_CB_0() {
		return BUTTONS_USER_ID_CB_0;
	}
	public String getEVENT_LIST_EV_ID_0() {
		return EVENT_LIST_EV_ID_0;
	}
	public String getEVENT_LIST_GA_ID_0() {
		return EVENT_LIST_GA_ID_0;
	}
	public String getEVENT_LIST_IS_GUIDED_EXPERIENCE_0() {
		return EVENT_LIST_IS_GUIDED_EXPERIENCE_0;
	}
	public String getEVENT_LIST_NAME_0() {
		return EVENT_LIST_NAME_0;
	}
	public String getEVENT_LIST_NEXT_SCHED_PAY_DUE_DATE_0() {
		return EVENT_LIST_NEXT_SCHED_PAY_DUE_DATE_0;
	}
	public String getEVENT_LIST_REQ_AMT_0() {
		return EVENT_LIST_REQ_AMT_0;
	}
	public String getEVENT_LIST_SPECIAL_HANDLING_0() {
		return EVENT_LIST_SPECIAL_HANDLING_0;
	}
	public String getEVENT_LIST_SSN_0() {
		return EVENT_LIST_SSN_0;
	}
	public String getEVENT_LIST_USER_LOGON_ID_0() {
		return EVENT_LIST_USER_LOGON_ID_0;
	}
	public String getQUERY_BY_PARENT_WORK_UNIT_0() {
		return QUERY_BY_PARENT_WORK_UNIT_0;
	}
	public String getQUERY_BY_WKUN_SHORT_NAME_0() {
		return QUERY_BY_WKUN_SHORT_NAME_0;
	}

	public void setServiceParameters()
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setBUTTONS_QUERY_BY_RG_LOAN_RB_0(Stock.GetParameterValue("BUTTONS_QUERY_BY_RG_LOAN_RB_0"));
		 this.setQUERY_BY_USER_LOGON_ID_0(Stock.GetParameterValue("QUERY_BY_USER_LOGON_ID_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&QUERY_BY_USER_LOGON_ID_0="+QUERY_BY_USER_LOGON_ID_0+"&BUTTONS_QUERY_BY_RG_LOAN_RB_0="+BUTTONS_QUERY_BY_RG_LOAN_RB_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for DBST service", this.queryString.replaceAll("&", "\n"), false);
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
			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run DBST disbursment outstanding service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DBST disbursment outstanding service", "Running Failed:", false);
		}
		
	}
	
	public void validateOutput(){
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		
	
		String msg = doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent();
//		Reporter.logEvent(Status.INFO, "From Response: Status Bar Messages" , msg, false);
		Reporter.logEvent(Status.INFO, "Values From Response:", "EVENT_LIST_EV_ID_0: " + doc.getElementsByTagName("EVENT_LIST_EV_ID_0").item(0).getTextContent()+
		 "\nEVENT_LIST_GA_ID_0: " + doc.getElementsByTagName("EVENT_LIST_GA_ID_0").item(0).getTextContent()+
		 "\nEVENT_LIST_NEXT_SCHED_PAY_DUE_DATE_0: " + doc.getElementsByTagName("EVENT_LIST_NEXT_SCHED_PAY_DUE_DATE_0").item(0).getTextContent()+
		 "\nEVENT_LIST_SSN_0: " + doc.getElementsByTagName("EVENT_LIST_SSN_0").item(0).getTextContent()+
		 "\nEVENT_LIST_USER_LOGON_ID_0: " + doc.getElementsByTagName("EVENT_LIST_USER_LOGON_ID_0").item(0).getTextContent()+
		 "\nEVENT_LIST_REQ_AMT_0: " + doc.getElementsByTagName("EVENT_LIST_REQ_AMT_0").item(0).getTextContent(), false);
		
	}
	
	public void validateInDatabase() throws SQLException{
		String ev_id_db=null;
		String event_id = doc.getElementsByTagName("EVENT_LIST_EV_ID_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDBST")[1],  event_id );
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable Name: DISB_BASIC", false);
			ev_id_db = queryResultSet.getString("EV_ID");
			Reporter.logEvent(Status.INFO, "Values From Database", 					
					"ID: "+queryResultSet.getString("ID")+
					"\nIND_ID: "+queryResultSet.getString("IND_ID")+
					"\nEV_ID: "+queryResultSet.getString("EV_ID")+
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nSTATUS_CODE: "+queryResultSet.getString("STATUS_CODE"),false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(event_id.equalsIgnoreCase(ev_id_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Event ID from Response and Database", "Both the values are same as Expected"+
					"\nEvent ID: "+"From Response: "+event_id+"\nFrom Database: "+ev_id_db, false);
		}
	}
	
}
