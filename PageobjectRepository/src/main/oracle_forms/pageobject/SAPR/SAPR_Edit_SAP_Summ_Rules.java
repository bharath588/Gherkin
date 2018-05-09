package pageobject.SAPR;

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

public class SAPR_Edit_SAP_Summ_Rules {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SAPR_Edit_SAP_Summarization_Rules";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SAP_MAIN_CODE_0;
	String SAP_MAIN_SUB_EFFDATE_0;
	String SAP_MAIN_SUB_EFFDATE_0_X1;
	
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
	public void setSAP_MAIN_CODE_0(String sAP_MAIN_CODE_0) {
		SAP_MAIN_CODE_0 = sAP_MAIN_CODE_0;
	}
	public void setSAP_MAIN_SUB_EFFDATE_0(String sAP_MAIN_SUB_EFFDATE_0) {
		SAP_MAIN_SUB_EFFDATE_0 = sAP_MAIN_SUB_EFFDATE_0;
	}
	public void setSAP_MAIN_SUB_EFFDATE_0_X1(String sAP_MAIN_SUB_EFFDATE_0_X1) {
		SAP_MAIN_SUB_EFFDATE_0_X1 = sAP_MAIN_SUB_EFFDATE_0_X1;
	}
	
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));		
		this.setSAP_MAIN_CODE_0(Stock.GetParameterValue("SAP_MAIN_CODE_0"));
		this.setSAP_MAIN_SUB_EFFDATE_0(Stock.GetParameterValue("SAP_MAIN_SUB_EFFDATE_0"));
		this.setSAP_MAIN_SUB_EFFDATE_0_X1(Stock.GetParameterValue("SAP_MAIN_SUB_EFFDATE_0_X1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SAP_MAIN_CODE_0="+SAP_MAIN_CODE_0+"&SAP_MAIN_SUB_EFFDATE_0="+SAP_MAIN_SUB_EFFDATE_0+"&SAP_MAIN_SUB_EFFDATE_0_X1="+SAP_MAIN_SUB_EFFDATE_0_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for SAPR service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run STRT service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STRT service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "SAP_MAIN_DESCR: "+doc.getElementsByTagName("SAP_MAIN_DESCR_0").item(0).getTextContent()+
					"\nACCT_CODE: "+doc.getElementsByTagName("SAP_MAIN_SUB_GL_ACCT_CODE_0").item(0).getTextContent()+
					"\nLDGR_CODE: "+doc.getElementsByTagName("SAP_MAIN_SUB_LDGR_CODE_0").item(0).getTextContent()+
					"\nMAIN_CODE: "+doc.getElementsByTagName("SAP_MAIN_SUB_MAIN_CODE_0").item(0).getTextContent()+
					"\nSUB_CODE: "+doc.getElementsByTagName("SAP_SUB_CODE_0").item(0).getTextContent(), false);			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String main_code = doc.getElementsByTagName("SAP_MAIN_SUB_MAIN_CODE_0").item(0).getTextContent();
		String sub_code = doc.getElementsByTagName("SAP_SUB_CODE_0").item(0).getTextContent();
		String ldgr_code = doc.getElementsByTagName("SAP_MAIN_SUB_LDGR_CODE_0").item(0).getTextContent();
		String acct_code = doc.getElementsByTagName("SAP_MAIN_SUB_GL_ACCT_CODE_0").item(0).getTextContent();
		
		String sub_db =null;
		String ldgr_db = null;
		String acct_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSAPR1")[1], main_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: SAP_MAIN_SUB",false);
			while(queryResultSet.next()){
				sub_db = queryResultSet.getString("SUB_CODE");
				ldgr_db = queryResultSet.getString("LDGR_CODE");
				acct_db = queryResultSet.getString("GL_ACCT_CODE");
				Reporter.logEvent(Status.INFO, "Values from Database", "MAIN_CODE: "+queryResultSet.getString("MAIN_CODE")+
						"\nSUB_CODE: "+queryResultSet.getString("SUB_CODE")+
						"\nLDGR_CODE: "+queryResultSet.getString("LDGR_CODE")+
						"\nACCT_CODE: "+queryResultSet.getString("GL_ACCT_CODE"), false);	
			}
			if((ldgr_db.equalsIgnoreCase(ldgr_code)) && (acct_db.equalsIgnoreCase(acct_code))){
				Reporter.logEvent(Status.PASS, "Comparing and Validating values from Response and Database", "Both the values are same as expected", false);						
			}
			else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating values from Response and Database", "Both the values are not same", false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSAPR2")[1], main_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: SAP_MAIN",false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "MAIN_CODE: "+queryResultSet.getString("CODE")+
						"\nDESCR: "+queryResultSet.getString("DESCR"), false);	
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSAPR3")[1], sub_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: SAP_SUB",false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "CODE: "+queryResultSet.getString("CODE"), false);	
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}
	}
}
