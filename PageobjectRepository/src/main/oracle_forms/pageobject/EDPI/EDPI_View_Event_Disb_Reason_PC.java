package pageobject.EDPI;

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

public class EDPI_View_Event_Disb_Reason_PC {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/EDPI_View_Event_Disb_Reason_PC_Rule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_EVTY_CODE_0;
	String EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2;
	String EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2_X1;
	String param9111;
	String EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_PROV_CO_2;
	String param9112;
	String EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_IRS_RULE_2;
	String param9113;
	
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
	public void setEVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_EVTY_CODE_0(
			String eVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_EVTY_CODE_0) {
		EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_EVTY_CODE_0 = eVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_EVTY_CODE_0;
	}
	public void setEVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2(
			String eVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2) {
		EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2 = eVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2;
	}
	public void setEVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2_X1(
			String eVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2_X1) {
		EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2_X1 = eVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2_X1;
	}
	public void setParam9111(String param9111) {
		this.param9111 = param9111;
	}
	public void setEVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_PROV_CO_2(
			String eVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_PROV_CO_2) {
		EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_PROV_CO_2 = eVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_PROV_CO_2;
	}
	public void setParam9112(String param9112) {
		this.param9112 = param9112;
	}
	public void setEVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_IRS_RULE_2(
			String eVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_IRS_RULE_2) {
		EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_IRS_RULE_2 = eVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_IRS_RULE_2;
	}
	public void setParam9113(String param9113) {
		this.param9113 = param9113;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));		
		this.setEVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2(Stock.GetParameterValue("EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2"));
		this.setEVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2_X1(Stock.GetParameterValue("EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2_X1"));
		this.setEVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_EVTY_CODE_0(Stock.GetParameterValue("EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_EVTY_CODE_0"));
		this.setEVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_IRS_RULE_2(Stock.GetParameterValue("EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_IRS_RULE_2"));
		this.setEVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_PROV_CO_2(Stock.GetParameterValue("EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_PROV_CO_2"));
		this.setParam9111(Stock.GetParameterValue("param9111"));
		this.setParam9112(Stock.GetParameterValue("param9112"));
		this.setParam9113(Stock.GetParameterValue("param9113"));		
		
	/*	queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2="+EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2+"&EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2_X1="+EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_DSRS_CODE_2_X1+
				"&EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_EVTY_CODE_0="+EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_EVTY_CODE_0+"&EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_IRS_RULE_2="+EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_IRS_RULE_2+
				"&EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_PROV_CO_2="+EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_PROV_CO_2+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_USERNAME="+LOGON_USERNAME+"&param9111="+param9111+"&param9112="+param9112+"&param9113="+param9113+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		*/
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_EVTY_CODE_0="+EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_EVTY_CODE_0+
				"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for EDPI service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run EDPI service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run EDPI service", "Running Failed:", false);
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
		String evty_code = this.EVTY_hash_DSRS_hash_PROVCO_hash_IRS_RULE_EVTY_CODE_0;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForEDPIAdd")[1], evty_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: EVTY#DSRS#PROVCO#IRS_RULE",false);
			Reporter.logEvent(Status.INFO, "Expected: Query should return more than one record", "Total number of record: "+DB.getRecordSetCount(queryResultSet),false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}
	}
	
}
