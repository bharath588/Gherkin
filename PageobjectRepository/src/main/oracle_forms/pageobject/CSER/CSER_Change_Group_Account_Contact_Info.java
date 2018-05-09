package pageobject.CSER;

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

public class CSER_Change_Group_Account_Contact_Info {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CSER_CHANGE_GROUP_ACCOUNT_CONTACT_INFORMATION";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String GACSV1_CURSOR1_LOV0;
	String GACSV1_TYPE_CODE_LOV1;
	
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
	public void setGACSV1_CURSOR1_LOV0(String gACSV1_CURSOR1_LOV0) {
		GACSV1_CURSOR1_LOV0 = gACSV1_CURSOR1_LOV0;
	}
	public void setGACSV1_TYPE_CODE_LOV1(String gACSV1_TYPE_CODE_LOV1) {
		GACSV1_TYPE_CODE_LOV1 = gACSV1_TYPE_CODE_LOV1;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setGACSV1_CURSOR1_LOV0(Stock.GetParameterValue("GACSV1_CURSOR1_LOV0"));
		this.setGACSV1_TYPE_CODE_LOV1(Stock.GetParameterValue("GACSV1_TYPE_CODE_LOV1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&GACSV1_CURSOR1_LOV0="+GACSV1_CURSOR1_LOV0+"&GACSV1_TYPE_CODE_LOV1="+GACSV1_TYPE_CODE_LOV1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for CSER Change service", this.queryString.replaceAll("&", "\n"), false);
		
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
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run CSER service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CSER service", "Running Failed:", false);
		}
	}

	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From Response: ", 
					"PLAN NAME: "+doc.getElementsByTagName("GET_GA_PLAN_NAME_0").item(0).getTextContent()+
					"\nPROD ID: "+doc.getElementsByTagName("GET_GA_PROD_ID_0").item(0).getTextContent()+
					"\nDESCR: "+doc.getElementsByTagName("STEP_TYPE_DESCR_0").item(0).getTextContent(), false);
		
		} else {
		//	Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
		//	System.out.println(Error);
			Reporter.logEvent(Status.PASS, "From Response: ", 
					"PLAN NAME: "+doc.getElementsByTagName("GET_GA_PLAN_NAME_0").item(0).getTextContent()+
					"\nPROD ID: "+doc.getElementsByTagName("GET_GA_PROD_ID_0").item(0).getTextContent()+
					"\nDESCR: "+doc.getElementsByTagName("STEP_TYPE_DESCR_0").item(0).getTextContent(), false);
		
		}
	}
	
	public void validateInDatabase()throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSERChange")[1], this.GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: CONTACT_SERVICER\nGA#CONTACT_SERVICER", "Record exists in the Database", false);
			
			while(queryResultSet.next()){	
				
				Reporter.logEvent(Status.INFO, "Values from Database",						
						"\nGA_ID: "+queryResultSet.getString("GA_ID")+
						"\nID: "+queryResultSet.getString("ID")+
						"\nCOMPANY: "+queryResultSet.getString("COMPANY_NAME")+
						"\nUSER_LOGON_ID: "+queryResultSet.getString("USER_LOGON_ID"), false);
			}
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
				
	}
}
