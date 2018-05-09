/**
 * 
 */
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

/**
 * @author smykjn
 *
 */
public class VSGR_Updt_Plan_Vesting_Rule_No_Part_Vest {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/VSGR_Updt_Plan_Vesting_Rule_No_Part_Vest";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	
	String plan_id;
	String CASCADE_CODE_LOV; 
	String CASCADE_RSN_LOV;
	String GET_GA_GA_ID_0_X1;
	String PLAN_VESTING_RULE_EARNED_ER_CNT_LAST_DAY_WORKED_1;
	String PLAN_VESTING_RULE_EARNED_ER_CONTRIB_HRS_WORKED_1;
	String PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_1;
	String PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_1;
	String PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_RET_1;
	String PLAN_VESTING_RULE_EFFDATE_3;
	String PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1;
	String PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_2;
	String PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_3;
	String PLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_1;
	String PLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_RULE_1;
	String PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1;
	String PLAN_VESTING_RULE_GDMT_SEQNBR_3;
	String PLAN_VESTING_RULE_HRS_WORKED_LAST_DAY_COMBO_1;
	String PLAN_VESTING_RULE_SDMT_CODE_3;
	String PLAN_VESTING_RULE_TERMDATE_0;
	String PLAN_VESTING_RULE_TERMDATE_0_X1;
	String RULE_TYPE_LOV;
	String RULE_TYPE_LOV_X1;
	String RULE_TYPE_LOV_X2;
	String param9317;
	String param9318;
	
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String GET_GA_GA_ID_0;
	
	
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
	public void setCASCADE_CODE_LOV(String cASCADE_CODE_LOV) {
		CASCADE_CODE_LOV = cASCADE_CODE_LOV;
	}
	public void setCASCADE_RSN_LOV(String cASCADE_RSN_LOV) {
		CASCADE_RSN_LOV = cASCADE_RSN_LOV;
	}
	public void setGET_GA_GA_ID_0_X1(String gET_GA_GA_ID_0_X1) {
		GET_GA_GA_ID_0_X1 = gET_GA_GA_ID_0_X1;
	}
	public void setPLAN_VESTING_RULE_EARNED_ER_CNT_LAST_DAY_WORKED_1(String pLAN_VESTING_RULE_EARNED_ER_CNT_LAST_DAY_WORKED_1) {
		PLAN_VESTING_RULE_EARNED_ER_CNT_LAST_DAY_WORKED_1 = pLAN_VESTING_RULE_EARNED_ER_CNT_LAST_DAY_WORKED_1;
	}
	public void setPLAN_VESTING_RULE_EARNED_ER_CONTRIB_HRS_WORKED_1(String pLAN_VESTING_RULE_EARNED_ER_CONTRIB_HRS_WORKED_1) {
		PLAN_VESTING_RULE_EARNED_ER_CONTRIB_HRS_WORKED_1 = pLAN_VESTING_RULE_EARNED_ER_CONTRIB_HRS_WORKED_1;
	}
	public void setPLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_1(String pLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_1) {
		PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_1 = pLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_1;
	}
	public void setPLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_1(String pLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_1) {
		PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_1 = pLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_1;
	}
	public void setPLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_RET_1(String pLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_RET_1) {
		PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_RET_1 = pLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_RET_1;
	}
	public void setPLAN_VESTING_RULE_EFFDATE_3(String pLAN_VESTING_RULE_EFFDATE_3) {
		PLAN_VESTING_RULE_EFFDATE_3 = pLAN_VESTING_RULE_EFFDATE_3;
	}
	public void setPLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1(String pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1) {
		PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1 = pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1;
	}
	public void setPLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_2(String pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_2) {
		PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_2 = pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_2;
	}
	public void setPLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_3(String pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_3) {
		PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_3 = pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_3;
	}
	public void setPLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_1(String pLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_1) {
		PLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_1 = pLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_1;
	}
	public void setPLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_RULE_1(String pLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_RULE_1) {
		PLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_RULE_1 = pLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_RULE_1;
	}
	public void setPLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1(String pLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1) {
		PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1 = pLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1;
	}
	public void setPLAN_VESTING_RULE_GDMT_SEQNBR_3(String pLAN_VESTING_RULE_GDMT_SEQNBR_3) {
		PLAN_VESTING_RULE_GDMT_SEQNBR_3 = pLAN_VESTING_RULE_GDMT_SEQNBR_3;
	}
	public void setPLAN_VESTING_RULE_HRS_WORKED_LAST_DAY_COMBO_1(String pLAN_VESTING_RULE_HRS_WORKED_LAST_DAY_COMBO_1) {
		PLAN_VESTING_RULE_HRS_WORKED_LAST_DAY_COMBO_1 = pLAN_VESTING_RULE_HRS_WORKED_LAST_DAY_COMBO_1;
	}
	public void setPLAN_VESTING_RULE_SDMT_CODE_3(String pLAN_VESTING_RULE_SDMT_CODE_3) {
		PLAN_VESTING_RULE_SDMT_CODE_3 = pLAN_VESTING_RULE_SDMT_CODE_3;
	}
	public void setPLAN_VESTING_RULE_TERMDATE_0(String pLAN_VESTING_RULE_TERMDATE_0) {
		PLAN_VESTING_RULE_TERMDATE_0 = pLAN_VESTING_RULE_TERMDATE_0;
	}
	public void setPLAN_VESTING_RULE_TERMDATE_0_X1(String pLAN_VESTING_RULE_TERMDATE_0_X1) {
		PLAN_VESTING_RULE_TERMDATE_0_X1 = pLAN_VESTING_RULE_TERMDATE_0_X1;
	}
	public void setRULE_TYPE_LOV(String rULE_TYPE_LOV) {
		RULE_TYPE_LOV = rULE_TYPE_LOV;
	}
	public void setRULE_TYPE_LOV_X1(String rULE_TYPE_LOV_X1) {
		RULE_TYPE_LOV_X1 = rULE_TYPE_LOV_X1;
	}
	public void setRULE_TYPE_LOV_X2(String rULE_TYPE_LOV_X2) {
		RULE_TYPE_LOV_X2 = rULE_TYPE_LOV_X2;
	}
	public void setparam9317(String Param9317) {
		param9317 = Param9317;
	}
	public void setparam9318(String Param9318) {
		param9318 = Param9318;
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
								+ "\nPlease select different ga-id with no exceptions.", false);
			}
		}
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("pptVestingDetails")[1],ga_id,ga_id);
		if(queryResultSet.next()){
				vestingDetails=false;
				plan_id=queryResultSet.getString("PLAN_ID");
				Reporter.logEvent(Status.FAIL,"Check if no records are found for vesting details for plan "+ga_id+"\n"
						+ "Query:\n"+Stock.getTestQuery("pptVestingDetails")[1],"records are found.please select different plan.", false);
		}else{
			vestingDetails=true;
			Reporter.logEvent(Status.INFO,"Check if no records are found for vesting details for plan "+ga_id+"\n"
					+ "Query:\n"+Stock.getTestQuery("pptVestingDetails")[1],"No records are found.", false);
		}
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("pptVestingPeriod")[1],ga_id,ga_id);
		if(queryResultSet.next()){
			vestingPeriod=false;
			plan_id=queryResultSet.getString("PLAN_ID");
			Reporter.logEvent(Status.WARNING,"Check if no records are found for vesting periods for plan "+ga_id+"\n"
					+ "Query:\n"+Stock.getTestQuery("pptVestingPeriod")[1],"records are found.please select different plan.", false);
		}else{
			vestingDetails=true;
			Reporter.logEvent(Status.INFO,"Check if no records are found for vesting periods for plan "+ga_id+"\n"
					+ "Query:\n"+Stock.getTestQuery("pptVestingPeriod")[1],"No records are found.", false);
		}
			
		if(vestingDetails && (vestingPeriod || exception))
			id=ga_id;
		else
			Reporter.logEvent(Status.INFO,"check precondition data.","Precondition data is not setup properly.", false);
		return id;
	}
	
	
	
	public void setServiceParameters() throws Exception
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0_X1(this.get_verified_ga_id(Stock.GetParameterValue("GET_GA_GA_ID_0_X1")));
		if(this.GET_GA_GA_ID_0_X1 == null){
			this.setGET_GA_GA_ID_0_X1(Stock.GetParameterValue("GET_GA_GA_ID_0_X1"));
		}
		this.setCASCADE_CODE_LOV(Stock.GetParameterValue("CASCADE_CODE_LOV"));
		this.setCASCADE_RSN_LOV(Stock.GetParameterValue("CASCADE_RSN_LOV"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setPLAN_VESTING_RULE_EARNED_ER_CNT_LAST_DAY_WORKED_1(Stock.GetParameterValue("PLAN_VESTING_RULE_EARNED_ER_CNT_LAST_DAY_WORKED_1").trim());
		this.setPLAN_VESTING_RULE_EARNED_ER_CONTRIB_HRS_WORKED_1(Stock.GetParameterValue("PLAN_VESTING_RULE_EARNED_ER_CONTRIB_HRS_WORKED_1").trim());
		this.setPLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_1(Stock.GetParameterValue("PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_1").trim());
		this.setPLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_1(Stock.GetParameterValue("PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_1").trim());
		this.setPLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_RET_1(Stock.GetParameterValue("PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_RET_1").trim());
		this.setPLAN_VESTING_RULE_EFFDATE_3(Stock.GetParameterValue("PLAN_VESTING_RULE_EFFDATE_3"));
		this.setPLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1(Stock.GetParameterValue("PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1"));
		this.setPLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_2(Stock.GetParameterValue("PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_2"));
		this.setPLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_3(Stock.GetParameterValue("PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_3"));
		this.setPLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_1(Stock.GetParameterValue("PLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_1").trim());
		this.setPLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_RULE_1(Stock.GetParameterValue("PLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_RULE_1").trim());
		this.setPLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1(Stock.GetParameterValue("PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1"));
		this.setPLAN_VESTING_RULE_GDMT_SEQNBR_3(Stock.GetParameterValue("PLAN_VESTING_RULE_GDMT_SEQNBR_3"));
		this.setPLAN_VESTING_RULE_HRS_WORKED_LAST_DAY_COMBO_1(Stock.GetParameterValue("PLAN_VESTING_RULE_HRS_WORKED_LAST_DAY_COMBO_1").trim());
		this.setPLAN_VESTING_RULE_SDMT_CODE_3(Stock.GetParameterValue("PLAN_VESTING_RULE_SDMT_CODE_3"));
		this.setPLAN_VESTING_RULE_TERMDATE_0(Stock.GetParameterValue("PLAN_VESTING_RULE_TERMDATE_0"));
		this.setPLAN_VESTING_RULE_TERMDATE_0_X1(Stock.GetParameterValue("PLAN_VESTING_RULE_TERMDATE_0_X1"));
		this.setRULE_TYPE_LOV(Stock.GetParameterValue("RULE_TYPE_LOV"));
		this.setRULE_TYPE_LOV_X1(Stock.GetParameterValue("RULE_TYPE_LOV_X1"));
		this.setRULE_TYPE_LOV_X2(Stock.GetParameterValue("RULE_TYPE_LOV_X2"));
		this.setparam9317(Stock.GetParameterValue("param9317"));
		this.setparam9318(Stock.GetParameterValue("param9318"));
		
		
		
		queryString = "?CASCADE_CODE_LOV="+CASCADE_CODE_LOV+"&CASCADE_RSN_LOV="+CASCADE_RSN_LOV+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0
				+ "&GET_GA_GA_ID_0_X1="+GET_GA_GA_ID_0_X1+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD
				+ "&LOGON_USERNAME="+LOGON_USERNAME+"&PLAN_VESTING_RULE_EARNED_ER_CNT_LAST_DAY_WORKED_1="+PLAN_VESTING_RULE_EARNED_ER_CNT_LAST_DAY_WORKED_1
				+ "&PLAN_VESTING_RULE_EARNED_ER_CONTRIB_HRS_WORKED_1="+PLAN_VESTING_RULE_EARNED_ER_CONTRIB_HRS_WORKED_1+"&PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_1="+PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_1
				+ "&PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_1="+PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_1+"&PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_RET_1="+PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_RET_1
				+ "&PLAN_VESTING_RULE_EFFDATE_3="+PLAN_VESTING_RULE_EFFDATE_3+"&PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1="+PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1
				+ "&PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_2="+PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_2
				+ "&PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_3="+PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_3
				+ "&PLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_1="+PLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_1
				+ "&PLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_RULE_1="+PLAN_VESTING_RULE_FORF_REDUCE_ER_CONTRIB_RULE_1
				+ "&PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1="+PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1
				+ "&PLAN_VESTING_RULE_GDMT_SEQNBR_3="+PLAN_VESTING_RULE_GDMT_SEQNBR_3
				+ "&PLAN_VESTING_RULE_HRS_WORKED_LAST_DAY_COMBO_1="+PLAN_VESTING_RULE_HRS_WORKED_LAST_DAY_COMBO_1
				+ "&PLAN_VESTING_RULE_SDMT_CODE_3="+PLAN_VESTING_RULE_SDMT_CODE_3
				+ "&PLAN_VESTING_RULE_TERMDATE_0="+PLAN_VESTING_RULE_TERMDATE_0
				+ "&PLAN_VESTING_RULE_TERMDATE_0_X1="+PLAN_VESTING_RULE_TERMDATE_0_X1
				+ "&RULE_TYPE_LOV="+RULE_TYPE_LOV+"&RULE_TYPE_LOV_X1="+RULE_TYPE_LOV_X1+"&RULE_TYPE_LOV_X2="+RULE_TYPE_LOV_X2
				+ "&param9317=1&param9318=1&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for VSGR_Updt_Plan_Vesting_Rule_No_Part_Vest service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run VSGR_Updt_Plan_Vesting_Rule_No_Part_Vest service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run VSGR_Updt_Plan_Vesting_Rule_No_Part_Vest service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if(Error.isEmpty())
		{
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);	
			Reporter.logEvent(Status.INFO, "From response:", "Popup message:"+doc.getElementsByTagName("PopupMessages").item(0).getTextContent()
					+"\nStatusBarMessage:"+doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent(), false);	
		}else{
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
	}
	
	public void validateInDatabase() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("")[1], plan_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating data existence in Database", "Records present in Database\nTablename: Plan", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validating data existence in Database", "Records doesn't exist in Database\nTablename: Plan", false);			
		}
	}
}
