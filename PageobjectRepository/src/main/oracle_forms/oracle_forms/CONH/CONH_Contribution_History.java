package pageobject.CONH;

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

public class CONH_Contribution_History {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CONH_QUERY";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String STEP_TYPE_LOV;
	String SEL_START_DATE_0;
	String SEL_END_DATE_0;
	String SEL_CASH_REASON_0;
	String SEL_CASH_EFFDATE_0;
	String SEL_DEPOSIT_TYPE_0;
	String SEL_SDMT_CODE_0;
	String SEL_GDMT_SEQNBR_0;
	
	public void setQueryResultSet1(ResultSet queryResultSet1) {
		this.queryResultSet1 = queryResultSet1;
	}
	public void setQueryResultSet(ResultSet queryResultSet) {
		this.queryResultSet = queryResultSet;
	}
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
	public void setSEL_START_DATE_0(String sEL_START_DATE_0) {
		SEL_START_DATE_0 = sEL_START_DATE_0;
	}
	public void setSEL_END_DATE_0(String sEL_END_DATE_0) {
		SEL_END_DATE_0 = sEL_END_DATE_0;
	}
	public void setSEL_CASH_REASON_0(String sEL_CASH_REASON_0) {
		SEL_CASH_REASON_0 = sEL_CASH_REASON_0;
	}
	public void setSEL_CASH_EFFDATE_0(String sEL_CASH_EFFDATE_0) {
		SEL_CASH_EFFDATE_0 = sEL_CASH_EFFDATE_0;
	}
	public void setSEL_DEPOSIT_TYPE_0(String sEL_DEPOSIT_TYPE_0) {
		SEL_DEPOSIT_TYPE_0 = sEL_DEPOSIT_TYPE_0;
	}
	public void setSEL_SDMT_CODE_0(String sEL_SDMT_CODE_0) {
		SEL_SDMT_CODE_0 = sEL_SDMT_CODE_0;
	}
	public void setSEL_GDMT_SEQNBR_0(String sEL_GDMT_SEQNBR_0) {
		SEL_GDMT_SEQNBR_0 = sEL_GDMT_SEQNBR_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setSEL_CASH_EFFDATE_0(Stock.GetParameterValue("SEL_CASH_EFFDATE_0"));
		this.setSEL_CASH_REASON_0(Stock.GetParameterValue("SEL_CASH_REASON_0"));
		this.setSEL_DEPOSIT_TYPE_0(Stock.GetParameterValue("SEL_DEPOSIT_TYPE_0"));
		this.setSEL_END_DATE_0(Stock.GetParameterValue("SEL_END_DATE_0"));
		this.setSEL_GDMT_SEQNBR_0(Stock.GetParameterValue("SEL_GDMT_SEQNBR_0"));
		this.setSEL_SDMT_CODE_0(Stock.GetParameterValue("SEL_SDMT_CODE_0"));
		this.setSEL_START_DATE_0(Stock.GetParameterValue("SEL_START_DATE_0"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
	
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&STEP_TYPE_LOV="+STEP_TYPE_LOV+"&SEL_START_DATE_0="+SEL_START_DATE_0+"&SEL_END_DATE_0="+SEL_END_DATE_0+
				"&SEL_CASH_REASON_0="+SEL_CASH_REASON_0+"&SEL_CASH_EFFDATE_0="+SEL_CASH_EFFDATE_0+"&SEL_DEPOSIT_TYPE_0="+SEL_DEPOSIT_TYPE_0+"&SEL_SDMT_CODE_0="+SEL_SDMT_CODE_0+
				"&SEL_GDMT_SEQNBR_0="+SEL_GDMT_SEQNBR_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for CONH service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run CONH service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CONH service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
	/*		Reporter.logEvent(Status.PASS, "From Response: ", "ISIS CODE: "+ doc.getElementsByTagName("EXTERNAL_ENTITY_TRANSLATION_ISIS_CODE_0").item(0).getTextContent()+
					"\nPC CODE: "+ doc.getElementsByTagName("EXTERNAL_ENTITY_TRANSLATION_PC_CODE_0").item(0).getTextContent(), false);
*/		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException{
		String ssn = null;
		String rmnc_id;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCONH1")[1], CONTROL_SSN_DISPL_0 );
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Account Transaction\nTable Name: IND_REMIT"+queryResultSet.getMetaData().getTableName(1), false);
			ssn = queryResultSet.getString("SSN");
			Reporter.logEvent(Status.INFO, "Values From DATABASE: \nTable Name: IND_REMIT", "RMNC_ID: "+queryResultSet.getString("RMNC_ID")+
					"\nPROCESS STATUS CODE: "+queryResultSet.getString("PROCESS_STATUS_CODE")+
					"\nAMOUNT: "+queryResultSet.getString("AMOUNT")+
					"\nSSN: "+queryResultSet.getString("SSN")+
					"\nIND ID: "+queryResultSet.getString("IND_ID"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCONH2")[1], queryResultSet.getString("RMNC_ID"));
		
		if(queryResultSet1.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Account Transaction\nTable Name: "+ "REMIT_NOTICE", false);
			rmnc_id = queryResultSet1.getString("ID");
			Reporter.logEvent(Status.INFO, "Values From DATABASE: \nTable Name: REMIT_NOTICE", "RMNC_ID: "+queryResultSet1.getString("ID")+
					"\nEVENT ID: "+queryResultSet1.getString("EV_ID")+
					"\nAMOUNT: "+queryResultSet1.getString("AMOUNT")+
					"\nBALANCE STATUS CODE: "+queryResultSet1.getString("BALANCE_STATUS_CODE")+
					"\nGA ID: "+queryResultSet1.getString("GA_ID"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(this.CONTROL_SSN_DISPL_0.equalsIgnoreCase(ssn)){
			Reporter.logEvent(Status.PASS,"Comparing and Validating SSN from Input and Database ","Both the values are same as Expected"+
					"\nSSN: "+"From Input: "+this.CONTROL_SSN_DISPL_0+"\nFrom Database: "+ssn,false);
		}
		}
}
	
