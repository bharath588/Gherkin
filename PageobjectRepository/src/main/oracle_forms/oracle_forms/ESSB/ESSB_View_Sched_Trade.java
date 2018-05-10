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

public class ESSB_View_Sched_Trade {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/ESSB_View_Schedule_Trade_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GROUP_HEADER_ID_0;
	String param27067;
	String GROUP_HEADER_ID_0_X1;
	String param270102;
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
	public void setParam27067(String param27067) {
		this.param27067 = param27067;
	}
	public void setGROUP_HEADER_ID_0_X1(String gROUP_HEADER_ID_0_X1) {
		GROUP_HEADER_ID_0_X1 = gROUP_HEADER_ID_0_X1;
	}
	public void setParam270102(String param270102) {
		this.param270102 = param270102;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGROUP_HEADER_ID_0(Stock.GetParameterValue("GROUP_HEADER_ID_0"));
		this.setGROUP_HEADER_ID_0_X1(Stock.GetParameterValue("GROUP_HEADER_ID_0_X1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GROUP_HEADER_ID_0="+GROUP_HEADER_ID_0+
				"&GROUP_HEADER_ID_0_X1="+GROUP_HEADER_ID_0_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for ESSB Delete service", this.queryString.replaceAll("&", "\n"), false);
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
			//System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run ESSB View service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run ESSB View service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
				
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From Response",
					"\nSDIO_ID: "+doc.getElementsByTagName("QUERY_DETL_SDIO_ID_0").item(0).getTextContent()+
					"\nTRADE_DATE: "+doc.getElementsByTagName("STOCK_TRADE_TRADE_DATE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String trade_date = doc.getElementsByTagName("STOCK_TRADE_TRADE_DATE_0").item(0).getTextContent();
		String sdio_id = doc.getElementsByTagName("QUERY_DETL_SDIO_ID_0").item(0).getTextContent();
		String sdio_id_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForESSBView")[1], this.GROUP_HEADER_ID_0, trade_date);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of records in Database", "Records exists in Database\nTable Name: STOCK_TRADE", false);
			
				Reporter.logEvent(Status.INFO, "Values from Database","Expected: Query should return 1 or more records\n"+
			"Total number of records: "+DB.getRecordSetCount(queryResultSet), false);
			while(queryResultSet.next()){
				sdio_id_db = queryResultSet.getString("SDIO_ID");
				Reporter.logEvent(Status.INFO, "From Database", "GA_ID: "+queryResultSet.getString("GA_ID")+
						"\nSDIO_ID: "+queryResultSet.getString("SDIO_ID")+
						"\nTRADE_DATE: "+queryResultSet.getString("TRADE_DATE"), false);
			}
			if(sdio_id.equalsIgnoreCase(sdio_id_db)){
				Reporter.logEvent(Status.PASS, "Comparing SDIO_ID from Response and Datbase", "Both values are same as expected"+
						"\nFrom Response: "+sdio_id+"\nFrom Database: "+sdio_id_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing SDIO_ID from Response and Datbase", "Both values are not same"+
						"\nFrom Response: "+sdio_id+"\nFrom Database: "+sdio_id_db, false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of records in Database", "Records doesn't exists in Database\nTable Name: STOCK_TRADE", false);
		}
	}
}
