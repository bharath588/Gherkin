package pageobject.VMLS;

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

public class VMLS_Create_Record {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/VMLS_Create_Record";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	public ResultSet queryResultSet1 = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String NY_VIDEO_SSN_0;

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

	public void setNY_VIDEO_SSN_0(String nY_VIDEO_SSN_0) {
		NY_VIDEO_SSN_0 = nY_VIDEO_SSN_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setNY_VIDEO_SSN_0(Stock.GetParameterValue("NY_VIDEO_SSN_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&NY_VIDEO_SSN_0="
				+ NY_VIDEO_SSN_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for VMLS service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	//As DB updation is taking time, so removing DB validation methods.
	/*public void deleteFromDatabase() throws SQLException {

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForVMLS")[1], this.NY_VIDEO_SSN_0);

		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(
					Status.INFO,
					"Validating existence of data in the Database\nTable Name: D_INST",
					"There exist a record in the Database for SSN: "
							+ this.NY_VIDEO_SSN_0, false);

			queryResultSet1 = DB.executeQuery(General.dbInstance,
					Stock.getTestQuery("queryForVMLSDelete")[1],
					this.NY_VIDEO_SSN_0);
			
				Reporter.logEvent(Status.INFO,
						"Validating existence of data in the Database",
						"Record deleted from the Database", false);

		}
		else{
			Reporter.logEvent(
					Status.INFO,
					"Validating existence of data in the Database\nTable Name: D_INST",
					"There is no record in the Database for SSN: "
							+ this.NY_VIDEO_SSN_0, false);
		}
	}*/

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
			Reporter.logEvent(Status.PASS, "Run VMLS service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run VMLS service",
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
					"Values from Response",
					"NY_VIDEO_GA_ID_0: "
							+ doc.getElementsByTagName("NY_VIDEO_GA_ID_0")
									.item(0).getTextContent()
							+ "\nNY_VIDEO_REQUEST_TYPE_0: "
							+ doc.getElementsByTagName(
									"NY_VIDEO_REQUEST_TYPE_0").item(0)
									.getTextContent()
							+ "\nNY_VIDEO_REQUEST_DATE_0: "
							+ doc.getElementsByTagName(
									"NY_VIDEO_REQUEST_DATE_0").item(0)
									.getTextContent()
							+ "\nNYVI_HDR1_TODAYS_DATE1_0: "
							+ doc.getElementsByTagName(
									"NYVI_HDR1_TODAYS_DATE1_0").item(0)
									.getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	/*public void validateInDatabase() throws SQLException {

		String ga_ID_resp = doc.getElementsByTagName("NY_VIDEO_GA_ID_0")
				.item(0).getTextContent();
		String request_Type_resp = doc
				.getElementsByTagName("NY_VIDEO_REQUEST_TYPE_0").item(0)
				.getTextContent();
		String request_Date_resp = doc
				.getElementsByTagName("NY_VIDEO_REQUEST_DATE_0").item(0)
				.getTextContent();

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForVMLS")[1], this.NY_VIDEO_SSN_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(
					Status.PASS,
					"Validating existence of data in the Database\nTable Name: D_INST",
					"Record exists in the Database", false);
			while (queryResultSet.next()) {
				String ga_ID_DB = queryResultSet.getString("GA_ID");
				String request_Type_DB = queryResultSet
						.getString("REQUEST_TYPE");
				String request_Date_DB = queryResultSet.getString("DPDATE");
				Reporter.logEvent(Status.INFO, "From Database: ", "ID: "
						+ queryResultSet.getString("REQUEST_TYPE")
						+ "\nGA_ID: " + queryResultSet.getString("GA_ID")
						+ "\nDPDATE: " + queryResultSet.getString("DPDATE"),
						false);
				if (ga_ID_resp.equals(ga_ID_DB)
						&& request_Type_resp.equals(request_Type_DB)
						&& request_Date_resp.equals(request_Date_DB)) {
					Reporter.logEvent(
							Status.PASS,
							"Matching the data in the Database\nTable Name: D_INST with Aura Player Response",
							"Record in the Database matches with Aura Player Response",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Matching the data in the Database\nTable Name: D_INST with Aura Player Response",
							"Record in the Database does not match with Aura Player Response",
							false);
				}
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"No Record exists in the Database", false);
		}
	}*/
}
