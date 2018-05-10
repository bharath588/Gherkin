package pageobject.PLMR;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class PLMR_Add_Plan_Match_Rule {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PLMR_ADDMATCHRULE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet1;
	private ResultSet queryResultSet2;
	private ResultSet queryResultSet3;

	String CONTROL_SELECTION_0; 
	String GET_GA_GA_ID_0; 
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME; 
	String PLAN_MATCH_RULE_CALC_SOURCE_0; 
	String PLAN_MATCH_RULE_SDMT_CODE_0; 
	String PLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0;
	String PLAN_MATCH_RULE_DETL_HIGH_THRESH_PERCENT_0; 
	String PLAN_MATCH_RULE_DETL_MATCH_PERCENT_0;
	
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
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
	public void setPLAN_MATCH_RULE_CALC_SOURCE_0(String pLAN_MATCH_RULE_CALC_SOURCE_0) {
		PLAN_MATCH_RULE_CALC_SOURCE_0 = pLAN_MATCH_RULE_CALC_SOURCE_0;
	}
	public void setPLAN_MATCH_RULE_SDMT_CODE_0(String pLAN_MATCH_RULE_SDMT_CODE_0) {
		PLAN_MATCH_RULE_SDMT_CODE_0 = pLAN_MATCH_RULE_SDMT_CODE_0;
	}
	public void setPLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0(String pLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0) {
		PLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0 = pLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0;
	}
	public void setPLAN_MATCH_RULE_DETL_HIGH_THRESH_PERCENT_0(String pLAN_MATCH_RULE_DETL_HIGH_THRESH_PERCENT_0) {
		PLAN_MATCH_RULE_DETL_HIGH_THRESH_PERCENT_0 = pLAN_MATCH_RULE_DETL_HIGH_THRESH_PERCENT_0;
	}
	public void setPLAN_MATCH_RULE_DETL_MATCH_PERCENT_0(String pLAN_MATCH_RULE_DETL_MATCH_PERCENT_0) {
		PLAN_MATCH_RULE_DETL_MATCH_PERCENT_0 = pLAN_MATCH_RULE_DETL_MATCH_PERCENT_0;
	}
	
	public void setServiceParameters()
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setPLAN_MATCH_RULE_CALC_SOURCE_0(Stock.GetParameterValue("PLAN_MATCH_RULE_CALC_SOURCE_0"));
		 this.setPLAN_MATCH_RULE_SDMT_CODE_0(Stock.GetParameterValue("PLAN_MATCH_RULE_SDMT_CODE_0"));
		 this.setPLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0(Stock.GetParameterValue("PLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0"));
		 this.setPLAN_MATCH_RULE_DETL_HIGH_THRESH_PERCENT_0(Stock.GetParameterValue("PLAN_MATCH_RULE_DETL_HIGH_THRESH_PERCENT_0"));
		 this.setPLAN_MATCH_RULE_DETL_MATCH_PERCENT_0(Stock.GetParameterValue("PLAN_MATCH_RULE_DETL_MATCH_PERCENT_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&PLAN_MATCH_RULE_SDMT_CODE_0="+PLAN_MATCH_RULE_SDMT_CODE_0+"&PLAN_MATCH_RULE_CALC_SOURCE_0="+PLAN_MATCH_RULE_CALC_SOURCE_0+
				 "&PLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0="+PLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0+"&PLAN_MATCH_RULE_DETL_MATCH_PERCENT_0="+PLAN_MATCH_RULE_DETL_MATCH_PERCENT_0+
				 "&PLAN_MATCH_RULE_DETL_HIGH_THRESH_PERCENT_0="+PLAN_MATCH_RULE_DETL_HIGH_THRESH_PERCENT_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
	
		 Reporter.logEvent(Status.INFO, "DATA SET UP: ", "GET_GA_GA_ID_0: "+GET_GA_GA_ID_0+
				 "\nPLAN_MATCH_RULE_CALC_SOURCE_0: "+PLAN_MATCH_RULE_CALC_SOURCE_0+
				 "\nPLAN_MATCH_RULE_SDMT_CODE_0: "+PLAN_MATCH_RULE_SDMT_CODE_0+
				 "\nPLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0: "+PLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0+
				 "\nPLAN_MATCH_RULE_DETL_HIGH_THRESH_PERCENT_0: "+PLAN_MATCH_RULE_DETL_HIGH_THRESH_PERCENT_0+
				 "\nPLAN_MATCH_RULE_DETL_MATCH_PERCENT_0: "+PLAN_MATCH_RULE_DETL_MATCH_PERCENT_0, false);
		 
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
	//		System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run PLMR plan match rule", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PLMR plan match rule", "Running Failed:", false);
		}
		
	}
	
	public void validateOutput() throws SQLException, Exception{
		
		String PMR_ID = null;
		String Plan_ID = null;
		String EffDate = null;
		String TermDate = null;
		String mny_type = null;
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		
		
		Reporter.logEvent(Status.INFO, "Values From RESPONSE: ", "GA_ID: "+ doc.getElementsByTagName("GROUP_HEADER1_N_GA_ID_0").item(0).getTextContent()+
				"\nGC_NAME: "+doc.getElementsByTagName("GROUP_HEADER1_N_GC_NAME_0").item(0).getTextContent()+
				"\nPLAN MATCH RULE: "+doc.getElementsByTagName("PLAN_MATCH_RULE_DETL_MATCH_0").item(0).getTextContent()+
				"\nEFF DATE: "+doc.getElementsByTagName("PLAN_MATCH_RULE_EFFDATE_0").item(0).getTextContent()+
				"\nTERM DATE: "+doc.getElementsByTagName("PLAN_MATCH_RULE_TERMDATE_0").item(0).getTextContent()+
				"\nSDMT DESCR: "+doc.getElementsByTagName("PLAN_MATCH_RULE_hash_GDMNTY_EE_SDMT_DESCR_0").item(0).getTextContent(), false);
		
		}
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToPlanMatchRuleRecord")[1], GET_GA_GA_ID_0);
		
		while(queryResultSet1.next())
		{
			PMR_ID = queryResultSet1.getString("ID");
			Plan_ID = queryResultSet1.getString("PLAN_ID");
			EffDate = queryResultSet1.getString("EFFDATE");
			TermDate = queryResultSet1.getString("TERMDATE");
			
			System.out.println("PMR_ID :" + PMR_ID + "\n" + "PLAN_ID :" + Plan_ID +"\n" + "EFFDATE :" + EffDate +"\n" + "TERMDATE :" + TermDate);
			Reporter.logEvent(Status.INFO, "Values From DATABASE: \nTable: PLAN_MATCH_RULE ", "PLAN_ID: "+ Plan_ID +"\n" + "EFFDATE :" + EffDate +"\n" + "TERMDATE :" + TermDate, false);
			break;
		}
		
		queryResultSet2 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTogetPlanMatchRuleDetail")[1], PMR_ID);
		if(queryResultSet2.next())
		{
		//	PMR_ID = queryResultSet2.getString("PMR_ID");
			String LowThresh = queryResultSet2.getString("LOW_THRESH_PERCENT");
			String HighThresh = queryResultSet2.getString("HIGH_THRESH_PERCENT");
			String MatchPercent = queryResultSet2.getString("MATCH_PERCENT");
			//System.out.println("PMR_ID :" +PMR_ID);
			System.out.println("LOW_THRESHOLD :" +LowThresh);
			System.out.println("HIGH_THRESHOLD :"+HighThresh);
			System.out.println("MATCH_PERCENT :" +MatchPercent);
			
			Reporter.logEvent(Status.INFO,"Table: PLAN_MATCH_RULE_DETL ","LOW_THRESHOLD :" +LowThresh+"\nHIGH_THRESHOLD :"+HighThresh+"\nMATCH_PERCENT :" +MatchPercent,false);
		}
		queryResultSet3 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTogetMoneytypePLMR")[1], PMR_ID);
		if(queryResultSet3.next())
		{
			PMR_ID = queryResultSet3.getString("PMR_ID");
			//System.out.println("PMR_ID :" +PMR_ID);
			String GA_ID = queryResultSet3.getString("GA_ID");
			System.out.println("GA_ID :"+GA_ID);
			String MoneyType = queryResultSet3.getString("MATCH_BASED_ON_SDMT_CODE");
			System.out.println("MONEY_TYPE :"+MoneyType);
			mny_type = MoneyType;
			Reporter.logEvent(Status.INFO,"Table: PLAN_MATCH_RULE#GRP_DEF_MNTY  ","GA_ID :"+GA_ID+"\nMONEY_TYPE :"+MoneyType, false);
		}
		if(this.PLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0.equalsIgnoreCase(mny_type)){
			Reporter.logEvent(Status.PASS,"Comparing and Validating money type created","Both the values are same as Expected"+
					"\nMoney Type: "+"From Input: "+this.PLAN_MATCH_RULE_hash_GDMNTY_MATCH_BASED_ON_SDMT_CODE_0+"\nFrom Response: "+mny_type,false);
		}
		
	}
}	
