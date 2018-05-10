/**
 * 
 */
package pageobject.GRAC;

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
public class GRAC_Change_Group_Account_Basic_Information {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GRAC_Change_Group_Account_Basic_Information";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String GA1_TEMP_PIN_ALLOWED_IND_0;

	
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
	public void setGA1_TEMP_PIN_ALLOWED_IND_0(String gA1_TEMP_PIN_ALLOWED_IND_0) {
		GA1_TEMP_PIN_ALLOWED_IND_0 = gA1_TEMP_PIN_ALLOWED_IND_0;
	}
	
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setGA1_TEMP_PIN_ALLOWED_IND_0(Stock.GetParameterValue("GA1_TEMP_PIN_ALLOWED_IND_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&GA1_TEMP_PIN_ALLOWED_IND_0="+GA1_TEMP_PIN_ALLOWED_IND_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for GRAC_Change_Group_Account_Basic_Information service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run GRAC_Change_Group_Account_Basic_Information service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run GRAC_Change_Group_Account_Basic_Information service", "Running Failed:", false);
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
	queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGRAC")[1], this.GET_GA_GA_ID_0);
	String tempPinAlllwd=null;
	if(DB.getRecordSetCount(queryResultSet)>0){
		Reporter.logEvent(Status.PASS, "Validate Record exists in DB.\nTable :GROUP_ACCOUNT", "Record exists in DB.", false);
		while(queryResultSet.next()){
			tempPinAlllwd = queryResultSet.getString("TEMP_PIN_ALLOWED_IND");
			Reporter.logEvent(Status.INFO, "Values from DB", "TEMP_PIN_ALLOWED_IND: "+queryResultSet.getString("TEMP_PIN_ALLOWED_IND"), false);
		}
		if(tempPinAlllwd.equals(this.GA1_TEMP_PIN_ALLOWED_IND_0))
			Reporter.logEvent(Status.PASS,"Validate TEMP_PIN_ALLOWED_IND is updated in GROUP_ACCOUNT table.",""
					+ "Expected values:"+GA1_TEMP_PIN_ALLOWED_IND_0+"\nActual value:"+tempPinAlllwd, false);
		else
			Reporter.logEvent(Status.FAIL,"Validate TEMP_PIN_ALLOWED_IND is updated in GROUP_ACCOUNT table.",""
					+ "Expected values:"+GA1_TEMP_PIN_ALLOWED_IND_0+"\nActual value:"+tempPinAlllwd, false);
	}
	else{
		Reporter.logEvent(Status.FAIL, "Validate Record existance in Database.", "Record does not exist in database.", false);
	}
}
	
public void flushData(){
	queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("updateTempPinAllwdInd")[1], GET_GA_GA_ID_0);
	Reporter.logEvent(Status.INFO,"Reset the data.","Data has been reset.\nQuery:"+Stock.getTestQuery("updateTempPinAllwdInd")[1], false);
	}
}
