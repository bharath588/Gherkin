package pageobject.INSN;

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

public class INSN_Query_and_Update_via_CABL_CHPA {

	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/INSN_Query_and_Update_via_CABL_CHPA";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String LOGON_USERNAME;
	String LOGON_PASSWORD;
	String LOGON_DATABASE;
	String CONTROL_SELECTION_0;
	String INDIVIDUAL_SSN_0;
	String PART_MODULE_LOV;
	String QUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	String STEP_TYPE_LOV;
	String IND_SNAP_ACCT_BAL_ACTY_AMOUNT_1;
	String IND_SNAP_ACCT_BAL_ACTY_SDMT_CODE_1;
	String IND_SNAP_ACCT_BAL_ACTY_GDMT_SEQNBR_1;

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

	public void setINDIVIDUAL_SSN_0(String iNDIVIDUAL_SSN_0) {
		INDIVIDUAL_SSN_0 = iNDIVIDUAL_SSN_0;
	}

	public void setPART_MODULE_LOV(String pART_MODULE_LOV) {
		PART_MODULE_LOV = pART_MODULE_LOV;
	}

	public void setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(
			String qUERY_CHG_MULTIPLE_SSN_EXT_LOV) {
		QUERY_CHG_MULTIPLE_SSN_EXT_LOV = qUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	}

	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}

	public void setIND_SNAP_ACCT_BAL_ACTY_AMOUNT_1(
			String iND_SNAP_ACCT_BAL_ACTY_AMOUNT_1) {
		IND_SNAP_ACCT_BAL_ACTY_AMOUNT_1 = iND_SNAP_ACCT_BAL_ACTY_AMOUNT_1;
	}

	public void setIND_SNAP_ACCT_BAL_ACTY_SDMT_CODE_1(
			String iND_SNAP_ACCT_BAL_ACTY_SDMT_CODE_1) {
		IND_SNAP_ACCT_BAL_ACTY_SDMT_CODE_1 = iND_SNAP_ACCT_BAL_ACTY_SDMT_CODE_1;
	}

	public void setIND_SNAP_ACCT_BAL_ACTY_GDMT_SEQNBR_1(
			String iND_SNAP_ACCT_BAL_ACTY_GDMT_SEQNBR_1) {
		IND_SNAP_ACCT_BAL_ACTY_GDMT_SEQNBR_1 = iND_SNAP_ACCT_BAL_ACTY_GDMT_SEQNBR_1;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINDIVIDUAL_SSN_0(Stock.GetParameterValue("INDIVIDUAL_SSN_0"));
		this.setPART_MODULE_LOV(Stock.GetParameterValue("PART_MODULE_LOV"));
		this.setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(Stock
				.GetParameterValue("QUERY_CHG_MULTIPLE_SSN_EXT_LOV"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		this.setIND_SNAP_ACCT_BAL_ACTY_AMOUNT_1(Stock
				.GetParameterValue("IND_SNAP_ACCT_BAL_ACTY_AMOUNT_1"));
		this.setIND_SNAP_ACCT_BAL_ACTY_SDMT_CODE_1(Stock
				.GetParameterValue("IND_SNAP_ACCT_BAL_ACTY_SDMT_CODE_1"));
		this.setIND_SNAP_ACCT_BAL_ACTY_GDMT_SEQNBR_1(Stock
				.GetParameterValue("IND_SNAP_ACCT_BAL_ACTY_GDMT_SEQNBR_1"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&INDIVIDUAL_SSN_0="
				+ INDIVIDUAL_SSN_0
				+ "&PART_MODULE_LOV="
				+ PART_MODULE_LOV
				+ "&QUERY_CHG_MULTIPLE_SSN_EXT_LOV="
				+ QUERY_CHG_MULTIPLE_SSN_EXT_LOV
				+ "&STEP_TYPE_LOV="
				+ STEP_TYPE_LOV
				+ "&IND_SNAP_ACCT_BAL_ACTY_AMOUNT_1="
				+ IND_SNAP_ACCT_BAL_ACTY_AMOUNT_1
				+ "&IND_SNAP_ACCT_BAL_ACTY_SDMT_CODE_1="
				+ IND_SNAP_ACCT_BAL_ACTY_SDMT_CODE_1
				+ "&IND_SNAP_ACCT_BAL_ACTY_GDMT_SEQNBR_1="
				+ IND_SNAP_ACCT_BAL_ACTY_GDMT_SEQNBR_1
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for INSN service",
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
			Reporter.logEvent(Status.PASS, "Run INSN service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run INSN service",
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
					"Values from Response",
					"PART_AGRMT_GA_ID_1: "
							+ doc.getElementsByTagName("PART_AGRMT_GA_ID_1")
									.item(0).getTextContent()
							+ "\nPART_AGRMT_GA_ID_0: "
							+ doc.getElementsByTagName("PART_AGRMT_GA_ID_0")
									.item(0).getTextContent()
							+ "\nINDIVIDUAL_LAST_NAME_0: "
							+ doc.getElementsByTagName("INDIVIDUAL_LAST_NAME_0")
									.item(0).getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);

		}
	}
}
