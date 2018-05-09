package pageobject.ETMT;

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

public class ETMT_Create_Event_Step {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ETMT_Create_Event_Step";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String EVTY1_CODE_0;
	String EVTY1_DESCR_0;
	String EVTY1_SJTY_CODE_LOV2;
	String EVTY1_SUB_SJTY_CO_LOV3;
	String EVTY1_CREATION_SO_LOV5;
	String SETY1_CODE_0 ;
	String SETY1_ORDER_NBR_0;
	String SETY1_LOG_ONCE_IND_0;
	String SETY1_DESCR_0;
	
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
	public void setEVTY1_CODE_0(String eVTY1_CODE_0) {
		EVTY1_CODE_0 = eVTY1_CODE_0;
	}
	public void setEVTY1_DESCR_0(String eVTY1_DESCR_0) {
		EVTY1_DESCR_0 = eVTY1_DESCR_0;
	}
	public void setEVTY1_SJTY_CODE_LOV2(String eVTY1_SJTY_CODE_LOV2) {
		EVTY1_SJTY_CODE_LOV2 = eVTY1_SJTY_CODE_LOV2;
	}
	public void setEVTY1_SUB_SJTY_CO_LOV3(String eVTY1_SUB_SJTY_CO_LOV3) {
		EVTY1_SUB_SJTY_CO_LOV3 = eVTY1_SUB_SJTY_CO_LOV3;
	}
	public void setEVTY1_CREATION_SO_LOV5(String eVTY1_CREATION_SO_LOV5) {
		EVTY1_CREATION_SO_LOV5 = eVTY1_CREATION_SO_LOV5;
	}
	public void setSETY1_CODE_0(String sETY1_CODE_0) {
		SETY1_CODE_0 = sETY1_CODE_0;
	}
	public void setSETY1_ORDER_NBR_0(String sETY1_ORDER_NBR_0) {
		SETY1_ORDER_NBR_0 = sETY1_ORDER_NBR_0;
	}
	public void setSETY1_LOG_ONCE_IND_0(String sETY1_LOG_ONCE_IND_0) {
		SETY1_LOG_ONCE_IND_0 = sETY1_LOG_ONCE_IND_0;
	}
	public void setSETY1_DESCR_0(String sETY1_DESCR_0) {
		SETY1_DESCR_0 = sETY1_DESCR_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setEVTY1_CODE_0(Stock.GetParameterValue("EVTY1_CODE_0"));
		this.setEVTY1_CREATION_SO_LOV5(Stock.GetParameterValue("EVTY1_CREATION_SO_LOV5"));
		this.setEVTY1_DESCR_0(Stock.GetParameterValue("EVTY1_DESCR_0"));
		this.setEVTY1_SJTY_CODE_LOV2(Stock.GetParameterValue("EVTY1_SJTY_CODE_LOV2"));
		this.setEVTY1_SUB_SJTY_CO_LOV3(Stock.GetParameterValue("EVTY1_SUB_SJTY_CO_LOV3"));
		this.setSETY1_CODE_0(Stock.GetParameterValue("SETY1_CODE_0"));
		this.setSETY1_DESCR_0(Stock.GetParameterValue("SETY1_DESCR_0"));
		this.setSETY1_LOG_ONCE_IND_0(Stock.GetParameterValue("SETY1_LOG_ONCE_IND_0"));
		this.setSETY1_ORDER_NBR_0(Stock.GetParameterValue("SETY1_ORDER_NBR_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&EVTY1_CODE_0="+EVTY1_CODE_0+"&EVTY1_DESCR_0="+EVTY1_DESCR_0+"&EVTY1_SJTY_CODE_LOV2="+EVTY1_SJTY_CODE_LOV2+"&EVTY1_SUB_SJTY_CO_LOV3="+EVTY1_SUB_SJTY_CO_LOV3+
				"&EVTY1_CREATION_SO_LOV5="+EVTY1_CREATION_SO_LOV5+"&SETY1_CODE_0="+SETY1_CODE_0+"&SETY1_ORDER_NBR_0="+SETY1_ORDER_NBR_0+
				"&SETY1_LOG_ONCE_IND_0="+SETY1_LOG_ONCE_IND_0+"&SETY1_DESCR_0="+SETY1_DESCR_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for ETMT service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run ETMT service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ETMT service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
		//	Reporter.logEvent(Status.PASS, "Capturing Response after Execution", "Response:\n"+doc.getDocumentElement(), false);
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "EVENT_CODE: "+ doc.getElementsByTagName("CFG_CONTROL_CODE_P2_0").item(0).getTextContent()+
					"\nEVENT DESCRIPTION: "+ doc.getElementsByTagName("CFG_CONTROL_DESCR_P2_0").item(0).getTextContent()+					
					"\nEVENT EFFDATE: "+ doc.getElementsByTagName("EVTY1_EFFDATE_0").item(0).getTextContent()+
					
					"\nSTEP CODE: "+ doc.getElementsByTagName("EVTY1_SJTY_CODE_0").item(0).getTextContent()+
					"\nSTEP EFFDATE: "+ doc.getElementsByTagName("SETY1_EFFDATE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String Ev_code_resp=null;
		String Ev_code_db=null;
		String step_code=null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForETMTevntType")[1],  this.EVTY1_CODE_0);
		Ev_code_resp = doc.getElementsByTagName("CFG_CONTROL_CODE_P2_0").item(0).getTextContent();
		if(queryResultSet.next()){
			Ev_code_db = queryResultSet.getString("CODE");
			Reporter.logEvent(Status.INFO, "Values From DATABASE:\n Table: EVENT_TYPE ", "CODE: "+queryResultSet.getString("CODE")+
					"\nDESCR: "+queryResultSet.getString("DESCR")+					
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nSJTY CODE: "+queryResultSet.getString("SJTY_CODE"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForETMTstepType")[1],  this.SETY1_CODE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
		while(queryResultSet.next()){
			step_code = queryResultSet.getString("CODE");
		Reporter.logEvent(Status.INFO, "Values From DATABASE:\n Table: STEP_TYPE ", "CODE: "+queryResultSet.getString("CODE")+
				"\nDESCR CODE: "+queryResultSet.getString("DESCR")+					
				"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
				"\nLOG ONCE IND: "+queryResultSet.getString("LOG_ONCE_IND")+
				"\nMAND IND: "+queryResultSet.getString("MAND_IND"), false);
		}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}	
		if(Ev_code_resp.equalsIgnoreCase(Ev_code_db)&&this.SETY1_CODE_0.equalsIgnoreCase(step_code)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Event Code from Response and Database", "Both the values are same as Expected"+
					"\nCODE: "+"From Response: "+Ev_code_resp+"\nFrom Database: "+Ev_code_db, false);
			Reporter.logEvent(Status.PASS, "Comparing and Validating Step Code from Response and Database", "Both the values are same as Expected"+
					"\nCODE: "+"From Response: "+this.SETY1_CODE_0+"\nFrom Database: "+step_code, false);
		}
		
	}
}
