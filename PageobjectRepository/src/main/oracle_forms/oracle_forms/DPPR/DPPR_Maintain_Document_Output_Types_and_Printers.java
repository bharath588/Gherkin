package pageobject.DPPR;

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

public class DPPR_Maintain_Document_Output_Types_and_Printers {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/DPPR_Maintain_Document_Output_Types_and_Printers";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String PRIN1_CODE_0;

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

	public void setPRIN1_CODE_0(String pRIN1_CODE_0) {
		PRIN1_CODE_0 = pRIN1_CODE_0;
	}

	public void setServiceParameters() throws SQLException {

		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setPRIN1_CODE_0(Stock.GetParameterValue("PRIN1_CODE_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&PRIN1_CODE_0="
				+ PRIN1_CODE_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for DPPR service",
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
			Reporter.logEvent(Status.PASS, "Run DPPR service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DPPR service",
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
					"Values From Response: ",
					"DMOPTY1_CODE_0: "
							+ doc.getElementsByTagName("DMOPTY1_CODE_0")
									.item(0).getTextContent()
							+ "\nPRIN1_DESCR_0: "
							+ doc.getElementsByTagName("PRIN1_DESCR_0").item(0)
									.getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException {
		String dmoptyCodeResp = doc.getElementsByTagName("DMOPTY1_CODE_0")
				.item(0).getTextContent();
		String descrResp = doc.getElementsByTagName("PRIN1_DESCR_0").item(0)
				.getTextContent();

		ResultSet queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForDPPR")[1], this.PRIN1_CODE_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database for Query",
					"Record exists in the Database\nTable for Query", false);
			while (queryResultSet.next()) {
				String dmoptyCodeDB = queryResultSet.getString("DMOPTY_CODE");
				String descrDB = queryResultSet.getString("DESCR");

				if (dmoptyCodeDB.equals(dmoptyCodeResp)
						&& descrDB.equals(descrResp)) {
					Reporter.logEvent(
							Status.PASS,
							"Comparing values from DB and Aura Player Response",
							"Values from Database: " + dmoptyCodeDB + "\t"
									+ descrDB + "And values from response: "
									+ dmoptyCodeResp + "\t" + descrResp
									+ " are same.", false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Comparing values from DB and Aura Player Response",
							"Values from Database: " + dmoptyCodeDB + "\t"
									+ descrDB + "And values from response: "
									+ dmoptyCodeResp + "\t" + descrResp
									+ " are not same.", false);
				}

			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database for Query",
					"Record doesn't exists in the Database for Query", false);
		}
	}
}
