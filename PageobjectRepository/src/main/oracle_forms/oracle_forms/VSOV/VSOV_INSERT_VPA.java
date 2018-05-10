package pageobject.VSOV;

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

public class VSOV_INSERT_VPA {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/VSOV_INSERT_VPA";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String QUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	String STEP_TYPE_LOV;
	String VESTING_ADJUSTMENT_TYPE_CODE_0;
	String VESTING_ADJUSTMENT_REASON_0;
	String SDMT_CODE_LOV;
	String GDMT_SEQNBR_LOV;
	String VESTING_ADJUSTMENT_DETAIL_VESTING_PCT_ADJUSTMENT_0;

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

	

	public void setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(
			String qUERY_CHG_MULTIPLE_SSN_EXT_LOV) {
		QUERY_CHG_MULTIPLE_SSN_EXT_LOV = qUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	}

	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}

	public void setVESTING_ADJUSTMENT_TYPE_CODE_0(
			String vESTING_ADJUSTMENT_TYPE_CODE_0) {
		VESTING_ADJUSTMENT_TYPE_CODE_0 = vESTING_ADJUSTMENT_TYPE_CODE_0;
	}

	public void setVESTING_ADJUSTMENT_REASON_0(String vESTING_ADJUSTMENT_REASON_0) {
		VESTING_ADJUSTMENT_REASON_0 = vESTING_ADJUSTMENT_REASON_0;
	}

	public void setSDMT_CODE_LOV(String sDMT_CODE_LOV) {
		SDMT_CODE_LOV = sDMT_CODE_LOV;
	}

	public void setGDMT_SEQNBR_LOV(String gDMT_SEQNBR_LOV) {
		GDMT_SEQNBR_LOV = gDMT_SEQNBR_LOV;
	}

	public void setVESTING_ADJUSTMENT_DETAIL_VESTING_PCT_ADJUSTMENT_0(
			String vESTING_ADJUSTMENT_DETAIL_VESTING_PCT_ADJUSTMENT_0) {
		VESTING_ADJUSTMENT_DETAIL_VESTING_PCT_ADJUSTMENT_0 = vESTING_ADJUSTMENT_DETAIL_VESTING_PCT_ADJUSTMENT_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(Stock
				.GetParameterValue("QUERY_CHG_MULTIPLE_SSN_EXT_LOV"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		this.setVESTING_ADJUSTMENT_TYPE_CODE_0(Stock
				.GetParameterValue("VESTING_ADJUSTMENT_TYPE_CODE_0"));
		this.setVESTING_ADJUSTMENT_REASON_0(Stock.GetParameterValue("VESTING_ADJUSTMENT_REASON_0"));
		this.setSDMT_CODE_LOV(Stock.GetParameterValue("SDMT_CODE_LOV"));
		this.setGDMT_SEQNBR_LOV(Stock.GetParameterValue("GDMT_SEQNBR_LOV"));
		this.setVESTING_ADJUSTMENT_DETAIL_VESTING_PCT_ADJUSTMENT_0(Stock
				.GetParameterValue("VESTING_ADJUSTMENT_DETAIL_VESTING_PCT_ADJUSTMENT_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&QUERY_CHG_MULTIPLE_SSN_EXT_LOV="
				+ QUERY_CHG_MULTIPLE_SSN_EXT_LOV
				+ "&STEP_TYPE_LOV="
				+ STEP_TYPE_LOV
				+ "&VESTING_ADJUSTMENT_TYPE_CODE_0="
				+ VESTING_ADJUSTMENT_TYPE_CODE_0
				+ "&VESTING_ADJUSTMENT_REASON_0="
				+ VESTING_ADJUSTMENT_REASON_0
				+ "&SDMT_CODE_LOV="
				+ SDMT_CODE_LOV
				+ "&GDMT_SEQNBR_LOV="
				+ GDMT_SEQNBR_LOV
				+ "&VESTING_ADJUSTMENT_DETAIL_VESTING_PCT_ADJUSTMENT_0="
				+ VESTING_ADJUSTMENT_DETAIL_VESTING_PCT_ADJUSTMENT_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for VSOV service",
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
			Reporter.logEvent(Status.PASS, "Run VSOV service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run VSOV service",
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
				Stock.getTestQuery("queryForVACC")[1]);

		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database",
					"Record exists in the Database", false);

			while (queryResultSet.next()) {
				String sdioID_DB = queryResultSet.getString("SDIO_ID");
				String partnerPCT_DB = queryResultSet.getString("PARTNER_PCT");
				String gwPCT_DB = queryResultSet.getString("GW_PCT");
				String rkPCT_DB = queryResultSet.getString("RK_PCT");
				String tpaPCT_DB = queryResultSet.getString("TPA_PCT");
				String faPCT_DB = queryResultSet.getString("FA_PCT");
				String faHiPCT_DB = queryResultSet.getString("FA_HI_PCT");
				String faLowPCT_DB = queryResultSet.getString("FA_LO_PCT");
				String hiDurationMonths_DB = queryResultSet.getString("HI_DURATION_MONTHS");
				

				Reporter.logEvent(Status.INFO,
						"Fetching data from the Database: ",
						"Data from the Database: " + "\nSDIO_ID: " + sdioID_DB
						+ "\nPARTNER_PCT: " + partnerPCT_DB
						+ "\nGW_PCT: " + gwPCT_DB
						+ "\nRK_PCT: " + rkPCT_DB
						+ "\nTPA_PCT: " + tpaPCT_DB
						+ "\nFA_PCT: " + faPCT_DB
						+ "\nFA_HI_PCT: " + faHiPCT_DB
						+ "\nFA_LO_PCT: " + faLowPCT_DB
						+ "\nHI_DURATION_MONTHS: " + hiDurationMonths_DB,
						false);
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}

	}
}
