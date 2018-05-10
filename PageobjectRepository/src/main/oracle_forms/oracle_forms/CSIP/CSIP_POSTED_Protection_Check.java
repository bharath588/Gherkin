/**
 * 
 */
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

/**
 * @author Mamta
 *
 */
public class CSIP_POSTED_Protection_Check {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/CSIP_Posted_Protection_Check";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String REMIT_NOTICE_ID_0;

	static String gcs_Basis_BeforeRunService = null,
			gcs_Value_BeforeRunService = null,
			csrs_code_BeforeRunService = null,
			cash_effdate_BeforeRunService = null,
			sdmt_code_BeforeRunService = null,
			sddety_code_BeforeRunService = null;
	static String gcs_Basis_AfterRunService = null,
			gcs_Value_AfterRunService = null, csrs_code_AfterRunService = null,
			cash_effdate_AfterRunService = null,
			sdmt_code_AfterRunService = null,
			sddety_code_AfterRunService = null;

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

	public void setREMIT_NOTICE_ID_0(String rEMIT_NOTICE_ID_0) {
		REMIT_NOTICE_ID_0 = rEMIT_NOTICE_ID_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setREMIT_NOTICE_ID_0(Stock.GetParameterValue("REMIT_NOTICE_ID_0"));

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
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO,
				"Prepare test data for CSIP_POSTED_Protection_Check service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void validateInDatabaseBeforeRunService() throws SQLException {
		queryResultSet = DB
				.executeQuery(
						General.dbInstance,
						Stock.getTestQuery("queryForCSIPProtectionCheckAfterPosted1")[1],
						REMIT_NOTICE_ID_0);
		if (queryResultSet.next()) {
			gcs_Basis_BeforeRunService = queryResultSet.getString("GCS_BASIS");
			gcs_Value_BeforeRunService = queryResultSet.getString("GCS_VALUE");
			csrs_code_BeforeRunService = queryResultSet.getString("CSRS_CODE");
			cash_effdate_BeforeRunService = queryResultSet
					.getString("CASH_EFFDATE");
		}
		queryResultSet = DB
				.executeQuery(
						General.dbInstance,
						Stock.getTestQuery("queryForCSIPProtectionCheckAfterPosted2")[1],
						REMIT_NOTICE_ID_0);
		if (queryResultSet.next()) {
			sdmt_code_BeforeRunService = queryResultSet.getString("SDMT_CODE");
			sddety_code_BeforeRunService = queryResultSet
					.getString("SDDETY_CODE");
		}
		Reporter.logEvent(Status.INFO,
				"Validate records in DB Before Run Service:", "gcs_Basis: "
						+ gcs_Basis_BeforeRunService + "\ngcs_Value:"
						+ gcs_Value_BeforeRunService + "\ncsrs_code:"
						+ csrs_code_BeforeRunService + "\ncash_effdate: "
						+ cash_effdate_BeforeRunService + "\nsdmt_code: "
						+ sdmt_code_BeforeRunService + "\nsddety_code"
						+ sddety_code_BeforeRunService, false);

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
			Reporter.logEvent(Status.PASS,
					"Run CSIP_POSTED_Protection_Check service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL,
					"Run CSIP_POSTED_Protection_Check service",
					"Running Failed:", false);
		}
	}

	public void validateOutput() throws SQLException {
		String Error = doc.getElementsByTagName("Error").item(0)
				.getTextContent();

		if (Error.isEmpty())
			Reporter.logEvent(Status.PASS,
					"Validate response - Error is empty", "Error tag is empty",
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
	}

	public void validateInDatabase() throws SQLException {
		queryResultSet = DB
				.executeQuery(
						General.dbInstance,
						Stock.getTestQuery("queryForCSIPProtectionCheckAfterPosted1")[1],
						REMIT_NOTICE_ID_0);
		if (queryResultSet.next()) {
			gcs_Basis_AfterRunService = queryResultSet.getString("GCS_BASIS");
			gcs_Value_AfterRunService = queryResultSet.getString("GCS_VALUE");
			csrs_code_AfterRunService = queryResultSet.getString("CSRS_CODE");
			cash_effdate_AfterRunService = queryResultSet
					.getString("CASH_EFFDATE");
		}
		queryResultSet = DB
				.executeQuery(
						General.dbInstance,
						Stock.getTestQuery("queryForCSIPProtectionCheckAfterPosted2")[1],
						REMIT_NOTICE_ID_0);
		if (queryResultSet.next()) {
			sdmt_code_AfterRunService = queryResultSet.getString("SDMT_CODE");
			sddety_code_AfterRunService = queryResultSet
					.getString("SDDETY_CODE");
		}

		Reporter.logEvent(Status.INFO,
				"Validate records in DB After Run Service:", "gcs_Basis: "
						+ gcs_Basis_AfterRunService + "\ngcs_Value:"
						+ gcs_Value_AfterRunService + "\ncsrs_code:"
						+ csrs_code_AfterRunService + "\ncash_effdate: "
						+ cash_effdate_AfterRunService + "\nsdmt_code: "
						+ sdmt_code_AfterRunService + "\nsddety_code"
						+ sddety_code_AfterRunService, false);
		if (csrs_code_BeforeRunService.equals(csrs_code_AfterRunService)
				&& cash_effdate_BeforeRunService
						.equals(cash_effdate_AfterRunService)
				&& sdmt_code_BeforeRunService.equals(sdmt_code_AfterRunService)
				&& sddety_code_BeforeRunService
						.equals(sddety_code_AfterRunService)) {
			Reporter.logEvent(
					Status.PASS,
					"Validate records in DB:",
					"Database Values before Runing the service and after running the service are same. No Updates",
					false);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Validate records in DB:",
					"Database Values before Runing the service and after running the service are not same.",
					false);
		}

	}
}
