package pageobject.TXYR;

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

public class TXYR_Tax_Year_Change {
	
	public String queryString;
	 private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/TXYR_Tax_Year_Change";
		private DocumentBuilderFactory docBuilderFactory;
		private DocumentBuilder docBuilder;
		private Document doc;
		private int queryResultSet;
		private int queryResultSetupdated;
		private ResultSet queryResultSet2;
		private ResultSet queryResultSet3;
		
		String CONTROL_SELECTION_0;
		String LOGON_DATABASE;
		String LOGON_PASSWORD;
		String LOGON_USERNAME;
		String CONTROL_SSN_0;
		String CONTROL_EV_ID_0;
		String CONTROL_NEW_TAX_YR_0;
		
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
		public void setCONTROL_SSN_0(String cONTROL_SSN_0) {
			CONTROL_SSN_0 = cONTROL_SSN_0;
		}
		public void setCONTROL_EV_ID_0(String cONTROL_EV_ID_0){
			CONTROL_EV_ID_0 = cONTROL_EV_ID_0;
		}
		public void setCONTROL_NEW_TAX_YR_0(String cONTROL_NEW_TAX_YR_0){
			CONTROL_NEW_TAX_YR_0 = cONTROL_NEW_TAX_YR_0;
		}
		
		
		public void setServiceParameters()
		{
			this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
			this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
			this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
			this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
			this.setCONTROL_SSN_0(Stock.GetParameterValue("CONTROL_SSN_0"));
			this.setCONTROL_EV_ID_0(Stock.GetParameterValue("CONTROL_EV_ID_0"));
			this.setCONTROL_NEW_TAX_YR_0(Stock.GetParameterValue("CONTROL_NEW_TAX_YR_0"));
			
			
			queryString = "?LOGON_USERNAME="+ LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD +"&LOGON_DATABASE=" 
			+ LOGON_DATABASE +"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +
		     "&CONTROL_SSN_0=" + CONTROL_SSN_0 +"&CONTROL_EV_ID_0=" + CONTROL_EV_ID_0 +
			"&CONTROL_NEW_TAX_YR_0="+ CONTROL_NEW_TAX_YR_0+"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
			
			Reporter.logEvent(Status.INFO, "Prepare test data for TXYR service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run TXYR service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run TXYR service", "Running Failed:", false);
			}
		}
		
		public void validateOutput()
		{
			String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

			if (Error.isEmpty()){
				Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			} else {
				Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
				System.out.println(Error);
			}	
				
		}
		
		public void cleanUp() throws Exception
		{	
		try {
			queryResultSet = DB.executeUpdate(General.dbInstance,
					Stock.getTestQuery("queryForTXYRupdate1")[1],this.CONTROL_EV_ID_0);
			System.out.println(queryResultSet);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Reporter.logEvent(Status.INFO, "Updating values From DATABASE TAX_INFO.","Updated values From DATABASE TAX_INFO.", false);
		
		try {
			queryResultSetupdated = DB.executeUpdate(General.dbInstance,
					Stock.getTestQuery("queryForTXYRupdate2")[1],this.CONTROL_EV_ID_0);
			System.out.println(queryResultSet);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Reporter.logEvent(Status.INFO, "Updating values From DATABASE DISB_RECVR","Updated values From DATABASE DISB_RECVR.", false);
	}

		
		public void validateInDatabase() throws SQLException{
			String dbYear1 = null;
			queryResultSet2 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTXYR1")[1],this.CONTROL_EV_ID_0);
			if(DB.getRecordSetCount(queryResultSet2)>0){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
				while(queryResultSet2.next()){
				    dbYear1 = queryResultSet2.getString("TAX_YR");
				    if(dbYear1.equalsIgnoreCase(this.CONTROL_NEW_TAX_YR_0))
				    {
				    Reporter.logEvent(Status.PASS,"Validating if the year is updated after the service run.", "Year updated after the service run"+dbYear1, false);
				}
				    else{
				    Reporter.logEvent(Status.FAIL,"Validating if the year is updated after the service run.", "Year not updated after the service run"+dbYear1, false);
				    }
				    }
			}
			  
			else{
					Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
				}
			
			
			
			String dbYear2 = null;
			queryResultSet3 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTXYR2")[1],this.CONTROL_EV_ID_0);
			if(DB.getRecordSetCount(queryResultSet3)>0){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
				while(queryResultSet2.next()){
				    dbYear2 = queryResultSet2.getString("TAX_YR");
				    if(dbYear2.equalsIgnoreCase(this.CONTROL_NEW_TAX_YR_0))
				    {
				    Reporter.logEvent(Status.PASS,"Validating if the year is updated after the service run.", "Year updated after the service run"+dbYear2, false);
				}
				    else{
				    Reporter.logEvent(Status.FAIL,"Validating if the year is updated after the service run.", "Year not updated after the service run"+dbYear2, false);
				    }
				    }
			}
			  
			else{
					Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
				}
			
			
		}	
}


