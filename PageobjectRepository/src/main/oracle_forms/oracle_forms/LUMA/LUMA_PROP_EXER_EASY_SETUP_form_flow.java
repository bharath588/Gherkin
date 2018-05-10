package pageobject.LUMA;

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

public class LUMA_PROP_EXER_EASY_SETUP_form_flow {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/PROP_EXER_EASY_SETUP_form_flow";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;

	String CONTROL_SELECTION_0_X1;
	String CONTROL_SELECTION_0_X2;
	String CONTROL_SELECTION_0_X3;
	String INP1_CATEGORY_0;
	String INP1_DATA_TYPE_CODE_0;

	String INP1_DFT_VALUE_0;
	String INP1_MAND_IND_0;
	String INP1_PARM_NAME_0;
	String INP1_PROMPT_0;
	String INP1_SEQNBR_0;

	String INP1_USER_CHANGEABLE_IND_0;
	String TRANSACTION_CODE_0;
	String TXN1_CHOICE_TYPE_0;
	String TXN1_CODE_0;
	String TXN1_CODE_0_X1;

	String TXN1_DESCR_0;
	String TXN1_FORM_VERSION_0;
	String TXN1_MENU_CHOICE_SEQNBR_0;
	String TXN1_MENU_CODE_0;
	String TXN1_MOD_SHORT_NAME_0;

	String TXN1_MOD_SHORT_NAME_0_X1;
	String TXN1_REPT_MOD_SHORT_NAME_0;
	String USAULM1_UPDATE_IND_0;

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

	public void setTXN1_CODE_0(String tXN1_CODE_0) {
		TXN1_CODE_0 = tXN1_CODE_0;
	}

	public void setTXN1_CODE_0_X1(String tXN1_CODE_0_X1) {
		TXN1_CODE_0_X1 = tXN1_CODE_0_X1;
	}

	public void setTXN1_DESCR_0(String tXN1_DESCR_0) {
		TXN1_DESCR_0 = tXN1_DESCR_0;
	}

	public void setTXN1_FORM_VERSION_0(String tXN1_FORM_VERSION_0) {
		TXN1_FORM_VERSION_0 = tXN1_FORM_VERSION_0;
	}

	public void setTXN1_MENU_CHOICE_SEQNBR_0(String tXN1_MENU_CHOICE_SEQNBR_0) {
		TXN1_MENU_CHOICE_SEQNBR_0 = tXN1_MENU_CHOICE_SEQNBR_0;
	}

	public void setTXN1_MENU_CODE_0(String tXN1_MENU_CODE_0) {
		TXN1_MENU_CODE_0 = tXN1_MENU_CODE_0;
	}

	public void setTXN1_MOD_SHORT_NAME_0(String tXN1_MOD_SHORT_NAME_0) {
		TXN1_MOD_SHORT_NAME_0 = tXN1_MOD_SHORT_NAME_0;
	}

	public void setTXN1_MOD_SHORT_NAME_0_X1(String tXN1_MOD_SHORT_NAME_0_X1) {
		TXN1_MOD_SHORT_NAME_0_X1 = tXN1_MOD_SHORT_NAME_0_X1;
	}

	public void setTXN1_REPT_MOD_SHORT_NAME_0(String tXN1_REPT_MOD_SHORT_NAME_0) {
		TXN1_REPT_MOD_SHORT_NAME_0 = tXN1_REPT_MOD_SHORT_NAME_0;
	}

	public void setUSAULM1_UPDATE_IND_0(String uSAULM1_UPDATE_IND_0) {
		USAULM1_UPDATE_IND_0 = uSAULM1_UPDATE_IND_0;
	}

	public void setCONTROL_SELECTION_0_X1(String cONTROL_SELECTION_0_X1) {
		CONTROL_SELECTION_0_X1 = cONTROL_SELECTION_0_X1;
	}

	public void setCONTROL_SELECTION_0_X2(String cONTROL_SELECTION_0_X2) {
		CONTROL_SELECTION_0_X2 = cONTROL_SELECTION_0_X2;
	}

	public void setCONTROL_SELECTION_0_X3(String cONTROL_SELECTION_0_X3) {
		CONTROL_SELECTION_0_X3 = cONTROL_SELECTION_0_X3;
	}

	public void setINP1_CATEGORY_0(String iNP1_CATEGORY_0) {
		INP1_CATEGORY_0 = iNP1_CATEGORY_0;
	}

	public void setINP1_DATA_TYPE_CODE_0(String iNP1_DATA_TYPE_CODE_0) {
		INP1_DATA_TYPE_CODE_0 = iNP1_DATA_TYPE_CODE_0;
	}

	public void setINP1_DFT_VALUE_0(String iNP1_DFT_VALUE_0) {
		INP1_DFT_VALUE_0 = iNP1_DFT_VALUE_0;
	}

	public void setINP1_MAND_IND_0(String iNP1_MAND_IND_0) {
		INP1_MAND_IND_0 = iNP1_MAND_IND_0;
	}

	public void setINP1_PARM_NAME_0(String iNP1_PARM_NAME_0) {
		INP1_PARM_NAME_0 = iNP1_PARM_NAME_0;
	}

	public void setINP1_PROMPT_0(String iNP1_PROMPT_0) {
		INP1_PROMPT_0 = iNP1_PROMPT_0;
	}

	public void setINP1_SEQNBR_0(String iNP1_SEQNBR_0) {
		INP1_SEQNBR_0 = iNP1_SEQNBR_0;
	}

	public void setINP1_USER_CHANGEABLE_IND_0(String iNP1_USER_CHANGEABLE_IND_0) {
		INP1_USER_CHANGEABLE_IND_0 = iNP1_USER_CHANGEABLE_IND_0;
	}

	public void setTRANSACTION_CODE_0(String tRANSACTION_CODE_0) {
		TRANSACTION_CODE_0 = tRANSACTION_CODE_0;
	}

	public void setTXN1_CHOICE_TYPE_0(String tXN1_CHOICE_TYPE_0) {
		TXN1_CHOICE_TYPE_0 = tXN1_CHOICE_TYPE_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setUSAULM1_UPDATE_IND_0(Stock
				.GetParameterValue("USAULM1_UPDATE_IND_0"));
		this.setTXN1_REPT_MOD_SHORT_NAME_0(Stock
				.GetParameterValue("TXN1_REPT_MOD_SHORT_NAME_0"));
		this.setTXN1_MOD_SHORT_NAME_0_X1(Stock
				.GetParameterValue("TXN1_MOD_SHORT_NAME_0_X1"));
		this.setTXN1_MOD_SHORT_NAME_0(Stock
				.GetParameterValue("TXN1_MOD_SHORT_NAME_0"));
		this.setTXN1_MENU_CODE_0(Stock.GetParameterValue("TXN1_MENU_CODE_0"));

		this.setTXN1_MENU_CHOICE_SEQNBR_0(Stock
				.GetParameterValue("TXN1_MENU_CHOICE_SEQNBR_0"));
		this.setTXN1_FORM_VERSION_0(Stock
				.GetParameterValue("TXN1_FORM_VERSION_0"));
		this.setTXN1_DESCR_0(Stock.GetParameterValue("TXN1_DESCR_0"));
		this.setTXN1_CODE_0_X1(Stock.GetParameterValue("TXN1_CODE_0_X1"));
		this.setTXN1_CODE_0(Stock.GetParameterValue("TXN1_CODE_0"));

		this.setTXN1_CHOICE_TYPE_0(Stock
				.GetParameterValue("TXN1_CHOICE_TYPE_0"));
		this.setTRANSACTION_CODE_0(Stock
				.GetParameterValue("TRANSACTION_CODE_0"));
		this.setINP1_USER_CHANGEABLE_IND_0(Stock
				.GetParameterValue("INP1_USER_CHANGEABLE_IND_0"));
		this.setINP1_SEQNBR_0(Stock.GetParameterValue("INP1_SEQNBR_0"));
		this.setINP1_PROMPT_0(Stock.GetParameterValue("INP1_PROMPT_0"));

		this.setINP1_PARM_NAME_0(Stock.GetParameterValue("INP1_PARM_NAME_0"));
		this.setINP1_MAND_IND_0(Stock.GetParameterValue("INP1_MAND_IND_0"));
		this.setINP1_DFT_VALUE_0(Stock.GetParameterValue("INP1_DFT_VALUE_0"));
		this.setINP1_DFT_VALUE_0(Stock.GetParameterValue("INP1_DFT_VALUE_0"));
		this.setINP1_CATEGORY_0(Stock.GetParameterValue("INP1_CATEGORY_0"));

		this.setCONTROL_SELECTION_0_X1(Stock
				.GetParameterValue("CONTROL_SELECTION_0_X1"));
		this.setCONTROL_SELECTION_0_X2(Stock
				.GetParameterValue("CONTROL_SELECTION_0_X2"));
		this.setCONTROL_SELECTION_0_X3(Stock
				.GetParameterValue("CONTROL_SELECTION_0_X3"));

		queryString = "?CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&CONTROL_SELECTION_0_X1="
				+ CONTROL_SELECTION_0_X1
				+ "&CONTROL_SELECTION_0_X2="
				+ CONTROL_SELECTION_0_X2
				+ "&CONTROL_SELECTION_0_X3="
				+ CONTROL_SELECTION_0_X3
				+ "&INP1_CATEGORY_0="
				+ INP1_CATEGORY_0
				+ "&INP1_DATA_TYPE_CODE_0="
				+ INP1_DATA_TYPE_CODE_0
				+ "&INP1_DFT_VALUE_0="
				+ INP1_DFT_VALUE_0
				+ "&INP1_MAND_IND_0="
				+ INP1_MAND_IND_0
				+ "&INP1_PARM_NAME_0="
				+ INP1_PARM_NAME_0
				+ "&INP1_PROMPT_0="
				+ INP1_PROMPT_0
				+ "&INP1_SEQNBR_0="
				+ INP1_SEQNBR_0
				+ "&INP1_USER_CHANGEABLE_IND_0="
				+ INP1_USER_CHANGEABLE_IND_0
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&TRANSACTION_CODE_0="
				+ TRANSACTION_CODE_0
				+ "&TXN1_CHOICE_TYPE_0="
				+ TXN1_CHOICE_TYPE_0
				+ "&TXN1_CODE_0="
				+ TXN1_CODE_0
				+ "&TXN1_CODE_0_X1="
				+ TXN1_CODE_0_X1
				+ "&TXN1_DESCR_0="
				+ TXN1_DESCR_0
				+ "&TXN1_FORM_VERSION_0="
				+ TXN1_FORM_VERSION_0
				+ "&TXN1_MENU_CHOICE_SEQNBR_0="
				+ TXN1_MENU_CHOICE_SEQNBR_0
				+ "&TXN1_MENU_CODE_0="
				+ TXN1_MENU_CODE_0
				+ "&TXN1_MOD_SHORT_NAME_0="
				+ TXN1_MOD_SHORT_NAME_0
				+ "&TXN1_MOD_SHORT_NAME_0_X1="
				+ TXN1_MOD_SHORT_NAME_0_X1
				+ "&TXN1_REPT_MOD_SHORT_NAME_0="
				+ TXN1_REPT_MOD_SHORT_NAME_0
				+ "&USAULM1_UPDATE_IND_0="
				+ USAULM1_UPDATE_IND_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for LUMA service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void preReqInDatabase() throws SQLException {
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForLUMAPreReq")[1],TXN1_MOD_SHORT_NAME_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(
					Status.INFO,
					"Validating existence of data in the Database for pre-requisite",
					"Record exists in the Database for pre-requisite", false);

		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Validating existence of data in the Database for pre-requisite",
					"No Record exists in the Database for pre-requisite", false);
		}

		/*queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForLUMA")[1],TXN1_CODE_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"Record exists in the Database", false);
			while (queryResultSet.next()) {

				queryResultSet = DB.executeQuery(General.dbInstance,
						Stock.getTestQuery("queryForLUMADelete")[1],TXN1_CODE_0);

				Reporter.logEvent(Status.INFO, "Delete from Database",
						"Record deleted from Database.", false);
			}

		} else {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database",
					"No Record exists in the Database", false);
		}*/
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
			Reporter.logEvent(Status.PASS, "Run LUMA service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run LUMA service",
					"Running Failed:", false);
		}
	}

	public String validateOutput() throws SQLException {
		String Error = doc.getElementsByTagName("Error").item(0)
				.getTextContent();
		String rmnc_id = null;
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
		return rmnc_id;
	}

	public void validateInDatabase() throws SQLException {
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForLUMA")[1],TXN1_CODE_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"Record exists in the Database.", false);
			while (queryResultSet.next()) {

				Reporter.logEvent(Status.PASS,
						"Validating existence of data in the Database",
						"Record exists in the Database.", false);

				/*
				 * Reporter.logEvent( Status.INFO, "Values from Database",
				 * "RMNC_ID: " + queryResultSet.getString("RMNC_ID") +
				 * "\nAG_ID: " + queryResultSet.getString("AG_ID") + "\nGC_ID: "
				 * + queryResultSet.getString("GC_ID") + "\nSDMT_CODE: " +
				 * queryResultSet.getString("SDMT_CODE") + "\nAMOUNT: " +
				 * queryResultSet.getString("AMOUNT") + "\nEFFDATE: " +
				 * queryResultSet.getString("CASH_EFFDATE") + "\nSTATUS_CODE: "
				 * + queryResultSet .getString("PROCESS_STATUS_CODE"), false);
				 */
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"No Record exists in the Database", false);
		}
	}
}
