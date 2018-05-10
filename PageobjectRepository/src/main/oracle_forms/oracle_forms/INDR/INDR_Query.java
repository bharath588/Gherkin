package pageobject.INDR;

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

public class INDR_Query {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/INDR_Query_2";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String AGRM1_RMNC_ID_0;
	
	public void setAGRM1_RMNC_ID_0(String aGRM1_RMNC_ID_0) {
		AGRM1_RMNC_ID_0 = aGRM1_RMNC_ID_0;
	}

	String BHRM1_BKA_CODE_LOV1;
	String BHRM1_AMOUNT_0;
	String BHRM1_CASH_EFFDATE_0;
	String BHRM1_BHSX_ID_LOV0;
	String RMNC1_GA_ID_0;
	String RMNC1_AMOUNT_0;
	String CONTROL_SELECTION_0_X1;
	String RMNC1_ID_0;
	String AGRM1_AMOUNT_0;
	String AGRM1_SDMT_CODE_LOV1;
	String AGRM1_PAYROLL_DATE_0;
	String AGRM1_PAYROLL_DATE_0_X1;
	String INRM1_CNTRL_ID_0;
	String INRM1_AMOUNT_0;
	public String getCONTROL_SELECTION_0() {
		return CONTROL_SELECTION_0;
	}
	
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public String getLOGON_DATABASE() {
		return LOGON_DATABASE;
	}
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public String getLOGON_PASSWORD() {
		return LOGON_PASSWORD;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public String getLOGON_USERNAME() {
		return LOGON_USERNAME;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public String getBHRM1_BKA_CODE_LOV1() {
		return BHRM1_BKA_CODE_LOV1;
	}
	public void setBHRM1_BKA_CODE_LOV1(String bHRM1_BKA_CODE_LOV1) {
		BHRM1_BKA_CODE_LOV1 = bHRM1_BKA_CODE_LOV1;
	}
	public String getBHRM1_AMOUNT_0() {
		return BHRM1_AMOUNT_0;
	}
	public void setBHRM1_AMOUNT_0(String bHRM1_AMOUNT_0) {
		BHRM1_AMOUNT_0 = bHRM1_AMOUNT_0;
	}
	public String getBHRM1_CASH_EFFDATE_0() {
		return BHRM1_CASH_EFFDATE_0;
	}
	public void setBHRM1_CASH_EFFDATE_0(String bHRM1_CASH_EFFDATE_0) {
		BHRM1_CASH_EFFDATE_0 = bHRM1_CASH_EFFDATE_0;
	}
	public String getBHRM1_BHSX_ID_LOV0() {
		return BHRM1_BHSX_ID_LOV0;
	}
	public void setBHRM1_BHSX_ID_LOV0(String bHRM1_BHSX_ID_LOV0) {
		BHRM1_BHSX_ID_LOV0 = bHRM1_BHSX_ID_LOV0;
	}
	public String getRMNC1_GA_ID_0() {
		return RMNC1_GA_ID_0;
	}
	public void setRMNC1_GA_ID_0(String rMNC1_GA_ID_0) {
		RMNC1_GA_ID_0 = rMNC1_GA_ID_0;
	}
	public String getRMNC1_AMOUNT_0() {
		return RMNC1_AMOUNT_0;
	}
	public void setRMNC1_AMOUNT_0(String rMNC1_AMOUNT_0) {
		RMNC1_AMOUNT_0 = rMNC1_AMOUNT_0;
	}
	public String getCONTROL_SELECTION_0_X1() {
		return CONTROL_SELECTION_0_X1;
	}
	public void setCONTROL_SELECTION_0_X1(String cONTROL_SELECTION_0_X1) {
		CONTROL_SELECTION_0_X1 = cONTROL_SELECTION_0_X1;
	}
	public String getRMNC1_ID_0() {
		return RMNC1_ID_0;
	}
	public void setRMNC1_ID_0(String rMNC1_ID_0) {
		RMNC1_ID_0 = rMNC1_ID_0;
	}
	public String getAGRM1_AMOUNT_0() {
		return AGRM1_AMOUNT_0;
	}
	public void setAGRM1_AMOUNT_0(String aGRM1_AMOUNT_0) {
		AGRM1_AMOUNT_0 = aGRM1_AMOUNT_0;
	}
	public String getAGRM1_SDMT_CODE_LOV1() {
		return AGRM1_SDMT_CODE_LOV1;
	}
	public void setAGRM1_SDMT_CODE_LOV1(String aGRM1_SDMT_CODE_LOV1) {
		AGRM1_SDMT_CODE_LOV1 = aGRM1_SDMT_CODE_LOV1;
	}
	public String getAGRM1_PAYROLL_DATE_0() {
		return AGRM1_PAYROLL_DATE_0;
	}
	public void setAGRM1_PAYROLL_DATE_0(String aGRM1_PAYROLL_DATE_0) {
		AGRM1_PAYROLL_DATE_0 = aGRM1_PAYROLL_DATE_0;
	}
	public String getAGRM1_PAYROLL_DATE_0_X1() {
		return AGRM1_PAYROLL_DATE_0_X1;
	}
	public void setAGRM1_PAYROLL_DATE_0_X1(String aGRM1_PAYROLL_DATE_0_X1) {
		AGRM1_PAYROLL_DATE_0_X1 = aGRM1_PAYROLL_DATE_0_X1;
	}
	public String getINRM1_CNTRL_ID_0() {
		return INRM1_CNTRL_ID_0;
	}
	public void setINRM1_CNTRL_ID_0(String iNRM1_CNTRL_ID_0) {
		INRM1_CNTRL_ID_0 = iNRM1_CNTRL_ID_0;
	}
	public String getINRM1_AMOUNT_0() {
		return INRM1_AMOUNT_0;
	}
	public void setINRM1_AMOUNT_0(String iNRM1_AMOUNT_0) {
		INRM1_AMOUNT_0 = iNRM1_AMOUNT_0;
	}
	

	public void setServiceParameters()
	{
		this.CONTROL_SELECTION_0 = Stock.GetParameterValue("CONTROL_SELECTION_0");
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setAGRM1_RMNC_ID_0(Stock.GetParameterValue("AGRM1_RMNC_ID_0"));
		
	/*	this.setAGRM1_AMOUNT_0(Stock.GetParameterValue("AGRM1_AMOUNT_0"));
		this.setAGRM1_PAYROLL_DATE_0(Stock.GetParameterValue("AGRM1_PAYROLL_DATE_0"));
		this.setAGRM1_PAYROLL_DATE_0_X1(Stock.GetParameterValue("AGRM1_PAYROLL_DATE_0_X1"));
		this.setAGRM1_SDMT_CODE_LOV1(Stock.GetParameterValue("AGRM1_SDMT_CODE_LOV1"));
		this.setBHRM1_AMOUNT_0(Stock.GetParameterValue("BHRM1_AMOUNT_0"));
		this.setBHRM1_BHSX_ID_LOV0(Stock.GetParameterValue("BHRM1_BHSX_ID_LOV0"));
		this.setBHRM1_BKA_CODE_LOV1(Stock.GetParameterValue("BHRM1_BKA_CODE_LOV1"));
		this.setBHRM1_CASH_EFFDATE_0(Stock.GetParameterValue("BHRM1_CASH_EFFDATE_0"));
		this.setCONTROL_SELECTION_0_X1(Stock.GetParameterValue("CONTROL_SELECTION_0_X1"));
		this.setINRM1_AMOUNT_0(Stock.GetParameterValue("INRM1_AMOUNT_0"));
		this.setINRM1_CNTRL_ID_0(Stock.GetParameterValue("INRM1_CNTRL_ID_0"));
		this.setINRM1_CNTRL_ID_0(Stock.GetParameterValue("INRM1_CNTRL_ID_0"));
		this.setRMNC1_AMOUNT_0(Stock.GetParameterValue("RMNC1_AMOUNT_0"));
		this.setRMNC1_GA_ID_0(Stock.GetParameterValue("RMNC1_GA_ID_0"));
		this.setRMNC1_ID_0(Stock.GetParameterValue("RMNC1_ID_0"));
		*/
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
				+"&AGRM1_RMNC_ID_0="+ AGRM1_RMNC_ID_0
				/*+"&BHRM1_BKA_CODE_LOV1="+ BHRM1_BKA_CODE_LOV1 
				+"&BHRM1_AMOUNT_0="+BHRM1_AMOUNT_0
				+"&BHRM1_CASH_EFFDATE_0="+BHRM1_CASH_EFFDATE_0
				+"&BHRM1_BHSX_ID_LOV0="+BHRM1_BHSX_ID_LOV0
				+"&RMNC1_GA_ID_0="+RMNC1_GA_ID_0
				+"&RMNC1_AMOUNT_0="+RMNC1_AMOUNT_0
				+"&CONTROL_SELECTION_0_X1="+CONTROL_SELECTION_0_X1
				+"&RMNC1_ID_0="+RMNC1_ID_0
				+"&AGRM1_AMOUNT_0="+AGRM1_AMOUNT_0
				+"&AGRM1_SDMT_CODE_LOV1="+AGRM1_SDMT_CODE_LOV1
				+"&AGRM1_PAYROLL_DATE_0=" + AGRM1_PAYROLL_DATE_0
				+"&AGRM1_PAYROLL_DATE_0_X1=" + AGRM1_PAYROLL_DATE_0_X1
				+"&INRM1_CNTRL_ID_0=" + INRM1_CNTRL_ID_0
				+"&INRM1_AMOUNT_0=" + INRM1_AMOUNT_0
		*/		+"&numOfRowsInTable=20&json=false&handlePopups=false";	
		Reporter.logEvent(Status.INFO, "Prepare test data for INDR service", this.queryString.replaceAll("&", "\n"), false);
		
		
	}
	
	public void runService()
	{
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
			Reporter.logEvent(Status.PASS, "Run INDR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run INDR service", "Running Failed:", false);
		}	
	}
	
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
		}
	}
	
	public void validateOutputInDatabase() throws SQLException{
		String rmnc_id = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForINDR")[1], this.AGRM1_RMNC_ID_0);
		if(queryResultSet.next())
		{
			rmnc_id = queryResultSet.getString("ID");
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable Name: REMIT_NOTICE", false);
			Reporter.logEvent(Status.INFO, "Validating from DATABASE:\nTableName: REMIT_NOTICE", "ID: "+queryResultSet.getString("ID")+
					"\nEV_ID: "+queryResultSet.getString("EV_ID")+
					"\nSEQNBR: "+queryResultSet.getString("STEP_SEQNBR")+
					"\nSTATUS_CODE: "+queryResultSet.getString("BALANCE_STATUS_CODE")+
					"\nAMOUNT: "+queryResultSet.getString("AMOUNT"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(this.AGRM1_RMNC_ID_0.equalsIgnoreCase(rmnc_id)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Remit ID from Input and Database", "Both the values are same as Expected"+
					"\nRemit ID: "+"From Input: "+this.AGRM1_RMNC_ID_0+"\nFrom Database: "+rmnc_id, false);
		}
	}
	
}
