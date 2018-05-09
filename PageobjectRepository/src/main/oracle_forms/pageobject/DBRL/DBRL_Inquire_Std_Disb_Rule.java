package pageobject.DBRL;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class DBRL_Inquire_Std_Disb_Rule {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DBRL_INQUIRE_STD_DISB_RULE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
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
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_DATABASE="+LOGON_DATABASE+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for DBRL service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run DBRL service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run DBRL service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		NodeList list = doc.getElementsByTagName("STD_RULE_ID_0");
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			ArrayList<String> RuleId = new ArrayList<String>();
			for(int i = 0; i < list.getLength(); i++){
				 RuleId.add(doc.getElementsByTagName("STD_RULE_ID_0").item(i).getTextContent());
			}
			Reporter.logEvent(Status.INFO, "Values from Response", "RULE_ID: "+RuleId, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		NodeList list = doc.getElementsByTagName("STD_RULE_ID_0");
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDBRL")[1], this.GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record insertion into DB", "Records exists in DB \nTable Name: STD_RULE\nRULE_SEL"+
					"\nTotal no. of records: "+DB.getRecordSetCount(queryResultSet), false);
			while(queryResultSet.next()){				
				Reporter.logEvent(Status.INFO, "Values from DB", "RULE_ID: "+queryResultSet.getString("ID")+
						"\n", false);
			}
			/*if(list.getLength() == DB.getRecordSetCount(queryResultSet)){
				Reporter.logEvent(Status.PASS, "Checking No of Rule IDs matches with no. of RuleID s in Forms", "No of Rule IDs matches with no. of RuleID s in Forms"+
						"\nFrom Response: "+list.getLength()+"\nFrom DB: "+DB.getRecordSetCount(queryResultSet), false);
			}else{
				Reporter.logEvent(Status.FAIL, "Checking No of Rule IDs matches with no. of RuleID s in Forms", "No of Rule IDs doesn't matche with no. of RuleID s in Forms"+
						"\nFrom Response: "+list.getLength()+"\nFrom DB: "+DB.getRecordSetCount(queryResultSet), false);
			}*/
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record insertion into DB", "Records inserted into DB successfully\nTable Name: GA_INVEST_ACTY", false);
		}
	}
	
}