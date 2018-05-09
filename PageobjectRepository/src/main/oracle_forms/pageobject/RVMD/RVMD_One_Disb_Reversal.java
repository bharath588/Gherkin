package pageobject.RVMD;

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

public class RVMD_One_Disb_Reversal {

	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/RVMD_One_Disbursement_Reversal_via_MAIN_MENU";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INDIVIDUAL_SSN_0;
	String INDIVIDUAL_SSN_0_X1;
	String EVENT_LOV;
	String DSBA1_TEST_SUPPRESS_IND_0;
	String CORRECTION_QUES_CORRECTION_0;
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
	public void setINDIVIDUAL_SSN_0(String iNDIVIDUAL_SSN_0) {
		INDIVIDUAL_SSN_0 = iNDIVIDUAL_SSN_0;
	}
	public void setINDIVIDUAL_SSN_0_X1(String iNDIVIDUAL_SSN_0_X1) {
		INDIVIDUAL_SSN_0_X1 = iNDIVIDUAL_SSN_0_X1;
	}
	public void setEVENT_LOV(String eVENT_LOV) {
		EVENT_LOV = eVENT_LOV;
	}
	public void setDSBA1_TEST_SUPPRESS_IND_0(String dSBA1_TEST_SUPPRESS_IND_0) {
		DSBA1_TEST_SUPPRESS_IND_0 = dSBA1_TEST_SUPPRESS_IND_0;
	}
	public void setCORRECTION_QUES_CORRECTION_0(String cORRECTION_QUES_CORRECTION_0) {
		CORRECTION_QUES_CORRECTION_0 = cORRECTION_QUES_CORRECTION_0;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINDIVIDUAL_SSN_0(Stock.GetParameterValue("INDIVIDUAL_SSN_0"));
		this.setINDIVIDUAL_SSN_0_X1(Stock.GetParameterValue("INDIVIDUAL_SSN_0_X1"));
		this.setEVENT_LOV(Stock.GetParameterValue("EVENT_LOV"));
		this.setDSBA1_TEST_SUPPRESS_IND_0(Stock.GetParameterValue("DSBA1_TEST_SUPPRESS_IND_0"));
		this.setCORRECTION_QUES_CORRECTION_0(Stock.GetParameterValue("CORRECTION_QUES_CORRECTION_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&INDIVIDUAL_SSN_0="+INDIVIDUAL_SSN_0+"&INDIVIDUAL_SSN_0_X1="+INDIVIDUAL_SSN_0_X1+"&EVENT_LOV="+EVENT_LOV+
				"&DSBA1_TEST_SUPPRESS_IND_0="+DSBA1_TEST_SUPPRESS_IND_0+
				"&CORRECTION_QUES_CORRECTION_0="+CORRECTION_QUES_CORRECTION_0+
				"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for RVMD_One_Disbursement_Reversals_Executed_via_MAIN_MENU service", this.queryString.replaceAll("&", "\n"), false);
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
//			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run RVMD_One_Disbursement_Reversals_Executed_via_MAIN_MENU service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run RVMD_One_Disbursement_Reversals_Executed_via_MAIN_MENU service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response:", "MASR_TO_DO_EV_ID_0: "+doc.getElementsByTagName("MASR_TO_DO_EV_ID_0").item(0).getTextContent()+
					"\nDSBA1_RVSL_0: "+doc.getElementsByTagName("DSBA1_RVSL_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String ev_id = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("rvmdDisbursementReversal")[1],INDIVIDUAL_SSN_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS,"Validate records exist in DB.\nTable Name:DISB_BASIC\nQuery:"
					+Stock.getTestQuery("rvmdOneDisbursementReversal")[1],"Records exist in DB.",false);
			while(queryResultSet.next()){
				ev_id = queryResultSet.getString("EV_ID");
				Reporter.logEvent(Status.INFO, "From DB", "STATUS_CODE: "+queryResultSet.getString("STATUS_CODE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL,"Validate records exist in DB.\nTable Name:DISB_BASIC\nQuery:"
					+Stock.getTestQuery("rvmdOneDisbursementReversal")[1],"Records do not exist in DB.",false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("rvmdDisbursementReversal2")[1], ev_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS,"Validate records exist in DB.\nTable Name: EVENT\nQuery:"
					+Stock.getTestQuery("rvmdOneDisbursementReversal2")[1],"Records exist in DB.",false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "From DB", "COMPLTN_DATE: "+queryResultSet.getString("COMPLTN_DATE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL,"Validate records exist in DB.\nTable Name: EVENT\nQuery:"
					+Stock.getTestQuery("rvmdOneDisbursementReversal2")[1],"Records do not exist in DB.",false);
		}
	}
}