package pageobject.ABRT;

import java.net.URLDecoder;
import java.sql.ResultSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class ABRT_Account_Bal_Report_pptLevel {
	
	public static String queryString;
	private static String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ABRT_Account_Balance_rpt_Participant_level";
	private static DocumentBuilderFactory docBuilderFactory;
	private static DocumentBuilder docBuilder;
	private static Document doc;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String INP1_DISP_DFT_VALUE_0;
	String INP1_DATE_VALUE_4;
	String INP1_DISP_DFT_VALUE_9;
	String MO1_DISP_DFT_PRIN_CODE_0;
	
	public void setINP1_DISP_DFT_VALUE_0(String iNP1_DISP_DFT_VALUE_0) {
		INP1_DISP_DFT_VALUE_0 = iNP1_DISP_DFT_VALUE_0;
	}
	public void setINP1_DATE_VALUE_4(String iNP1_DATE_VALUE_4) {
		INP1_DATE_VALUE_4 = iNP1_DATE_VALUE_4;
	}
	public void setINP1_DISP_DFT_VALUE_9(String iNP1_DISP_DFT_VALUE_9) {
		INP1_DISP_DFT_VALUE_9 = iNP1_DISP_DFT_VALUE_9;
	}
	public void setMO1_DISP_DFT_PRIN_CODE_0(String mO1_DISP_DFT_PRIN_CODE_0) {
		MO1_DISP_DFT_PRIN_CODE_0 = mO1_DISP_DFT_PRIN_CODE_0;
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
	

	public void setServiceParameters()
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setINP1_DATE_VALUE_4(Stock.GetParameterValue("INP1_DATE_VALUE_4"));
		 this.setINP1_DISP_DFT_VALUE_0(Stock.GetParameterValue("INP1_DISP_DFT_VALUE_0"));
		 this.setINP1_DISP_DFT_VALUE_9(Stock.GetParameterValue("INP1_DISP_DFT_VALUE_9"));
		 this.setMO1_DISP_DFT_PRIN_CODE_0(Stock.GetParameterValue("MO1_DISP_DFT_PRIN_CODE_0"));
	
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&INP1_DISP_DFT_VALUE_0="+INP1_DISP_DFT_VALUE_0+
				 "&INP1_DATE_VALUE_4="+INP1_DATE_VALUE_4+"&INP1_DISP_DFT_VALUE_9="+INP1_DISP_DFT_VALUE_9+"&MO1_DISP_DFT_PRIN_CODE_0="+MO1_DISP_DFT_PRIN_CODE_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for ABRT PPT LEVEL service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run ABRT Account Balance Report at Participant level", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ABRT Account Balance Report at Participant level", "Running Failed:", false);
		}
		
	}
	
	public void validateOutput(){
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From Response: ", "Response Messages: "+doc.getElementsByTagName("PopupMessages").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		
	}
	

}
