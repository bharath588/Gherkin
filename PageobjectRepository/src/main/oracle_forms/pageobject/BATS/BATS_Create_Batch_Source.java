package pageobject.BATS;

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

public class BATS_Create_Batch_Source {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/BATS_Create_Batch_Source";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String BHSX1_ID_0;
	String BHSX1_BANK_ACCT_LOV;
	String BHSX1_CASH_IND_0;
	String param990;
	String BHSX1_FIRST_LINE_MAILING_0;
	String BHSX1_CITY_0;
	String BHSX1_STATE_CODE_0;
	String BHSX1_COUNTRY_0;
	String COUNTRY_LOV;
	String BHSX1_ZIP_CODE_0;
	
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
	public void setBHSX1_ID_0(String bHSX1_ID_0) {
		BHSX1_ID_0 = bHSX1_ID_0;
	}
	public void setBHSX1_BANK_ACCT_LOV(String bHSX1_BANK_ACCT_LOV) {
		BHSX1_BANK_ACCT_LOV = bHSX1_BANK_ACCT_LOV;
	}
	public void setBHSX1_CASH_IND_0(String bHSX1_CASH_IND_0) {
		BHSX1_CASH_IND_0 = bHSX1_CASH_IND_0;
	}
	public void setParam990(String param990) {
		this.param990 = param990;
	}
	public void setBHSX1_FIRST_LINE_MAILING_0(String bHSX1_FIRST_LINE_MAILING_0) {
		BHSX1_FIRST_LINE_MAILING_0 = bHSX1_FIRST_LINE_MAILING_0;
	}
	public void setBHSX1_CITY_0(String bHSX1_CITY_0) {
		BHSX1_CITY_0 = bHSX1_CITY_0;
	}
	public void setBHSX1_STATE_CODE_0(String bHSX1_STATE_CODE_0) {
		BHSX1_STATE_CODE_0 = bHSX1_STATE_CODE_0;
	}
	public void setBHSX1_COUNTRY_0(String bHSX1_COUNTRY_0) {
		BHSX1_COUNTRY_0 = bHSX1_COUNTRY_0;
	}
	public void setCOUNTRY_LOV(String cOUNTRY_LOV) {
		COUNTRY_LOV = cOUNTRY_LOV;
	}
	public void setBHSX1_ZIP_CODE_0(String bHSX1_ZIP_CODE_0) {
		BHSX1_ZIP_CODE_0 = bHSX1_ZIP_CODE_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));		
		this.setBHSX1_BANK_ACCT_LOV(Stock.GetParameterValue("BHSX1_BANK_ACCT_LOV"));
		this.setBHSX1_CASH_IND_0(Stock.GetParameterValue("BHSX1_CASH_IND_0"));
		this.setParam990(Stock.GetParameterValue("param990"));
		this.setBHSX1_CITY_0(Stock.GetParameterValue("BHSX1_CITY_0"));
		this.setBHSX1_COUNTRY_0(Stock.GetParameterValue("BHSX1_COUNTRY_0"));
		this.setBHSX1_FIRST_LINE_MAILING_0(Stock.GetParameterValue("BHSX1_FIRST_LINE_MAILING_0"));
		this.setBHSX1_ID_0(Stock.GetParameterValue("BHSX1_ID_0"));
		this.setBHSX1_STATE_CODE_0(Stock.GetParameterValue("BHSX1_STATE_CODE_0"));
		this.setBHSX1_ZIP_CODE_0(Stock.GetParameterValue("BHSX1_ZIP_CODE_0"));
		this.setCOUNTRY_LOV(Stock.GetParameterValue("COUNTRY_LOV"));
		
		
	/*	queryString = "?BHSX1_BANK_ACCT_LOV="+BHSX1_BANK_ACCT_LOV+
				"&BHSX1_CASH_IND_0="+BHSX1_CASH_IND_0+
				"&BHSX1_CITY_0="+BHSX1_CITY_0+
				"&BHSX1_COUNTRY_0="+BHSX1_COUNTRY_0+
				"&BHSX1_FIRST_LINE_MAILING_0="+BHSX1_FIRST_LINE_MAILING_0+
				"&BHSX1_ID_0="+BHSX1_ID_0+
				"&BHSX1_STATE_CODE_0="+BHSX1_STATE_CODE_0+
				"&BHSX1_ZIP_CODE_0="+BHSX1_ZIP_CODE_0+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&COUNTRY_LOV="+COUNTRY_LOV+
				"&LOGON_DATABASE="+LOGON_DATABASE+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_USERNAME="+LOGON_USERNAME+
				"&param990="+param990+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		*/
		
		queryString = "?BHSX1_BANK_ACCT_LOV="+BHSX1_BANK_ACCT_LOV+
				"&BHSX1_CASH_IND_0="+BHSX1_CASH_IND_0+
				"&BHSX1_CITY_0="+BHSX1_CITY_0+
				"&BHSX1_COUNTRY_0="+BHSX1_COUNTRY_0+
				"&BHSX1_FIRST_LINE_MAILING_0="+BHSX1_FIRST_LINE_MAILING_0+
				"&BHSX1_ID_0="+BHSX1_ID_0+
				"&BHSX1_STATE_CODE_0="+BHSX1_STATE_CODE_0+
				"&BHSX1_ZIP_CODE_0="+BHSX1_ZIP_CODE_0+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&COUNTRY_LOV="+COUNTRY_LOV+
				"&LOGON_DATABASE="+LOGON_DATABASE+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_USERNAME="+LOGON_USERNAME+
				"&param990="+param990+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for BATS service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run BATS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run BATS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
	
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response: ", 
					"\nBKA_CODE: "+ doc.getElementsByTagName("BHSX1_BKA_CODE_0").item(0).getTextContent()+
					"\nBKA_TYPE_CODE: "+ doc.getElementsByTagName("BHSX1_BKA_TYPE_CODE_0").item(0).getTextContent()+
					"\nCOUNTRY: "+ doc.getElementsByTagName("BHSX1_COUNTRY_DESCR_0").item(0).getTextContent()+
					"\nPROV_COMP: "+ doc.getElementsByTagName("BHSX1_PROV_COMPANY_0").item(0).getTextContent()
					, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String bka_code_db = null;
		String bka_code_resp = doc.getElementsByTagName("BHSX1_BKA_CODE_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForBATS")[1], this.BHSX1_ID_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable Name: BATCH_SOURCE", false);
			
			
			while(queryResultSet.next()){
				bka_code_db = queryResultSet.getString("BKA_CODE");
				Reporter.logEvent(Status.INFO, "Values From Database: ", 					
					"ID: "+queryResultSet.getString("ID")+					
					"\nBKA_CODE: "+queryResultSet.getString("BKA_CODE")+
					"\nBKA TYPE CODE: "+queryResultSet.getString("BKA_TYPE_CODE")+
					"\nROV COMPANY: "+queryResultSet.getString("PROV_COMPANY")
					, false);
			}
			if(bka_code_db.equalsIgnoreCase(bka_code_resp)){
				Reporter.logEvent(Status.PASS, "Comparing and Validating BKA CODE from Response and Database", "Both the values are same as Expected"+
						"\nFrom Response: "+bka_code_resp+"\nFrom Database: "+bka_code_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating BKA CODE from Response and Database", "Both the values are not same as Expected"+
						"\nFrom Response: "+bka_code_resp+"\nFrom Database: "+bka_code_db, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
	public void resetData()throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForBATSReset")[1], this.BHSX1_ID_0);
	}
}