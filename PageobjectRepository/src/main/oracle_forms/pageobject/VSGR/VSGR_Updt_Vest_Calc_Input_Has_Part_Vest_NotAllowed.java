/**
 * 
 */
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

/**
 * @author smykjn
 */
public class VSGR_Updt_Vest_Calc_Input_Has_Part_Vest_NotAllowed {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/VSGR_Updt_Vest_Calc_Input_Has_Part_Vest_NotAllowed";
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
	
	
	public String get_verified_ga_id(String ga_id) throws SQLException{
		String id=null;
		boolean exception=false;
		boolean vestingDetails= false;
		boolean vestingPeriod= false;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("planExceptionIndicators")[1],ga_id);
		while(queryResultSet.next()){
			String srvcException = queryResultSet.getString("SERVICE_RULE_EXCEPTION_IND");
			String vstngException = queryResultSet.getString("VESTING_RULE_EXCEPTION_IND");
			if(srvcException==null && vstngException==null){
				exception=true;break;
			}
			else{
				Reporter.logEvent(Status.FAIL,"Validate if plan "+ga_id+" does not have exception indicators."
						+ "\n"+Stock.getTestQuery("planExceptionIndicators")[1],"Plan "+ga_id+" is found with exception on vesting and service rule."
								+ "\nPlease select different ga-id with no exception.", false);
			}
		}
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("pptVestingDetails")[1],ga_id,ga_id);
		if(queryResultSet.next()){
				vestingDetails=true;
				plan_id=queryResultSet.getString("PLAN_ID");
		}else{
			Reporter.logEvent(Status.FAIL,"Check if records are found for vesting details for plan "+ga_id+"\n"
					+ "Query:\n"+Stock.getTestQuery("pptVestingDetails")[1],"Records are not found.", false);
		}
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("pptVestingPeriod")[1],ga_id,ga_id);
		if(queryResultSet.next()){
			vestingPeriod=true;
		}else{
			Reporter.logEvent(Status.FAIL,"Check if records are found for vesting details for plan "+ga_id+"\n"
					+ "Query:\n"+Stock.getTestQuery("pptVestingPeriod")[1],"Records are not found.", false);
		}
			
		if(vestingDetails && vestingPeriod && exception)
			id=ga_id;
		else
			Reporter.logEvent(Status.FAIL,"check precondition data.","Precondition data is not setup properly.", false);
		
		
		return id;
	}
	
	
	
	public void setServiceParameters() throws Exception
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(this.get_verified_ga_id(Stock.GetParameterValue("GET_GA_GA_ID_0")));
		this.setBUTTONS_VESTING_CALCULATION_INPUT_0(Stock.GetParameterValue("BUTTONS_VESTING_CALCULATION_INPUT_0"));
		
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&BUTTONS_VESTING_CALCULATION_INPUT_0="+BUTTONS_VESTING_CALCULATION_INPUT_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for VSGR_Updt_Vest_Calc_Input_Has_Part_Vest_NotAllowed service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run VSGR_Updt_Vest_Calc_Input_Has_Part_Vest_NotAllowed service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run VSGR_Updt_Vest_Calc_Input_Has_Part_Vest_NotAllowed service", "Running Failed:", false);
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
		String vest_cal_input = null;
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
	}
}
