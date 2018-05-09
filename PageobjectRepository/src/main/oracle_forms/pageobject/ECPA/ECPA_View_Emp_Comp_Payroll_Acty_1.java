package pageobject.ECPA;

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

public class ECPA_View_Emp_Comp_Payroll_Acty_1 {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/ECPA_View_Emp_Comp_Payroll_Acty_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
		public ResultSet queryResultSet2 = null;
public ResultSet queryResultSet1 = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String QUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	String STEP_TYPE_LOV;
	String CTRL_BEGIN_DATE_0;
	String CTRL_END_DATE_0;

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

	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
	}

	public void setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(
			String qUERY_CHG_MULTIPLE_SSN_EXT_LOV) {
		QUERY_CHG_MULTIPLE_SSN_EXT_LOV = qUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	}

	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}

	public void setCTRL_BEGIN_DATE_0(String cTRL_BEGIN_DATE_0) {
		CTRL_BEGIN_DATE_0 = cTRL_BEGIN_DATE_0;
	}

	public void setCTRL_END_DATE_0(String cTRL_END_DATE_0) {
		CTRL_END_DATE_0 = cTRL_END_DATE_0;
	}

	public void setServiceParameters() throws SQLException {
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SSN_DISPL_0(Stock
				.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(Stock.GetParameterValue("QUERY_CHG_MULTIPLE_SSN_EXT_LOV"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		this.setCTRL_BEGIN_DATE_0(Stock
				.GetParameterValue("CTRL_BEGIN_DATE_0"));
		this.setCTRL_END_DATE_0(Stock.GetParameterValue("CTRL_END_DATE_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&CONTROL_SSN_DISPL_0="
				+ CONTROL_SSN_DISPL_0
				+ "&QUERY_CHG_MULTIPLE_SSN_EXT_LOV="
				+ QUERY_CHG_MULTIPLE_SSN_EXT_LOV
				+ "&STEP_TYPE_LOV="
				+ STEP_TYPE_LOV
				+ "&CTRL_BEGIN_DATE_0="
				+ CTRL_BEGIN_DATE_0
				+ "&CTRL_END_DATE_0="
				+ CTRL_END_DATE_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=ture&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for ECPA service",
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
			    
		doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run  ECPA service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ECPA service",
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
	
	public void validateInDatabase() throws SQLException{
		queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForECPA1")[1]);
		

		if(DB.getRecordSetCount(queryResultSet1)>0){
			Reporter.logEvent(Status.PASS, "Validating records exists in DB", "Records exists in DB", false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		
		queryResultSet2 = DB.executeQuery(General.dbInstance,Stock.getTestQuery("queryForECPA2")[1]);
		
		
		if(DB.getRecordSetCount(queryResultSet2)>0){
			Reporter.logEvent(Status.PASS, "Validating records exists in DB", "Records exists in DB", false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
}
