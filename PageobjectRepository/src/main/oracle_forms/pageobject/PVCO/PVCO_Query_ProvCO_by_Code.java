package pageobject.PVCO;

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

public class PVCO_Query_ProvCO_by_Code {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/PVCO_Query_Providing_Company_by_Code";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String PC1_CODE_0;

	

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

	public void setPC1_CODE_0(String pC1_CODE_0) {
		PC1_CODE_0 = pC1_CODE_0;
	}

	public void setServiceParameters() throws SQLException {

		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setPC1_CODE_0(Stock.GetParameterValue("PC1_CODE_0"));

		queryString = "?CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_USERNAME="
				+ LOGON_USERNAME
				+"&PC1_CODE_0"
				+PC1_CODE_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for PVCO service",
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
			Reporter.logEvent(Status.PASS, "Run PVCO service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PVCO service",
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
					"PVCO_STD_SERVICE_CODE_0: "
							+ doc.getElementsByTagName(
									"PVCO_STD_SERVICE_CODE_0").item(0)
									.getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException {

		ResultSet queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForPVCOInsert")[1], this.PC1_CODE_0);
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
