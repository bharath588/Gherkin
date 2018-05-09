package pageobject.DSRQ;

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

public class DSRQ_Display_Disb_Req_Info {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DSRQ_Display_Disbursement_Request_Info";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String IND_ONLY_HEADER_SSN_0;
	String EVNT_LOV;
	
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
	public void setIND_ONLY_HEADER_SSN_0(String iND_ONLY_HEADER_SSN_0) {
		IND_ONLY_HEADER_SSN_0 = iND_ONLY_HEADER_SSN_0;
	}
	public void setEVNT_LOV(String eVNT_LOV) {
		EVNT_LOV = eVNT_LOV;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setEVNT_LOV(Stock.GetParameterValue("EVNT_LOV"));
		this.setIND_ONLY_HEADER_SSN_0(Stock.GetParameterValue("IND_ONLY_HEADER_SSN_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&IND_ONLY_HEADER_SSN_0="+IND_ONLY_HEADER_SSN_0+"&EVNT_LOV="+EVNT_LOV+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for DSRQ service", this.queryString.replaceAll("&", "\n"), false);
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
			//System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run DSRQ service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DSRQ service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "REQ_EV_ID: "+doc.getElementsByTagName("DISB_REQ_EV_ID_0").item(0).getTextContent()+
					"\nREQ_RECV_SSN: "+doc.getElementsByTagName("DISB_REQ_RECVR_SSN_0").item(0).getTextContent()+
					"\nREQ_GA_ID: "+doc.getElementsByTagName("DISB_REQ_VESTING_GA_ID_0").item(0).getTextContent(), false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String ev_id = doc.getElementsByTagName("DISB_REQ_EV_ID_0").item(0).getTextContent();
		String disb_id = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDSRQ1")[1], ev_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: DISB_REQ",false);
			while(queryResultSet.next()){
			disb_id = queryResultSet.getString("ID");
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ID: "+ queryResultSet.getString("ID")+
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nEV_ID: "+queryResultSet.getString("EV_ID")+
					"\nIND_ID: "+queryResultSet.getString("IND_ID")+
					"\nGC_ID: "+queryResultSet.getString("GC_ID")+
					"\nDSMD_CODE: "+queryResultSet.getString("DSMD_CODE"), false);
			}
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDSRQ2")[1], disb_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: DISB_REQ_ELEMENT",false);
			while(queryResultSet.next()){
			disb_id = queryResultSet.getString("DBREQ_ID");
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ID: "+ queryResultSet.getString("DBREQ_ID")+
					"\nGA_ID: "+queryResultSet.getString("GA_ID")+
					"\nDISB_PROCESS_MTHD: "+queryResultSet.getString("DISB_PROCESS_MTHD")+
					"\nTAX_REASON_CODE: "+queryResultSet.getString("TAX_REASON_CODE"), false);
			}
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
		}	
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDSRQ3")[1], disb_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: DISB_REQ_MONEY_SOURCE",false);
			while(queryResultSet.next()){
			disb_id = queryResultSet.getString("DBREQ_ID");
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ID: "+ queryResultSet.getString("DBREQ_ID")+
					"\nGA_ID: "+queryResultSet.getString("GA_ID")+
					"\nSDMT_CODE: "+queryResultSet.getString("SDMT_CODE"), false);
			}
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
		}	
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDSRQ4")[1], disb_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: DISB_REQ_PAYOUT",false);
			while(queryResultSet.next()){
			disb_id = queryResultSet.getString("DBREQ_ID");
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ID: "+ queryResultSet.getString("DBREQ_ID")+
					"\nGA_ID: "+queryResultSet.getString("GA_ID")+
					"\nREQ_AMT: "+queryResultSet.getString("REQ_AMT"), false);
			}
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
		}	
		
	}
	
}
