package pageobject.FDRI;

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

public class FDRI_Insert_Additional_Fees {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/FDRI_Insert_Additional_Fees";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;

	String provCompany_DB;
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String LOV_DAF_PROV_COMPANY;
	String LOV_DAF_DOL_FEE_TYPE_CODE;
	String DOL_ADDITIONAL_FEE_EFFDATE_1;
	String LOV_DAF_ASSESS_METHOD;
	String LOV_DAF_ASSESS_FREQ;
	String DOL_ADDITIONAL_FEE_RATE_1;
	String DOL_ADDITIONAL_FEE_ANNUALIZED_RATE_1;
	String DOL_ADDITIONAL_FEE_RATE_TEXT_1;

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

	public void setLOV_DAF_PROV_COMPANY(String lOV_DAF_PROV_COMPANY) {
		LOV_DAF_PROV_COMPANY = lOV_DAF_PROV_COMPANY;
	}

	public void setLOV_DAF_DOL_FEE_TYPE_CODE(String lOV_DAF_DOL_FEE_TYPE_CODE) {
		LOV_DAF_DOL_FEE_TYPE_CODE = lOV_DAF_DOL_FEE_TYPE_CODE;
	}

	public void setDOL_ADDITIONAL_FEE_EFFDATE_1(
			String dOL_ADDITIONAL_FEE_EFFDATE_1) {
		DOL_ADDITIONAL_FEE_EFFDATE_1 = dOL_ADDITIONAL_FEE_EFFDATE_1;
	}

	public void setLOV_DAF_ASSESS_METHOD(String lOV_DAF_ASSESS_METHOD) {
		LOV_DAF_ASSESS_METHOD = lOV_DAF_ASSESS_METHOD;
	}

	public void setLOV_DAF_ASSESS_FREQ(String lOV_DAF_ASSESS_FREQ) {
		LOV_DAF_ASSESS_FREQ = lOV_DAF_ASSESS_FREQ;
	}

	public void setDOL_ADDITIONAL_FEE_RATE_1(String dOL_ADDITIONAL_FEE_RATE_1) {
		DOL_ADDITIONAL_FEE_RATE_1 = dOL_ADDITIONAL_FEE_RATE_1;
	}

	public void setDOL_ADDITIONAL_FEE_ANNUALIZED_RATE_1(
			String dOL_ADDITIONAL_FEE_ANNUALIZED_RATE_1) {
		DOL_ADDITIONAL_FEE_ANNUALIZED_RATE_1 = dOL_ADDITIONAL_FEE_ANNUALIZED_RATE_1;
	}

	public void setDOL_ADDITIONAL_FEE_RATE_TEXT_1(
			String dOL_ADDITIONAL_FEE_RATE_TEXT_1) {
		DOL_ADDITIONAL_FEE_RATE_TEXT_1 = dOL_ADDITIONAL_FEE_RATE_TEXT_1;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOV_DAF_PROV_COMPANY(Stock
				.GetParameterValue("LOV_DAF_PROV_COMPANY"));
		this.setLOV_DAF_DOL_FEE_TYPE_CODE(Stock
				.GetParameterValue("LOV_DAF_DOL_FEE_TYPE_CODE"));
		this.setDOL_ADDITIONAL_FEE_EFFDATE_1(Stock
				.GetParameterValue("DOL_ADDITIONAL_FEE_EFFDATE_1"));
		this.setLOV_DAF_ASSESS_METHOD(Stock
				.GetParameterValue("LOV_DAF_ASSESS_METHOD"));
		this.setLOV_DAF_ASSESS_FREQ(Stock
				.GetParameterValue("LOV_DAF_ASSESS_FREQ"));
		this.setDOL_ADDITIONAL_FEE_RATE_1(Stock
				.GetParameterValue("DOL_ADDITIONAL_FEE_RATE_1"));
		this.setDOL_ADDITIONAL_FEE_ANNUALIZED_RATE_1(Stock
				.GetParameterValue("DOL_ADDITIONAL_FEE_ANNUALIZED_RATE_1"));
		this.setDOL_ADDITIONAL_FEE_RATE_TEXT_1(Stock
				.GetParameterValue("DOL_ADDITIONAL_FEE_RATE_TEXT_1"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&LOV_DAF_PROV_COMPANY="
				+ LOV_DAF_PROV_COMPANY
				+ "&LOV_DAF_DOL_FEE_TYPE_CODE="
				+ LOV_DAF_DOL_FEE_TYPE_CODE
				+ "&DOL_ADDITIONAL_FEE_EFFDATE_1="
				+ DOL_ADDITIONAL_FEE_EFFDATE_1
				+ "&LOV_DAF_ASSESS_METHOD="
				+ LOV_DAF_ASSESS_METHOD
				+ "&LOV_DAF_ASSESS_FREQ="
				+ LOV_DAF_ASSESS_FREQ
				+ "&DOL_ADDITIONAL_FEE_RATE_1="
				+ DOL_ADDITIONAL_FEE_RATE_1
				+ "&DOL_ADDITIONAL_FEE_ANNUALIZED_RATE_1="
				+ DOL_ADDITIONAL_FEE_ANNUALIZED_RATE_1
				+ "&DOL_ADDITIONAL_FEE_RATE_TEXT_1="
				+ DOL_ADDITIONAL_FEE_RATE_TEXT_1
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for  service",
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
			Reporter.logEvent(Status.PASS, "Run FDRI service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run FDRI service",
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
					"Values from Response: ",
					"DOL_ADDITIONAL_FEE_PROV_COMPANY_0: "
							+ doc.getElementsByTagName(
									"DOL_ADDITIONAL_FEE_PROV_COMPANY_0")
									.item(0).getTextContent()
							+ "\nDOL_ADDITIONAL_FEE_MAIN_NAME_0: "
							+ doc.getElementsByTagName(
									"DOL_ADDITIONAL_FEE_MAIN_NAME_0").item(0)
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
				Stock.getTestQuery("queryForFDRI")[1]);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database",
					"Record exists in the Database.", false);
			while (queryResultSet.next()) {
				provCompany_DB = queryResultSet.getString("PROV_COMPANY");
				Reporter.logEvent(Status.PASS,
						"Fetching PROV_COMPANY from database", "PROV_COMPANY: "
								+ provCompany_DB, false);

			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database for query1", false);
		}

		queryResultSet1 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForFDRI1")[1], provCompany_DB);
		if (DB.getRecordSetCount(queryResultSet1) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database",
					"Record exists in the Database for query2.", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database for query2", false);
		}

	}

	public void deleteInDatabase() throws SQLException {

		queryResultSet1 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForFDRI1")[1], provCompany_DB);
		if (DB.getRecordSetCount(queryResultSet1) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database",
					"Record exists in the Database for query2.", false);

			queryResultSet = DB.executeQuery(General.dbInstance,
					Stock.getTestQuery("queryForFDRIDELETE")[1],provCompany_DB);
			Reporter.logEvent(Status.INFO, "Deleting data in the Database",
					"Record deleted from the Database.", false);
		} else {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database for query2", false);
		}

	}
}
