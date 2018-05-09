package pageobject.MVPJ;

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

public class MVPJ_Change_Market_Value_Offered_Rates {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MVPJ_CHANGE_MARKET_VALUE_OFFERED_RATES";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;	
	ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String MARKET_VALUE_RATE_MARKET_VALUE_RATE_0;
	String MARKET_VALUE_RATE_MARKET_VALUE_RATE_0_X1;
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
	public void setMARKET_VALUE_RATE_MARKET_VALUE_RATE_0(
			String mARKET_VALUE_RATE_MARKET_VALUE_RATE_0) {
		MARKET_VALUE_RATE_MARKET_VALUE_RATE_0 = mARKET_VALUE_RATE_MARKET_VALUE_RATE_0;
	}
	public void setMARKET_VALUE_RATE_MARKET_VALUE_RATE_0_X1(
			String mARKET_VALUE_RATE_MARKET_VALUE_RATE_0_X1) {
		MARKET_VALUE_RATE_MARKET_VALUE_RATE_0_X1 = mARKET_VALUE_RATE_MARKET_VALUE_RATE_0_X1;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setMARKET_VALUE_RATE_MARKET_VALUE_RATE_0(Stock.GetParameterValue("MARKET_VALUE_RATE_MARKET_VALUE_RATE_0"));
		this.setMARKET_VALUE_RATE_MARKET_VALUE_RATE_0_X1(Stock.GetParameterValue("MARKET_VALUE_RATE_MARKET_VALUE_RATE_0_X1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&MARKET_VALUE_RATE_MARKET_VALUE_RATE_0="+MARKET_VALUE_RATE_MARKET_VALUE_RATE_0+"&MARKET_VALUE_RATE_MARKET_VALUE_RATE_0_X1="+MARKET_VALUE_RATE_MARKET_VALUE_RATE_0_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for MVPJ service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() {
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
			Reporter.logEvent(Status.PASS, "Run MVPJ service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run MVPJ service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
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
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForMVPJ1")[1], this.GET_GA_GA_ID_0, this.MARKET_VALUE_RATE_MARKET_VALUE_RATE_0_X1);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: MARKET_VALUE_RATE", false);
			while(queryResultSet.next()){				
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "GA_ID: "+queryResultSet.getString("GA_ID")+
					"\nSDTE_LENGTH: "+queryResultSet.getString("SDTE_LENGTH")+
					"\nSDTE_LENGTH_QUAL: "+queryResultSet.getString("SDTE_LENGTH_QUAL")+
					"\nMARKET_VALUE_RATE: "+queryResultSet.getString("MARKET_VALUE_RATE"), false);
			}
		}	
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForMVPJ2")[1]);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: EVENT", false);
			while(queryResultSet.next()){				
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ID: "+queryResultSet.getString("ID")+
					"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+
					"\nLOGON_ID: "+queryResultSet.getString("LOG_USER_LOGON_ID")+
					"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME"), false);
			}
		}	
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
}
