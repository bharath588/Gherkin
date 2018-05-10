package pageobject.BLAD;

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

public class BLAD_Add_Billing_Adjustments {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/BLAD_Add_Billing_Adjustments_Record";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String QUERY_BY_GA_ID_0;
	String GROUP_HEADER_GA_ID_0;
	String BILL_ADJ_BIBA_ID_LOV0;
	String BILL_ADJ_N_SSN_LOV2;
	String BILL_ADJ_PART_GCS_BA_LOV4;
	String BILL_ADJ_AMOUNT_0;
	String BILL_ADJ_NARRATIVE_0;
	
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
	public void setQUERY_BY_GA_ID_0(String qUERY_BY_GA_ID_0) {
		QUERY_BY_GA_ID_0 = qUERY_BY_GA_ID_0;
	}
	public void setGROUP_HEADER_GA_ID_0(String gROUP_HEADER_GA_ID_0) {
		GROUP_HEADER_GA_ID_0 = gROUP_HEADER_GA_ID_0;
	}
	public void setBILL_ADJ_BIBA_ID_LOV0(String bILL_ADJ_BIBA_ID_LOV0) {
		BILL_ADJ_BIBA_ID_LOV0 = bILL_ADJ_BIBA_ID_LOV0;
	}
	public void setBILL_ADJ_N_SSN_LOV2(String bILL_ADJ_N_SSN_LOV2) {
		BILL_ADJ_N_SSN_LOV2 = bILL_ADJ_N_SSN_LOV2;
	}
	public void setBILL_ADJ_PART_GCS_BA_LOV4(String bILL_ADJ_PART_GCS_BA_LOV4) {
		BILL_ADJ_PART_GCS_BA_LOV4 = bILL_ADJ_PART_GCS_BA_LOV4;
	}
	public void setBILL_ADJ_AMOUNT_0(String bILL_ADJ_AMOUNT_0) {
		BILL_ADJ_AMOUNT_0 = bILL_ADJ_AMOUNT_0;
	}
	public void setBILL_ADJ_NARRATIVE_0(String bILL_ADJ_NARRATIVE_0) {
		BILL_ADJ_NARRATIVE_0 = bILL_ADJ_NARRATIVE_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGROUP_HEADER_GA_ID_0(Stock.GetParameterValue("GROUP_HEADER_GA_ID_0"));
		this.setBILL_ADJ_AMOUNT_0(Stock.GetParameterValue("BILL_ADJ_AMOUNT_0"));
		this.setBILL_ADJ_BIBA_ID_LOV0(Stock.GetParameterValue("BILL_ADJ_BIBA_ID_LOV0"));
		this.setBILL_ADJ_N_SSN_LOV2(Stock.GetParameterValue("BILL_ADJ_N_SSN_LOV2"));
		this.setBILL_ADJ_NARRATIVE_0(Stock.GetParameterValue("BILL_ADJ_NARRATIVE_0"));
		this.setBILL_ADJ_PART_GCS_BA_LOV4(Stock.GetParameterValue("BILL_ADJ_PART_GCS_BA_LOV4"));
		
		queryString = "?BILL_ADJ_AMOUNT_0="+BILL_ADJ_AMOUNT_0+"&BILL_ADJ_BIBA_ID_LOV0="+BILL_ADJ_BIBA_ID_LOV0+"&BILL_ADJ_NARRATIVE_0="+BILL_ADJ_NARRATIVE_0+"&BILL_ADJ_N_SSN_LOV2="+BILL_ADJ_N_SSN_LOV2+
				"&BILL_ADJ_PART_GCS_BA_LOV4="+BILL_ADJ_PART_GCS_BA_LOV4+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&GROUP_HEADER_GA_ID_0="+GROUP_HEADER_GA_ID_0+
				"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for BLAD service", this.queryString.replaceAll("&", "\n"), false);
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
			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run BLAD service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run BLAD service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "TRANSACTION_CODE: "+ doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent()+
					"\nTRANSACTION_DESCR_0: "+ doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		String ev_id = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForBLADAdd1")[1], this.GROUP_HEADER_GA_ID_0, this.BILL_ADJ_NARRATIVE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating if database records are getting created or not", "Database records are created\nTableName: BILL_ADJ", false);
			while(queryResultSet.next()){
				ev_id = queryResultSet.getString("EV_ID");
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nBIBA_ID: "+queryResultSet.getString("BIBA_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nAMOUNT: "+queryResultSet.getString("AMOUNT")+
						"\nNARRATIVE: "+queryResultSet.getString("NARRATIVE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating if database records are getting created or not", "Database records are not created\nTableName: BILL_ADJ", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForBLADAdd2")[1], ev_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating if database records are getting created or not", "Database records are created\nTableName: EVENT", false);
			while(queryResultSet.next()){
				ev_id = queryResultSet.getString("ID");
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+
						"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME")+
						"\nLOGON_ID: "+queryResultSet.getString("LOG_USER_LOGON_ID"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating if database records are getting created or not", "Database records are not created\nTableName: EVENT", false);
		}
	}
}
