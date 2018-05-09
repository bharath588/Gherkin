package pageobject.DSRL;

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

public class DSRL_disb_step_rule {
	
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/DSRL_disb_step_rule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SELECT_PARM_DISB_TYPE_0;
	String param9246;
	String DISB_REASONS_AVAILABLE_0;
	String DISB_STEPS_AVAILABLE_0;
	String DISB_STEPS_AVAILABLE_0_X1;
	String DISB_STEPS_AVAILABLE_0_X2;
	String DISB_STEPS_AVAILABLE_0_X3;	
	String DISB_STEPS_AVAILABLE_0_X4;
 
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

	public void setSELECT_PARM_DISB_TYPE_0(String sELECT_PARM_DISB_TYPE_0) {
		SELECT_PARM_DISB_TYPE_0 = sELECT_PARM_DISB_TYPE_0;
	}

	public void setparam9246(String Param9246) {
		param9246 = Param9246;
	}

	public void setDISB_REASONS_AVAILABLE_0(String dISB_REASONS_AVAILABLE_0) {
		DISB_REASONS_AVAILABLE_0 = dISB_REASONS_AVAILABLE_0;
	}
	
	public void setDISB_STEPS_AVAILABLE_0(String dISB_STEPS_AVAILABLE_0) {
		DISB_STEPS_AVAILABLE_0 = dISB_STEPS_AVAILABLE_0;
	}

	public void setDISB_STEPS_AVAILABLE_0_X1(String dISB_STEPS_AVAILABLE_0_X1	) {
		DISB_STEPS_AVAILABLE_0_X1	 = dISB_STEPS_AVAILABLE_0_X1	;
	}
	
	public void setDISB_STEPS_AVAILABLE_0_X2(String dISB_STEPS_AVAILABLE_0_X2) {
		DISB_STEPS_AVAILABLE_0_X2 = dISB_STEPS_AVAILABLE_0_X2;
	}
	
	public void setDISB_STEPS_AVAILABLE_0_X3(String dISB_STEPS_AVAILABLE_0_X3) {
		DISB_STEPS_AVAILABLE_0_X3 = dISB_STEPS_AVAILABLE_0_X3;
	}
	
	public void setDISB_STEPS_AVAILABLE_0_X4(String dISB_STEPS_AVAILABLE_0_X4) {
		DISB_STEPS_AVAILABLE_0_X4 = dISB_STEPS_AVAILABLE_0_X4;
	}
	
	
	public void setServiceParameters() {
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setSELECT_PARM_DISB_TYPE_0(Stock.GetParameterValue("SELECT_PARM_DISB_TYPE_0"));
		this.setparam9246(Stock.GetParameterValue("param9246"));
		this.setDISB_REASONS_AVAILABLE_0(Stock.GetParameterValue("DISB_REASONS_AVAILABLE_0"));
		this.setDISB_STEPS_AVAILABLE_0(Stock.GetParameterValue("DISB_STEPS_AVAILABLE_0"));
		this.setDISB_STEPS_AVAILABLE_0_X1(Stock.GetParameterValue("DISB_STEPS_AVAILABLE_0_X1"));
		this.setDISB_STEPS_AVAILABLE_0_X2(Stock.GetParameterValue("DISB_STEPS_AVAILABLE_0_X2"));
		this.setDISB_STEPS_AVAILABLE_0_X3(Stock.GetParameterValue("DISB_STEPS_AVAILABLE_0_X3"));
		this.setDISB_STEPS_AVAILABLE_0_X4(Stock.GetParameterValue("DISB_STEPS_AVAILABLE_0_X4"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME 
				+"&LOGON_PASSWORD="
				+LOGON_PASSWORD
				+"&LOGON_DATABASE="
				+LOGON_DATABASE
				+"&CONTROL_SELECTION_0="
				+CONTROL_SELECTION_0
				+"&SELECT_PARM_DISB_TYPE_0="
				+SELECT_PARM_DISB_TYPE_0
				+"&param9246="
				+param9246
				+"&DISB_REASONS_AVAILABLE_0="
				+DISB_REASONS_AVAILABLE_0
				+"&DISB_STEPS_AVAILABLE_0="
				+DISB_STEPS_AVAILABLE_0
				+"&DISB_STEPS_AVAILABLE_0_X1="
				+DISB_STEPS_AVAILABLE_0_X1
				+"&DISB_STEPS_AVAILABLE_0_X2="
				+DISB_STEPS_AVAILABLE_0_X2
				+"&DISB_STEPS_AVAILABLE_0_X3="
				+DISB_STEPS_AVAILABLE_0_X3
				+"&DISB_STEPS_AVAILABLE_0_X4="
				+DISB_STEPS_AVAILABLE_0_X4
				+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

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
			Reporter.logEvent(Status.PASS, "Run DSRL service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DSRL service",
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

	public void validateInDatabase() throws SQLException {

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForDSRL")[1]);

		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating Records exists in Database",
					"Records exists in Database", false);

		}
				else {
					Reporter.logEvent(
							Status.FAIL,
							"Validating Records exists in Database",
							"Records does not exists in Database",
							false);
				

		}
	}

	
	

}