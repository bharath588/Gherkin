package pageobject.GTRB;

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

public class GTRB_Change_group_Transfer_Basic_Rule_Info {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/GTRB_Change_group_Transfer_Basic_Rule_Info";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String TRF_BASIC_RULE_ALLOW_TRF_NBR_0;
	String TRF_BASIC_RULE_ALLOW_TRF_NBR_0_X1;

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

	
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	
	

	public void setTRF_BASIC_RULE_ALLOW_TRF_NBR_0(
			String tRF_BASIC_RULE_ALLOW_TRF_NBR_0) {
		TRF_BASIC_RULE_ALLOW_TRF_NBR_0 = tRF_BASIC_RULE_ALLOW_TRF_NBR_0;
	}

	public void setTRF_BASIC_RULE_ALLOW_TRF_NBR_0_X1(
			String tRF_BASIC_RULE_ALLOW_TRF_NBR_0_X1) {
		TRF_BASIC_RULE_ALLOW_TRF_NBR_0_X1 = tRF_BASIC_RULE_ALLOW_TRF_NBR_0_X1;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock
				.GetParameterValue("GET_GA_GA_ID_0"));
		this.setTRF_BASIC_RULE_ALLOW_TRF_NBR_0(Stock
				.GetParameterValue("TRF_BASIC_RULE_ALLOW_TRF_NBR_0"));
		this.setTRF_BASIC_RULE_ALLOW_TRF_NBR_0_X1(Stock
				.GetParameterValue("TRF_BASIC_RULE_ALLOW_TRF_NBR_0_X1"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&GET_GA_GA_ID_0="
				+ GET_GA_GA_ID_0
				+ "&TRF_BASIC_RULE_ALLOW_TRF_NBR_0="
				+ TRF_BASIC_RULE_ALLOW_TRF_NBR_0
				+ "&TRF_BASIC_RULE_ALLOW_TRF_NBR_0="
				+ TRF_BASIC_RULE_ALLOW_TRF_NBR_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for GTRB service",
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
			Reporter.logEvent(Status.PASS, "Run GTRB service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run GTRB service",
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
			Reporter.logEvent(Status.INFO,
					"Fetching data from the Database: ",
					"Data from the Database: " + "\nGROUP_HEADER_GC_ID_0: " + doc.getElementsByTagName("GROUP_HEADER_GC_ID_0").item(0).getTextContent()
					+ "\nGROUP_HEADER_GC_NAME_0: " + doc.getElementsByTagName("GROUP_HEADER_GC_NAME_0").item(0).getTextContent()
					+ "\nGROUP_HEADER_GA_ID_0: " + doc.getElementsByTagName("GROUP_HEADER_GA_ID_0").item(0).getTextContent()
					+ "\nGROUP_HEADER_PLAN_NAME_0: " + doc.getElementsByTagName("GROUP_HEADER_PLAN_NAME_0").item(0).getTextContent()
					+ "\nGROUP_HEADER_PROD_ID_0: " + doc.getElementsByTagName("GROUP_HEADER_PROD_ID_0").item(0).getTextContent(),
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
				Stock.getTestQuery("queryForCVGA")[1]);

		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database",
					"Record exists in the Database", false);

			
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}

	}
}

