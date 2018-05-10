package pageobject.KOSP;

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

public class KOSP_Process {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/KOSP_Process";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String RMNC1_ID_0;
	String RMNC1_ID_0_X1;
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
	public void setRMNC1_ID_0(String rMNC1_ID_0) {
		RMNC1_ID_0 = rMNC1_ID_0;
	}
	public void setRMNC1_ID_0_X1(String rMNC1_ID_0_X1) {
		RMNC1_ID_0_X1 = rMNC1_ID_0_X1;
	}
	
	public void setServiceParameters()
	{		
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setRMNC1_ID_0(Stock.GetParameterValue("RMNC1_ID_0"));
		this.setRMNC1_ID_0_X1(Stock.GetParameterValue("RMNC1_ID_0_X1"));
		
		queryString = "?CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
				+"&LOGON_DATABASE=" + LOGON_DATABASE 
				+ "&LOGON_PASSWORD=" + LOGON_PASSWORD 
				+"&LOGON_USERNAME=" + LOGON_USERNAME
				+ "&RMNC1_ID_0="+RMNC1_ID_0+"&RMNC1_ID_0_X1="+RMNC1_ID_0_X1
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for KOSP service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService()
	{
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
			Reporter.logEvent(Status.PASS, "Run KOSP service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run KOSP service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if(error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Checking if error tag is empty", "Error tag is empty", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if error tag is empty", "Error tag is not empty:"+error, false);
		}
	}
	
	public void validateInDatabase() throws SQLException
	{
		String rmncId = this.RMNC1_ID_0_X1;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForKOSP1")[1], rmncId);
		if(DB.getRecordSetCount(queryResultSet)>0){
			while(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Update the status Process_status_code=’D’ if drop or ‘RJ’ if there is error in data",
					"Status Code in table AG_REMIT: "+queryResultSet.getString("PROCESS_STATUS_CODE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Checking whether data exists in DB", "Records doesn't exist in DB\nAG_REMIT", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForKOSP2")[1], rmncId);
		if(DB.getRecordSetCount(queryResultSet)>0){
			while(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Update the status Process_status_code=’D’ if drop or ‘RJ’ if there is error in data",
					"Status Code in table IND_REMIT: "+queryResultSet.getString("PROCESS_STATUS_CODE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Checking whether data exists in DB", "Records doesn't exist in DB\nIND_REMIT", false);
		}
	}
}
