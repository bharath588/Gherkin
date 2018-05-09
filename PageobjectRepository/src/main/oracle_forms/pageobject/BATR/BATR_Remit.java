package pageobject.BATR;

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

public class BATR_Remit {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/BATR_REMIT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String BHRM1_BKA_CODE_0;
	String BHRM1_AMOUNT_0;
	String BHRM1_CASH_EFFDATE_0;
	String BHRM1_BHSX_ID_0;
	String RMNC1_GA_ID_0;
	String RMNC1_AMOUNT_0;
	
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
	public void setBHRM1_BKA_CODE_0(String bHRM1_BKA_CODE_0) {
		BHRM1_BKA_CODE_0 = bHRM1_BKA_CODE_0;
	}
	public void setBHRM1_AMOUNT_0(String bHRM1_AMOUNT_0) {
		BHRM1_AMOUNT_0 = bHRM1_AMOUNT_0;
	}
	public void setBHRM1_CASH_EFFDATE_0(String bHRM1_CASH_EFFDATE_0) {
		BHRM1_CASH_EFFDATE_0 = bHRM1_CASH_EFFDATE_0;
	}
	public void setBHRM1_BHSX_ID_0(String bHRM1_BHSX_ID_0) {
		BHRM1_BHSX_ID_0 = bHRM1_BHSX_ID_0;
	}
	public void setRMNC1_GA_ID_0(String rMNC1_GA_ID_0) {
		RMNC1_GA_ID_0 = rMNC1_GA_ID_0;
	}
	public void setRMNC1_AMOUNT_0(String rMNC1_AMOUNT_0) {
		RMNC1_AMOUNT_0 = rMNC1_AMOUNT_0;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setBHRM1_AMOUNT_0(Stock.GetParameterValue("BHRM1_AMOUNT_0"));
		this.setBHRM1_BHSX_ID_0(Stock.GetParameterValue("BHRM1_BHSX_ID_0"));
		this.setBHRM1_BKA_CODE_0(Stock.GetParameterValue("BHRM1_BKA_CODE_0"));
		this.setBHRM1_CASH_EFFDATE_0(Stock.GetParameterValue("BHRM1_CASH_EFFDATE_0"));
		this.setRMNC1_AMOUNT_0(Stock.GetParameterValue("RMNC1_AMOUNT_0"));
		this.setRMNC1_GA_ID_0(Stock.GetParameterValue("RMNC1_GA_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&BHRM1_BKA_CODE_0="+BHRM1_BKA_CODE_0+"&BHRM1_AMOUNT_0="+BHRM1_AMOUNT_0+"&BHRM1_CASH_EFFDATE_0="+BHRM1_CASH_EFFDATE_0+"&BHRM1_BHSX_ID_0="+BHRM1_BHSX_ID_0+
				"&RMNC1_GA_ID_0="+RMNC1_GA_ID_0+"&RMNC1_AMOUNT_0="+RMNC1_AMOUNT_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for BATR_Remit service", this.queryString.replaceAll("&", "\n"), false);
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
			//System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run BATR_Remit service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run BATR_Remit service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "BALANCE_STATUS_CODE: "+ doc.getElementsByTagName("BHRM1_BALANCE_STATUS_CODE_0").item(0).getTextContent()+
					"\nEV_ID: "+ doc.getElementsByTagName("BHRM1_EV_ID_0").item(0).getTextContent()+
					"\nBHRM_EV_ID: "+ doc.getElementsByTagName("BHRM1_EV_ID_0").item(0).getTextContent()+
					"\nRMNC1_CASH_EFFDATE_0: "+ doc.getElementsByTagName("RMNC1_CASH_EFFDATE_0").item(0).getTextContent()+
					"\nRMNC1_WKUN_SHORT_NAME_0: "+ doc.getElementsByTagName("RMNC1_WKUN_SHORT_NAME_0").item(0).getTextContent()+
					"\nRMNC1_ID_0: "+ doc.getElementsByTagName("RMNC1_ID_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException
	{
		String bhrm_id = doc.getElementsByTagName("BHRM1_ID_0").item(0).getTextContent();
		String remit_id = doc.getElementsByTagName("RMNC1_ID_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForBATRRemit1")[1], bhrm_id);		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Verify records exists in Database", "Records exists in Database\nTableName: BATCH_REMIT", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nEV_ID: "+queryResultSet.getString("EV_ID")+
						"\nBALANCE_STATUS_CODE: "+queryResultSet.getString("BALANCE_STATUS_CODE")+
						"\nBHSX_ID: "+queryResultSet.getString("BHSX_ID")+
						"\nBKA_CODE: "+queryResultSet.getString("BKA_CODE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Verify records exists in Database", "Records doesn't exists in Database\nTableName:BATCH_REMIT", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForBATRRemit2")[1], remit_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Verify records exists in Database", "Records exists in Database\nTableName: REMIT_NOTICE", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nEV_ID: "+queryResultSet.getString("EV_ID")+
						"\nBALANCE_STATUS_CODE: "+queryResultSet.getString("BALANCE_STATUS_CODE")+
						"\nAMOUNT: "+queryResultSet.getString("AMOUNT")+
						"\nCASH_EFFDATE: "+queryResultSet.getString("CASH_EFFDATE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Verify records exists in Database", "Records doesn't exists in Database\nTableName:BATCH_REMIT", false);
		}
	}
	
}
