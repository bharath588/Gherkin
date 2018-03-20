/**
 * 
 */
package pageobjects.loan;

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

/**
 * @author srsksr
 *
 */
public class RVMD_Multiple_Disbursement_Reversals_Executed_via_MAIN_MENU {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/RVMD_Multiple_Disbursement_Reversals_Executed_via_MAIN_MENU";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INDIVIDUAL_SSN_0;
	String EVENT_LOV;
	String DSBA1_TEST_SUPPRESS_IND_0;
	String CORRECTION_QUES_CORRECTION_0;
	String EVENT_LOV_X1;
	String DSBA1_TEST_SUPPRESS_IND_0_X1;
	String CORRECTION_QUES_CORRECTION_0_X1;
	
	
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
	public void setEVENT_LOV(String eVENT_LOV) {
		EVENT_LOV = eVENT_LOV;
	}
	public void setDSBA1_TEST_SUPPRESS_IND_0(String dSBA1_TEST_SUPPRESS_IND_0) {
		DSBA1_TEST_SUPPRESS_IND_0 = dSBA1_TEST_SUPPRESS_IND_0;
	}
	public void setCORRECTION_QUES_CORRECTION_0(String cORRECTION_QUES_CORRECTION_0) {
		CORRECTION_QUES_CORRECTION_0 = cORRECTION_QUES_CORRECTION_0;
	}
	public void setEVENT_LOV_X1(String eVENT_LOV_X1) {
		EVENT_LOV_X1 = eVENT_LOV_X1;
	}
	public void setDSBA1_TEST_SUPPRESS_IND_0_X1(String dSBA1_TEST_SUPPRESS_IND_0_X1) {
		DSBA1_TEST_SUPPRESS_IND_0_X1 = dSBA1_TEST_SUPPRESS_IND_0_X1;
	}
	public void setCORRECTION_QUES_CORRECTION_0_X1(String cORRECTION_QUES_CORRECTION_0_X1) {
		CORRECTION_QUES_CORRECTION_0_X1 = cORRECTION_QUES_CORRECTION_0_X1;
	}
	
	public void setServiceParameters(String ssn) throws SQLException	
	{
		String sqlQuery[]=Stock.getTestQuery("rvmdDisbursementReversal");
		this.setLOGON_DATABASE(Stock.getConfigParam(sqlQuery[0]));
		this.setLOGON_PASSWORD("auratest");
		this.setLOGON_USERNAME("t00lidge");
		this.setCONTROL_SELECTION_0("RVMD");
		this.setINDIVIDUAL_SSN_0(ssn);
		this.setEVENT_LOV("1");
		this.setDSBA1_TEST_SUPPRESS_IND_0("Y");
		this.setCORRECTION_QUES_CORRECTION_0("Y");
		this.setEVENT_LOV_X1("1");
		this.setDSBA1_TEST_SUPPRESS_IND_0_X1("Y");
		this.setCORRECTION_QUES_CORRECTION_0_X1("Y");
		
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&INDIVIDUAL_SSN_0="+INDIVIDUAL_SSN_0+"&EVENT_LOV="+EVENT_LOV+"&DSBA1_TEST_SUPPRESS_IND_0="+DSBA1_TEST_SUPPRESS_IND_0+
				"&CORRECTION_QUES_CORRECTION_0="+CORRECTION_QUES_CORRECTION_0+"&EVENT_LOV_X1="+EVENT_LOV_X1+
				"&DSBA1_TEST_SUPPRESS_IND_0_X1="+DSBA1_TEST_SUPPRESS_IND_0_X1+"&CORRECTION_QUES_CORRECTION_0_X1="+CORRECTION_QUES_CORRECTION_0_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for RVMD_Multiple_Disbursement_Reversals_Executed_via_MAIN_MENU service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run RVMD_Multiple_Disbursement_Reversals_Executed_via_MAIN_MENU service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run RVMD_Multiple_Disbursement_Reversals_Executed_via_MAIN_MENU service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response:", ""
					+ "PopupMessages:"+doc.getElementsByTagName("PopupMessages").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase(String ssn) throws SQLException{
		String sqlQuery[]=Stock.getTestQuery("rvmdDisbursementReversal");
		queryResultSet = DB.executeQuery(sqlQuery[0],sqlQuery[1],ssn);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS,"Validate records exist in DB.\nTable Name:DISB_BASIC\nQuery:"
					+Stock.getTestQuery("rvmdDisbursementReversal")[1],"Records exist in DB.",false);
		}else{
			Reporter.logEvent(Status.FAIL,"Validate records exist in DB.\nTable Name:DISB_BASIC\nQuery:"
					+Stock.getTestQuery("rvmdDisbursementReversal")[1],"Records do not exist in DB.",false);
		}
	}

}
