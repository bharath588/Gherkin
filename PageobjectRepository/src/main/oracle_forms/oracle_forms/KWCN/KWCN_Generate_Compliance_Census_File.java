package pageobject.KWCN;

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

public class KWCN_Generate_Compliance_Census_File {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/KWCN_Generate_Compliance_Census_File";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INP1_DISP_DFT_VALUE_0;
	String INP1_DISP_DFT_VALUE_1;
	String INP1_DISP_DFT_VALUE_2;
	String INP1_DISP_DFT_VALUE_3;
	String MO1_DISP_DFT_PRIN_CODE_0;
	String MO1_DISP_HOUR_0;
	String MO1_DISP_TIME_0;
	String MO1_TIME_IND_0;
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
	public void setINP1_DISP_DFT_VALUE_0(String iNP1_DISP_DFT_VALUE_0) {
		INP1_DISP_DFT_VALUE_0 = iNP1_DISP_DFT_VALUE_0;
	}
	public void setINP1_DISP_DFT_VALUE_1(String iNP1_DISP_DFT_VALUE_1) {
		INP1_DISP_DFT_VALUE_1 = iNP1_DISP_DFT_VALUE_1;
	}
	public void setINP1_DISP_DFT_VALUE_2(String iNP1_DISP_DFT_VALUE_2) {
		INP1_DISP_DFT_VALUE_2 = iNP1_DISP_DFT_VALUE_2;
	}
	public void setINP1_DISP_DFT_VALUE_3(String iNP1_DISP_DFT_VALUE_3) {
		INP1_DISP_DFT_VALUE_3 = iNP1_DISP_DFT_VALUE_3;
	}
	public void setMO1_DISP_DFT_PRIN_CODE_0(String mO1_DISP_DFT_PRIN_CODE_0) {
		MO1_DISP_DFT_PRIN_CODE_0 = mO1_DISP_DFT_PRIN_CODE_0;
	}
	public void setMO1_DISP_HOUR_0(String mO1_DISP_HOUR_0) {
		MO1_DISP_HOUR_0 = mO1_DISP_HOUR_0;
	}
	public void setMO1_DISP_TIME_0(String mO1_DISP_TIME_0) {
		MO1_DISP_TIME_0 = mO1_DISP_TIME_0;
	}
	public void setMO1_TIME_IND_0(String mO1_TIME_IND_0) {
		MO1_TIME_IND_0 = mO1_TIME_IND_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINP1_DISP_DFT_VALUE_0(Stock.GetParameterValue("INP1_DISP_DFT_VALUE_0"));
		this.setINP1_DISP_DFT_VALUE_1(Stock.GetParameterValue("INP1_DISP_DFT_VALUE_1"));
		this.setINP1_DISP_DFT_VALUE_2(Stock.GetParameterValue("INP1_DISP_DFT_VALUE_2"));
		this.setINP1_DISP_DFT_VALUE_3(Stock.GetParameterValue("INP1_DISP_DFT_VALUE_3"));
		this.setMO1_DISP_DFT_PRIN_CODE_0(Stock.GetParameterValue("MO1_DISP_DFT_PRIN_CODE_0"));
		this.setMO1_DISP_HOUR_0(Stock.GetParameterValue("MO1_DISP_HOUR_0"));
		this.setMO1_DISP_TIME_0(Stock.GetParameterValue("MO1_DISP_TIME_0"));
		this.setMO1_TIME_IND_0(Stock.GetParameterValue("MO1_TIME_IND_0"));
		
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&INP1_DISP_DFT_VALUE_0="+INP1_DISP_DFT_VALUE_0+"&INP1_DISP_DFT_VALUE_1="+INP1_DISP_DFT_VALUE_1+"&INP1_DISP_DFT_VALUE_2="+INP1_DISP_DFT_VALUE_2+"&INP1_DISP_DFT_VALUE_3="+INP1_DISP_DFT_VALUE_3+
				"&MO1_DISP_DFT_PRIN_CODE_0="+MO1_DISP_DFT_PRIN_CODE_0+"&MO1_DISP_HOUR_0="+MO1_DISP_HOUR_0+"&MO1_DISP_TIME_0="+MO1_DISP_TIME_0+"&MO1_TIME_IND_0="+MO1_TIME_IND_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for KWCN service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run KWCN service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run KWCN service", "Running Failed:", false);
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
	
	public void validateInDatabase()throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForKWCN1")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);			
			Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: USER_MENU_ACCESS", "Expected:\nUser is able to generate Compliance Census File "+
			"Records/Templates exists\nTotal number of records: "+DB.getRecordSetCount(queryResultSet), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForKWCN2")[1]);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
			while(queryResultSet.next()){			
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: TRANSACTION", "CODE: "+queryResultSet.getString("CODE")+
					"\nDESCR: "+queryResultSet.getString("DESCR")+
					"\nMENU_CODE: "+queryResultSet.getString("MENU_CODE")+
					"\nSHORT NAME: "+queryResultSet.getString("REPT_MOD_SHORT_NAME")
					, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForKWCN3")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: INPUT_PARM", "TXN_CODE: "+queryResultSet.getString("TXN_CODE")+
					"\nDATA TYPE: "+queryResultSet.getString("DATA_TYPE_CODE")+
					"\nPROMPT: "+queryResultSet.getString("PROMPT")					
					, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
}
