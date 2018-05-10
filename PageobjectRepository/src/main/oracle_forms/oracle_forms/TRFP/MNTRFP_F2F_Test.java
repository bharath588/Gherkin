package pageobject.TRFP;

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

public class MNTRFP_F2F_Test {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MNTRFP_F_2F_test";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String param27018;
	String IND1_SSN_0;
	String IND1_GC_ID_0;
	String FROM_DETL_REQ_PERCENT_0;
	String TO_DETL_TO_REQ_PERCENT_0;
	String NAVMENU_OPTION_0;
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
	public void setParam27018(String param27018) {
		this.param27018 = param27018;
	}
	public void setIND1_SSN_0(String iND1_SSN_0) {
		IND1_SSN_0 = iND1_SSN_0;
	}
	public void setIND1_GC_ID_0(String iND1_GC_ID_0) {
		IND1_GC_ID_0 = iND1_GC_ID_0;
	}
	public void setFROM_DETL_REQ_PERCENT_0(String fROM_DETL_REQ_PERCENT_0) {
		FROM_DETL_REQ_PERCENT_0 = fROM_DETL_REQ_PERCENT_0;
	}
	public void setTO_DETL_TO_REQ_PERCENT_0(String tO_DETL_TO_REQ_PERCENT_0) {
		TO_DETL_TO_REQ_PERCENT_0 = tO_DETL_TO_REQ_PERCENT_0;
	}
	public void setNAVMENU_OPTION_0(String nAVMENU_OPTION_0) {
		NAVMENU_OPTION_0 = nAVMENU_OPTION_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setParam27018(Stock.GetParameterValue("param27018"));
		 this.setIND1_GC_ID_0(Stock.GetParameterValue("IND1_GC_ID_0"));
		 this.setIND1_SSN_0(Stock.GetParameterValue("IND1_SSN_0"));
		 this.setFROM_DETL_REQ_PERCENT_0(Stock.GetParameterValue("FROM_DETL_REQ_PERCENT_0"));
		 this.setTO_DETL_TO_REQ_PERCENT_0(Stock.GetParameterValue("TO_DETL_TO_REQ_PERCENT_0"));
		 this.setNAVMENU_OPTION_0(Stock.GetParameterValue("NAVMENU_OPTION_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&IND1_SSN_0="+IND1_SSN_0+"&IND1_GC_ID_0="+IND1_GC_ID_0+
				 "&FROM_DETL_REQ_PERCENT_0="+FROM_DETL_REQ_PERCENT_0+
				 "&TO_DETL_TO_REQ_PERCENT_0="+TO_DETL_TO_REQ_PERCENT_0+
				 "&NAVMENU_OPTION_0="+NAVMENU_OPTION_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for TRFP service", this.queryString.replaceAll("&", "\n"), false);
		 
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
				Reporter.logEvent(Status.PASS, "Run  TRFP service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run TRFP service", "Running Failed:", false);
			}
			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);//11
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void resetData() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTRFPReset1")[1], this.IND1_SSN_0);
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTRFPReset2")[1], this.IND1_SSN_0);
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTRFP1")[1], this.IND1_SSN_0);
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
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTRFP2")[1], this.IND1_SSN_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Event generated in Database", "Event generated in DB\nTableName: TRF_DETL", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "TFBA_ID: "+queryResultSet.getString("TFBA_ID")+
						"\nSDIO_ID: "+queryResultSet.getString("SDIO_ID")+
						"\nSTATUS_CODE: "+queryResultSet.getString("STATUS_CODE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Event generated in Database", "Event is not generated in DB\nTableName: TRF_DETL", false);
		}
	}
}