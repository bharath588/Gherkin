package pageobject.INTR;

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

public class INTR_Manually_Create_Investment_Trade {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GQ19INTR_Manually_Create_Investment_Trade";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INTR_BLOCK1_GA_0;
	String INTR_BLOCK1_REASON_0;
	String param27072;
	String INTR_BLOCK2_MARK_CB_3;
	String ACTIVITY_TYPE_LOV;
	String INTR_BLOCK2_AMOUNT_3;
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
	public void setINTR_BLOCK1_GA_0(String iNTR_BLOCK1_GA_0) {
		INTR_BLOCK1_GA_0 = iNTR_BLOCK1_GA_0;
	}
	public void setINTR_BLOCK1_REASON_0(String iNTR_BLOCK1_REASON_0) {
		INTR_BLOCK1_REASON_0 = iNTR_BLOCK1_REASON_0;
	}
	public void setParam27072(String param27072) {
		this.param27072 = param27072;
	}
	public void setINTR_BLOCK2_MARK_CB_3(String iNTR_BLOCK2_MARK_CB_3) {
		INTR_BLOCK2_MARK_CB_3 = iNTR_BLOCK2_MARK_CB_3;
	}
	public void setACTIVITY_TYPE_LOV(String aCTIVITY_TYPE_LOV) {
		ACTIVITY_TYPE_LOV = aCTIVITY_TYPE_LOV;
	}
	public void setINTR_BLOCK2_AMOUNT_3(String iNTR_BLOCK2_AMOUNT_3) {
		INTR_BLOCK2_AMOUNT_3 = iNTR_BLOCK2_AMOUNT_3;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINTR_BLOCK1_GA_0(Stock.GetParameterValue("INTR_BLOCK1_GA_0"));
		this.setINTR_BLOCK1_REASON_0(Stock.GetParameterValue("INTR_BLOCK1_REASON_0"));
		this.setINTR_BLOCK2_AMOUNT_3(Stock.GetParameterValue("INTR_BLOCK2_AMOUNT_3"));
		this.setINTR_BLOCK2_MARK_CB_3(Stock.GetParameterValue("INTR_BLOCK2_MARK_CB_3"));
		this.setParam27072(Stock.GetParameterValue("param27072"));
		this.setACTIVITY_TYPE_LOV(Stock.GetParameterValue("ACTIVITY_TYPE_LOV"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_DATABASE="+LOGON_DATABASE+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&INTR_BLOCK1_GA_0="+INTR_BLOCK1_GA_0+
				"&INTR_BLOCK1_REASON_0="+INTR_BLOCK1_REASON_0+
				"&param27072="+param27072+
				"&INTR_BLOCK2_MARK_CB_3="+INTR_BLOCK2_MARK_CB_3+
				"&ACTIVITY_TYPE_LOV="+ACTIVITY_TYPE_LOV+
				"&INTR_BLOCK2_AMOUNT_3="+INTR_BLOCK2_AMOUNT_3+
				"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for INTR service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run INTR service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run INTR service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		
		} else {
//			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String ev_id1 = null;
		String ev_id2= null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForINTR1")[1], this.INTR_BLOCK1_GA_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record insertion into DB", "Records inserted into DB successfully\nTable Name: GA_INVEST_ACTY", false);
			while(queryResultSet.next()){
				ev_id1 = queryResultSet.getString("EV_ID");
				Reporter.logEvent(Status.INFO, "Values from DB", "SDIO_ID: "+queryResultSet.getString("SDIO_ID")+
						"\nINVEST_ID: "+queryResultSet.getString("INVEST_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nEV_ID: "+queryResultSet.getString("EV_ID")+
						"\n", false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record insertion into DB", "Records inserted into DB successfully\nTable Name: GA_INVEST_ACTY", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForINTR2")[1], this.INTR_BLOCK1_GA_0, ev_id1);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record insertion into DB", "Records inserted into DB successfully\nTable Name: EVENT", false);
			while(queryResultSet.next()){
				ev_id2 = queryResultSet.getString("ID");
				Reporter.logEvent(Status.INFO, "Values from DB", "EV_ID: "+queryResultSet.getString("ID")+
						"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+
						"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME")+
						"\n", false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record insertion into DB", "Records not inserted into DB \nTable Name: EVENT", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForINTR3")[1], ev_id2);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record insertion into DB", "Records inserted into DB successfully\nTable Name: ACCTG_TXN_ACTY", false);
			while(queryResultSet.next()){
				
				Reporter.logEvent(Status.INFO, "Values from DB", "ID: "+queryResultSet.getString("ID")+
						"\nARS_NAME: "+queryResultSet.getString("ARS_NAME")+
						"\nSDIO_ID: "+queryResultSet.getString("SDIO_ID")+
						"\nGA_ID: "+queryResultSet.getString("GA_ID")+
						"\n", false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record insertion into DB", "Records not inserted into DB \nTable Name: ACCTG_TXN_ACTY", false);
		}
	}
	
	public void resetData() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForINTRReset")[1], this.INTR_BLOCK1_GA_0);
		Reporter.logEvent(Status.INFO, "Data has been reset", "Previous Records are deleted", false);
		
	}
}