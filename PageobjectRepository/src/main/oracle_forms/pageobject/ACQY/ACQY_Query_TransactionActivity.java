package pageobject.ACQY;

import generallib.General;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;
import oracle.jdbc.pool.OracleDataSource;

import org.w3c.dom.Document;

import core.framework.Globals;

public class ACQY_Query_TransactionActivity {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ACQY";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	
	//input parameters
	String ACCT_QUERY1_EV_ID_0;
	String ACCT_QUERY1_GA_ID_0;
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	
	//output parameter
	String CONTROL_TODAYS_DATE1_0; 
	
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
	public void setACCT_QUERY1_EV_ID_0(String aCCT_QUERY1_EV_ID_0) {
		ACCT_QUERY1_EV_ID_0 = aCCT_QUERY1_EV_ID_0;
	}
	public void setACCT_QUERY1_GA_ID_0(String aCCT_QUERY1_GA_ID_0) {
		ACCT_QUERY1_GA_ID_0 = aCCT_QUERY1_GA_ID_0;
	}
	
	public String getCONTROL_TODAYS_DATE1_0(){
		return CONTROL_TODAYS_DATE1_0;
	}
	
	public void setServiceParameters()
	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setACCT_QUERY1_EV_ID_0(Stock.GetParameterValue("ACCT_QUERY1_EV_ID_0"));
		this.setACCT_QUERY1_GA_ID_0(Stock.GetParameterValue("ACCT_QUERY1_GA_ID_0"));
		
		queryString = "?ACCT_QUERY1_EV_ID_0=" + ACCT_QUERY1_EV_ID_0 + "&ACCT_QUERY1_GA_ID_0=" + ACCT_QUERY1_GA_ID_0 +
				"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 + "&LOGON_DATABASE" + LOGON_DATABASE + 
				"&LOGON_PASSWORD" + LOGON_PASSWORD + "&LOGON_USERNAME" + LOGON_USERNAME +
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		Reporter.logEvent(Status.INFO, "Prepare test data for ACQY service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run ACQY service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ACQY service", "Running Failed:", false);
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
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAccountTransaction")[1], ACCT_QUERY1_EV_ID_0, ACCT_QUERY1_GA_ID_0);
		
		if(queryResultSet.next()){			
			Reporter.logEvent(Status.INFO, "Validating Records exists for Account Transaction", "Records exists for Account Transaction"+"\nCount of number of records: "+queryResultSet.getString("TOTAL_COUNT"), false);
		}
		/*if(queryResultSet.getString("TOTAL_COUNT").equalsIgnoreCase("3")){
			Reporter.logEvent(Status.PASS, "Validating Number of Records \nFrom Database ", "Expected: 3\nActual: "+queryResultSet.getString("TOTAL_COUNT"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Number of Records \nFrom Database ", "Expected: 3\nActual: "+queryResultSet.getString("TOTAL_COUNT"), false);
		}*/
	}
	
}
