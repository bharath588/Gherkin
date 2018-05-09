/**
 * 
 */
package pageobject.PURV;

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
public class GQ19PURV {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PURV_insertmode";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_PROD_ID_0;
	String PROD_RECOV_STRUC_RECOV_ID_1;
	String PROD_RECOV_STRUC_EFFDATE_1;
	
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
	public void setPROD_RECOV_STRUC_RECOV_ID_1(String pROD_RECOV_STRUC_RECOV_ID_1) {
		PROD_RECOV_STRUC_RECOV_ID_1 = pROD_RECOV_STRUC_RECOV_ID_1;
	}
	public void setPROD_RECOV_STRUC_EFFDATE_1(String pROD_RECOV_STRUC_EFFDATE_1) {
		PROD_RECOV_STRUC_EFFDATE_1 = pROD_RECOV_STRUC_EFFDATE_1;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_PROD_ID_0(Stock.GetParameterValue("GET_PROD_ID_0"));
		this.setPROD_RECOV_STRUC_RECOV_ID_1(Stock.GetParameterValue("PROD_RECOV_STRUC_RECOV_ID_1"));
		this.setPROD_RECOV_STRUC_EFFDATE_1(Stock.GetParameterValue("PROD_RECOV_STRUC_EFFDATE_1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_PROD_ID_0="+GET_PROD_ID_0+"&PROD_RECOV_STRUC_RECOV_ID_1="+PROD_RECOV_STRUC_RECOV_ID_1+"&PROD_RECOV_STRUC_EFFDATE_1="+PROD_RECOV_STRUC_EFFDATE_1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for PURV_insertmode service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run PURV_insertmode service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PURV_insertmode service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response:", "PROD_RECOV_STRUC_RECOV_ID_0="+doc.getElementsByTagName("PROD_RECOV_STRUC_RECOV_ID_0").item(0).getTextContent(), false);
		}
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
public void validateInDatabase() throws SQLException{
	queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("purvInsertMode")[1],GET_PROD_ID_0,PROD_RECOV_STRUC_RECOV_ID_1);
	String recov_id=null,prod_id=null,effdate=null;
	if(DB.getRecordSetCount(queryResultSet)>0){
		Reporter.logEvent(Status.PASS, "Validate Record is inserted in DB.\nTable :prod_recov_struc", "Record exists in DB.", false);
		if(queryResultSet.next()){
			recov_id = queryResultSet.getString("RECOV_ID");
			prod_id = queryResultSet.getString("PROD_ID");
			effdate = queryResultSet.getString("EFFDATE");
		}
		Reporter.logEvent(Status.INFO,"From DB:","RECOV_ID:"+recov_id+"\nPROd_ID:"+prod_id+"\nEFFDATE:"+effdate , false);
	}
	else{
		Reporter.logEvent(Status.FAIL, "Validate Record is inserted in Database", "Record is not inserted in database", false);
	}
}
	
public void flushData(){
	queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("updatePurvInsertMode")[1], GET_PROD_ID_0,PROD_RECOV_STRUC_RECOV_ID_1);
	Reporter.logEvent(Status.INFO,"Reset the data.","Data has been reset.\nQuery:"+Stock.getTestQuery("updatePurvInsertMode")[1], false);
	}
}
