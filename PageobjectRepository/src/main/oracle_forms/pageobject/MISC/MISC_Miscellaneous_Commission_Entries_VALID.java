package pageobject.MISC;

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

public class MISC_Miscellaneous_Commission_Entries_VALID {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/MICMMiscellaneousCommissionEntriesValid";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String LOGON_USERNAME_X1;
	String LOGON_PASSWORD_X1;
	String LOGON_DATABASE_X1;
	String CUCPCR1_SS1_ID_0;
	String CUCPCR1_GA_ID_0;
	String CUCPCR1_COMP_STRUC_LOV4;
	String CUCPCR1_COMP_REASON_LOV2;
	String CUCPCR1_COMM_PREM_AMT_0;
	String CUCPCR1_COMM_TYPE_CODE_0;
	String CUCPCR1_RATE_0;
	String CUCPCR1_SHARE_PERCENT_0;
	String CUCPCR1_NARRATIVE_0;

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

	public void setLOGON_USERNAME_X1(String lOGON_USERNAME_X1) {
		LOGON_USERNAME_X1 = lOGON_USERNAME_X1;
	}

	public void setLOGON_PASSWORD_X1(String lOGON_PASSWORD_X1) {
		LOGON_PASSWORD_X1 = lOGON_PASSWORD_X1;
	}

	public void setLOGON_DATABASE_X1(String lOGON_DATABASE_X1) {
		LOGON_DATABASE_X1 = lOGON_DATABASE_X1;
	}

	public void setCUCPCR1_SS1_ID_0(String cUCPCR1_SS1_ID_0) {
		CUCPCR1_SS1_ID_0 = cUCPCR1_SS1_ID_0;
	}

	public void setCUCPCR1_GA_ID_0(String cUCPCR1_GA_ID_0) {
		CUCPCR1_GA_ID_0 = cUCPCR1_GA_ID_0;
	}

	public void setCUCPCR1_COMP_STRUC_LOV4(String cUCPCR1_COMP_STRUC_LOV4) {
		CUCPCR1_COMP_STRUC_LOV4 = cUCPCR1_COMP_STRUC_LOV4;
	}

	public void setCUCPCR1_COMP_REASON_LOV2(String cUCPCR1_COMP_REASON_LOV2) {
		CUCPCR1_COMP_REASON_LOV2 = cUCPCR1_COMP_REASON_LOV2;
	}

	public void setCUCPCR1_COMM_PREM_AMT_0(String cUCPCR1_COMM_PREM_AMT_0) {
		CUCPCR1_COMM_PREM_AMT_0 = cUCPCR1_COMM_PREM_AMT_0;
	}

	public void setCUCPCR1_COMM_TYPE_CODE_0(String cUCPCR1_COMM_TYPE_CODE_0) {
		CUCPCR1_COMM_TYPE_CODE_0 = cUCPCR1_COMM_TYPE_CODE_0;
	}

	public void setCUCPCR1_RATE_0(String cUCPCR1_RATE_0) {
		CUCPCR1_RATE_0 = cUCPCR1_RATE_0;
	}

	public void setCUCPCR1_SHARE_PERCENT_0(String cUCPCR1_SHARE_PERCENT_0) {
		CUCPCR1_SHARE_PERCENT_0 = cUCPCR1_SHARE_PERCENT_0;
	}

	public void setCUCPCR1_NARRATIVE_0(String cUCPCR1_NARRATIVE_0) {
		CUCPCR1_NARRATIVE_0 = cUCPCR1_NARRATIVE_0;
	}

	public void setServiceParameters() {
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME_X1(Stock.GetParameterValue("LOGON_USERNAME_X1"));
		this.setLOGON_PASSWORD_X1(Stock.GetParameterValue("LOGON_PASSWORD_X1"));
		this.setLOGON_DATABASE_X1(Stock.GetParameterValue("LOGON_DATABASE_X1"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCUCPCR1_SS1_ID_0(Stock.GetParameterValue("CUCPCR1_SS1_ID_0"));
		this.setCUCPCR1_GA_ID_0(Stock.GetParameterValue("CUCPCR1_GA_ID_0"));
		this.setCUCPCR1_COMP_STRUC_LOV4(Stock
				.GetParameterValue("CUCPCR1_COMP_STRUC_LOV4"));
		this.setCUCPCR1_COMP_REASON_LOV2(Stock
				.GetParameterValue("CUCPCR1_COMP_REASON_LOV2"));
		this.setCUCPCR1_COMM_PREM_AMT_0(Stock
				.GetParameterValue("CUCPCR1_COMM_PREM_AMT_0"));
		this.setCUCPCR1_COMM_TYPE_CODE_0(Stock
				.GetParameterValue("CUCPCR1_COMM_TYPE_CODE_0"));
		this.setCUCPCR1_RATE_0(Stock.GetParameterValue("CUCPCR1_RATE_0"));
		this.setCUCPCR1_SHARE_PERCENT_0(Stock
				.GetParameterValue("CUCPCR1_SHARE_PERCENT_0"));
		this.setCUCPCR1_NARRATIVE_0(Stock
				.GetParameterValue("CUCPCR1_NARRATIVE_0"));

		queryString = "?LOGON_USERNAME="
				+LOGON_USERNAME
				+"&LOGON_PASSWORD="
				+LOGON_PASSWORD
				+"&LOGON_DATABASE="
				+LOGON_DATABASE
				+"&LOGON_USERNAME_X1="
				+LOGON_USERNAME_X1
				+"&LOGON_PASSWORD_X1="
				+LOGON_PASSWORD_X1
				+"&LOGON_DATABASE_X1="
				+LOGON_DATABASE_X1
				+"&CONTROL_SELECTION_0="
				+CONTROL_SELECTION_0
				+"&CUCPCR1_SS1_ID_0="
				+CUCPCR1_SS1_ID_0
				+"&CUCPCR1_GA_ID_0="
				+CUCPCR1_GA_ID_0
				+"&CUCPCR1_COMP_STRUC_LOV4="
				+CUCPCR1_COMP_STRUC_LOV4
				+"&CUCPCR1_COMP_REASON_LOV2="
				+CUCPCR1_COMP_REASON_LOV2
				+"&CUCPCR1_COMM_PREM_AMT_0="
				+CUCPCR1_COMM_PREM_AMT_0
				+"&CUCPCR1_COMM_TYPE_CODE_0="
				+CUCPCR1_COMM_TYPE_CODE_0
				+"&CUCPCR1_RATE_0="
				+CUCPCR1_RATE_0
				+"&CUCPCR1_SHARE_PERCENT_0="
				+CUCPCR1_SHARE_PERCENT_0
				+"&CUCPCR1_NARRATIVE_0="
				+CUCPCR1_NARRATIVE_0
				+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for MISC service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run MISC service",
					"Execution Done Successfully!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run MISC service",
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
					"CUCPCR1_DPDATE_0: "
							+ doc.getElementsByTagName("CUCPCR1_DPDATE_0")
									.item(0).getTextContent()
							+ "\nCUCPCR1_EFFDATE_0: "
							+ doc.getElementsByTagName("CUCPCR1_EFFDATE_0")
									.item(0).getTextContent()
							+ "\nCUCPCR1_COMP_STRUC_ID_0: "
							+ doc.getElementsByTagName("CUCPCR1_COMP_STRUC_ID_0")
							+ "\nCUCPCR1_COMP_REASON_CODE_0: "
							+ doc.getElementsByTagName(
									"CUCPCR1_COMP_REASON_CODE_0").item(0)
									.getTextContent()
							+ "\nCUCPCR1_AMOUNT_0: "
							+ doc.getElementsByTagName("CUCPCR1_AMOUNT_0")
									.item(0).getTextContent()
							+ "\nCUCPCR1_PAY_STATUS_CODE_0: "
							+ doc.getElementsByTagName("CUCPCR1_PAY_STATUS_CODE_0")
							+ "\nCUCPCR1_FIRST_YEAR_IND_0: "
							+ doc.getElementsByTagName(
									"CUCPCR1_FIRST_YEAR_IND_0").item(0)
									.getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException {
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForMISC")[1], this.CUCPCR1_GA_ID_0,
				this.CUCPCR1_SS1_ID_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(
					Status.PASS,
					"Validating existence of data in the Database" + "\nD_ISIS",
					"Record exists in the Database. There are total "
							+ DB.getRecordSetCount(queryResultSet) + " Records.",
					false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"No Record exists in the Database", false);
		}

	}
}