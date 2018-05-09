package pageobject.FSET;

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

public class FSET_Find_Fset {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/FSET_Find_an_FSET_for_a_WMAD_file_type_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String FD_ID_0 ;
		
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
	public void setFD_ID_0(String fD_ID_0){
		FD_ID_0 = fD_ID_0;
	}
	
		
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setFD_ID_0(Stock.GetParameterValue("FD_ID_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&FD_ID_0="+FD_ID_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for FSET service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run  FSET service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run FSET service", "Running Failed:", false);
			}
			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
//			Reporter.logEvent(Status.INFO, "Capturing Response after Execution", "Response:\n"+doc.getDocumentElement(), false);
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From response: ","CONTROL_MENU_TITLE_0: "+doc.getElementsByTagName("CONTROL_MENU_TITLE_0").item(0).getTextContent()+
					"\nTRANSACTION_CODE_0: "+doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent()+"\n",false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		
	}
	
	public void validateInDatabase() throws SQLException{
		String fd_id = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForFsetFind")[1], FD_ID_0);
		
		if(queryResultSet.next()){
			fd_id = queryResultSet.getString("ID");
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Records exists in Database", false);
			Reporter.logEvent(Status.INFO, "Values From database", "ID: "+ queryResultSet.getString("ID")+
					"\nCODE: "+ queryResultSet.getString("CODE")+
					"\nDESCRIPTION: "+queryResultSet.getString("DESCR"), false);
		}
		else{
			Reporter.logEvent(Status.INFO, "Validating from database: ", "No record found in databse", false);
		}
		if(this.FD_ID_0.equalsIgnoreCase(fd_id)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating DMTY CODE from Input and Database", "Both the values are same as Expected"+
					"\nDMTY_CODE: "+"From Input: "+this.FD_ID_0+"\nFrom Database: "+fd_id, false);
		}
	}

}

