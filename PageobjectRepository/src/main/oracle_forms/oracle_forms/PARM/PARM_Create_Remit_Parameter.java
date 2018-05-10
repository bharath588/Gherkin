package pageobject.PARM;

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

public class PARM_Create_Remit_Parameter {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PARM_create_remit_parameter";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String RMP1_MEDIA_TYPE_LOV0;
	String RMP1_FORMAT_CODE_0;
	String RMP1_GCS_BASIS_LOV1;
	String RMP1_PAYROLL_FRE_LOV2;
	String RMP1_PAYROLL_SCHED_START_DATE_0;
	String RMP1_PRE_PAYROLL_LAG_0;
	String RMP1_PRE_PAYROLL_LAG_0_X1;
	String RMP1_REMIT_TYPE_LOV3;
	String RMP1_CASH_EFFDATE_LAG_DAYS_0;
	
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
	public void setRMP1_MEDIA_TYPE_LOV0(String rMP1_MEDIA_TYPE_LOV0) {
		RMP1_MEDIA_TYPE_LOV0 = rMP1_MEDIA_TYPE_LOV0;
	}
	public void setRMP1_FORMAT_CODE_0(String rMP1_FORMAT_CODE_0) {
		RMP1_FORMAT_CODE_0 = rMP1_FORMAT_CODE_0;
	}
	public void setRMP1_GCS_BASIS_LOV1(String rMP1_GCS_BASIS_LOV1) {
		RMP1_GCS_BASIS_LOV1 = rMP1_GCS_BASIS_LOV1;
	}
	public void setRMP1_PAYROLL_FRE_LOV2(String rMP1_PAYROLL_FRE_LOV2) {
		RMP1_PAYROLL_FRE_LOV2 = rMP1_PAYROLL_FRE_LOV2;
	}
	public void setRMP1_PAYROLL_SCHED_START_DATE_0(
			String rMP1_PAYROLL_SCHED_START_DATE_0) {
		RMP1_PAYROLL_SCHED_START_DATE_0 = rMP1_PAYROLL_SCHED_START_DATE_0;
	}
	public void setRMP1_PRE_PAYROLL_LAG_0(String rMP1_PRE_PAYROLL_LAG_0) {
		RMP1_PRE_PAYROLL_LAG_0 = rMP1_PRE_PAYROLL_LAG_0;
	}
	public void setRMP1_PRE_PAYROLL_LAG_0_X1(String rMP1_PRE_PAYROLL_LAG_0_X1) {
		RMP1_PRE_PAYROLL_LAG_0_X1 = rMP1_PRE_PAYROLL_LAG_0_X1;
	}
	public void setRMP1_REMIT_TYPE_LOV3(String rMP1_REMIT_TYPE_LOV3) {
		RMP1_REMIT_TYPE_LOV3 = rMP1_REMIT_TYPE_LOV3;
	}
	public void setRMP1_CASH_EFFDATE_LAG_DAYS_0(String rMP1_CASH_EFFDATE_LAG_DAYS_0) {
		RMP1_CASH_EFFDATE_LAG_DAYS_0 = rMP1_CASH_EFFDATE_LAG_DAYS_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setRMP1_CASH_EFFDATE_LAG_DAYS_0(Stock.GetParameterValue("RMP1_CASH_EFFDATE_LAG_DAYS_0"));
		this.setRMP1_FORMAT_CODE_0(Stock.GetParameterValue("RMP1_FORMAT_CODE_0"));
		this.setRMP1_GCS_BASIS_LOV1(Stock.GetParameterValue("RMP1_GCS_BASIS_LOV1"));
		this.setRMP1_MEDIA_TYPE_LOV0(Stock.GetParameterValue("RMP1_MEDIA_TYPE_LOV0"));
		this.setRMP1_PAYROLL_FRE_LOV2(Stock.GetParameterValue("RMP1_PAYROLL_FRE_LOV2"));
		this.setRMP1_PAYROLL_SCHED_START_DATE_0(Stock.GetParameterValue("RMP1_PAYROLL_SCHED_START_DATE_0"));
		this.setRMP1_PRE_PAYROLL_LAG_0(Stock.GetParameterValue("RMP1_PRE_PAYROLL_LAG_0"));
		this.setRMP1_PRE_PAYROLL_LAG_0_X1(Stock.GetParameterValue("RMP1_PRE_PAYROLL_LAG_0_X1"));
		this.setRMP1_REMIT_TYPE_LOV3(Stock.GetParameterValue("RMP1_REMIT_TYPE_LOV3"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&RMP1_MEDIA_TYPE_LOV0="+RMP1_MEDIA_TYPE_LOV0+"&RMP1_FORMAT_CODE_0="+RMP1_FORMAT_CODE_0+
				"&RMP1_GCS_BASIS_LOV1="+RMP1_GCS_BASIS_LOV1+"&RMP1_PAYROLL_FRE_LOV2="+RMP1_PAYROLL_FRE_LOV2+"&RMP1_PAYROLL_SCHED_START_DATE_0="+RMP1_PAYROLL_SCHED_START_DATE_0+
				"&RMP1_PRE_PAYROLL_LAG_0="+RMP1_PRE_PAYROLL_LAG_0+"&RMP1_PRE_PAYROLL_LAG_0_X1="+RMP1_PRE_PAYROLL_LAG_0_X1+"&RMP1_REMIT_TYPE_LOV3="+RMP1_REMIT_TYPE_LOV3+
				"&RMP1_CASH_EFFDATE_LAG_DAYS_0="+RMP1_CASH_EFFDATE_LAG_DAYS_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for PARM service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run PARM service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PARM service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
	
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response: ", 
					"PLAN NAME: "+ doc.getElementsByTagName("GROUP_HEADER1_PLAN_NAME_0").item(0).getTextContent()+
					"\nPROD ID: "+ doc.getElementsByTagName("GROUP_HEADER1_PROD_ID_0").item(0).getTextContent()+
					"\nREMIT TYPE: "+ doc.getElementsByTagName("RMP1_REMIT_TYPE_0").item(0).getTextContent()+
					"\nIND ID TYPE: "+ doc.getElementsByTagName("RMP1_IND_ID_TYPE_0").item(0).getTextContent()+
					"\nGCS_BASIS: "+ doc.getElementsByTagName("RMP1_GCS_BASIS_0").item(0).getTextContent()+
					"\nGCS_VALUE: "+ doc.getElementsByTagName("RMP1_GCS_BASIS_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String remit_type_db = null;
		String remit_type_resp = doc.getElementsByTagName("RMP1_REMIT_TYPE_0").item(0).getTextContent();
		String gcs_basis = doc.getElementsByTagName("RMP1_GCS_BASIS_0").item(0).getTextContent();
		String gcs_val = doc.getElementsByTagName("RMP1_GCS_VALUE_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPARM")[1], this.GET_GA_GA_ID_0, gcs_basis, gcs_val);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Account Transaction\nTable Name: REMIT_PARM", false);
			remit_type_db = queryResultSet.getString("REMIT_TYPE");
			Reporter.logEvent(Status.INFO, "Values From Database: ", 					
					"AG_ID: "+queryResultSet.getString("AG_ID")+					
					"\nMEDIA TYPE: "+queryResultSet.getString("MEDIA_TYPE")+
					"\nGCS_BASIS: "+queryResultSet.getString("GCS_BASIS")+
					"\nGCS_VALUE: "+queryResultSet.getString("GCS_VALUE")+
					"\nIND_ID_TYPE: "+queryResultSet.getString("IND_ID_TYPE")+
					"\nREMIT TYPE: "+queryResultSet.getString("REMIT_TYPE")
					, false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(remit_type_resp.equalsIgnoreCase(remit_type_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating REMIT TYPE from Response and Database", "Both the values are same as Expected"+
					"\nFrom Response: "+remit_type_resp+"\nFrom Database: "+remit_type_db, false);
		}
	}
}
