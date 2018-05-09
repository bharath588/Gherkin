package pageobject.ESSB;

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

public class ESSB_Create_TradeSchedule {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ESSB_Create_TradeSchedule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GROUP_HEADER_ID_0;
	String TRADE_FREQ_LOV2;
	String QUERY_DETL_START_DATE_0;
	String QUERY_DETL_STOP_DATE_0;
	
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
	public void setGROUP_HEADER_ID_0(String gROUP_HEADER_ID_0) {
		GROUP_HEADER_ID_0 = gROUP_HEADER_ID_0;
	}
	public void setTRADE_FREQ_LOV2(String tRADE_FREQ_LOV2) {
		TRADE_FREQ_LOV2 = tRADE_FREQ_LOV2;
	}
	public void setQUERY_DETL_START_DATE_0(String qUERY_DETL_START_DATE_0) {
		QUERY_DETL_START_DATE_0 = qUERY_DETL_START_DATE_0;
	}
	public void setQUERY_DETL_STOP_DATE_0(String qUERY_DETL_STOP_DATE_0) {
		QUERY_DETL_STOP_DATE_0 = qUERY_DETL_STOP_DATE_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGROUP_HEADER_ID_0(Stock.GetParameterValue("GROUP_HEADER_ID_0"));
		this.setTRADE_FREQ_LOV2(Stock.GetParameterValue("TRADE_FREQ_LOV2"));
		this.setQUERY_DETL_START_DATE_0(Stock.GetParameterValue("QUERY_DETL_START_DATE_0"));
		this.setQUERY_DETL_STOP_DATE_0(Stock.GetParameterValue("QUERY_DETL_STOP_DATE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GROUP_HEADER_ID_0="+GROUP_HEADER_ID_0+"&TRADE_FREQ_LOV2="+TRADE_FREQ_LOV2+"&QUERY_DETL_START_DATE_0="+QUERY_DETL_START_DATE_0+
				"&QUERY_DETL_STOP_DATE_0="+QUERY_DETL_STOP_DATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for ESSB service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run ESSB service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run ESSB service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "GC NAME: "+ doc.getElementsByTagName("GROUP_HEADER_GC_NAME_0").item(0).getTextContent()+
					"\nIRS CODE: "+doc.getElementsByTagName("GROUP_HEADER_IRS_CODE_0").item(0).getTextContent()+
					"\nPLAN NAME: "+doc.getElementsByTagName("GROUP_HEADER_PLAN_NAME_0").item(0).getTextContent()+
					"\nPROD ID: "+doc.getElementsByTagName("GROUP_HEADER_PROD_ID_0").item(0).getTextContent()+
					"\nSDIO ID: "+doc.getElementsByTagName("QUERY_DETL_SDIO_ID_0").item(0).getTextContent()+
					"\nSDIO NAME: "+doc.getElementsByTagName("QUERY_DETL_SDIO_NAME_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String ga_id= this.GROUP_HEADER_ID_0;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForESSB")[1], ga_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
							
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: STOCK_TRADE", 
						"Total number of records: "+DB.getRecordSetCount(queryResultSet), false);
					
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
}
