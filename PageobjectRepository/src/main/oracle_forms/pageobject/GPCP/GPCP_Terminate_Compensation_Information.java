package pageobject.GPCP;

import generallib.DateCompare;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class GPCP_Terminate_Compensation_Information {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/GPCP_Terminate_Compensation_Information";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet1;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String LOGON_USERNAME_X1;
	String LOGON_PASSWORD_X1;
	String LOGON_DATABASE_X1;
	String GET_GA_GA_ID_0;
	String GRP_COMP_PKG_TERMDATE_1;
	String GRP_COMP_PKG_TERMDATE_1_X1;
	String param9159;
	String termDateBeforeRun;
	String termDateUpdated;

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

	public void setLOGON_USERNAME_X1(String lOGON_USERNAME_X1) {
		LOGON_USERNAME_X1 = lOGON_USERNAME_X1;
	}

	public void setLOGON_PASSWORD_X1(String lOGON_PASSWORD_X1) {
		LOGON_PASSWORD_X1 = lOGON_PASSWORD_X1;
	}

	public void setLOGON_DATABASE_X1(String lOGON_DATABASE_X1) {
		LOGON_DATABASE_X1 = lOGON_DATABASE_X1;
	}

	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}

	public void setGRP_COMP_PKG_TERMDATE_1(String gRP_COMP_PKG_TERMDATE_1) {
		GRP_COMP_PKG_TERMDATE_1 = gRP_COMP_PKG_TERMDATE_1;
	}

	public void setGRP_COMP_PKG_TERMDATE_1_X1(String gRP_COMP_PKG_TERMDATE_1_X1) {
		GRP_COMP_PKG_TERMDATE_1_X1 = gRP_COMP_PKG_TERMDATE_1_X1;
	}

	public void setParam9159(String param9159) {
		this.param9159 = param9159;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_DATABASE_X1(Stock.GetParameterValue("LOGON_DATABASE_X1"));
		this.setLOGON_PASSWORD_X1(Stock.GetParameterValue("LOGON_PASSWORD_X1"));
		this.setLOGON_USERNAME_X1(Stock.GetParameterValue("LOGON_USERNAME_X1"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setGRP_COMP_PKG_TERMDATE_1(Stock
				.GetParameterValue("GRP_COMP_PKG_TERMDATE_1"));
		this.setGRP_COMP_PKG_TERMDATE_1_X1(Stock
				.GetParameterValue("GRP_COMP_PKG_TERMDATE_1_X1"));
		this.setParam9159(Stock.GetParameterValue("param9159"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&LOGON_USERNAME_X1="
				+ LOGON_USERNAME_X1
				+ "&LOGON_PASSWORD_X1="
				+ LOGON_PASSWORD_X1
				+ "&LOGON_DATABASE_X1="
				+ LOGON_DATABASE_X1
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&GET_GA_GA_ID_0="
				+ GET_GA_GA_ID_0
				+ "&GRP_COMP_PKG_TERMDATE_1="
				+ GRP_COMP_PKG_TERMDATE_1
				+ "&GRP_COMP_PKG_TERMDATE_1_X1="
				+ GRP_COMP_PKG_TERMDATE_1_X1
				+ "&param9159="
				+ param9159
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for GPCP service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void getDateInDBBeforeRun() throws Exception {
		System.out.println("Inside getDateInDBBeforeRun");
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForGPCPTermDate")[1],
				this.GET_GA_GA_ID_0, this.GRP_COMP_PKG_TERMDATE_1_X1);
		System.out.println("queryResultSet1 parameters: " + this.GET_GA_GA_ID_0
				+ this.GRP_COMP_PKG_TERMDATE_1_X1);

		if (DB.getRecordSetCount(queryResultSet) > 0) {
			System.out.println("Inside if records exist");
			Reporter.logEvent(
					Status.INFO,
					"getDateInDBBeforeRun: Validating existence of data in the Database",
					"getDateInDBBeforeRun: Record exists in the Database",
					false);

			while (queryResultSet.next()) {
				System.out.println("Inside While");
				termDateBeforeRun = queryResultSet.getString("TERMDATE");
				System.out.println("Initial TermDate in DB: "
						+ termDateBeforeRun);
				termDateBeforeRun = DateCompare.FormatDate2(termDateBeforeRun);
				System.out.println("Formatted TermDate in DB: "
						+ termDateBeforeRun);
				Reporter.logEvent(Status.INFO,
						"Fetching Term Date before Run Service",
						"Term Date before Run Service: " + termDateBeforeRun,
						false);
			}
		}
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Date dateobj = new Date();
		String currentDate = df.format(dateobj);
		System.out.println("termDateBeforeRun: " + this.termDateBeforeRun);
		System.out.println("Date before updation: "
				+ this.GRP_COMP_PKG_TERMDATE_1_X1);
		this.GRP_COMP_PKG_TERMDATE_1_X1 = currentDate;
		System.out.println("Date after updation: "
				+ this.GRP_COMP_PKG_TERMDATE_1_X1);
		int rows_updated = DB.executeUpdate(General.dbInstance,
				Stock.getTestQuery("queryForGPCPUpdateTermDate")[1],
				this.GRP_COMP_PKG_TERMDATE_1_X1, termDateBeforeRun,
				this.GET_GA_GA_ID_0);// CHECK THIS
		System.out.println("rows_updated: " + rows_updated);
		if (rows_updated > 0) {
			Reporter.logEvent(Status.PASS,
					"Updated Term Date before Run Service",
					"Updated Term Date before Run Service: "
							+ this.GRP_COMP_PKG_TERMDATE_1_X1, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Updated Term Date before Run Service",
					"Term Date did not updated before Run Service: "
							+ this.GRP_COMP_PKG_TERMDATE_1_X1, false);
		}
	}

	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println("serviceURL: " + serviceURL);
			URL obj = new URL(serviceURL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			// print result
			System.out.println(response.toString());
			con.getContent();
			PrintWriter pw = new PrintWriter("response.txt");
			pw.println(response.toString().replaceAll("<></>", ""));
			pw.close();

			BufferedReader br = new BufferedReader(new FileReader(
					"response.txt"));
			String line;
			while ((line = br.readLine()) != null) {
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
			Reporter.logEvent(Status.PASS, "Run GPCP service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run GPCP service",
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
			
			/*Reporter.logEvent(
			Status.PASS,
			"From Response: ",
			"GRP_COMP_PKG_TERMDATE_0: "
					+ doc.getElementsByTagName("GRP_COMP_PKG_TERMDATE_0")
							.item(0).getTextContent(), false);*/
		} else {
			Reporter.logEvent(Status.INFO,
					"Validate response - Error is not empty",
					"We are ignoring the error.\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException, ParseException {
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForGPCP")[1], this.GET_GA_GA_ID_0);
		queryResultSet1 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForGPCPTermDate")[1],
				this.GET_GA_GA_ID_0, this.GRP_COMP_PKG_TERMDATE_1_X1);

		if (DB.getRecordSetCount(queryResultSet1) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"validateInDatabase: Record exists in the Database for the updated Term Date: "
							+ this.GRP_COMP_PKG_TERMDATE_1_X1, false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"validateInDatabase: Record exists in the Database for the updated Term Date: "
							+ this.GRP_COMP_PKG_TERMDATE_1_X1, false);
		}

	}

	public void revertDateUpdation() throws Exception {
		System.out.println("Inside Undo Revert");
		System.out.println("termDateBeforeRun: " + termDateBeforeRun
				+ "GRP_COMP_PKG_TERMDATE_1_X1: "
				+ this.GRP_COMP_PKG_TERMDATE_1_X1 + "GET_GA_GA_ID_0: "
				+ this.GET_GA_GA_ID_0);
		int undoRows_updated = DB.executeUpdate(General.dbInstance,
				Stock.getTestQuery("queryForGPCPUpdateTermDate")[1],
				termDateBeforeRun, this.GRP_COMP_PKG_TERMDATE_1_X1,
				this.GET_GA_GA_ID_0);// CHECK THIS
		System.out.println("undoRows_updated: " + undoRows_updated);
		if (undoRows_updated > 0) {
			Reporter.logEvent(
					Status.PASS,
					"Reverting the date updation to Aura Player Date in the Database",
					"Updated Date: " + this.GRP_COMP_PKG_TERMDATE_1_X1
							+ " has been reverted in the Database to: "
							+ termDateBeforeRun, false);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Reverting the date updation to Aura Player Date in the Database",
					"Updated Date: " + this.GRP_COMP_PKG_TERMDATE_1_X1
							+ " has not been reverted in the Database to: "
							+ termDateBeforeRun, false);
		}
	}
}