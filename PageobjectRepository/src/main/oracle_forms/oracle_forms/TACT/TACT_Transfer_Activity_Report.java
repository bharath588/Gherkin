package pageobject.TACT;

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

public class TACT_Transfer_Activity_Report {

		public String queryString;
		private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/TACT_Transfer_Activity_Report";
		private DocumentBuilderFactory docBuilderFactory;
		private DocumentBuilder docBuilder;
		private Document doc;
		private ResultSet queryResultSet;
		
		String CONTROL_SELECTION_0;  
		String LOGON_DATABASE; 
		String LOGON_PASSWORD; 
		String LOGON_USERNAME;
		String INP1_DATE_VALUE_0;
		String INP1_DATE_VALUE_1;
		String INP1_DISP_DFT_VALUE_2;
		String MO1_DISP_DFT_PRIN_CODE_0;
		
		
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
		public void setINP1_DATE_VALUE_0(String iNP1_DATE_VALUE_0) {
			INP1_DATE_VALUE_0 = iNP1_DATE_VALUE_0;
		}
		public void setINP1_DATE_VALUE_1(String iNP1_DATE_VALUE_1) {
			INP1_DATE_VALUE_1 = iNP1_DATE_VALUE_1;
		}
		public void setINP1_DISP_DFT_VALUE_2(String iNP1_DISP_DFT_VALUE_2) {
			INP1_DISP_DFT_VALUE_2 = iNP1_DISP_DFT_VALUE_2;
		}
		public void setMO1_DISP_DFT_PRIN_CODE_0(String mO1_DISP_DFT_PRIN_CODE_0) {
			MO1_DISP_DFT_PRIN_CODE_0 = mO1_DISP_DFT_PRIN_CODE_0;
		}
		
		public void setServiceParameters() throws SQLException
		{
			 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
			 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
			 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
			 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
			 this.setINP1_DATE_VALUE_0(Stock.GetParameterValue("INP1_DATE_VALUE_0"));
			 this.setINP1_DATE_VALUE_0(Stock.GetParameterValue("INP1_DATE_VALUE_1"));
			 this.setINP1_DISP_DFT_VALUE_2(Stock.GetParameterValue("INP1_DISP_DFT_VALUE_2"));
			 this.setMO1_DISP_DFT_PRIN_CODE_0(Stock.GetParameterValue("MO1_DISP_DFT_PRIN_CODE_0"));
			 
			 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
					 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&INP1_DATE_VALUE_0="+INP1_DATE_VALUE_0+"&INP1_DATE_VALUE_1="+INP1_DATE_VALUE_1+
					 "&INP1_DISP_DFT_VALUE_2="+INP1_DISP_DFT_VALUE_2+"&MO1_DISP_DFT_PRIN_CODE_0="+MO1_DISP_DFT_PRIN_CODE_0+
					 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
			 
			 Reporter.logEvent(Status.INFO, "Prepare test data for TACT service", this.queryString.replaceAll("&", "\n"), false);
			 
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
			//		System.out.println(serviceURL);
					doc.getDocumentElement().normalize();
					Reporter.logEvent(Status.PASS, "Run  TACT_Transfer_Activity_Report service", "Execution Done!", false);
				} catch (Exception e) {
					e.printStackTrace();
					Globals.exception = e;
					Reporter.logEvent(Status.FAIL, "Run TACT_Transfer_Activity_Report service", "Running Failed:", false);
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
		
			Reporter.logEvent(Status.PASS, "From response: ","TITLE: "+doc.getElementsByTagName("CONTROL_MENU_TITLE_0").item(0).getTextContent()+
			"\nTRANSACTION_CODE_0: "+doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent()+
			"\nTRANSACTION_DESCR_0: "+doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent()+
			"\nMessages from Response: "+doc.getElementsByTagName("PopupMessages").item(0).getTextContent()+
			"\n",false);
		}
		
}

