package pageobject.GLWQ;

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
import org.w3c.dom.NodeList;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class GLWQ_GLWB_Benefit_Base_Inquiry {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GQ19GLWQ_GLWB_Benefit_Base_Inquiry";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CFG_CONTROL_SSN_0;
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
	public void setCFG_CONTROL_SSN_0(String cFG_CONTROL_SSN_0) {
		CFG_CONTROL_SSN_0 = cFG_CONTROL_SSN_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCFG_CONTROL_SSN_0(Stock.GetParameterValue("CFG_CONTROL_SSN_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_DATABASE="+LOGON_DATABASE+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CFG_CONTROL_SSN_0="+CFG_CONTROL_SSN_0+
				"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for GLWQ service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run GLWQ service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run GLWQ service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String ind_id = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGLWQ1")[1], this.CFG_CONTROL_SSN_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record insertion into DB", "Records exists in DB \nTable Name: INDIVIDUAL", false);
			while(queryResultSet.next()){				
				ind_id = queryResultSet.getString("ID");
				Reporter.logEvent(Status.INFO, "Values from DB", "IND_ID: "+queryResultSet.getString("ID")+
						"\n", false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record insertion into DB", "Records doesn't exists in DB\nTable Name: INDIVIDUAL", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGLWQ2")[1], ind_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record insertion into DB", "Records exists in DB \nTable Name: GLWB_INV_ACCT", false);
			while(queryResultSet.next()){	
				Reporter.logEvent(Status.INFO, "Values from DB", "ID: "+queryResultSet.getString("ID")+
						"\nGA_ID: "+queryResultSet.getString("GA_ID")+
						"\nSDIO_ID: "+queryResultSet.getString("SDIO_ID")+
						"\nSTATUS_CODE: "+queryResultSet.getString("STATUS_CODE")+
						"\nSTATUS_EFFDATE: "+queryResultSet.getString("STATUS_EFFDATE")+
						"\n", false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record insertion into DB", "Records doesn't exists in DB\nTable Name: GLWB_INV_ACCT", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGLWQ3")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record insertion into DB", "Records exists in DB \nTable Name: GLWB_BENEBASE_BAL", false);
			while(queryResultSet.next()){	
				Reporter.logEvent(Status.INFO, "Values from DB", "ID: "+queryResultSet.getString("GIVA_ID")+
						"\nEV_ID: "+queryResultSet.getString("EV_ID")+
						"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME")+
						"\nAMT: "+queryResultSet.getString("AMT")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\n", false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record insertion into DB", "Records doesn't exists in DB\nTable NAme: GLWB_BENEBASE_BAL", false);
		}
	}
}