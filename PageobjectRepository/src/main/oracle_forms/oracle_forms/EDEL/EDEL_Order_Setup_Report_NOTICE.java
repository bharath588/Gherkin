package pageobject.EDEL;

import generallib.General;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class EDEL_Order_Setup_Report_NOTICE {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/EDEL_Order_Setup_Report_NOTICE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet1;

	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String INP1_DISP_DFT_VALUE_0;
	String INP1_DISP_DFT_VALUE_1;
	String MO1_COPY_NBR_0;
	String MO1_DISP_DFT_PRIN_CODE_0;

	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}

	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}

	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}

	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}

	public void setINP1_DISP_DFT_VALUE_0(String iNP1_DISP_DFT_VALUE_0) {
		INP1_DISP_DFT_VALUE_0 = iNP1_DISP_DFT_VALUE_0;
	}

	public void setINP1_DISP_DFT_VALUE_1(String iNP1_DISP_DFT_VALUE_1) {
		INP1_DISP_DFT_VALUE_1 = iNP1_DISP_DFT_VALUE_1;
	}

	public void setMO1_COPY_NBR_0(String mO1_COPY_NBR_0) {
		MO1_COPY_NBR_0 = mO1_COPY_NBR_0;
	}

	public void setMO1_DISP_DFT_PRIN_CODE_0(String mO1_DISP_DFT_PRIN_CODE_0) {
		MO1_DISP_DFT_PRIN_CODE_0 = mO1_DISP_DFT_PRIN_CODE_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINP1_DISP_DFT_VALUE_0(Stock
				.GetParameterValue("INP1_DISP_DFT_VALUE_0"));
		this.setINP1_DISP_DFT_VALUE_1(Stock
				.GetParameterValue("INP1_DISP_DFT_VALUE_1"));
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
				+ "&INP1_DISP_DFT_VALUE_1="
				+ INP1_DISP_DFT_VALUE_1
				+ "&MO1_COPY_NBR_0="
				+ MO1_COPY_NBR_0
				+ "&MO1_DISP_DFT_PRIN_CODE_0="
				+ MO1_DISP_DFT_PRIN_CODE_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO,
				"Prepare test data for EDEL_Order_Setup_Report_NOTICE service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void preDBValidation() {
		queryResultSet = DB.executeQuery(General.dbInstance, Stock
				.getTestQuery("queryForEDELOrderSetUp1")[1],
				this.CONTROL_SELECTION_0, this.LOGON_USERNAME.toUpperCase()
						.trim());
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(
					Status.PASS,
					"Validating access of user id: "
							+ this.LOGON_USERNAME.toUpperCase().trim()
							+ " in the Database" + "\nD_IN02",
					this.LOGON_USERNAME.toUpperCase().trim()
							+ " user id has access to transaction code EDSU and access to create files.",
					false);

		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Validating acces of user id: "
							+ this.LOGON_USERNAME.toUpperCase().trim()
							+ " in the Database",
					this.LOGON_USERNAME.toUpperCase().trim()
							+ " user id does not have access to transaction code EDSU and access to create files.",
					false);
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
			Reporter.logEvent(Status.PASS, "Run EDEL service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run EDEL service",
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
					"INP1_DISP_DFT_VALUE_4: "
							+ doc.getElementsByTagName("INP1_DISP_DFT_VALUE_4")
									.item(0).getTextContent(), false);

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

	public void validateInDatabase() throws SQLException, ParseException {
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForEDELOrderSetUp2")[1]);
		queryResultSet1 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForEDELOrderSetUp3")[1]);

		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"validateInDatabase: Record exists in the Database", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database for Query: "+queryResultSet,
					"validateInDatabase: No Record exists in the Database",
					false);
		}
		if (DB.getRecordSetCount(queryResultSet1) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database for Query: "+queryResultSet1,
					"validateInDatabase: Record exists in the Database", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"validateInDatabase: No Record exists in the Database",
					false);
		}


	}
}