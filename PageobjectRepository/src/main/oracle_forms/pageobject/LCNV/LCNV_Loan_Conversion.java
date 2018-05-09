package pageobject.LCNV;

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

public class LCNV_Loan_Conversion {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/LCNV_Loan_Conversion";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	ResultSet queryResultSet;
	ResultSet queryResultSet1;	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String STEP_TYPE_LOV;
	String LOAN_INFO_PLAN_YEAR_BEGIN_BAL_0;
	String LOAN_INFO_TRF_LOAN_ACCT_ID_0;
	String LOAN_INFO_ORIG_EFFDATE_0;
	String LOAN_INFO_ORIG_LOAN_AMT_0;
	String LOAN_INFO_PAST_DUE_PRINCIPAL_0;
	String LOAN_INFO_PAST_DUE_INTEREST_0;
	String LOAN_INFO_LOAN_AMT_0;
	String LOAN_INFO_REPAY_AMT_0;
	String LOAN_INFO_EFFDATE_0;
	String LOAN_INFO_MATURITY_DATE_0;
	String LOAN_INFO_FIRST_DUE_DATE_0;
	String LOAN_INFO_DEFAULT_DATE_0;
	String LOAN_INFO_FIXED_INT_RATE_0;
	String LOAN_INFO_LOAN_REASON_CODE_0;
	String LOAN_INFO_LOAN_TERM_0;
	String LOAN_INFO_REPAY_MTHD_CODE_0;
	String LOAN_INFO_REPAY_FREQ_0;
	
	
	
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
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;	
	}	
	public void setLOAN_INFO_PLAN_YEAR_BEGIN_BAL_0(String lOAN_INFO_PLAN_YEAR_BEGIN_BAL_0) {
		LOAN_INFO_PLAN_YEAR_BEGIN_BAL_0 = lOAN_INFO_PLAN_YEAR_BEGIN_BAL_0;	
	}	
	public void setLOAN_INFO_TRF_LOAN_ACCT_ID_0(String lOAN_INFO_TRF_LOAN_ACCT_ID_0) {
		LOAN_INFO_TRF_LOAN_ACCT_ID_0 = lOAN_INFO_TRF_LOAN_ACCT_ID_0;	
	}	
	public void setLOAN_INFO_ORIG_EFFDATE_0(String lOAN_INFO_ORIG_EFFDATE_0) {
		LOAN_INFO_ORIG_EFFDATE_0 = lOAN_INFO_ORIG_EFFDATE_0;	
	}	
	public void setLOAN_INFO_ORIG_LOAN_AMT_0(String lOAN_INFO_ORIG_LOAN_AMT_0) {
		LOAN_INFO_ORIG_LOAN_AMT_0 = lOAN_INFO_ORIG_LOAN_AMT_0;	
	}	
	public void setLOAN_INFO_PAST_DUE_PRINCIPAL_0(String lOAN_INFO_PAST_DUE_PRINCIPAL_0) {
		LOAN_INFO_PAST_DUE_PRINCIPAL_0 = lOAN_INFO_PAST_DUE_PRINCIPAL_0;	
	}	
	public void setLOAN_INFO_PAST_DUE_INTEREST_0(String lOAN_INFO_PAST_DUE_INTEREST_0) {
		LOAN_INFO_PAST_DUE_INTEREST_0 = lOAN_INFO_PAST_DUE_INTEREST_0;	
	}
	public void setLOAN_INFO_LOAN_AMT_0(String lOAN_INFO_LOAN_AMT_0) {
		LOAN_INFO_LOAN_AMT_0 = lOAN_INFO_LOAN_AMT_0;	
	}
	public void setLOAN_INFO_REPAY_AMT_0(String lOAN_INFO_REPAY_AMT_0) {
		LOAN_INFO_REPAY_AMT_0 = lOAN_INFO_REPAY_AMT_0;	
	}
	public void setLOAN_INFO_EFFDATE_0(String lOAN_INFO_EFFDATE_0) {
		LOAN_INFO_EFFDATE_0 = lOAN_INFO_EFFDATE_0;	
	}
	public void setLOAN_INFO_MATURITY_DATE_0(String lOAN_INFO_MATURITY_DATE_0) {
		LOAN_INFO_MATURITY_DATE_0 = lOAN_INFO_MATURITY_DATE_0;	
	}
	public void setLOAN_INFO_FIRST_DUE_DATE_0(String lOAN_INFO_FIRST_DUE_DATE_0) {
		LOAN_INFO_FIRST_DUE_DATE_0 = lOAN_INFO_FIRST_DUE_DATE_0;	
	}
	public void setLOAN_INFO_DEFAULT_DATE_0(String lOAN_INFO_DEFAULT_DATE_0) {
		LOAN_INFO_DEFAULT_DATE_0 = lOAN_INFO_DEFAULT_DATE_0;	
	}
	public void setLOAN_INFO_FIXED_INT_RATE_0(String lOAN_INFO_FIXED_INT_RATE_0) {
		LOAN_INFO_FIXED_INT_RATE_0 = lOAN_INFO_FIXED_INT_RATE_0;	
	}
	public void setLOAN_INFO_LOAN_REASON_CODE_0(String lOAN_INFO_LOAN_REASON_CODE_0) {
		LOAN_INFO_LOAN_REASON_CODE_0 = lOAN_INFO_LOAN_REASON_CODE_0;	
	}
	public void setLOAN_INFO_LOAN_TERM_0(String lOAN_INFO_LOAN_TERM_0) {
		LOAN_INFO_LOAN_TERM_0 = lOAN_INFO_LOAN_TERM_0;	
	}
	public void setLOAN_INFO_REPAY_MTHD_CODE_0(String lOAN_INFO_REPAY_MTHD_CODE_0) {
		LOAN_INFO_REPAY_MTHD_CODE_0 = lOAN_INFO_REPAY_MTHD_CODE_0;	
	}
	public void setLOAN_INFO_REPAY_FREQ_0(String lOAN_INFO_REPAY_FREQ_0) {
		LOAN_INFO_REPAY_FREQ_0 = lOAN_INFO_REPAY_FREQ_0;	
	}
		
	
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		this.setLOAN_INFO_PLAN_YEAR_BEGIN_BAL_0(Stock.GetParameterValue("LOAN_INFO_PLAN_YEAR_BEGIN_BAL_0"));
		this.setLOAN_INFO_TRF_LOAN_ACCT_ID_0(Stock.GetParameterValue("LOAN_INFO_TRF_LOAN_ACCT_ID_0"));
		this.setLOAN_INFO_ORIG_EFFDATE_0(Stock.GetParameterValue("LOAN_INFO_ORIG_EFFDATE_0"));
		this.setLOAN_INFO_ORIG_LOAN_AMT_0(Stock.GetParameterValue("LOAN_INFO_ORIG_LOAN_AMT_0"));
		this.setLOAN_INFO_PAST_DUE_PRINCIPAL_0(Stock.GetParameterValue("LOAN_INFO_PAST_DUE_PRINCIPAL_0"));
		this.setLOAN_INFO_PAST_DUE_INTEREST_0(Stock.GetParameterValue("LOAN_INFO_PAST_DUE_INTEREST_0"));
		this.setLOAN_INFO_LOAN_AMT_0(Stock.GetParameterValue("LOAN_INFO_LOAN_AMT_0"));
		this.setLOAN_INFO_REPAY_AMT_0(Stock.GetParameterValue("LOAN_INFO_REPAY_AMT_0"));
		this.setLOAN_INFO_EFFDATE_0(Stock.GetParameterValue("LOAN_INFO_EFFDATE_0"));
		this.setLOAN_INFO_MATURITY_DATE_0(Stock.GetParameterValue("LOAN_INFO_MATURITY_DATE_0"));
		this.setLOAN_INFO_FIRST_DUE_DATE_0(Stock.GetParameterValue("LOAN_INFO_FIRST_DUE_DATE_0"));
		this.setLOAN_INFO_DEFAULT_DATE_0(Stock.GetParameterValue("LOAN_INFO_DEFAULT_DATE_0"));
		this.setLOAN_INFO_FIXED_INT_RATE_0(Stock.GetParameterValue("LOAN_INFO_FIXED_INT_RATE_0"));
		this.setLOAN_INFO_LOAN_REASON_CODE_0(Stock.GetParameterValue("LOAN_INFO_LOAN_REASON_CODE_0"));
		this.setLOAN_INFO_LOAN_TERM_0(Stock.GetParameterValue("LOAN_INFO_LOAN_TERM_0"));
		this.setLOAN_INFO_REPAY_MTHD_CODE_0(Stock.GetParameterValue("LOAN_INFO_REPAY_MTHD_CODE_0"));
		this.setLOAN_INFO_REPAY_FREQ_0(Stock.GetParameterValue("LOAN_INFO_REPAY_FREQ_0"));
		
		
		
		/*queryString = "?CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 + "&LOGON_DATABASE=" + LOGON_DATABASE +
				"&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_USERNAME=" + LOGON_USERNAME +
				"&REMIT_NOTICE_ID_0=" + REMIT_NOTICE_ID_0 +
				"&numOfRowsInTable=20&json=false&handlePopups=false";*/
		
		queryString = "?LOGON_USERNAME="
				+LOGON_USERNAME
				+"&LOGON_PASSWORD="
				+LOGON_PASSWORD
				+"&LOGON_DATABASE="
				+LOGON_DATABASE
				+"&CONTROL_SELECTION_0="
				+CONTROL_SELECTION_0
				+"&CONTROL_SSN_DISPL_0="
				+CONTROL_SSN_DISPL_0
				+"&STEP_TYPE_LOV="
				+ STEP_TYPE_LOV
				+"&LOAN_INFO_PLAN_YEAR_BEGIN_BAL_0="
				+LOAN_INFO_PLAN_YEAR_BEGIN_BAL_0
				+"&LOAN_INFO_TRF_LOAN_ACCT_ID_0="
				+LOAN_INFO_TRF_LOAN_ACCT_ID_0
				+"&LOAN_INFO_ORIG_EFFDATE_0="
				+LOAN_INFO_ORIG_EFFDATE_0
				+"&LOAN_INFO_ORIG_LOAN_AMT_0="
				+LOAN_INFO_ORIG_LOAN_AMT_0
				+"&LOAN_INFO_PAST_DUE_PRINCIPAL_0="
				+LOAN_INFO_PAST_DUE_PRINCIPAL_0
				+"&LOAN_INFO_PAST_DUE_INTEREST_0="
				+LOAN_INFO_PAST_DUE_INTEREST_0
				+"&LOAN_INFO_LOAN_AMT_0="
				+LOAN_INFO_LOAN_AMT_0
				+"&LOAN_INFO_REPAY_AMT_0="
				+LOAN_INFO_REPAY_AMT_0
				+"&LOAN_INFO_EFFDATE_0="
				+LOAN_INFO_EFFDATE_0
				+"&LOAN_INFO_MATURITY_DATE_0="
				+LOAN_INFO_MATURITY_DATE_0
				+"&LOAN_INFO_FIRST_DUE_DATE_0="
				+LOAN_INFO_FIRST_DUE_DATE_0
				+"&LOAN_INFO_DEFAULT_DATE_0="
				+LOAN_INFO_DEFAULT_DATE_0
				+"&LOAN_INFO_FIXED_INT_RATE_0="
				+LOAN_INFO_FIXED_INT_RATE_0
				+"&LOAN_INFO_LOAN_REASON_CODE_0="
				+LOAN_INFO_LOAN_REASON_CODE_0
				+"&LOAN_INFO_LOAN_TERM_0="
				+LOAN_INFO_LOAN_TERM_0
				+"&LOAN_INFO_REPAY_MTHD_CODE_0="
				+LOAN_INFO_REPAY_MTHD_CODE_0
				+"&LOAN_INFO_REPAY_FREQ_0="
				+LOAN_INFO_REPAY_FREQ_0
				+"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for LCNV service", this.queryString.replaceAll("&", "\n"), false);
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
		
			Reporter.logEvent(Status.PASS, "Run LCNV service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run LCNV service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} 
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForIdLCNV")[1], this.CONTROL_SSN_DISPL_0);
			String indId = null;	
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS,"Validations in database","Validations of event Id generated in database to check if any row is generated",false);
			/*String id = queryResultSet.getString("ID");
			String ev_id = queryResultSet.getString("EV_ID");
			String amt = queryResultSet.getString("AMOUNT");*/
			indId = queryResultSet.getString("ID");
			System.out.println("ID = " + indId);
			Reporter.logEvent(Status.INFO,"Values From database\nTable Name: Individual","IND_ID:"+indId,false);
		}
		queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForLCNV")[1],indId);
		
		if(DB.getRecordSetCount(queryResultSet1)>0)
		{
			Reporter.logEvent(Status.PASS,
					"Validating Records exists in Database",
					"Records exists in Database", false);
			if(queryResultSet1.next())
			{
			Reporter.logEvent(Status.INFO,"Values from the Database\n Table Name:","ID:"+queryResultSet1.getString("IND_ID")+
					"GA_ID:"+queryResultSet1.getString("GA_ID"),false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, 
					"Validating Records exists in Database",
					"No record found in Databse", false);
		}
		
	}	

	}
}