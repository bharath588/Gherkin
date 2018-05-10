package pageobject.GFTR;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class GFTR_GROUP_FUND_ELIMINATION {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/GFTR_GROUP_FUND_ELIMINATION";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;
	public ResultSet queryResultSet2 = null;
	String eventID;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GROUP_FUND_TRANSFER_1_GA_ID_0;
	String GROUP_FUND_TRANSFER_1_EFFDATE_0;
	String param9340;
	String TO_FUNDS_LOV;

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

	public void setGROUP_FUND_TRANSFER_1_GA_ID_0(
			String gROUP_FUND_TRANSFER_1_GA_ID_0) {
		GROUP_FUND_TRANSFER_1_GA_ID_0 = gROUP_FUND_TRANSFER_1_GA_ID_0;
	}

	public void setGROUP_FUND_TRANSFER_1_EFFDATE_0(
			String gROUP_FUND_TRANSFER_1_EFFDATE_0) {
		GROUP_FUND_TRANSFER_1_EFFDATE_0 = gROUP_FUND_TRANSFER_1_EFFDATE_0;
	}

	public void setParam9340(String param9340) {
		this.param9340 = param9340;
	}

	public void setTO_FUNDS_LOV(String tO_FUNDS_LOV) {
		TO_FUNDS_LOV = tO_FUNDS_LOV;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGROUP_FUND_TRANSFER_1_GA_ID_0(Stock
				.GetParameterValue("GROUP_FUND_TRANSFER_1_GA_ID_0"));
		this.setGROUP_FUND_TRANSFER_1_EFFDATE_0(Stock
				.GetParameterValue("GROUP_FUND_TRANSFER_1_EFFDATE_0"));
		this.setParam9340(Stock.GetParameterValue("param9340"));
		this.setTO_FUNDS_LOV(Stock.GetParameterValue("TO_FUNDS_LOV"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&GROUP_FUND_TRANSFER_1_GA_ID_0="
				+ GROUP_FUND_TRANSFER_1_GA_ID_0
				+ "&GROUP_FUND_TRANSFER_1_EFFDATE_0="
				+ GROUP_FUND_TRANSFER_1_EFFDATE_0
				+ "&param9340="
				+ param9340
				+ "&TO_FUNDS_LOV="
				+ TO_FUNDS_LOV
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for GFTR service",
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
			Reporter.logEvent(Status.PASS, "Run GFTR service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run GFTR service",
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
}
