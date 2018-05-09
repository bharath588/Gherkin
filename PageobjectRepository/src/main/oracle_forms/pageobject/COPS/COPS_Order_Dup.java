package pageobject.COPS;

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

public class COPS_Order_Dup {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/COPS_ORDER_DUP";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GROUP_HEADER_GA_ID_0;
	String STMT_ACTY_PERD_SELECT_0;
	
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
	public void setGROUP_HEADER_GA_ID_0(String gROUP_HEADER_GA_ID_0) {
		GROUP_HEADER_GA_ID_0 = gROUP_HEADER_GA_ID_0;
	}
	public void setSTMT_ACTY_PERD_SELECT_0(String sTMT_ACTY_PERD_SELECT_0) {
		STMT_ACTY_PERD_SELECT_0 = sTMT_ACTY_PERD_SELECT_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGROUP_HEADER_GA_ID_0(Stock.GetParameterValue("GROUP_HEADER_GA_ID_0"));
		this.setSTMT_ACTY_PERD_SELECT_0(Stock.GetParameterValue("STMT_ACTY_PERD_SELECT_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GROUP_HEADER_GA_ID_0="+GROUP_HEADER_GA_ID_0+"&STMT_ACTY_PERD_SELECT_0="+STMT_ACTY_PERD_SELECT_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for COPS service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run COPS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run COPS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			}
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCOPS")[1],  this.GROUP_HEADER_GA_ID_0);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records is inserted into table", false);
			Reporter.logEvent(Status.INFO, "From DATABASE: \nTable Name: STMT_TO_DO", "AG_ID: "+queryResultSet.getString("AG_ID")+
					"\nIND_ID: "+queryResultSet.getString("IND_ID")+					
					"\nEV_ID: "+queryResultSet.getString("EV_ID")+
					"\nDMTY_CODE: "+queryResultSet.getString("DMTY_CODE")+
					"\nBEGIN EFFDATE: "+queryResultSet.getString("BEGIN_EFFDATE")+
					"\nEND EFFDATE: "+queryResultSet.getString("END_EFFDATE")+
					"\nJOB BATCH NO: "+queryResultSet.getString("JOB_BATCH_NO")+
					"\nRUN_DATE_TIME: "+queryResultSet.getString("RUN_DATE_TIME"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
	}
}
