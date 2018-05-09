package pageobject.STSV;

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

public class STSV_Add_Std_Share_Value {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STSV_Add_Standar_Share_Value";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String HEADER_INVEST_ID_0;
	String SHARE_VALUE_NET_ASSET_VALUE_0;
	String SHARE_VALUE_CHECKED_IND_0;
	
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
	public void setHEADER_INVEST_ID_0(String hEADER_INVEST_ID_0) {
		HEADER_INVEST_ID_0 = hEADER_INVEST_ID_0;
	}
	public void setSHARE_VALUE_NET_ASSET_VALUE_0(
			String sHARE_VALUE_NET_ASSET_VALUE_0) {
		SHARE_VALUE_NET_ASSET_VALUE_0 = sHARE_VALUE_NET_ASSET_VALUE_0;
	}
	public void setSHARE_VALUE_CHECKED_IND_0(String sHARE_VALUE_CHECKED_IND_0) {
		SHARE_VALUE_CHECKED_IND_0 = sHARE_VALUE_CHECKED_IND_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setHEADER_INVEST_ID_0(Stock.GetParameterValue("HEADER_INVEST_ID_0"));
		this.setSHARE_VALUE_CHECKED_IND_0(Stock.GetParameterValue("SHARE_VALUE_CHECKED_IND_0"));
		this.setSHARE_VALUE_NET_ASSET_VALUE_0(Stock.GetParameterValue("SHARE_VALUE_NET_ASSET_VALUE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&HEADER_INVEST_ID_0="+HEADER_INVEST_ID_0+"&SHARE_VALUE_NET_ASSET_VALUE_0="+SHARE_VALUE_NET_ASSET_VALUE_0+"&SHARE_VALUE_CHECKED_IND_0="+SHARE_VALUE_CHECKED_IND_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for STSV service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run STSV service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run STSV service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response: ", 
					"\nTRANSACTION CODE: "+doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent()+
					"\nDESCR: "+ doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent()
					, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{	
		String asset_value = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTSV_Add1")[1], this.HEADER_INVEST_ID_0, this.SHARE_VALUE_NET_ASSET_VALUE_0);			
			if(DB.getRecordSetCount(queryResultSet)>0){
				Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: SHARE_VALUE", "Record exists in the Database", false);
				while(queryResultSet.next()){		
					asset_value = queryResultSet.getString("NET_ASSET_VALUE");
					Reporter.logEvent(Status.INFO, "From Database: ", "ID: "+queryResultSet.getString("INVEST_ID")+
							"\nNET_ASSET_VALUE: "+queryResultSet.getString("NET_ASSET_VALUE")+
							"\nBUY_VALUE: "+queryResultSet.getString("PREV_BUY_VALUE"), false);
				}
			}
			else{
				Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
			}
		
			queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTSV_Add2")[1], this.HEADER_INVEST_ID_0);			
			if(DB.getRecordSetCount(queryResultSet)>0){
				Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: INV_PULL", "Record exists in the Database", false);
				while(queryResultSet.next()){				
					Reporter.logEvent(Status.INFO, "From Database: ", "ID: "+queryResultSet.getString("ID")+
							"\nSDIO_ID: "+queryResultSet.getString("SDIO_ID")+
							"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
							"\nACCUM_UNIT_VALUE: "+queryResultSet.getString("PREV_ACCUM_UNIT_VALUE"), false);
				}
			}
			else{
				Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}	
	}
}
