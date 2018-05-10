package pageobject.RIND;

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

public class GQ19RIND_Related_Individual_Query {
	
	public String queryString;
	 private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GQ19RIND_Related_Individual_Query";
		private DocumentBuilderFactory docBuilderFactory;
		private DocumentBuilder docBuilder;
		private Document doc;
		private ResultSet queryResultSet1;
		private ResultSet queryResultSet2;
		private ResultSet queryResultSet3;
		private ResultSet queryResultSet4;
		private ResultSet queryResultSet5;
		private ResultSet queryResultSet6;
		
		String CONTROL_SELECTION_0;
		String LOGON_DATABASE;
		String LOGON_PASSWORD;
		String LOGON_USERNAME;
		
		
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
			this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
			this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
			
			
			
			queryString = "?LOGON_USERNAME="+ LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD +"&LOGON_DATABASE=" 
			+ LOGON_DATABASE +"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
			
			Reporter.logEvent(Status.INFO, "Prepare test data for RIND service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run RIND service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run RIND service", "Running Failed:", false);
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
		
			//Checking in table INDIVIDUAL
			queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRIND1")[1],Stock.GetParameterValue("IND_ID"));
			
			if(DB.getRecordSetCount(queryResultSet1)>0){
				while(queryResultSet1.next()){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database.Table name:INDIVIDUAL", "Records exists in Database.Table name:INDIVIDUAL\n ID="+queryResultSet1.getString("ID")
						+"\nFirstName="+queryResultSet1.getString("FIRST_NAME"), false);
			}}
				else{
					Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
				}
				
			
			//Checking in table RELATED_INDIVIDUAL
			String pa_ind_id = null;
			queryResultSet2 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRIND2")[1],Stock.GetParameterValue("IND_ID"));
		
			if(DB.getRecordSetCount(queryResultSet2)>0){
				while(queryResultSet2.next()){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database.Table name:RELATED_INDIVIDUAL", "Records exists in Database.Table name:RELATED_INDIVIDUAL \n", false);
				
				   pa_ind_id= queryResultSet2.getString("PA_IND_ID");
				   Reporter.logEvent(Status.INFO,"Query run to fetch the pa_ind_id using ind_id.","Query fetch the pa_ind_id:\n"+pa_ind_id, false);
		    queryResultSet3 = DB.executeQuery(General.dbInstance,Stock.getTestQuery("queryForRIND5")[1],pa_ind_id);
				 if(DB.getRecordSetCount(queryResultSet3)>0){
						Reporter.logEvent(Status.PASS, "Validating Records exists in Database.Table name:RELATED_INDIVIDUAL", "Records exists in Database.Table name:RELATED_INDIVIDUAL \n PA_GA_ID="+pa_ind_id, false);  
				 }
				 else
				 {
					 Reporter.logEvent(Status.FAIL, "Validating Records exists in Database.Table name:RELATED_INDIVIDUAL", "Records does not exist in Database.Table name:RELATED_INDIVIDUAL", false);
				 }
			}}
			
			  
			else{
					Reporter.logEvent(Status.FAIL, "Validating Record existence in Database.Table name:RELATED_INDIVIDUAL", "No records exists in Database.Table name:RELATED_INDIVIDUAL \n PA_GA_ID="+pa_ind_id, false);
				}
			
		//Checking in table PART_ARGMNT
			queryResultSet4 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRIND3")[1]);
			
			if(DB.getRecordSetCount(queryResultSet4)>0){
				while(queryResultSet4.next()){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database.Table name:PART_ARGMNT", "Records exists in Database.Table name:PART_ARGMNT \n GA_ID="+queryResultSet4.getString("GA_ID"), false);
			}}
				else{
				Reporter.logEvent(Status.FAIL, "Validating Record existence in Database.Table name:PART_ARGMNT", "No records exists in Database.Table name:PART_ARGMNT \n GA_ID="+queryResultSet4.getString("GA_ID"),false);
				}
			
			
			//Checking in table EMPLOYMENT 
			queryResultSet5 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRIND4")[1]);
			
			if(DB.getRecordSetCount(queryResultSet5)>0){
				while(queryResultSet5.next()){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database.Table name:EMPLOYMENT", "Records exists in Database.Table name:EMPLOYMENT \n GC_ID="+queryResultSet5.getString("GC_ID"), false);
			}}
				else{
					Reporter.logEvent(Status.FAIL, "Validating Record existence in Database.Table name:EMPLOYMENT", "No records exists in Database.Table name:EMPLOYMENT \n GC_ID="+queryResultSet5.getString("GC_ID"), false);
				}
			
			//Checking in table ADDRESS
			queryResultSet6 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRIND6")[1],pa_ind_id);
			
			if(DB.getRecordSetCount(queryResultSet6)>0){
				while(queryResultSet6.next()){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database.Table name:ADDRESS", "Records exists in Database.Table name:ADDRESS \n seq_number="+queryResultSet6.getString("SEQNBR"), false);
			}}
				else{
					Reporter.logEvent(Status.FAIL, "Validating Record existence in Database.Table name:ADDRESS", "No records exists in Database.Table name:ADDRESS \n seq_number="+queryResultSet6.getString("SEQNBR"),false);
				}
			
			
		}	

	
	

}
