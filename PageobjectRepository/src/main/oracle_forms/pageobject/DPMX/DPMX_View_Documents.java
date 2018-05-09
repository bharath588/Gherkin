package pageobject.DPMX;

import java.net.URLDecoder;
import java.sql.ResultSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class DPMX_View_Documents {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DPMX_view_docids_documents";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;


	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME; 
	String RESULTS_SELECT_IND_2;
	String SELECTION_DOC_ID_0;
	String SELECTION_END_DOC_ID_0;
	String SELECTION_GA_ID_0;
	String SELECTION_USER_ID_0;
	
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
	public void setRESULTS_SELECT_IND_2(String rESULTS_SELECT_IND_2) {
		RESULTS_SELECT_IND_2 = rESULTS_SELECT_IND_2;
	}
	public void setSELECTION_DOC_ID_0(String sELECTION_DOC_ID_0) {
		SELECTION_DOC_ID_0 = sELECTION_DOC_ID_0;
	}
	public void setSELECTION_END_DOC_ID_0(String sELECTION_END_DOC_ID_0) {
		SELECTION_END_DOC_ID_0 = sELECTION_END_DOC_ID_0;
	}
	public void setSELECTION_GA_ID_0(String sELECTION_GA_ID_0) {
		SELECTION_GA_ID_0 = sELECTION_GA_ID_0;
	}
	public void setSELECTION_USER_ID_0(String sELECTION_USER_ID_0) {
		SELECTION_USER_ID_0 = sELECTION_USER_ID_0;
	}
	
	
	String CFG_CONTROL_TODAYS_DATE1_0;
	String RESULTS_DATE_CREATED_0;
	String RESULTS_DATE_PRINTED_0;
	String RESULTS_DH_CODE_0;
	String RESULTS_DMTY_CODE_0;
	String RESULTS_DOC_ID_0;
	String RESULTS_EVENT_ID_0;
	String RESULTS_GA_ID_0;
	String RESULTS_INDIVIDUAL_ID_0;
	String RESULTS_STATUS_CODE_0;
	String RESULTS_USER_ID_0;

	public void setServiceParameters()
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setRESULTS_SELECT_IND_2(Stock.GetParameterValue("RESULTS_SELECT_IND_2"));
		 this.setSELECTION_DOC_ID_0(Stock.GetParameterValue("SELECTION_DOC_ID_0"));
		 this.setSELECTION_END_DOC_ID_0(Stock.GetParameterValue("SELECTION_END_DOC_ID_0"));
		 this.setSELECTION_GA_ID_0(Stock.GetParameterValue("SELECTION_GA_ID_0"));
		 this.setSELECTION_USER_ID_0(Stock.GetParameterValue("SELECTION_USER_ID_0"));
		 
		 queryString = "?CONTROL_SELECTION_0=" +CONTROL_SELECTION_0+"&LOGON_DATABASE=" +LOGON_DATABASE+
				 "&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+"&RESULTS_SELECT_IND_2="+RESULTS_SELECT_IND_2+
				 "&SELECTION_DOC_ID_0="+SELECTION_DOC_ID_0+"&SELECTION_END_DOC_ID_0="+SELECTION_END_DOC_ID_0+"&SELECTION_GA_ID_0="+SELECTION_GA_ID_0+
				 "&SELECTION_USER_ID_0="+SELECTION_USER_ID_0+"&numOfRowsInTable=20&json=false&handlePopups=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for DPMX service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run DPMX View Documents service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DPMX View Documents service", "Running Failed:", false);
		}
		
	}
	
	public void validateOutput(){
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		
		CFG_CONTROL_TODAYS_DATE1_0 =  doc.getElementsByTagName("CFG_CONTROL_TODAYS_DATE1_0").item(0).getTextContent();
		RESULTS_DATE_CREATED_0 =  doc.getElementsByTagName("RESULTS_DATE_CREATED_0").item(0).getTextContent();
		RESULTS_DATE_PRINTED_0 = doc.getElementsByTagName("RESULTS_DATE_PRINTED_0").item(0).getTextContent();
		RESULTS_DH_CODE_0 = doc.getElementsByTagName("RESULTS_DH_CODE_0").item(0).getTextContent();
		RESULTS_DMTY_CODE_0 = doc.getElementsByTagName("RESULTS_DMTY_CODE_0").item(0).getTextContent();
		RESULTS_DOC_ID_0 = doc.getElementsByTagName("RESULTS_DOC_ID_0").item(0).getTextContent();
		RESULTS_EVENT_ID_0 = doc.getElementsByTagName("RESULTS_EVENT_ID_0").item(0).getTextContent();
		RESULTS_GA_ID_0 = doc.getElementsByTagName("RESULTS_GA_ID_0").item(0).getTextContent();
		RESULTS_INDIVIDUAL_ID_0 = doc.getElementsByTagName("RESULTS_INDIVIDUAL_ID_0").item(0).getTextContent();
		RESULTS_STATUS_CODE_0 = doc.getElementsByTagName("RESULTS_STATUS_CODE_0").item(0).getTextContent();
		RESULTS_USER_ID_0 = doc.getElementsByTagName("RESULTS_USER_ID_0").item(0).getTextContent();
		
		Reporter.logEvent(Status.INFO, "From Response Expected Outcome: ", "Getting CFG_CONTROL_TODAYS_DATE1_0"  + CFG_CONTROL_TODAYS_DATE1_0
		+"\nGetting RESULTS_DATE_CREATED_0"  + RESULTS_DATE_CREATED_0
		+"\nGetting RESULTS_DATE_PRINTED_0"  + RESULTS_DATE_PRINTED_0
		 +"\nGetting RESULTS_DH_CODE_0" +RESULTS_DH_CODE_0
		 +"\nGetting RESULTS_DMTY_CODE_0"+ RESULTS_DMTY_CODE_0
		 +"\nGetting RESULTS_DOC_ID_0"  +RESULTS_DOC_ID_0
		 +"\nGetting RESULTS_EVENT_ID_0"+ RESULTS_EVENT_ID_0
		 +"\nGetting RESULTS_GA_ID_0"  +RESULTS_GA_ID_0
		 +"\nGetting RESULTS_INDIVIDUAL_ID_0"+  RESULTS_INDIVIDUAL_ID_0
		 +"\nGetting RESULTS_STATUS_CODE_0"  +RESULTS_STATUS_CODE_0
		 +"\nGetting RESULTS_USER_ID_0" + RESULTS_USER_ID_0, false);
		
	}



}
