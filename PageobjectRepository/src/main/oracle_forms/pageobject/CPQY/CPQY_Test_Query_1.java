package pageobject.CPQY;

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

public class CPQY_Test_Query_1 {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CPQY_Test_Query_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
//	private ResultSet queryResultSet2;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String CUCPCR1_ACCT_NBR_0;
	
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
	public void setCUCPCR1_ACCT_NBR_0(String cUCPCR1_ACCT_NBR_0) {
		CUCPCR1_ACCT_NBR_0 = cUCPCR1_ACCT_NBR_0;
	}

	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setCUCPCR1_ACCT_NBR_0(Stock.GetParameterValue("CUCPCR1_ACCT_NBR_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				 "&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&CUCPCR1_ACCT_NBR_0="+CUCPCR1_ACCT_NBR_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for CPQY service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run CPQY_Test_Query_1 service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run CPQY_Test_Query_1 service", "Running Failed:", false);
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
	
		Reporter.logEvent(Status.INFO, "From response: ","Response Messages "+doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent(),false);
	}
	
	public void validateInDatabase()throws SQLException{
		String acct_nbr=null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCommAcct")[1], CUCPCR1_ACCT_NBR_0);
		if(queryResultSet.next()){
			acct_nbr = queryResultSet.getString("ACCT_NBR");
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Records exists in Database", false);
			Reporter.logEvent(Status.PASS, "Validate DAtabase: ", "EV_ID: "+queryResultSet.getString("EV_ID")+
				"\nID: "+queryResultSet.getString("ID")+
				"\nACCT NUM: "+queryResultSet.getString("ACCT_NBR")+
				"\nAMOUNT: "+queryResultSet.getString("COMM_PREM_AMT")+"\n", false);
		}
		else{
			Reporter.logEvent(Status.INFO, "Validating from database: ", "No record found in databse", false);
		}
		if(this.CUCPCR1_ACCT_NBR_0.equalsIgnoreCase(acct_nbr)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Account NUmber from Input and Database", "Both the values are same as Expected"+
					"\nAccount Number: "+"\nFrom Input: "+this.CUCPCR1_ACCT_NBR_0+"\nFrom Database: "+acct_nbr, false);
		}
	}
}
