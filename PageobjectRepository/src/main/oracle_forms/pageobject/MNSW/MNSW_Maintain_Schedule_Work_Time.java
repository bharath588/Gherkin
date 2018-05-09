package pageobject.MNSW;

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

public class MNSW_Maintain_Schedule_Work_Time {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MNSW_Maintain_Schedule_Work_Time";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String HEADER_USER_LOGON_ID_0;
	String SCHED_WK_TIME_HOURS_0;
	String SCHED_WK_TIME_HOURS_0_X1;
	String SCHED_WK_TIME_EFFDATE_0;
	String SCHED_WK_TIME_DAY_OF_WEEK_5;
	String SCHED_WK_TIME_HOURS_5;
	String SCHED_WK_TIME_EFFDATE_5;
	String SCHED_WK_TIME_DAY_OF_WEEK_6;
	String SCHED_WK_TIME_HOURS_6;
	String SCHED_WK_TIME_EFFDATE_6;
	String SCHED_WK_TIME_DAY_OF_WEEK_7;
	String SCHED_WK_TIME_HOURS_7;
	String SCHED_WK_TIME_EFFDATE_7;
	String SCHED_WK_TIME_DAY_OF_WEEK_8;
	String SCHED_WK_TIME_HOURS_8;
	String SCHED_WK_TIME_EFFDATE_8;
	String SCHED_WK_TIME_DAY_OF_WEEK_8_X1;
	String SCHED_WK_TIME_HOURS_8_X1;
	String SCHED_WK_TIME_EFFDATE_8_X1;
	String SCHED_WK_TIME_TERMDATE_0;
	String SCHED_WK_TIME_TERMDATE_0_X1;
	String SCHED_WK_TIME_TERMDATE_1;
	String SCHED_WK_TIME_TERMDATE_1_X1;
	String SCHED_WK_TIME_TERMDATE_2;
	String SCHED_WK_TIME_TERMDATE_2_X1;
	String SCHED_WK_TIME_TERMDATE_3;
	String SCHED_WK_TIME_TERMDATE_3_X1;
	String SCHED_WK_TIME_TERMDATE_4;
	String SCHED_WK_TIME_TERMDATE_4_X1;
	
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
	public void setHEADER_USER_LOGON_ID_0(String hEADER_USER_LOGON_ID_0) {
		HEADER_USER_LOGON_ID_0 = hEADER_USER_LOGON_ID_0;
	}
	public void setSCHED_WK_TIME_HOURS_0(String sCHED_WK_TIME_HOURS_0) {
		SCHED_WK_TIME_HOURS_0 = sCHED_WK_TIME_HOURS_0;
	}
	public void setSCHED_WK_TIME_HOURS_0_X1(String sCHED_WK_TIME_HOURS_0_X1) {
		SCHED_WK_TIME_HOURS_0_X1 = sCHED_WK_TIME_HOURS_0_X1;
	}
	public void setSCHED_WK_TIME_EFFDATE_0(String sCHED_WK_TIME_EFFDATE_0) {
		SCHED_WK_TIME_EFFDATE_0 = sCHED_WK_TIME_EFFDATE_0;
	}
	public void setSCHED_WK_TIME_DAY_OF_WEEK_5(String sCHED_WK_TIME_DAY_OF_WEEK_5) {
		SCHED_WK_TIME_DAY_OF_WEEK_5 = sCHED_WK_TIME_DAY_OF_WEEK_5;
	}
	public void setSCHED_WK_TIME_HOURS_5(String sCHED_WK_TIME_HOURS_5) {
		SCHED_WK_TIME_HOURS_5 = sCHED_WK_TIME_HOURS_5;
	}
	public void setSCHED_WK_TIME_EFFDATE_5(String sCHED_WK_TIME_EFFDATE_5) {
		SCHED_WK_TIME_EFFDATE_5 = sCHED_WK_TIME_EFFDATE_5;
	}
	public void setSCHED_WK_TIME_DAY_OF_WEEK_6(String sCHED_WK_TIME_DAY_OF_WEEK_6) {
		SCHED_WK_TIME_DAY_OF_WEEK_6 = sCHED_WK_TIME_DAY_OF_WEEK_6;
	}
	public void setSCHED_WK_TIME_HOURS_6(String sCHED_WK_TIME_HOURS_6) {
		SCHED_WK_TIME_HOURS_6 = sCHED_WK_TIME_HOURS_6;
	}
	public void setSCHED_WK_TIME_EFFDATE_6(String sCHED_WK_TIME_EFFDATE_6) {
		SCHED_WK_TIME_EFFDATE_6 = sCHED_WK_TIME_EFFDATE_6;
	}
	public void setSCHED_WK_TIME_DAY_OF_WEEK_7(String sCHED_WK_TIME_DAY_OF_WEEK_7) {
		SCHED_WK_TIME_DAY_OF_WEEK_7 = sCHED_WK_TIME_DAY_OF_WEEK_7;
	}
	public void setSCHED_WK_TIME_HOURS_7(String sCHED_WK_TIME_HOURS_7) {
		SCHED_WK_TIME_HOURS_7 = sCHED_WK_TIME_HOURS_7;
	}
	public void setSCHED_WK_TIME_EFFDATE_7(String sCHED_WK_TIME_EFFDATE_7) {
		SCHED_WK_TIME_EFFDATE_7 = sCHED_WK_TIME_EFFDATE_7;
	}
	public void setSCHED_WK_TIME_DAY_OF_WEEK_8(String sCHED_WK_TIME_DAY_OF_WEEK_8) {
		SCHED_WK_TIME_DAY_OF_WEEK_8 = sCHED_WK_TIME_DAY_OF_WEEK_8;
	}
	public void setSCHED_WK_TIME_HOURS_8(String sCHED_WK_TIME_HOURS_8) {
		SCHED_WK_TIME_HOURS_8 = sCHED_WK_TIME_HOURS_8;
	}
	public void setSCHED_WK_TIME_EFFDATE_8(String sCHED_WK_TIME_EFFDATE_8) {
		SCHED_WK_TIME_EFFDATE_8 = sCHED_WK_TIME_EFFDATE_8;
	}
	public void setSCHED_WK_TIME_DAY_OF_WEEK_8_X1(
			String sCHED_WK_TIME_DAY_OF_WEEK_8_X1) {
		SCHED_WK_TIME_DAY_OF_WEEK_8_X1 = sCHED_WK_TIME_DAY_OF_WEEK_8_X1;
	}
	public void setSCHED_WK_TIME_HOURS_8_X1(String sCHED_WK_TIME_HOURS_8_X1) {
		SCHED_WK_TIME_HOURS_8_X1 = sCHED_WK_TIME_HOURS_8_X1;
	}
	public void setSCHED_WK_TIME_EFFDATE_8_X1(String sCHED_WK_TIME_EFFDATE_8_X1) {
		SCHED_WK_TIME_EFFDATE_8_X1 = sCHED_WK_TIME_EFFDATE_8_X1;
	}
	public void setSCHED_WK_TIME_TERMDATE_0(String sCHED_WK_TIME_TERMDATE_0) {
		SCHED_WK_TIME_TERMDATE_0 = sCHED_WK_TIME_TERMDATE_0;
	}
	public void setSCHED_WK_TIME_TERMDATE_0_X1(String sCHED_WK_TIME_TERMDATE_0_X1) {
		SCHED_WK_TIME_TERMDATE_0_X1 = sCHED_WK_TIME_TERMDATE_0_X1;
	}
	public void setSCHED_WK_TIME_TERMDATE_1(String sCHED_WK_TIME_TERMDATE_1) {
		SCHED_WK_TIME_TERMDATE_1 = sCHED_WK_TIME_TERMDATE_1;
	}
	public void setSCHED_WK_TIME_TERMDATE_1_X1(String sCHED_WK_TIME_TERMDATE_1_X1) {
		SCHED_WK_TIME_TERMDATE_1_X1 = sCHED_WK_TIME_TERMDATE_1_X1;
	}
	public void setSCHED_WK_TIME_TERMDATE_2(String sCHED_WK_TIME_TERMDATE_2) {
		SCHED_WK_TIME_TERMDATE_2 = sCHED_WK_TIME_TERMDATE_2;
	}
	public void setSCHED_WK_TIME_TERMDATE_2_X1(String sCHED_WK_TIME_TERMDATE_2_X1) {
		SCHED_WK_TIME_TERMDATE_2_X1 = sCHED_WK_TIME_TERMDATE_2_X1;
	}
	public void setSCHED_WK_TIME_TERMDATE_3(String sCHED_WK_TIME_TERMDATE_3) {
		SCHED_WK_TIME_TERMDATE_3 = sCHED_WK_TIME_TERMDATE_3;
	}
	public void setSCHED_WK_TIME_TERMDATE_3_X1(String sCHED_WK_TIME_TERMDATE_3_X1) {
		SCHED_WK_TIME_TERMDATE_3_X1 = sCHED_WK_TIME_TERMDATE_3_X1;
	}
	public void setSCHED_WK_TIME_TERMDATE_4(String sCHED_WK_TIME_TERMDATE_4) {
		SCHED_WK_TIME_TERMDATE_4 = sCHED_WK_TIME_TERMDATE_4;
	}
	public void setSCHED_WK_TIME_TERMDATE_4_X1(String sCHED_WK_TIME_TERMDATE_4_X1) {
		SCHED_WK_TIME_TERMDATE_4_X1 = sCHED_WK_TIME_TERMDATE_4_X1;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setHEADER_USER_LOGON_ID_0(Stock.GetParameterValue("HEADER_USER_LOGON_ID_0"));
		this.setSCHED_WK_TIME_DAY_OF_WEEK_5(Stock.GetParameterValue("SCHED_WK_TIME_DAY_OF_WEEK_5"));
		this.setSCHED_WK_TIME_DAY_OF_WEEK_6(Stock.GetParameterValue("SCHED_WK_TIME_DAY_OF_WEEK_6"));
		this.setSCHED_WK_TIME_DAY_OF_WEEK_7(Stock.GetParameterValue("SCHED_WK_TIME_DAY_OF_WEEK_7"));
		this.setSCHED_WK_TIME_DAY_OF_WEEK_8(Stock.GetParameterValue("SCHED_WK_TIME_DAY_OF_WEEK_8"));
		this.setSCHED_WK_TIME_DAY_OF_WEEK_8_X1(Stock.GetParameterValue("SCHED_WK_TIME_DAY_OF_WEEK_8_X1"));
		this.setSCHED_WK_TIME_EFFDATE_0(Stock.GetParameterValue("SCHED_WK_TIME_EFFDATE_0"));
		this.setSCHED_WK_TIME_EFFDATE_5(Stock.GetParameterValue("SCHED_WK_TIME_EFFDATE_5"));
		this.setSCHED_WK_TIME_EFFDATE_6(Stock.GetParameterValue("SCHED_WK_TIME_EFFDATE_6"));
		this.setSCHED_WK_TIME_EFFDATE_7(Stock.GetParameterValue("SCHED_WK_TIME_EFFDATE_7"));
		this.setSCHED_WK_TIME_EFFDATE_8(Stock.GetParameterValue("SCHED_WK_TIME_EFFDATE_8"));
		this.setSCHED_WK_TIME_EFFDATE_8_X1(Stock.GetParameterValue("SCHED_WK_TIME_HOURS_8_X1"));
		this.setSCHED_WK_TIME_HOURS_0(Stock.GetParameterValue("SCHED_WK_TIME_HOURS_0"));
		this.setSCHED_WK_TIME_HOURS_0_X1(Stock.GetParameterValue("SCHED_WK_TIME_HOURS_0_X1"));
		this.setSCHED_WK_TIME_HOURS_5(Stock.GetParameterValue("SCHED_WK_TIME_HOURS_5"));
		this.setSCHED_WK_TIME_HOURS_6(Stock.GetParameterValue("SCHED_WK_TIME_HOURS_6"));
		this.setSCHED_WK_TIME_HOURS_7(Stock.GetParameterValue("SCHED_WK_TIME_HOURS_7"));
		this.setSCHED_WK_TIME_HOURS_8(Stock.GetParameterValue("SCHED_WK_TIME_HOURS_8"));
		this.setSCHED_WK_TIME_HOURS_8_X1(Stock.GetParameterValue("SCHED_WK_TIME_HOURS_8_X1"));
		this.setSCHED_WK_TIME_TERMDATE_0(Stock.GetParameterValue("SCHED_WK_TIME_TERMDATE_0"));
		this.setSCHED_WK_TIME_TERMDATE_0_X1(Stock.GetParameterValue("SCHED_WK_TIME_TERMDATE_0_X1"));
		this.setSCHED_WK_TIME_TERMDATE_1(Stock.GetParameterValue("SCHED_WK_TIME_TERMDATE_1"));
		this.setSCHED_WK_TIME_TERMDATE_1_X1(Stock.GetParameterValue("SCHED_WK_TIME_TERMDATE_1_X1"));
		this.setSCHED_WK_TIME_TERMDATE_2(Stock.GetParameterValue("SCHED_WK_TIME_TERMDATE_2"));
		this.setSCHED_WK_TIME_TERMDATE_2_X1(Stock.GetParameterValue("SCHED_WK_TIME_TERMDATE_2_X1"));
		this.setSCHED_WK_TIME_TERMDATE_3(Stock.GetParameterValue("SCHED_WK_TIME_TERMDATE_3"));
		this.setSCHED_WK_TIME_TERMDATE_3_X1(Stock.GetParameterValue("SCHED_WK_TIME_TERMDATE_3_X1"));
		this.setSCHED_WK_TIME_TERMDATE_4(Stock.GetParameterValue("SCHED_WK_TIME_TERMDATE_4"));
		this.setSCHED_WK_TIME_TERMDATE_4_X1(Stock.GetParameterValue("SCHED_WK_TIME_TERMDATE_4_X1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&HEADER_USER_LOGON_ID_0="+HEADER_USER_LOGON_ID_0+
				"&SCHED_WK_TIME_HOURS_0="+SCHED_WK_TIME_HOURS_0+"&SCHED_WK_TIME_HOURS_0_X1="+SCHED_WK_TIME_HOURS_0_X1+
				"&SCHED_WK_TIME_EFFDATE_0="+SCHED_WK_TIME_EFFDATE_0+"&SCHED_WK_TIME_DAY_OF_WEEK_5="+SCHED_WK_TIME_DAY_OF_WEEK_5+
				"&SCHED_WK_TIME_HOURS_5="+SCHED_WK_TIME_HOURS_5+"&SCHED_WK_TIME_EFFDATE_5="+SCHED_WK_TIME_EFFDATE_5+
				"&SCHED_WK_TIME_DAY_OF_WEEK_6="+SCHED_WK_TIME_DAY_OF_WEEK_6+"&SCHED_WK_TIME_HOURS_6="+SCHED_WK_TIME_HOURS_6+
				"&SCHED_WK_TIME_EFFDATE_6="+SCHED_WK_TIME_EFFDATE_6+"&SCHED_WK_TIME_DAY_OF_WEEK_7="+SCHED_WK_TIME_DAY_OF_WEEK_7+
				"&SCHED_WK_TIME_HOURS_7="+SCHED_WK_TIME_HOURS_7+"&SCHED_WK_TIME_EFFDATE_7="+SCHED_WK_TIME_EFFDATE_7+
				"&SCHED_WK_TIME_DAY_OF_WEEK_8="+SCHED_WK_TIME_DAY_OF_WEEK_8+"&SCHED_WK_TIME_HOURS_8="+SCHED_WK_TIME_HOURS_8+
				"&SCHED_WK_TIME_EFFDATE_8="+SCHED_WK_TIME_EFFDATE_8+"&SCHED_WK_TIME_DAY_OF_WEEK_8_X1="+SCHED_WK_TIME_DAY_OF_WEEK_8_X1+
				"&SCHED_WK_TIME_HOURS_8_X1="+SCHED_WK_TIME_HOURS_8_X1+"&SCHED_WK_TIME_EFFDATE_8_X1="+SCHED_WK_TIME_EFFDATE_8_X1+
				"&SCHED_WK_TIME_TERMDATE_0="+SCHED_WK_TIME_TERMDATE_0+"&SCHED_WK_TIME_TERMDATE_0_X1="+SCHED_WK_TIME_TERMDATE_0_X1+
				"&SCHED_WK_TIME_TERMDATE_1="+SCHED_WK_TIME_TERMDATE_1+"&SCHED_WK_TIME_TERMDATE_1_X1="+SCHED_WK_TIME_TERMDATE_1_X1+
				"&SCHED_WK_TIME_TERMDATE_2="+SCHED_WK_TIME_TERMDATE_2+"&SCHED_WK_TIME_TERMDATE_2_X1="+SCHED_WK_TIME_TERMDATE_2_X1+
				"&SCHED_WK_TIME_TERMDATE_3="+SCHED_WK_TIME_TERMDATE_3+"&SCHED_WK_TIME_TERMDATE_3_X1="+SCHED_WK_TIME_TERMDATE_3_X1+
				"&SCHED_WK_TIME_TERMDATE_4="+SCHED_WK_TIME_TERMDATE_4+"&SCHED_WK_TIME_TERMDATE_4_X1="+SCHED_WK_TIME_TERMDATE_4_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for MNSW maintain Schedule Work Time service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run MNSW service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run MNSW service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){	
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO,"Values from the Response","WORK DATE: "+doc.getElementsByTagName("HEADER_WK_DATE_0").item(0).getTextContent()+
					"\nDay OF THE WEEK: "+doc.getElementsByTagName("SCHED_WK_TIME_DAY_OF_WEEK_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForMNSWMaintain")[1],  this.HEADER_USER_LOGON_ID_0 );
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.INFO, "Validating Records exists in Database for Maintaining Employee Schedule Worktime\nTable Name: SCHED_WK_TIME", "Records exists for Account Transaction\nTable Name: WORK_CONV_PART", false);
			while(queryResultSet.next()){
			Reporter.logEvent(Status.INFO, "Values From Database: ", "USER NAME: "+queryResultSet.getString("USER_LOGON_ID")+					
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+					
					"\nDAY_OF_WEEK: "+queryResultSet.getString("DAY_OF_WEEK")+
					"\nTERM_DATE: "+queryResultSet.getString("TERMDATE"), false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database for Maintaining Employee Schedule Worktime", "No records exists in Database", false);
		}
		
	}
}
