package pageobject.DPAR;

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

public class DPAR_Query_Documents {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DPAR_Query_Documents";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String DOCUMENTS_GA_ID_0;
	String QUERY_DATE_START_QUERY_DATE_0;
	String QUERY_DATE_END_QUERY_DATE_0;
	
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
	public void setDOCUMENTS_GA_ID_0(String dOCUMENTS_GA_ID_0) {
		DOCUMENTS_GA_ID_0 = dOCUMENTS_GA_ID_0;
	}
	public void setQUERY_DATE_START_QUERY_DATE_0(
			String qUERY_DATE_START_QUERY_DATE_0) {
		QUERY_DATE_START_QUERY_DATE_0 = qUERY_DATE_START_QUERY_DATE_0;
	}
	public void setQUERY_DATE_END_QUERY_DATE_0(String qUERY_DATE_END_QUERY_DATE_0) {
		QUERY_DATE_END_QUERY_DATE_0 = qUERY_DATE_END_QUERY_DATE_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setDOCUMENTS_GA_ID_0(Stock.GetParameterValue("DOCUMENTS_GA_ID_0"));
		this.setQUERY_DATE_END_QUERY_DATE_0(Stock.GetParameterValue("QUERY_DATE_END_QUERY_DATE_0"));
		this.setQUERY_DATE_START_QUERY_DATE_0(Stock.GetParameterValue("QUERY_DATE_START_QUERY_DATE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&DOCUMENTS_GA_ID_0="+DOCUMENTS_GA_ID_0+"&QUERY_DATE_START_QUERY_DATE_0="+QUERY_DATE_START_QUERY_DATE_0+"&QUERY_DATE_END_QUERY_DATE_0="+QUERY_DATE_END_QUERY_DATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for DPAR Change service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run DPAR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DPAR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From Response: ", "Expected: List of documents for GA_ID and date range display on screen\n"+
					"\nGA_ID: "+doc.getElementsByTagName("QUERY_SEL_GA_ID_0").item(0).getTextContent()+
					"\nIND_ID: "+doc.getElementsByTagName("QUERY_SEL_IND_ID_0").item(0).getTextContent()+
					"\nDATE_CREATED: "+doc.getElementsByTagName("QUERY_SEL_DATE_CREATED_0").item(0).getTextContent()+
					"\nSESSION_ID: "+doc.getElementsByTagName("CFG_CONTROL_SESSION_ID_0").item(0).getTextContent()+
					"\nSYSTEM_CODE: "+doc.getElementsByTagName("CFG_CONTROL_SYSTEM_CODE_0").item(0).getTextContent()+
					"\nDMTY_CODE: "+doc.getElementsByTagName("QUERY_SEL_DMTY_CODE_0").item(0).getTextContent()+
					"\nDOC_ID: "+doc.getElementsByTagName("QUERY_SEL_DOC_ID_0").item(0).getTextContent()
					, false);
		
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
}
