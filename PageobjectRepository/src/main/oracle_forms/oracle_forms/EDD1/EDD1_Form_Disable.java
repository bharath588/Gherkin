package pageobject.EDD1;

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

public class EDD1_Form_Disable {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/EDD1_Form_Disable";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;	
	ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String LOV_GA_ID;
	String LOV_ACCESS_TYPE;
	String param27067;
	String param27067_X1;
	String param27067_X2;
	String param27067_X3;
	String param27067_X4;
	String param27067_X5;
	String param27067_X6;
	String param27067_X7;
	String param27067_X8;
	String MENU_ENABLE_4;
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
	public void setLOV_GA_ID(String lOV_GA_ID) {
		LOV_GA_ID = lOV_GA_ID;
	}
	public void setLOV_ACCESS_TYPE(String lOV_ACCESS_TYPE) {
		LOV_ACCESS_TYPE = lOV_ACCESS_TYPE;
	}
	public void setParam27067(String param27067) {
		this.param27067 = param27067;
	}
	public void setParam27067_X1(String param27067_X1) {
		this.param27067_X1 = param27067_X1;
	}
	public void setParam27067_X2(String param27067_X2) {
		this.param27067_X2 = param27067_X2;
	}
	public void setParam27067_X3(String param27067_X3) {
		this.param27067_X3 = param27067_X3;
	}
	public void setParam27067_X4(String param27067_X4) {
		this.param27067_X4 = param27067_X4;
	}
	public void setParam27067_X5(String param27067_X5) {
		this.param27067_X5 = param27067_X5;
	}
	public void setParam27067_X6(String param27067_X6) {
		this.param27067_X6 = param27067_X6;
	}
	public void setParam27067_X7(String param27067_X7) {
		this.param27067_X7 = param27067_X7;
	}
	public void setParam27067_X8(String param27067_X8) {
		this.param27067_X8 = param27067_X8;
	}
	public void setMENU_ENABLE_4(String mENU_ENABLE_4) {
		MENU_ENABLE_4 = mENU_ENABLE_4;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOV_ACCESS_TYPE(Stock.GetParameterValue("LOV_ACCESS_TYPE"));
		this.setLOV_GA_ID(Stock.GetParameterValue("LOV_GA_ID"));
		this.setMENU_ENABLE_4(Stock.GetParameterValue("MENU_ENABLE_4"));
		this.setParam27067(Stock.GetParameterValue("param27067"));
		this.setParam27067_X1(Stock.GetParameterValue("param27067_X1"));
		this.setParam27067_X2(Stock.GetParameterValue("param27067_X2"));
		this.setParam27067_X3(Stock.GetParameterValue("param27067_X3"));
		this.setParam27067_X4(Stock.GetParameterValue("param27067_X4"));
		this.setParam27067_X5(Stock.GetParameterValue("param27067_X5"));
		this.setParam27067_X6(Stock.GetParameterValue("param27067_X6"));
		this.setParam27067_X7(Stock.GetParameterValue("param27067_X7"));
		this.setParam27067_X8(Stock.GetParameterValue("param27067_X8"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&LOV_GA_ID="+LOV_GA_ID+"&LOV_ACCESS_TYPE="+LOV_ACCESS_TYPE+
				"&param27067="+param27067+"&param27067_X1="+param27067_X1+"&param27067_X2="+param27067_X2+"&param27067_X3="+param27067_X3+
				"&param27067_X4="+param27067_X4+"&param27067_X5="+param27067_X5+"&param27067_X6="+param27067_X6+
				"&param27067_X7="+param27067_X7+"&param27067_X8="+param27067_X8+
				"&MENU_ENABLE_4="+MENU_ENABLE_4+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for EDD1 Disable service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run EDD1 service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run EDD1 service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","CFG_CONTROL_GA_ID_0: "+doc.getElementsByTagName("CFG_CONTROL_GA_ID_0").item(0).getTextContent()+
					"\nCFG_CONTROL_GA_DESCR_0: "+doc.getElementsByTagName("CFG_CONTROL_GA_DESCR_0").item(0).getTextContent()+
					"\nCFG_CONTROL_ACCESS_TYPE_CODE_0: "+doc.getElementsByTagName("CFG_CONTROL_ACCESS_TYPE_CODE_0").item(0).getTextContent()+
					"\nMENU_ACTION_VALUE_0: "+doc.getElementsByTagName("MENU_ACTION_VALUE_0").item(0).getTextContent()+
					"\nMENU_DISPLAY_TEXT_0: "+doc.getElementsByTagName("MENU_DISPLAY_TEXT_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase() throws SQLException{
		String ga_id = doc.getElementsByTagName("CFG_CONTROL_GA_ID_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForEDD1Disbale")[1], ga_id, ga_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of records in DB", "Records exists in DB\nTableName: MENU", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB\nSTATUS should be enabled", "ID: "+queryResultSet.getString("ID")+
						"\nMASTER_MENU_ID: "+queryResultSet.getString("MASTER_MENU_ID")+
						"\nRULE_CODE: "+queryResultSet.getString("RULE_CODE")+
						"\nSTATUS: "+queryResultSet.getString("STATUS"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of records in DB", "Records doesn't exists in DB\nTableName: MENU", false);
		}
	}
	
}
