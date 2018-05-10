package pageobject.UNLOCK;

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

public class UNLOCK_Unlock_Evnt {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/UNLOCK_Unlock_Unlock_Event";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet1;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String REMIT1_RMNC_ID_0;
	
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
	public void setREMIT1_RMNC_ID_0(String rEMIT1_RMNC_ID_0) {
		REMIT1_RMNC_ID_0 = rEMIT1_RMNC_ID_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
	//	 this.setREMIT1_RMNC_ID_0(Stock.GetParameterValue("REMIT1_RMNC_ID_0"));
		 
		 queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTogetRemitID")[1]);
		 if(queryResultSet1.next()){
			 this.setREMIT1_RMNC_ID_0(queryResultSet1.getString("ID"));
			 System.out.println(REMIT1_RMNC_ID_0);
			 Reporter.logEvent(Status.INFO, "INPUT", "REMIT_ID: "+REMIT1_RMNC_ID_0, false);
		}
	
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&REMIT1_RMNC_ID_0="+REMIT1_RMNC_ID_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for UNLOCK service", this.queryString.replaceAll("&", "\n"), false);
		 
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
				System.out.println(serviceURL);
				doc.getDocumentElement().normalize();
			//	System.out.println(doc);
				Reporter.logEvent(Status.PASS, "Run  UNLOCK_Unlock_Event service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run UNLOCK_Unlock_Event service", "Running Failed:", false);
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
	
		Reporter.logEvent(Status.PASS, "From response: ","REMIT1_LOCKED_FOR_UPD_DATETIME_0: "+doc.getElementsByTagName("REMIT1_LOCKED_FOR_UPD_DATETIME_0").item(0).getTextContent()+
				"\nREMIT1_LOCKED_FOR_UPD_USERID_0: "+doc.getElementsByTagName("REMIT1_LOCKED_FOR_UPD_USERID_0").item(0).getTextContent()+
				"\nREMIT1_RESULT_0: "+doc.getElementsByTagName("REMIT1_RESULT_0").item(0).getTextContent()+
				"\n",false);
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(Stock.getTestQuery("queryToUnlockEvnt")[0], Stock.getTestQuery("queryToUnlockEvnt")[1], REMIT1_RMNC_ID_0);
		if(queryResultSet.next()){
			System.out.println("Record Exists");
			Reporter.logEvent(Status.PASS, "From DATABASE: ", "PROD_ID: "+ queryResultSet.getString("PROD_ID")+
					"\nGA_ID: "+queryResultSet.getString("GA_ID")+
					"\nEV_ID: "+queryResultSet.getString("EV_ID")+
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nDESCR: "+queryResultSet.getString("DESCR")+
					"\nTERMDATE: "+queryResultSet.getString("TERMDATE"), false);
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Remit is not LOCKED", false);
		}
		
	} 
	
}
