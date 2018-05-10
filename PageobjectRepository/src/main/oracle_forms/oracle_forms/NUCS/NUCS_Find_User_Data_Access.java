package pageobject.NUCS;

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

public class NUCS_Find_User_Data_Access {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/NUCS_Find_user_with_data_access";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String USER_CLASS_ID_0;
	
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
	public void setUSER_CLASS_ID_0(String uSER_CLASS_ID_0) {
		USER_CLASS_ID_0 = uSER_CLASS_ID_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setUSER_CLASS_ID_0(Stock.GetParameterValue("USER_CLASS_ID_0"));
	/*	 queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTogetUserLogonId")[1]);
		 if(queryResultSet.next()){
			 this.setUSER_CLASS_ID_0(queryResultSet.getString("USER_LOGON_ID"));
		 }*/
		 		 
		 queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				 "&LOGON_USERNAME="+LOGON_USERNAME+"&USER_CLASS_ID_0="+USER_CLASS_ID_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for NUCS service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run  NUCS service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run NUCS service", "Running Failed:", false);
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
	
	public void validateInDatabase() throws SQLException{
		String code = this.USER_CLASS_ID_0;
		String code_db = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForFindUserInfo")[1],code);
		
		if(queryResultSet.next()){
			code_db = queryResultSet.getString("USER_LOGON_ID");
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
			Reporter.logEvent(Status.INFO, "Values From database", "USER_LOGON_ID: "+ queryResultSet.getString("USER_LOGON_ID"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating from database: ", "No record found in databse", false);
		}
		if(code.equalsIgnoreCase(code_db)){
			Reporter.logEvent(Status.PASS,"Validating USCS_ID from Response and Database\nTable Name: USER#USER_CLASS","USCS_ID\n"+
		"From Response: "+code+"\nFrom Database: "+code_db,false);
		}
  
	}

}
