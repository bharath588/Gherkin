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

public class VSGR_Updt_Plan_Vesting_Rule_Has_Part_Vest {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/VSGR_Updt_Plan_Vesting_Rule_Has_Part_Vest_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String GET_GA_GA_ID_0;
	String param9318;
	String param9319;
	String RULE_TYPE_LOV;
	String PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5;
	String PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_5;
	String PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_5;
	String PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_5;
	String PLAN_VESTING_RULE_TERMDATE_4;
	String PLAN_VESTING_RULE_TERMDATE_4_X1;
	String CASCADE_CODE_LOV;
	String CASCADE_RSN_LOV;
	String PLAN_VESTING_RULE_EFFDATE_5;
	String PLAN_VESTING_RULE_SDMT_CODE_5;
	String PLAN_VESTING_RULE_GDMT_SEQNBR_5;
	String PLAN_VESTING_RULE_RULE_TYPE_CODE_5;
	String PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5_X1;
	String PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1;
	String PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1;
	
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public void setQueryResultSet(ResultSet queryResultSet) {
		this.queryResultSet = queryResultSet;
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
	public void setParam9318(String param9318) {
		this.param9318 = param9318;
	}
	public void setParam9319(String param9319) {
		this.param9319 = param9319;
	}
	public void setRULE_TYPE_LOV(String rULE_TYPE_LOV) {
		RULE_TYPE_LOV = rULE_TYPE_LOV;
	}
	public void setPLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5(
			String pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5) {
		PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5 = pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5;
	}
	public void setPLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_5(
			String pLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_5) {
		PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_5 = pLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_5;
	}
	public void setPLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_5(
			String pLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_5) {
		PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_5 = pLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_5;
	}
	public void setPLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_5(
			String pLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_5) {
		PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_5 = pLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_5;
	}
	public void setPLAN_VESTING_RULE_TERMDATE_4(String pLAN_VESTING_RULE_TERMDATE_4) {
		PLAN_VESTING_RULE_TERMDATE_4 = pLAN_VESTING_RULE_TERMDATE_4;
	}
	public void setPLAN_VESTING_RULE_TERMDATE_4_X1(
			String pLAN_VESTING_RULE_TERMDATE_4_X1) {
		PLAN_VESTING_RULE_TERMDATE_4_X1 = pLAN_VESTING_RULE_TERMDATE_4_X1;
	}
	public void setCASCADE_CODE_LOV(String cASCADE_CODE_LOV) {
		CASCADE_CODE_LOV = cASCADE_CODE_LOV;
	}
	public void setCASCADE_RSN_LOV(String cASCADE_RSN_LOV) {
		CASCADE_RSN_LOV = cASCADE_RSN_LOV;
	}
	public void setPLAN_VESTING_RULE_EFFDATE_5(String pLAN_VESTING_RULE_EFFDATE_5) {
		PLAN_VESTING_RULE_EFFDATE_5 = pLAN_VESTING_RULE_EFFDATE_5;
	}
	public void setPLAN_VESTING_RULE_SDMT_CODE_5(
			String pLAN_VESTING_RULE_SDMT_CODE_5) {
		PLAN_VESTING_RULE_SDMT_CODE_5 = pLAN_VESTING_RULE_SDMT_CODE_5;
	}
	public void setPLAN_VESTING_RULE_GDMT_SEQNBR_5(
			String pLAN_VESTING_RULE_GDMT_SEQNBR_5) {
		PLAN_VESTING_RULE_GDMT_SEQNBR_5 = pLAN_VESTING_RULE_GDMT_SEQNBR_5;
	}
	public void setPLAN_VESTING_RULE_RULE_TYPE_CODE_5(
			String pLAN_VESTING_RULE_RULE_TYPE_CODE_5) {
		PLAN_VESTING_RULE_RULE_TYPE_CODE_5 = pLAN_VESTING_RULE_RULE_TYPE_CODE_5;
	}
	public void setPLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5_X1(
			String pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5_X1) {
		PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5_X1 = pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5_X1;
	}
	public void setPLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1(
			String pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1) {
		PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1 = pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1;
	}
	public void setPLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1(
			String pLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1) {
		PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1 = pLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setCASCADE_CODE_LOV(Stock.GetParameterValue("CASCADE_CODE_LOV"));
		this.setCASCADE_RSN_LOV(Stock.GetParameterValue("CASCADE_RSN_LOV"));
		this.setParam9318(Stock.GetParameterValue("param9318"));
		this.setParam9319(Stock.GetParameterValue("param9319"));
		this.setPLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_5(Stock.GetParameterValue("PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_5"));
		this.setPLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_5(Stock.GetParameterValue("pLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_5"));
		this.setPLAN_VESTING_RULE_EFFDATE_5(Stock.GetParameterValue("pLAN_VESTING_RULE_EFFDATE_5"));
		this.setPLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1(Stock.GetParameterValue("pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1"));
		this.setPLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5(Stock.GetParameterValue("pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5"));
		this.setPLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5_X1(Stock.GetParameterValue("pLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5_X1"));
		this.setPLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1(Stock.GetParameterValue("pLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1"));
		this.setPLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_5(Stock.GetParameterValue("pLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_5"));
		this.setPLAN_VESTING_RULE_GDMT_SEQNBR_5(Stock.GetParameterValue("pLAN_VESTING_RULE_GDMT_SEQNBR_5"));
		this.setPLAN_VESTING_RULE_RULE_TYPE_CODE_5(Stock.GetParameterValue("pLAN_VESTING_RULE_RULE_TYPE_CODE_5"));
		this.setPLAN_VESTING_RULE_SDMT_CODE_5(Stock.GetParameterValue("pLAN_VESTING_RULE_SDMT_CODE_5"));
		this.setPLAN_VESTING_RULE_TERMDATE_4(Stock.GetParameterValue("pLAN_VESTING_RULE_TERMDATE_4"));
		this.setPLAN_VESTING_RULE_TERMDATE_4_X1(Stock.GetParameterValue("pLAN_VESTING_RULE_TERMDATE_4_X1"));
		this.setRULE_TYPE_LOV(Stock.GetParameterValue("rULE_TYPE_LOV"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&param9318="+param9318+"&param9319="+param9319+"&RULE_TYPE_LOV="+RULE_TYPE_LOV+"&PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5="+PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5+
				"&PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_5="+PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DIS_5+"&PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_5="+PLAN_VESTING_RULE_EARNED_ER_CONTRIB_IN_YR_OF_DTH_5+
				"&PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_5="+PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_5+"&PLAN_VESTING_RULE_TERMDATE_4="+PLAN_VESTING_RULE_TERMDATE_4+"&PLAN_VESTING_RULE_TERMDATE_4_X1="+PLAN_VESTING_RULE_TERMDATE_4_X1+
				"&CASCADE_CODE_LOV="+CASCADE_CODE_LOV+"&CASCADE_RSN_LOV="+CASCADE_RSN_LOV+"&PLAN_VESTING_RULE_EFFDATE_5="+PLAN_VESTING_RULE_EFFDATE_5+"&PLAN_VESTING_RULE_SDMT_CODE_5="+PLAN_VESTING_RULE_SDMT_CODE_5+
				"&PLAN_VESTING_RULE_GDMT_SEQNBR_5="+PLAN_VESTING_RULE_GDMT_SEQNBR_5+"&PLAN_VESTING_RULE_RULE_TYPE_CODE_5="+PLAN_VESTING_RULE_RULE_TYPE_CODE_5+"&PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5_X1="+PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_5_X1+
				"&PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1="+PLAN_VESTING_RULE_ENFORCE_EARNED_ER_CONTRIB_RULE_1+"&PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1="+PLAN_VESTING_RULE_FORF_REDUCE_EXPENSES_1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for VSGR Update Service Rule Has Part Vest service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run VSGR Update Plan Vesting Rule Has Part vest service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run VSGR Update Plan Vesting Rule Has Part vest service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if(Error.isEmpty())
		{
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);	
			Reporter.logEvent(Status.INFO, "From Response ", "BUTTONS_VESTING_CALCULATION_INPUT_0: "+doc.getElementsByTagName("BUTTONS_VESTING_CALCULATION_INPUT_0").item(0).getTextContent(),false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
	}
	
	public void validateInDatabase() throws SQLException{
		
	//	String btnvesting = doc.getElementsByTagName("BUTTONS_VESTING_CALCULATION_INPUT_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForVSGRUpdtPlanServicehasPartVest")[1], this.GET_GA_GA_ID_0, this.PLAN_VESTING_RULE_RULE_TYPE_CODE_5, this.PLAN_VESTING_RULE_SDMT_CODE_5);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating data existence in Database", "Records present in Database\nTablename: PLAN_VESTING_RULE", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "From Database", "PLAN_ID: "+queryResultSet.getString("PLAN_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nRULE_TYPE_CODE: "+queryResultSet.getString("RULE_TYPE_CODE")+
						"\nSDMT_CODE: "+queryResultSet.getString("SDMT_CODE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating data existence in Database", "Records doesn't exist in Database\nTablename: PLAN_VESTING_RULE", false);			
		}
	}
}
