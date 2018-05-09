package pageobject.RMPT;

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
import com.mongodb.connection.QueryResult;

import core.framework.Globals;

public class RMPT_TERM {
	
	
	public String queryString;
	 private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CPAL_Copy_Allocs_1";
		private DocumentBuilderFactory docBuilderFactory;
		private DocumentBuilder docBuilder;
		private Document doc;
		private int queryResultSet;
		private ResultSet queryResultSet1;
		private ResultSet queryResultSet2;
		
		String CONTROL_SELECTION_0;
		String LOGON_DATABASE;
		String LOGON_PASSWORD;
		String LOGON_USERNAME;
		String GET_GA_GA_ID_0;
		String SSN_SSN_0;
		String SSN_MOVE_0;
		String BUTTON_NARRATIVE_0;
		String STATUS_SUBCODE_LOV;
		
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
		public void setSSN_SSN_0(String sSN_SSN_0){
			SSN_SSN_0 = sSN_SSN_0;
		}
		public void setSSN_MOVE_0(String sSN_MOVE_0){
			SSN_MOVE_0 = sSN_MOVE_0;
		}
		public void setBUTTON_NARRATIVE_0(String bUTTON_NARRATIVE_0){
			BUTTON_NARRATIVE_0 = bUTTON_NARRATIVE_0;
		}
		public void setSTATUS_SUBCODE_LOV(String sTATUS_SUBCODE_LOV){
			STATUS_SUBCODE_LOV = sTATUS_SUBCODE_LOV;
		}
		
		
		public void setServiceParameters()
		{
			this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
			this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
			this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
			this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
			this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
			this.setSSN_SSN_0(Stock.GetParameterValue("SSN_SSN_0"));
			this.setSSN_MOVE_0(Stock.GetParameterValue("SSN_MOVE_0"));
			this.setBUTTON_NARRATIVE_0(Stock.GetParameterValue("BUTTON_NARRATIVE_0"));
			this.setSTATUS_SUBCODE_LOV(Stock.GetParameterValue("STATUS_SUBCODE_LOV"));
			
			queryString = "?LOGON_USERNAME="+ LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD +"&LOGON_DATABASE=" 
			+ LOGON_DATABASE +"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +
		     "&GET_GA_GA_ID_0=" + GET_GA_GA_ID_0 +"&SSN_SSN_0=" + SSN_SSN_0 +
			"&SSN_MOVE_0="	+ SSN_MOVE_0+"&BUTTON_NARRATIVE_0="+BUTTON_NARRATIVE_0+
			"&STATUS_SUBCODE_LOV="+STATUS_SUBCODE_LOV+
			"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
			
			Reporter.logEvent(Status.INFO, "Prepare test data for RMPT service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run RMPT service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run RMPT service", "Running Failed:", false);
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
		
		public void ValidatebfrUpdate() throws Exception
		{
			queryResultSet1 = DB.executeQuery(General.dbInstance,Stock.getTestQuery("queryForRMPT1")[1]);
			if(DB.getRecordSetCount(queryResultSet1)>0)
			{
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
				}
			
		}
		
		public void UpdateRMPT() throws Exception
		{	
		try {
			queryResultSet = DB.executeUpdate(General.dbInstance,
					Stock.getTestQuery("queryForRMPTUpdate")[1],this.GET_GA_GA_ID_0,
					Stock.GetParameterValue("IND_ID"));
			System.out.println(queryResultSet);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Reporter.logEvent(Status.INFO, "Updating values From DATABASE.",
				"Updating values From DATABASE.", false);
	}

		
		public void ValidateAftrUpdate() throws SQLException{
			String status_code = null;
			String status_subcode = null;
			queryResultSet2 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRMPT2")[1],this.GET_GA_GA_ID_0,
					this.SSN_SSN_0);
			if(DB.getRecordSetCount(queryResultSet2)>0){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
				while(queryResultSet2.next()){
				    status_code = queryResultSet2.getString("status_code");
				    status_subcode= queryResultSet2.getString("status_subcode");
				   String exp_status_code = Stock.GetParameterValue("exp_status_code");
				   String exp_status_subcode = Stock.GetParameterValue("exp_status_subcode");
				   if(status_code.equalsIgnoreCase(exp_status_code)){
					Reporter.logEvent(Status.PASS,"Validating the if status code is same as expected.", "Status code is same as expected.DB value ="+status_code+
							"\nExpected value="+exp_status_code , false);
				}
				   else
				   {
					   Reporter.logEvent(Status.FAIL,"Validating the if status code is same as expected.", "Status code is not same as expected.DB value ="+status_code+
								"\nExpected value="+exp_status_code , false);
				   }
				   }
			}	  
			else{
					Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
				}
			
			
		
		}
}

