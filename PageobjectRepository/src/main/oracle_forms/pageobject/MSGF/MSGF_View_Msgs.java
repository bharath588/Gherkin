package pageobject.MSGF;

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

public class MSGF_View_Msgs {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MSGF_View_MesgNotification";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String CONTROL_SELECTION_0_X1;
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String LOGON_DATABASE_X1; 
	String LOGON_PASSWORD_X1; 
	String LOGON_USERNAME_X1;
	String MESSAGE_MT_PGM_NAME_0;
	
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setCONTROL_SELECTION_0_X1(String cONTROL_SELECTION_0_X1) {
		CONTROL_SELECTION_0_X1 = cONTROL_SELECTION_0_X1;
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
	public void setMESSAGE_MT_PGM_NAME_0(String mESSAGE_MT_PGM_NAME_0) {
		MESSAGE_MT_PGM_NAME_0 = mESSAGE_MT_PGM_NAME_0;
	}
	

	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setCONTROL_SELECTION_0_X1(Stock.GetParameterValue("CONTROL_SELECTION_0_X1"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setLOGON_PASSWORD_X1(Stock.GetParameterValue("LOGON_PASSWORD_X1"));
		 this.setLOGON_USERNAME_X1(Stock.GetParameterValue("LOGON_USERNAME_X1"));
		 this.setMESSAGE_MT_PGM_NAME_0(Stock.GetParameterValue("MESSAGE_MT_PGM_NAME_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SELECTION_0_X1="+CONTROL_SELECTION_0_X1+"&MESSAGE_MT_PGM_NAME_0="+MESSAGE_MT_PGM_NAME_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for MSGF VIEW service", this.queryString.replaceAll("&", "\n"), false);
		 
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
				Reporter.logEvent(Status.PASS, "Run  MSGF_View_Messgaes service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run MSGF_View_Messgaes service", "Running Failed:", false);
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
	
		Reporter.logEvent(Status.INFO, "Values From response: ","MESSAGE_PGM_NAME: "+this.MESSAGE_MT_PGM_NAME_0+doc.getElementsByTagName("MESSAGE_PGM_DESCR_0").item(0).getTextContent()+
			//	"\nTRANSACTION_DESCR_0: "+doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent()+
				"\n",false);
	}
	
	public void validateInDatabase() throws SQLException{
		String mt_pgm_name = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToViewMsgs")[1], MESSAGE_MT_PGM_NAME_0);
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database",false);
			mt_pgm_name = queryResultSet.getString("MT_PGM_NAME");
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ID: "+ queryResultSet.getString("ID")+
					"\nPGM_NAME: "+queryResultSet.getString("MT_PGM_NAME")+
					"\nDESCRIPTION: "+queryResultSet.getString("DESCR"), false);
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
		}
		if(MESSAGE_MT_PGM_NAME_0.equalsIgnoreCase(mt_pgm_name)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating MT_PGM_NAME from Input and Database", "Both the values are same as Expected"+
					"\nMT_PGM_NAME: "+"From Input: "+MESSAGE_MT_PGM_NAME_0+"\nFrom Database: "+mt_pgm_name, false);
		}
		
	}
}
