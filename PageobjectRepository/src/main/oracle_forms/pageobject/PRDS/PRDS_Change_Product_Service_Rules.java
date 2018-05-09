package pageobject.PRDS;

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

public class PRDS_Change_Product_Service_Rules {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/PRDS_change_product_service_rules";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_PROD_ID_0;
	String Available_Service_Codes_List;
	String CODE_SUBCODE_LOV;
	String PROD_SERVICE_EFFDATE_13;
	String Available_Service_Codes_List_X1;
	String CODE_SUBCODE_LOV_X1;
	String PROD_SERVICE_EFFDATE_13_X1;

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

	public void setGET_PROD_ID_0(String gET_PROD_ID_0) {
		GET_PROD_ID_0 = gET_PROD_ID_0;
	}

	public void setAvailable_Service_Codes_List(
			String available_Service_Codes_List) {
		Available_Service_Codes_List = available_Service_Codes_List;
	}

	public void setCODE_SUBCODE_LOV(String cODE_SUBCODE_LOV) {
		CODE_SUBCODE_LOV = cODE_SUBCODE_LOV;
	}

	public void setPROD_SERVICE_EFFDATE_13(String pROD_SERVICE_EFFDATE_13) {
		PROD_SERVICE_EFFDATE_13 = pROD_SERVICE_EFFDATE_13;
	}

	public void setAvailable_Service_Codes_List_X1(
			String available_Service_Codes_List_X1) {
		Available_Service_Codes_List_X1 = available_Service_Codes_List_X1;
	}

	public void setCODE_SUBCODE_LOV_X1(String cODE_SUBCODE_LOV_X1) {
		CODE_SUBCODE_LOV_X1 = cODE_SUBCODE_LOV_X1;
	}

	public void setPROD_SERVICE_EFFDATE_13_X1(String pROD_SERVICE_EFFDATE_13_X1) {
		PROD_SERVICE_EFFDATE_13_X1 = pROD_SERVICE_EFFDATE_13_X1;
	}

	public void setServiceParameters() throws SQLException {

		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_PROD_ID_0(Stock.GetParameterValue("GET_PROD_ID_0"));
		this.setAvailable_Service_Codes_List(Stock
				.GetParameterValue("Available_Service_Codes_List"));
		this.setCODE_SUBCODE_LOV(Stock.GetParameterValue("CODE_SUBCODE_LOV"));
		this.setPROD_SERVICE_EFFDATE_13(Stock
				.GetParameterValue("PROD_SERVICE_EFFDATE_13"));
		this.setAvailable_Service_Codes_List_X1(Stock
				.GetParameterValue("CODE_SUBCODE_LOV_X1"));
		this.setCODE_SUBCODE_LOV_X1(Stock.GetParameterValue("CODE_SUBCODE_LOV_X1"));
		this.setPROD_SERVICE_EFFDATE_13_X1(Stock
				.GetParameterValue("CODE_SUBCODE_LOV_X1"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&GET_PROD_ID_0="
				+ GET_PROD_ID_0
				+ "&Available_Service_Codes_List="
				+ Available_Service_Codes_List
				+ "&CODE_SUBCODE_LOV="
				+ CODE_SUBCODE_LOV
				+ "&PROD_SERVICE_EFFDATE_13="
				+ PROD_SERVICE_EFFDATE_13
				+ "&Available_Service_Codes_List_X1="
				+ Available_Service_Codes_List_X1
				+ "&CODE_SUBCODE_LOV_X1="
				+ CODE_SUBCODE_LOV_X1
				+ "&PROD_SERVICE_EFFDATE_13_X1="
				+ PROD_SERVICE_EFFDATE_13_X1
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for PRDS service",
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
			Reporter.logEvent(Status.PASS, "Run PRDS service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PRDS service",
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
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException {
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForPRDS")[1], this.GET_PROD_ID_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"Record exists in the Database.",
					false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"No Record exists in the Database", false);
		}
	}
}
