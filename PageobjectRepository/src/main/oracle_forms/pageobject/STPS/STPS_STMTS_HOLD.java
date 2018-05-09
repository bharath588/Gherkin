/**
 * 
 */
package pageobject.STPS;

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

/**
 * @author smykjn
 *
 */
public class STPS_STMTS_HOLD {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STPS_STMTS_HOLD";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	
	String stmt_code;
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String AG1_STMT_PROC_CODE_0;
	String AG1_STMT_PROC_CODE_0_X1;
	
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
	public void setAG1_STMT_PROC_CODE_0(String aG1_STMT_PROC_CODE_0) {
		AG1_STMT_PROC_CODE_0 = aG1_STMT_PROC_CODE_0;
	}
	public void setAG1_STMT_PROC_CODE_0_X1(String aG1_STMT_PROC_CODE_0_X1) {
		AG1_STMT_PROC_CODE_0_X1 = aG1_STMT_PROC_CODE_0_X1;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setAG1_STMT_PROC_CODE_0(Stock.GetParameterValue("AG1_STMT_PROC_CODE_0"));
		this.setAG1_STMT_PROC_CODE_0_X1(Stock.GetParameterValue("AG1_STMT_PROC_CODE_0_X1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&AG1_STMT_PROC_CODE_0="+AG1_STMT_PROC_CODE_0+"&AG1_STMT_PROC_CODE_0_X1="+AG1_STMT_PROC_CODE_0_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for STPS_STMTS_HOLD service", this.queryString.replaceAll("&", "\n"), false);
		
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
			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run STPS_STMTS_HOLD service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STPS_STMTS_HOLD service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response:", "AGIL1_GA_ID_0="+doc.getElementsByTagName("AGIL1_GA_ID_0").item(0).getTextContent(), false);
		}
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("selectAcctGrpg")[1],GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validate Records exist in DB.\nTable :acct_grpg", "Record exists in DB.", false);
			if(queryResultSet.next()){
				 stmt_code = queryResultSet.getString("STMT_PROC_CODE");
				Reporter.logEvent(Status.INFO,"From DB:","STMT_PROC_CODE:"+stmt_code, false);
			}
			if(AG1_STMT_PROC_CODE_0_X1.equals(stmt_code))
				Reporter.logEvent(Status.PASS,"Validate stmt_proc_code is updated to HOLD.","STMT_PROC_CODE is updated.\n"
						+ "Expected code:"+AG1_STMT_PROC_CODE_0_X1+"\nActual termdate:"+stmt_code, false);
			else
				Reporter.logEvent(Status.FAIL,"Validate stmt_proc_code is updated to HOLD.","STMT_PROC_CODE is not updated.\n"
						+ "Expected code:"+AG1_STMT_PROC_CODE_0_X1+"\nActual termdate:"+stmt_code, false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
	public void flushData(){
		queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("UpdateStmtProcCode")[1], GET_GA_GA_ID_0);
		Reporter.logEvent(Status.INFO,"Reset the data.","Data has been reset.\nQuery:"+Stock.getTestQuery("UpdateStmtProcCode")[1], false);
	}
	
}
