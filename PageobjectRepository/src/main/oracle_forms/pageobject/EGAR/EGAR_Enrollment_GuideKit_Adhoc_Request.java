package pageobject.EGAR;

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

public class EGAR_Enrollment_GuideKit_Adhoc_Request {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/EGAR_Enrollment_GuideKit_Adhoc_Request";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String LOGON_DATABASE_X1;
	String LOGON_USERNAME_X1;
	String LOGON_PASSWORD_X1;
	String ISIS_FEED_BATCH_TODO_GA_ID_0;
	String PROCESS_CODE_LOV;

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

	public void setLOGON_DATABASE_X1(String lOGON_DATABASE_X1) {
		LOGON_DATABASE_X1 = lOGON_DATABASE_X1;
	}

	public void setLOGON_USERNAME_X1(String lOGON_USERNAME_X1) {
		LOGON_USERNAME_X1 = lOGON_USERNAME_X1;
	}

	public void setLOGON_PASSWORD_X1(String lOGON_PASSWORD_X1) {
		LOGON_PASSWORD_X1 = lOGON_PASSWORD_X1;
	}

	public void setISIS_FEED_BATCH_TODO_GA_ID_0(
			String iSIS_FEED_BATCH_TODO_GA_ID_0) {
		ISIS_FEED_BATCH_TODO_GA_ID_0 = iSIS_FEED_BATCH_TODO_GA_ID_0;
	}

	public void setPROCESS_CODE_LOV(String pROCESS_CODE_LOV) {
		PROCESS_CODE_LOV = pROCESS_CODE_LOV;
	}

	

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE_X1(Stock.GetParameterValue("LOGON_DATABASE_X1"));
		this.setLOGON_USERNAME_X1(Stock.GetParameterValue("LOGON_USERNAME_X1"));
		this.setLOGON_PASSWORD_X1(Stock.GetParameterValue("LOGON_PASSWORD_X1"));
		this.setISIS_FEED_BATCH_TODO_GA_ID_0(Stock
				.GetParameterValue("ISIS_FEED_BATCH_TODO_GA_ID_0"));
		this.setPROCESS_CODE_LOV(Stock.GetParameterValue("PROCESS_CODE_LOV"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&LOGON_USERNAME_X1="
				+ LOGON_USERNAME_X1
				+ "&LOGON_PASSWORD_X1="
				+ LOGON_PASSWORD_X1
				+ "&LOGON_DATABASE_X1="
				+ LOGON_DATABASE_X1
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&ISIS_FEED_BATCH_TODO_GA_ID_0="
				+ ISIS_FEED_BATCH_TODO_GA_ID_0
				+ "&PROCESS_CODE_LOV="
				+ PROCESS_CODE_LOV
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for EGAR service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void deleteInDatabase() throws SQLException {

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForEGAR")[1],
				ISIS_FEED_BATCH_TODO_GA_ID_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(
					Status.INFO,
					"Validating existence of data in the Database",
					"Record is present into isis_feed_batch_todo for the process code entered.",
					false);
			queryResultSet1 = DB.executeQuery(General.dbInstance,
					Stock.getTestQuery("queryForEGARDelete")[1],
					ISIS_FEED_BATCH_TODO_GA_ID_0);
			Reporter.logEvent(Status.INFO, "Deleting data from the Database",
					"Deleting data from the Database", false);

		} else {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}

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
			// System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run EGAR service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run EGAR service",
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
			Reporter.logEvent(
					Status.INFO,
					"Values from Response: ",
					"PopupMessages: "
							+ doc.getElementsByTagName("PopupMessages").item(0)
									.getTextContent()
							+ "\nStatusBarMessages: "
							+ doc.getElementsByTagName("StatusBarMessages")
									.item(0).getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);

		}
	}

	public void validateInDatabase() throws SQLException {

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForEGAR")[1],
				ISIS_FEED_BATCH_TODO_GA_ID_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(
					Status.PASS,
					"Validating existence of data in the Database",
					"A record is inserted into isis_feed_batch_todo for the process code entered.",
					false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}

	}
}
