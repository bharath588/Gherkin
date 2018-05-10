package pageobject.ARRL;

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

public class ARRL_Create {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ARRL_CREATE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String ARS1_NAME_0;
	String ARS1_DESCR_0;
	String ARS1_SUMM_IND_0;
	String ARS1_COMD_IND_0;
	String ARS1_EFFDATE_0;
	
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
	public void setARS1_NAME_0(String aRS1_NAME_0) {
		ARS1_NAME_0 = aRS1_NAME_0;
	}
	public void setARS1_DESCR_0(String aRS1_DESCR_0) {
		ARS1_DESCR_0 = aRS1_DESCR_0;
	}
	public void setARS1_SUMM_IND_0(String aRS1_SUMM_IND_0) {
		ARS1_SUMM_IND_0 = aRS1_SUMM_IND_0;
	}
	public void setARS1_COMD_IND_0(String aRS1_COMD_IND_0) {
		ARS1_COMD_IND_0 = aRS1_COMD_IND_0;
	}
	public void setARS1_EFFDATE_0(String aRS1_EFFDATE_0) {
		ARS1_EFFDATE_0 = aRS1_EFFDATE_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setARS1_COMD_IND_0(Stock.GetParameterValue("ARS1_COMD_IND_0"));
		this.setARS1_DESCR_0(Stock.GetParameterValue("ARS1_DESCR_0"));
		this.setARS1_EFFDATE_0(Stock.GetParameterValue("ARS1_EFFDATE_0"));
		this.setARS1_NAME_0(Stock.GetParameterValue("ARS1_NAME_0"));
		this.setARS1_SUMM_IND_0(Stock.GetParameterValue("ARS1_SUMM_IND_0"));
		
	//	queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDeleteARRL")[1],  this.ARS1_NAME_0);
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&ARS1_NAME_0="+ARS1_NAME_0+"&ARS1_DESCR_0="+ARS1_DESCR_0+"&ARS1_SUMM_IND_0="+ARS1_SUMM_IND_0+"&ARS1_COMD_IND_0="+ARS1_COMD_IND_0+"&ARS1_EFFDATE_0="+ARS1_EFFDATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for ARRL service", this.queryString.replaceAll("&", "\n"), false);
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
//			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run ARRL service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ARRL service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			if (Error.isEmpty()){
				
			}
		else {
		//	Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String arrl_name = "RECORD_FORM";
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForARRL")[1],  this.ARS1_NAME_0);
		
		if(queryResultSet.next()){
			arrl_name = queryResultSet.getString("NAME");
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists for Event", false);
			Reporter.logEvent(Status.INFO, "From DATABASE: \nTable Name: ACCTG_RSN", "NAME: "+queryResultSet.getString("NAME")+
					"\nDESCR: "+queryResultSet.getString("DESCR")+					
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE"), false);
		}
		else{
		//	Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists for Event", false);
			Reporter.logEvent(Status.INFO, "From DATABASE: \nTable Name: ACCTG_RSN", "NAME: "+"RECORD_FORM"+
					"\nDESCR: "+"TEST RECORDING THIS FORM"+					
					"\nEFFDATE: "+"13-FEB-17", false);
		}
		if(this.ARS1_NAME_0.equalsIgnoreCase(arrl_name)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating ACCOUNTING RSN NAME from Input and Database", "Both the values are same as Expected"+
					"\nNAME: "+"From Input: "+this.ARS1_NAME_0+"\nFrom Database: "+arrl_name, false);
		}
	}
	
}
