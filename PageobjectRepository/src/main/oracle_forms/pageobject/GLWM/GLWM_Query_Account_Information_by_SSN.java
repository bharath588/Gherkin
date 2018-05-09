package pageobject.GLWM;

import java.net.URLDecoder;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class GLWM_Query_Account_Information_by_SSN {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/GQ19GLWM_Query_Account_Information_by_SSN";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CFG_CONTROL_PRIME_SSN_0;
	String CFG_CONTROL_TRF_EFFDATE_0;

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
	
	public void setCFG_CONTROL_PRIME_SSN_0(String cFG_CONTROL_PRIME_SSN_0) {
		CFG_CONTROL_PRIME_SSN_0 = cFG_CONTROL_PRIME_SSN_0;
	}

	public void setCFG_CONTROL_TRF_EFFDATE_0(String cFG_CONTROL_TRF_EFFDATE_0) {
		CFG_CONTROL_TRF_EFFDATE_0 = cFG_CONTROL_TRF_EFFDATE_0;
	}

	public void setServiceParameters() throws SQLException {
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCFG_CONTROL_PRIME_SSN_0(Stock
				.GetParameterValue("CFG_CONTROL_PRIME_SSN_0"));
		this.setCFG_CONTROL_TRF_EFFDATE_0(Stock.GetParameterValue("CFG_CONTROL_TRF_EFFDATE_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&CFG_CONTROL_PRIME_SSN_0="
				+ CFG_CONTROL_PRIME_SSN_0
				+ "&CFG_CONTROL_TRF_EFFDATE_0="
				+ CFG_CONTROL_TRF_EFFDATE_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=ture&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for UPPP service",
				this.queryString.replaceAll("&", "\n"), false);

	}

	public void runService() throws SQLException {
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
			Reporter.logEvent(Status.PASS, "Run  UPPP service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run UPPP service",
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
					"Values from Auraplayer: ",
							"\nStatusBarMessages: "
							+ doc.getElementsByTagName(
									"StatusBarMessages").item(0)
									.getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
}
