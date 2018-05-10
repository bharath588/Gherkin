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

public class CSHF_Query_Compliance_by_Group_Account {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CSHF_Query_Compliance_Service_History_by_Group_Account";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GROUP_HEADER1_N_GA_ID_0;
	String SERVICE_TYPE_LOV;
	
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
	public void setGROUP_HEADER1_N_GA_ID_0(String gROUP_HEADER1_N_GA_ID_0) {
		GROUP_HEADER1_N_GA_ID_0 = gROUP_HEADER1_N_GA_ID_0;
	}
	public void setSERVICE_TYPE_LOV(String sERVICE_TYPE_LOV) {
		SERVICE_TYPE_LOV = sERVICE_TYPE_LOV;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGROUP_HEADER1_N_GA_ID_0(Stock.GetParameterValue("GROUP_HEADER1_N_GA_ID_0"));
		this.setSERVICE_TYPE_LOV(Stock.GetParameterValue("SERVICE_TYPE_LOV"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GROUP_HEADER1_N_GA_ID_0="+GROUP_HEADER1_N_GA_ID_0+"&SERVICE_TYPE_LOV="+SERVICE_TYPE_LOV+
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
			Reporter.logEvent(Status.INFO, "Values From Response: ", "RECORD TYPE: "+ doc.getElementsByTagName("CSH_JOIN_RECORD_TYPE_0").item(0).getTextContent()+
					"\nEFFDATE: "+ doc.getElementsByTagName("CSH_JOIN_EFFDATE_0").item(0).getTextContent()+
					"\nDESCR: "+ doc.getElementsByTagName("CSH_JOIN_RT_DESCRIPTION_0").item(0).getTextContent()+
					"\nSSN: "+ doc.getElementsByTagName("CSH_JOIN_SSN_0").item(0).getTextContent()+
					"\nPLAN NAME: "+ doc.getElementsByTagName("GROUP_HEADER1_N_PLAN_NAME_0").item(0).getTextContent()+
					"\nSERVICE TYPE: "+ doc.getElementsByTagName("GROUP_HEADER1_N_SERVICE_TYPE_0").item(0).getTextContent()+
					"\nSERVICE DESCR: "+ doc.getElementsByTagName("GROUP_HEADER1_N_SERVICE_TYPE_DESC_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String effdate = doc.getElementsByTagName("CSH_JOIN_EFFDATE_0").item(0).getTextContent();
		String code_input = doc.getElementsByTagName("CSH_JOIN_RECORD_TYPE_0").item(0).getTextContent();
		String code_db = null;		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSHFQuery")[1], this.GROUP_HEADER1_N_GA_ID_0, effdate);
		if(DB.getRecordSetCount(queryResultSet)>0){
			while(queryResultSet.next()){
				Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
				code_db = queryResultSet.getString("RECORD_TYPE");
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: COMPLIANCE_SERVICE_HISTORY", "GA_ID: "+queryResultSet.getString("GA_ID")+
					"\nSERVICE_TYPE: "+queryResultSet.getString("SERVICE_TYPE")+
					"\nRECORD_TYPE: "+queryResultSet.getString("RECORD_TYPE")+
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nIND_ID: "+queryResultSet.getString("IND_ID"), false);
				if(code_input.equalsIgnoreCase(code_db)){
					Reporter.logEvent(Status.PASS, "Comapring and Validating RECORD TYPE from Response and Database", "Both the values are same as expected"+
							"\nFrom Response: "+code_input+"\nFrom Database: "+code_db, false);
				}else{
					Reporter.logEvent(Status.FAIL, "Comapring and Validating RECORD TYPE from Response and Database", "Values are not same as expected",false);
				}
			}	
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
		
		
	}
	
}
