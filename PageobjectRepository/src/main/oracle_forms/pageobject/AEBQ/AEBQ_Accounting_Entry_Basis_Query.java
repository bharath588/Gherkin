package pageobject.AEBQ;

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

public class AEBQ_Accounting_Entry_Basis_Query {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/AEBQ_Accounting_Entry_Basis_Query";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String ARS_NAME_LOV;
	String LOGON_DATABASE_X1;
	String LOGON_PASSWORD_X1;
	String LOGON_USERNAME_X1;

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

	public void setARS_NAME_LOV(String aRS_NAME_LOV) {
		ARS_NAME_LOV = aRS_NAME_LOV;
	}

	public void setLOGON_DATABASE_X1(String lOGON_DATABASE_X1) {
		LOGON_DATABASE_X1 = lOGON_DATABASE_X1;
	}

	public void setLOGON_PASSWORD_X1(String lOGON_PASSWORD_X1) {
		LOGON_PASSWORD_X1 = lOGON_PASSWORD_X1;
	}

	public void setLOGON_USERNAME_X1(String lOGON_USERNAME_X1) {
		LOGON_USERNAME_X1 = lOGON_USERNAME_X1;
	}

	public void setServiceParameters() throws SQLException {

		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_USERNAME_X1(Stock.GetParameterValue("LOGON_USERNAME_X1"));
		this.setLOGON_PASSWORD_X1(Stock.GetParameterValue("LOGON_PASSWORD_X1"));
		this.setLOGON_DATABASE_X1(Stock.GetParameterValue("LOGON_DATABASE_X1"));
		this.setARS_NAME_LOV(Stock.GetParameterValue("ARS_NAME_LOV"));

		queryString = "?ARS_NAME_LOV="
				+ ARS_NAME_LOV
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&LOGON_DATABASE_X1="
				+ LOGON_DATABASE_X1
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_PASSWORD_X1="
				+ LOGON_PASSWORD_X1
				+ "&LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_USERNAME_X1="
				+ LOGON_USERNAME_X1
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for AEBQ service",
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
			Reporter.logEvent(Status.PASS, "Run AEBQ service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run AEBQ service",
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
					"Values From Response: ",
					"ACCTG_ENTRY_BASIS_ARS_NAME_0: "
							+ doc.getElementsByTagName(
									"ACCTG_ENTRY_BASIS_ARS_NAME_0").item(0)
									.getTextContent()
							+ "\nACCTG_ENTRY_BASIS_DR_CR_PAIR_NBR_0: "
							+ doc.getElementsByTagName(
									"ACCTG_ENTRY_BASIS_DR_CR_PAIR_NBR_0")
									.item(0).getTextContent()
							+ "\nCFG_CONTROL_ARS_NAME_0: "
							+ doc.getElementsByTagName("CFG_CONTROL_ARS_NAME_0")
									.item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException {
		String ars_Name_resp = doc.getElementsByTagName("ACCTG_ENTRY_BASIS_ARS_NAME_0").item(0).getTextContent();

		ResultSet queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForAEBQ")[1], ars_Name_resp);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database for Query",
					"Record exists in the Database\nTable for Query", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database for Query",
					"Record doesn't exists in the Database for Query", false);
		}
	}
}
