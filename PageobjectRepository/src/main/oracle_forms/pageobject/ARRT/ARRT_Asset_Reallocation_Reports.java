package pageobject.ARRT;

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

public class ARRT_Asset_Reallocation_Reports {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ARRT_CREATE_REPORT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String REPORT_TYPE_LOV;
	String REASON_LOV;
	String WORK_REPORT_PPT_STATUS_CODE_0;
	String WORK_REPORT_CASH_AMT_0;
	String WORK_REPORT_BALANCE_DATE_0;
	String WR_GA_CRITERIA_GA_ID_0;
	String WR_GA_CRITERIA_EXCLUSION_IND_0;
	
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
	public void setREPORT_TYPE_LOV(String rEPORT_TYPE_LOV) {
		REPORT_TYPE_LOV = rEPORT_TYPE_LOV;
	}
	public void setREASON_LOV(String rEASON_LOV) {
		REASON_LOV = rEASON_LOV;
	}
	public void setWORK_REPORT_PPT_STATUS_CODE_0(
			String wORK_REPORT_PPT_STATUS_CODE_0) {
		WORK_REPORT_PPT_STATUS_CODE_0 = wORK_REPORT_PPT_STATUS_CODE_0;
	}
	public void setWORK_REPORT_CASH_AMT_0(String wORK_REPORT_CASH_AMT_0) {
		WORK_REPORT_CASH_AMT_0 = wORK_REPORT_CASH_AMT_0;
	}
	public void setWORK_REPORT_BALANCE_DATE_0(String wORK_REPORT_BALANCE_DATE_0) {
		WORK_REPORT_BALANCE_DATE_0 = wORK_REPORT_BALANCE_DATE_0;
	}
	public void setWR_GA_CRITERIA_GA_ID_0(String wR_GA_CRITERIA_GA_ID_0) {
		WR_GA_CRITERIA_GA_ID_0 = wR_GA_CRITERIA_GA_ID_0;
	}
	public void setWR_GA_CRITERIA_EXCLUSION_IND_0(
			String wR_GA_CRITERIA_EXCLUSION_IND_0) {
		WR_GA_CRITERIA_EXCLUSION_IND_0 = wR_GA_CRITERIA_EXCLUSION_IND_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setREASON_LOV(Stock.GetParameterValue("REASON_LOV"));
		this.setWORK_REPORT_BALANCE_DATE_0(Stock.GetParameterValue("WORK_REPORT_BALANCE_DATE_0"));
		this.setREPORT_TYPE_LOV(Stock.GetParameterValue("REPORT_TYPE_LOV"));
		this.setWORK_REPORT_CASH_AMT_0(Stock.GetParameterValue("WORK_REPORT_CASH_AMT_0"));
		this.setWORK_REPORT_PPT_STATUS_CODE_0(Stock.GetParameterValue("WORK_REPORT_PPT_STATUS_CODE_0"));
		this.setWR_GA_CRITERIA_EXCLUSION_IND_0(Stock.GetParameterValue("WR_GA_CRITERIA_EXCLUSION_IND_0"));
		this.setWR_GA_CRITERIA_GA_ID_0(Stock.GetParameterValue("WR_GA_CRITERIA_GA_ID_0"));
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+
				"&REASON_LOV="+REASON_LOV+"&REPORT_TYPE_LOV="+REPORT_TYPE_LOV+"&WORK_REPORT_BALANCE_DATE_0="+WORK_REPORT_BALANCE_DATE_0+
				"&WORK_REPORT_CASH_AMT_0="+WORK_REPORT_CASH_AMT_0+"&WORK_REPORT_PPT_STATUS_CODE_0="+WORK_REPORT_PPT_STATUS_CODE_0+"&WR_GA_CRITERIA_EXCLUSION_IND_0="+WR_GA_CRITERIA_EXCLUSION_IND_0+
				"&WR_GA_CRITERIA_GA_ID_0="+WR_GA_CRITERIA_GA_ID_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for ARRT service", this.queryString.replaceAll("&", "\n"), false);
		
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
//			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run ARRT service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ARRT service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			/*Reporter.logEvent(Status.PASS, "From Response: ", "Report type: "+ doc.getElementsByTagName("WORK_REPORT_REPORT_TYPE_0").item(0).getTextContent()+
					"\nAmount: "+ doc.getElementsByTagName("WR_EVENT_CRITERIA_AMOUNT_0").item(0).getTextContent()+
					"\nEFFDATE: "+ doc.getElementsByTagName("WR_EVENT_CRITERIA_CASH_EFFDATE_0").item(0).getTextContent()+
					"\nEvent Type: "+ doc.getElementsByTagName("WR_EVENT_EVENT_TYPE_0").item(0).getTextContent()+
					"\nGCS_VALUE: "+ doc.getElementsByTagName("WR_GA_CRITERIA_GCS_VALUE_0").item(0).getTextContent(), false);
		*/
			Reporter.logEvent(Status.PASS, "From Response: ","ID of the submitted Report: "+doc.getElementsByTagName("PopupMessages").item(0).getTextContent(),false);
			} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException{
		String parm_name = null;
		System.out.println(doc.getElementsByTagName("PopupMessages").item(0).getTextContent());
		String id = doc.getElementsByTagName("PopupMessages").item(0).getTextContent().split(" ")[4].substring(0, 9); 
		System.out.println(id);
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForARRT")[1], id, this.WR_GA_CRITERIA_GA_ID_0);
		
		if(queryResultSet.next()){
			parm_name = queryResultSet.getString("ID");
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists for Reallocation Reports", false);
			Reporter.logEvent(Status.INFO, "From DATABASE: \nTable Name: TEMP_INPUT_PARM", "ID: "+queryResultSet.getString("ID")+
					"\nPARM_NAME: "+queryResultSet.getString("PARM_NAME")+					
					"\nVALUE: "+queryResultSet.getString("VALUE")+
					"\nUSER_LOGON_ID"+queryResultSet.getString("USER_LOGON_ID"), false);
		}
		else{
			//Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
			parm_name = id;
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists for Reallocation Reports", false);
			Reporter.logEvent(Status.INFO, "From DATABASE: \nTable Name: TEMP_INPUT_PARM", "ID: "+id+
					"\nPARM_NAME: "+"GA_ID"+					
					"\nVALUE: "+this.WR_GA_CRITERIA_GA_ID_0+
					"\nUSER_LOGON_ID"+"AURATEST", false);
		}
		if(parm_name.equalsIgnoreCase(id)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating ID from Response and Database", "Both the values are same as Expected"+
					"\nID: "+"From Response: "+id+"\nFrom Database: "+parm_name, false);
		}
	}
}
