package pageobject.MINP;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class MINP_Test_Query {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MINP_Test_Query_2";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
//	private ResultSet queryResultSet2;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String TXN1_CODE_0 ;
	
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
	public void setTXN1_CODE_0(String tXN1_CODE_0) {
		TXN1_CODE_0 = tXN1_CODE_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setTXN1_CODE_0(Stock.GetParameterValue("TXN1_CODE_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&TXN1_CODE_0="+TXN1_CODE_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for MINP TEST QUERY service", this.queryString.replaceAll("&", "\n"), false);
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
				System.out.println(serviceURL);
				doc.getDocumentElement().normalize();
				Reporter.logEvent(Status.PASS, "Run MINP_Test_Query service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run MINP_Test_Query service", "Running Failed:", false);
			}
			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			
			String txn_code = doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent();
			String txn_descr = doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent();
			Reporter.logEvent(Status.INFO, "From Response: ", "TRANSACTION CODE: "+txn_code+"\nTRANSACTION DESCRIPTION: "+txn_descr, false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		
	}
	
	public void validateInDatabase()throws SQLException{
		String txn_code_db = null;
		String txn_code = this.TXN1_CODE_0;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryMinp")[1], this.TXN1_CODE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating whether data exists for TXN_ID", "Records exists in DATABASE\nTableName: INPUT_PARM", false);

			while(queryResultSet.next()){	
			txn_code_db = queryResultSet.getString("TXN_CODE");
			Reporter.logEvent(Status.INFO, "Validate DAtabase:\nTable: INPUT_PARM ", "TXN_CODE: "+queryResultSet.getString("TXN_CODE")+
				"\nSEQ NBR: "+queryResultSet.getString("SEQNBR")+
				"\nMAND IND: "+queryResultSet.getString("MAND_IND")+
				"\nPARM NAME: "+queryResultSet.getString("PARM_NAME")+"\n", false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating whether data exists for TXN_ID", "Records doesn't exists in DATABASE\nTableName: INPUT_PARM", false);
		}
		if(txn_code_db.equalsIgnoreCase(txn_code)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Tax Transaction ID from Input and Database", "Both the values are same as Expected"+
					"\nTax Transaction: "+"From Input: "+txn_code+"\nFrom Database: "+txn_code_db, false);
		}
	}
	

}
