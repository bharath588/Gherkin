package pageobject.STRT;

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

public class STRT_Edit_Investment_Option_Rate {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STRT_Edit_Investment_Option_Rate";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet2 = null;
	public ResultSet queryResultSet3 = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String DEPR1_N_D_SDIO_ID_LOV0;
	String DEPR1_N_D_START_D_LOV4;
	String DEPR1_N_D_PUGR_CO_LOV5;
	String IU_RATES_SDRTTY_CODE_LOV15;
	String IU_RATES_VALUE_0;
	
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
	public void setDEPR1_N_D_SDIO_ID_LOV0(String dEPR1_N_D_SDIO_ID_LOV0) {
		DEPR1_N_D_SDIO_ID_LOV0 = dEPR1_N_D_SDIO_ID_LOV0;
	}
	public void setDEPR1_N_D_START_D_LOV4(String dEPR1_N_D_START_D_LOV4) {
		DEPR1_N_D_START_D_LOV4 = dEPR1_N_D_START_D_LOV4;
	}
	public void setDEPR1_N_D_PUGR_CO_LOV5(String dEPR1_N_D_PUGR_CO_LOV5) {
		DEPR1_N_D_PUGR_CO_LOV5 = dEPR1_N_D_PUGR_CO_LOV5;
	}
	public void setIU_RATES_SDRTTY_CODE_LOV15(String iU_RATES_SDRTTY_CODE_LOV15) {
		IU_RATES_SDRTTY_CODE_LOV15 = iU_RATES_SDRTTY_CODE_LOV15;
	}
	public void setIU_RATES_VALUE_0(String iU_RATES_VALUE_0) {
		IU_RATES_VALUE_0 = iU_RATES_VALUE_0;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));		
		this.setDEPR1_N_D_PUGR_CO_LOV5(Stock.GetParameterValue("DEPR1_N_D_PUGR_CO_LOV5"));
		this.setDEPR1_N_D_SDIO_ID_LOV0(Stock.GetParameterValue("DEPR1_N_D_SDIO_ID_LOV0"));
		this.setDEPR1_N_D_START_D_LOV4(Stock.GetParameterValue("DEPR1_N_D_START_D_LOV4"));
		this.setIU_RATES_SDRTTY_CODE_LOV15(Stock.GetParameterValue("IU_RATES_SDRTTY_CODE_LOV15"));
		this.setIU_RATES_VALUE_0(Stock.GetParameterValue("IU_RATES_VALUE_0"));
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&DEPR1_N_D_PUGR_CO_LOV5="+DEPR1_N_D_PUGR_CO_LOV5+"&DEPR1_N_D_SDIO_ID_LOV0="+DEPR1_N_D_SDIO_ID_LOV0+
				"&DEPR1_N_D_START_D_LOV4="+DEPR1_N_D_START_D_LOV4+"&IU_RATES_SDRTTY_CODE_LOV15="+IU_RATES_SDRTTY_CODE_LOV15+"&IU_RATES_VALUE_0="+IU_RATES_VALUE_0+
				"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for STRT service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run STRT service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STRT service", "Running Failed:", false);
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
		
	String sdio_id = doc.getElementsByTagName("DEPR1_N_D_SDIO_ID_0").item(0).getTextContent();
			queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTRTedit1")[1], sdio_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database\nExpected: Query should return more than 1 record", "Records exists in Database\nTable name: PROD_PRICE_LEVEL"+
					"Total number of Records: "+DB.getRecordSetCount(queryResultSet),false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}	
		
		queryResultSet2 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTRTedit2")[1],sdio_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database\nExpected: Query should return more than 1 record", "Records exists in Database\nTable name: PROD_PRICE_LEVEL"+
					"Total number of Records: "+DB.getRecordSetCount(queryResultSet),false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}	
		
		queryResultSet3 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTRTedit3")[1],sdio_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database\nExpected: Query should return more than 1 record", "Records exists in Database\nTable name: PROD_PRICE_LEVEL"+
					"Total number of Records: "+DB.getRecordSetCount(queryResultSet),false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}	
	}
	
	
}
