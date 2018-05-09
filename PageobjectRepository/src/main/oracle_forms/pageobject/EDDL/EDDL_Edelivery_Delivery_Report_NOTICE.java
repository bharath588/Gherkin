package pageobject.EDDL;

import generallib.General;

import java.io.File;
import java.io.IOException;
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

public class EDDL_Edelivery_Delivery_Report_NOTICE {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/EDDL_Order_Delivery_Report_NOTICE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	// private ResultSet queryResultSet;
	private ResultSet queryResultSetPreReq1;
	private ResultSet queryResultSetPreReq2;
	private ResultSet queryResultSetPreReq3;
	private ResultSet queryResultSetPreReq4;
	private ResultSet queryResultSetPreReq5;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INP1_DISP_DFT_VALUE_0;
	String MO1_COPY_NBR_0;
	String MO1_DISP_DFT_PRIN_CODE_0;

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

	public void setINP1_DISP_DFT_VALUE_0(String iNP1_DISP_DFT_VALUE_0) {
		INP1_DISP_DFT_VALUE_0 = iNP1_DISP_DFT_VALUE_0;
	}

	public void setMO1_COPY_NBR_0(String mO1_COPY_NBR_0) {
		MO1_COPY_NBR_0 = mO1_COPY_NBR_0;
	}

	public void setMO1_DISP_DFT_PRIN_CODE_0(String mO1_DISP_DFT_PRIN_CODE_0) {
		MO1_DISP_DFT_PRIN_CODE_0 = mO1_DISP_DFT_PRIN_CODE_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINP1_DISP_DFT_VALUE_0(Stock
				.GetParameterValue("INP1_DISP_DFT_VALUE_0"));
		this.setMO1_COPY_NBR_0(Stock.GetParameterValue("MO1_COPY_NBR_0"));
		this.setMO1_DISP_DFT_PRIN_CODE_0(Stock
				.GetParameterValue("MO1_DISP_DFT_PRIN_CODE_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&INP1_DISP_DFT_VALUE_0="
				+ INP1_DISP_DFT_VALUE_0
				+ "&MO1_COPY_NBR_0"
				+ MO1_COPY_NBR_0
				+ "&MO1_DISP_DFT_PRIN_CODE_0"
				+ MO1_DISP_DFT_PRIN_CODE_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for EDDS service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void validatePrereqQueriesInDataBase() throws SQLException {
		// Some Values have been harcoded. Remove them.
		queryResultSetPreReq1 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForEDDLPre1")[1],
				this.CONTROL_SELECTION_0, "AURATEST");
		queryResultSetPreReq2 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForEDDLPre2")[1],
				this.CONTROL_SELECTION_0);
		queryResultSetPreReq3 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForEDDLPre3")[1], "GQ19EDEL");
		queryResultSetPreReq4 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForEDDLPre4")[1],
				this.CONTROL_SELECTION_0);
		queryResultSetPreReq5 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForEDDLPre5")[1],
				this.INP1_DISP_DFT_VALUE_0);

		if (DB.getRecordSetCount(queryResultSetPreReq1) > 0
				&& DB.getRecordSetCount(queryResultSetPreReq2) > 0
				&& DB.getRecordSetCount(queryResultSetPreReq4) > 0
				&& DB.getRecordSetCount(queryResultSetPreReq5) > 0
				&& DB.getRecordSetCount(queryResultSetPreReq3) > 0) {
			Reporter.logEvent(
					Status.PASS,
					"PRE-Requisites: Validating existence of data in the Database",
					"Record exists in the Database", false);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"PRE-Requisites: Validating existence of data in the Database",
					"Record does not exist in the Database", false);
		}
	}

	public void deleteOutputFile() throws IOException {
		File file = new File(Stock.GetParameterValue("Location"));
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isFile() && f.exists()) {
				f.delete();
				Reporter.logEvent(Status.PASS, "Deleting all the files: ",
						"Files deleted Successfully", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Deleting all the files: ",
						"Files not Deleted", false);
			}
		}

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
			Reporter.logEvent(Status.PASS, "Run EDDL service",
					"Execution Done Successfully!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run EDDL service",
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
					Status.PASS,
					"From Response: ",
					"INP1_DISP_DFT_VALUE_1: "
							+ doc.getElementsByTagName("INP1_DISP_DFT_VALUE_1")
									.item(0).getTextContent()
							+ "\nINP1_DISP_DFT_VALUE_2: "
							+ doc.getElementsByTagName("INP1_DISP_DFT_VALUE_2")
									.item(0).getTextContent()
							+ "\nINP1_DISP_DFT_VALUE_3: "
							+ doc.getElementsByTagName("INP1_DISP_DFT_VALUE_3"),
					false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateOutputFileCreated() throws IOException {
		File file = new File(Stock.GetParameterValue("Location"));
		if (file.exists()) {
			Reporter.logEvent(Status.PASS, "Verifying the existance of file: ",
					"Output file created Successfully", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verifying the existance of file: ",
					"Output file not created", false);
		}

	}

}
