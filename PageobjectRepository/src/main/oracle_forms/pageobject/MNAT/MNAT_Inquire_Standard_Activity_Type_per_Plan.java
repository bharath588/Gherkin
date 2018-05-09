package pageobject.MNAT;

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

public class MNAT_Inquire_Standard_Activity_Type_per_Plan {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/MNAT_Inquire_Standard_Activity_Type_per_Plan";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String PARENT_ACTY_CODE_0;

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

	public void setPARENT_ACTY_CODE_0(String pARENT_ACTY_CODE_0) {
		PARENT_ACTY_CODE_0 = pARENT_ACTY_CODE_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setPARENT_ACTY_CODE_0(Stock
				.GetParameterValue("PARENT_ACTY_CODE_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&PARENT_ACTY_CODE_0="
				+ PARENT_ACTY_CODE_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for MNAT service",
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
			Reporter.logEvent(Status.PASS, "Run MNAT service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run MNAT service",
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
					"PARENT_ACTY_DESCR_0: "
							+ doc.getElementsByTagName("PARENT_ACTY_DESCR_0")
									.item(0).getTextContent()
							+ "\nPARENT_ACTY_TYPE_CODE_0: "
							+ doc.getElementsByTagName(
									"PARENT_ACTY_TYPE_CODE_0").item(0)
									.getTextContent()
							+ "\nPARENT_ACTY_QUAL_FREQ_CODE_0: "
							+ doc.getElementsByTagName(
									"PARENT_ACTY_QUAL_FREQ_CODE_0").item(0)
									.getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);

		}
	}

	public void validateInDatabase() throws SQLException {

		String descr = doc.getElementsByTagName("PARENT_ACTY_DESCR_0").item(0)
				.getTextContent();
		String freqCode = doc
				.getElementsByTagName("PARENT_ACTY_QUAL_FREQ_CODE_0").item(0)
				.getTextContent();
		String typeCode = doc.getElementsByTagName("PARENT_ACTY_TYPE_CODE_0")
				.item(0).getTextContent();

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForMNAT")[1], PARENT_ACTY_CODE_0,
				descr);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database",
					"Record exists in the Database", false);

			while (queryResultSet.next()) {
				String descr_DB = queryResultSet.getString("DESCR");
				String freqCode_DB = queryResultSet.getString("QUAL_FREQ_CODE");
				String typeCode_DB = queryResultSet.getString("TYPE_CODE");

				if (descr.equals(descr_DB) && freqCode.equals(freqCode_DB)
						&& typeCode.equals(typeCode_DB)) {
					Reporter.logEvent(Status.PASS,
							"Comparing data from the Database and Auraplayer",
							"Data from the Database and Auraplayer matches",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Comparing data from the Database and Auraplayer",
							"Data from the Database and Auraplayer does not match",
							false);
				}
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}

	}
}
