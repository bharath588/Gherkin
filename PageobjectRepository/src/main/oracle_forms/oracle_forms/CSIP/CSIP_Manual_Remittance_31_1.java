package pageobject.CSIP;

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

public class CSIP_Manual_Remittance_31_1 {

	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/CSIP_Manual_Remittance_31_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	/**
	 * Input Parameters
	 */
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String BHRM1_BKA_CODE_0;
	String BHRM1_AMOUNT_0;
	String BHRM1_BHSX_ID_0;
	String RMNC1_GA_ID_0;
	String RMNC1_AMOUNT_0;

	/**
	 * Output parameters
	 * 
	 */
	String RMNC_ID;
	String EFFDATE;
	String AMOUNT;

	public String getRMNC_ID() {
		return RMNC_ID;
	}

	public String getEFFDATE() {
		return EFFDATE;
	}

	public String getAMOUNT() {
		return AMOUNT;
	}

	public String getBHRM1_AMOUNT_0() {
		return BHRM1_AMOUNT_0;
	}

	public String getCONTROL_SELECTION_0() {
		return CONTROL_SELECTION_0;
	}

	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}

	public String getLOGON_DATABASE() {
		return LOGON_DATABASE;
	}

	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}

	public String getLOGON_PASSWORD() {
		return LOGON_PASSWORD;
	}

	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}

	public String getLOGON_USERNAME() {
		return LOGON_USERNAME;
	}

	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}

	public String getBHRM1_BKA_CODE_0() {
		return BHRM1_BKA_CODE_0;
	}

	public void setBHRM1_BKA_CODE_0(String bHRM1_BKA_CODE_0) {
		BHRM1_BKA_CODE_0 = bHRM1_BKA_CODE_0;
	}

	public void setBHRM1_AMOUNT_0(String bHRM1_AMOUNT_0) {
		BHRM1_AMOUNT_0 = bHRM1_AMOUNT_0;
	}

	public String getBHRM1_BHSX_ID_0() {
		return BHRM1_BHSX_ID_0;
	}

	public void setBHRM1_BHSX_ID_0(String bHRM1_BHSX_ID_0) {
		BHRM1_BHSX_ID_0 = bHRM1_BHSX_ID_0;
	}

	public String getRMNC1_GA_ID_0() {
		return RMNC1_GA_ID_0;
	}

	public void setRMNC1_GA_ID_0(String rMNC1_GA_ID_0) {
		RMNC1_GA_ID_0 = rMNC1_GA_ID_0;
	}

	public String getRMNC1_AMOUNT_0() {
		return RMNC1_AMOUNT_0;
	}

	public void setRMNC1_AMOUNT_0(String rMNC1_AMOUNT_0) {
		RMNC1_AMOUNT_0 = rMNC1_AMOUNT_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setBHRM1_AMOUNT_0(Stock.GetParameterValue("BHRM1_AMOUNT_0"));
		this.setBHRM1_BHSX_ID_0(Stock.GetParameterValue("BHRM1_BHSX_ID_0"));
		this.setBHRM1_BKA_CODE_0(Stock.GetParameterValue("BHRM1_BKA_CODE_0"));
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
				+ "&BHRM1_BKA_CODE_0="
				+ BHRM1_BKA_CODE_0
				+ "&BHRM1_AMOUNT_0="
				+ BHRM1_AMOUNT_0
				+ "&BHRM1_BHSX_ID_0="
				+ BHRM1_BHSX_ID_0
				+ "&RMNC1_GA_ID_0="
				+ RMNC1_GA_ID_0
				+ "&RMNC1_AMOUNT_0="
				+ RMNC1_AMOUNT_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for CSIP service",
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
			Reporter.logEvent(Status.PASS, "Run CSIP service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CSIP service",
					"Running Failed:", false);
		}
	}

	public String validateOutput() throws SQLException {
		String Error = doc.getElementsByTagName("Error").item(0)
				.getTextContent();

		if (Error.isEmpty()) {
			Reporter.logEvent(Status.PASS,
					"Validate response - Error is empty", "Error tag is empty",
					false);
			Reporter.logEvent(
					Status.INFO,
					"Values From Response which will be used in service CSIP_Manual_Remittance_41: ",
					"AMOUNT: "
							+ doc.getElementsByTagName("RMNC1_AMOUNT_0")
									.item(0).getTextContent().trim()
							+ "\nEFFDATE: "
							+ doc.getElementsByTagName("RMNC1_CASH_EFFDATE_0")
									.item(0).getTextContent()
							+ "\nRMNC_ID: "
							+ doc.getElementsByTagName("RMNC1_ID_0").item(0)
									.getTextContent(), false);

			// Updated by Mamta

			this.RMNC_ID = doc.getElementsByTagName("RMNC1_ID_0").item(0)
					.getTextContent();
			this.EFFDATE = doc.getElementsByTagName("RMNC1_CASH_EFFDATE_0")
					.item(0).getTextContent();
			this.AMOUNT = doc.getElementsByTagName("RMNC1_AMOUNT_0").item(0)
					.getTextContent();
			System.out.println("Remit ID: " + this.RMNC_ID + "\nEFFDATE: "
					+ this.EFFDATE + "\nAMOUNT: " + this.AMOUNT.trim());

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);

		}
		return this.RMNC_ID;
	}

	public void validateInDatabase() throws SQLException {
		String rmnc_id = doc.getElementsByTagName("RMNC1_ID_0").item(0)
				.getTextContent();
		double amnt = Double.parseDouble(doc
				.getElementsByTagName("RMNC1_AMOUNT_0").item(0)
				.getTextContent().trim());

		double amnt_db = 0;

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForCSIP31")[1], rmnc_id);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			while (queryResultSet.next()) {
				Reporter.logEvent(
						Status.PASS,
						"Validating existence of data in the Database",
						"Record exists in the Database\nTable Name: REMIT_NOTICE",
						false);
				amnt_db = Double
						.parseDouble(queryResultSet.getString("AMOUNT"));

				Reporter.logEvent(
						Status.INFO,
						"Values from Database",
						"ID: "
								+ queryResultSet.getString("ID")
								+ "\nEV_ID: "
								+ queryResultSet.getString("EV_ID")
								+ "\nBHRM_ID: "
								+ queryResultSet.getString("BHRM_ID")
								+ "\nSTATUS_CODE: "
								+ queryResultSet
										.getString("BALANCE_STATUS_CODE")
								+ "\nAMOUNT: "
								+ queryResultSet.getString("AMOUNT")
								+ "\nEFFDATE: "
								+ queryResultSet.getString("CASH_EFFDATE"),
						false);
			}
			if (Double.compare(amnt, amnt_db) == 0) {
				Reporter.logEvent(
						Status.PASS,
						"Comapring and Validating AMOUNT from Response and Database",
						"Both the values are same as expected"
								+ "\nFrom Response: " + amnt
								+ "\nFrom Database: " + amnt_db, false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Comapring and Validating RECORD TYPE from Response and Database",
						"Values are not same as expected", false);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"No Record exists in the Database", false);
		}

	}
}
