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

public class CSIP_Manual_Adjust {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/CSIP_ManualAdjust";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String LOGON_USERNAME;
	String LOGON_PASSWORD;
	String LOGON_DATABASE;
	String CONTROL_SELECTION_0;
	String REMIT_NOTICE_ID_0;
	String IND_REMIT_SUM_AMOUNT_1_0;
	String IND_REMIT_SUM_AMOUNT_1_0_X1;

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

	public void setIND_REMIT_SUM_AMOUNT_1_0(String iND_REMIT_SUM_AMOUNT_1_0) {
		IND_REMIT_SUM_AMOUNT_1_0 = iND_REMIT_SUM_AMOUNT_1_0;
	}

	public void setIND_REMIT_SUM_AMOUNT_1_0_X1(
			String iND_REMIT_SUM_AMOUNT_1_0_X1) {
		IND_REMIT_SUM_AMOUNT_1_0_X1 = iND_REMIT_SUM_AMOUNT_1_0_X1;
	}

	public void setServiceParameters(String remit_id) {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setREMIT_NOTICE_ID_0(Stock.GetParameterValue("REMIT_NOTICE_ID_0"));
		this.setIND_REMIT_SUM_AMOUNT_1_0(Stock
				.GetParameterValue("IND_REMIT_SUM_AMOUNT_1_0"));
		this.setIND_REMIT_SUM_AMOUNT_1_0_X1(Stock
				.GetParameterValue("IND_REMIT_SUM_AMOUNT_1_0_X1"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&REMIT_NOTICE_ID_0="
				+ REMIT_NOTICE_ID_0
				+ "&IND_REMIT_SUM_AMOUNT_1_0="
				+ IND_REMIT_SUM_AMOUNT_1_0
				+ "&IND_REMIT_SUM_AMOUNT_1_0_X1="
				+ IND_REMIT_SUM_AMOUNT_1_0_X1
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
					"AG_REMIT_GA_ID_0: "
							+ doc.getElementsByTagName("AG_REMIT_GA_ID_0")
									.item(0).getTextContent()
							+ "\nAG_REMIT_AMOUNT_0: "
							+ doc.getElementsByTagName("AG_REMIT_AMOUNT_0")
									.item(0).getTextContent()
							+ "\nREMIT_NOTICE_PLAN_NAME_0: "
							+ doc.getElementsByTagName(
									"REMIT_NOTICE_PLAN_NAME_0").item(0)
									.getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);

		}
	}

	public void validateInDatabase() throws SQLException {
		String rmnc_id = this.REMIT_NOTICE_ID_0;

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForCSIPManualAdj")[1], rmnc_id);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"Record exists in the Database\nTable Name: REMIT_NOTICE",
					false);
			while (queryResultSet.next()) {
				Reporter.logEvent(Status.INFO, "From Database ", "RMNC_ID: "
						+ queryResultSet.getString("RMNC_ID"), false);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}
	}
}
