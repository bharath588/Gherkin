/**
 * 
 */
package pageobject.PLEG;

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

/**
 * @author smykjn
 *
 */
public class PLEG_Setup_Multiple_Rule {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PLEG_Setup_Multiple_Rule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String CFG_CONTROL_RG_ELIGIBILITY_ELIG_MNTY_RB_0;
	String ELIGIBILITY_RULE_MNTY_PLAN_LEVEL_RULE_IND_0;
	String ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0;
	String ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0;
	String ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0;
	String ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0;
	String ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_0;
	String ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0;
	String ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0;
	String ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_1;
	String ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_1;
	String ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_1;
	String ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_1;
	String ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_1;
	String ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_1;
	String ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_1;
	
	
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
	public void setCFG_CONTROL_RG_ELIGIBILITY_ELIG_MNTY_RB_0(String cFG_CONTROL_RG_ELIGIBILITY_ELIG_MNTY_RB_0) {
		CFG_CONTROL_RG_ELIGIBILITY_ELIG_MNTY_RB_0 = cFG_CONTROL_RG_ELIGIBILITY_ELIG_MNTY_RB_0;
	}
	public void setELIGIBILITY_RULE_MNTY_PLAN_LEVEL_RULE_IND_0(String eLIGIBILITY_RULE_MNTY_PLAN_LEVEL_RULE_IND_0) {
		ELIGIBILITY_RULE_MNTY_PLAN_LEVEL_RULE_IND_0 = eLIGIBILITY_RULE_MNTY_PLAN_LEVEL_RULE_IND_0;
	}
	public void setELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0(String eLIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0) {
		ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0 = eLIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0;
	}
	public void setELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0(String eLIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0) {
		ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0 = eLIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0;
	}
	public void setELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0(String eLIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0) {
		ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0 = eLIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0;
	}
	public void setELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0(String eLIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0) {
		ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0 = eLIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0;
	}
	public void setELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_0(String eLIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_0) {
		ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_0 = eLIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_0;
	}
	public void setELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0(String eLIGIBILITY_RULE_MNTY_NEXT_PERIOD_0) {
		ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0 = eLIGIBILITY_RULE_MNTY_NEXT_PERIOD_0;
	}
	public void setELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0(String eLIGIBILITY_RULE_MNTY_ENTRY_FREQ_0) {
		ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0 = eLIGIBILITY_RULE_MNTY_ENTRY_FREQ_0;
	}
	public void setELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_1(String eLIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_1) {
		ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_1 = eLIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_1;
	}
	public void setELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_1(String eLIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_1) {
		ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_1 = eLIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_1;
	}
	public void setELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_1(String eLIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_1) {
		ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_1 = eLIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_1;
	}
	public void setELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_1(String eLIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_1) {
		ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_1 = eLIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_1;
	}
	public void setELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_1(String eLIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_1) {
		ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_1 = eLIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_1;
	}
	public void setELIGIBILITY_RULE_MNTY_NEXT_PERIOD_1(String eLIGIBILITY_RULE_MNTY_NEXT_PERIOD_1) {
		ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_1 = eLIGIBILITY_RULE_MNTY_NEXT_PERIOD_1;
	}
	public void setELIGIBILITY_RULE_MNTY_ENTRY_FREQ_1(String eLIGIBILITY_RULE_MNTY_ENTRY_FREQ_1) {
		ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_1 = eLIGIBILITY_RULE_MNTY_ENTRY_FREQ_1;
	}
	
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setCFG_CONTROL_RG_ELIGIBILITY_ELIG_MNTY_RB_0(Stock.GetParameterValue("CFG_CONTROL_RG_ELIGIBILITY_ELIG_MNTY_RB_0"));
		this.setELIGIBILITY_RULE_MNTY_PLAN_LEVEL_RULE_IND_0(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_PLAN_LEVEL_RULE_IND_0"));
		this.setELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0"));
		this.setELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0"));
		this.setELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0"));
		this.setELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0"));
		this.setELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_0(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_0"));
		this.setELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0"));
		this.setELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0"));
		this.setELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_1(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_1"));
		this.setELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_1(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_1"));
		this.setELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_1(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_1"));
		this.setELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_1(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_1"));
		this.setELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_1(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_1"));
		this.setELIGIBILITY_RULE_MNTY_NEXT_PERIOD_1(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_1"));
		this.setELIGIBILITY_RULE_MNTY_ENTRY_FREQ_1(Stock.GetParameterValue("ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&CFG_CONTROL_RG_ELIGIBILITY_ELIG_MNTY_RB_0="+CFG_CONTROL_RG_ELIGIBILITY_ELIG_MNTY_RB_0+
				"&ELIGIBILITY_RULE_MNTY_PLAN_LEVEL_RULE_IND_0="+ELIGIBILITY_RULE_MNTY_PLAN_LEVEL_RULE_IND_0+
				"&ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0="+ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_0+
				"&ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0="+ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_0+
				"&ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0="+ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_0+
				"&ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0="+ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_0+
				"&ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_0="+ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_0+
				"&ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0="+ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_0+
				"&ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0="+ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_0+
				"&ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_1="+ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_1+
				"&ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_1="+ELIGIBILITY_RULE_MNTY_MIN_ELIG_AGE_MONTHS_1+
				"&ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_1="+ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_1+
				"&ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_1="+ELIGIBILITY_RULE_MNTY_MIN_ELIG_SERV_LEN_QUAL_1+
				"&ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_1="+ELIGIBILITY_RULE_MNTY_REQUIRED_HOURS_METHOD_1+
				"&ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_1="+ELIGIBILITY_RULE_MNTY_NEXT_PERIOD_1+
				"&ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_1="+ELIGIBILITY_RULE_MNTY_ENTRY_FREQ_1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for PLEG_Setup_Multiple_Rule service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() throws SQLException {
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
				Reporter.logEvent(Status.PASS, "Run PLEG_Setup_Multiple_Rule service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run PLEG_Setup_Multiple_Rule service", "Running Failed:", false);
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
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("eligibilityRuleMnty")[1], this.GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: eligibility_rule_mnty", "Records have been inserted in the Database", false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("eligibilityRuleSet")[1], this.GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: eligibility_rule_set", "Record has been inserted in the Database", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO,"From DB:","Default_Eligibility_Rule_Ind:"+queryResultSet.getString("DEFAULT_ELIGIBILITY_RULE_IND"),false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
	
	public void dataFlush() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("deleteElgRuleMnty")[1], this.GET_GA_GA_ID_0);
		queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("deleteElgRuleSet")[1], this.GET_GA_GA_ID_0);
		Reporter.logEvent(Status.INFO,"Reset the data.", "Data has been reset.", false);
	}
	
	
}
