/**
 * 
 */
package pageobject.UDFR;

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
public class UDFR_newrequestid {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/UDFR_newrequestid";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0;
	String CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0;
	String CONTROL_BLOCK_NEW_REQ_NEED_BY_DATE_0;
	
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
	public void setCONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0(String cONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0) {
		CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0 = cONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0;
	}
	public void setCONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0(String cONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0) {
		CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0 = cONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0;
	}
	public void setCONTROL_BLOCK_NEW_REQ_NEED_BY_DATE_0(String cONTROL_BLOCK_NEW_REQ_NEED_BY_DATE_0) {
		CONTROL_BLOCK_NEW_REQ_NEED_BY_DATE_0 = cONTROL_BLOCK_NEW_REQ_NEED_BY_DATE_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0(Stock.GetParameterValue("CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0"));
		this.setCONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0(Stock.GetParameterValue("CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0"));
		this.setCONTROL_BLOCK_NEW_REQ_NEED_BY_DATE_0(Stock.GetParameterValue("CONTROL_BLOCK_NEW_REQ_NEED_BY_DATE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0="+CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0+
				"&CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0="+CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0+
				"&CONTROL_BLOCK_NEW_REQ_NEED_BY_DATE_0="+CONTROL_BLOCK_NEW_REQ_NEED_BY_DATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for UDFR_newrequestid service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run UDFR_newrequestid service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run UDFR_newrequestid service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
	
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response:", ""
					+ "StatusBarMessages="+doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	

	public void validateInDatabase() throws SQLException{	
		String reason=null;
		String reqnbr = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("udfrNewUpload")[1],
				this.CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0,this.CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Table Name: upload_deferral_request", false);
			reason = queryResultSet.getString("UPLOAD_REASON");
			reqnbr = queryResultSet.getString("SERVICE_REQUEST_NBR");
			Reporter.logEvent(Status.INFO, "Values From Database: ", 					
					"UPLOAD_REASON: "+reason+					
					"\nSERVICE_REQUEST_NBR: "+reqnbr
					, false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
	}
	
	public void dataFlush(){
		DB.executeQuery(General.dbInstance, Stock.getTestQuery("DeleteudfrRecord")[1],
				this.CONTROL_BLOCK_NEW_REQ_SERVICE_REQUEST_NBR_0,this.CONTROL_BLOCK_NEW_REQ_UPLOAD_REASON_0);
			Reporter.logEvent(Status.INFO, "Reset the data.","Data has been reset.\n"+ Stock.getTestQuery("DeleteudfrRecord")[1], false);
	}
}
