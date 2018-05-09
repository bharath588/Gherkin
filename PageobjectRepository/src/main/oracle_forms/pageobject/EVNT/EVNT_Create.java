package pageobject.EVNT;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class EVNT_Create {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/EVNT_Create";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private String eventId;
	private ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;

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
	public void setEVENT_N_FAST_KEY_CHAR_0(String eVENT_N_FAST_KEY_CHAR_0) {
		EVENT_N_FAST_KEY_CHAR_0 = eVENT_N_FAST_KEY_CHAR_0;
	}
	public void setEVENT_N_FAST_KEY_CHAR_0_X1(String eVENT_N_FAST_KEY_CHAR_0_X1) {
		EVENT_N_FAST_KEY_CHAR_0_X1 = eVENT_N_FAST_KEY_CHAR_0_X1;
	}
	public void setEVENT_N_FAST_KEY_CHAR_0_X2(String eVENT_N_FAST_KEY_CHAR_0_X2) {
		EVENT_N_FAST_KEY_CHAR_0_X2 = eVENT_N_FAST_KEY_CHAR_0_X2;
	}
	public void setEVENT_N_FAST_KEY_CHAR_0_X3(String eVENT_N_FAST_KEY_CHAR_0_X3) {
		EVENT_N_FAST_KEY_CHAR_0_X3 = eVENT_N_FAST_KEY_CHAR_0_X3;
	}
	public void setEVENT_N_FAST_KEY_CHAR_0_X4(String eVENT_N_FAST_KEY_CHAR_0_X4) {
		EVENT_N_FAST_KEY_CHAR_0_X4 = eVENT_N_FAST_KEY_CHAR_0_X4;
	}
	public void setEVENT_N_SUBJECT_ID_0(String eVENT_N_SUBJECT_ID_0) {
		EVENT_N_SUBJECT_ID_0 = eVENT_N_SUBJECT_ID_0;
	}
	public String getQueryString() {
		return queryString;
	}
	public String getServiceURL() {
		return serviceURL;
	}
	public DocumentBuilderFactory getDocBuilderFactory() {
		return docBuilderFactory;
	}
	public DocumentBuilder getDocBuilder() {
		return docBuilder;
	}
	public Document getDoc() {
		return doc;
	}
	public String getEventId() {
		return eventId;
	}
	public ResultSet getQueryResultSet() {
		return queryResultSet;
	}
	public String getCONTROL_SELECTION_0() {
		return CONTROL_SELECTION_0;
	}
	public String getLOGON_DATABASE() {
		return LOGON_DATABASE;
	}
	public String getLOGON_PASSWORD() {
		return LOGON_PASSWORD;
	}
	public String getLOGON_USERNAME() {
		return LOGON_USERNAME;
	}
	public String getEVENT_N_FAST_KEY_CHAR_0() {
		return EVENT_N_FAST_KEY_CHAR_0;
	}
	public String getEVENT_N_FAST_KEY_CHAR_0_X1() {
		return EVENT_N_FAST_KEY_CHAR_0_X1;
	}
	public String getEVENT_N_FAST_KEY_CHAR_0_X2() {
		return EVENT_N_FAST_KEY_CHAR_0_X2;
	}
	public String getEVENT_N_FAST_KEY_CHAR_0_X3() {
		return EVENT_N_FAST_KEY_CHAR_0_X3;
	}
	public String getEVENT_N_FAST_KEY_CHAR_0_X4() {
		return EVENT_N_FAST_KEY_CHAR_0_X4;
	}
	public String getEVENT_N_SUBJECT_ID_0() {
		return EVENT_N_SUBJECT_ID_0;
	}
	String LOGON_USERNAME;
	String  EVENT_N_FAST_KEY_CHAR_0;
	String  EVENT_N_FAST_KEY_CHAR_0_X1;
	String  EVENT_N_FAST_KEY_CHAR_0_X2;
	String  EVENT_N_FAST_KEY_CHAR_0_X3;
	String  EVENT_N_FAST_KEY_CHAR_0_X4;
	String  EVENT_N_SUBJECT_ID_0;

	public void setServiceParameters()
	{
		this.CONTROL_SELECTION_0 = Stock.GetParameterValue("CONTROL_SELECTION_0");
		this.setEVENT_N_FAST_KEY_CHAR_0(Stock.GetParameterValue("EVENT_N_FAST_KEY_CHAR_0"));
		/*this.EVENT_N_FAST_KEY_CHAR_0_X1 = Stock.GetParameterValue("EVENT_N_FAST_KEY_CHAR_0_X1");
		this.EVENT_N_FAST_KEY_CHAR_0_X2 = Stock.GetParameterValue("EVENT_N_FAST_KEY_CHAR_0_X2");
		this.EVENT_N_FAST_KEY_CHAR_0_X3 = Stock.GetParameterValue("EVENT_N_FAST_KEY_CHAR_0_X3");
		this.EVENT_N_FAST_KEY_CHAR_0_X4 = Stock.GetParameterValue("EVENT_N_FAST_KEY_CHAR_0_X4");*/
		this.setEVENT_N_SUBJECT_ID_0(Stock.GetParameterValue("EVENT_N_SUBJECT_ID_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&EVENT_N_FAST_KEY_CHAR_0="+EVENT_N_FAST_KEY_CHAR_0+
				"&EVENT_N_SUBJECT_ID_0="+EVENT_N_SUBJECT_ID_0+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_USERNAME="+LOGON_USERNAME+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";	
		Reporter.logEvent(Status.INFO, "Prepare test data for EVNT_Create service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run EVNT service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run EVNT service", "Running Failed:", false);
		}
	}

	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
		}
		eventId = doc.getElementsByTagName("EVENT_ID_0").item(0).getTextContent();

		if(!eventId.equalsIgnoreCase(null))
		{
		Reporter.logEvent(Status.PASS, "Event Id generated ", "The event id generated is "+ eventId, false);
		}else{
			Reporter.logEvent(Status.FAIL, "Check if eventid is generated", "The event id is not generated !!!", false);
		}
	}

	public void validateInDatabase() throws SQLException, ParseException
	{
		Reporter.logEvent(Status.INFO,"Validations of event Id generated in database","Validations of event Id generated in database to check if any row is generated",false);
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("checkEventCreatedQuery")[1], eventId);
		String dateTxt = doc.getElementsByTagName("BUTTON_PALETTE_DATE_TEXT_0").item(0).getTextContent();
		
		
		if(queryResultSet != null)
		{
			if(queryResultSet.next())
			{
				
				Reporter.logEvent(Status.PASS, "Validate the event created in Database event table", "The event is created successfully "+
						"The details are " + "ID = " + queryResultSet.getString("ID")+"\n"+"DPDATE Time = "+ queryResultSet.getString("DPDATE_TIME"), false);
				
				if(queryResultSet.getString("RESP_USER_LOGON_ID").equalsIgnoreCase(LOGON_USERNAME))
				{
					Reporter.logEvent(Status.PASS, "Compare DB user logon id with the logon id in the response", "The logon id in both DB and response are equal"
							+"\nExpected : " + this.LOGON_USERNAME
							+"\nActual : " + queryResultSet.getString("RESP_USER_LOGON_ID"),false);
				}else{
					Reporter.logEvent(Status.FAIL, "Compare DB user logon id with the logon id in the response", "The logon id in both DB and response are different"
							+"\nExpected : " + this.LOGON_USERNAME
							+"\nActual : " + queryResultSet.getString("RESP_USER_LOGON_ID"),false);
				}
				
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	//		Calendar calender = Calendar.getInstance();
			Date dpdate = queryResultSet.getDate("DPDATE_TIME");
			String date1 = sdf.format(dpdate);
	//		String date2=sdf.format(calender.getTime());
			//Date responseDate = sdf.getDateInstance().parse(dateTxt);
			Date responseDate = sdf.parse(dateTxt);
			String date3 = sdf.format(responseDate);
			
				
				if(date1.equalsIgnoreCase(date3))
				{
					Reporter.logEvent(Status.PASS, "Compare the db date with the date in response",
							"Both db date and the date captured in response are similar"
							+ "\nActual : "+date3
							+"\nExpected : "+date1,false);
				}else{
					Reporter.logEvent(Status.FAIL, "Compare the db date with the date in response",
							"Both db date and the date captured in response are different"
							+ "\nActual : "+date3
							+"\nExpected : "+date1,false);
				}
				
				String eventCode = doc.getElementsByTagName("EVENT_EVTY_CODE_0").item(0).getTextContent();
				if(eventCode.equalsIgnoreCase(queryResultSet.getString("EVTY_CODE")))
				{
					Reporter.logEvent(Status.PASS, "Compare the Event code captured in Database with the same captured in response",
							"Both event code captured in DB and response are similar"
							+ "\nActual : "+eventCode
							+"\nExpected : "+queryResultSet.getString("EVTY_CODE"),false);
				}else{
					Reporter.logEvent(Status.FAIL, "Compare the Event code captured in Database with the same captured in response",
							"Both event code captured in DB and response are different"
							+ "\nActual : "+eventCode
							+"\nExpected : "+queryResultSet.getString("EVTY_CODE"),false);
				}
			}else{
				Reporter.logEvent(Status.FAIL, "Check if the record is created in Event table for the created event",
						"Row/Record is not created for the event id", false);
			}
		}

	}
	
	public void extractOutput()
	{
		EVENT_N_SUBJECT_ID_0 = doc.getElementsByTagName("EVENT_N_SUB_SUBJECT_ID_0").item(0).getTextContent();
		Reporter.logEvent(Status.INFO, "Verify the Group client Id", EVENT_N_SUBJECT_ID_0, false);
		String EVENT_RECVD_DATE = doc.getElementsByTagName("EVENT_RECVD_DATE_0").item(0).getTextContent();
		Reporter.logEvent(Status.INFO, "Verify the Event create date", EVENT_RECVD_DATE, false);
	}
}