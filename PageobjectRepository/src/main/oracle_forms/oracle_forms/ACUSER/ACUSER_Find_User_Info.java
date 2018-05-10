package pageobject.ACUSER;

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

public class ACUSER_Find_User_Info {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ACUSER_Find_User_information_for_a_KL2_type_user";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String USERS_LOGON_ID_0;
	
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
	public void setUSERS_LOGON_ID_0(String uSERS_LOGON_ID_0) {
		USERS_LOGON_ID_0 = uSERS_LOGON_ID_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setUSERS_LOGON_ID_0(Stock.GetParameterValue("USERS_LOGON_ID_0"));
		 
		 queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+ 
				 "&LOGON_DATABASE="+LOGON_DATABASE+
				  "&LOGON_PASSWORD="+LOGON_PASSWORD+
				 "&LOGON_USERNAME="+LOGON_USERNAME+
				 "&USERS_LOGON_ID_0="+USERS_LOGON_ID_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for ACUSER service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run  ACUSER service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run ACUSER service", "Running Failed:", false);
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
	
		Reporter.logEvent(Status.INFO, "From response: ","Expected Results: The User's information should display"+
		"\nUSERS_USER_DIRECTORY_PATH_0: "+doc.getElementsByTagName("USERS_USER_DIRECTORY_PATH_0").item(0).getTextContent(),false);
	}
	
	public void validateInDatabase() throws SQLException{
		
		/*queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForFindUserInfo")[1]);
		
		if(queryResultSet.next()){
			System.out.println("Records exists for Account Transaction");
			Reporter.logEvent(Status.PASS, "Validating From database", "USER_LOGON_ID: "+ queryResultSet.getString("USER_LOGON_ID")+
					"\nSJTY_CODE: "+queryResultSet.getString("SJTY_CODE"), false);
		}
		else{
	//		Reporter.logEvent(Status.FAIL, "Validating from database: ", "No record found in databse", false);
		}
  */
	}
}