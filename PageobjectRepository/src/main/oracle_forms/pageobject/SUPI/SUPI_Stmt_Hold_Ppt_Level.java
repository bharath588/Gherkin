package pageobject.SUPI;

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

public class SUPI_Stmt_Hold_Ppt_Level {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SUPI_Stmt_Hold_Ppt_Level";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SELECTION_GA_ID_0;
	String SELECTION_SSN_0;
	String GROUP_ACCOUNT_SELECT_IND_0;
	String param9144;
	String param9145;
	
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
	public void setSELECTION_GA_ID_0(String sELECTION_GA_ID_0) {
		SELECTION_GA_ID_0 = sELECTION_GA_ID_0;
	}
	public void setSELECTION_SSN_0(String sELECTION_SSN_0) {
		SELECTION_SSN_0 = sELECTION_SSN_0;
	}
	public void setGROUP_ACCOUNT_SELECT_IND_0(String gROUP_ACCOUNT_SELECT_IND_0) {
		GROUP_ACCOUNT_SELECT_IND_0 = gROUP_ACCOUNT_SELECT_IND_0;
	}
	public void setParam9144(String param9144) {
		this.param9144 = param9144;
	}
	public void setParam9145(String param9145) {
		this.param9145 = param9145;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGROUP_ACCOUNT_SELECT_IND_0(Stock.GetParameterValue("GROUP_ACCOUNT_SELECT_IND_0"));
		this.setParam9144(Stock.GetParameterValue("param9144"));
		this.setParam9145(Stock.GetParameterValue("param9145"));
		this.setSELECTION_GA_ID_0(Stock.GetParameterValue("SELECTION_GA_ID_0"));
		this.setSELECTION_SSN_0(Stock.GetParameterValue("SELECTION_SSN_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SELECTION_GA_ID_0="+SELECTION_GA_ID_0+"&SELECTION_SSN_0="+SELECTION_SSN_0+"&GROUP_ACCOUNT_SELECT_IND_0="+GROUP_ACCOUNT_SELECT_IND_0+"&param9144="+param9144+"&param9145="+param9145+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for SUPI service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() throws SQLException {
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
			Reporter.logEvent(Status.PASS, "Run SUPI service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run SUPI service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "GA_ID: "+ doc.getElementsByTagName("GROUP_ACCOUNT_GA_ID_0").item(0).getTextContent()+
					"\nSSN: "+doc.getElementsByTagName("GROUP_ACCOUNT_SSN_0").item(0).getTextContent()+
					"\nGA STMT REASON CODE: "+doc.getElementsByTagName("GROUP_ACCOUNT_STMT_HOLD_REASON_CODE_0").item(0).getTextContent()+
					"\nGA PROC CODE: "+doc.getElementsByTagName("GROUP_ACCOUNT_STMT_HOLD_REASON_CODE_0").item(0).getTextContent()+
					"\nREASON DESC: "+doc.getElementsByTagName("GROUP_INFO_HOLD_REASON_DESC_0").item(0).getTextContent()+
					"\nGRP INFO STMT REASON CODE: "+doc.getElementsByTagName("GROUP_INFO_STMT_HOLD_REASON_CODE_0").item(0).getTextContent()+
					"\nGRP INFO CODE: "+doc.getElementsByTagName("GROUP_INFO_STMT_PROC_CODE_0").item(0).getTextContent()+
					"\nGRP INFO PROC DESC: "+doc.getElementsByTagName("GROUP_INFO_STMT_PROC_DESC_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String stmt_proc_code = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSUPI")[1], this.SELECTION_GA_ID_0, this.SELECTION_SSN_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
			while(queryResultSet.next()){
				stmt_proc_code = queryResultSet.getString("STMT_PROC_CODE");
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: PART_AGRMT\nINDIVIDUAL\nExpected: Participant stmt proc code is set to HOLD", "SSN: "+queryResultSet.getString("SSN")+
					"\nIND_ID: "+queryResultSet.getString("IND_ID")+
					"\nGA_ID: "+queryResultSet.getString("GA_ID")+
					"\nSTMT_PROC_CODE: "+queryResultSet.getString("STMT_PROC_CODE"), false);
			}
			if(stmt_proc_code.equalsIgnoreCase("hold")){
				Reporter.logEvent(Status.PASS, "Expected: Participant stmt proc code is set to HOLD","STMT_PROC_CODE from database: "+stmt_proc_code,false); 
			}
			else{
				Reporter.logEvent(Status.FAIL, "Expected: Participant stmt proc code is set to HOLD","STMT_PROC_CODE from database: "+stmt_proc_code,false);
			}
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
}
