package pageobject.STIV;

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

public class STIV_Insert_New_Std_Investment {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GQ19STIV_Insert_New_Standard_Investment";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INVESTMENT_ID_0;
	String INVESTMENT_NAME_0;
	String INVESTMENT_PROV_COMPAN_LOV0;
	String INVESTMENT_FUND_TYPE_C_LOV1;
	String INVESTMENT_DIVIDEND_PA_LOV2;
	String INVESTMENT_OBJECTIVE_LOV3;
	String INVESTMENT_DIVIDEND_DE_LOV4;
	String SHARE_VALUE_NET_ASSET_VALUE_0;
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
	public void setINVESTMENT_ID_0(String iNVESTMENT_ID_0) {
		INVESTMENT_ID_0 = iNVESTMENT_ID_0;
	}
	public void setINVESTMENT_NAME_0(String iNVESTMENT_NAME_0) {
		INVESTMENT_NAME_0 = iNVESTMENT_NAME_0;
	}
	public void setINVESTMENT_PROV_COMPAN_LOV0(String iNVESTMENT_PROV_COMPAN_LOV0) {
		INVESTMENT_PROV_COMPAN_LOV0 = iNVESTMENT_PROV_COMPAN_LOV0;
	}
	public void setINVESTMENT_FUND_TYPE_C_LOV1(String iNVESTMENT_FUND_TYPE_C_LOV1) {
		INVESTMENT_FUND_TYPE_C_LOV1 = iNVESTMENT_FUND_TYPE_C_LOV1;
	}
	public void setINVESTMENT_DIVIDEND_PA_LOV2(String iNVESTMENT_DIVIDEND_PA_LOV2) {
		INVESTMENT_DIVIDEND_PA_LOV2 = iNVESTMENT_DIVIDEND_PA_LOV2;
	}
	public void setINVESTMENT_OBJECTIVE_LOV3(String iNVESTMENT_OBJECTIVE_LOV3) {
		INVESTMENT_OBJECTIVE_LOV3 = iNVESTMENT_OBJECTIVE_LOV3;
	}
	public void setINVESTMENT_DIVIDEND_DE_LOV4(String iNVESTMENT_DIVIDEND_DE_LOV4) {
		INVESTMENT_DIVIDEND_DE_LOV4 = iNVESTMENT_DIVIDEND_DE_LOV4;
	}
	public void setSHARE_VALUE_NET_ASSET_VALUE_0(
			String sHARE_VALUE_NET_ASSET_VALUE_0) {
		SHARE_VALUE_NET_ASSET_VALUE_0 = sHARE_VALUE_NET_ASSET_VALUE_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINVESTMENT_DIVIDEND_DE_LOV4(Stock.GetParameterValue("INVESTMENT_DIVIDEND_DE_LOV4"));
		this.setINVESTMENT_DIVIDEND_PA_LOV2(Stock.GetParameterValue("INVESTMENT_DIVIDEND_PA_LOV2"));
		this.setINVESTMENT_FUND_TYPE_C_LOV1(Stock.GetParameterValue("INVESTMENT_FUND_TYPE_C_LOV1"));
		this.setINVESTMENT_ID_0(Stock.GetParameterValue("INVESTMENT_ID_0"));
		this.setINVESTMENT_NAME_0(Stock.GetParameterValue("INVESTMENT_NAME_0"));
		this.setINVESTMENT_OBJECTIVE_LOV3(Stock.GetParameterValue("INVESTMENT_OBJECTIVE_LOV3"));
		this.setINVESTMENT_PROV_COMPAN_LOV0(Stock.GetParameterValue("INVESTMENT_PROV_COMPAN_LOV0"));
		this.setSHARE_VALUE_NET_ASSET_VALUE_0(Stock.GetParameterValue("SHARE_VALUE_NET_ASSET_VALUE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_DATABASE="+LOGON_DATABASE+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&INVESTMENT_ID_0="+INVESTMENT_ID_0+
				"&INVESTMENT_NAME_0="+INVESTMENT_NAME_0+
				"&INVESTMENT_PROV_COMPAN_LOV0="+INVESTMENT_PROV_COMPAN_LOV0+
				"&INVESTMENT_FUND_TYPE_C_LOV1="+INVESTMENT_FUND_TYPE_C_LOV1+
				"&INVESTMENT_DIVIDEND_PA_LOV2="+INVESTMENT_DIVIDEND_PA_LOV2+
				"&INVESTMENT_OBJECTIVE_LOV3="+INVESTMENT_OBJECTIVE_LOV3+
				"&INVESTMENT_DIVIDEND_DE_LOV4="+INVESTMENT_DIVIDEND_DE_LOV4+
				"&SHARE_VALUE_NET_ASSET_VALUE_0="+SHARE_VALUE_NET_ASSET_VALUE_0+				
				"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for STIV service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run STIV service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run STIV service", "Running Failed:", false);
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
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTIVInsert1")[1], this.INVESTMENT_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: INVESTMENT", "Records inserted into the Database", false);
			while(queryResultSet.next()){				
				Reporter.logEvent(Status.INFO, "Values from DB", "ID: "+queryResultSet.getString("ID")+
						"\nNAME: "+queryResultSet.getString("NAME")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nPROV_COMPANY: "+queryResultSet.getString("PROV_COMPANY"), false);	
			}			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTIVInsert2")[1], this.INVESTMENT_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: VESTING_COMPUTATION_DETL", "Records inserted into the Database", false);
			while(queryResultSet.next()){				
				Reporter.logEvent(Status.INFO, "Values from DB", "INVEST_ID: "+queryResultSet.getString("INVEST_ID")+
						"\nPREV_NET_ASSET_VALUE: "+queryResultSet.getString("PREV_NET_ASSET_VALUE")+
						"\nVALUATION_EFFDATE: "+queryResultSet.getString("VALUATION_EFFDATE")+
						"\nPREV_VALUATION_EFFDATE: "+queryResultSet.getString("PREV_VALUATION_EFFDATE"), false);	
			}			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
	
	public void setUpData() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTIVReset1")[1], this.INVESTMENT_ID_0);
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTIVReset2")[1], this.INVESTMENT_ID_0);
	}
}