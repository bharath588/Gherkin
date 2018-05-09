package pageobject.DPQY;

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

public class DPQY_Test_Query {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/QYDPQY_Test_Query";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GROUP_HEADER1_GA_ID_0;
	String DOC_VIEW1_SSN_0;
	
	public String getDOC_VIEW1_SSN_0() {
		return DOC_VIEW1_SSN_0;
	}
	public void setDOC_VIEW1_SSN_0(String dOC_VIEW1_SSN_0) {
		DOC_VIEW1_SSN_0 = dOC_VIEW1_SSN_0;
	}
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
	public void setGROUP_HEADER1_GA_ID_0(String gROUP_HEADER1_GA_ID_0) {
		GROUP_HEADER1_GA_ID_0 = gROUP_HEADER1_GA_ID_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setDOC_VIEW1_SSN_0(Stock.GetParameterValue("DOC_VIEW1_SSN_0"));
		this.setGROUP_HEADER1_GA_ID_0(Stock.GetParameterValue("GROUP_HEADER1_GA_ID_0"));
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&DOC_VIEW1_SSN_0="+DOC_VIEW1_SSN_0+"&GROUP_HEADER1_GA_ID_0="+GROUP_HEADER1_GA_ID_0+
				"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for QYDPQY service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run DPQY service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DPQY service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
	
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			
			Reporter.logEvent(Status.INFO, "From Response: ", 
					"DOC_cc01: "+ doc.getElementsByTagName("DMDA1_CC01_0").item(0).getTextContent()+
					"\nPC CODE: "+ doc.getElementsByTagName("DMDA1_PC_CODE_0").item(0).getTextContent()+
					"\nPROD ID: "+ doc.getElementsByTagName("DMDA1_PROD_ID_0").item(0).getTextContent()+
					"\nSJTY CODE: "+ doc.getElementsByTagName("DMRVDT1_SJTY_CODE_0").item(0).getTextContent()+
					"\nDOC_GA_ID: "+ doc.getElementsByTagName("DOCUMENT_GA_ID_0").item(0).getTextContent()+
					"\nDOC_ID: "+ doc.getElementsByTagName("DOCUMENT_ID_0").item(0).getTextContent()+
					"\nDOC_IND_ID: "+ doc.getElementsByTagName("DOCUMENT_IND_ID_0").item(0).getTextContent()
					, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String first_name = null;
		String FIRST_NAME = doc.getElementsByTagName("DOCUMENT_MAILING_NAME_1_0").item(0).getTextContent();
		
		String dmty_code = null;
		String DMTY_CODE = doc.getElementsByTagName("DOCUMENT_DMTY_CODE_0").item(0).getTextContent();
		
		String sjty_code = null;
		String SJTY_CODE = doc.getElementsByTagName("DOC_VIEW1_SJTY_CODE_0").item(0).getTextContent();
		
		String DOC_CC01 = doc.getElementsByTagName("DMDA1_CC01_0").item(0).getTextContent();
		String doc_cc01 = null;
		
		String doc_ind_id = doc.getElementsByTagName("DOCUMENT_IND_ID_0").item(0).getTextContent();
		String doc_id = doc.getElementsByTagName("DOCUMENT_ID_0").item(0).getTextContent();;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDPQY1")[1], this.DOC_VIEW1_SSN_0);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Account Transaction\nTable Name: INDIVIDUAL", false);
			first_name = queryResultSet.getString("MAILING_NAME_1");
			Reporter.logEvent(Status.INFO, "Values From Database: ", 					
					"SSN: "+queryResultSet.getString("SSN")+					
					"\nMAILING NAME: "+queryResultSet.getString("MAILING_NAME_1")+
					"\nID: "+queryResultSet.getString("ID")
					, false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database Table: INDIVIDUAL", false);
		}
		if(FIRST_NAME.equalsIgnoreCase(first_name)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating MAILING NAME from Response and Database", "Both the values are same as Expected"+
					"\nFrom Response: "+FIRST_NAME+"\nFrom Database: "+first_name, false);
		}		
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDPQY2")[1], doc_ind_id, doc_id);		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Account Transaction\nTable Name: DOCUMENT", false);
			dmty_code = queryResultSet.getString("DMTY_CODE");
			Reporter.logEvent(Status.INFO, "Values From Database: ", 					
					"DMTY_CODE: "+queryResultSet.getString("DMTY_CODE")+					
					"\nDOC_dc29: "+queryResultSet.getString("DC29")+
					"\nID: "+queryResultSet.getString("ID")+
					"\nIND_ID: "+queryResultSet.getString("IND_ID")+
					"\nGA_ID: "+queryResultSet.getString("GA_ID")
					, false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database Table: DOCUMENT", false);
		}
		if(DMTY_CODE.equalsIgnoreCase(dmty_code)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating DMTY_CODE from Response and Database", "Both the values are same as Expected"+
					"\nFrom Response: "+DMTY_CODE+"\nFrom Database: "+dmty_code, false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDPQY3")[1],  doc_id, doc_ind_id);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Account Transaction\nTable Name: DOC_RECVR_DEST", false);
			sjty_code = queryResultSet.getString("SJTY_CODE");
			Reporter.logEvent(Status.INFO, "Values From Database: ","\nDOC_ID: "+queryResultSet.getString("DOC_ID")+ 					
					"\nSJTY_CODE: "+queryResultSet.getString("SJTY_CODE")+					
					"\nUSER_LOGON_ID: "+queryResultSet.getString("USER_LOGON_ID")+					
					"\nIND_ID: "+queryResultSet.getString("IND_ID")+
					"\nWKUN_SHORT_NAME: "+queryResultSet.getString("WKUN_SHORT_NAME")
					, false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database Table: DOC_RECVR_DEST", false);
		}
		if(SJTY_CODE.equalsIgnoreCase(sjty_code)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating SJTY_CODE from Response and Database", "Both the values are same as Expected"+
					"\nFrom Response: "+SJTY_CODE+"\nFrom Database: "+sjty_code, false);
		}
		
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDPQY4")[1],  doc_id, DOC_CC01);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Account Transaction\nTable Name: DOC_DETL", false);
			doc_cc01 = queryResultSet.getString("CC01");
			Reporter.logEvent(Status.INFO, "Values From Database: ","\nDOC_ID: "+queryResultSet.getString("DOC_ID")+ 					
					"\nDMCP ANME: "+queryResultSet.getString("DMCP_NAME")+					
					"\nCC01: "+queryResultSet.getString("CC01")
					, false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database Table: DOC_DETL", false);
		}
		if(DOC_CC01.equalsIgnoreCase(doc_cc01)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating CC01 from Response and Database", "Both the values are same as Expected"+
					"\nFrom Response: "+DOC_CC01+"\nFrom Database: "+doc_cc01, false);
		}
		
	}
}
