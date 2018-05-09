package pageobject.WHEN;

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

public class WHEN_Create_Std_Level_Rule {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/WHEN_Create_Std_Level_Rule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String STD_RL_ID_1;
	String param9130;
	String STD_RL_DESCR_1;
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
	public void setSTD_RL_ID_1(String sTD_RL_ID_1) {
		STD_RL_ID_1 = sTD_RL_ID_1;
	}
	public void setParam9130(String param9130) {
		this.param9130 = param9130;
	}
	public void setSTD_RL_DESCR_1(String sTD_RL_DESCR_1) {
		STD_RL_DESCR_1 = sTD_RL_DESCR_1;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setSTD_RL_DESCR_1(Stock.GetParameterValue("STD_RL_DESCR_1"));
		this.setParam9130(Stock.GetParameterValue("param9130"));
		this.setSTD_RL_ID_1(Stock.GetParameterValue("STD_RL_ID_1"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
				+"&STD_RL_ID_1="+STD_RL_ID_1+"&param9130="+param9130+"&STD_RL_DESCR_1="+STD_RL_DESCR_1
				+"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for WHEN service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run WHEN service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run WHEN service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","StatusBarMessages: "+doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent()+
					"\nSTD_RL_ID_0: "+doc.getElementsByTagName("STD_RL_ID_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase() throws SQLException{
		String descr = this.STD_RL_DESCR_1;
		String descr_db =  null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForWHEN")[1], this.STD_RL_ID_1);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: STD_RULE", false);
			while(queryResultSet.next()){		
				descr_db = queryResultSet.getString("DESCR");
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ID: "+queryResultSet.getString("ID")+
					"\nRULE_TYPE: "+queryResultSet.getString("RULE_TYPE")+
					"\nDESCR: "+queryResultSet.getString("DESCR"), false);
			}
			if(descr.equalsIgnoreCase(descr_db)){
				Reporter.logEvent(Status.PASS, "Comaparing and validating DESCR from response and DB", "Both the values are same as expected output"+
			"\nFrom Response: "+descr+"\nFrom DB: "+descr_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comaparing and validating DESCR from response and DB", "Both the values are not same"+
						"\nFrom Response: "+descr+"\nFrom DB: "+descr_db, false);
			}
		}	
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
	public void flushData() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForWHENDel")[1], this.STD_RL_ID_1);
	}
}
