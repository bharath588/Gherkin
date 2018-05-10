package pageobject.CSIP;

import generallib.DateCompare;
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

public class CSIP_Remit_Payroll_End_Date {

	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CSIP_REMIT_PAYROLL_END_DATE_3MO";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String REMIT_NOTICE_ID_0;
	String AG_REMIT_AMOUNT_0;
	String AG_REMIT_SDMT_CODE_LOV;
	String AG_REMIT_PAYROLL_DATE_0;
	

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
	public void setAG_REMIT_AMOUNT_0(String aG_REMIT_AMOUNT_0) {
		AG_REMIT_AMOUNT_0 = aG_REMIT_AMOUNT_0;
	}
	public void setAG_REMIT_SDMT_CODE_LOV(String aG_REMIT_SDMT_CODE_LOV) {
		AG_REMIT_SDMT_CODE_LOV = aG_REMIT_SDMT_CODE_LOV;
	}
	public void setAG_REMIT_PAYROLL_DATE_0(String aG_REMIT_PAYROLL_DATE_0) {
		AG_REMIT_PAYROLL_DATE_0 = aG_REMIT_PAYROLL_DATE_0;
	}
	
	public void setServiceParameters(String remit_id, String effdate)	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setREMIT_NOTICE_ID_0(remit_id);
		this.setAG_REMIT_AMOUNT_0(Stock.GetParameterValue("AG_REMIT_AMOUNT_0"));
		this.setAG_REMIT_PAYROLL_DATE_0(DateCompare.GenerateFutureDate());
				
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&REMIT_NOTICE_ID_0="+REMIT_NOTICE_ID_0+"&AG_REMIT_AMOUNT_0="+AG_REMIT_AMOUNT_0+"&AG_REMIT_SDMT_CODE_LOV="+AG_REMIT_SDMT_CODE_LOV+"&AG_REMIT_PAYROLL_DATE_0="+AG_REMIT_PAYROLL_DATE_0+
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
		String PopUpMsg = doc.getElementsByTagName("PopupMessages").item(0).getTextContent();
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			
			if(PopUpMsg.contains("There is a three or more month difference between this payroll date and the cash effective date")){
				Reporter.logEvent(Status.PASS, "Expected Response:  There is a three or more month difference between this payroll date and the cash effective date!;", PopUpMsg, false);
			}else{
		//		Reporter.logEvent(Status.FAIL, "Expected Response:  There is a three or more month difference between this payroll date and the cash effective date!;", PopUpMsg, false);
			}
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String rmnc_id = this.REMIT_NOTICE_ID_0;
		String amnt_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSIPPayrollEndDate")[1], rmnc_id);
		if(!(DB.getRecordSetCount(queryResultSet)>0)){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record doesn't exists in the Database\nTable Name: AG_REMIT", false);
						
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "Record exists in the Database", false);
		}
	}
}
