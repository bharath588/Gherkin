package pageobject.CSGS;

import generallib.General;

import java.io.File;
import java.io.FileNotFoundException;
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

public class CSGS_Query_Group_Summary_Form {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CSGS_Query_Group_Summary_Form";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	
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
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		
			 		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for CSGS service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run  CSGS service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run CSGS service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From response: ","STD_RULE_ID_0: "+doc.getElementsByTagName("STD_RULE_ID_0").item(0).getTextContent()+
					"\nGA_ID: "+doc.getElementsByTagName("Error").item(0).getTextContent()
					,false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
		
	}
	
	public void validateInDatabase() throws SQLException{
		String plan_id = null;
		String prod_id = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSGS1")[1], this.GET_GA_GA_ID_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Verifying records existence in DB", "Records exists in DB\nTable: GROUP_ACCOUNT", false);
			while(queryResultSet.next()){
				plan_id = queryResultSet.getString("PLAN_ID");
				prod_id = queryResultSet.getString("PROD_ID");
				
				Reporter.logEvent(Status.INFO, "Values from DB", "EFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nSTATUS_CODE: "+queryResultSet.getString("STATUS_CODE")+
						"\nPLAN_ID: "+queryResultSet.getString("PLAN_ID")+
						"\nPROD_ID: "+queryResultSet.getString("PROD_ID"), false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Verifying records existence in DB", "Records doesn't exists in DB", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSGS2")[1], this.GET_GA_GA_ID_0.split("-")[0]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Verifying records existence in DB", "Records exists in DB\nTable: GROUP_CLIENT", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "ID: "+queryResultSet.getString("ID")+
						"\nTAX_ID: "+queryResultSet.getString("TAX_ID")+
						"\nNAME: "+queryResultSet.getString("NAME"), false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Verifying records existence in DB", "Records doesn't exists in DB", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSGS3")[1], prod_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Verifying records existence in DB", "Records exists in DB\nTable: PRODUCT", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "PROD_ID: "+queryResultSet.getString("ID")+
						"\nIRSRL_CODE: "+queryResultSet.getString("IRSRL_CODE")+
						"\nNAME: "+queryResultSet.getString("NAME")+
						"\nPROV_COMPANY: "+queryResultSet.getString("PROV_COMPANY"), false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Verifying records existence in DB", "Records doesn't exists in DB", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSGS4")[1], plan_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Verifying records existence in DB", "Records exists in DB\nTable: PLAN", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "ID: "+queryResultSet.getString("ID")+
						"\nNAME: "+queryResultSet.getString("NAME")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE"), false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Verifying records existence in DB", "Records doesn't exists in DB", false);
		}
		
		/*queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSGS5")[1], plan_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Verifying records existence in DB", "Records exists in DB\nTable: PLAN_HISTORY", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "PLAN_ID: "+queryResultSet.getString("PLAN_ID")+
						"\nEFFDATE: "+queryResultSet.getString("PLAN_ID")+
						"\nPLAN_DOC_TYPE: "+queryResultSet.getString("PLAN_DOC_TYPE")+
						"\nRECEIPT_DATE: "+queryResultSet.getString("RECEIPT_DATE")+
						"\nDESCR: "+queryResultSet.getString("DESCR"), false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Verifying records existence in DB", "Records doesn't exists in DB", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCSGS6")[1], this.GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Verifying records existence in DB", "Records exists in DB\nTable: GA_WK_ASGNMT_CRIT", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "GA_ID: "+queryResultSet.getShort("GA_ID"), false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Verifying records existence in DB", "Records doesn't exists in DB", false);
		}*/
	}
	
	public void checkInFolder() throws FileNotFoundException{
		
		File output = new File("");
		if(output.exists()){
			
		}
	}
}
