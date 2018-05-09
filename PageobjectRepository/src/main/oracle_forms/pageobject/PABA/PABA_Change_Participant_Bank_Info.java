/**
 * 
 */
package pageobject.PABA;

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

/**
 * @author smykjn
 *
 */
public class PABA_Change_Participant_Bank_Info {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PABA_CHANGE_PARTICIPANT_BANKING_INFO";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet2;
	private ResultSet queryResultSet3;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String STEP_TYPE_LOV;
	String LOAN_ACCT_LOV;
	String PART_BANKING_EFFDATE_0;
	String PART_BANKING_BANK_ACCT_NAME_0;
	String PART_BANKING_SAVINGS_CHECKING_IND_0;
	String PART_BANKING_ACCOUNT_NICKNAME_0;
	String PART_BANKING_ACCT_NBR_0;
	String BANK_TRANSIT_NBR_LOV;
	String PART_BANKING_NARR_LINE_1_0;
	String PART_BANKING_NARR_LINE_2_0;
	

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
	public void setLOAN_ACCT_LOV(String lOAN_ACCT_LOV) {
		LOAN_ACCT_LOV = lOAN_ACCT_LOV;
	}
	public void setPART_BANKING_EFFDATE_0(String pART_BANKING_EFFDATE_0) {
		PART_BANKING_EFFDATE_0 = pART_BANKING_EFFDATE_0;
	}
	public void setPART_BANKING_BANK_ACCT_NAME_0(String pART_BANKING_BANK_ACCT_NAME_0) {
		PART_BANKING_BANK_ACCT_NAME_0 = pART_BANKING_BANK_ACCT_NAME_0;
	}
	public void setPART_BANKING_SAVINGS_CHECKING_IND_0(String pART_BANKING_SAVINGS_CHECKING_IND_0) {
		PART_BANKING_SAVINGS_CHECKING_IND_0 = pART_BANKING_SAVINGS_CHECKING_IND_0;
	}
	public void setPART_BANKING_ACCOUNT_NICKNAME_0(String pART_BANKING_ACCOUNT_NICKNAME_0) {
		PART_BANKING_ACCOUNT_NICKNAME_0 = pART_BANKING_ACCOUNT_NICKNAME_0;
	}
	public void setPART_BANKING_ACCT_NBR_0(String pART_BANKING_ACCT_NBR_0) {
		PART_BANKING_ACCT_NBR_0 = pART_BANKING_ACCT_NBR_0;
	}
	public void setBANK_TRANSIT_NBR_LOV(String bANK_TRANSIT_NBR_LOV) {
		BANK_TRANSIT_NBR_LOV = bANK_TRANSIT_NBR_LOV;
	}
	public void setPART_BANKING_NARR_LINE_1_0(String pART_BANKING_NARR_LINE_1_0) {
		PART_BANKING_NARR_LINE_1_0 = pART_BANKING_NARR_LINE_1_0;
	}
	public void setPART_BANKING_NARR_LINE_2_0(String pART_BANKING_NARR_LINE_2_0) {
		PART_BANKING_NARR_LINE_2_0 = pART_BANKING_NARR_LINE_2_0;
	}


	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setBANK_TRANSIT_NBR_LOV(Stock.GetParameterValue("BANK_TRANSIT_NBR_LOV"));
		this.setLOAN_ACCT_LOV(Stock.GetParameterValue("LOAN_ACCT_LOV"));
		this.setPART_BANKING_ACCOUNT_NICKNAME_0(Stock.GetParameterValue("PART_BANKING_ACCOUNT_NICKNAME_0"));
		this.setPART_BANKING_ACCT_NBR_0(Stock.GetParameterValue("PART_BANKING_ACCT_NBR_0"));
		this.setPART_BANKING_BANK_ACCT_NAME_0(Stock.GetParameterValue("PART_BANKING_BANK_ACCT_NAME_0"));
		this.setPART_BANKING_EFFDATE_0(Stock.GetParameterValue("PART_BANKING_EFFDATE_0"));
		this.setPART_BANKING_NARR_LINE_1_0(Stock.GetParameterValue("PART_BANKING_NARR_LINE_1_0"));
		this.setPART_BANKING_NARR_LINE_2_0(Stock.GetParameterValue("PART_BANKING_NARR_LINE_2_0"));
		this.setPART_BANKING_SAVINGS_CHECKING_IND_0(Stock.GetParameterValue("PART_BANKING_SAVINGS_CHECKING_IND_0"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));

		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0+ "&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+
				"&STEP_TYPE_LOV="+STEP_TYPE_LOV+"&LOAN_ACCT_LOV="+LOAN_ACCT_LOV+"&PART_BANKING_EFFDATE_0="+
				PART_BANKING_EFFDATE_0+"&PART_BANKING_BANK_ACCT_NAME_0="+PART_BANKING_BANK_ACCT_NAME_0+""
				+ "&PART_BANKING_SAVINGS_CHECKING_IND_0="+PART_BANKING_SAVINGS_CHECKING_IND_0+""
				+ "&PART_BANKING_ACCOUNT_NICKNAME_0="+PART_BANKING_ACCOUNT_NICKNAME_0+""
				+ "&PART_BANKING_ACCT_NBR_0="+PART_BANKING_ACCT_NBR_0+"&BANK_TRANSIT_NBR_LOV="+BANK_TRANSIT_NBR_LOV+""
				+ "&PART_BANKING_NARR_LINE_1_0="+PART_BANKING_NARR_LINE_1_0+"&PART_BANKING_NARR_LINE_2_0="+PART_BANKING_NARR_LINE_2_0
				+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for PABA_CHANGE_PARTICIPANT_BANKING_INFO service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run PABA_CHANGE_PARTICIPANT_BANKING_INFO service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PABA_CHANGE_PARTICIPANT_BANKING_INFO service", "Running Failed:", false);
		}
	}

	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response:","TRANSACTION_CODE_0:"+doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent()+
					"\nTRANSACTION_DESCR_0:"+doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent()+
					"\nPopupMessages:"+doc.getElementsByTagName("PopupMessages").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}

	}
	
	public void validateInDatabase()throws SQLException{
		String ev_id = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPABAChange1")[1], this.CONTROL_SSN_DISPL_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of record in Database", "Records exists in DB \nTablename: "+
					"PART_BANKING", false);
			while(queryResultSet.next()){
				ev_id = queryResultSet.getString("EV_ID");
				Reporter.logEvent(Status.INFO, "Values from Database", "GA_ID:"+queryResultSet.getString("GA_ID")+
						"\nEV_ID: "+queryResultSet.getString("EV_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nBANK_ACCT_NAME: "+queryResultSet.getString("BANK_ACCT_NAME")+
						"\nACCT_NBR: "+queryResultSet.getString("ACCT_NBR"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of record in Database", "Records doesn't exists in DB", false);
		}
		
		queryResultSet2 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPABAChange2")[1], ev_id);
		
		if(DB.getRecordSetCount(queryResultSet2)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of record in Database", "Records exists in DB \nTablename: "+
					"STEP", false);
			while(queryResultSet2.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "EVTY_CODE:"+queryResultSet2.getString("EVTY_CODE")+
						"\nEV_ID: "+queryResultSet2.getString("EV_ID")+
						"\nSETY_CODE: "+queryResultSet2.getString("SETY_CODE")+
						"\nWKUN_SHORT_NAME: "+queryResultSet2.getString("WKUN_SHORT_NAME")+
						"\nSEQNBR: "+queryResultSet2.getString("SEQNBR"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of record in Database", "Records doesn't exists in DB", false);
		}
		
		queryResultSet3 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPABAChange3")[1], ev_id);
		
		if(DB.getRecordSetCount(queryResultSet3)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of record in Database", "Records exists in DB \nTablename: "+
					"EVENT", false);
			while(queryResultSet3.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "EVTY_CODE:"+queryResultSet3.getString("EVTY_CODE")+
						"\nEV_ID: "+queryResultSet3.getString("ID")+
						"\nLOGON_ID: "+queryResultSet3.getString("RESP_USER_LOGON_ID")+
						"\nDPDATE_TIME: "+queryResultSet3.getString("DPDATE_TIME")+
						"\nRECVD_DATE: "+queryResultSet3.getString("RECVD_DATE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of record in Database", "Records doesn't exists in DB", false);
		}
	}
}
