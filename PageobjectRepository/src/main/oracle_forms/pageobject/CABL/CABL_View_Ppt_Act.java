package pageobject.CABL;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class CABL_View_Ppt_Act {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CABL_View_PPT_Activity";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String INDIVIDUAL_SSN_0;
	String NAVIGATION_LOV;
	
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
	public void setINDIVIDUAL_SSN_0(String iNDIVIDUAL_SSN_0) {
		INDIVIDUAL_SSN_0 = iNDIVIDUAL_SSN_0;
	}
	public void setNAVIGATION_LOV(String nAVIGATION_LOV) {
		NAVIGATION_LOV = nAVIGATION_LOV;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setINDIVIDUAL_SSN_0(Stock.GetParameterValue("INDIVIDUAL_SSN_0"));
		 this.setNAVIGATION_LOV(Stock.GetParameterValue("NAVIGATION_LOV"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&INDIVIDUAL_SSN_0="+INDIVIDUAL_SSN_0+"&NAVIGATION_LOV="+NAVIGATION_LOV+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for CABL service", this.queryString.replaceAll("&", "\n"), false);
	}
		 
	public void runService() throws SQLException {
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
				Reporter.logEvent(Status.PASS, "Run CABL_View_PPT_Activity service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run CABL_View_PPT_Activity service", "Running Failed:", false);
			}
			
		}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		Reporter.logEvent(Status.INFO, "From response ", "Expected Results: Able to View Participants Activity\nINDIVIDUAL_BIRTH_DATE_0: " +doc.getElementsByTagName("INDIVIDUAL_BIRTH_DATE_0").item(0).getTextContent()+
				"\nINDIVIDUAL_IND_ID_0: "+doc.getElementsByTagName("INDIVIDUAL_IND_ID_0").item(0).getTextContent()+"\nIO_ACT_DETL_AMOUNT_2: "+doc.getElementsByTagName("IO_ACT_DETL_AMOUNT_2").item(0).getTextContent()+
				"\nSDIO_ID_0: "+doc.getElementsByTagName("IO_ACT_DETL_SDIO_ID_0").item(0).getTextContent()+"\nGA_ID_0: "+doc.getElementsByTagName("PART_AGRMT_GA_ID_0").item(0).getTextContent(), false);
		
	}
}
