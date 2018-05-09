package pageobject.VSGR;

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

public class VSGR_Updt_Service_Rule_No_Part_Vest_1 {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/VSGR_Updt_Service_Rule_No_Part_Vest_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	String SERVICE_RULE_BREAK_IN_SRV_HRS_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String GET_GA_GA_ID_0;
	String SERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0;
	String SERVICE_RULE_EARLY_RETIRE_AGE_0;
	String SERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0;
	String SERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0;
	String SERVICE_RULE_FULL_VEST_DEATH_IND_0;
	String SERVICE_RULE_FULL_VEST_EARLY_RETIRE_0;
	String SERVICE_RULE_MIN_SRV_HRS_0;
	String SERVICE_RULE_NORMAL_RETIRE_AGE_0;
	String SERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0;
	String SERVICE_RULE_VEST_REMIT_TYPE_0;
	String param9317;
	String param9318;
	public void setSERVICE_RULE_BREAK_IN_SRV_HRS_0(
			String sERVICE_RULE_BREAK_IN_SRV_HRS_0) {
		SERVICE_RULE_BREAK_IN_SRV_HRS_0 = sERVICE_RULE_BREAK_IN_SRV_HRS_0;
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
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	public void setSERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0(
			String sERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0) {
		SERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0 = sERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0;
	}
	public void setSERVICE_RULE_EARLY_RETIRE_AGE_0(
			String sERVICE_RULE_EARLY_RETIRE_AGE_0) {
		SERVICE_RULE_EARLY_RETIRE_AGE_0 = sERVICE_RULE_EARLY_RETIRE_AGE_0;
	}
	public void setSERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0(
			String sERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0) {
		SERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0 = sERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0;
	}
	public void setSERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0(
			String sERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0) {
		SERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0 = sERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0;
	}
	public void setSERVICE_RULE_FULL_VEST_DEATH_IND_0(
			String sERVICE_RULE_FULL_VEST_DEATH_IND_0) {
		SERVICE_RULE_FULL_VEST_DEATH_IND_0 = sERVICE_RULE_FULL_VEST_DEATH_IND_0;
	}
	public void setSERVICE_RULE_FULL_VEST_EARLY_RETIRE_0(
			String sERVICE_RULE_FULL_VEST_EARLY_RETIRE_0) {
		SERVICE_RULE_FULL_VEST_EARLY_RETIRE_0 = sERVICE_RULE_FULL_VEST_EARLY_RETIRE_0;
	}
	public void setSERVICE_RULE_MIN_SRV_HRS_0(String sERVICE_RULE_MIN_SRV_HRS_0) {
		SERVICE_RULE_MIN_SRV_HRS_0 = sERVICE_RULE_MIN_SRV_HRS_0;
	}
	public void setSERVICE_RULE_NORMAL_RETIRE_AGE_0(
			String sERVICE_RULE_NORMAL_RETIRE_AGE_0) {
		SERVICE_RULE_NORMAL_RETIRE_AGE_0 = sERVICE_RULE_NORMAL_RETIRE_AGE_0;
	}
	public void setSERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0(
			String sERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0) {
		SERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0 = sERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0;
	}
	public void setSERVICE_RULE_VEST_REMIT_TYPE_0(
			String sERVICE_RULE_VEST_REMIT_TYPE_0) {
		SERVICE_RULE_VEST_REMIT_TYPE_0 = sERVICE_RULE_VEST_REMIT_TYPE_0;
	}
	public void setParam9317(String param9317) {
		this.param9317 = param9317;
	}
	public void setParam9318(String param9318) {
		this.param9318 = param9318;
	}
	
	public void setServiceParameters() throws Exception
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0((Stock.GetParameterValue("GET_GA_GA_ID_0")));
		this.setParam9317(Stock.GetParameterValue("param9317"));
		this.setParam9318(Stock.GetParameterValue("param9318"));
		this.setSERVICE_RULE_BREAK_IN_SRV_HRS_0(Stock.GetParameterValue("SERVICE_RULE_BREAK_IN_SRV_HRS_0"));
		this.setSERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0(Stock.GetParameterValue("SERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0"));
		this.setSERVICE_RULE_EARLY_RETIRE_AGE_0(Stock.GetParameterValue("SERVICE_RULE_EARLY_RETIRE_AGE_0"));
		this.setSERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0(Stock.GetParameterValue("SERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0"));
		this.setSERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0(Stock.GetParameterValue("SERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0"));
		this.setSERVICE_RULE_FULL_VEST_DEATH_IND_0(Stock.GetParameterValue("SERVICE_RULE_FULL_VEST_DEATH_IND_0"));
		this.setSERVICE_RULE_FULL_VEST_EARLY_RETIRE_0(Stock.GetParameterValue("SERVICE_RULE_FULL_VEST_EARLY_RETIRE_0"));
		this.setSERVICE_RULE_MIN_SRV_HRS_0(Stock.GetParameterValue("SERVICE_RULE_MIN_SRV_HRS_0"));
		this.setSERVICE_RULE_NORMAL_RETIRE_AGE_0(Stock.GetParameterValue("SERVICE_RULE_NORMAL_RETIRE_AGE_0"));
		this.setSERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0(Stock.GetParameterValue("SERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0"));
		this.setSERVICE_RULE_VEST_REMIT_TYPE_0(Stock.GetParameterValue("SERVICE_RULE_VEST_REMIT_TYPE_0"));
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+
				"&SERVICE_RULE_BREAK_IN_SRV_HRS_0="+SERVICE_RULE_BREAK_IN_SRV_HRS_0+"&SERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0="+SERVICE_RULE_DISREGARD_SRV_PRIOR_18_IND_0+"&SERVICE_RULE_EARLY_RETIRE_AGE_0="+SERVICE_RULE_EARLY_RETIRE_AGE_0+
				"&SERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0="+SERVICE_RULE_EARLY_RETIRE_YRS_OF_PART_0+"&SERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0="+SERVICE_RULE_EARLY_RETIRE_YRS_OF_SRV_0+"&SERVICE_RULE_FULL_VEST_DEATH_IND_0="+SERVICE_RULE_FULL_VEST_DEATH_IND_0+
				"&SERVICE_RULE_FULL_VEST_EARLY_RETIRE_0="+SERVICE_RULE_FULL_VEST_EARLY_RETIRE_0+"&SERVICE_RULE_MIN_SRV_HRS_0="+SERVICE_RULE_MIN_SRV_HRS_0+"&SERVICE_RULE_NORMAL_RETIRE_AGE_0="+SERVICE_RULE_NORMAL_RETIRE_AGE_0+
				"&SERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0="+SERVICE_RULE_NORMAL_RETIRE_YRS_OF_PART_0+"&SERVICE_RULE_VEST_REMIT_TYPE_0="+SERVICE_RULE_VEST_REMIT_TYPE_0+"&param9317="+param9317+"&param9318="+param9318+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for VSGR_Updt_Service_Rule_No_Part_Vest_1 service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run VSGR_Updt_Service_Rule_No_Part_Vest_1 service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run VSGR_Updt_Service_Rule_No_Part_Vest_1 service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if(Error.isEmpty())
		{
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "BUTTONS_VESTING_CALCULATION_INPUT_0: "+doc.getElementsByTagName("BUTTONS_VESTING_CALCULATION_INPUT_0").item(0).getTextContent(), false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}	
	}
	
	/*public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForVSGR_Updt_HasPart_NotAllowed_1")[1], plan_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating data existence in Database", "Records present in Database\nTablename: Plan", false);
			while(queryResultSet.next()){
				vest_cal_input = queryResultSet.getString("VESTING_CALCULATION_INPUT");
				Reporter.logEvent(Status.INFO, "From Database", "Vesting calculation input: "+vest_cal_input, false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating data existence in Database", "Records doesn't exist in Database\nTablename: Plan", false);			
		}
		
		if(!vest_cal_input.equals(BUTTONS_VESTING_CALCULATION_INPUT_0))
			Reporter.logEvent(Status.PASS,"Verify update was not made to plan table.","Vesting calculation input:"+vest_cal_input+"\n"
					+ "update vesting calculation input:"+BUTTONS_VESTING_CALCULATION_INPUT_0, false);
		else
			Reporter.logEvent(Status.FAIL,"Verify update was not made to plan table.","Vesting calculation input:"+vest_cal_input+"\n"
					+ "update vesting calculation input:"+BUTTONS_VESTING_CALCULATION_INPUT_0, false);
		//one more DB valiation remaining....
	}*/
	
}
