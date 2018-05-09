package pageobject.EDPI;

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

public class EDPI_Add_Event_Disb_Reason_PC_IRS_Rule {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/EDPI_Add_Event_Disb_Reason_PC_IRS_Rule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SELECT_EVENT_EVTY_CODE_0;
	String DISB_REASONS_AVAILABLE_0;
	String DISB_REASONS_AVAILABLE_0_X1;
	String DISB_REASONS_AVAILABLE_0_X2;
	String DISB_REASONS_SELECTED_0;
	String DISB_REASONS_AVAILABLE_0_X3;
	String PROV_CO_AVAILABLE_0;
	String PROV_CO_AVAILABLE_0_X1;
	String PROV_CO_AVAILABLE_0_X2;
	String PROV_CO_AVAILABLE_0_X3;
	String PROV_CO_AVAILABLE_0_X4;
	String PROV_CO_AVAILABLE_0_X5;
	
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
	public void setSELECT_EVENT_EVTY_CODE_0(String sELECT_EVENT_EVTY_CODE_0) {
		SELECT_EVENT_EVTY_CODE_0 = sELECT_EVENT_EVTY_CODE_0;
	}
	public void setDISB_REASONS_AVAILABLE_0(String dISB_REASONS_AVAILABLE_0) {
		DISB_REASONS_AVAILABLE_0 = dISB_REASONS_AVAILABLE_0;
	}
	public void setDISB_REASONS_AVAILABLE_0_X1(String dISB_REASONS_AVAILABLE_0_X1) {
		DISB_REASONS_AVAILABLE_0_X1 = dISB_REASONS_AVAILABLE_0_X1;
	}
	public void setDISB_REASONS_AVAILABLE_0_X2(String dISB_REASONS_AVAILABLE_0_X2) {
		DISB_REASONS_AVAILABLE_0_X2 = dISB_REASONS_AVAILABLE_0_X2;
	}
	public void setDISB_REASONS_SELECTED_0(String dISB_REASONS_SELECTED_0) {
		DISB_REASONS_SELECTED_0 = dISB_REASONS_SELECTED_0;
	}
	public void setDISB_REASONS_AVAILABLE_0_X3(String dISB_REASONS_AVAILABLE_0_X3) {
		DISB_REASONS_AVAILABLE_0_X3 = dISB_REASONS_AVAILABLE_0_X3;
	}
	public void setPROV_CO_AVAILABLE_0(String pROV_CO_AVAILABLE_0) {
		PROV_CO_AVAILABLE_0 = pROV_CO_AVAILABLE_0;
	}
	public void setPROV_CO_AVAILABLE_0_X1(String pROV_CO_AVAILABLE_0_X1) {
		PROV_CO_AVAILABLE_0_X1 = pROV_CO_AVAILABLE_0_X1;
	}
	public void setPROV_CO_AVAILABLE_0_X2(String pROV_CO_AVAILABLE_0_X2) {
		PROV_CO_AVAILABLE_0_X2 = pROV_CO_AVAILABLE_0_X2;
	}
	public void setPROV_CO_AVAILABLE_0_X3(String pROV_CO_AVAILABLE_0_X3) {
		PROV_CO_AVAILABLE_0_X3 = pROV_CO_AVAILABLE_0_X3;
	}
	public void setPROV_CO_AVAILABLE_0_X4(String pROV_CO_AVAILABLE_0_X4) {
		PROV_CO_AVAILABLE_0_X4 = pROV_CO_AVAILABLE_0_X4;
	}
	public void setPROV_CO_AVAILABLE_0_X5(String pROV_CO_AVAILABLE_0_X5) {
		PROV_CO_AVAILABLE_0_X5 = pROV_CO_AVAILABLE_0_X5;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));		
		this.setDISB_REASONS_AVAILABLE_0(Stock.GetParameterValue("DISB_REASONS_AVAILABLE_0"));
		this.setDISB_REASONS_AVAILABLE_0_X1(Stock.GetParameterValue("DISB_REASONS_AVAILABLE_0_X1"));
		this.setDISB_REASONS_AVAILABLE_0_X2(Stock.GetParameterValue("DISB_REASONS_AVAILABLE_0_X2"));
		this.setDISB_REASONS_AVAILABLE_0_X3(Stock.GetParameterValue("DISB_REASONS_AVAILABLE_0_X3"));
		this.setDISB_REASONS_SELECTED_0(Stock.GetParameterValue("DISB_REASONS_SELECTED_0"));
		this.setPROV_CO_AVAILABLE_0(Stock.GetParameterValue("PROV_CO_AVAILABLE_0"));
		this.setPROV_CO_AVAILABLE_0_X1(Stock.GetParameterValue("PROV_CO_AVAILABLE_0_X1"));
		this.setPROV_CO_AVAILABLE_0_X2(Stock.GetParameterValue("PROV_CO_AVAILABLE_0_X2"));
		this.setPROV_CO_AVAILABLE_0_X3(Stock.GetParameterValue("PROV_CO_AVAILABLE_0_X3"));
		this.setPROV_CO_AVAILABLE_0_X4(Stock.GetParameterValue("PROV_CO_AVAILABLE_0_X4"));
		this.setPROV_CO_AVAILABLE_0_X5(Stock.GetParameterValue("PROV_CO_AVAILABLE_0_X5"));
		this.setSELECT_EVENT_EVTY_CODE_0(Stock.GetParameterValue("SELECT_EVENT_EVTY_CODE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SELECT_EVENT_EVTY_CODE_0="+SELECT_EVENT_EVTY_CODE_0+"&DISB_REASONS_AVAILABLE_0="+DISB_REASONS_AVAILABLE_0+"&DISB_REASONS_AVAILABLE_0_X1="+DISB_REASONS_AVAILABLE_0_X1+
				"&DISB_REASONS_AVAILABLE_0_X2="+DISB_REASONS_AVAILABLE_0_X2+"&DISB_REASONS_SELECTED_0="+DISB_REASONS_SELECTED_0+"&DISB_REASONS_AVAILABLE_0_X3="+DISB_REASONS_AVAILABLE_0_X3+
				"&PROV_CO_AVAILABLE_0="+PROV_CO_AVAILABLE_0+"&PROV_CO_AVAILABLE_0_X1="+PROV_CO_AVAILABLE_0_X1+"&PROV_CO_AVAILABLE_0_X2="+PROV_CO_AVAILABLE_0_X2+
				"&PROV_CO_AVAILABLE_0_X3="+PROV_CO_AVAILABLE_0_X3+"&PROV_CO_AVAILABLE_0_X4="+PROV_CO_AVAILABLE_0_X4+"&PROV_CO_AVAILABLE_0_X5="+PROV_CO_AVAILABLE_0_X5+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for EDPI service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run EDPI service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run EDPI service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			/*Reporter.logEvent(Status.INFO, "Values from Response", "SAP_MAIN_DESCR: "+doc.getElementsByTagName("SAP_MAIN_DESCR_0").item(0).getTextContent()+
					"\nACCT_CODE: "+doc.getElementsByTagName("SAP_MAIN_SUB_GL_ACCT_CODE_0").item(0).getTextContent()+
					"\nLDGR_CODE: "+doc.getElementsByTagName("SAP_MAIN_SUB_LDGR_CODE_0").item(0).getTextContent()+
					"\nMAIN_CODE: "+doc.getElementsByTagName("SAP_MAIN_SUB_MAIN_CODE_0").item(0).getTextContent()+
					"\nSUB_CODE: "+doc.getElementsByTagName("SAP_SUB_CODE_0").item(0).getTextContent(), false);*/			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String evty_code = this.SELECT_EVENT_EVTY_CODE_0;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForEDPIAdd")[1], evty_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: EVTY#DSRS#PROVCO#IRS_RULE",false);
			Reporter.logEvent(Status.INFO, "Expected: Query should return more than one record", "Total number of record: "+DB.getRecordSetCount(queryResultSet),false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
		}
	}
}
