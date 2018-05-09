package pageobject.TXSS;

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

public class TXSS_Insert {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/TXSS_INSERT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String TAX_ASSR_CODE_0;
	String TAX_ASSR_DESCR_0;

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

	

	

	public void setTAX_ASSR_CODE_0(String tAX_ASSR_CODE_0) {
		TAX_ASSR_CODE_0 = tAX_ASSR_CODE_0;
	}

	public void setTAX_ASSR_DESCR_0(String tAX_ASSR_DESCR_0) {
		TAX_ASSR_DESCR_0 = tAX_ASSR_DESCR_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setTAX_ASSR_CODE_0(Stock.GetParameterValue("TAX_ASSR_CODE_0"));
		this.setTAX_ASSR_DESCR_0(Stock.GetParameterValue("TAX_ASSR_DESCR_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&TAX_ASSR_CODE_0="
				+ TAX_ASSR_CODE_0
				+ "&TAX_ASSR_DESCR_0="
				+ TAX_ASSR_DESCR_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for TXSS service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void deleteInDatabase() throws SQLException {

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForTXSS")[1],TAX_ASSR_CODE_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(
					Status.INFO,
					"Validating existence of data in the Database",
					"Record is present into isis_feed_batch_todo for the process code entered.",
					false);
			queryResultSet1 = DB.executeQuery(General.dbInstance,
					Stock.getTestQuery("queryForTXSSDelete")[1],TAX_ASSR_CODE_0);
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
			Reporter.logEvent(Status.PASS, "Run TXSS service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run TXSS service",
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
					"CB_FORM_NAME_0: "
							+ doc.getElementsByTagName("CB_FORM_NAME_0").item(0)
									.getTextContent()
							+ "\nTAX_ASSR_LEVEL_CODE_0: "
							+ doc.getElementsByTagName("TAX_ASSR_LEVEL_CODE_0")
									.item(1).getTextContent()
									+ "\nTAX_ASSR_FOREIGN_DOMESTIC_CODE_0: "
							+ doc.getElementsByTagName("TAX_ASSR_FOREIGN_DOMESTIC_CODE_0")
									.item(1).getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);

		}
	}

	public void validateInDatabase() throws SQLException {

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForTXSS")[1],TAX_ASSR_CODE_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(
					Status.PASS,
					"Validating existence of data in the Database",
					"Row inserted into tax_assr table",
					false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}

	}
}
