package pageobject.AUSS;

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

public class AUSS_QueryMode {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/AUSS_QUERYMODE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CFG_CONTROL_ID_P3_0;
	String CFG_CONTROL_ID_P3_0_X1;
	
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
	public void setCFG_CONTROL_ID_P3_0(String cFG_CONTROL_ID_P3_0) {
		CFG_CONTROL_ID_P3_0 = cFG_CONTROL_ID_P3_0;
	}
	public void setCFG_CONTROL_ID_P3_0_X1(String cFG_CONTROL_ID_P3_0_X1) {
		CFG_CONTROL_ID_P3_0_X1 = cFG_CONTROL_ID_P3_0_X1;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCFG_CONTROL_ID_P3_0(Stock.GetParameterValue("CFG_CONTROL_ID_P3_0"));
		this.setCFG_CONTROL_ID_P3_0_X1(Stock.GetParameterValue("CFG_CONTROL_ID_P3_0_X1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CFG_CONTROL_ID_P3_0="+CFG_CONTROL_ID_P3_0+"&CFG_CONTROL_ID_P3_0_X1="+CFG_CONTROL_ID_P3_0_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for AUSS service", this.queryString.replaceAll("&", "\n"), false);
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
			//System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run AUSS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run AUSS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "CONTROL ID: "+doc.getElementsByTagName("CFG_CONTROL_ID_P5_0").item(0).getTextContent()+
					"\nNAME: "+doc.getElementsByTagName("CFG_CONTROL_LEGAL_NAME_P3_0").item(0).getTextContent()+
					"\nFIRM ID: "+doc.getElementsByTagName("SS1_ASSOC_FIRM_ID_0").item(0).getTextContent()+
					"\nEMAIL: "+doc.getElementsByTagName("SS1_EMAIL_ADDRESS_0").item(0).getTextContent()+
					"\nTAX ID: "+doc.getElementsByTagName("SSCMA1_AGENT_TAX_ID_0").item(0).getTextContent()+
					"\nACCT NUM: "+doc.getElementsByTagName("SSCMA1_ACCT_NBR_0").item(0).getTextContent()+
					"\nBRANCH CODE: "+doc.getElementsByTagName("SSCMA1_BRANCH_CODE_0").item(0).getTextContent(), false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String acct_nbr = doc.getElementsByTagName("SSCMA1_ACCT_NBR_0").item(0).getTextContent();
		String legal_name = doc.getElementsByTagName("CFG_CONTROL_LEGAL_NAME_P3_0").item(0).getTextContent();
		String id = doc.getElementsByTagName("SS1_SAP_BP_ID_0").item(0).getTextContent();
		String legal_name_db = null;
		/*queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAUSS1")[1], legal_name, id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: SS_COMM_ACCT",false);
				while(queryResultSet.next()){
					legal_name_db = queryResultSet.getString("MAILING_NAME_1");
					Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ACCT_NBR: "+ queryResultSet.getString("ACCT_NBR")+
							"\nSEL_SERV_ID: "+queryResultSet.getString("SEL_SERV_ID")+
							"\nBRANCH_CODE: "+queryResultSet.getString("BRANCH_CODE")+
							"\nFIRST LINE MAILING: "+queryResultSet.getString("FIRST_LINE_MAILING")+
							"\nMAILING_NAME: "+queryResultSet.getString("MAILING_NAME_1"), false);
				}
			if(legal_name.equalsIgnoreCase(legal_name_db)){
				Reporter.logEvent(Status.PASS, "Comparing and validating Legal Name value from response and Database","Both the values are same as expected"+
						"\nFrom Response: "+legal_name+"\nFrom Database: "+legal_name_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and validating Legal Name value from response and Database","Both the values are not same"+
						"\nFrom Response: "+legal_name+"\nFrom Database: "+legal_name_db, false);
			}
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
		}*/
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAUSS2")[1], legal_name, id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: SEL_SERV",false);
				while(queryResultSet.next()){
					legal_name_db = queryResultSet.getString("LEGAL_NAME");
					Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ID: "+ queryResultSet.getString("ID")+
							"\nLEGAL_NAME: "+queryResultSet.getString("LEGAL_NAME")+
							"\nFIRST LINE MAILING: "+queryResultSet.getString("FIRST_LINE_MAILING"), false);
				}
			if(legal_name.equalsIgnoreCase(legal_name_db)){
				Reporter.logEvent(Status.PASS, "Comparing and validating Legal Name value from response and Database","Both the values are same as expected"+
						"\nFrom Response: "+legal_name+"\nFrom Database: "+legal_name_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and validating Legal Name value from response and Database","Both the values are not same"+
						"\nFrom Response: "+legal_name+"\nFrom Database: "+legal_name_db, false);
			}
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
		}
	}
}
