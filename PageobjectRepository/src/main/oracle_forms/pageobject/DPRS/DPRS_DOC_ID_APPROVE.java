/**
 * 
 */
package pageobject.DPRS;

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
public class DPRS_DOC_ID_APPROVE {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DPRS_DOC_ID_APPROVE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String DPDQ_PRINT_CNTL_DOC_ID_0;
	String QUERY_SEL_SELECT_0;
	
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
	public void setDPDQ_PRINT_CNTL_DOC_ID_0(String dPDQ_PRINT_CNTL_DOC_ID_0) {
		DPDQ_PRINT_CNTL_DOC_ID_0 = dPDQ_PRINT_CNTL_DOC_ID_0;
	}
	public void setQUERY_SEL_SELECT_0(String qUERY_SEL_SELECT_0) {
		QUERY_SEL_SELECT_0 = qUERY_SEL_SELECT_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setDPDQ_PRINT_CNTL_DOC_ID_0(Stock.GetParameterValue("DPDQ_PRINT_CNTL_DOC_ID_0"));
		this.setQUERY_SEL_SELECT_0(Stock.GetParameterValue("QUERY_SEL_SELECT_0"));
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&DPDQ_PRINT_CNTL_DOC_ID_0="+DPDQ_PRINT_CNTL_DOC_ID_0+"&LOGON_DATABASE="+LOGON_DATABASE+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+"&QUERY_SEL_SELECT_0="+QUERY_SEL_SELECT_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for DPRS_DOC_ID_APPROVE service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run DPRS_DOC_ID_APPROVE service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DPRS_DOC_ID_APPROVE service", "Running Failed:", false);
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
	
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("DPRS_DOC_ID_APPROVE")[1],DPDQ_PRINT_CNTL_DOC_ID_0 );
		String status=null;
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists in DB.\nTable Name:dpx_control", "Records exists in DB.", false);
			status = queryResultSet.getString("STATUS_CODE");
			if(status.equals("APPROVED"))
				Reporter.logEvent(Status.PASS,"Validate status from DB.\nQuery:"+Stock.getTestQuery("DPRS_DOC_ID_APPROVE")[1]+
						"\nExpected:APPROVED","Actual:"+status,false );
			else
				Reporter.logEvent(Status.FAIL,"Validate status from DB.\nQuery:"+Stock.getTestQuery("DPRS_DOC_ID_APPROVE")[1]+
						"\nExpected:APPROVED","Actual:"+status,false );
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database.\nTableName:dpx_control", "No records exists in Database Table", false);
		}
		
	}
	
	
	public void flushData() throws Exception{
		DB.executeUpdate(General.dbInstance,Stock.getTestQuery("updateDPRSdocStatus")[1],DPDQ_PRINT_CNTL_DOC_ID_0);
		Reporter.logEvent(Status.INFO,"Reset data.\nQuery:"+Stock.getTestQuery("updateDPRSdocStatus")[1],"Data has been reset.", false);
	}
}
