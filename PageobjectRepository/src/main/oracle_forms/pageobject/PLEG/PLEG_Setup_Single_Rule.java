package pageobject.PLEG;

import generallib.DateCompare;
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

public class PLEG_Setup_Single_Rule {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/PLEG_Setup_Single_Rule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet1;
	private ResultSet queryResultSet2;

	String LOGON_USERNAME;
	String LOGON_PASSWORD;
	String LOGON_DATABASE;
	String CONTROL_SELECTION_0;
	String GET_GA_GA_ID_0;
	String CFG_CONTROL_RG_ELIGIBILITY_ELIG_ALL_RB_0;
	String ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0;
	String ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0;
	String ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0;
	String ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0;
	String ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_M;
	String ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0;
	String ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0;
	

	String planID_db;

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

	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}

	public void setCFG_CONTROL_RG_ELIGIBILITY_ELIG_ALL_RB_0(
			String cFG_CONTROL_RG_ELIGIBILITY_ELIG_ALL_RB_0) {
		CFG_CONTROL_RG_ELIGIBILITY_ELIG_ALL_RB_0 = cFG_CONTROL_RG_ELIGIBILITY_ELIG_ALL_RB_0;
	}

	public void setELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0(
			String eLIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0) {
		ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0 = eLIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0;
	}

	public void setELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0(
			String eLIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0) {
		ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0 = eLIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0;
	}

	public void setELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0(
			String eLIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0) {
		ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0 = eLIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0;
	}

	public void setELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0(
			String eLIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0) {
		ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0 = eLIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0;
	}

	public void setELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_M(
			String eLIGIBILITY_RULE_MNTY_REQUIRED_HOURS_M) {
		ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_M = eLIGIBILITY_RULE_MNTY_REQUIRED_HOURS_M;
	}

	public void setELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0(
			String eLIGIBILITY_RULE_MNTY_NEXT_PERIOD_0) {
		ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0 = eLIGIBILITY_RULE_MNTY_NEXT_PERIOD_0;
	}

	public void setELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0(
			String eLIGIBILITY_RULE_MNTY_ENTRY_FREQ_0) {
		ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0 = eLIGIBILITY_RULE_MNTY_ENTRY_FREQ_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setCFG_CONTROL_RG_ELIGIBILITY_ELIG_ALL_RB_0(Stock
				.GetParameterValue("CFG_CONTROL_RG_ELIGIBILITY_ELIG_ALL_RB_0"));
		this.setELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0(Stock
				.GetParameterValue("ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0(Stock
				.GetParameterValue("ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0"));
		this.setELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0(Stock
				.GetParameterValue("ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0"));
		this.setELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0(Stock
				.GetParameterValue("ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0"));
		this.setELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_M(Stock
				.GetParameterValue("ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_M"));
		this.setELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0(Stock
				.GetParameterValue("ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0"));
		this.setELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0(Stock
				.GetParameterValue("ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&GET_GA_GA_ID_0="
				+ GET_GA_GA_ID_0
				+ "&CFG_CONTROL_RG_ELIGIBILITY_ELIG_ALL_RB_0="
				+ CFG_CONTROL_RG_ELIGIBILITY_ELIG_ALL_RB_0
				+ "&ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0="
				+ ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0
				+ "&ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0="
				+ ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0
				+ "&ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0="
				+ ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0
				+ "&ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0="
				+ ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0
				+ "&ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_M="
				+ ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_M
				+ "&ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0="
				+ ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0
				+ "&ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0="
				+ ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for PLEG service",
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
			Reporter.logEvent(Status.PASS, "Run PLEG service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PLEG service",
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
					"ELIGIBILITY_RULE_SET_DESCRIPTION_0: "
							+ doc.getElementsByTagName(
									"ELIGIBILITY_RULE_SET_DESCRIPTION_0")
									.item(0).getTextContent()
							+ "\nELIGIBILITY_RULE_MNTY_EFFDATE_0: "
							+ doc.getElementsByTagName(
									"ELIGIBILITY_RULE_MNTY_EFFDATE_0").item(0)
									.getTextContent()
							+ "\nCFG_CONTROL_TODAYS_DATE_0: "
							+ doc.getElementsByTagName("CFG_CONTROL_TODAYS_DATE_0")
							+ "\nGET_GA_PLAN_NAME_0: "
							+ doc.getElementsByTagName("GET_GA_PLAN_NAME_0")
									.item(0).getTextContent()
							+ "\nGET_GA_PROD_ID_0: "
							+ doc.getElementsByTagName("GET_GA_PROD_ID_0")
									.item(0).getTextContent()
							+ "\nSTEP_TYPE_DESCR_0: "
							+ doc.getElementsByTagName("STEP_TYPE_DESCR_0"),
					false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException {
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForPLEG")[1], this.GET_GA_GA_ID_0);
		while (queryResultSet.next()) {
			planID_db = queryResultSet.getString("PLAN_ID");
		}
		queryResultSet1 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForPLEG1")[1], planID_db);
		if (DB.getRecordSetCount(queryResultSet1) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"Record inserted into eligibility_rule_mnty", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record did not insert into eligibility_rule_mnty", false);
		}
		queryResultSet2 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForPLEG2")[1], planID_db);
		if (DB.getRecordSetCount(queryResultSet2) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"Record inserted into eligibility_rule_set", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record did not insert into eligibility_rule_set", false);
		}

	}
}