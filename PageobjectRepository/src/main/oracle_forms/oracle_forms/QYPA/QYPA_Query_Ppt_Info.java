 package pageobject.QYPA;

import generallib.DateCompare;
import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class QYPA_Query_Ppt_Info {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/QYPA_Test_Query_Participat";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	
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
	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&LOGON_DATABASE="+LOGON_DATABASE+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for QYPA service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run QYPA service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run QYPA service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","CONTROL_PLAN_NAME_0: "+doc.getElementsByTagName("CONTROL_PLAN_NAME_0").item(0).getTextContent()+
					"\nCONTROL_PROD_ID_0: "+doc.getElementsByTagName("CONTROL_PROD_ID_0").item(0).getTextContent()+
					"\nADDRESS_VALIDATION_SOURCE_CODE_0: "+doc.getElementsByTagName("ADDRESS_VALIDATION_SOURCE_CODE_0").item(0).getTextContent()+
					"\nINDIVIDUAL_LAST_NAME_0: "+doc.getElementsByTagName("INDIVIDUAL_LAST_NAME_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase() throws SQLException{
		
		String last_name = doc.getElementsByTagName("INDIVIDUAL_LAST_NAME_0").item(0).getTextContent();
		String hire_date = doc.getElementsByTagName("EMPLOYMENT_LOA_LOA_HIREDATE_DISPLAY_0").item(0).getTextContent();
		String hire_date_db = null;
		Date hire_date_db_new = null;
		String ind_id = null;
		String ga_id = null;
		String last_name_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForQYPA1")[1], this.CONTROL_SSN_DISPL_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: PART_AGRMT", false);
			while(queryResultSet.next()){
				ind_id = queryResultSet.getString("IND_ID");
				ga_id = queryResultSet.getString("GA_ID");
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "GA_ID:  "+ queryResultSet.getString("GA_ID")+
		 				"\nIND_ID: "+ queryResultSet.getString("IND_ID")+
						"\nSTATUS_EFFDATE: "+queryResultSet.getString("STATUS_EFFDATE"), false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForQYPA2")[1], ind_id);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: INDIVIDUAL", false);
			while(queryResultSet.next()){
				last_name_db = queryResultSet.getString("LAST_NAME");
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ID:  "+ queryResultSet.getString("ID")+
						"\nLAST_NAME: "+ queryResultSet.getString("LAST_NAME")+
						"\nMAILING_NAME: "+queryResultSet.getString("MAILING_NAME_1"), false);
			}
			if(last_name.equalsIgnoreCase(last_name_db)){
				Reporter.logEvent(Status.PASS, "Comparing and Verifying LAST_NAME from Response and Database", "Both the values are same as expected"+
						"\nFrom Response: " +last_name+"\nFrom Database: "+last_name_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Verifying LAST_NAME from Response and Database", "Both the values are not same"+
						"\nFrom Response: " +last_name+"\nFrom Database: "+last_name_db, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForQYPA3")[1], ind_id, ga_id);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: INVOPT_ALLOC", false);
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Total number of Records: "+DB.getRecordSetCount(queryResultSet), false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForQYPA4")[1], ind_id);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: EMPLOYMENT", false);
			while(queryResultSet.next()){
				hire_date_db = queryResultSet.getString("TO_CHAR(HIRE_DATE,'DD/MM/YYYY')").replaceAll("/","-");
									
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "GC_ID:  "+ queryResultSet.getString("GC_ID")+
						"\nIND_ID: "+ queryResultSet.getString("IND_ID")+
						"\nHIRE_DATE: "+queryResultSet.getString("TO_CHAR(HIRE_DATE,'DD/MM/YYYY')"), false);
			}			
			
			/*if(hire_date.equalsIgnoreCase(hire_date_db_new.toString())){
				Reporter.logEvent(Status.PASS, "Comparing and Verifying HIRE_DATE from Response and Database", "Both the values are same as expected"+
						"\nFrom Response: " +hire_date+"\nFrom Database: "+hire_date_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Verifying HIRE_DATE from Response and Database", "Both the values are not same"+
						"\nFrom Response: " +hire_date+"\nFrom Database: "+hire_date_db, false);
			}*/
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
}
