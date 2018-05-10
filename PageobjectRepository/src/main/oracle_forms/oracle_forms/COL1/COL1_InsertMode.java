package pageobject.COL1;

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

public class COL1_InsertMode {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/COL1_INSERTMODE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String LDGR1_CODE_0;
	String LDGR1_NAME_0;
	String COLD1_PC_CODE_0;
	String COLD1_PACC_FILE_0;
	
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
	public void setLDGR1_CODE_0(String lDGR1_CODE_0) {
		LDGR1_CODE_0 = lDGR1_CODE_0;
	}
	public void setLDGR1_NAME_0(String lDGR1_NAME_0) {
		LDGR1_NAME_0 = lDGR1_NAME_0;
	}
	public void setCOLD1_PC_CODE_0(String cOLD1_PC_CODE_0) {
		COLD1_PC_CODE_0 = cOLD1_PC_CODE_0;
	}
	public void setCOLD1_PACC_FILE_0(String cOLD1_PACC_FILE_0) {
		COLD1_PACC_FILE_0 = cOLD1_PACC_FILE_0;
	}
	
	public String setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCOLD1_PACC_FILE_0(Stock.GetParameterValue("COLD1_PACC_FILE_0"));
		this.setCOLD1_PC_CODE_0(Stock.GetParameterValue("COLD1_PC_CODE_0"));
		this.setLDGR1_CODE_0(Stock.GetParameterValue("LDGR1_CODE_0"));
		this.setLDGR1_NAME_0(Stock.GetParameterValue("LDGR1_NAME_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&LDGR1_CODE_0="+LDGR1_CODE_0+"&LDGR1_NAME_0="+LDGR1_NAME_0+"&COLD1_PC_CODE_0="+COLD1_PC_CODE_0+"&COLD1_PACC_FILE_0="+COLD1_PACC_FILE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for COL1 service", this.queryString.replaceAll("&", "\n"), false);
		return this.LDGR1_CODE_0;
		
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
			Reporter.logEvent(Status.PASS, "Run COL1 service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run COL1 service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String ldgr_code = this.LDGR1_CODE_0;
				
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCOL1Insert1")[1],  ldgr_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking if Database records are inserted or not", "Records inserted in Database\nTable Name: LEDGER", false);
			while(queryResultSet.next()){
				
				Reporter.logEvent(Status.INFO, "Values from Database", "CODE: "+queryResultSet.getString("CODE")+
						"\nNAME: "+queryResultSet.getString("NAME"), false);
			}
		}	
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCOL1Insert2")[1],  ldgr_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking if Database records are inserted or not", "Records inserted in Database\nTable Name: CO_LEDGER", false);
			while(queryResultSet.next()){
				
				Reporter.logEvent(Status.INFO, "Values from Database", "CODE: "+queryResultSet.getString("LDGR_CODE")+
						"\nPC_CODE: "+queryResultSet.getString("PC_CODE")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nPACC_FILE: "+queryResultSet.getString("PACC_FILE"), false);
			}
			
		}
	}
	
	public void FlushData(){
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCOL1Insert1Delete1")[1], this.LDGR1_CODE_0 );
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCOL1Insert2Delete2")[1], this.LDGR1_CODE_0);
	}
	
}
