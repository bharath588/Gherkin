package pageobject.PRRE;

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

public class PRRE_Query_Product_Restrict_Info {

	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PRRE_Query_Product_Restriction_Information";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_PROD_ID_0;
	
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
	public void setGET_PROD_ID_0(String gET_PROD_ID_0) {
		GET_PROD_ID_0 = gET_PROD_ID_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_PROD_ID_0(Stock.GetParameterValue("GET_PROD_ID_0"));		
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&GET_PROD_ID_0="+GET_PROD_ID_0+"&LOGON_DATABASE="+LOGON_DATABASE+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for PRRE service", this.queryString.replaceAll("&", "\n"), false);
		
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
//			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run PRRE service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PRRE service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response", "PROD HEADER: "+doc.getElementsByTagName("PROD_HEADER1_ID_0").item(0).getTextContent()+
					"\nPROD HEADER NAME: "+doc.getElementsByTagName("PROD_HEADER1_NAME_0").item(0).getTextContent()+
					"\nDESCR: "+doc.getElementsByTagName("PURS1_DESCR1_0").item(0).getTextContent()+
					"\nSDMT_CODE: "+doc.getElementsByTagName("PURS1_SDMT_CODE_0").item(0).getTextContent()+
					"\nCNTY_CODE: "+doc.getElementsByTagName("PURS2_CNTY_CODE_0").item(0).getTextContent()+
					"\nMK TYPE CODE: "+doc.getElementsByTagName("PURS3_MK_TYPE_CODE_0").item(0).getTextContent()+
					"\nMK SUBTYPE CODE: "+doc.getElementsByTagName("PURS3_MK_SUBTYPE_CODE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String prod_id = this.GET_PROD_ID_0;
		String sdmt_code = doc.getElementsByTagName("PURS1_SDMT_CODE_0").item(0).getTextContent();
		String cnty_code = doc.getElementsByTagName("PURS2_CNTY_CODE_0").item(0).getTextContent();
		String type_code = doc.getElementsByTagName("PURS3_MK_TYPE_CODE_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPRRE1")[1], prod_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: PROD_RESTRC", false);
			Reporter.logEvent(Status.INFO, "Values from Database", "Total number of Records: "+DB.getRecordSetCount(queryResultSet), false);
						
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPRRE2")[1],  prod_id, sdmt_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: PROD_RESTRC", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "PROD ID: "+queryResultSet.getString("PROD_ID")+
						"\nSEQNBR: "+queryResultSet.getString("SEQNBR")+
						"\nSDMT_CODE: "+queryResultSet.getString("SDMT_CODE"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPRRE3")[1], prod_id, cnty_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: PROD_RESTRC", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "PROD ID: "+queryResultSet.getString("PROD_ID")+
						"\nSEQNBR: "+queryResultSet.getString("SEQNBR")+
						"\nCNTY_CODE: "+queryResultSet.getString("CNTY_CODE"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPRRE4")[1], prod_id, type_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: PROD_RESTRC", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "PROD ID: "+queryResultSet.getString("PROD_ID")+
						"\nSEQNBR: "+queryResultSet.getString("SEQNBR")+
						"\nTYPE_CODE: "+queryResultSet.getString("MK_TYPE_CODE"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
	}
	
}
