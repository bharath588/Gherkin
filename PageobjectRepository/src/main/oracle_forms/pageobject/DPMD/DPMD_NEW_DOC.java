package pageobject.DPMD;

import generallib.General;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class DPMD_NEW_DOC {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/DPMD_NEW_DOC";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;

	String LOGON_USERNAME;
	String LOGON_PASSWORD;
	String LOGON_DATABASE;
	String CONTROL_SELECTION_0;
	String DMFT1_CONTENT_TYPE_CODE_0;
	String DMFT1_DESCR_0;
	String DMFT1_DOC_TEMPLATE_0;
	String DMFT1_DPS_SYSTEM_CODE_0;
	String DMFT1_REVIEW_DEFAULT_MEDIA_TYPE_0;
	String DMTY1_ARCHIVE_CODE_0;
	String DMTY1_ARCHIVE_PERD_0;
	String DMTY1_CATEGORY_CODE_0;
	String DMTY1_CODE_0;
	String DMTY1_DATA_RETENTION_DAYS_0;
	String DMTY1_DESCR_0;
	String DMTY1_DH_CODE_0;
	String DMTY1_DMOPTY_CODE_0;
	String DMTY1_DOC_CLASS_0;
	String DMTY1_REVIEW_DEFAULT_MEDIA_TYPE_0;
	String DMTY1_REVIEW_IND_0;
	String param9111;
	String param9112;

	

	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}

	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}

	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}

	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}

	public void setDMFT1_CONTENT_TYPE_CODE_0(String dMFT1_CONTENT_TYPE_CODE_0) {
		DMFT1_CONTENT_TYPE_CODE_0 = dMFT1_CONTENT_TYPE_CODE_0;
	}

	public void setDMFT1_DESCR_0(String dMFT1_DESCR_0) {
		DMFT1_DESCR_0 = dMFT1_DESCR_0;
	}

	public void setDMFT1_DOC_TEMPLATE_0(String dMFT1_DOC_TEMPLATE_0) {
		DMFT1_DOC_TEMPLATE_0 = dMFT1_DOC_TEMPLATE_0;
	}

	public void setDMFT1_DPS_SYSTEM_CODE_0(String dMFT1_DPS_SYSTEM_CODE_0) {
		DMFT1_DPS_SYSTEM_CODE_0 = dMFT1_DPS_SYSTEM_CODE_0;
	}

	public void setDMFT1_REVIEW_DEFAULT_MEDIA_TYPE_0(
			String dMFT1_REVIEW_DEFAULT_MEDIA_TYPE_0) {
		DMFT1_REVIEW_DEFAULT_MEDIA_TYPE_0 = dMFT1_REVIEW_DEFAULT_MEDIA_TYPE_0;
	}

	public void setDMTY1_ARCHIVE_CODE_0(String dMTY1_ARCHIVE_CODE_0) {
		DMTY1_ARCHIVE_CODE_0 = dMTY1_ARCHIVE_CODE_0;
	}

	public void setDMTY1_ARCHIVE_PERD_0(String dMTY1_ARCHIVE_PERD_0) {
		DMTY1_ARCHIVE_PERD_0 = dMTY1_ARCHIVE_PERD_0;
	}

	public void setDMTY1_CATEGORY_CODE_0(String dMTY1_CATEGORY_CODE_0) {
		DMTY1_CATEGORY_CODE_0 = dMTY1_CATEGORY_CODE_0;
	}

	public void setDMTY1_CODE_0(String dMTY1_CODE_0) {
		DMTY1_CODE_0 = dMTY1_CODE_0;
	}

	public void setDMTY1_DATA_RETENTION_DAYS_0(
			String dMTY1_DATA_RETENTION_DAYS_0) {
		DMTY1_DATA_RETENTION_DAYS_0 = dMTY1_DATA_RETENTION_DAYS_0;
	}

	public void setDMTY1_DESCR_0(String dMTY1_DESCR_0) {
		DMTY1_DESCR_0 = dMTY1_DESCR_0;
	}

	public void setDMTY1_DH_CODE_0(String dMTY1_DH_CODE_0) {
		DMTY1_DH_CODE_0 = dMTY1_DH_CODE_0;
	}

	public void setDMTY1_DMOPTY_CODE_0(String dMTY1_DMOPTY_CODE_0) {
		DMTY1_DMOPTY_CODE_0 = dMTY1_DMOPTY_CODE_0;
	}

	public void setDMTY1_DOC_CLASS_0(String dMTY1_DOC_CLASS_0) {
		DMTY1_DOC_CLASS_0 = dMTY1_DOC_CLASS_0;
	}

	public void setDMTY1_REVIEW_DEFAULT_MEDIA_TYPE_0(
			String dMTY1_REVIEW_DEFAULT_MEDIA_TYPE_0) {
		DMTY1_REVIEW_DEFAULT_MEDIA_TYPE_0 = dMTY1_REVIEW_DEFAULT_MEDIA_TYPE_0;
	}

	public void setDMTY1_REVIEW_IND_0(String dMTY1_REVIEW_IND_0) {
		DMTY1_REVIEW_IND_0 = dMTY1_REVIEW_IND_0;
	}
	public void setParam9111(String param9111) {
		this.param9111 = param9111;
	}

	public void setParam9112(String param9112) {
		this.param9112 = param9112;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setDMFT1_CONTENT_TYPE_CODE_0(Stock
				.GetParameterValue("DMFT1_CONTENT_TYPE_CODE_0"));
		this.setDMFT1_DESCR_0(Stock.GetParameterValue("DMFT1_DESCR_0"));
		this.setDMFT1_DOC_TEMPLATE_0(Stock
				.GetParameterValue("DMFT1_DOC_TEMPLATE_0"));
		this.setDMFT1_DPS_SYSTEM_CODE_0(Stock
				.GetParameterValue("DMFT1_DPS_SYSTEM_CODE_0"));
		this.setDMFT1_REVIEW_DEFAULT_MEDIA_TYPE_0(Stock
				.GetParameterValue("DMFT1_REVIEW_DEFAULT_MEDIA_TYPE_0"));
		this.setDMTY1_ARCHIVE_CODE_0(Stock
				.GetParameterValue("DMTY1_ARCHIVE_CODE_0"));
		this.setDMTY1_ARCHIVE_PERD_0(Stock
				.GetParameterValue("DMTY1_ARCHIVE_PERD_0"));
		this.setDMTY1_CATEGORY_CODE_0(Stock
				.GetParameterValue("DMTY1_CATEGORY_CODE_0"));
		this.setDMTY1_CODE_0(Stock.GetParameterValue("DMTY1_CODE_0"));
		this.setDMTY1_DATA_RETENTION_DAYS_0(Stock
				.GetParameterValue("DMTY1_DATA_RETENTION_DAYS_0"));
		this.setDMTY1_DESCR_0(Stock.GetParameterValue("DMTY1_DESCR_0"));
		this.setDMTY1_DH_CODE_0(Stock.GetParameterValue("DMTY1_DH_CODE_0"));
		this.setDMTY1_DMOPTY_CODE_0(Stock
				.GetParameterValue("DMTY1_DMOPTY_CODE_0"));
		this.setDMTY1_DOC_CLASS_0(Stock.GetParameterValue("DMTY1_DOC_CLASS_0"));
		this.setDMTY1_REVIEW_DEFAULT_MEDIA_TYPE_0(Stock
				.GetParameterValue("DMTY1_REVIEW_DEFAULT_MEDIA_TYPE_0"));
		this.setDMTY1_REVIEW_IND_0(Stock
				.GetParameterValue("DMTY1_REVIEW_IND_0"));
		this.setParam9111(Stock.GetParameterValue("Param9111"));
		this.setParam9112(Stock.GetParameterValue("Param9112"));

		queryString = "?CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&DMFT1_CONTENT_TYPE_CODE_0="
				+ DMFT1_CONTENT_TYPE_CODE_0
				+ "&DMFT1_DESCR_0="
				+ DMFT1_DESCR_0
				+ "&DMFT1_DOC_TEMPLATE_0="
				+ DMFT1_DOC_TEMPLATE_0
				+ "&DMFT1_DPS_SYSTEM_CODE_0="
				+ DMFT1_DPS_SYSTEM_CODE_0
				+ "&DMFT1_REVIEW_DEFAULT_MEDIA_TYPE_0="
				+ DMFT1_REVIEW_DEFAULT_MEDIA_TYPE_0
				+ "&DMTY1_ARCHIVE_CODE_0="
				+ DMTY1_ARCHIVE_CODE_0
				+ "&DMTY1_ARCHIVE_PERD_0="
				+ DMTY1_ARCHIVE_PERD_0
				+ "&DMTY1_CATEGORY_CODE_0="
				+ DMTY1_CATEGORY_CODE_0
				+ "&DMTY1_CODE_0="
				+ DMTY1_CODE_0
				+ "&DMTY1_DATA_RETENTION_DAYS_0="
				+ DMTY1_DATA_RETENTION_DAYS_0
				+ "&DMTY1_DESCR_0="
				+ DMTY1_DESCR_0
				+ "&DMTY1_DH_CODE_0="
				+ DMTY1_DH_CODE_0
				+ "&DMTY1_DMOPTY_CODE_0="
				+ DMTY1_DMOPTY_CODE_0
				+ "&DMTY1_DOC_CLASS_0="
				+ DMTY1_DOC_CLASS_0
				+ "&DMTY1_REVIEW_DEFAULT_MEDIA_TYPE_0="
				+ DMTY1_REVIEW_DEFAULT_MEDIA_TYPE_0
				+ "&DMTY1_REVIEW_IND_0="
				+ DMTY1_REVIEW_IND_0
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&param9111="
				+ param9111
				+ "&param9112="
				+ param9112
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for DPMD service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println("serviceURL: "+serviceURL);
			URL obj = new URL(serviceURL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			

			//print result
			System.out.println(response.toString());
			con.getContent();
			PrintWriter pw = new PrintWriter("response.txt");
			pw.println(response.toString().replaceAll("<></>", ""));
			pw.close();

			 BufferedReader br = new BufferedReader(new FileReader("response.txt"));
			 String line;
			 while((line = br.readLine()) != null)
			 {
			     System.out.println(line);
			 }
			 br.close();
			 
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File("response.txt"));
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run DPMD service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DPMD service", "Running Failed:", false);
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

	public void validateInDatabase() throws SQLException {

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForDPMD1")[1], this.DMTY1_CODE_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database for Query1",
					"Record exists in the Database\nTable for Query1", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database for Query1",
					"Record doesn't exists in the Database for Query1", false);
		}

		queryResultSet1 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForDPMD2")[1], this.DMTY1_CODE_0);
		if (DB.getRecordSetCount(queryResultSet1) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database for Query2",
					"Record exists in the Database\nTable for Query2", false);

		} else {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database for Query2",
					"Record doesn't exists in the Database for Query2 For SSN",
					false);
		}
	}
}
