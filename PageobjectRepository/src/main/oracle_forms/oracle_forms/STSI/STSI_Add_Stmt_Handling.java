package pageobject.STSI;

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

public class STSI_Add_Stmt_Handling {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STSI_ADD_STMT_HANDLING";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String STMT_HANDLING_INSTRUCT_STATEMENT_HANDLING_CODE_0;
	String LOV_VENDOR_CODE;
	String STMT_HANDLING_INSTRUCT_TYPE_CODE_0;
	String STMT_HANDLING_INSTRUCT_INSTRUCTION_TEXT_0;
	
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
	public void setSTMT_HANDLING_INSTRUCT_STATEMENT_HANDLING_CODE_0(
			String sTMT_HANDLING_INSTRUCT_STATEMENT_HANDLING_CODE_0) {
		STMT_HANDLING_INSTRUCT_STATEMENT_HANDLING_CODE_0 = sTMT_HANDLING_INSTRUCT_STATEMENT_HANDLING_CODE_0;
	}
	public void setLOV_VENDOR_CODE(String lOV_VENDOR_CODE) {
		LOV_VENDOR_CODE = lOV_VENDOR_CODE;
	}
	public void setSTMT_HANDLING_INSTRUCT_TYPE_CODE_0(
			String sTMT_HANDLING_INSTRUCT_TYPE_CODE_0) {
		STMT_HANDLING_INSTRUCT_TYPE_CODE_0 = sTMT_HANDLING_INSTRUCT_TYPE_CODE_0;
	}
	public void setSTMT_HANDLING_INSTRUCT_INSTRUCTION_TEXT_0(
			String sTMT_HANDLING_INSTRUCT_INSTRUCTION_TEXT_0) {
		STMT_HANDLING_INSTRUCT_INSTRUCTION_TEXT_0 = sTMT_HANDLING_INSTRUCT_INSTRUCTION_TEXT_0;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOV_VENDOR_CODE(Stock.GetParameterValue("LOV_VENDOR_CODE"));
		this.setSTMT_HANDLING_INSTRUCT_TYPE_CODE_0(Stock.GetParameterValue("STMT_HANDLING_INSTRUCT_TYPE_CODE_0"));
		this.setSTMT_HANDLING_INSTRUCT_STATEMENT_HANDLING_CODE_0(Stock.GetParameterValue("STMT_HANDLING_INSTRUCT_STATEMENT_HANDLING_CODE_0"));
		this.setSTMT_HANDLING_INSTRUCT_INSTRUCTION_TEXT_0(Stock.GetParameterValue("STMT_HANDLING_INSTRUCT_INSTRUCTION_TEXT_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&STMT_HANDLING_INSTRUCT_STATEMENT_HANDLING_CODE_0="+STMT_HANDLING_INSTRUCT_STATEMENT_HANDLING_CODE_0+"&LOV_VENDOR_CODE="+LOV_VENDOR_CODE+
				"&STMT_HANDLING_INSTRUCT_TYPE_CODE_0="+STMT_HANDLING_INSTRUCT_TYPE_CODE_0+"&STMT_HANDLING_INSTRUCT_INSTRUCTION_TEXT_0="+STMT_HANDLING_INSTRUCT_INSTRUCTION_TEXT_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for STSI Add STMT Handling service", this.queryString.replaceAll("&", "\n"), false);
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
			//System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run STSI service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STSI service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "CONTROL_CFG_FORM_NAME_0: "+ doc.getElementsByTagName("CONTROL_CFG_FORM_NAME_0").item(0).getTextContent()+
					"\nCONTROL_CFG_SIDE_OF_HOUSE_0: "+ doc.getElementsByTagName("CONTROL_CFG_SIDE_OF_HOUSE_0").item(0).getTextContent()+
					"\nCONTROL_CFG_TITLE_0: "+ doc.getElementsByTagName("CONTROL_CFG_TITLE_0").item(0).getTextContent()+
					"\nSTMT_HANDLING_INSTRUCT_VENDOR_CODE_0: "+ doc.getElementsByTagName("STMT_HANDLING_INSTRUCT_VENDOR_CODE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		String vendor_code = doc.getElementsByTagName("STMT_HANDLING_INSTRUCT_VENDOR_CODE_0").item(0).getTextContent();
		String vendor_code_db = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryforSTSIHandling")[1], this.STMT_HANDLING_INSTRUCT_STATEMENT_HANDLING_CODE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Verifies whether record is inserted into Database", "Record inserted into Database\nTableName: STMT_HANDLING_INSTRUCT", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "STATEMENT_HANDLING_CODE: "+queryResultSet.getString("STATEMENT_HANDLING_CODE")+
						"\nVENDOR_CODE: "+queryResultSet.getString("VENDOR_CODE")+
						"\nINSTRUCTION_TEXT: "+queryResultSet.getString("INSTRUCTION_TEXT"), false);
				vendor_code_db = queryResultSet.getString("VENDOR_CODE");	
			}
			if(vendor_code.equalsIgnoreCase(vendor_code_db)){
				Reporter.logEvent(Status.PASS, "Comparing and validating Vendor code from Response and Database", 
						"Both the values are same as expected\n"+"From Response: "+vendor_code+"\nFrom Database: "+vendor_code_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and validating Vendor code from Response and Database", 
						"Both the values are not same \n"+"From Response: "+vendor_code+"\nFrom Database: "+vendor_code_db, false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Verifies whether record is inserted into Database", "Record not inserted into Database\nTableName: STMT_HANDLING_INSTRUCT", false);
		}
	}
	
	public void flushData() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryforSTSIHandlingDelete")[1], this.STMT_HANDLING_INSTRUCT_STATEMENT_HANDLING_CODE_0);
	}
}
