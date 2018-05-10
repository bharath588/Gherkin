package pageobject.CSIP;

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

public class CSIP_Default_Alloc_Cash {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CSIP_DEFAULT_ALLOC_CASH_UI";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String REMIT_NOTICE_ID_0;
	String param270187;
	String param270187_X1;
	String param270187_X2;
	String param270187_X3;
	String REMIT_NOTICE_GCS_VALUE_0;
	String REMIT_NOTICE_ID_0_X1;
	String param270812;
	String param270812_X1;
	String param270812_X2;
	String MASS_R1100_INFO_SELECT_ALL_R1100_0;
	
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
	public void setREMIT_NOTICE_ID_0(String rEMIT_NOTICE_ID_0) {
		REMIT_NOTICE_ID_0 = rEMIT_NOTICE_ID_0;
	}
	public void setParam270187(String param270187) {
		this.param270187 = param270187;
	}
	public void setParam270187_X1(String param270187_X1) {
		this.param270187_X1 = param270187_X1;
	}
	public void setParam270187_X2(String param270187_X2) {
		this.param270187_X2 = param270187_X2;
	}
	public void setParam270187_X3(String param270187_X3) {
		this.param270187_X3 = param270187_X3;
	}
	public void setREMIT_NOTICE_GCS_VALUE_0(String rEMIT_NOTICE_GCS_VALUE_0) {
		REMIT_NOTICE_GCS_VALUE_0 = rEMIT_NOTICE_GCS_VALUE_0;
	}
	public void setREMIT_NOTICE_ID_0_X1(String rEMIT_NOTICE_ID_0_X1) {
		REMIT_NOTICE_ID_0_X1 = rEMIT_NOTICE_ID_0_X1;
	}
	public void setParam270812(String param270812) {
		this.param270812 = param270812;
	}
	public void setParam270812_X1(String param270812_X1) {
		this.param270812_X1 = param270812_X1;
	}
	public void setParam270812_X2(String param270812_X2) {
		this.param270812_X2 = param270812_X2;
	}
	public void setMASS_R1100_INFO_SELECT_ALL_R1100_0(
			String mASS_R1100_INFO_SELECT_ALL_R1100_0) {
		MASS_R1100_INFO_SELECT_ALL_R1100_0 = mASS_R1100_INFO_SELECT_ALL_R1100_0;
	}
	
	public void setServiceParameters(String remit_id)	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setREMIT_NOTICE_ID_0(remit_id);
		this.setMASS_R1100_INFO_SELECT_ALL_R1100_0(Stock.GetParameterValue("MASS_R1100_INFO_SELECT_ALL_R1100_0"));
		this.setParam270187(Stock.GetParameterValue("param270187"));
		this.setParam270187_X1(Stock.GetParameterValue("param270187_X1"));
		this.setParam270187_X2(Stock.GetParameterValue("param270187_X2"));
		this.setParam270187_X3(Stock.GetParameterValue("param270187_X3"));
		this.setParam270812(Stock.GetParameterValue("param270812"));
		this.setParam270812_X1(Stock.GetParameterValue("param270812_X1"));
		this.setParam270812_X2(Stock.GetParameterValue("param270812_X2"));
		this.setREMIT_NOTICE_GCS_VALUE_0(Stock.GetParameterValue("REMIT_NOTICE_GCS_VALUE_0"));
		this.setREMIT_NOTICE_ID_0_X1(remit_id);
		
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&REMIT_NOTICE_ID_0="+REMIT_NOTICE_ID_0+"&param270187="+param270187+"&param270187_X1="+param270187_X1+"&param270187_X2="+param270187_X2+
				"&param270187_X3="+param270187_X3+"&REMIT_NOTICE_GCS_VALUE_0="+REMIT_NOTICE_GCS_VALUE_0+"&REMIT_NOTICE_ID_0_X1="+REMIT_NOTICE_ID_0_X1+
				"&param270812="+param270812+"&param270812_X1="+param270812_X1+"&param270812_X2="+param270812_X2+"&MASS_R1100_INFO_SELECT_ALL_R1100_0="+MASS_R1100_INFO_SELECT_ALL_R1100_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for CSIP service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run CSIP service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CSIP service", "Running Failed:", false);
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
		String rmnc_id = this.REMIT_NOTICE_ID_0;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSIPCreateFundingReq")[1], rmnc_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database\nTable Name: REMIT_NOTICE", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "From Database ", "RMNC_ID: "+queryResultSet.getString("ID")+
						"\nEV_ID: "+queryResultSet.getString("EV_ID")+
						"\nBAL_STATUS_CODE: "+queryResultSet.getString("BALANCE_STATUS_CODE")+
						"\nAMOUNT: "+queryResultSet.getString("AMOUNT"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "Record doesn't exists in the Database", false);
		}
	}
}
