/**
 * 
 */
package pageobject.MLKS;

import generallib.DateCompare;
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
public class MLKS_Maintain_Locks {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MLKS_Maintain_Locks";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String MLKS1_GA_ID_0;
	String MLKS1_EFFDATE_0;
	String MLKS1_DESCR_0;
	
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
	public void setMLKS1_GA_ID_0(String mLKS1_GA_ID_0) {
		MLKS1_GA_ID_0 = mLKS1_GA_ID_0;
	}
	public void setMLKS1_EFFDATE_0(String mLKS1_EFFDATE_0) {
		MLKS1_EFFDATE_0 = mLKS1_EFFDATE_0;
	}
	public void setMLKS1_DESCR_0(String mLKS1_DESCR_0) {
		MLKS1_DESCR_0 = mLKS1_DESCR_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setMLKS1_GA_ID_0(Stock.GetParameterValue("MLKS1_GA_ID_0"));
		this.setMLKS1_EFFDATE_0(DateCompare.GenerateFutureDate(0));
		this.setMLKS1_DESCR_0(Stock.GetParameterValue("MLKS1_DESCR_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&MLKS1_GA_ID_0="+MLKS1_GA_ID_0.toUpperCase()+"&MLKS1_EFFDATE_0="+MLKS1_EFFDATE_0+"&MLKS1_DESCR_0="+MLKS1_DESCR_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for MLKS_Maintain_Locks service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run MLKS_Maintain_Locks service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run MLKS_Maintain_Locks service", "Running Failed:", false);
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
	queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("mklsMaintainLocks")[1],MLKS1_GA_ID_0);
	String effdate=null;
	if(DB.getRecordSetCount(queryResultSet)>0){
		Reporter.logEvent(Status.PASS, "Validate Record is inserted in DB.\nTable :ACTY_LOCK", "Record exists in DB.", false);
		if(queryResultSet.next()){
			effdate = queryResultSet.getString("EFFDATE");
		}
		Reporter.logEvent(Status.INFO,"From DB:","EFFDATE:"+effdate , false);
	}
	else{
		Reporter.logEvent(Status.FAIL, "Validate Record is inserted in Database", "Record is not inserted in database", false);
	}
}
	
public void flushData(){
	queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("deletemklsrecord")[1], MLKS1_GA_ID_0);
	Reporter.logEvent(Status.INFO,"Reset the data.","Data has been reset.\nQuery:"+Stock.getTestQuery("deletemklsrecord")[1], false);
	}
}
