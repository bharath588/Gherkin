package pageobject.TRST;

import java.io.File;
import java.io.IOException;
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

public class TRST_Transfer_Restriction_Rule_Report {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/TRST_Transfer_Restriction_Rule_Report_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INP1_DISP_DFT_VALUE_0;
	String INP1_DISP_DFT_VALUE_0_X1;
	String INP1_DISP_DFT_VALUE_3;
	

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

	public void setINP1_DISP_DFT_VALUE_0_X1(String iNP1_DISP_DFT_VALUE_0_X1) {
		INP1_DISP_DFT_VALUE_0_X1 = iNP1_DISP_DFT_VALUE_0_X1;
	}

	public void setINP1_DISP_DFT_VALUE_3(String iNP1_DISP_DFT_VALUE_3) {
		INP1_DISP_DFT_VALUE_3 = iNP1_DISP_DFT_VALUE_3;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINP1_DISP_DFT_VALUE_0(Stock
				.GetParameterValue("INP1_DISP_DFT_VALUE_0"));
		this.setINP1_DISP_DFT_VALUE_0_X1(Stock
				.GetParameterValue("INP1_DISP_DFT_VALUE_0_X1"));
		this.setINP1_DISP_DFT_VALUE_3(Stock
				.GetParameterValue("INP1_DISP_DFT_VALUE_3"));

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
				+ "&INP1_DISP_DFT_VALUE_0_X1="
				+ INP1_DISP_DFT_VALUE_0_X1
				+ "&INP1_DISP_DFT_VALUE_3="
				+ INP1_DISP_DFT_VALUE_3
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for TRST service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void deleteOutputFile() throws IOException {

		File file = new File(Stock.GetParameterValue("Location"));
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.getName().startsWith("GQ19TRST") || f.getName().startsWith("TRST")) {
				f.delete();
				Reporter.logEvent(Status.PASS, "Deleting all the files: ",
						"Files deleted Successfully", false);
			}
		}
		Reporter.logEvent(Status.INFO, "Deleting all the TRST files: ",
				"Deleting all the files", false);
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
			Reporter.logEvent(Status.PASS, "Run TRST service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run TRST service",
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
			Reporter.logEvent(Status.PASS, "From Response: ", "INP1_DISP_DFT_VALUE_5: "
					+ doc.getElementsByTagName("INP1_DISP_DFT_VALUE_5").item(0).getTextContent()
					+"\nCODES_MARKER_0: "+ doc.getElementsByTagName("CODES_MARKER_0").item(0).getTextContent()
					+"\nMO1_DISP_DFT_PRIN_CODE_0: "+ doc.getElementsByTagName("MO1_DISP_DFT_PRIN_CODE_0").item(0).getTextContent(), false);
			

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	public void validateOutputFileCreated() throws IOException, InterruptedException {
		Thread.sleep(5000);
		File file = new File(Stock.GetParameterValue("Location")); 
		File[] files = file.listFiles(); 
		for (File f : files) { 
			if(f.getName().startsWith("GQ19TRST") || f.getName().startsWith("TRST")) { 
				Reporter.logEvent(Status.PASS,
				  "Verifying the existance of file: ",
				  "Output file created Successfully", false); 
				} 
			}
		Reporter.logEvent(Status.INFO, "Verifying all the TRST files: ",
				"Verifying all the files", false);
	}
}
