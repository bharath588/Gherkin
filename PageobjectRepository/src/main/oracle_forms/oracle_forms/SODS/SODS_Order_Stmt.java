package pageobject.SODS;

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

public class SODS_Order_Stmt {

	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SODS_ORDER_STMT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GROUP_HEADER_GA_ID_0;
	String DOC_PERIOD_LOV;
	String IND_HEADER_SELECT_IND_3;
	String ARCHIVER_SELECT_IND_0;
	String PRT_SEL_DISPLAY_IND_0;
	
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
	public void setGROUP_HEADER_GA_ID_0(String gROUP_HEADER_GA_ID_0) {
		GROUP_HEADER_GA_ID_0 = gROUP_HEADER_GA_ID_0;
	}
	public void setDOC_PERIOD_LOV(String dOC_PERIOD_LOV) {
		DOC_PERIOD_LOV = dOC_PERIOD_LOV;
	}
	public void setIND_HEADER_SELECT_IND_3(String iND_HEADER_SELECT_IND_3) {
		IND_HEADER_SELECT_IND_3 = iND_HEADER_SELECT_IND_3;
	}
	public void setARCHIVER_SELECT_IND_0(String aRCHIVER_SELECT_IND_0) {
		ARCHIVER_SELECT_IND_0 = aRCHIVER_SELECT_IND_0;
	}
	public void setPRT_SEL_DISPLAY_IND_0(String pRT_SEL_DISPLAY_IND_0) {
		PRT_SEL_DISPLAY_IND_0 = pRT_SEL_DISPLAY_IND_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setARCHIVER_SELECT_IND_0(Stock.GetParameterValue("ARCHIVER_SELECT_IND_0"));
		this.setDOC_PERIOD_LOV(Stock.GetParameterValue("DOC_PERIOD_LOV"));
		this.setGROUP_HEADER_GA_ID_0(Stock.GetParameterValue("GROUP_HEADER_GA_ID_0"));
		this.setIND_HEADER_SELECT_IND_3(Stock.GetParameterValue("IND_HEADER_SELECT_IND_3"));
		this.setPRT_SEL_DISPLAY_IND_0(Stock.GetParameterValue("PRT_SEL_DISPLAY_IND_0"));
				
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GROUP_HEADER_GA_ID_0="+GROUP_HEADER_GA_ID_0+"&DOC_PERIOD_LOV="+DOC_PERIOD_LOV+"&IND_HEADER_SELECT_IND_3="+IND_HEADER_SELECT_IND_3+
				"&ARCHIVER_SELECT_IND_0="+ARCHIVER_SELECT_IND_0+"&PRT_SEL_DISPLAY_IND_0="+PRT_SEL_DISPLAY_IND_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SODS service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run SODS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SODS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "ARCHIVER_DOC_ID: "+doc.getElementsByTagName("ARCHIVER_DOC_ID_0").item(0).getTextContent()+
					"\nARCHIVER_DP_STORE_DATE: "+doc.getElementsByTagName("ARCHIVER_DP_STORE_DATE_0").item(0).getTextContent()+
					"\nARCHIVER_SSN: "+doc.getElementsByTagName("ARCHIVER_SSN_0").item(0).getTextContent()+
					"\nARCHIVER_TOP_DMTY_CODE: "+doc.getElementsByTagName("ARCHIVER_TOP_DMTY_CODE_0").item(0).getTextContent()+
					"\nARCHIVER_TOP_PLAN_NAME: "+doc.getElementsByTagName("ARCHIVER_TOP_PLAN_NAME_0").item(0).getTextContent()+
					"\nDOC_FORMAT_DESCR: "+doc.getElementsByTagName("DOC_FORMAT_DESCR_0").item(0).getTextContent()+
					"\nDOC_FORMAT_ID: "+doc.getElementsByTagName("DOC_FORMAT_ID_0").item(0).getTextContent()+
					"\nGROUP_HEADER_PLAN_NAME: "+doc.getElementsByTagName("GROUP_HEADER_PLAN_NAME_0").item(0).getTextContent()+
					"\nIND_HEADER_SSN: "+doc.getElementsByTagName("IND_HEADER_SSN_0").item(0).getTextContent()+
					"\nSTMT_ACTY_DMTY_CODE: "+doc.getElementsByTagName("STMT_ACTY_DMTY_CODE_0").item(0).getTextContent(), false);			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	
}
