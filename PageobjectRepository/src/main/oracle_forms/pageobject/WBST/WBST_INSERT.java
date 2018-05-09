package pageobject.WBST;

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

public class WBST_INSERT {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/WBST_INSERT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet1;
	//private ResultSet queryResultSet2;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String DOC_LOCATION_RULE_DETAIL_DLR_CODE_0;
	String SDIO_ID_LOV;
	String DOC_LOCATION_RULE_DETAIL_LOCATION_0;
	String DOC_LOCATION_RULE_DETAIL_EFFDATE_0;
	
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

	public void setDOC_LOCATION_RULE_DETAIL_DLR_CODE_0(
			String dOC_LOCATION_RULE_DETAIL_DLR_CODE_0) {
		DOC_LOCATION_RULE_DETAIL_DLR_CODE_0 = dOC_LOCATION_RULE_DETAIL_DLR_CODE_0;
	}

	public void setSDIO_ID_LOV(String sDIO_ID_LOV) {
		SDIO_ID_LOV = sDIO_ID_LOV;
	}

	public void setDOC_LOCATION_RULE_DETAIL_LOCATION_0(
			String dOC_LOCATION_RULE_DETAIL_LOCATION_0) {
		DOC_LOCATION_RULE_DETAIL_LOCATION_0 = dOC_LOCATION_RULE_DETAIL_LOCATION_0;
	}

	public void setDOC_LOCATION_RULE_DETAIL_EFFDATE_0(
			String dOC_LOCATION_RULE_DETAIL_EFFDATE_0) {
		DOC_LOCATION_RULE_DETAIL_EFFDATE_0 = dOC_LOCATION_RULE_DETAIL_EFFDATE_0;
	}

	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setDOC_LOCATION_RULE_DETAIL_DLR_CODE_0(Stock.GetParameterValue("DOC_LOCATION_RULE_DETAIL_DLR_CODE_0"));
	     this.setSDIO_ID_LOV(Stock.GetParameterValue("SDIO_ID_LOV"));
	     this.setDOC_LOCATION_RULE_DETAIL_LOCATION_0(Stock.GetParameterValue("DOC_LOCATION_RULE_DETAIL_LOCATION_0"));
	     this.setDOC_LOCATION_RULE_DETAIL_EFFDATE_0(Stock.GetParameterValue("DOC_LOCATION_RULE_DETAIL_EFFDATE_0"));
		 		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME
				 +"&LOGON_PASSWORD="+LOGON_PASSWORD
				 +"&LOGON_DATABASE="+LOGON_DATABASE
				 +"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0
				 +"&DOC_LOCATION_RULE_DETAIL_DLR_CODE_0="+DOC_LOCATION_RULE_DETAIL_DLR_CODE_0
				 +"&SDIO_ID_LOV="+SDIO_ID_LOV
				 +"&DOC_LOCATION_RULE_DETAIL_LOCATION_0="+DOC_LOCATION_RULE_DETAIL_LOCATION_0
				 +"&DOC_LOCATION_RULE_DETAIL_EFFDATE_0="+DOC_LOCATION_RULE_DETAIL_EFFDATE_0
				 +"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for WBST service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run WBST service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run WBST service", "Running Failed:", false);
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
		
	}
   public void cleanup() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForWBST")[1]);
		
		if(DB.getRecordSetCount(queryResultSet)>0)
		{
			Reporter.logEvent(Status.INFO, "Validating Records exists in Database", "Records exists in Database", false);
			queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDeleteWBST")[1]);
			   
			
		}
		else{
			Reporter.logEvent(Status.INFO, "Validating Records exists in Database", "No record found in databse", false);
		}
		
		
 
	}

   public void validateInDatabase()throws SQLException{
	   queryResultSet1 = DB.executeQuery(General.dbInstance,Stock.getTestQuery("queryForWBST")[1]);
	   if(DB.getRecordSetCount(queryResultSet1)>0)
		{
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
			
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "No record found in databse", false);
		}
		
   }
	
}



/*package pageobject.WBST;

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

public class WBST_INSERT {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/WBST_INSERT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet1;
	//private ResultSet queryResultSet2;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String DOC_LOCATION_RULE_DETAIL_DLR_CODE_0;
	String SDIO_ID_LOV;
	String DOC_LOCATION_RULE_DETAIL_LOCATION_0;
	String DOC_LOCATION_RULE_DETAIL_EFFDATE_0;
	
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

	public void setDOC_LOCATION_RULE_DETAIL_DLR_CODE_0(
			String dOC_LOCATION_RULE_DETAIL_DLR_CODE_0) {
		DOC_LOCATION_RULE_DETAIL_DLR_CODE_0 = dOC_LOCATION_RULE_DETAIL_DLR_CODE_0;
	}

	public void setSDIO_ID_LOV(String sDIO_ID_LOV) {
		SDIO_ID_LOV = sDIO_ID_LOV;
	}

	public void setDOC_LOCATION_RULE_DETAIL_LOCATION_0(
			String dOC_LOCATION_RULE_DETAIL_LOCATION_0) {
		DOC_LOCATION_RULE_DETAIL_LOCATION_0 = dOC_LOCATION_RULE_DETAIL_LOCATION_0;
	}

	public void setDOC_LOCATION_RULE_DETAIL_EFFDATE_0(
			String dOC_LOCATION_RULE_DETAIL_EFFDATE_0) {
		DOC_LOCATION_RULE_DETAIL_EFFDATE_0 = dOC_LOCATION_RULE_DETAIL_EFFDATE_0;
	}

	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setDOC_LOCATION_RULE_DETAIL_DLR_CODE_0(Stock.GetParameterValue("DOC_LOCATION_RULE_DETAIL_DLR_CODE_0"));
	     this.setSDIO_ID_LOV(Stock.GetParameterValue("SDIO_ID_LOV"));
	     this.setDOC_LOCATION_RULE_DETAIL_LOCATION_0(Stock.GetParameterValue("DOC_LOCATION_RULE_DETAIL_LOCATION_0"));
	     this.setDOC_LOCATION_RULE_DETAIL_EFFDATE_0(Stock.GetParameterValue("DOC_LOCATION_RULE_DETAIL_EFFDATE_0"));
		 		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME
				 +"&LOGON_PASSWORD="+LOGON_PASSWORD
				 +"&LOGON_DATABASE="+LOGON_DATABASE
				 +"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0
				 +"&DOC_LOCATION_RULE_DETAIL_DLR_CODE_0="+DOC_LOCATION_RULE_DETAIL_DLR_CODE_0
				 +"&SDIO_ID_LOV="+SDIO_ID_LOV
				 +"&DOC_LOCATION_RULE_DETAIL_LOCATION_0="+DOC_LOCATION_RULE_DETAIL_LOCATION_0
				 +"&DOC_LOCATION_RULE_DETAIL_EFFDATE_0="+DOC_LOCATION_RULE_DETAIL_EFFDATE_0
				 +"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for WBST service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run WBST service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run WBST service", "Running Failed:", false);
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
		
	}
   public void cleanup() throws SQLException{
	   String sdio_id = doc.getElementsByTagName("DOC_LOCATION_RULE_DETAIL_SDIO_ID_0").item(0).getTextContent();
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDeleteWBST")[1], this.DOC_LOCATION_RULE_DETAIL_DLR_CODE_0, sdio_id);
		   Reporter.logEvent(Status.INFO, "Cleaning up Data for Re do test", "Data has been Reset", false);			   
		
	}

   public void validateInDatabase()throws SQLException{
	   String sdio_id = doc.getElementsByTagName("DOC_LOCATION_RULE_DETAIL_SDIO_ID_0").item(0).getTextContent();
	   String sdio_id_db = null;
	   
	   queryResultSet1 = DB.executeQuery(General.dbInstance,Stock.getTestQuery("queryForWBST")[1]);
	   if(DB.getRecordSetCount(queryResultSet1)>0)
		{
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTablename: DOC_LOCATION_RULE_DETAIL", false);
			while(queryResultSet1.next()){
				sdio_id_db = queryResultSet1.getString("SDIO_ID");
				Reporter.logEvent(Status.INFO, "Values from DB", "DLR_CODE: "+queryResultSet1.getString("DLR_CODE")+
						"\nDMTY_CODE: "+queryResultSet1.getString("DMTY_CODE")+
						"\nSDIO_ID: "+queryResultSet1.getString("SDIO_ID")+
						"\nLOCATION: "+queryResultSet1.getString("LOCATION")+
						"\nEFFDATE: "+queryResultSet1.getString("EFFDATE"), false);
			}
			if(sdio_id.equalsIgnoreCase(sdio_id_db)){
				Reporter.logEvent(Status.PASS, "Comparing and Validating SDIO_ID field from Response and Database", "Both the values are same as expected"+
						"\nFrom Response: "+sdio_id+"\nFrom DB: "+sdio_id_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating SDIO_ID field from Response and Database", "Both the values are not same "+
						"\nFrom Response: "+sdio_id+"\nFrom DB: "+sdio_id_db, false);
			}
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "No record found in databse", false);
		}
		
   }
	
}*/