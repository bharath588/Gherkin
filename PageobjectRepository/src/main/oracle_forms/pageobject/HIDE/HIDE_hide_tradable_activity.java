package pageobject.HIDE;

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

public class HIDE_hide_tradable_activity {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/HIDE_hide_tradable_activity";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String eventID_DB;
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String LOGON_USERNAME_X1;
	String LOGON_PASSWORD_X1;
	String LOGON_DATABASE_X1;
	String CONTROL_CTRL_EV_ID_0;
	String CONTROL_CTRL_NARRATIVE_0;

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

	public void setLOGON_USERNAME_X1(String lOGON_USERNAME_X1) {
		LOGON_USERNAME_X1 = lOGON_USERNAME_X1;
	}

	public void setLOGON_PASSWORD_X1(String lOGON_PASSWORD_X1) {
		LOGON_PASSWORD_X1 = lOGON_PASSWORD_X1;
	}

	public void setLOGON_DATABASE_X1(String lOGON_DATABASE_X1) {
		LOGON_DATABASE_X1 = lOGON_DATABASE_X1;
	}

	public void setCONTROL_CTRL_EV_ID_0(String cONTROL_CTRL_EV_ID_0) {
		CONTROL_CTRL_EV_ID_0 = cONTROL_CTRL_EV_ID_0;
	}

	public void setCONTROL_CTRL_NARRATIVE_0(String cONTROL_CTRL_NARRATIVE_0) {
		CONTROL_CTRL_NARRATIVE_0 = cONTROL_CTRL_NARRATIVE_0;
	}

	public void preValidateInDatabase() throws SQLException {

		ResultSet queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForHIDEPreReq")[1]);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database for Query",
					"Record exists in the Database\nTable for Query", false);
			while (queryResultSet.next()) {
				eventID_DB = queryResultSet.getString("EV_ID");
				Reporter.logEvent(Status.INFO,
						"Fetching Event ID from Database for Query",
						"Event ID is: " + eventID_DB
								+ " in Database\nTable for Query", false);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database for Query",
					"Record doesn't exists in the Database for Query", false);
		}
	}

	public void setServiceParameters() throws SQLException {

		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_USERNAME_X1(Stock.GetParameterValue("LOGON_USERNAME_X1"));
		this.setLOGON_PASSWORD_X1(Stock.GetParameterValue("LOGON_PASSWORD_X1"));
		this.setLOGON_DATABASE_X1(Stock.GetParameterValue("LOGON_DATABASE_X1"));
		this.CONTROL_CTRL_EV_ID_0 = eventID_DB;
		this.setCONTROL_CTRL_NARRATIVE_0(Stock
				.GetParameterValue("CONTROL_CTRL_NARRATIVE_0"));

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
				+ "&CONTROL_CTRL_EV_ID_0="
				+ CONTROL_CTRL_EV_ID_0
				+ "&CONTROL_CTRL_NARRATIVE_0="
				+ CONTROL_CTRL_NARRATIVE_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for HIDE service",
				this.queryString.replaceAll("&", "\n"), false);

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
			Reporter.logEvent(Status.PASS, "Run HIDE service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run HIDE service",
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
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException {

		ResultSet queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForHIDE")[1], eventID_DB);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database for Query",
					"Record exists in the Database\nTable for Query", false);
			if (queryResultSet.next()) {
				String waitPullCode_DB = queryResultSet
						.getString("WAIT_PULL_CODE");
				String waitPullSDIOID_DB = queryResultSet
						.getString("WAIT_PULL_SDIO_ID");
				String ivplID_DB = queryResultSet.getString("IVPL_ID");
				String poolPullEVID_DB = queryResultSet
						.getString("POOL_PULL_EV_ID");
				System.out.println("Values From DB: " + waitPullCode_DB
						+ waitPullSDIOID_DB + ivplID_DB + poolPullEVID_DB);
				if (waitPullCode_DB == null && waitPullSDIOID_DB == null
						&& ivplID_DB.equals("0") && poolPullEVID_DB.equals("0")) {
					Reporter.logEvent(Status.PASS,
							"Values from DB for Permanent HIDE",
							"Values from Database: " + "WAIT_PULL_CODE: "
									+ waitPullCode_DB + "\nWAIT_PULL_SDIO_ID: "
									+ waitPullSDIOID_DB + "\nIVPL_ID: "
									+ ivplID_DB + "\nPOOL_PULL_EV_ID: "
									+ poolPullEVID_DB + " are correct.", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Values from DB for Permanent HIDE",
							"Values from Database: " + "WAIT_PULL_CODE: "
									+ waitPullCode_DB + "\nWAIT_PULL_SDIO_ID: "
									+ waitPullSDIOID_DB + "\nIVPL_ID: "
									+ ivplID_DB + "\nPOOL_PULL_EV_ID: "
									+ poolPullEVID_DB + " are correct.", false);
				}

			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database for Query",
					"Record doesn't exists in the Database for Query", false);
		}
	}
}
