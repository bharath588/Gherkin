package pageobject.PABD;

import java.net.URLDecoder;
import java.sql.ResultSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class PABD_Happy_Path {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PABD_Happy_Path1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;	
	ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String QUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	String STEP_TYPE_LOV;
	String PABD_HDR1_EFFDATE_0;
	
	public void setPABD_HDR1_EFFDATE_0(String pABD_HDR1_EFFDATE_0) {
		PABD_HDR1_EFFDATE_0 = pABD_HDR1_EFFDATE_0;
	}
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
	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
	}
	public void setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(
			String qUERY_CHG_MULTIPLE_SSN_EXT_LOV) {
		QUERY_CHG_MULTIPLE_SSN_EXT_LOV = qUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	}
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(Stock.GetParameterValue("QUERY_CHG_MULTIPLE_SSN_EXT_LOV"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		this.setPABD_HDR1_EFFDATE_0(Stock.GetParameterValue("PABD_HDR1_EFFDATE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&QUERY_CHG_MULTIPLE_SSN_EXT_LOV="+QUERY_CHG_MULTIPLE_SSN_EXT_LOV+"&STEP_TYPE_LOV="+STEP_TYPE_LOV+
				"&PABD_HDR1_EFFDATE_0="+PABD_HDR1_EFFDATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for PABD service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run PABD service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PABD service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);	
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
}
