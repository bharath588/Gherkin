package pageobject.DAMT;

import java.net.URLDecoder;
import java.sql.ResultSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class DAMT_Process {
	
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DAMT_Process1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	
	
	private String CONTROL_SELECTION_0;
	private String LOGON_DATABASE;
	private String LOGON_USERNAME;
	private String LOGON_PASSWORD;
	private String DISB_DETL_REQ_AMT_0;
	private String STEP1_QRY_EV_ID_0;
	
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
	public String getDISB_DETL_REQ_AMT_0() {
		return DISB_DETL_REQ_AMT_0;
	}
	public void setDISB_DETL_REQ_AMT_0(String dISB_DETL_REQ_AMT_0) {
		DISB_DETL_REQ_AMT_0 = dISB_DETL_REQ_AMT_0;
	}
	public String getSTEP1_QRY_EV_ID_0() {
		return STEP1_QRY_EV_ID_0;
	}
	public void setSTEP1_QRY_EV_ID_0(String sTEP1_QRY_EV_ID_0) {
		STEP1_QRY_EV_ID_0 = sTEP1_QRY_EV_ID_0;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setDISB_DETL_REQ_AMT_0(Stock.GetParameterValue("DISB_DETL_REQ_AMT_0"));
		
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME 
				+"&LOGON_PASSWORD=" + LOGON_PASSWORD 
				+ "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0
				+"&DISB_DETL_REQ_AMT_0=" + DISB_DETL_REQ_AMT_0
				+"&STEP1_QRY_EV_ID_0="+STEP1_QRY_EV_ID_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for DAMT service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run DAMT service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DAMT service", "Running Failed:", false);
		}
	}

	
	public void validateOutput()
	{
		
	}
	
	
}
