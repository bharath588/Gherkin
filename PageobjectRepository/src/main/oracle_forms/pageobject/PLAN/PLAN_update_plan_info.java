package pageobject.PLAN;

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

public class PLAN_update_plan_info {

	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/PLAN_Update_plan_info";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private int queryResultSet;
	private ResultSet queryResultSet2;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String PLAN1_PLAN_INFO_0;
	String PLAN1_PLAN_INFO_0_X1;

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

	public void setPLAN1_PLAN_INFO_0(String pLAN1_PLAN_INFO_0) {
		PLAN1_PLAN_INFO_0 = pLAN1_PLAN_INFO_0;
	}

	public void setPLAN1_PLAN_INFO_0_X1(String pLAN1_PLAN_INFO_0_X1) {
		PLAN1_PLAN_INFO_0_X1 = pLAN1_PLAN_INFO_0_X1;
	}

	public void setServiceParameters() {
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setPLAN1_PLAN_INFO_0(Stock.GetParameterValue("PLAN1_PLAN_INFO_0"));
		this.setPLAN1_PLAN_INFO_0_X1(Stock
				.GetParameterValue("PLAN1_PLAN_INFO_0_X1"));

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
				+ "&PLAN1_PLAN_INFO_0="
				+ PLAN1_PLAN_INFO_0
				+ "&PLAN1_PLAN_INFO_0_X1="
				+ PLAN1_PLAN_INFO_0_X1
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for PLAN service",
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
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run PLAN service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PLAN service",
					"Running Failed:", false);
		}
	}

	public void validateOutput() {
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

	public void updateInDatabase() throws SQLException {

		try {
			queryResultSet = DB.executeUpdate(General.dbInstance,
					Stock.getTestQuery("queryForDeletePLAN")[1], this.GET_GA_GA_ID_0);
			System.out.println(queryResultSet);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Reporter.logEvent(Status.INFO, "Updating values From DATABASE.",
				"Updating values From DATABASE.", false);
	}

	public void validateInDatabase() throws SQLException {

		queryResultSet2 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForPLAN")[1], this.GET_GA_GA_ID_0);

		if (DB.getRecordSetCount(queryResultSet2) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating Records exists in Database",
					"Records exists in Database", false);

			while (queryResultSet2.next()) {
				String plan_infoDB = queryResultSet2.getString("PLAN_INFO");
				String plan_infoInput = this.PLAN1_PLAN_INFO_0_X1;
				
				System.out.println("plan_infoDB: "+plan_infoDB);
				System.out.println("plan_infoInput: "+plan_infoInput);

				if (plan_infoDB.equalsIgnoreCase(plan_infoInput)) {
					Reporter.logEvent(
							Status.PASS,
							"Validating values From DATABASE matches with input parameters",
							"Values from the DATABASE matches with input parameter",
							false);
				}

				else {
					Reporter.logEvent(
							Status.FAIL,
							"Validating values From DATABASE matches with input parameters",
							"Values from the DATABASE matches with input parameter",
							false);
				}
			}

		}
	}

}