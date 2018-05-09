package pageobject.SGHU;

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

public class SGHU_Rmv_Stmt_Group {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SGHU_Rmv_Stmt_Group_Hold";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GROUP_ACCOUNT_SELECT_IND_0;
	String SELECTION_START_GA_ID_0;
	
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
	public void setGROUP_ACCOUNT_SELECT_IND_0(String gROUP_ACCOUNT_SELECT_IND_0) {
		GROUP_ACCOUNT_SELECT_IND_0 = gROUP_ACCOUNT_SELECT_IND_0;
	}
	public void setSELECTION_START_GA_ID_0(String sELECTION_START_GA_ID_0) {
		SELECTION_START_GA_ID_0 = sELECTION_START_GA_ID_0;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGROUP_ACCOUNT_SELECT_IND_0(Stock.GetParameterValue("GROUP_ACCOUNT_SELECT_IND_0"));
		this.SELECTION_START_GA_ID_0 = dataSetUp();
		
		System.out.println("GA_ID: "+this.SELECTION_START_GA_ID_0);
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GROUP_ACCOUNT_SELECT_IND_0="+GROUP_ACCOUNT_SELECT_IND_0+
				 "&LOGON_DATABASE="+LOGON_DATABASE+
				 "&LOGON_PASSWORD="+LOGON_PASSWORD+
				 "&LOGON_USERNAME="+LOGON_USERNAME+
				 "&SELECTION_START_GA_ID_0="+SELECTION_START_GA_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SGHU service", this.queryString.replaceAll("&", "\n"), false);
		
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

			Reporter.logEvent(Status.PASS, "Run SGHU service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SGHU service", "Running Failed:", false);
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
	
	public void validateInDatabase()throws SQLException{
		String hold_removal_date = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSGHURmv")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
			
			while(queryResultSet.next()){
				hold_removal_date = queryResultSet.getString("HOLD_REMOVAL_DATE");
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: STMT_GROUP_HOLD", "Expected: HOLD REMOVAL DATE should be populated"+
						"\nGA_ID: "+queryResultSet.getString("GA_ID")+
						"\nHOLD_REMOVAL_DATE: "+queryResultSet.getString("HOLD_REMOVAL_DATE")+
						"\nUSER_ID: "+queryResultSet.getString("USER_ID"), false);
			}
			if(hold_removal_date != null){
				Reporter.logEvent(Status.PASS, "Expected: HOLD REMOVAL DATE should be populated", "HOLD REMOVAL DATE: "+hold_removal_date, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Expected: HOLD REMOVAL DATE should be populated", "HOLD REMOVAL DATE: "+hold_removal_date, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
				
	}
	
	
	public void resetData() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSGHURmvReset")[1]);
		
	}
	
	public String dataSetUp() throws SQLException {
		String GA_ID = null;
	/*	queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSGHURmvSelectGaid")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			if(queryResultSet.next()){
				GA_ID = queryResultSet.getString("GA_ID");
			}
		}else{*/
			queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSGHURmvSelectMasterGaid")[1]);
			if(DB.getRecordSetCount(queryResultSet)>0){
				while(queryResultSet.next()){
					String master_gaid = queryResultSet.getString("GAID");
					
					try {
						queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSGHURmvInsertValues")[1], master_gaid);
						GA_ID = master_gaid;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				Reporter.logEvent(Status.FAIL, "Setting up data", "Data is not set up properly", false);
			}
		
		
		return GA_ID;
	}
}
