package pageobject.TMFR;

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

public class TMFR_tax_reporting_form_rule {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/TMFR_tax_reporting_form_rule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet1;

	String param9183;
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String NAME_DATE_EFFECTIVE_DATE_0;
	String IRS_RULES_AVAILABLE_0;
	String IRS_RULES_AVAILABLE_0_X1;
	String IRS_RULES_AVAILABLE_0_X2;
	String IRS_RULES_AVAILABLE_0_X3;
	String REASONS_AVAILABLE_0;
	String REASONS_AVAILABLE_0_X1;

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

	

	public void setParam9183(String param9183) {
		this.param9183 = param9183;
	}

	public void setNAME_DATE_EFFECTIVE_DATE_0(String nAME_DATE_EFFECTIVE_DATE_0) {
		NAME_DATE_EFFECTIVE_DATE_0 = nAME_DATE_EFFECTIVE_DATE_0;
	}

	public void setIRS_RULES_AVAILABLE_0(String iRS_RULES_AVAILABLE_0) {
		IRS_RULES_AVAILABLE_0 = iRS_RULES_AVAILABLE_0;
	}

	public void setIRS_RULES_AVAILABLE_0_X1(String iRS_RULES_AVAILABLE_0_X1) {
		IRS_RULES_AVAILABLE_0_X1 = iRS_RULES_AVAILABLE_0_X1;
	}

	public void setIRS_RULES_AVAILABLE_0_X2(String iRS_RULES_AVAILABLE_0_X2) {
		IRS_RULES_AVAILABLE_0_X2 = iRS_RULES_AVAILABLE_0_X2;
	}

	public void setIRS_RULES_AVAILABLE_0_X3(String iRS_RULES_AVAILABLE_0_X3) {
		IRS_RULES_AVAILABLE_0_X3 = iRS_RULES_AVAILABLE_0_X3;
	}

	public void setREASONS_AVAILABLE_0(String rEASONS_AVAILABLE_0) {
		REASONS_AVAILABLE_0 = rEASONS_AVAILABLE_0;
	}

	public void setREASONS_AVAILABLE_0_X1(String rEASONS_AVAILABLE_0_X1) {
		REASONS_AVAILABLE_0_X1 = rEASONS_AVAILABLE_0_X1;
	}

	public void setServiceParameters() throws SQLException {
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setParam9183(Stock.GetParameterValue("param9183"));
		this.setNAME_DATE_EFFECTIVE_DATE_0(Stock.GetParameterValue("NAME_DATE_EFFECTIVE_DATE_0"));
		this.setIRS_RULES_AVAILABLE_0(Stock
				.GetParameterValue("IRS_RULES_AVAILABLE_0"));
		this.setIRS_RULES_AVAILABLE_0_X1(Stock
				.GetParameterValue("IRS_RULES_AVAILABLE_0_X1"));
		this.setIRS_RULES_AVAILABLE_0_X2(Stock.GetParameterValue("IRS_RULES_AVAILABLE_0_X2"));
		this.setIRS_RULES_AVAILABLE_0_X3(Stock.GetParameterValue("IRS_RULES_AVAILABLE_0_X3"));
		this.setREASONS_AVAILABLE_0(Stock.GetParameterValue("REASONS_AVAILABLE_0"));
		this.setREASONS_AVAILABLE_0_X1(Stock
				.GetParameterValue("REASONS_AVAILABLE_0_X1"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&param9183="
				+ param9183
				+ "&NAME_DATE_EFFECTIVE_DATE_0="
				+ NAME_DATE_EFFECTIVE_DATE_0
				+ "&IRS_RULES_AVAILABLE_0="
				+ IRS_RULES_AVAILABLE_0
				+ "&IRS_RULES_AVAILABLE_0_X1="
				+ IRS_RULES_AVAILABLE_0_X1
				+ "&IRS_RULES_AVAILABLE_0_X2="
				+ IRS_RULES_AVAILABLE_0_X2
				+ "&IRS_RULES_AVAILABLE_0_X3="
				+ IRS_RULES_AVAILABLE_0_X3
				+ "&REASONS_AVAILABLE_0="
				+ REASONS_AVAILABLE_0
				+ "&REASONS_AVAILABLE_0_X1="
				+ REASONS_AVAILABLE_0_X1
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for TMFR service",
				this.queryString.replaceAll("&", "\n"), false);

	}
	
	public void deInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTMFR")[1],NAME_DATE_EFFECTIVE_DATE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.INFO, "Validating Records in Database", "There are Records in the Database.", false);
			while(queryResultSet.next()){
				queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTMFRDelete")[1],NAME_DATE_EFFECTIVE_DATE_0);
				Reporter.logEvent(Status.PASS, "Deleting record in Database", "Records Deleted.", false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Records in Database", "There are Records in the Database to delete.", false);
		}
		
			
		}

	public void runService() throws SQLException {
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
			Reporter.logEvent(Status.PASS, "Run  TMFR service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run TMFR service",
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
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTMFR")[1],NAME_DATE_EFFECTIVE_DATE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Record generated in Database", "Record is generated in Database", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "IRSRL_CODE: "+queryResultSet.getString("IRSRL_CODE")+
						"\nFUNC_TAX_AREA_CODE: "+queryResultSet.getString("FUNC_TAX_AREA_CODE")+
						"\nTAX_REASON_CODE: "+queryResultSet.getString("TAX_REASON_CODE")+
						"\nTAX_FORM_CODE: "+queryResultSet.getString("TAX_FORM_CODE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Record generated in Database", "Record is not generated in Database", false);
		}
		
		
	}
}
