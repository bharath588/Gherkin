package pageobject.PINV;
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

public class PINV_Change_Product_Investment {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/PINV_Change_Product_investment_Option_rate";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String CONTROL_SELECTION_0_X1;
	String GET_PROD_ID_0;
	String HEADER_N_QUERY_DATE_0;
	String Standard_Investment_Options;
	String Standard_Investment_Options_X1;
	String PROD_INVOPT_SDIO_ID_LOV3;
	String PROD_INVOPT_STATUS_CODE_0;
	String PROD_INVOPT_STATUS_EFFDATE_0;
	String PUP1_CURSOR1_0;

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

	public void setCONTROL_SELECTION_0_X1(String cONTROL_SELECTION_0_X1) {
		CONTROL_SELECTION_0_X1 = cONTROL_SELECTION_0_X1;
	}

	public void setGET_PROD_ID_0(String gET_PROD_ID_0) {
		GET_PROD_ID_0 = gET_PROD_ID_0;
	}

	public void setHEADER_N_QUERY_DATE_0(String hEADER_N_QUERY_DATE_0) {
		HEADER_N_QUERY_DATE_0 = hEADER_N_QUERY_DATE_0;
	}

	public void setStandard_Investment_Options(
			String standard_Investment_Options) {
		Standard_Investment_Options = standard_Investment_Options;
	}

	public void setStandard_Investment_Options_X1(
			String standard_Investment_Options_X1) {
		Standard_Investment_Options_X1 = standard_Investment_Options_X1;
	}

	public void setPROD_INVOPT_SDIO_ID_LOV3(String pROD_INVOPT_SDIO_ID_LOV3) {
		PROD_INVOPT_SDIO_ID_LOV3 = pROD_INVOPT_SDIO_ID_LOV3;
	}

	public void setPROD_INVOPT_STATUS_CODE_0(String pROD_INVOPT_STATUS_CODE_0) {
		PROD_INVOPT_STATUS_CODE_0 = pROD_INVOPT_STATUS_CODE_0;
	}

	public void setPROD_INVOPT_STATUS_EFFDATE_0(
			String pROD_INVOPT_STATUS_EFFDATE_0) {
		PROD_INVOPT_STATUS_EFFDATE_0 = pROD_INVOPT_STATUS_EFFDATE_0;
	}

	public void setPUP1_CURSOR1_0(String pUP1_CURSOR1_0) {
		PUP1_CURSOR1_0 = pUP1_CURSOR1_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SELECTION_0_X1(Stock
				.GetParameterValue("CONTROL_SELECTION_0_X1"));
		this.setGET_PROD_ID_0(Stock.GetParameterValue("GET_PROD_ID_0"));
		this.setHEADER_N_QUERY_DATE_0(Stock
				.GetParameterValue("HEADER_N_QUERY_DATE_0"));
		this.setStandard_Investment_Options(Stock
				.GetParameterValue("Standard_Investment_Options"));
		this.setStandard_Investment_Options_X1(Stock
				.GetParameterValue("Standard_Investment_Options_X1"));
		this.setPROD_INVOPT_SDIO_ID_LOV3(Stock
				.GetParameterValue("PROD_INVOPT_SDIO_ID_LOV3"));
		this.setPROD_INVOPT_STATUS_CODE_0(Stock
				.GetParameterValue("PROD_INVOPT_STATUS_CODE_0"));
		this.setPROD_INVOPT_STATUS_EFFDATE_0(Stock
				.GetParameterValue("PROD_INVOPT_STATUS_EFFDATE_0"));
		this.setPUP1_CURSOR1_0(Stock.GetParameterValue("PUP1_CURSOR1_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&CONTROL_SELECTION_0_X1="
				+ CONTROL_SELECTION_0_X1
				+ "&GET_PROD_ID_0="
				+ GET_PROD_ID_0
				+ "&HEADER_N_QUERY_DATE_0="
				+ HEADER_N_QUERY_DATE_0
				+ "&Standard_Investment_Options="
				+ Standard_Investment_Options
				+ "&Standard_Investment_Options_X1="
				+ Standard_Investment_Options_X1
				+ "&PROD_INVOPT_SDIO_ID_LOV3="
				+ PROD_INVOPT_SDIO_ID_LOV3
				+ "&PROD_INVOPT_STATUS_CODE_0="
				+ PROD_INVOPT_STATUS_CODE_0
				+ "&PROD_INVOPT_STATUS_EFFDATE_0="
				+ PROD_INVOPT_STATUS_EFFDATE_0
				+ "&PUP1_CURSOR1_0="
				+ PUP1_CURSOR1_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(
				Status.INFO,
				"Prepare test data for PINV_change_product_Investment_Options_money_types_rates service",
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
			Reporter.logEvent(Status.PASS, "Run PINV service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PINV service",
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
					"HEADER_PRODUCT_0: "
							+ doc.getElementsByTagName("HEADER_PRODUCT_0")
									.item(0).getTextContent()
							+ "\nHEADER_PR_NAME_0: "
							+ doc.getElementsByTagName("HEADER_PR_NAME_0")
									.item(0).getTextContent()
							+ "\nHEADER_IRSRL_CODE_0: "
							+ doc.getElementsByTagName("HEADER_IRSRL_CODE_0")
									.item(0).getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}

	}

	public void validateInDatabase() throws SQLException {
		String sdioID="%"+this.Standard_Investment_Options_X1+"%";
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPINVChangeProductInvestment")[1], this.GET_PROD_ID_0,sdioID);
		
		String upload_Req_ID = doc.getElementsByTagName("HEADER_PRODUCT_0")
				.item(0).getTextContent();
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(
					Status.PASS,
					"Validating existence of data in the Database" + "\nD_INST",
					"Record exists in the Database.", false);
			while (queryResultSet.next()) {
				String prodID_DB = queryResultSet.getString("PROD_ID");
				Reporter.logEvent(Status.INFO, "Values from DB: ", "PROD_ID: "
						+ prodID_DB, false);
				if (upload_Req_ID.equals(prodID_DB)) {
					Reporter.logEvent(
							Status.PASS,
							"Comparing the data in the Database with Service"
									+ "\nD_INST",
							"Record in the Database matches with the input parameters.",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Comparing the data in the Database with Service"
									+ "\nD_INST",
							"Records in the Database do not match with the input parameters.",
							false);
				}
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"No Record exists in the Database", false);
		}

	}
}
