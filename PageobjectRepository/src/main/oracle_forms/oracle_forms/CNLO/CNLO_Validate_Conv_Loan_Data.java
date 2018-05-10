package pageobject.CNLO;

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

public class CNLO_Validate_Conv_Loan_Data {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CNLO_Validate_Conversion_Loan_data";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0;
	String param9171;
	String MO1_DISP_DFT_PRIN_CODE_0;
	
	public String getParam9171() {
		return param9171;
	}
	public void setParam9171(String param9171) {
		this.param9171 = param9171;
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
	public void setCONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0(
			String cONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0) {
		CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0 = cONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0;
	}
	public void setMO1_DISP_DFT_PRIN_CODE_0(String mO1_DISP_DFT_PRIN_CODE_0) {
		MO1_DISP_DFT_PRIN_CODE_0 = mO1_DISP_DFT_PRIN_CODE_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0(Stock.GetParameterValue("CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0"));
		this.setMO1_DISP_DFT_PRIN_CODE_0(Stock.GetParameterValue("MO1_DISP_DFT_PRIN_CODE_0"));
		this.setParam9171(Stock.GetParameterValue("param9171"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0="+CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0+
				"&param9171="+param9171+"&MO1_DISP_DFT_PRIN_CODE_0="+MO1_DISP_DFT_PRIN_CODE_0
				+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for CNLO service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run CNLO service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CNLO service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
	
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From Response: ", "EV_ID: "+ doc.getElementsByTagName("CONVERSION_GROUP_ACCOUNT_EV_ID_0").item(0).getTextContent()+
					"\nPLAN NAME: "+ doc.getElementsByTagName("CONVERSION_GROUP_ACCOUNT_PLAN_NAME_0").item(0).getTextContent()+
					"\nHEADER: "+ doc.getElementsByTagName("NARRATIVE_HEADER_0").item(0).getTextContent()+
					"\nDISP_DFT_VALUE: "+ doc.getElementsByTagName("INP1_DISP_DFT_VALUE_0").item(0).getTextContent()+
					"\nSSN: "+ doc.getElementsByTagName("WORK_CONV_PART_LOAN_ACCT_SSN_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String ev_id_db = null;
		String event_id = doc.getElementsByTagName("CONVERSION_GROUP_ACCOUNT_EV_ID_0").item(0).getTextContent();
		String ssn = doc.getElementsByTagName("WORK_CONV_PART_LOAN_ACCT_SSN_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCNLO")[1],  event_id, ssn );
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.INFO, "Validating Records exists for Account Transaction", "Records exists for Account Transaction\nTable Name: WORK_CONV_PART and WORK_CONV_PART_LOAN_ACCT", false);
			ev_id_db = queryResultSet.getString("EV_ID");
			Reporter.logEvent(Status.INFO, "Values From Database: ", 					
					"EV_ID: "+queryResultSet.getString("EV_ID")+					
					"\nSSN: "+queryResultSet.getString("SSN")+
					"\nID: "+queryResultSet.getString("ID")+
					"\nLOAN_AMT: "+queryResultSet.getString("LOAN_AMT")
					, false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(event_id.equalsIgnoreCase(ev_id_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating EVENT ID from Response and Database", "Both the values are same as Expected"+
					"\nFrom Response: "+event_id+"\nFrom Database: "+ev_id_db, false);
		}
	}
}
