package pageobject.VSGR;

import generallib.General;

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

public class VSGR_Updt_Vest_Calc_Input_Has_Part_Allowed {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/VSGR_Updt_Vest_Calc_Input_Has_Part_Vest_Allowed";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	String plan_id;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String GET_GA_GA_ID_0;
	String BUTTONS_VESTING_CALCULATION_INPUT_0;
	
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
	public void setBUTTONS_VESTING_CALCULATION_INPUT_0(
			String bUTTONS_VESTING_CALCULATION_INPUT_0) {
		BUTTONS_VESTING_CALCULATION_INPUT_0 = bUTTONS_VESTING_CALCULATION_INPUT_0;
	}
	
	public void setServiceParameters() throws Exception
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setBUTTONS_VESTING_CALCULATION_INPUT_0(Stock.GetParameterValue("BUTTONS_VESTING_CALCULATION_INPUT_0"));
		
		
		queryString = "?BUTTONS_VESTING_CALCULATION_INPUT_0="+BUTTONS_VESTING_CALCULATION_INPUT_0+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+
				"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for VSGR_Updt_Vest_Calc_Input_Has_Part_Vest_Allowed service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run VSGR_Updt_Vest_Calc_Input_Has_Part_Vest_Allowed service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run VSGR_Updt_Vest_Calc_Input_Has_Part_Vest_Allowed service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if(Error.isEmpty())
		{
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);	
		}else{
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
	}
	
	public void validateInDatabase() throws SQLException{
		plan_id = get_verified_ga_id(this.GET_GA_GA_ID_0);
		String ev_id = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForVSGR_Updt_Calc_HasPart_Allowed_1")[1], plan_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating data existence in Database", "Records present in Database\nTablename: Plan", false);
			while(queryResultSet.next()){
				
				Reporter.logEvent(Status.INFO, "From Database", "Vesting calculation input: "+queryResultSet.getString("VESTING_CALCULATION_INPUT"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating data existence in Database", "Records doesn't exist in Database\nTablename: Plan", false);			
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForVSGR_Updt_Calc_HasPart_Allowed_2")[1], this.LOGON_USERNAME, this.GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating data existence in Database", "Records present in Database\nTablename: EVENT", false);
			while(queryResultSet.next()){
				ev_id = queryResultSet.getString("ID");
				Reporter.logEvent(Status.INFO, "From Database", "ID: "+queryResultSet.getString("ID")+
						"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+
						"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME"), false);
				if(queryResultSet.getString("EVTY_CODE").equalsIgnoreCase("CHG GROUP")){
					Reporter.logEvent(Status.PASS, "Validating whether EVTY_CODE is CHG GROUP", "EVTY_CODE from DB: "+queryResultSet.getString("EVTY_CODE"), false);
				}else{
					Reporter.logEvent(Status.FAIL, "Validating whether EVTY_CODE is CHG GROUP", "EVTY_CODE from DB: "+queryResultSet.getString("EVTY_CODE"), false);
				}
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating data existence in Database", "Records doesn't exist in Database\nTablename: EVENT", false);			
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForVSGR_Updt_Calc_HasPart_Allowed_3")[1], ev_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating data existence in Database", "Records present in Database\nTablename: STEP", false);
			while(queryResultSet.next()){
				ev_id = queryResultSet.getString("ID");
				Reporter.logEvent(Status.INFO, "From Database", "ID: "+queryResultSet.getString("EV_ID")+
						"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+
						"\nUSER_LOGON_ID: "+queryResultSet.getString("USER_LOGON_ID"), false);
				
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating data existence in Database", "Records doesn't exist in Database\nTablename: STEP", false);			
		}
		
		
	}
	
	public String get_verified_ga_id(String ga_id) throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPlanID")[1], this.GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			while(queryResultSet.next())
				plan_id = queryResultSet.getString("PLAN_ID");
		}else{
			plan_id = null;
		}
		return plan_id;
	}
}
