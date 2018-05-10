package pageobject.UDFR;

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

public class UDFR_Date_Valid {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/UDFR_DATE_VALID";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet1;

	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0;
	String CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_GA_ID_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_SSN_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_STDT_CODE_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_STATUS_CODE_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_CHANGE_CODE_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_EFFDATE_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_DPDATE_TIME_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_INITIAL_DFRL_IND_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_PERCENTAGE_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_PCT_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_MAXIMUM_PCT_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_FREQ_0;
	String UPLOAD_DEFERRAL_DATA_BLOCK_NEXT_SCHEDULE_DATE_0;

	

	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}

	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}

	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}

	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}

	public void setCONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0(
			String cONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0) {
		CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0 = cONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0;
	}

	public void setCONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0(
			String cONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0) {
		CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0 = cONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_GA_ID_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_GA_ID_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_GA_ID_0 = uPLOAD_DEFERRAL_DATA_BLOCK_GA_ID_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_SSN_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_SSN_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_SSN_0 = uPLOAD_DEFERRAL_DATA_BLOCK_SSN_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_STDT_CODE_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_STDT_CODE_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_STDT_CODE_0 = uPLOAD_DEFERRAL_DATA_BLOCK_STDT_CODE_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_STATUS_CODE_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_STATUS_CODE_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_STATUS_CODE_0 = uPLOAD_DEFERRAL_DATA_BLOCK_STATUS_CODE_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_CHANGE_CODE_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_CHANGE_CODE_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_CHANGE_CODE_0 = uPLOAD_DEFERRAL_DATA_BLOCK_CHANGE_CODE_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_EFFDATE_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_EFFDATE_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_EFFDATE_0 = uPLOAD_DEFERRAL_DATA_BLOCK_EFFDATE_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_DPDATE_TIME_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_DPDATE_TIME_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_DPDATE_TIME_0 = uPLOAD_DEFERRAL_DATA_BLOCK_DPDATE_TIME_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_INITIAL_DFRL_IND_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_INITIAL_DFRL_IND_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_INITIAL_DFRL_IND_0 = uPLOAD_DEFERRAL_DATA_BLOCK_INITIAL_DFRL_IND_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_PERCENTAGE_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_PERCENTAGE_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_PERCENTAGE_0 = uPLOAD_DEFERRAL_DATA_BLOCK_PERCENTAGE_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_PCT_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_PCT_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_PCT_0 = uPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_PCT_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_MAXIMUM_PCT_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_MAXIMUM_PCT_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_MAXIMUM_PCT_0 = uPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_MAXIMUM_PCT_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_FREQ_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_FREQ_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_FREQ_0 = uPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_FREQ_0;
	}

	public void setUPLOAD_DEFERRAL_DATA_BLOCK_NEXT_SCHEDULE_DATE_0(
			String uPLOAD_DEFERRAL_DATA_BLOCK_NEXT_SCHEDULE_DATE_0) {
		UPLOAD_DEFERRAL_DATA_BLOCK_NEXT_SCHEDULE_DATE_0 = uPLOAD_DEFERRAL_DATA_BLOCK_NEXT_SCHEDULE_DATE_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0(Stock
				.GetParameterValue("CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0"));
		this.setCONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0(Stock
				.GetParameterValue("CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_GA_ID_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_GA_ID_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_SSN_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_SSN_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_STDT_CODE_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_STDT_CODE_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_STATUS_CODE_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_STATUS_CODE_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_CHANGE_CODE_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_CHANGE_CODE_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_EFFDATE_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_EFFDATE_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_DPDATE_TIME_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_DPDATE_TIME_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_PERCENTAGE_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_PERCENTAGE_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_INITIAL_DFRL_IND_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_INITIAL_DFRL_IND_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_PCT_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_PCT_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_MAXIMUM_PCT_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_MAXIMUM_PCT_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_FREQ_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_FREQ_0"));
		this.setUPLOAD_DEFERRAL_DATA_BLOCK_NEXT_SCHEDULE_DATE_0(Stock
				.GetParameterValue("UPLOAD_DEFERRAL_DATA_BLOCK_NEXT_SCHEDULE_DATE_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0="
				+ CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0
				+ "&CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0="
				+ CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_GA_ID_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_GA_ID_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_SSN_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_SSN_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_STDT_CODE_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_STDT_CODE_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_STATUS_CODE_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_STATUS_CODE_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_CHANGE_CODE_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_CHANGE_CODE_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_EFFDATE_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_EFFDATE_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_DPDATE_TIME_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_DPDATE_TIME_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_PERCENTAGE_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_PERCENTAGE_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_INITIAL_DFRL_IND_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_INITIAL_DFRL_IND_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_PCT_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_PCT_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_MAXIMUM_PCT_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_MAXIMUM_PCT_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_FREQ_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_INCREASE_FREQ_0
				+ "&UPLOAD_DEFERRAL_DATA_BLOCK_NEXT_SCHEDULE_DATE_0="
				+ UPLOAD_DEFERRAL_DATA_BLOCK_NEXT_SCHEDULE_DATE_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO,
				"Prepare test data for UDFR_DATE_VALID service",
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
			Reporter.logEvent(Status.PASS, "Run UDFR service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run UDFR service",
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
					Status.PASS,
					"From Response: ",
					"ALL_UPLOAD_REQUEST_BLOCK_ID_0: "
							+ doc.getElementsByTagName(
									"ALL_UPLOAD_REQUEST_BLOCK_ID_0").item(0)
									.getTextContent()
							+ "\nUPLOAD_DEFERRAL_REQUEST_BLOCK_SERVICE_REQUEST_NBR_0: "
							+ doc.getElementsByTagName(
									"UPLOAD_DEFERRAL_REQUEST_BLOCK_SERVICE_REQUEST_NBR_0")
									.item(0).getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}

	}

	public void validateInDatabase() throws SQLException {
		String upload_Req_ID=doc.getElementsByTagName("ALL_UPLOAD_REQUEST_BLOCK_ID_0").item(0).getTextContent();
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForUDFRDateValid1")[1],upload_Req_ID);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(
					Status.PASS,
					"Validating existence of data in the Database" + "\nD_INST",
					"Record exists in the Database.", false);
			while (queryResultSet.next()) {
				String gaID_DB = queryResultSet
						.getString("GA_ID");
				String SSN_DB = queryResultSet
						.getString("SSN");
				Reporter.logEvent(Status.INFO, "Values from DB: ", "GAID: "+gaID_DB+" and SSN: "+SSN_DB, false);
				if (this.UPLOAD_DEFERRAL_DATA_BLOCK_GA_ID_0
						.equals(gaID_DB)
						&& this.UPLOAD_DEFERRAL_DATA_BLOCK_SSN_0
								.equals(SSN_DB)) {
					Reporter.logEvent(
							Status.PASS,
							"Comparing the data in the Database with Service"
									+ "\nD_INST",
							"Record in the Database matches with the input parameters.",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Comparing the data in the Database with Service"
									+ "\nD_INST",
							"Records in the Database do not match with the input parameters.",
							false);
				}
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"No Record exists in the Database", false);
		}
		queryResultSet1 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForUDFRDateValid2")[1],this.CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0,this.CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0);
		if (DB.getRecordSetCount(queryResultSet1) > 0) {
			Reporter.logEvent(
					Status.PASS,
					"Validating existence of data in the Database" + "\nD_INST",
					"Record exists in the Database.", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"No Record exists in the Database", false);
		}

	}
}
