package pageobject.CPIO;

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

public class CPIO_Add_Competing_Inv_Option {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CPIO_Add_Competing_Investment_Option";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CPIO1_CODE_0;
	String CPIO1_COMPANY_NAME_0;
	String CPIO1_COMPANY_NAME_0_X1;
	String CPIO1_COMPANY_NAME_0_X2;
	String CPIO1_NAME_0;
	String CPIO1_TYPE_CODE_0;
	String CPIO1_ALLOCATED_IND_0;
	
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
	public void setCPIO1_CODE_0(String cPIO1_CODE_0) {
		CPIO1_CODE_0 = cPIO1_CODE_0;
	}
	public void setCPIO1_COMPANY_NAME_0(String cPIO1_COMPANY_NAME_0) {
		CPIO1_COMPANY_NAME_0 = cPIO1_COMPANY_NAME_0;
	}
	public void setCPIO1_COMPANY_NAME_0_X1(String cPIO1_COMPANY_NAME_0_X1) {
		CPIO1_COMPANY_NAME_0_X1 = cPIO1_COMPANY_NAME_0_X1;
	}
	public void setCPIO1_COMPANY_NAME_0_X2(String cPIO1_COMPANY_NAME_0_X2) {
		CPIO1_COMPANY_NAME_0_X2 = cPIO1_COMPANY_NAME_0_X2;
	}
	public void setCPIO1_NAME_0(String cPIO1_NAME_0) {
		CPIO1_NAME_0 = cPIO1_NAME_0;
	}
	public void setCPIO1_TYPE_CODE_0(String cPIO1_TYPE_CODE_0) {
		CPIO1_TYPE_CODE_0 = cPIO1_TYPE_CODE_0;
	}
	public void setCPIO1_ALLOCATED_IND_0(String cPIO1_ALLOCATED_IND_0) {
		CPIO1_ALLOCATED_IND_0 = cPIO1_ALLOCATED_IND_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCPIO1_ALLOCATED_IND_0(Stock.GetParameterValue("CPIO1_ALLOCATED_IND_0"));
		this.setCPIO1_CODE_0(Stock.GetParameterValue("CPIO1_CODE_0"));
		this.setCPIO1_COMPANY_NAME_0(Stock.GetParameterValue("CPIO1_COMPANY_NAME_0"));
		this.setCPIO1_COMPANY_NAME_0_X1(Stock.GetParameterValue("CPIO1_COMPANY_NAME_0_X1"));
		this.setCPIO1_COMPANY_NAME_0_X2(Stock.GetParameterValue("CPIO1_COMPANY_NAME_0_X2"));
		this.setCPIO1_NAME_0(Stock.GetParameterValue("CPIO1_NAME_0"));
		this.setCPIO1_TYPE_CODE_0(Stock.GetParameterValue("CPIO1_TYPE_CODE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CPIO1_CODE_0="+CPIO1_CODE_0+"&CPIO1_COMPANY_NAME_0="+CPIO1_COMPANY_NAME_0+"&CPIO1_COMPANY_NAME_0_X1="+CPIO1_COMPANY_NAME_0_X1+"&CPIO1_COMPANY_NAME_0_X2="+CPIO1_COMPANY_NAME_0_X2+
				"&CPIO1_NAME_0="+CPIO1_NAME_0+"&CPIO1_TYPE_CODE_0="+CPIO1_TYPE_CODE_0+"&CPIO1_ALLOCATED_IND_0="+CPIO1_ALLOCATED_IND_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for CPIO service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run CPIO service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CPIO service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
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
		String comp_name = this.CPIO1_COMPANY_NAME_0;
		String comp_name_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCPIOQuery")[1],  this.CPIO1_CODE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records exists in Database\nTable Name: COMPETING_INVOPT", false);
			while(queryResultSet.next()){
				comp_name_db = queryResultSet.getString("COMPANY_NAME");
				Reporter.logEvent(Status.INFO, "Values from Database", "CODE: "+queryResultSet.getString("CODE")+
						"\nTYPE_CODE: "+queryResultSet.getString("TYPE_CODE")+
						"\nCOMPANY NAME: "+queryResultSet.getString("COMPANY_NAME")+
						"\nNAME: "+queryResultSet.getString("NAME"), false);
			}
			if(comp_name.equalsIgnoreCase(comp_name_db)){
				Reporter.logEvent(Status.PASS, "Comparing and Validating COMPANY NAME from Input and Database", "Both the values are same as expected"+
						"\nFrom Input: "+comp_name+"\nFrom Database: "+comp_name_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating COMAPNY NAME from Input and Database", "Both the values are not same"+
						"\nFrom Input: "+comp_name+"\nFrom Database: "+comp_name_db, false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
	}
	
	public void FlushData(){
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCPIOAddDelete")[1],  this.CPIO1_CODE_0);
	}
	
}
