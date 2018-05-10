package pageobject.CINQ;

import generallib.General;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

public class CINQ_Add_Group_Accounts {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CINQ_Add_Group_Accounts";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String param989;
	String param989_X1;
	String CHECK_INQ_BLK_GA_ID_0;
	String CHECK_INQ_BLK_MAILING_NAME_0;
	String CHECK_INQ_BLK_ADDRESS_LINE_1_0;
	String CHECK_INQ_BLK_ADDRESS_LINE_2_0;
	String CHECK_INQ_BLK_GA_ID_1;
	String CHECK_INQ_BLK_MAILING_NAME_1;
	String CHECK_INQ_BLK_ADDRESS_LINE_1_1;
	String CHECK_INQ_BLK_ADDRESS_LINE_2_1;
	
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
	public void setParam989(String param989) {
		this.param989 = param989;
	}
	public void setParam989_X1(String param989_X1) {
		this.param989_X1 = param989_X1;
	}
	public void setCHECK_INQ_BLK_GA_ID_0(String cHECK_INQ_BLK_GA_ID_0) {
		CHECK_INQ_BLK_GA_ID_0 = cHECK_INQ_BLK_GA_ID_0;
	}
	public void setCHECK_INQ_BLK_MAILING_NAME_0(String cHECK_INQ_BLK_MAILING_NAME_0) {
		CHECK_INQ_BLK_MAILING_NAME_0 = cHECK_INQ_BLK_MAILING_NAME_0;
	}
	public void setCHECK_INQ_BLK_ADDRESS_LINE_1_0(
			String cHECK_INQ_BLK_ADDRESS_LINE_1_0) {
		CHECK_INQ_BLK_ADDRESS_LINE_1_0 = cHECK_INQ_BLK_ADDRESS_LINE_1_0;
	}
	public void setCHECK_INQ_BLK_ADDRESS_LINE_2_0(
			String cHECK_INQ_BLK_ADDRESS_LINE_2_0) {
		CHECK_INQ_BLK_ADDRESS_LINE_2_0 = cHECK_INQ_BLK_ADDRESS_LINE_2_0;
	}
	public void setCHECK_INQ_BLK_GA_ID_1(String cHECK_INQ_BLK_GA_ID_1) {
		CHECK_INQ_BLK_GA_ID_1 = cHECK_INQ_BLK_GA_ID_1;
	}
	public void setCHECK_INQ_BLK_MAILING_NAME_1(String cHECK_INQ_BLK_MAILING_NAME_1) {
		CHECK_INQ_BLK_MAILING_NAME_1 = cHECK_INQ_BLK_MAILING_NAME_1;
	}
	public void setCHECK_INQ_BLK_ADDRESS_LINE_1_1(
			String cHECK_INQ_BLK_ADDRESS_LINE_1_1) {
		CHECK_INQ_BLK_ADDRESS_LINE_1_1 = cHECK_INQ_BLK_ADDRESS_LINE_1_1;
	}
	public void setCHECK_INQ_BLK_ADDRESS_LINE_2_1(
			String cHECK_INQ_BLK_ADDRESS_LINE_2_1) {
		CHECK_INQ_BLK_ADDRESS_LINE_2_1 = cHECK_INQ_BLK_ADDRESS_LINE_2_1;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setParam989(Stock.GetParameterValue("param989"));
		this.setParam989_X1(Stock.GetParameterValue("param989_X1"));
		this.setCHECK_INQ_BLK_GA_ID_0(Stock.GetParameterValue("CHECK_INQ_BLK_GA_ID_0"));
		this.setCHECK_INQ_BLK_ADDRESS_LINE_1_0(Stock.GetParameterValue("CHECK_INQ_BLK_ADDRESS_LINE_1_0"));
		this.setCHECK_INQ_BLK_ADDRESS_LINE_2_0(Stock.GetParameterValue("CHECK_INQ_BLK_ADDRESS_LINE_2_0"));
		this.setCHECK_INQ_BLK_MAILING_NAME_0(Stock.GetParameterValue("CHECK_INQ_BLK_MAILING_NAME_0"));
		this.setCHECK_INQ_BLK_GA_ID_1(Stock.GetParameterValue("CHECK_INQ_BLK_GA_ID_1"));
		this.setCHECK_INQ_BLK_ADDRESS_LINE_1_1(Stock.GetParameterValue("CHECK_INQ_BLK_ADDRESS_LINE_1_1"));
		this.setCHECK_INQ_BLK_ADDRESS_LINE_2_1(Stock.GetParameterValue("CHECK_INQ_BLK_ADDRESS_LINE_2_1"));
		this.setCHECK_INQ_BLK_MAILING_NAME_1(Stock.GetParameterValue("CHECK_INQ_BLK_MAILING_NAME_1"));
		
		queryString = "?CHECK_INQ_BLK_ADDRESS_LINE_1_0="+CHECK_INQ_BLK_ADDRESS_LINE_1_0+"&CHECK_INQ_BLK_ADDRESS_LINE_1_1="+CHECK_INQ_BLK_ADDRESS_LINE_1_1+"&CHECK_INQ_BLK_ADDRESS_LINE_2_0="+CHECK_INQ_BLK_ADDRESS_LINE_2_0+
				"&CHECK_INQ_BLK_ADDRESS_LINE_2_1="+CHECK_INQ_BLK_ADDRESS_LINE_2_1+"&CHECK_INQ_BLK_GA_ID_0="+CHECK_INQ_BLK_GA_ID_0+"&CHECK_INQ_BLK_GA_ID_1="+CHECK_INQ_BLK_GA_ID_1+
				"&CHECK_INQ_BLK_MAILING_NAME_0="+CHECK_INQ_BLK_MAILING_NAME_0+"&CHECK_INQ_BLK_MAILING_NAME_1="+CHECK_INQ_BLK_MAILING_NAME_1+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+
				"&param989="+param989+"&param989_X1="+param989_X1+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for CINQ service", this.queryString.replaceAll("&", "\n"), false);
	
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
		//	this.queryString = URLEncoder.encode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			URL obj = new URL(serviceURL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			System.out.println(con.getErrorStream());
			System.out.println(con.getResponseCode());
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
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
			Reporter.logEvent(Status.PASS, "Run CINQ service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CINQ service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){	
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		}	
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String prov_comp = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCINQAdd")[1], this.CHECK_INQ_BLK_GA_ID_0, this.CHECK_INQ_BLK_GA_ID_1, this.param989);		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists in Database\nTable Name: CHECK_INQ", false);
			while(queryResultSet.next()){	
				//check
				Reporter.logEvent(Status.PASS, "From Database ", "PROV_COMP: "+queryResultSet.getString("PROV_COMPANY")+
					"\nGA_ID: "+queryResultSet.getString("GA_ID")+
					"\nMAILING_NAME: "+queryResultSet.getString("MAILING_NAME")+
					"\nADDRESS_LINE_1: "+queryResultSet.getString("ADDRESS_LINE_1")+
					"\nADDRESS_LINE_2: "+queryResultSet.getString("ADDRESS_LINE_2"),false);
			
				if(this.param989.equalsIgnoreCase(prov_comp)){
					Reporter.logEvent(Status.PASS, "Comparing and validating PROV_COMPANY_NAME from input and Database", "Both the values are same as expected+"
							+ "\nFrom Input: "+this.param989+"From Database: "+prov_comp, false);
				}else{
					Reporter.logEvent(Status.FAIL, "Comparing and validating PROV_COMPANY_NAME from input and Database", "Values are not same as expected+"
							+ "\nFrom Input: "+this.param989+"From Database: "+prov_comp, false);
				}
			}
		}	
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		
		
	}

}
