package pageobject.cashdropflow;

import generallib.DateCompare;
import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class AGRM_Process {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/AGRM_NEW_PROCESS";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	public ResultSet queryResultSet;
	
	public String AGRM1_AMOUNT_0;
	String LOGON_DATABASE_X1;

	String  AGRM1_PAYROLL_DATE_0;
	String  AGRM1_SDMT_CODE_LOV1;
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String RMNC1_ID_0;
	
	
	
	public String getLOGON_DATABASE_X1() {
		return LOGON_DATABASE_X1;
	}
	public void setLOGON_DATABASE_X1(String lOGON_DATABASE_X1) {
		LOGON_DATABASE_X1 = lOGON_DATABASE_X1;
	}
	public String getLOGON_PASSWORD_X1() {
		return LOGON_PASSWORD_X1;
	}
	public void setLOGON_PASSWORD_X1(String lOGON_PASSWORD_X1) {
		LOGON_PASSWORD_X1 = lOGON_PASSWORD_X1;
	}
	public String getLOGON_USERNAME_X1() {
		return LOGON_USERNAME_X1;
	}
	public void setLOGON_USERNAME_X1(String lOGON_USERNAME_X1) {
		LOGON_USERNAME_X1 = lOGON_USERNAME_X1;
	}



	String LOGON_PASSWORD_X1;
	public String getRMNC1_ID_0() {
		return RMNC1_ID_0;
	}
	public void setRMNC1_ID_0(String rMNC1_ID_0) {
		RMNC1_ID_0 = rMNC1_ID_0;
	}

	

	String LOGON_USERNAME_X1;
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public String getServiceURL() {
		return serviceURL;
	}
	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}
	public DocumentBuilderFactory getDocBuilderFactory() {
		return docBuilderFactory;
	}
	public void setDocBuilderFactory(DocumentBuilderFactory docBuilderFactory) {
		this.docBuilderFactory = docBuilderFactory;
	}
	public DocumentBuilder getDocBuilder() {
		return docBuilder;
	}
	public void setDocBuilder(DocumentBuilder docBuilder) {
		this.docBuilder = docBuilder;
	}
	public Document getDoc() {
		return doc;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	public String getAGRM1_AMOUNT_0() {
		return AGRM1_AMOUNT_0;
	}
	public void setAGRM1_AMOUNT_0(String aGRM1_AMOUNT_0) {
		AGRM1_AMOUNT_0 = aGRM1_AMOUNT_0;
	}
	public String getAGRM1_PAYROLL_DATE_0() {
		return AGRM1_PAYROLL_DATE_0;
	}
	public void setAGRM1_PAYROLL_DATE_0(String aGRM1_PAYROLL_DATE_0) {
		AGRM1_PAYROLL_DATE_0 = aGRM1_PAYROLL_DATE_0;
	}
	public String getAGRM1_SDMT_CODE_LOV1() {
		return AGRM1_SDMT_CODE_LOV1;
	}
	public void setAGRM1_SDMT_CODE_LOV1(String aGRM1_SDMT_CODE_LOV1) {
		AGRM1_SDMT_CODE_LOV1 = aGRM1_SDMT_CODE_LOV1;
	}
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
	public String ToDate(Date indate)
	{
	   String dateString = null;
	   SimpleDateFormat sdfr = new SimpleDateFormat("dd-MMM-yy");
	   try{
		dateString = sdfr.format( indate );
	   }catch (Exception ex ){
		System.out.println(ex);
	   }
	   return dateString.toUpperCase();
	}
	
	public void setServiceParameters () throws SQLException {
		this.LOGON_USERNAME = Stock.GetParameterValue("LOGON_USERNAME");
		this.LOGON_PASSWORD = Stock.GetParameterValue("LOGON_PASSWORD");
		this.LOGON_DATABASE = Stock.GetParameterValue("LOGON_DATABASE_X1");
		this.AGRM1_AMOUNT_0 = Stock.GetParameterValue("AGRM1_AMOUNT_0");
	//	this.AGRM1_PAYROLL_DATE_0 = Stock.GetParameterValue("AGRM1_PAYROLL_DATE_0");
		this.AGRM1_PAYROLL_DATE_0 = ToDate(DateCompare.getUniquedate(Stock.GetParameterValue("RMNC1_GA_ID_0")));
		this.AGRM1_SDMT_CODE_LOV1 = Stock.GetParameterValue("AGRM1_SDMT_CODE_LOV1");
	
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME 
				+"&LOGON_PASSWORD=" + LOGON_PASSWORD 
				+ "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0
				+ "&RMNC1_ID_0=" + RMNC1_ID_0 
				+"&AGRM1_AMOUNT_0=" + AGRM1_AMOUNT_0
				+"&AGRM1_SDMT_CODE_LOV1=" + AGRM1_SDMT_CODE_LOV1
				+"&AGRM1_PAYROLL_DATE_0=" + AGRM1_PAYROLL_DATE_0				 			
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		Reporter.logEvent(Status.INFO, "Prepare test data for AGRM service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run AGRM service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run AGRM service", "Running Failed:", false);
		}
	}

	
	public void validateOutput() throws SQLException
	{
		String error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if(error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validating from Response: ", "Error Tag:" + "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From Response  ", "Cash EFFDATE: " + doc.getElementsByTagName("AGRM1_CASH_EFFDATE_0").item(0).getTextContent() 
								+ "\nGA_ID: " + doc.getElementsByTagName("RMNC1_SCRN_GA_ID_0").item(0).getTextContent(), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating from Response: ", "Error Tag:" + "Error tag is not empty" + error, false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTocheckAGRMProcess")[1],this.RMNC1_ID_0);
		if(queryResultSet != null)
		{
			if(queryResultSet.next())
			{
				Reporter.logEvent(Status.PASS, "Check if any record is created in the AG_REMIT table after the AGRM form is submitted", "A record is created in the ag_remit table for money type and fund type.", false);
				
			Reporter.logEvent(Status.INFO, "Amount in AG_Remit table", queryResultSet.getString("AMOUNT"),false);	
			Reporter.logEvent(Status.INFO, "Money type", queryResultSet.getString("SDMT_CODE"),false);
			Reporter.logEvent(Status.INFO, "Payroll Date", queryResultSet.getString("PAYROLL_DATE"),false);
			}else{
				Reporter.logEvent(Status.FAIL, "Check if any record is created in the AG_REMIT table after the AGRM form is submitted", "No record is created in the ag_remit table for money type and fund type.", false);
			}
		}
		
		
		
	}
	
	
	
}
