package pageobject.SPBP;

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

public class GQ19SPBP {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SPBP_INSERTMODE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String SUPP_BP_PAY_INFO_PAYMENT_RECEIVER_TYPE_0;
	String SUPP_BP_PAY_INFO_CSV_ID_0;
	String CONTACT_SERVICER_LOV2;
	String SUPP_BP_PAY_INFO_SHARE_PCT_0;
	String SUPP_BP_PAY_INFO_EFFDATE_0;
	String SUPP_BP_PAY_INFO_PLAN_HLDNG_ACCT_SDMT_CODE_0;
	String PLAN_HOLDING_ACCT_LOV;
	String SUPP_BP_PAY_INFO_ARS_NAME_0;
	String SUPP_BP_PAY_INFO_PAYMENT_TYPE_0;
	String SUPP_BP_PAY_INFO_SAP_ADDRESS_ID_0;
	String SUPP_BP_PAY_INFO_SAP_BANKING_DETAIL_ID_0;
	String SUPP_BP_PAY_INFO_PAYMENT_METHOD_0;
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
	public void setSUPP_BP_PAY_INFO_PAYMENT_RECEIVER_TYPE_0(
			String sUPP_BP_PAY_INFO_PAYMENT_RECEIVER_TYPE_0) {
		SUPP_BP_PAY_INFO_PAYMENT_RECEIVER_TYPE_0 = sUPP_BP_PAY_INFO_PAYMENT_RECEIVER_TYPE_0;
	}
	public void setSUPP_BP_PAY_INFO_CSV_ID_0(String sUPP_BP_PAY_INFO_CSV_ID_0) {
		SUPP_BP_PAY_INFO_CSV_ID_0 = sUPP_BP_PAY_INFO_CSV_ID_0;
	}
	public void setCONTACT_SERVICER_LOV2(String cONTACT_SERVICER_LOV2) {
		CONTACT_SERVICER_LOV2 = cONTACT_SERVICER_LOV2;
	}
	public void setSUPP_BP_PAY_INFO_SHARE_PCT_0(String sUPP_BP_PAY_INFO_SHARE_PCT_0) {
		SUPP_BP_PAY_INFO_SHARE_PCT_0 = sUPP_BP_PAY_INFO_SHARE_PCT_0;
	}
	public void setSUPP_BP_PAY_INFO_EFFDATE_0(String sUPP_BP_PAY_INFO_EFFDATE_0) {
		SUPP_BP_PAY_INFO_EFFDATE_0 = sUPP_BP_PAY_INFO_EFFDATE_0;
	}
	public void setSUPP_BP_PAY_INFO_PLAN_HLDNG_ACCT_SDMT_CODE_0(
			String sUPP_BP_PAY_INFO_PLAN_HLDNG_ACCT_SDMT_CODE_0) {
		SUPP_BP_PAY_INFO_PLAN_HLDNG_ACCT_SDMT_CODE_0 = sUPP_BP_PAY_INFO_PLAN_HLDNG_ACCT_SDMT_CODE_0;
	}
	public void setPLAN_HOLDING_ACCT_LOV(String pLAN_HOLDING_ACCT_LOV) {
		PLAN_HOLDING_ACCT_LOV = pLAN_HOLDING_ACCT_LOV;
	}
	public void setSUPP_BP_PAY_INFO_ARS_NAME_0(String sUPP_BP_PAY_INFO_ARS_NAME_0) {
		SUPP_BP_PAY_INFO_ARS_NAME_0 = sUPP_BP_PAY_INFO_ARS_NAME_0;
	}
	public void setSUPP_BP_PAY_INFO_PAYMENT_TYPE_0(
			String sUPP_BP_PAY_INFO_PAYMENT_TYPE_0) {
		SUPP_BP_PAY_INFO_PAYMENT_TYPE_0 = sUPP_BP_PAY_INFO_PAYMENT_TYPE_0;
	}
	public void setSUPP_BP_PAY_INFO_SAP_ADDRESS_ID_0(
			String sUPP_BP_PAY_INFO_SAP_ADDRESS_ID_0) {
		SUPP_BP_PAY_INFO_SAP_ADDRESS_ID_0 = sUPP_BP_PAY_INFO_SAP_ADDRESS_ID_0;
	}
	public void setSUPP_BP_PAY_INFO_SAP_BANKING_DETAIL_ID_0(
			String sUPP_BP_PAY_INFO_SAP_BANKING_DETAIL_ID_0) {
		SUPP_BP_PAY_INFO_SAP_BANKING_DETAIL_ID_0 = sUPP_BP_PAY_INFO_SAP_BANKING_DETAIL_ID_0;
	}
	public void setSUPP_BP_PAY_INFO_PAYMENT_METHOD_0(
			String sUPP_BP_PAY_INFO_PAYMENT_METHOD_0) {
		SUPP_BP_PAY_INFO_PAYMENT_METHOD_0 = sUPP_BP_PAY_INFO_PAYMENT_METHOD_0;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTACT_SERVICER_LOV2(Stock.GetParameterValue("CONTACT_SERVICER_LOV2"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setPLAN_HOLDING_ACCT_LOV(Stock.GetParameterValue("PLAN_HOLDING_ACCT_LOV"));
		this.setSUPP_BP_PAY_INFO_ARS_NAME_0(Stock.GetParameterValue("SUPP_BP_PAY_INFO_ARS_NAME_0"));
		this.setSUPP_BP_PAY_INFO_CSV_ID_0(Stock.GetParameterValue("SUPP_BP_PAY_INFO_CSV_ID_0"));
		this.setSUPP_BP_PAY_INFO_EFFDATE_0(Stock.GetParameterValue("SUPP_BP_PAY_INFO_EFFDATE_0"));
		this.setSUPP_BP_PAY_INFO_PAYMENT_METHOD_0(Stock.GetParameterValue("SUPP_BP_PAY_INFO_PAYMENT_METHOD_0"));
		this.setSUPP_BP_PAY_INFO_PAYMENT_RECEIVER_TYPE_0(Stock.GetParameterValue("SUPP_BP_PAY_INFO_PAYMENT_RECEIVER_TYPE_0"));
		this.setSUPP_BP_PAY_INFO_PAYMENT_TYPE_0(Stock.GetParameterValue("SUPP_BP_PAY_INFO_PAYMENT_TYPE_0"));
		this.setSUPP_BP_PAY_INFO_PLAN_HLDNG_ACCT_SDMT_CODE_0(Stock.GetParameterValue("SUPP_BP_PAY_INFO_PLAN_HLDNG_ACCT_SDMT_CODE_0"));
		this.setSUPP_BP_PAY_INFO_SAP_ADDRESS_ID_0(Stock.GetParameterValue("SUPP_BP_PAY_INFO_SAP_ADDRESS_ID_0"));
		this.setSUPP_BP_PAY_INFO_SAP_BANKING_DETAIL_ID_0(Stock.GetParameterValue("SUPP_BP_PAY_INFO_SAP_BANKING_DETAIL_ID_0"));
		this.setSUPP_BP_PAY_INFO_SHARE_PCT_0(Stock.GetParameterValue("SUPP_BP_PAY_INFO_SHARE_PCT_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE + "&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 + 
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&SUPP_BP_PAY_INFO_PAYMENT_RECEIVER_TYPE_0="+SUPP_BP_PAY_INFO_PAYMENT_RECEIVER_TYPE_0+"&SUPP_BP_PAY_INFO_CSV_ID_0="+SUPP_BP_PAY_INFO_CSV_ID_0+
				"&CONTACT_SERVICER_LOV2="+CONTACT_SERVICER_LOV2+"&SUPP_BP_PAY_INFO_SHARE_PCT_0="+SUPP_BP_PAY_INFO_SHARE_PCT_0+"&SUPP_BP_PAY_INFO_EFFDATE_0="+SUPP_BP_PAY_INFO_EFFDATE_0+
				"&SUPP_BP_PAY_INFO_PLAN_HLDNG_ACCT_SDMT_CODE_0="+SUPP_BP_PAY_INFO_PLAN_HLDNG_ACCT_SDMT_CODE_0+"&PLAN_HOLDING_ACCT_LOV="+PLAN_HOLDING_ACCT_LOV+"&SUPP_BP_PAY_INFO_ARS_NAME_0="+SUPP_BP_PAY_INFO_ARS_NAME_0+
				"&SUPP_BP_PAY_INFO_PAYMENT_TYPE_0="+SUPP_BP_PAY_INFO_PAYMENT_TYPE_0+"&SUPP_BP_PAY_INFO_SAP_ADDRESS_ID_0="+SUPP_BP_PAY_INFO_SAP_ADDRESS_ID_0+"&SUPP_BP_PAY_INFO_SAP_BANKING_DETAIL_ID_0="+SUPP_BP_PAY_INFO_SAP_BANKING_DETAIL_ID_0+
				"&SUPP_BP_PAY_INFO_PAYMENT_METHOD_0="+SUPP_BP_PAY_INFO_PAYMENT_METHOD_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SPBP service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run QYPA service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run QYPA service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","GA_RECOV_STRUC_RECOV_ID_0: "+doc.getElementsByTagName("GA_RECOV_STRUC_RECOV_ID_0").item(0).getTextContent()+
					"\nGA_RECOV_STRUC_RECOV_ID_1: "+doc.getElementsByTagName("GA_RECOV_STRUC_RECOV_ID_1").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
public void validateInDatabase() throws SQLException{
		
		String recov_id = doc.getElementsByTagName("GA_RECOV_STRUC_RECOV_ID_1").item(0).getTextContent();
		String recov_id_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSPBP")[1], this.GET_GA_GA_ID_0, recov_id);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: SUPP_BP_PAY_INFO", false);
			while(queryResultSet.next()){
				recov_id_db = queryResultSet.getString("RECOV_ID");
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "PAYMENT_RECEIVER_TYPE:  "+ queryResultSet.getString("PAYMENT_RECEIVER_TYPE")+
		 				"\nCSV_ID: "+ queryResultSet.getString("CSV_ID")+
						"\nGA_ID: "+queryResultSet.getString("GA_ID")+
						"\nRECOV_ID: "+queryResultSet.getString("RECOV_ID"), false);
			}
			if(recov_id.contains(recov_id_db)){
				Reporter.logEvent(Status.PASS, "Comparing and Validating RECOVERY_ID from Response and Database", "Both the values are same as expected"+
						"\nFrom Response: "+recov_id+"\nFrom Database: "+recov_id_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating RECOVERY_ID from Response and Database", "Both the values are not same "+
						"\nFrom Response: "+recov_id+"\nFrom Database: "+recov_id_db, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
	}
}
