package pageobject.KWRS;

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

public class KWRS_Change_Work_Report_Request_Status {
	
	
	public String queryString;
	 private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/KWRS_Change_Work_Report_Request_Status";
		private DocumentBuilderFactory docBuilderFactory;
		private DocumentBuilder docBuilder;
		private Document doc;
		private ResultSet queryResultSet;
		
		
		String CONTROL_SELECTION_0;
		String LOGON_DATABASE;
		String LOGON_PASSWORD;
		String LOGON_USERNAME;
		String WORK_REPORT_REQUEST_FILE_NAME_0;
		String CHANGE_RECORD_STATUS_CODE_0;
		
		
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
		public void setWORK_REPORT_REQUEST_FILE_NAME_0(String wORK_REPORT_REQUEST_FILE_NAME_0){
			WORK_REPORT_REQUEST_FILE_NAME_0 = wORK_REPORT_REQUEST_FILE_NAME_0;
		}
		public void setCHANGE_RECORD_STATUS_CODE_0(String cHANGE_RECORD_STATUS_CODE_0){
			CHANGE_RECORD_STATUS_CODE_0 = cHANGE_RECORD_STATUS_CODE_0;
		}
		
		
		public void setServiceParameters()
		{
			this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
			this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
			this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
			this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
			this.setWORK_REPORT_REQUEST_FILE_NAME_0(Stock.GetParameterValue("WORK_REPORT_REQUEST_FILE_NAME_0"));
			this.setCHANGE_RECORD_STATUS_CODE_0(Stock.GetParameterValue("CHANGE_RECORD_STATUS_CODE_0"));
			
			
			queryString = "?LOGON_USERNAME="+ LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD +"&LOGON_DATABASE=" 
			+ LOGON_DATABASE +"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +
			"&WORK_REPORT_REQUEST_FILE_NAME_0="+WORK_REPORT_REQUEST_FILE_NAME_0+"&CHANGE_RECORD_STATUS_CODE_0="+CHANGE_RECORD_STATUS_CODE_0
			+"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
			
			Reporter.logEvent(Status.INFO, "Prepare test data for KWRS service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run KWRS service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run KWRS service", "Running Failed:", false);
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
					
		public void validateInDatabase() throws SQLException{
		
			queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForKWRS")[1],Stock.GetParameterValue("file_name"));
			
			if(DB.getRecordSetCount(queryResultSet)>0){
				while(queryResultSet.next()){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database.Table name:work_report_request", "Records exists in Database.Table name:work_report_request\n ID="+queryResultSet.getString("ID")
						+"\nUserId="+queryResultSet.getString("USER_LOGON_ID")+"\nTXN_CODE="+queryResultSet.getString("TXN_CODE")
						+"\nFILENAME="+queryResultSet.getString("FILE_NAME")+"\n Req_date_time="+queryResultSet.getString("REQUEST_DATE_TIME"), false);
			}}
				else{
					Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
				}
				
			
		}
}
