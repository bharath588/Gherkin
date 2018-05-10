package pageobject.PART;

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

public class PART_AllocationCHG {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/PART_ALLOC_CHG";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_ADD_CHG_DATE_DISP_0;
	String CONTROL_ALLOC_BLOCK_STATUS_0;
	String PART_ALLOCS_PERCENT_DISPLAY_0;
	String PART_ALLOCS_PERCENT_DISPLAY_1;
	String PART_ALLOCS_PERCENT_DISPLAY_2;
	String PART_ALLOCS_PERCENT_DISPLAY_3;
	String QUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	String CONTROL_SELECTION_0;
	String CONTROL_SSN_DISPL_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;

	public void setCONTROL_ADD_CHG_DATE_DISP_0(
			String cONTROL_ADD_CHG_DATE_DISP_0) {
		CONTROL_ADD_CHG_DATE_DISP_0 = cONTROL_ADD_CHG_DATE_DISP_0;
	}

	public void setCONTROL_ALLOC_BLOCK_STATUS_0(
			String cONTROL_ALLOC_BLOCK_STATUS_0) {
		CONTROL_ALLOC_BLOCK_STATUS_0 = cONTROL_ALLOC_BLOCK_STATUS_0;
	}

	public void setPART_ALLOCS_PERCENT_DISPLAY_0(
			String pART_ALLOCS_PERCENT_DISPLAY_0) {
		PART_ALLOCS_PERCENT_DISPLAY_0 = pART_ALLOCS_PERCENT_DISPLAY_0;
	}

	public void setPART_ALLOCS_PERCENT_DISPLAY_1(
			String pART_ALLOCS_PERCENT_DISPLAY_1) {
		PART_ALLOCS_PERCENT_DISPLAY_1 = pART_ALLOCS_PERCENT_DISPLAY_1;
	}

	public void setPART_ALLOCS_PERCENT_DISPLAY_2(
			String pART_ALLOCS_PERCENT_DISPLAY_2) {
		PART_ALLOCS_PERCENT_DISPLAY_2 = pART_ALLOCS_PERCENT_DISPLAY_2;
	}

	public void setPART_ALLOCS_PERCENT_DISPLAY_3(
			String pART_ALLOCS_PERCENT_DISPLAY_3) {
		PART_ALLOCS_PERCENT_DISPLAY_3 = pART_ALLOCS_PERCENT_DISPLAY_3;
	}

	public void setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(
			String qUERY_CHG_MULTIPLE_SSN_EXT_LOV) {
		QUERY_CHG_MULTIPLE_SSN_EXT_LOV = qUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	}

	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}

	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
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

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SSN_DISPL_0(Stock
				.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setCONTROL_ADD_CHG_DATE_DISP_0(Stock
				.GetParameterValue("CONTROL_ADD_CHG_DATE_DISP_0"));
		this.setCONTROL_ALLOC_BLOCK_STATUS_0(Stock
				.GetParameterValue("CONTROL_ALLOC_BLOCK_STATUS_0"));
		this.setPART_ALLOCS_PERCENT_DISPLAY_0(Stock
				.GetParameterValue("PART_ALLOCS_PERCENT_DISPLAY_0"));
		this.setPART_ALLOCS_PERCENT_DISPLAY_1(Stock
				.GetParameterValue("PART_ALLOCS_PERCENT_DISPLAY_1"));
		this.setPART_ALLOCS_PERCENT_DISPLAY_2(Stock
				.GetParameterValue("PART_ALLOCS_PERCENT_DISPLAY_2"));
		this.setPART_ALLOCS_PERCENT_DISPLAY_3(Stock
				.GetParameterValue("PART_ALLOCS_PERCENT_DISPLAY_3"));
		this.setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(Stock
				.GetParameterValue("QUERY_CHG_MULTIPLE_SSN_EXT_LOV"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&CONTROL_SSN_DISPL_0="
				+ CONTROL_SSN_DISPL_0
				+ "&CONTROL_ALLOC_BLOCK_STATUS_0="
				+ CONTROL_ALLOC_BLOCK_STATUS_0
				+ "&CONTROL_ADD_CHG_DATE_DISP_0="
				+ CONTROL_ADD_CHG_DATE_DISP_0
				+ "&PART_ALLOCS_PERCENT_DISPLAY_0="
				+ PART_ALLOCS_PERCENT_DISPLAY_0
				+ "&PART_ALLOCS_PERCENT_DISPLAY_1="
				+ PART_ALLOCS_PERCENT_DISPLAY_1
				+ "&PART_ALLOCS_PERCENT_DISPLAY_2="
				+ PART_ALLOCS_PERCENT_DISPLAY_2
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for PART service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			// System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run PART service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PART service",
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
					"CONTROL_GA_ID_0: "
							+ doc.getElementsByTagName("CONTROL_GA_ID_0")
									.item(0).getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException {

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForPART2")[1],
				this.CONTROL_SSN_DISPL_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating Records exists in Database",
					"Records exists in Database.", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating Records exists in Database",
					"Record does not Exist For SSN: "
							+ this.CONTROL_SSN_DISPL_0, false);
		}
	}
}
