package pageobject.TARS;

import generallib.General;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class TARS_Query_All_Assr_Code {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/TARS_Query_All_Assr_Codes";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String param27077;
	
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
	public void setParam27077(String param27077) {
		this.param27077 = param27077;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setParam27077(Stock.GetParameterValue("param27077"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+				
				"&param27077="+param27077+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for TARS service", this.queryString.replaceAll("&", "\n"), false);
	
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			URL obj = new URL(serviceURL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			

			//print result
			System.out.println(response.toString());
			con.getContent();
			PrintWriter pw = new PrintWriter("response.txt");
			pw.println(response.toString().replaceAll("<></>", ""));
			pw.close();

			 BufferedReader br = new BufferedReader(new FileReader("response.txt"));
			 String line;
			 while((line = br.readLine()) != null)
			 {
			     System.out.println(line);
			 }
			 br.close();
			 
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File("response.txt"));
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run TARS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run TARS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){	
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response: ", "TAX_CODE: "+doc.getElementsByTagName("TAX_ASSR_RPT_SCHED_TXAE_CODE_0").item(0).getTextContent()+
					"\nTRANSACTION_CODE: "+doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent()+
					"\nTRANSACTION_DESCR: "+doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent(), false);
		}	
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTARSAll1")[1]);		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists in Database\nTable Name: TAX_ASSR"+
		"Total number of records: "+DB.getRecordSetCount(queryResultSet), false);			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTARSAll2")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists in Database\nTable Name: TAX_ASSR_ADDNL_CRIT"+
		"Total number of records: "+DB.getRecordSetCount(queryResultSet), false);						
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTARSAll3")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists in Database\nTable Name: TAX_ASSR_DEFAULT_CRITERIA"+
					"Total number of records: "+DB.getRecordSetCount(queryResultSet), false);						
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTARSAll4")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists in Database\nTable Name: TAX_ASSR_RPT_SCHED"+
					"Total number of records: "+DB.getRecordSetCount(queryResultSet), false);						
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTARSAll5")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists in Database\nTable Name: TAX_ASSR_SELECTION_RULE"+
					"Total number of records: "+DB.getRecordSetCount(queryResultSet), false);						
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
}
