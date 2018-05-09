package pageobject.cashdropflow;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class MACA_Process {
	
	private String CONTROL_SELECTION_0;
	private String LOGON_DATABASE;
	private String LOGON_USERNAME;
	private String LOGON_PASSWORD;
	private String RMNC1_EV_ID_0;
	private String TPINRM1_AMOUNT1_0;
	private String TPINRM1_SSN_0;
	private String CONTROL_ACTION_CODE_0;
	
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/Cashdrop_MACA";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	public String getCONTROL_SELECTION_0() {
		return CONTROL_SELECTION_0;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public String getLOGON_DATABASE() {
		return LOGON_DATABASE;
	}
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public String getLOGON_USERNAME() {
		return LOGON_USERNAME;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public String getLOGON_PASSWORD() {
		return LOGON_PASSWORD;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public String getRMNC1_EV_ID_0() {
		return RMNC1_EV_ID_0;
	}
	public void setRMNC1_EV_ID_0(String rMNC1_EV_ID_0) {
		RMNC1_EV_ID_0 = rMNC1_EV_ID_0;
	}
	public String getTPINRM1_AMOUNT1_0() {
		return TPINRM1_AMOUNT1_0;
	}
	public void setTPINRM1_AMOUNT1_0(String tPINRM1_AMOUNT1_0) {
		TPINRM1_AMOUNT1_0 = tPINRM1_AMOUNT1_0;
	}
	public String getTPINRM1_SSN_0() {
		return TPINRM1_SSN_0;
	}
	public void setTPINRM1_SSN_0(String tPINRM1_SSN_0) {
		TPINRM1_SSN_0 = tPINRM1_SSN_0;
	}
	public String getCONTROL_ACTION_CODE_0() {
		return CONTROL_ACTION_CODE_0;
	}
	public void setCONTROL_ACTION_CODE_0(String cONTROL_ACTION_CODE_0) {
		CONTROL_ACTION_CODE_0 = cONTROL_ACTION_CODE_0;
	}

	public void setServiceParameters()
	{
		this.setCONTROL_ACTION_CODE_0(Stock.GetParameterValue("CONTROL_ACTION_CODE_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setTPINRM1_AMOUNT1_0(Stock.GetParameterValue("TPINRM1_AMOUNT1_0"));
		
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME 
				+"&LOGON_PASSWORD=" + LOGON_PASSWORD 
				+ "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0				 
				+"&RMNC1_EV_ID_0=" + RMNC1_EV_ID_0
				+"&CONTROL_ACTION_CODE_0=" + CONTROL_ACTION_CODE_0
				+ "&TPINRM1_SSN_0=" + TPINRM1_SSN_0
				+"&TPINRM1_AMOUNT1_0=" + TPINRM1_AMOUNT1_0 				 
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for MACA service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService()
	{
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
			Reporter.logEvent(Status.PASS, "Run MACA service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run MACA service", "Running Failed:", false);
		}
	}
	
	
		public void validateOutput() throws SQLException
		{
			queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("remittanceAllocationQuery")[1],this.RMNC1_EV_ID_0);
			if(queryResultSet != null)
			{
				if(queryResultSet.next())
				{
					Reporter.logEvent(Status.PASS, "Check if any record is created in the IND_REMIT table after the MACA form is submitted", "A record is created in the ind_remit table for money type and fund type.", false);
					Reporter.logEvent(Status.INFO, "Database Validation", "Values in IND_REMIT table after MACA", false);
					Reporter.logEvent(Status.INFO, "Remittance ID : ", queryResultSet.getString("RMNC_ID"), false);
					Reporter.logEvent(Status.INFO, "SSN : ", queryResultSet.getString("SSN"), false);
					Reporter.logEvent(Status.INFO, "Individual ID : ", queryResultSet.getString("IND_ID"), false);
				}else{
					Reporter.logEvent(Status.FAIL, "Check if any record is created in the IND_REMIT table after the MACA form is submitted", "No record is created in the ind_remit table for money type and fund type.", false);
				}
			}
		}
}
	
