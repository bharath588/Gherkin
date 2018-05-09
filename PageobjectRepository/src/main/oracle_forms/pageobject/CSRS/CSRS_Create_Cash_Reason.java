package pageobject.CSRS;

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

public class CSRS_Create_Cash_Reason {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CSRS_Create_Cash_Reason";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CASH_REASON_CODE_0;
	String CASH_REASON_DESCR_0;
	String CASH_REASON_EFFDATE_0;
	String param991;
	String param992;
	String CASH_REASON_BATCH_SOURCE_CASH_IND_0;
	String CASH_REASON_APPLY_LAG_IND_0;
	String param993;
	String CASH_REASON_DEBIT_GROUP_IND_0;
	
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
	public void setCASH_REASON_CODE_0(String cASH_REASON_CODE_0) {
		CASH_REASON_CODE_0 = cASH_REASON_CODE_0;
	}
	public void setCASH_REASON_DESCR_0(String cASH_REASON_DESCR_0) {
		CASH_REASON_DESCR_0 = cASH_REASON_DESCR_0;
	}
	public void setCASH_REASON_EFFDATE_0(String cASH_REASON_EFFDATE_0) {
		CASH_REASON_EFFDATE_0 = cASH_REASON_EFFDATE_0;
	}
	public void setParam991(String param991) {
		this.param991 = param991;
	}
	public void setParam992(String param992) {
		this.param992 = param992;
	}
	public void setCASH_REASON_BATCH_SOURCE_CASH_IND_0(
			String cASH_REASON_BATCH_SOURCE_CASH_IND_0) {
		CASH_REASON_BATCH_SOURCE_CASH_IND_0 = cASH_REASON_BATCH_SOURCE_CASH_IND_0;
	}
	public void setCASH_REASON_APPLY_LAG_IND_0(String cASH_REASON_APPLY_LAG_IND_0) {
		CASH_REASON_APPLY_LAG_IND_0 = cASH_REASON_APPLY_LAG_IND_0;
	}
	public void setParam993(String param993) {
		this.param993 = param993;
	}
	public void setCASH_REASON_DEBIT_GROUP_IND_0(
			String cASH_REASON_DEBIT_GROUP_IND_0) {
		CASH_REASON_DEBIT_GROUP_IND_0 = cASH_REASON_DEBIT_GROUP_IND_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE").toLowerCase());
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCASH_REASON_APPLY_LAG_IND_0(Stock.GetParameterValue("CASH_REASON_APPLY_LAG_IND_0"));
		this.setCASH_REASON_BATCH_SOURCE_CASH_IND_0(Stock.GetParameterValue("CASH_REASON_BATCH_SOURCE_CASH_IND_0"));
		this.setCASH_REASON_CODE_0(Stock.GetParameterValue("CASH_REASON_CODE_0"));
		this.setCASH_REASON_DEBIT_GROUP_IND_0(Stock.GetParameterValue("CASH_REASON_DEBIT_GROUP_IND_0"));
		this.setCASH_REASON_DESCR_0(Stock.GetParameterValue("CASH_REASON_DESCR_0"));
		this.setCASH_REASON_EFFDATE_0(Stock.GetParameterValue("CASH_REASON_EFFDATE_0"));
		this.setParam991(Stock.GetParameterValue("param991"));
		this.setParam992(Stock.GetParameterValue("param992"));
		this.setParam993(Stock.GetParameterValue("param993"));
		
		queryString = "?CASH_REASON_APPLY_LAG_IND_0="+CASH_REASON_APPLY_LAG_IND_0+ 
				"&CASH_REASON_BATCH_SOURCE_CASH_IND_0="+CASH_REASON_BATCH_SOURCE_CASH_IND_0+
				"&CASH_REASON_CODE_0="+CASH_REASON_CODE_0+
				"&CASH_REASON_DEBIT_GROUP_IND_0="+CASH_REASON_DEBIT_GROUP_IND_0+
				"&CASH_REASON_DESCR_0="+CASH_REASON_DESCR_0+
				"&CASH_REASON_EFFDATE_0="+CASH_REASON_EFFDATE_0+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&LOGON_DATABASE="+LOGON_DATABASE+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_USERNAME="+LOGON_USERNAME+				
				"&param991="+param991+
				"&param992="+param992+
				"&param993="+param993+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for CSRS service", this.queryString.replaceAll("&", "\n"), false);		
		
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
			Reporter.logEvent(Status.PASS, "Run CSRS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CSRS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "CASH_REASON_DFT_SDDETY_CODE_0: "+ doc.getElementsByTagName("CASH_REASON_DFT_SDDETY_CODE_0").item(0).getTextContent()+
					"\nCASH_REASON_FAT_CODE_0: "+ doc.getElementsByTagName("CASH_REASON_FAT_CODE_0").item(0).getTextContent()+
					"\nCASH_REASON_PLSM_DESCR_0: "+ doc.getElementsByTagName("CASH_REASON_PLSM_DESCR_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String cash_code  = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSRS")[1],  this.CASH_REASON_CODE_0);
		
		if(queryResultSet.next()){
			cash_code = queryResultSet.getString("CODE");
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Account Transaction\nTable Name: WORK_CONV_PART", false);
			Reporter.logEvent(Status.INFO, "Values From Database: \nTable Name: CASH_REASON", "CODE: "+queryResultSet.getString("CODE")+
					"\nDESCR: "+queryResultSet.getString("DESCR")+					
					"\nSDDETY_CODE: "+queryResultSet.getString("DFT_SDDETY_CODE"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(this.CASH_REASON_CODE_0.equalsIgnoreCase(cash_code)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Cash Reason Code from Input and Database", "Both the values are same as Expected"+
		"\nCODE: "+"From Input: "+this.CASH_REASON_CODE_0+"\nFrom Database: "+cash_code, false);
		}
	}
	
	
	
}