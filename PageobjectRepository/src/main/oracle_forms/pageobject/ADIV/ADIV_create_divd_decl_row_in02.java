package pageobject.ADIV;

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

public class ADIV_create_divd_decl_row_in02 {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/ADIV_create_divd_decl_row_in02";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet1;
	private ResultSet queryResultSet2;

	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String DIVIDENDS_SDIO_ID_0;
	String DIVIDENDS_DECL_DATE_0;
	String DIVIDENDS_RECORD_DATE_0;
	String DIVIDENDS_EX_DIVIDEND_DATE_0;
	String DIVIDENDS_PAYABLE_DATE_0;
	String DIVIDENDS_TYPE_CODE_0;
	String DIVIDENDS_SDIO_ID_0_X1;
	String DIVIDENDS_DECL_DATE_0_X1;
	String DIVIDENDS_RECORD_DATE_0_X1;
	String DIVIDENDS_EX_DIVIDEND_DATE_0_X1;
	String DIVIDENDS_PAYABLE_DATE_0_X1;
	String DIVIDENDS_TYPE_CODE_0_X1;
	String DIVIDENDS_RATE_0;
	String DIVIDENDS_SCHED_CODE_0;
	String DIVIDENDS_SCHED_CODE_0_X1;

	

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

	public void setDIVIDENDS_SDIO_ID_0(String dIVIDENDS_SDIO_ID_0) {
		DIVIDENDS_SDIO_ID_0 = dIVIDENDS_SDIO_ID_0;
	}

	public void setDIVIDENDS_DECL_DATE_0(String dIVIDENDS_DECL_DATE_0) {
		DIVIDENDS_DECL_DATE_0 = dIVIDENDS_DECL_DATE_0;
	}

	public void setDIVIDENDS_RECORD_DATE_0(String dIVIDENDS_RECORD_DATE_0) {
		DIVIDENDS_RECORD_DATE_0 = dIVIDENDS_RECORD_DATE_0;
	}

	public void setDIVIDENDS_EX_DIVIDEND_DATE_0(String dIVIDENDS_EX_DIVIDEND_DATE_0) {
		DIVIDENDS_EX_DIVIDEND_DATE_0 = dIVIDENDS_EX_DIVIDEND_DATE_0;
	}

	public void setDIVIDENDS_PAYABLE_DATE_0(String dIVIDENDS_PAYABLE_DATE_0) {
		DIVIDENDS_PAYABLE_DATE_0 = dIVIDENDS_PAYABLE_DATE_0;
	}

	public void setDIVIDENDS_TYPE_CODE_0(String dIVIDENDS_TYPE_CODE_0) {
		DIVIDENDS_TYPE_CODE_0 = dIVIDENDS_TYPE_CODE_0;
	}

	public void setDIVIDENDS_SDIO_ID_0_X1(String dIVIDENDS_SDIO_ID_0_X1) {
		DIVIDENDS_SDIO_ID_0_X1 = dIVIDENDS_SDIO_ID_0_X1;
	}

	public void setDIVIDENDS_DECL_DATE_0_X1(String dIVIDENDS_DECL_DATE_0_X1) {
		DIVIDENDS_DECL_DATE_0_X1 = dIVIDENDS_DECL_DATE_0_X1;
	}

	public void setDIVIDENDS_RECORD_DATE_0_X1(String dIVIDENDS_RECORD_DATE_0_X1) {
		DIVIDENDS_RECORD_DATE_0_X1 = dIVIDENDS_RECORD_DATE_0_X1;
	}

	public void setDIVIDENDS_EX_DIVIDEND_DATE_0_X1(
			String dIVIDENDS_EX_DIVIDEND_DATE_0_X1) {
		DIVIDENDS_EX_DIVIDEND_DATE_0_X1 = dIVIDENDS_EX_DIVIDEND_DATE_0_X1;
	}

	public void setDIVIDENDS_PAYABLE_DATE_0_X1(String dIVIDENDS_PAYABLE_DATE_0_X1) {
		DIVIDENDS_PAYABLE_DATE_0_X1 = dIVIDENDS_PAYABLE_DATE_0_X1;
	}

	public void setDIVIDENDS_TYPE_CODE_0_X1(String dIVIDENDS_TYPE_CODE_0_X1) {
		DIVIDENDS_TYPE_CODE_0_X1 = dIVIDENDS_TYPE_CODE_0_X1;
	}

	public void setDIVIDENDS_RATE_0(String dIVIDENDS_RATE_0) {
		DIVIDENDS_RATE_0 = dIVIDENDS_RATE_0;
	}

	public void setDIVIDENDS_SCHED_CODE_0(String dIVIDENDS_SCHED_CODE_0) {
		DIVIDENDS_SCHED_CODE_0 = dIVIDENDS_SCHED_CODE_0;
	}

	public void setDIVIDENDS_SCHED_CODE_0_X1(String dIVIDENDS_SCHED_CODE_0_X1) {
		DIVIDENDS_SCHED_CODE_0_X1 = dIVIDENDS_SCHED_CODE_0_X1;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setDIVIDENDS_SDIO_ID_0(Stock
				.GetParameterValue("DIVIDENDS_SDIO_ID_0"));
		this.setDIVIDENDS_DECL_DATE_0(Stock
				.GetParameterValue("DIVIDENDS_DECL_DATE_0"));
		this.setDIVIDENDS_RECORD_DATE_0(Stock
				.GetParameterValue("DIVIDENDS_RECORD_DATE_0"));
		this.setDIVIDENDS_EX_DIVIDEND_DATE_0(Stock
				.GetParameterValue("DIVIDENDS_EX_DIVIDEND_DATE_0"));
		this.setDIVIDENDS_PAYABLE_DATE_0(Stock
				.GetParameterValue("DIVIDENDS_PAYABLE_DATE_0"));
		this.setDIVIDENDS_TYPE_CODE_0(Stock
				.GetParameterValue("DIVIDENDS_TYPE_CODE_0"));
		this.setDIVIDENDS_SDIO_ID_0_X1(Stock.GetParameterValue("DIVIDENDS_SDIO_ID_0_X1"));
		this.setDIVIDENDS_DECL_DATE_0_X1(Stock.GetParameterValue("DIVIDENDS_DECL_DATE_0_X1"));
		this.setDIVIDENDS_RECORD_DATE_0_X1(Stock.GetParameterValue("DIVIDENDS_RECORD_DATE_0_X1"));
		this.setDIVIDENDS_EX_DIVIDEND_DATE_0_X1(Stock.GetParameterValue("DIVIDENDS_EX_DIVIDEND_DATE_0_X1"));
		this.setDIVIDENDS_PAYABLE_DATE_0_X1(Stock.GetParameterValue("DIVIDENDS_PAYABLE_DATE_0_X1"));
		this.setDIVIDENDS_TYPE_CODE_0_X1(Stock.GetParameterValue("DIVIDENDS_TYPE_CODE_0_X1"));
		this.setDIVIDENDS_RATE_0(Stock.GetParameterValue("DIVIDENDS_RATE_0"));
		this.setDIVIDENDS_SCHED_CODE_0(Stock
				.GetParameterValue("DIVIDENDS_SCHED_CODE_0"));
		this.setDIVIDENDS_SCHED_CODE_0_X1(Stock
				.GetParameterValue("DIVIDENDS_SCHED_CODE_0_X1"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&DIVIDENDS_SDIO_ID_0="
				+ DIVIDENDS_SDIO_ID_0
				+ "&DIVIDENDS_DECL_DATE_0="
				+ DIVIDENDS_DECL_DATE_0
				+ "&DIVIDENDS_RECORD_DATE_0="
				+ DIVIDENDS_RECORD_DATE_0
				+ "&DIVIDENDS_EX_DIVIDEND_DATE_0="
				+ DIVIDENDS_EX_DIVIDEND_DATE_0
				+ "&DIVIDENDS_PAYABLE_DATE_0="
				+ DIVIDENDS_PAYABLE_DATE_0
				+ "&DIVIDENDS_TYPE_CODE_0="
				+ DIVIDENDS_TYPE_CODE_0
				+ "&DIVIDENDS_SDIO_ID_0_X1="
				+ DIVIDENDS_SDIO_ID_0_X1
				+ "&DIVIDENDS_DECL_DATE_0_X1="
				+ DIVIDENDS_DECL_DATE_0_X1
				+ "&DIVIDENDS_RECORD_DATE_0_X1="
				+ DIVIDENDS_RECORD_DATE_0_X1
				+ "&DIVIDENDS_PAYABLE_DATE_0_X1="
				+ DIVIDENDS_PAYABLE_DATE_0_X1
				+ "&DIVIDENDS_TYPE_CODE_0_X1="
				+ DIVIDENDS_TYPE_CODE_0_X1
				+ "&DIVIDENDS_TYPE_CODE_0_X1="
				+ DIVIDENDS_TYPE_CODE_0_X1
				+ "&DIVIDENDS_RATE_0="
				+ DIVIDENDS_RATE_0
				+ "&DIVIDENDS_SCHED_CODE_0="
				+ DIVIDENDS_SCHED_CODE_0
				+ "&DIVIDENDS_SCHED_CODE_0_X1="
				+ DIVIDENDS_SCHED_CODE_0_X1
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO,
				"Prepare test data for ADIV_create_divd_decl_row_in02",
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
			Reporter.logEvent(Status.PASS, "Run ADIV service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ADIV service",
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
					"TRANSACTION_DESCR_0: "
							+ doc.getElementsByTagName("TRANSACTION_DESCR_0")
									.item(0).getTextContent()
							+ "\nTRANSACTION_CODE_0: "
							+ doc.getElementsByTagName(
									"TRANSACTION_CODE_0")
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
				Stock.getTestQuery("queryForADIV1")[1],
				this.DIVIDENDS_SDIO_ID_0, this.DIVIDENDS_RECORD_DATE_0);

		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database for query1"
							+ "\nD_INST",
					"Record exists in the Database for query1.", false);
			while (queryResultSet.next()) {
				String evID_DB = queryResultSet.getString("EV_ID");
				Reporter.logEvent(Status.INFO, "Values from DB: ", "EV_ID: "
						+ evID_DB, false);
				queryResultSet1 = DB.executeQuery(General.dbInstance,
						Stock.getTestQuery("queryForADIV2")[1], evID_DB);
				if (DB.getRecordSetCount(queryResultSet1) > 0) {
					Reporter.logEvent(Status.PASS,
							"Validating existence of data in the Database for query2"
									+ "\nD_INST",
							"Record exists in the Database for query2.", false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Validating existence of data in the Database for query2",
							"No Record exists in the Database for query2",
							false);
				}
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database for query1",
					"No Record exists in the Database for query1", false);
		}

	}

	public void deleteRecordFromDB() throws SQLException {
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForADIV1")[1],
				this.DIVIDENDS_SDIO_ID_0, this.DIVIDENDS_RECORD_DATE_0);
		
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database for query1"
							+ "\nD_INST",
					"Record exists in the Database for query1.", false);
			queryResultSet2 = DB.executeQuery(General.dbInstance,
					Stock.getTestQuery("queryForADIV3")[1],
					this.DIVIDENDS_SDIO_ID_0, this.DIVIDENDS_RECORD_DATE_0);
			Reporter.logEvent(Status.PASS, "Deleting Records in the Database"
					+ "\nD_INST", "Deleted Records in the Database.", false);

		} else {
			Reporter.logEvent(Status.INFO, "Deleting Records in the Database"
					+ "\nD_INST", "There are no Records in the Database.",
					false);
		}

	}
}
