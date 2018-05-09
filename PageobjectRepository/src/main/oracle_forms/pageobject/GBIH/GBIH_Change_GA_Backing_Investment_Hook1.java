package pageobject.GBIH;

import java.net.URLDecoder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class GBIH_Change_GA_Backing_Investment_Hook1 {
	
	 public String queryString;
	 private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GBIH_Change_GA_Backing_Investment_Hook1";
		private DocumentBuilderFactory docBuilderFactory;
		private DocumentBuilder docBuilder;
		private Document doc;
		String CONTROL_SELECTION_0;
		String LOGON_DATABASE;
		String LOGON_PASSWORD;
		String LOGON_USERNAME;
		String GET_GA_GA_ID_0;
		String GA_INVEST_ACCOUNT_NUMBER_0;
		String GA_INVEST_ACCOUNT_NUMBER_0_X1;
		
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
		public void setGA_INVEST_ACCOUNT_NUMBER_0(String gA_INVEST_ACCOUNT_NUMBER_0){
			GA_INVEST_ACCOUNT_NUMBER_0 = gA_INVEST_ACCOUNT_NUMBER_0;
		}
		public void setGA_INVEST_ACCOUNT_NUMBER_0_X1(String gA_INVEST_ACCOUNT_NUMBER_0_X1){
			GA_INVEST_ACCOUNT_NUMBER_0_X1 = gA_INVEST_ACCOUNT_NUMBER_0_X1;
		}
		
		
		public void setServiceParameters()
		{
			this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
			this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
			this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
			this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
			this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
			this.setGA_INVEST_ACCOUNT_NUMBER_0(Stock.GetParameterValue("GA_INVEST_ACCOUNT_NUMBER_0"));
			this.setGA_INVEST_ACCOUNT_NUMBER_0_X1(Stock.GetParameterValue("GA_INVEST_ACCOUNT_NUMBER_0_X1"));
			
			
			queryString = "?LOGON_USERNAME="+ LOGON_USERNAME 
					+ "&LOGON_PASSWORD=" + LOGON_PASSWORD 
					+"&LOGON_DATABASE=" 
			+ LOGON_DATABASE 
			+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
					+ "&GET_GA_GA_ID_0=" + GET_GA_GA_ID_0 
					+"&GA_INVEST_ACCOUNT_NUMBER_0=" + GA_INVEST_ACCOUNT_NUMBER_0 
					+ "&GA_INVEST_ACCOUNT_NUMBER_0_X1=" + GA_INVEST_ACCOUNT_NUMBER_0_X1 +
					"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
			
			Reporter.logEvent(Status.INFO, "Prepare test data for GBIH service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run GBIH service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run GBIH service", "Running Failed:", false);
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