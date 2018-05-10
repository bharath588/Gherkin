package pageobject.FDIN;

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

public class FDIN_Insert {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/FDIN_INSERT";	
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String CONTROL_DMTY_CODE_0;
	String INSERT_DFAP_GA_ID_0;
	
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
	public void setCONTROL_DMTY_CODE_0(String cONTROL_DMTY_CODE_0) {
		CONTROL_DMTY_CODE_0 = cONTROL_DMTY_CODE_0;
	}
	public void setINSERT_DFAP_GA_ID_0(String iNSERT_DFAP_GA_ID_0) {
		INSERT_DFAP_GA_ID_0 = iNSERT_DFAP_GA_ID_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setCONTROL_DMTY_CODE_0(Stock.GetParameterValue("CONTROL_DMTY_CODE_0"));
		 this.setINSERT_DFAP_GA_ID_0(Stock.GetParameterValue("INSERT_DFAP_GA_ID_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_DMTY_CODE_0="+CONTROL_DMTY_CODE_0+"&INSERT_DFAP_GA_ID_0="+INSERT_DFAP_GA_ID_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for FDIN service", this.queryString.replaceAll("&", "\n"), false);
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
		//	System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run FDIN service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run FDIN service", "Running Failed:", false);
		}
	}
	
	public void validateOutput(){
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
//			Reporter.logEvent(Status.PASS, "Capturing Response after Execution", "Response:\n"+doc.getDocumentElement(), false);
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From Response of Service ","DMTY_CODE: "+doc.getElementsByTagName("DOL_FEE_ACTY_PERD_DMTY_CODE_0").item(0).getTextContent()+
					"\nDESCR: "+doc.getElementsByTagName("CONTROL_DESCR_0").item(0).getTextContent()+
					"\nEFF DATE: "+doc.getElementsByTagName("DOL_FEE_ACTY_PERD_EFFDATE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		
	}
	
	public void validateInDatabase() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToVerifyFDIN")[1], INSERT_DFAP_GA_ID_0, CONTROL_DMTY_CODE_0);
		String dmty_code = null;
		if(queryResultSet.next())
		{
			dmty_code = queryResultSet.getString("DMTY_CODE");
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Records exists in Database", false);
			Reporter.logEvent(Status.INFO, "From DATABASE:" , "GA_ID: " +queryResultSet.getString("GA_ID")+
					"\nDMTY_CODE: " +queryResultSet.getString("DMTY_CODE")+
					"\nDH_CODE: " +queryResultSet.getString("DH_CODE")+
					"\nEFFDATE: " +queryResultSet.getString("EFFDATE")+
					"\nRUN_TYPE_CODE: " +queryResultSet.getString("RUN_TYPE_CODE")+
					"\nSTART DATE: " +queryResultSet.getString("SCHEDULE_START_DATE"), false);
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(this.CONTROL_DMTY_CODE_0.equalsIgnoreCase(dmty_code)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating DMTY CODE from Input and Database", "Both the values are same as Expected"+
					"\nDMTY_CODE: "+"From Input: "+this.CONTROL_DMTY_CODE_0+"\nFrom Database: "+dmty_code, false);
		}
	}

	

}
