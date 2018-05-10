package pageobject.GRPS;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class GRPS_View_GA_Service {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GRPS_VIEW_GA_Service";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	
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
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for GRPS service", this.queryString.replaceAll("&", "\n"), false);		
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
			Reporter.logEvent(Status.PASS, "Run GRPS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run GRPS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response:\nExpected: Able to view group's service rules", "GA_ID: "+ doc.getElementsByTagName("CONTROL_PLAN_NAME_0").item(0).getTextContent()+
			"\nPLAN_NAME: "+doc.getElementsByTagName("CONTROL_PLAN_NAME_0").item(0).getTextContent()+
			"\nDPDATE_TIME: "+doc.getElementsByTagName("GA_SERVICE_DPDATE_TIME_0").item(0).getTextContent()+
			"\nEFFDATE:"+doc.getElementsByTagName("GA_SERVICE_EFFDATE_0").item(0).getTextContent()+
			"\nGA_SERVICE_SDSV_CODE: "+doc.getElementsByTagName("GA_SERVICE_SDSV_CODE_0").item(0).getTextContent()+
			"\nGA_SERVICE_DESCR: "+doc.getElementsByTagName("GA_SERVICE_SDSV_DESCR_0").item(0).getTextContent()+
			"\nGA_SERVICE_SDSV_SUBCODE: "+doc.getElementsByTagName("GA_SERVICE_SDSV_SUBCODE_0").item(0).getTextContent()+
			"\nGA_SERVICE_SERVICE_LEVEL_CODE: "+doc.getElementsByTagName("GA_SERVICE_SERVICE_LEVEL_CODE_0").item(0).getTextContent()+
			"\nGA_SERVICE_PLAN_SDSV_CODE: "+doc.getElementsByTagName("GA_SERVICE_hash_PART_GRPG_PLAN_SDSV_CODE_0").item(0).getTextContent()+
			"\nGA_SERVICE_PLAN_SDSV_SUB_CODE: "+doc.getElementsByTagName("GA_SERVICE_hash_PART_GRPG_PLAN_SDSV_SUBCODE_0").item(0).getTextContent()+
			"\n", false);			
		
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	
}
