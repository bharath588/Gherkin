package pageobject.NMTL;

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

public class NMTL_new_money_total {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/NMTL_new_money_total";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INCOMING_GA_ID_0;
	String INCOMING_START_DATE_0;
	String INCOMING_END_DATE_0;
	String INCOMING_CHECK_BOX_CNT_0;
	String INCOMING_CHECK_BOX_ROL_0;
	String INCOMING_CHECK_BOX_CNV_0;

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

	public void setINCOMING_GA_ID_0(String iNCOMING_GA_ID_0) {
		INCOMING_GA_ID_0 = iNCOMING_GA_ID_0;
	}

	public void setINCOMING_START_DATE_0(String iNCOMING_START_DATE_0) {
		INCOMING_START_DATE_0 = iNCOMING_START_DATE_0;
	}

	public void setINCOMING_END_DATE_0(String iNCOMING_END_DATE_0) {
		INCOMING_END_DATE_0 = iNCOMING_END_DATE_0;
	}

	public void setINCOMING_CHECK_BOX_CNT_0(String iNCOMING_CHECK_BOX_CNT_0) {
		INCOMING_CHECK_BOX_CNT_0 = iNCOMING_CHECK_BOX_CNT_0;
	}

	public void setINCOMING_CHECK_BOX_ROL_0(String iNCOMING_CHECK_BOX_ROL_0) {
		INCOMING_CHECK_BOX_ROL_0 = iNCOMING_CHECK_BOX_ROL_0;
	}

	public void setINCOMING_CHECK_BOX_CNV_0(String iNCOMING_CHECK_BOX_CNV_0) {
		INCOMING_CHECK_BOX_CNV_0 = iNCOMING_CHECK_BOX_CNV_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINCOMING_GA_ID_0(Stock
				.GetParameterValue("INCOMING_GA_ID_0"));
		this.setINCOMING_START_DATE_0(Stock.GetParameterValue("INCOMING_START_DATE_0"));
		this.setINCOMING_END_DATE_0(Stock.GetParameterValue("INCOMING_END_DATE_0"));
		this.setINCOMING_CHECK_BOX_CNT_0(Stock.GetParameterValue("INCOMING_CHECK_BOX_CNT_0"));
		this.setINCOMING_CHECK_BOX_ROL_0(Stock
				.GetParameterValue("INCOMING_CHECK_BOX_ROL_0"));
		this.setINCOMING_CHECK_BOX_CNV_0(Stock
				.GetParameterValue("INCOMING_CHECK_BOX_CNV_0"));
				
		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&INCOMING_GA_ID_0="
				+ INCOMING_GA_ID_0
				+"&INCOMING_START_DATE_0="
				+ INCOMING_START_DATE_0
				+ "&INCOMING_END_DATE_0="
				+ INCOMING_END_DATE_0
				+ "&INCOMING_CHECK_BOX_CNT_0="
				+ INCOMING_CHECK_BOX_CNT_0
				+ "&INCOMING_CHECK_BOX_ROL_0="
				+ INCOMING_CHECK_BOX_ROL_0
				+ "&INCOMING_CHECK_BOX_ROL_0="
				+ INCOMING_CHECK_BOX_ROL_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for NMLT service",
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
			Reporter.logEvent(Status.PASS, "Run NMLT service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run NMLT service",
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
			Reporter.logEvent(Status.INFO, "Values from Response: ",
					"\nCONTROL_MENU_TITLE_0: "+doc.getElementsByTagName("CONTROL_MENU_TITLE_0").item(0).getTextContent()
					+"\nTRANSACTION_DESCR_0"+doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);

		}
	}

	
}
