package pageobject.PEDC;

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

public class PEDC_Chg_Electronic_Doc_Consent {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PEDC_CHANGE_ELECTRONIC_DOCUMENT_CONSENT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String STEP_TYPE_LOV;
	String STEP_TYPE_LOV_X1;
	String param9725;
	String param9726;
	String param9727;
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
	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
	}
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}
	public void setSTEP_TYPE_LOV_X1(String sTEP_TYPE_LOV_X1) {
		STEP_TYPE_LOV_X1 = sTEP_TYPE_LOV_X1;
	}
	public void setParam9725(String param9725) {
		this.param9725 = param9725;
	}
	public void setParam9726(String param9726) {
		this.param9726 = param9726;
	}
	public void setParam9727(String param9727) {
		this.param9727 = param9727;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));		
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setParam9725(Stock.GetParameterValue("param9725"));
		this.setParam9726(Stock.GetParameterValue("param9726"));
		this.setParam9727(Stock.GetParameterValue("param9727"));
		this.setSTEP_TYPE_LOV_X1(Stock.GetParameterValue("STEP_TYPE_LOV_X1"));		
		
		
		/*queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPEDCssn")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			while(queryResultSet.next())
				this.setCONTROL_SSN_DISPL_0(queryResultSet.getString("SSN"));
			System.out.println("SSN: "+this.CONTROL_SSN_DISPL_0);
		}else{
			this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
			System.out.println("SSN: "+this.CONTROL_SSN_DISPL_0);
		}*/
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_USERNAME="+LOGON_USERNAME+"&STEP_TYPE_LOV="+STEP_TYPE_LOV+"&STEP_TYPE_LOV_X1="+STEP_TYPE_LOV_X1+
				"&param9725="+param9725+"&param9726="+param9726+"&param9727="+param9727+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for PEDC service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run PEDC service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PEDC service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "CONTROL_PLAN_NAME_0: "+doc.getElementsByTagName("CONTROL_PLAN_NAME_0").item(0).getTextContent()+
					"\nCONTROL_PROD_ID_0: "+doc.getElementsByTagName("CONTROL_PROD_ID_0").item(0).getTextContent()+
					"\nEMPLOYMENT_LOA_LOA_HIREDATE_DISPLAY_0: "+doc.getElementsByTagName("EMPLOYMENT_LOA_LOA_HIREDATE_DISPLAY_0").item(0).getTextContent(), false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String ssn_ip = this.CONTROL_SSN_DISPL_0;
		String ga_id = null;
		String evty_code =null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPEDCChg1")[1], ssn_ip);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: ELEC_DOC_PART_SELECTION",false);
				while(queryResultSet.next()){
					ga_id = queryResultSet.getString("GA_ID");
					Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "SSN: "+ queryResultSet.getString("SSN")+
							"\nIND_ID: "+ queryResultSet.getString("IND_ID")+
							"\nGA_ID: "+ queryResultSet.getString("GA_ID")+
							"\nEDOCS_TYPE_CODE: "+ queryResultSet.getString("EDOCS_TYPE_CODE")+
							"\nEDOCS_CODE: "+ queryResultSet.getString("EDOCS_CODE")+
							"\nEDOCSG_ID: "+ queryResultSet.getString("EDOCSG_ID")+
							"\nSTATUS_CODE: "+ queryResultSet.getString("STATUS_CODE"), false);
				}				
			}
			else{
				Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
			}	
	
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPEDCChg2")[1], ga_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Event created in Database", "Records exists in Database, Event has been created\nTable name: EVENT\nINDIVIDUAL",false);
				while(queryResultSet.next()){
					evty_code = queryResultSet.getString("EVTY_CODE");
					Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "EV_ID: "+ queryResultSet.getString("ID")+
						"\nEVTY_CODE: "+ queryResultSet.getString("EVTY_CODE")+
						"\nLOGON_ID: "+ queryResultSet.getString("RESP_USER_LOGON_ID"), false);
				}
				if(evty_code.equalsIgnoreCase("CHG NUPART")){
					Reporter.logEvent(Status.PASS, "Validating an event is created with evty code CHG NUPART","EVTY CODE in Database: "+evty_code,false);
				}
				else{
					Reporter.logEvent(Status.FAIL, "Validating an event is created with evty code CHG NUPART","EVTY CODE in Database: "+evty_code,false);
				}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}
	}
	
}
