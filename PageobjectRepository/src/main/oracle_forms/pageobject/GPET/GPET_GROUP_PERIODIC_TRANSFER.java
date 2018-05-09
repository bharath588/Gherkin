package pageobject.GPET;

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

public class GPET_GROUP_PERIODIC_TRANSFER {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/GPET_GROUP_PERIODIC_TRANSFER";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;
	public ResultSet queryResultSet2 = null;
	String eventID;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GROUP_GA_ID_0;

	String SCHEDULE_TRF_TYPE_CODE_LIST_0;
	String SCHEDULE_FREQ_CODE_0;
	String param9219;
	String FROM_INVOPT_REQ_PERCENT_0;
	String param9220;
	String TO_INVOPT_REQ_PERCENT_0;

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


	public void setGROUP_GA_ID_0(String gROUP_GA_ID_0) {
		GROUP_GA_ID_0 = gROUP_GA_ID_0;
	}

	public void setSCHEDULE_TRF_TYPE_CODE_LIST_0(
			String sCHEDULE_TRF_TYPE_CODE_LIST_0) {
		SCHEDULE_TRF_TYPE_CODE_LIST_0 = sCHEDULE_TRF_TYPE_CODE_LIST_0;
	}

	public void setSCHEDULE_FREQ_CODE_0(String sCHEDULE_FREQ_CODE_0) {
		SCHEDULE_FREQ_CODE_0 = sCHEDULE_FREQ_CODE_0;
	}

	public void setParam9219(String param9219) {
		this.param9219 = param9219;
	}

	public void setFROM_INVOPT_REQ_PERCENT_0(String fROM_INVOPT_REQ_PERCENT_0) {
		FROM_INVOPT_REQ_PERCENT_0 = fROM_INVOPT_REQ_PERCENT_0;
	}

	public void setParam9220(String param9220) {
		this.param9220 = param9220;
	}

	public void setTO_INVOPT_REQ_PERCENT_0(String tO_INVOPT_REQ_PERCENT_0) {
		TO_INVOPT_REQ_PERCENT_0 = tO_INVOPT_REQ_PERCENT_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGROUP_GA_ID_0(Stock
				.GetParameterValue("GROUP_GA_ID_0"));
		this.setSCHEDULE_TRF_TYPE_CODE_LIST_0(Stock
				.GetParameterValue("SCHEDULE_TRF_TYPE_CODE_LIST_0"));
		this.setSCHEDULE_FREQ_CODE_0(Stock
				.GetParameterValue("SCHEDULE_FREQ_CODE_0"));
		this.setParam9219(Stock
				.GetParameterValue("param9219"));
		this.setFROM_INVOPT_REQ_PERCENT_0(Stock
				.GetParameterValue("FROM_INVOPT_REQ_PERCENT_0"));
		this.setParam9220(Stock
				.GetParameterValue("param9220"));
		this.setTO_INVOPT_REQ_PERCENT_0(Stock
				.GetParameterValue("TO_INVOPT_REQ_PERCENT_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&GROUP_GA_ID_0="
				+ GROUP_GA_ID_0
				+ "&SCHEDULE_TRF_TYPE_CODE_LIST_0="
				+ SCHEDULE_TRF_TYPE_CODE_LIST_0
				+ "&SCHEDULE_FREQ_CODE_0="
				+ SCHEDULE_FREQ_CODE_0
				+ "&param9219="
				+ param9219
				+ "&FROM_INVOPT_REQ_PERCENT_0="
				+ FROM_INVOPT_REQ_PERCENT_0
				+ "&param9220="
				+ param9220
				+ "&TO_INVOPT_REQ_PERCENT_0="
				+ TO_INVOPT_REQ_PERCENT_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for GPET service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println("serviceURL: " + serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			// System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run GPET service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run GPET service",
					"Running Failed:", false);
		}
	}

	public void validateOutput() throws SQLException {
		String Error = doc.getElementsByTagName("Error").item(0)
				.getTextContent();

		if (Error.isEmpty()) {
			Reporter.logEvent(Status.PASS,
					"Validate response - Error is empty", "Error tag is empty",
					false);
			String popUp_Msg=doc.getElementsByTagName("PopupMessages").item(0).getTextContent();
			System.out.println("popUp_Msg: "+popUp_Msg.length());
			System.out.println("popUp_Msg: "+popUp_Msg);
			eventID=popUp_Msg.substring(popUp_Msg.length()-29, popUp_Msg.length()-18);
			System.out.println("eventID: "+eventID);
			Reporter.logEvent(Status.INFO,
					"Fetching data from Auraplayer Response: ",
					"POP UP Messages: "+popUp_Msg,
					false);
			

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);

		}
	}

	public void validateInDatabase() throws SQLException {
		
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForGPET")[1],"98991");

		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database",
					"Record exists in the Database", false);
			
			queryResultSet1 = DB.executeQuery(General.dbInstance,
					Stock.getTestQuery("queryForGPETDelete")[1],"98991");
			Reporter.logEvent(Status.INFO,
					"Deleting data in the Database",
					"Record deleted in the Database", false);

			
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}
		
		queryResultSet2 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForGPETEvent")[1],eventID);

		if (DB.getRecordSetCount(queryResultSet2) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"Event ID: "+eventID+" exists in the Database", false);

			
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Event ID: "+eventID+" doesn't exists in the Database", false);
		}

	}
}
