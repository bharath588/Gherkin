package pageobject.KWRK;

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

public class KWRK_Work_Participant_Data_Table_Cleanup {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/KWRK_Work_Participant_Data_Table_Cleanup_record_deletion";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String WORK_PARTICIPANT_DATA_GA_ID_0;
	String WORK_PARTICIPANT_DATA_DELETE_ROW_12;

	

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

	public void setWORK_PARTICIPANT_DATA_GA_ID_0(
			String wORK_PARTICIPANT_DATA_GA_ID_0) {
		WORK_PARTICIPANT_DATA_GA_ID_0 = wORK_PARTICIPANT_DATA_GA_ID_0;
	}

	public void setWORK_PARTICIPANT_DATA_DELETE_ROW_12(
			String wORK_PARTICIPANT_DATA_DELETE_ROW_12) {
		WORK_PARTICIPANT_DATA_DELETE_ROW_12 = wORK_PARTICIPANT_DATA_DELETE_ROW_12;
	}

	public void setServiceParameters() throws SQLException {

		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setWORK_PARTICIPANT_DATA_GA_ID_0(Stock.GetParameterValue("WORK_PARTICIPANT_DATA_GA_ID_0"));
		this.setWORK_PARTICIPANT_DATA_DELETE_ROW_12(Stock.GetParameterValue("WORK_PARTICIPANT_DATA_DELETE_ROW_12"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&WORK_PARTICIPANT_DATA_GA_ID_0="
				+ WORK_PARTICIPANT_DATA_GA_ID_0
				+ "&WORK_PARTICIPANT_DATA_DELETE_ROW_12="
				+ WORK_PARTICIPANT_DATA_DELETE_ROW_12
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for KWRK service",
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
			Reporter.logEvent(Status.PASS, "Run KWRK service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run KWRK service",
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
					"CONTROL_MENU_TITLE_0: "
							+ doc.getElementsByTagName("CONTROL_MENU_TITLE_0")
									.item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException {
		String ssn="700010054";
		ResultSet queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForKWRK")[1], this.WORK_PARTICIPANT_DATA_GA_ID_0,ssn);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database for Query",
					"Record exists in the Database\nTable for Query and did not get deleted", false);
			
		} else {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database for Query",
					"Record doesn't exists in the Database for Query and got successfully deleted", false);
		}
	}
}
