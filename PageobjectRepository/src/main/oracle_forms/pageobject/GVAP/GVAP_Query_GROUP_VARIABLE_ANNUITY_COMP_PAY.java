package pageobject.GVAP;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class GVAP_Query_GROUP_VARIABLE_ANNUITY_COMP_PAY {
	
	public String queryString;
	 private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() +
			 						":8080/ServiceManager/Macro/ExecMacro/GVAP_Query_GROUP_VARIABLE_ANNUITY_COMP_PAY";
		private DocumentBuilderFactory docBuilderFactory;
		private DocumentBuilder docBuilder;
		private Document doc;
		private ResultSet queryResultSet;
		
		String CONTROL_SELECTION_0;
		String LOGON_DATABASE;
		String LOGON_PASSWORD;
		String LOGON_USERNAME;
		String CONTROL_SELECTION_0_X1;
		String CONTROL_M_GA_ID_0;
		
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
		public void setCONTROL_SELECTION_0_X1(String cONTROL_SELECTION_0_X1) {
			CONTROL_SELECTION_0_X1 = cONTROL_SELECTION_0_X1;
		}
		public void setCONTROL_M_GA_ID_0(String cONTROL_M_GA_ID_0){
			CONTROL_M_GA_ID_0 = cONTROL_M_GA_ID_0;
		}
		
		
		public void setServiceParameters()
		{
			this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
			this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
			this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
			this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
			this.setCONTROL_SELECTION_0_X1(Stock.GetParameterValue("CONTROL_SELECTION_0_X1"));
			this.setCONTROL_M_GA_ID_0(Stock.GetParameterValue("CONTROL_M_GA_ID_0"));
			
			
			queryString = "?LOGON_USERNAME="+ LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD +"&LOGON_DATABASE=" 
			+ LOGON_DATABASE +"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 + ""
					+ "&CONTROL_SELECTION_0_X1=" + CONTROL_SELECTION_0_X1 +"&CONTROL_M_GA_ID_0=" + CONTROL_M_GA_ID_0 +
					"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
			
			Reporter.logEvent(Status.INFO, "Prepare test data for GVAG service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run GVAP service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run GVAP service", "Running Failed:", false);
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
		
		
		public void validateInDatabase() throws SQLException{
			
			queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGVAP")[1]);
			if(DB.getRecordSetCount(queryResultSet)>0){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
				Reporter.logEvent(Status.INFO,"Expected number of rows returned:14","Number of rows returned:"+DB.getRecordSetCount(queryResultSet), 
						false);

			}
			else
				
					Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
						
			
			
		}
	
	
	
	

}