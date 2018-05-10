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

public class CSIP_Offset_Funding_Cash_UI {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/CSIP_OFFSET_FUNDING_CASH_UI";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String LOGON_USERNAME;
	String LOGON_PASSWORD;
	String LOGON_DATABASE;
	String CONTROL_SELECTION_0;
	String REMIT_NOTICE_ID_0;
	String WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1;
	String WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X1;
	String WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X2;
	String WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_3;

	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}

	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}

	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}

	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}

	public void setREMIT_NOTICE_ID_0(String rEMIT_NOTICE_ID_0) {
		REMIT_NOTICE_ID_0 = rEMIT_NOTICE_ID_0;
	}

	public void setWIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1(
			String wIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1) {
		WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1 = wIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1;
	}

	public void setWIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X1(
			String wIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X1) {
		WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X1 = wIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X1;
	}

	public void setWIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X2(
			String wIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X2) {
		WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X2 = wIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X2;
	}

	public void setWIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_3(
			String wIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_3) {
		WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_3 = wIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_3;
	}

	public void setServiceParameters(String remit_id) {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.REMIT_NOTICE_ID_0=remit_id;
		this.setWIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1(Stock
				.GetParameterValue("WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1"));
		this.setWIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X1(Stock
				.GetParameterValue("WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X1"));
		this.setWIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X2(Stock
				.GetParameterValue("WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X2"));
		this.setWIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_3(Stock
				.GetParameterValue("WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_3"));

		queryString = "?CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&REMIT_NOTICE_ID_0="
				+ REMIT_NOTICE_ID_0
				+ "&WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1="
				+ WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1
				+ "&WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X1="
				+ WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X1
				+ "&WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X2="
				+ WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_1_X2
				+ "&WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_3="
				+ WIRE_OFFSET_ACCOUNTS_OFFSET_APPLIED_3
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for CSIP service",
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
			Reporter.logEvent(Status.PASS, "Run CSIP service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CSIP service",
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
			/*Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);*/
			System.out.println(Error);

		}
	}

	public void validateInDatabase() throws SQLException {
		//String rmnc_id = this.REMIT_NOTICE_ID_0;

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForCSIPOffsetFundingCashUI")[1],
				REMIT_NOTICE_ID_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"Record exists in the Database\nTable Name: REMIT_NOTICE",
					false);
			while (queryResultSet.next()) {
				Reporter.logEvent(Status.INFO, "From Database ", "ID: "
						+ queryResultSet.getString("ID"), false);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}
	}
}
