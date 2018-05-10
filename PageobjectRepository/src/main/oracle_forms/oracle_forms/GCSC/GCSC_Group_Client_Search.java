package pageobject.GCSC;

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

public class GCSC_Group_Client_Search {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GCSC_Group_Client_Search";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String GC1_ID_0;
	
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
	public void setGC1_ID_0(String gC1_ID_0) {
		GC1_ID_0 = gC1_ID_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setGC1_ID_0(Stock.GetParameterValue("GC1_ID_0"));
	
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&GC1_ID_0="+GC1_ID_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for GRMT Inquire Money Types service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() throws SQLException {
		try {
				this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
				serviceURL += this.queryString;
				docBuilderFactory = DocumentBuilderFactory.newInstance();
				docBuilderFactory.setIgnoringComments(true);
				docBuilderFactory.setIgnoringElementContentWhitespace(true);
				docBuilderFactory.setNamespaceAware(true);
				docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.parse(serviceURL);
		//		System.out.println(serviceURL);
				doc.getDocumentElement().normalize();
				Reporter.logEvent(Status.PASS, "Run GCSC Group Client Search service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run GCSC Group Client Search service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From response: ","GA_ID: "+doc.getElementsByTagName("GA1_ID_0").item(0).getTextContent()+
					"\nPLAN NAME: "+doc.getElementsByTagName("GA1_PLAN_NAME_0").item(0).getTextContent()+
					"\nPROD_ID: "+doc.getElementsByTagName("GA1_PROD_ID_0").item(0).getTextContent()+
					"\nNAME: "+doc.getElementsByTagName("GC1_NAME_TEMP_0").item(0).getTextContent()+
					"\nSTATE_CODE: "+doc.getElementsByTagName("GC1_STATE_CODE_0").item(0).getTextContent(),false);
		}
		 else {
				Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
				System.out.println(Error);
			}
				
	}
	
	public void validateInDatabase() throws SQLException{

		String GA_ID = doc.getElementsByTagName("GA1_ID_0").item(0).getTextContent();
		String PLAN_NAME = doc.getElementsByTagName("GA1_PLAN_NAME_0").item(0).getTextContent();
		String PROD_ID = doc.getElementsByTagName("GA1_PROD_ID_0").item(0).getTextContent();
		String NAME = doc.getElementsByTagName("GC1_NAME_TEMP_0").item(0).getTextContent();
		String STATE_CODE = doc.getElementsByTagName("GC1_STATE_CODE_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGCSC1")[1], this.GC1_ID_0);
		if(queryResultSet.next()){
			String name = queryResultSet.getString("NAME");
			String state_code = queryResultSet.getString("RES_STATE_CODE");
			Reporter.logEvent(Status.PASS, "Validating existence of Records in Database","Record exists in Database", false);
			
			Reporter.logEvent(Status.INFO, "Values From Database","Table Name: GROUP_CLIENT\nGC_ID: "+queryResultSet.getString("ID")+
					"\nTAX_ID: "+queryResultSet.getString("TAX_ID")+
					"\nNAME: "+queryResultSet.getString("NAME")+
					"\nSTATE_CODE: "+queryResultSet.getString("RES_STATE_CODE"),false); 
			
			if(NAME.equalsIgnoreCase(name) && (STATE_CODE.equalsIgnoreCase(state_code))){
				Reporter.logEvent(Status.PASS, "Comparing and validating GROUP CLIENT NAME and STATE CODE from Response and Database", "Both the values are same as expected"+
			"\nNAME:"+"\nFrom Response: "+NAME+"\nFrom Database: "+name+
			"\nSTATE CODE:"+"\nFrom Response: "+STATE_CODE+"\nFrom Database: "+state_code,false);
			}
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGCSC2")[1], this.GC1_ID_0);
		if(queryResultSet.next()){
			String prod_id = queryResultSet.getString("PROD_ID");
			
			Reporter.logEvent(Status.PASS, "Validating existence of Records in Database","Record exists in Database", false);
			
			Reporter.logEvent(Status.INFO, "Values From Database","Table Name: GROUP_ACCOUNT\nGA_ID: "+queryResultSet.getString("ID")+
					"\nPROD_ID: "+queryResultSet.getString("PROD_ID")+
					"\nPLAN_ID: "+queryResultSet.getString("PLAN_ID")+
					"\nSTATUS_CODE: "+queryResultSet.getString("STATUS_CODE"),false); 
			
			if(PROD_ID.equalsIgnoreCase(prod_id)){
				Reporter.logEvent(Status.PASS, "Comparing and validating PROD ID from Response and Database", "Both the values are same as expected"+			
			"\nFrom Response: "+PROD_ID+"\nFrom Database: "+prod_id,false);
			}
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGCSC3")[1], this.GC1_ID_0);
		if(queryResultSet.next()){
			String plan_name = queryResultSet.getString("NAME");
			
			Reporter.logEvent(Status.PASS, "Validating existence of Records in Database","Record exists in Database", false);
			
			Reporter.logEvent(Status.INFO, "Values From Database","Table Name: PLAN\nPLAN_ID: "+queryResultSet.getString("ID")+
					"\nNAME: "+queryResultSet.getString("NAME")+
					"\nCITY: "+queryResultSet.getString("CITY")+
					"\nSTATE_CODE: "+queryResultSet.getString("STATE_CODE"),false); 
			
			if(PLAN_NAME.equalsIgnoreCase(plan_name)){
				Reporter.logEvent(Status.PASS, "Comparing and validating PLAN NAME from Response and Database", "Both the values are same as expected"+			
			"\nFrom Response: "+PLAN_NAME+"\nFrom Database: "+plan_name,false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of Records in Database","No Record exists ", false);
		}
		
	}
}
