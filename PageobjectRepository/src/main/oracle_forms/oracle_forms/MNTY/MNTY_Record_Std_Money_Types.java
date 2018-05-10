package pageobject.MNTY;

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

public class MNTY_Record_Std_Money_Types {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MNTY_Create";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SDMT1_CODE_0;
	String SDMT1_DESCR_0;
	String SDMT1_SOURCE_CODE_0;
	String TAX_STATUS_LOV;
	
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
	public void setSDMT1_CODE_0(String sDMT1_CODE_0) {
		SDMT1_CODE_0 = sDMT1_CODE_0;
	}
	public void setSDMT1_DESCR_0(String sDMT1_DESCR_0) {
		SDMT1_DESCR_0 = sDMT1_DESCR_0;
	}
	public void setSDMT1_SOURCE_CODE_0(String sDMT1_SOURCE_CODE_0) {
		SDMT1_SOURCE_CODE_0 = sDMT1_SOURCE_CODE_0;
	}
	public void setTAX_STATUS_LOV(String tAX_STATUS_LOV) {
		TAX_STATUS_LOV = tAX_STATUS_LOV;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSDMT1_CODE_0(Stock.GetParameterValue("SDMT1_CODE_0"));
		this.setSDMT1_DESCR_0(Stock.GetParameterValue("SDMT1_DESCR_0"));
		this.setSDMT1_SOURCE_CODE_0(Stock.GetParameterValue("SDMT1_SOURCE_CODE_0"));
		this.setTAX_STATUS_LOV(Stock.GetParameterValue("TAX_STATUS_LOV"));
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCreateMoneyTypes")[1],  this.SDMT1_CODE_0);
		if(queryResultSet.next()){
			DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDeleteMoneyTypes")[1],  this.SDMT1_CODE_0);
		}
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SDMT1_CODE_0="+SDMT1_CODE_0+"&SDMT1_DESCR_0="+SDMT1_DESCR_0+"&SDMT1_SOURCE_CODE_0="+SDMT1_SOURCE_CODE_0+"&TAX_STATUS_LOV="+TAX_STATUS_LOV+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for MNTY Create Money Type service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run MNTY service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run MNTY service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "TAX_STATUS: "+ doc.getElementsByTagName("SDMT1_TAX_STATUS_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String TAX_STAT = doc.getElementsByTagName("SDMT1_TAX_STATUS_0").item(0).getTextContent();
		String tax_stat = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCreateMoneyTypes")[1],  this.SDMT1_CODE_0);
		
		if(queryResultSet.next()){
			tax_stat = queryResultSet.getString("TAX_STATUS");
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for New Money Type", false);
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "Table: STD_MNTY\nCODE: "+queryResultSet.getString("CODE")+
					"\nDESCR: "+queryResultSet.getString("DESCR")+			
					"\nTAX STATUS: "+queryResultSet.getString("TAX_STATUS")+			
					"\nSOURCE CODE: "+queryResultSet.getString("SOURCE_CODE"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(TAX_STAT.equalsIgnoreCase(tax_stat)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating TAX STATUS from Response and Database", "Both the values are same as Expected"+
					"\nTAX STATUS: "+"From Response: "+TAX_STAT+"\nFrom Database: "+tax_stat, false);
		}
	}
	
}
