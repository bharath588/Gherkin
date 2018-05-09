package pageobject.CSHF;

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

public class CSHF_Add_Compliance {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CSHF_Add_Compliance_Service_History_Record_for_Group_Account";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String LOGON_DATABASE_X1;
	String LOGON_PASSWORD_X1;
	String LOGON_USERNAME_X1;
	String LOGON_DATABASE_X2;
	String LOGON_PASSWORD_X2;
	String LOGON_USERNAME_X2;
	String GROUP_HEADER1_N_GA_ID_0;
	String SERVICE_TYPE_LOV;
	String RECORD_TYPE_LOV;
	String CSH_JOIN_EFFDATE_0;
	String CSH_JOIN_SSN_0;
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
	public void setLOGON_DATABASE_X1(String lOGON_DATABASE_X1) {
		LOGON_DATABASE_X1 = lOGON_DATABASE_X1;
	}
	public void setLOGON_PASSWORD_X1(String lOGON_PASSWORD_X1) {
		LOGON_PASSWORD_X1 = lOGON_PASSWORD_X1;
	}
	public void setLOGON_USERNAME_X1(String lOGON_USERNAME_X1) {
		LOGON_USERNAME_X1 = lOGON_USERNAME_X1;
	}
	public void setLOGON_DATABASE_X2(String lOGON_DATABASE_X2) {
		LOGON_DATABASE_X2 = lOGON_DATABASE_X2;
	}
	public void setLOGON_PASSWORD_X2(String lOGON_PASSWORD_X2) {
		LOGON_PASSWORD_X2 = lOGON_PASSWORD_X2;
	}
	public void setLOGON_USERNAME_X2(String lOGON_USERNAME_X2) {
		LOGON_USERNAME_X2 = lOGON_USERNAME_X2;
	}
	public void setGROUP_HEADER1_N_GA_ID_0(String gROUP_HEADER1_N_GA_ID_0) {
		GROUP_HEADER1_N_GA_ID_0 = gROUP_HEADER1_N_GA_ID_0;
	}
	public void setSERVICE_TYPE_LOV(String sERVICE_TYPE_LOV) {
		SERVICE_TYPE_LOV = sERVICE_TYPE_LOV;
	}
	public void setRECORD_TYPE_LOV(String rECORD_TYPE_LOV) {
		RECORD_TYPE_LOV = rECORD_TYPE_LOV;
	}
	public void setCSH_JOIN_EFFDATE_0(String cSH_JOIN_EFFDATE_0) {
		CSH_JOIN_EFFDATE_0 = cSH_JOIN_EFFDATE_0;
	}
	public void setCSH_JOIN_SSN_0(String cSH_JOIN_SSN_0) {
		CSH_JOIN_SSN_0 = cSH_JOIN_SSN_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_DATABASE_X1(Stock.GetParameterValue("LOGON_DATABASE_X1"));
		this.setLOGON_PASSWORD_X1(Stock.GetParameterValue("LOGON_PASSWORD_X1"));
		this.setLOGON_USERNAME_X1(Stock.GetParameterValue("LOGON_USERNAME_X1"));
		this.setLOGON_DATABASE_X2(Stock.GetParameterValue("LOGON_DATABASE_X2"));
		this.setLOGON_PASSWORD_X2(Stock.GetParameterValue("LOGON_PASSWORD_X2"));
		this.setLOGON_USERNAME_X2(Stock.GetParameterValue("LOGON_USERNAME_X2"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGROUP_HEADER1_N_GA_ID_0(Stock.GetParameterValue("GROUP_HEADER1_N_GA_ID_0"));
		this.setSERVICE_TYPE_LOV(Stock.GetParameterValue("SERVICE_TYPE_LOV"));
		this.setRECORD_TYPE_LOV(Stock.GetParameterValue("rECORD_TYPE_LOV"));
		this.setCSH_JOIN_EFFDATE_0(Stock.GetParameterValue("cSH_JOIN_EFFDATE_0"));
		this.setCSH_JOIN_SSN_0(Stock.GetParameterValue("cSH_JOIN_SSN_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				"&LOGON_USERNAME_X1="+LOGON_USERNAME_X1+"&LOGON_PASSWORD_X1="+LOGON_PASSWORD_X1+"&LOGON_DATABASE_X1="+LOGON_DATABASE_X1+
				"&LOGON_USERNAME_X2="+LOGON_USERNAME_X2+"&LOGON_PASSWORD_X2="+LOGON_PASSWORD_X2+"&LOGON_DATABASE_X2="+LOGON_DATABASE_X2+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GROUP_HEADER1_N_GA_ID_0="+GROUP_HEADER1_N_GA_ID_0+"&SERVICE_TYPE_LOV="+SERVICE_TYPE_LOV+"&RECORD_TYPE_LOV="+RECORD_TYPE_LOV+
				"&CSH_JOIN_EFFDATE_0="+CSH_JOIN_EFFDATE_0+"&CSH_JOIN_SSN_0="+CSH_JOIN_SSN_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for CSHF service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run CSHF service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CSHF service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
			
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSHFAdd")[1], this.GROUP_HEADER1_N_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database\nTablename: COMPLIANCE_SERVICE_HISTORY", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "GA_ID: "+queryResultSet.getString("GA_ID")+
					"\nSERVICE_TYPE: "+queryResultSet.getString("SERVICE_TYPE")+
					"\nRECORD_TYPE: "+queryResultSet.getString("RECORD_TYPE")+
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nIND_ID: "+queryResultSet.getString("IND_ID"), false);
			}	
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
	
	public void FlushDataCreated(){
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSHFAddDelete")[1], this.GROUP_HEADER1_N_GA_ID_0);
	}
	
		
}
