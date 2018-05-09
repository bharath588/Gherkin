package pageobject.group_info;

import generallib.DateCompare;
import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class GRIF_Group_Info {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GRIF_PROCESS";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	ResultSet queryResultSet;
	
	//input parameters
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String SDIO_ID_LOV;
	String FEE_TYPE_LOV;
	String GA_INV_FEE_DETL_EFFDATE_1;
	String RATE_TYPE_LOV;
	String GA_INV_FEE_DETL_RATE_DISP_1;
	String GA_INV_FEE_DETL_INV_CUSIP_NUMBER_1;
//	String GA_INV_FEE_DETL_INV_CUSIP_NUMBER_0_X1;
//	String GA_INV_FEE_DETL_INV_EXT_TRADE_ACCT_NBR_0;
	String GA_INV_FEE_DETL_AGREEMENT_ID_1;
	String GA_INV_FEE_DETL_FEE_ID_1;
	String GA_INV_FEE_DETL_METHOD_ID_1;
	String GA_INV_FEE_DETL_TRADING_ACCOUNT_1;
	String GA_INV_FEE_DETL_ACCOUNT_NUMBER_1;
	String GA_INV_FEE_DETL_REVENUE_CLIENT_1;
	String GA_INV_FEE_DETL_EXTRACTION_METHOD_1;
	String GA_INV_FEE_DETL_INV_START_DATE_1;
	String GA_INV_FEE_DETL_INV_STOP_DATE_1;
	
	//output parameters
	String  GROUP_HEADER_TODAY_0; 
	String  GROUP_HEADER_GA_ID_0 ;
	String  GROUP_HEADER_PLAN_NAME_0; 
	String  GROUP_HEADER_PROD_ID_0;
	String  GROUP_HEADER_PROV_CO_0;
	String  GROUP_HEADER_AUTO_INV_FEE_LOAD_HOLD_IND_0; 
	String  GA_INV_FEE_DETL_MANUAL_LOAD_INDICATOR_0; 
	String  STEP_TYPE_CURSOR_8;
	
		
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
	public void setSDIO_ID_LOV(String sDIO_ID_LOV) {
		SDIO_ID_LOV = sDIO_ID_LOV;
	}
	public void setFEE_TYPE_LOV(String fEE_TYPE_LOV) {
		FEE_TYPE_LOV = fEE_TYPE_LOV;
	}
	public void setGA_INV_FEE_DETL_EFFDATE_1(String gA_INV_FEE_DETL_EFFDATE_1) {
		GA_INV_FEE_DETL_EFFDATE_1 = gA_INV_FEE_DETL_EFFDATE_1;
	}
	public void setRATE_TYPE_LOV(String rATE_TYPE_LOV) {
		RATE_TYPE_LOV = rATE_TYPE_LOV;
	}
	public void setGA_INV_FEE_DETL_RATE_DISP_1(String gA_INV_FEE_DETL_RATE_DISP_1) {
		GA_INV_FEE_DETL_RATE_DISP_1 = gA_INV_FEE_DETL_RATE_DISP_1;
	}
	public void setGA_INV_FEE_DETL_INV_CUSIP_NUMBER_1(
			String gA_INV_FEE_DETL_INV_CUSIP_NUMBER_1) {
		GA_INV_FEE_DETL_INV_CUSIP_NUMBER_1 = gA_INV_FEE_DETL_INV_CUSIP_NUMBER_1;
	}
	public void setGA_INV_FEE_DETL_AGREEMENT_ID_1(
			String gA_INV_FEE_DETL_AGREEMENT_ID_1) {
		GA_INV_FEE_DETL_AGREEMENT_ID_1 = gA_INV_FEE_DETL_AGREEMENT_ID_1;
	}
	public void setGA_INV_FEE_DETL_FEE_ID_1(String gA_INV_FEE_DETL_FEE_ID_1) {
		GA_INV_FEE_DETL_FEE_ID_1 = gA_INV_FEE_DETL_FEE_ID_1;
	}
	public void setGA_INV_FEE_DETL_METHOD_ID_1(String gA_INV_FEE_DETL_METHOD_ID_1) {
		GA_INV_FEE_DETL_METHOD_ID_1 = gA_INV_FEE_DETL_METHOD_ID_1;
	}
	public void setGA_INV_FEE_DETL_TRADING_ACCOUNT_1(
			String gA_INV_FEE_DETL_TRADING_ACCOUNT_1) {
		GA_INV_FEE_DETL_TRADING_ACCOUNT_1 = gA_INV_FEE_DETL_TRADING_ACCOUNT_1;
	}
	public void setGA_INV_FEE_DETL_ACCOUNT_NUMBER_1(
			String gA_INV_FEE_DETL_ACCOUNT_NUMBER_1) {
		GA_INV_FEE_DETL_ACCOUNT_NUMBER_1 = gA_INV_FEE_DETL_ACCOUNT_NUMBER_1;
	}
	public void setGA_INV_FEE_DETL_REVENUE_CLIENT_1(
			String gA_INV_FEE_DETL_REVENUE_CLIENT_1) {
		GA_INV_FEE_DETL_REVENUE_CLIENT_1 = gA_INV_FEE_DETL_REVENUE_CLIENT_1;
	}
	public void setGA_INV_FEE_DETL_EXTRACTION_METHOD_1(
			String gA_INV_FEE_DETL_EXTRACTION_METHOD_1) {
		GA_INV_FEE_DETL_EXTRACTION_METHOD_1 = gA_INV_FEE_DETL_EXTRACTION_METHOD_1;
	}
	public void setGA_INV_FEE_DETL_INV_START_DATE_1(
			String gA_INV_FEE_DETL_INV_START_DATE_1) {
		GA_INV_FEE_DETL_INV_START_DATE_1 = gA_INV_FEE_DETL_INV_START_DATE_1;
	}
	public void setGA_INV_FEE_DETL_INV_STOP_DATE_1(
			String gA_INV_FEE_DETL_INV_STOP_DATE_1) {
		GA_INV_FEE_DETL_INV_STOP_DATE_1 = gA_INV_FEE_DETL_INV_STOP_DATE_1;
	}
	//output
	public String getGROUP_HEADER_TODAY_0() {
		return GROUP_HEADER_TODAY_0;
	}
	public String getGROUP_HEADER_GA_ID_0() {
		return GROUP_HEADER_GA_ID_0;
	}
	public String getGROUP_HEADER_PLAN_NAME_0() {
		return GROUP_HEADER_PLAN_NAME_0;
	}
	public String getGROUP_HEADER_PROD_ID_0() {
		return GROUP_HEADER_PROD_ID_0;
	}
	public String getGROUP_HEADER_PROV_CO_0() {
		return GROUP_HEADER_PROV_CO_0;
	}
	public String getGROUP_HEADER_AUTO_INV_FEE_LOAD_HOLD_IND_0() {
		return GROUP_HEADER_AUTO_INV_FEE_LOAD_HOLD_IND_0;
	}
	public String getGA_INV_FEE_DETL_MANUAL_LOAD_INDICATOR_0() {
		return GA_INV_FEE_DETL_MANUAL_LOAD_INDICATOR_0;
	}
	public String getSTEP_TYPE_CURSOR_8() {
		return STEP_TYPE_CURSOR_8;
	}
		
	
	
	//method set service parameters
	public void setServiceParameters() throws ParseException
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setSDIO_ID_LOV(Stock.GetParameterValue("SDIO_ID_LOV"));
		this.setFEE_TYPE_LOV(Stock.GetParameterValue("FEE_TYPE_LOV"));
		this.setGA_INV_FEE_DETL_EFFDATE_1(Stock.GetParameterValue("GA_INV_FEE_DETL_EFFDATE_1"));
		this.setRATE_TYPE_LOV(Stock.GetParameterValue("RATE_TYPE_LOV"));
		this.setGA_INV_FEE_DETL_RATE_DISP_1(Stock.GetParameterValue("GA_INV_FEE_DETL_RATE_DISP_1"));
		this.setGA_INV_FEE_DETL_INV_CUSIP_NUMBER_1(Stock.GetParameterValue("GA_INV_FEE_DETL_INV_CUSIP_NUMBER_1"));
	//	this.setGA_INV_FEE_DETL_INV_CUSIP_NUMBER_0_X1(Stock.GetParameterValue("GA_INV_FEE_DETL_INV_CUSIP_NUMBER_0_X1"));
	//	this.setGA_INV_FEE_DETL_INV_EXT_TRADE_ACCT_NBR_0(Stock.GetParameterValue("GA_INV_FEE_DETL_INV_EXT_TRADE_ACCT_NBR_1"));
		this.setGA_INV_FEE_DETL_AGREEMENT_ID_1(Stock.GetParameterValue(" GA_INV_FEE_DETL_AGREEMENT_ID_1"));
		this.setGA_INV_FEE_DETL_FEE_ID_1(Stock.GetParameterValue("GA_INV_FEE_DETL_FEE_ID_1"));
		this.setGA_INV_FEE_DETL_METHOD_ID_1(Stock.GetParameterValue("GA_INV_FEE_DETL_METHOD_ID_1"));
		this.setGA_INV_FEE_DETL_TRADING_ACCOUNT_1(Stock.GetParameterValue("GA_INV_FEE_DETL_TRADING_ACCOUNT_1"));
		this.setGA_INV_FEE_DETL_ACCOUNT_NUMBER_1(Stock.GetParameterValue("GA_INV_FEE_DETL_ACCOUNT_NUMBER_1"));
		this.setGA_INV_FEE_DETL_REVENUE_CLIENT_1(Stock.GetParameterValue("GA_INV_FEE_DETL_REVENUE_CLIENT_1"));
		this.setGA_INV_FEE_DETL_EXTRACTION_METHOD_1(Stock.GetParameterValue("GA_INV_FEE_DETL_EXTRACTION_METHOD_1"));
		this.setGA_INV_FEE_DETL_INV_START_DATE_1(Stock.GetParameterValue("GA_INV_FEE_DETL_INV_START_DATE_1"));
		this.setGA_INV_FEE_DETL_INV_STOP_DATE_1(Stock.GetParameterValue("GA_INV_FEE_DETL_INV_STOP_DATE_1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&SDIO_ID_LOV="+SDIO_ID_LOV+
				"&FEE_TYPE_LOV="+FEE_TYPE_LOV+"&GA_INV_FEE_DETL_EFFDATE_1="+GA_INV_FEE_DETL_EFFDATE_1+"&RATE_TYPE_LOV="+RATE_TYPE_LOV+
				"&GA_INV_FEE_DETL_RATE_DISP_1="+GA_INV_FEE_DETL_RATE_DISP_1+"&GA_INV_FEE_DETL_INV_CUSIP_NUMBER_1="+GA_INV_FEE_DETL_INV_CUSIP_NUMBER_1+
				"&GA_INV_FEE_DETL_AGREEMENT_ID_1="+GA_INV_FEE_DETL_AGREEMENT_ID_1+"&GA_INV_FEE_DETL_FEE_ID_1="+GA_INV_FEE_DETL_FEE_ID_1+
				"&GA_INV_FEE_DETL_METHOD_ID_1="+GA_INV_FEE_DETL_METHOD_ID_1+"&GA_INV_FEE_DETL_TRADING_ACCOUNT_1="+GA_INV_FEE_DETL_TRADING_ACCOUNT_1+
				"&GA_INV_FEE_DETL_ACCOUNT_NUMBER_1="+GA_INV_FEE_DETL_ACCOUNT_NUMBER_1+"&GA_INV_FEE_DETL_REVENUE_CLIENT_1="+GA_INV_FEE_DETL_REVENUE_CLIENT_1+
				"&GA_INV_FEE_DETL_EXTRACTION_METHOD_1="+GA_INV_FEE_DETL_EXTRACTION_METHOD_1+"&GA_INV_FEE_DETL_INV_START_DATE_1="+GA_INV_FEE_DETL_INV_START_DATE_1+
				"&GA_INV_FEE_DETL_INV_STOP_DATE_1="+GA_INV_FEE_DETL_INV_STOP_DATE_1+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for GRIF service", this.queryString.replaceAll("&", "\n"), false);
		
		
	}
	
//run service method
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
			Reporter.logEvent(Status.PASS, "Run GRIF service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run GRIF service", "Running Failed:", false);
		}
	}
	
	
	//VALIDATE ERROR TAG
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response: ", "SDIO_ID: " + doc.getElementsByTagName("GA_INV_FEE_DETL_SDIO_ID_0").item(0).getTextContent() +"\n", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
		}
	}
	
	
	//validating in database
	public void validateInDatabase() throws SQLException, ParseException
	{
	
		/*queryResultSet = DB.executeQuery(Stock.getTestQuery("query_insert_termRecords")[0], Stock.getTestQuery("query_insert_termRecords")[1], GET_GA_GA_ID_0);
		
		if(queryResultSet.next())
		{
			Reporter.logEvent(Status.PASS, "Validate the record created in Database ga_inv_fee_detl table", "Record can be inserted, terminated", false);
			String ga_id = queryResultSet.getString("GA_ID");
			String effdate = queryResultSet.getString("EFFDATE");
			String termdate = queryResultSet.getString("TERMDATE");
			System.out.println("GA_ID: " + ga_id + "\nEFFDATE: " + effdate + "\nTERMDATE: " + termdate +"\n");
			Reporter.logEvent(Status.PASS, "From Database: ", "GA_ID: " + ga_id +"\n" + "EFFDATE: " + effdate +"\n" + "TERMDATE: " + termdate, false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}*/
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        Date date1 = sdf.parse(GA_INV_FEE_DETL_INV_START_DATE_1);
        Date date2 = sdf.parse(GA_INV_FEE_DETL_INV_STOP_DATE_1);

        if(date1.compareTo(date2)<0){
        	Reporter.logEvent(Status.PASS, "Comparing Start and Stop Dates", "START DATE is lesser than STOP DATE "+"\nSTART DATE: " + date1 +"\nSTOP DATE: " + date2, false);
        }

        else{
        	Reporter.logEvent(Status.FAIL, "Comparing Start and Stop Dates", "START DATE: " + date1 +"\n" +" Is greater than "+ "STOP DATE: " + date2, false);
        }
		
	}

}
