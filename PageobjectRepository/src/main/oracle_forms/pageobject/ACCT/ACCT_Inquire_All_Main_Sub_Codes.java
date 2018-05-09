package pageobject.ACCT;

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

public class ACCT_Inquire_All_Main_Sub_Codes {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ACCT_Inquire_All_Main_Sub_Codes";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	
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
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
	
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for ACCT INQUIRE ALL CODES service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run  ACCT INQUIRE ALL CODES service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run  ACCT INQUIRE ALL CODES service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From response: ","EFFDATE: "+doc.getElementsByTagName("VALID_ACCTG_COMBO_EFFDATE_0").item(0).getTextContent()+
					"\nMAIN_CODE: "+doc.getElementsByTagName("VALID_ACCTG_COMBO_MAIN_CODE_0").item(0).getTextContent()+
					"\n:PC_CODE "+doc.getElementsByTagName("VALID_ACCTG_COMBO_PC_CODE_0").item(0).getTextContent()+
					"\nSUBCODE: "+doc.getElementsByTagName("VALID_ACCTG_COMBO_SUB_CODE_0").item(0).getTextContent()+
					"\nTERMDATE: "+doc.getElementsByTagName("VALID_ACCTG_COMBO_TERMDATE_0").item(0).getTextContent()+		
					"\n",false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase()throws SQLException{
		String Effdate = doc.getElementsByTagName("VALID_ACCTG_COMBO_EFFDATE_0").item(0).getTextContent();
		String Maincode = doc.getElementsByTagName("VALID_ACCTG_COMBO_MAIN_CODE_0").item(0).getTextContent(); 
		String PcCode = doc.getElementsByTagName("VALID_ACCTG_COMBO_PC_CODE_0").item(0).getTextContent();
		String Subcode = doc.getElementsByTagName("VALID_ACCTG_COMBO_SUB_CODE_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForACCTAll")[1], Maincode, PcCode, Subcode, Effdate);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable Name: VALID_ACCTG_COMBO", false);
			Reporter.logEvent(Status.INFO, "Values From Database: ","EFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nMAIN_CODE: "+queryResultSet.getString("MAIN_CODE")+
					"\nSUB_CODE: "+queryResultSet.getString("SUB_CODE")+
					"\nPC_CODE: "+queryResultSet.getString("PC_CODE")+
					"\nTERMDATE: "+queryResultSet.getString("TERMDATE"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(Maincode.equalsIgnoreCase(queryResultSet.getString("MAIN_CODE"))&&
				Subcode.equalsIgnoreCase(queryResultSet.getString("SUB_CODE"))&&
				PcCode.equalsIgnoreCase(queryResultSet.getString("PC_CODE"))){
					Reporter.logEvent(Status.PASS, "Comparing Values From Response and Database", "Values are same in Response and Database"+
				"\nMAIN_CODE: "+"\nFrom Response: "+Maincode+"\nFrom Database: "+queryResultSet.getString("MAIN_CODE")+
				"\nSUB_CODE: "+"\nFrom Response: "+Subcode+"\nFrom Database: "+queryResultSet.getString("SUB_CODE")+
				"\nPC_CODE: "+"\nFrom Response: "+PcCode+"\nFrom Database: "+queryResultSet.getString("PC_CODE"), false);
		}
	}
}
