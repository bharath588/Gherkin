package pageobject.BATR;

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

public class BATR_Batch_Remit {

	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/BATR_BATCH_REMIT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String BHRM1_BKA_CODE_LOV1;
	String BHRM1_AMOUNT_0;
	String BHRM1_BHSX_ID_LOV0;
	String RMNC1_GA_ID_0;
	String RMNC1_AMOUNT_0;

	String RMNC1_ID_0;
	String RMNC1_CASH_EFFDATE_0;

	public String getRMNC1_ID_0() {
		return RMNC1_ID_0;
	}

	public String getRMNC1_CASH_EFFDATE_0() {
		return RMNC1_CASH_EFFDATE_0;
	}

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

	public void setBHRM1_BKA_CODE_LOV1(String bHRM1_BKA_CODE_LOV1) {
		BHRM1_BKA_CODE_LOV1 = bHRM1_BKA_CODE_LOV1;
	}

	public void setBHRM1_AMOUNT_0(String bHRM1_AMOUNT_0) {
		BHRM1_AMOUNT_0 = bHRM1_AMOUNT_0;
	}

	public String getBHRM1_AMOUNT_0() {
		return this.BHRM1_AMOUNT_0;
	}

	public void setBHRM1_BHSX_ID_LOV0(String bHRM1_BHSX_ID_LOV0) {
		BHRM1_BHSX_ID_LOV0 = bHRM1_BHSX_ID_LOV0;
	}

	public void setRMNC1_GA_ID_0(String rMNC1_GA_ID_0) {
		RMNC1_GA_ID_0 = rMNC1_GA_ID_0;
	}

	public void setRMNC1_AMOUNT_0(String rMNC1_AMOUNT_0) {
		RMNC1_AMOUNT_0 = rMNC1_AMOUNT_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_BATR"));
		this.setBHRM1_AMOUNT_0(Stock.GetParameterValue("BHRM1_AMOUNT_0"));
		this.setBHRM1_BHSX_ID_LOV0(Stock
				.GetParameterValue("BHRM1_BHSX_ID_LOV0"));
		this.setBHRM1_BKA_CODE_LOV1(Stock
				.GetParameterValue("BHRM1_BKA_CODE_LOV1"));
		this.setRMNC1_AMOUNT_0(Stock.GetParameterValue("RMNC1_AMOUNT_0"));
		this.setRMNC1_GA_ID_0(Stock.GetParameterValue("RMNC1_GA_ID_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&BHRM1_BKA_CODE_LOV1="
				+ BHRM1_BKA_CODE_LOV1
				+ "&BHRM1_AMOUNT_0="
				+ BHRM1_AMOUNT_0
				+ "&BHRM1_BHSX_ID_LOV0="
				+ BHRM1_BHSX_ID_LOV0
				+ "&RMNC1_GA_ID_0="
				+ RMNC1_GA_ID_0
				+ "&RMNC1_AMOUNT_0="
				+ RMNC1_AMOUNT_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for BATR service",
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
			// System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run BATR service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run BATR service",
					"Running Failed:", false);
		}
	}

	public String validateOutput() throws SQLException {
		String Error = doc.getElementsByTagName("Error").item(0)
				.getTextContent();
		String rmnc_id = null;
		if (Error.isEmpty()) {
			Reporter.logEvent(Status.PASS,
					"Validate response - Error is empty", "Error tag is empty",
					false);
			Reporter.logEvent(Status.INFO, "From Response ", "REMIT ID: "
					+ doc.getElementsByTagName("RMNC1_ID_0").item(0)
							.getTextContent()
					+ "\nRMNC1_AMOUNT "
					+ doc.getElementsByTagName("RMNC1_AMOUNT_0").item(0)
							.getTextContent()
					+ "\nRMNC1_CASH_EFFDATE: "
					+ doc.getElementsByTagName("RMNC1_CASH_EFFDATE_0").item(0)
							.getTextContent(), false);
			this.RMNC1_ID_0 = doc.getElementsByTagName("RMNC1_ID_0").item(0)
					.getTextContent();
			this.RMNC1_CASH_EFFDATE_0 = doc
					.getElementsByTagName("RMNC1_CASH_EFFDATE_0").item(0)
					.getTextContent();
			rmnc_id=doc.getElementsByTagName("RMNC1_ID_0").item(0)
					.getTextContent();
			System.out.println("rmnc_id: "+rmnc_id);
			/*Mamta*/
			Reporter.logEvent(Status.INFO,
					"Passing values Remit ID and Cash EffDate", "RMNC1_ID_0: "+RMNC1_ID_0
					+"\nRMNC1_CASH_EFFDATE_0: "+RMNC1_CASH_EFFDATE_0,
					false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		return rmnc_id;
	}

	public void validateInDatabase() throws SQLException {
		String rmnc_id = doc.getElementsByTagName("RMNC1_ID_0").item(0)
				.getTextContent();
		String amnt = this.BHRM1_AMOUNT_0;
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForCSIPBatr")[1], rmnc_id, amnt);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"Record exists in the Database\nTable Name: AG_REMIT",
					false);
			while (queryResultSet.next()) {

				Reporter.logEvent(
						Status.INFO,
						"Values from Database",
						"RMNC_ID: "
								+ queryResultSet.getString("RMNC_ID")
								+ "\nAG_ID: "
								+ queryResultSet.getString("AG_ID")
								+ "\nGC_ID: "
								+ queryResultSet.getString("GC_ID")
								+ "\nSDMT_CODE: "
								+ queryResultSet.getString("SDMT_CODE")
								+ "\nAMOUNT: "
								+ queryResultSet.getString("AMOUNT")
								+ "\nEFFDATE: "
								+ queryResultSet.getString("CASH_EFFDATE")
								+ "\nSTATUS_CODE: "
								+ queryResultSet
										.getString("PROCESS_STATUS_CODE"),
						false);
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"No Record exists in the Database", false);
		}
	}

}
