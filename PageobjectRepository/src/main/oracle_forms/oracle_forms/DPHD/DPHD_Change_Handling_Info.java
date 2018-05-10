package pageobject.DPHD;

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

public class DPHD_Change_Handling_Info {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DPHD_Change_Handling_Information";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0_X1;
	String DOC_HANDLING_CODE_0;
	String DOC_HANDLING_DESCR_0;
	
	String descr_before;
	
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
	public void setCONTROL_SELECTION_0_X1(String cONTROL_SELECTION_0_X1) {
		CONTROL_SELECTION_0_X1 = cONTROL_SELECTION_0_X1;
	}
	public void setDOC_HANDLING_CODE_0(String dOC_HANDLING_CODE_0) {
		DOC_HANDLING_CODE_0 = dOC_HANDLING_CODE_0;
	}
	public void setDOC_HANDLING_DESCR_0(String dOC_HANDLING_DESCR_0) {
		DOC_HANDLING_DESCR_0 = dOC_HANDLING_DESCR_0;
	}
	
	public void getData() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDPHD")[1], this.DOC_HANDLING_CODE_0);
		while(queryResultSet.next()){
			this.descr_before = queryResultSet.getString("DESCR");
		}
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SELECTION_0_X1(Stock.GetParameterValue("CONTROL_SELECTION_0_X1"));
		this.setDOC_HANDLING_CODE_0(Stock.GetParameterValue("DOC_HANDLING_CODE_0"));
		this.setDOC_HANDLING_DESCR_0(Stock.GetParameterValue("DOC_HANDLING_DESCR_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_SELECTION_0_X1="+CONTROL_SELECTION_0_X1+"&DOC_HANDLING_CODE_0="+DOC_HANDLING_CODE_0+"&DOC_HANDLING_DESCR_0="+DOC_HANDLING_DESCR_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for DPHD service", this.queryString.replaceAll("&", "\n"), false);
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
		//		System.out.println(serviceURL);
				doc.getDocumentElement().normalize();
				Reporter.logEvent(Status.PASS, "Run DPHD service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run DPHD service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From response", "StatusBarMessages: "+doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String hand_descr = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDPHD")[1], this.DOC_HANDLING_CODE_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: DOC_HANDLING", "Record exists in the Database", false);
			while(queryResultSet.next()){
				hand_descr = queryResultSet.getString("DESCR");
				Reporter.logEvent(Status.INFO, "From Database", "CODE: "+queryResultSet.getString("CODE")+
						"\nROUTING_DEST: "+queryResultSet.getString("ROUTING_DESTINATION")+
						"\nDESCR: "+queryResultSet.getString("DESCR"), false);
			}
			if(this.DOC_HANDLING_DESCR_0.contains(hand_descr)){
				Reporter.logEvent(Status.PASS, "Comapring DESCR from Input and Database", "Both the values are same as expected"+
			"\nFrom Input: "+this.DOC_HANDLING_DESCR_0+"\nFrom DB: "+hand_descr, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comapring DESCR from Input and Database", "Both the values are not same"+
						"\nFrom Input: "+this.DOC_HANDLING_DESCR_0+"\nFrom DB: "+hand_descr, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
	
	public void resetData(){
		String newServiceURL = "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DPHD_Change_Handling_Information";
		this.queryString = null;
		this.setDOC_HANDLING_DESCR_0(this.descr_before);
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_SELECTION_0_X1="+CONTROL_SELECTION_0_X1+"&DOC_HANDLING_CODE_0="+DOC_HANDLING_CODE_0+"&DOC_HANDLING_DESCR_0="+DOC_HANDLING_DESCR_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			newServiceURL += this.queryString;
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(newServiceURL);
			System.out.println("New URL: "+newServiceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run DPHD Reset service", "Data Reset Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DPHD Reset service", "Data Reset Failed:", false);
		}
		
	}
	
}
