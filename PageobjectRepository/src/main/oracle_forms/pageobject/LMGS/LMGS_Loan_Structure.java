package pageobject.LMGS;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class LMGS_Loan_Structure {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/LMGS_Group_Loan_Structure";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String GRP_LOAN_STRUC_SDLNST_CODE_0;
	String GRP_LOAN_STRUC_PAY_AHEAD_MAX_DAYS_0;
	String GRP_LOAN_STRUC_MAX_LOANS_ALLOWED_0;
	String GRP_LOAN_STRUC_CONSOLIDATE_IND_COLLATERAL_NO_BUT_0;
	String GRP_LOAN_STRUC_DEFAULTED_LOANS_ALLOWED_IND_DEFAULTED_LOANS_NO_BUT_0;
	String GRP_LOAN_STRUC_TERMINATED_EMPLOYEE_LOAN_IND_TERMINATED_EMPLOYEE_LOAN_NO_0;
	String GRP_LOAN_STRUC_LOAN_DEMINIMUS_IND_LOAN_DEMINIMUS_IND_NO_0;
	String GRP_LOAN_STRUC_LOAN_2ND_DEFAULT_NOTICE_IND_LOAN_2ND_DEFAULT_NOTICE_IND_NO_0;
	String GRP_LOAN_STRUC_LOAN_HARDSHIP_IND_LOAN_HARDSHIP_IND_NO_0;
	String GRP_LOAN_STRUC_ALLOW_LOANS_DISABILITY_LOA_IND_ALOW_LOANS_DISABILTY_LOA_IND_Y_0;
	String GRP_LOAN_STRUC_ALLOW_LOANS_EMP_LOA_IND_ALLOW_LOANS_EMP_LOA_IND_Y_0;
	
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
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	public void setGRP_LOAN_STRUC_SDLNST_CODE_0(String gRP_LOAN_STRUC_SDLNST_CODE_0) {
		GRP_LOAN_STRUC_SDLNST_CODE_0 = gRP_LOAN_STRUC_SDLNST_CODE_0;
	}
	public void setGRP_LOAN_STRUC_PAY_AHEAD_MAX_DAYS_0(
			String gRP_LOAN_STRUC_PAY_AHEAD_MAX_DAYS_0) {
		GRP_LOAN_STRUC_PAY_AHEAD_MAX_DAYS_0 = gRP_LOAN_STRUC_PAY_AHEAD_MAX_DAYS_0;
	}
	public void setGRP_LOAN_STRUC_MAX_LOANS_ALLOWED_0(
			String gRP_LOAN_STRUC_MAX_LOANS_ALLOWED_0) {
		GRP_LOAN_STRUC_MAX_LOANS_ALLOWED_0 = gRP_LOAN_STRUC_MAX_LOANS_ALLOWED_0;
	}
	public void setGRP_LOAN_STRUC_CONSOLIDATE_IND_COLLATERAL_NO_BUT_0(
			String gRP_LOAN_STRUC_CONSOLIDATE_IND_COLLATERAL_NO_BUT_0) {
		GRP_LOAN_STRUC_CONSOLIDATE_IND_COLLATERAL_NO_BUT_0 = gRP_LOAN_STRUC_CONSOLIDATE_IND_COLLATERAL_NO_BUT_0;
	}
	public void setGRP_LOAN_STRUC_DEFAULTED_LOANS_ALLOWED_IND_DEFAULTED_LOANS_NO_BUT_0(
			String gRP_LOAN_STRUC_DEFAULTED_LOANS_ALLOWED_IND_DEFAULTED_LOANS_NO_BUT_0) {
		GRP_LOAN_STRUC_DEFAULTED_LOANS_ALLOWED_IND_DEFAULTED_LOANS_NO_BUT_0 = gRP_LOAN_STRUC_DEFAULTED_LOANS_ALLOWED_IND_DEFAULTED_LOANS_NO_BUT_0;
	}
	public void setGRP_LOAN_STRUC_TERMINATED_EMPLOYEE_LOAN_IND_TERMINATED_EMPLOYEE_LOAN_NO_0(
			String gRP_LOAN_STRUC_TERMINATED_EMPLOYEE_LOAN_IND_TERMINATED_EMPLOYEE_LOAN_NO_0) {
		GRP_LOAN_STRUC_TERMINATED_EMPLOYEE_LOAN_IND_TERMINATED_EMPLOYEE_LOAN_NO_0 = gRP_LOAN_STRUC_TERMINATED_EMPLOYEE_LOAN_IND_TERMINATED_EMPLOYEE_LOAN_NO_0;
	}
	public void setGRP_LOAN_STRUC_LOAN_DEMINIMUS_IND_LOAN_DEMINIMUS_IND_NO_0(
			String gRP_LOAN_STRUC_LOAN_DEMINIMUS_IND_LOAN_DEMINIMUS_IND_NO_0) {
		GRP_LOAN_STRUC_LOAN_DEMINIMUS_IND_LOAN_DEMINIMUS_IND_NO_0 = gRP_LOAN_STRUC_LOAN_DEMINIMUS_IND_LOAN_DEMINIMUS_IND_NO_0;
	}
	public void setGRP_LOAN_STRUC_LOAN_2ND_DEFAULT_NOTICE_IND_LOAN_2ND_DEFAULT_NOTICE_IND_NO_0(
			String gRP_LOAN_STRUC_LOAN_2ND_DEFAULT_NOTICE_IND_LOAN_2ND_DEFAULT_NOTICE_IND_NO_0) {
		GRP_LOAN_STRUC_LOAN_2ND_DEFAULT_NOTICE_IND_LOAN_2ND_DEFAULT_NOTICE_IND_NO_0 = gRP_LOAN_STRUC_LOAN_2ND_DEFAULT_NOTICE_IND_LOAN_2ND_DEFAULT_NOTICE_IND_NO_0;
	}
	public void setGRP_LOAN_STRUC_LOAN_HARDSHIP_IND_LOAN_HARDSHIP_IND_NO_0(
			String gRP_LOAN_STRUC_LOAN_HARDSHIP_IND_LOAN_HARDSHIP_IND_NO_0) {
		GRP_LOAN_STRUC_LOAN_HARDSHIP_IND_LOAN_HARDSHIP_IND_NO_0 = gRP_LOAN_STRUC_LOAN_HARDSHIP_IND_LOAN_HARDSHIP_IND_NO_0;
	}
	public void setGRP_LOAN_STRUC_ALLOW_LOANS_DISABILITY_LOA_IND_ALOW_LOANS_DISABILTY_LOA_IND_Y_0(
			String gRP_LOAN_STRUC_ALLOW_LOANS_DISABILITY_LOA_IND_ALOW_LOANS_DISABILTY_LOA_IND_Y_0) {
		GRP_LOAN_STRUC_ALLOW_LOANS_DISABILITY_LOA_IND_ALOW_LOANS_DISABILTY_LOA_IND_Y_0 = gRP_LOAN_STRUC_ALLOW_LOANS_DISABILITY_LOA_IND_ALOW_LOANS_DISABILTY_LOA_IND_Y_0;
	}
	public void setGRP_LOAN_STRUC_ALLOW_LOANS_EMP_LOA_IND_ALLOW_LOANS_EMP_LOA_IND_Y_0(
			String gRP_LOAN_STRUC_ALLOW_LOANS_EMP_LOA_IND_ALLOW_LOANS_EMP_LOA_IND_Y_0) {
		GRP_LOAN_STRUC_ALLOW_LOANS_EMP_LOA_IND_ALLOW_LOANS_EMP_LOA_IND_Y_0 = gRP_LOAN_STRUC_ALLOW_LOANS_EMP_LOA_IND_ALLOW_LOANS_EMP_LOA_IND_Y_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		 this.setGRP_LOAN_STRUC_ALLOW_LOANS_DISABILITY_LOA_IND_ALOW_LOANS_DISABILTY_LOA_IND_Y_0(Stock.GetParameterValue("GRP_LOAN_STRUC_ALLOW_LOANS_DISABILITY_LOA_IND_ALOW_LOANS_DISABILTY_LOA_IND_Y_0"));
		 this.setGRP_LOAN_STRUC_ALLOW_LOANS_EMP_LOA_IND_ALLOW_LOANS_EMP_LOA_IND_Y_0(Stock.GetParameterValue("GRP_LOAN_STRUC_ALLOW_LOANS_EMP_LOA_IND_ALLOW_LOANS_EMP_LOA_IND_Y_0"));
		 this.setGRP_LOAN_STRUC_CONSOLIDATE_IND_COLLATERAL_NO_BUT_0(Stock.GetParameterValue("GRP_LOAN_STRUC_CONSOLIDATE_IND_COLLATERAL_NO_BUT_0"));
		 this.setGRP_LOAN_STRUC_DEFAULTED_LOANS_ALLOWED_IND_DEFAULTED_LOANS_NO_BUT_0(Stock.GetParameterValue("GRP_LOAN_STRUC_DEFAULTED_LOANS_ALLOWED_IND_DEFAULTED_LOANS_NO_BUT_0"));
		 this.setGRP_LOAN_STRUC_LOAN_2ND_DEFAULT_NOTICE_IND_LOAN_2ND_DEFAULT_NOTICE_IND_NO_0(Stock.GetParameterValue("GRP_LOAN_STRUC_LOAN_2ND_DEFAULT_NOTICE_IND_LOAN_2ND_DEFAULT_NOTICE_IND_NO_0"));
		 this.setGRP_LOAN_STRUC_LOAN_DEMINIMUS_IND_LOAN_DEMINIMUS_IND_NO_0(Stock.GetParameterValue("GRP_LOAN_STRUC_LOAN_2ND_DEFAULT_NOTICE_IND_LOAN_2ND_DEFAULT_NOTICE_IND_NO_0"));
		 this.setGRP_LOAN_STRUC_LOAN_HARDSHIP_IND_LOAN_HARDSHIP_IND_NO_0(Stock.GetParameterValue("GRP_LOAN_STRUC_LOAN_HARDSHIP_IND_LOAN_HARDSHIP_IND_NO_0"));
		 this.setGRP_LOAN_STRUC_MAX_LOANS_ALLOWED_0(Stock.GetParameterValue("GRP_LOAN_STRUC_MAX_LOANS_ALLOWED_0"));
		 this.setGRP_LOAN_STRUC_PAY_AHEAD_MAX_DAYS_0(Stock.GetParameterValue("GRP_LOAN_STRUC_PAY_AHEAD_MAX_DAYS_0"));
		 this.setGRP_LOAN_STRUC_SDLNST_CODE_0(Stock.GetParameterValue("GRP_LOAN_STRUC_SDLNST_CODE_0"));
		 this.setGRP_LOAN_STRUC_TERMINATED_EMPLOYEE_LOAN_IND_TERMINATED_EMPLOYEE_LOAN_NO_0(Stock.GetParameterValue("GRP_LOAN_STRUC_TERMINATED_EMPLOYEE_LOAN_IND_TERMINATED_EMPLOYEE_LOAN_NO_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&GRP_LOAN_STRUC_SDLNST_CODE_0="+GRP_LOAN_STRUC_SDLNST_CODE_0+
				 "&GRP_LOAN_STRUC_PAY_AHEAD_MAX_DAYS_0="+GRP_LOAN_STRUC_PAY_AHEAD_MAX_DAYS_0+"&GRP_LOAN_STRUC_MAX_LOANS_ALLOWED_0="+GRP_LOAN_STRUC_MAX_LOANS_ALLOWED_0+
				 "&GRP_LOAN_STRUC_CONSOLIDATE_IND_COLLATERAL_NO_BUT_0="+GRP_LOAN_STRUC_CONSOLIDATE_IND_COLLATERAL_NO_BUT_0+
				 "&GRP_LOAN_STRUC_DEFAULTED_LOANS_ALLOWED_IND_DEFAULTED_LOANS_NO_BUT_0="+GRP_LOAN_STRUC_DEFAULTED_LOANS_ALLOWED_IND_DEFAULTED_LOANS_NO_BUT_0+
				 "&GRP_LOAN_STRUC_TERMINATED_EMPLOYEE_LOAN_IND_TERMINATED_EMPLOYEE_LOAN_NO_0="+GRP_LOAN_STRUC_TERMINATED_EMPLOYEE_LOAN_IND_TERMINATED_EMPLOYEE_LOAN_NO_0+
				 "&GRP_LOAN_STRUC_LOAN_DEMINIMUS_IND_LOAN_DEMINIMUS_IND_NO_0="+GRP_LOAN_STRUC_LOAN_DEMINIMUS_IND_LOAN_DEMINIMUS_IND_NO_0+
				 "&GRP_LOAN_STRUC_LOAN_2ND_DEFAULT_NOTICE_IND_LOAN_2ND_DEFAULT_NOTICE_IND_NO_0="+GRP_LOAN_STRUC_LOAN_2ND_DEFAULT_NOTICE_IND_LOAN_2ND_DEFAULT_NOTICE_IND_NO_0+
				 "&GRP_LOAN_STRUC_LOAN_HARDSHIP_IND_LOAN_HARDSHIP_IND_NO_0="+GRP_LOAN_STRUC_LOAN_HARDSHIP_IND_LOAN_HARDSHIP_IND_NO_0+
				 "&GRP_LOAN_STRUC_ALLOW_LOANS_DISABILITY_LOA_IND_ALOW_LOANS_DISABILTY_LOA_IND_Y_0="+GRP_LOAN_STRUC_ALLOW_LOANS_DISABILITY_LOA_IND_ALOW_LOANS_DISABILTY_LOA_IND_Y_0+
				 "&GRP_LOAN_STRUC_ALLOW_LOANS_EMP_LOA_IND_ALLOW_LOANS_EMP_LOA_IND_Y_0="+GRP_LOAN_STRUC_ALLOW_LOANS_EMP_LOA_IND_ALLOW_LOANS_EMP_LOA_IND_Y_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for LMGS service", this.queryString.replaceAll("&", "\n"), false);
		 
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
		//		System.out.println(serviceURL);
				doc.getDocumentElement().normalize();
				Reporter.logEvent(Status.PASS, "Run LMGS_Group_Loan_Structure service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run LMGS_Group_Loan_Structure service", "Running Failed:", false);
			}
			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		Reporter.logEvent(Status.INFO, "Values From response: ","CFG_CONTROL_TODAYS_DATE_0: "+doc.getElementsByTagName("CFG_CONTROL_TODAYS_DATE_0").item(0).getTextContent()+
				"\nGET_GA_PLAN_NAME_0: "+doc.getElementsByTagName("GET_GA_PLAN_NAME_0").item(0).getTextContent()+
				"\nAMOUNT: "+doc.getElementsByTagName("GRP_LOAN_STRUC_PAYOFF_TOLERANCE_AMT_0").item(0).getTextContent()+"\nDESCRIPTION: "+doc.getElementsByTagName("STEP_TYPE_DESCR_0").item(0).getTextContent(),false);
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForLoanStructure")[1], GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of record in Database", "Records exists in Database", false);
			while(queryResultSet.next()){
				String ga_id = queryResultSet.getString("GA_ID");
				String descr = queryResultSet.getString("STRUC_DESCR");
				String loanType = queryResultSet.getString("LOAN_TYPE_CODE");
				Reporter.logEvent(Status.INFO, "Values From Database: \nTable Name: GRP_LOAN_STRUC", "Expected: A record should display with loan structure for group_id\nGA_ID: "+ga_id+"\nSTRUC_DESCR: "+descr+"\nLOAN_TYPE_CODE: "+loanType, false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of record in Database", "Records doesn't exist in Database", false);
		}
	}	
	
}	
	
