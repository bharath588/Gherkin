package pageobject.NUSR;

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

public class NUSR_Query_user_Information {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/NUSR_Query_User";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet2;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String USER_LOGON_ID_0;
	
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
	public void setUSER_CLASS_ID_0(String uSER_LOGON_ID_0) {
		USER_LOGON_ID_0 = uSER_LOGON_ID_0;
	}
	

	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setUSER_CLASS_ID_0(Stock.GetParameterValue("USER_LOGON_ID_0"));
	/*	 queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTogetUserLogonId")[1]);
		 if(queryResultSet.next()){
			 this.setUSER_CLASS_ID_0(queryResultSet.getString("USER_LOGON_ID"));
		 }*/
		 		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME
				 +"&LOGON_PASSWORD="+LOGON_PASSWORD
				 +"&LOGON_DATABASE="+LOGON_DATABASE
				 +"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0
				 +"&USERS_LOGON_ID_0="+USER_LOGON_ID_0
				 +"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for NUSR service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run  NUSR service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run NUSR service", "Running Failed:", false);
			}
			
	}
	
   public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}	
		
	}
   public void deleteFromDatabase() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForNUSR")[1],USER_LOGON_ID_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0)
		{
			Reporter.logEvent(Status.INFO, "Validating Records exists in Database", "Records exists in Database", false);
			DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDeleteNUSR")[1],USER_LOGON_ID_0);
			   
			
		}
		else{
			Reporter.logEvent(Status.INFO, "Validating Records exists in Database", "No record found in databse", false);
		}
		
		
 
	}

   public void validateInDatabaseAfterDelete()throws SQLException{
	   queryResultSet2 = DB.executeQuery(General.dbInstance,Stock.getTestQuery("queryForNUSR")[1],USER_LOGON_ID_0);
	   if(DB.getRecordSetCount(queryResultSet2)>0)
		{
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
			
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "No record found in databse", false);
		}
		
   }
	
	
	
}