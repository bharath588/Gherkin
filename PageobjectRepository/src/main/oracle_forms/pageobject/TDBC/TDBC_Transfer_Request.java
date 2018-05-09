package pageobject.TDBC;

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

public class TDBC_Transfer_Request {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/TDBC_Transfer_Request";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String Event_ID;
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String TRF_REQ_SSN_0;
	String TRF_REQ_GC_ID_0;
	String TRF_REQ_GA_ID_0;
	String TRF_REQ_EFFDATE_0;
	String TRF_REQ_TRF_TYPE_CODE_0;
	String param9206;
	String TRF_FROM_ELEMENT_REQ_PERCENT_0;
	String TRF_TO_ELEMENT_SET_NUMBER_0;
	String param9209;
	String TO_SDMT_CODE;
	String TRF_TO_ELEMENT_REQ_PERCENT_0;
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
	public void setTRF_REQ_SSN_0(String tRF_REQ_SSN_0) {
		TRF_REQ_SSN_0 = tRF_REQ_SSN_0;
	}
	public void setTRF_REQ_GC_ID_0(String tRF_REQ_GC_ID_0) {
		TRF_REQ_GC_ID_0 = tRF_REQ_GC_ID_0;
	}
	public void setTRF_REQ_GA_ID_0(String tRF_REQ_GA_ID_0) {
		TRF_REQ_GA_ID_0 = tRF_REQ_GA_ID_0;
	}
	public void setTRF_REQ_EFFDATE_0(String tRF_REQ_EFFDATE_0) {
		TRF_REQ_EFFDATE_0 = tRF_REQ_EFFDATE_0;
	}
	public void setTRF_REQ_TRF_TYPE_CODE_0(String tRF_REQ_TRF_TYPE_CODE_0) {
		TRF_REQ_TRF_TYPE_CODE_0 = tRF_REQ_TRF_TYPE_CODE_0;
	}
	public void setParam9206(String param9206) {
		this.param9206 = param9206;
	}
	public void setTRF_FROM_ELEMENT_REQ_PERCENT_0(
			String tRF_FROM_ELEMENT_REQ_PERCENT_0) {
		TRF_FROM_ELEMENT_REQ_PERCENT_0 = tRF_FROM_ELEMENT_REQ_PERCENT_0;
	}
	public void setTRF_TO_ELEMENT_SET_NUMBER_0(String tRF_TO_ELEMENT_SET_NUMBER_0) {
		TRF_TO_ELEMENT_SET_NUMBER_0 = tRF_TO_ELEMENT_SET_NUMBER_0;
	}
	public void setParam9209(String param9209) {
		this.param9209 = param9209;
	}
	public void setTO_SDMT_CODE(String tO_SDMT_CODE) {
		TO_SDMT_CODE = tO_SDMT_CODE;
	}
	public void setTRF_TO_ELEMENT_REQ_PERCENT_0(String tRF_TO_ELEMENT_REQ_PERCENT_0) {
		TRF_TO_ELEMENT_REQ_PERCENT_0 = tRF_TO_ELEMENT_REQ_PERCENT_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setParam9206(Stock.GetParameterValue("param9206"));
		 this.setParam9209(Stock.GetParameterValue("param9209"));
		 this.setTO_SDMT_CODE(Stock.GetParameterValue("TO_SDMT_CODE"));
		 this.setTRF_FROM_ELEMENT_REQ_PERCENT_0(Stock.GetParameterValue("TRF_FROM_ELEMENT_REQ_PERCENT_0"));
		 this.setTRF_REQ_EFFDATE_0(Stock.GetParameterValue("TRF_REQ_EFFDATE_0"));
		 this.setTRF_REQ_GA_ID_0(Stock.GetParameterValue("TRF_REQ_GA_ID_0"));
		 this.setTRF_REQ_GC_ID_0(Stock.GetParameterValue("TRF_REQ_GC_ID_0"));
		 this.setTRF_REQ_SSN_0(Stock.GetParameterValue("TRF_REQ_SSN_0"));
		 this.setTRF_REQ_TRF_TYPE_CODE_0(Stock.GetParameterValue("TRF_REQ_TRF_TYPE_CODE_0"));
		 this.setTRF_TO_ELEMENT_REQ_PERCENT_0(Stock.GetParameterValue("TRF_TO_ELEMENT_REQ_PERCENT_0"));
		 this.setTRF_TO_ELEMENT_SET_NUMBER_0(Stock.GetParameterValue("TRF_TO_ELEMENT_SET_NUMBER_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&TRF_REQ_SSN_0="+TRF_REQ_SSN_0+
				 "&TRF_REQ_GC_ID_0="+TRF_REQ_GC_ID_0+
				 "&TRF_REQ_GA_ID_0="+TRF_REQ_GA_ID_0+
				 "&TRF_REQ_EFFDATE_0="+TRF_REQ_EFFDATE_0+
				 "&TRF_REQ_TRF_TYPE_CODE_0="+TRF_REQ_TRF_TYPE_CODE_0+
				 "&param9206="+param9206+
				 "&TRF_FROM_ELEMENT_REQ_PERCENT_0="+TRF_FROM_ELEMENT_REQ_PERCENT_0+
				 "&TRF_TO_ELEMENT_SET_NUMBER_0="+TRF_TO_ELEMENT_SET_NUMBER_0+
				 "&param9209="+param9209+
				 "&TO_SDMT_CODE="+TO_SDMT_CODE+
				 "&TRF_TO_ELEMENT_REQ_PERCENT_0="+TRF_TO_ELEMENT_REQ_PERCENT_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for TACT service", this.queryString.replaceAll("&", "\n"), false);
		 
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
				Reporter.logEvent(Status.PASS, "Run  TDBC service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run TDBC service", "Running Failed:", false);
			}
			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);//11
			String ev_id = doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent();
			ev_id = ev_id.substring(ev_id.length()-11);
			System.out.println(ev_id.replaceAll("[^0-9]", " ").trim());
			this.Event_ID = ev_id.replaceAll("[^0-9]", " ").trim();
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTDBC1")[1], this.Event_ID);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Event generated in Database", "Event generated in DB\nTableName: EVENT", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "ID: "+queryResultSet.getString("ID")+
						"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+
						"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Event generated in Database", "Event is not generated in DB\nTableName: EVENT", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTDBC2")[1], this.Event_ID);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Event generated in Database", "Event generated in DB\nTableName: TRF_BASIC", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "ID: "+queryResultSet.getString("ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nEV_ID: "+queryResultSet.getString("EV_ID"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Event generated in Database", "Event is not generated in DB\nTableName: TRF_BASIC", false);
		}
	}
}
