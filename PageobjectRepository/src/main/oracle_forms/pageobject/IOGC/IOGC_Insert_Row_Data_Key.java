package pageobject.IOGC;

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

public class IOGC_Insert_Row_Data_Key {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/IOGC_Insert_New_Row_Data_Key";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String IOAG1_GA_ID_2;
	String IOAG1_DATA_KEY_2;
	String IOAG1_DATA_VALUE_2;
	
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
	public void setIOAG1_GA_ID_2(String iOAG1_GA_ID_2) {
		IOAG1_GA_ID_2 = iOAG1_GA_ID_2;
	}
	public void setIOAG1_DATA_KEY_2(String iOAG1_DATA_KEY_2) {
		IOAG1_DATA_KEY_2 = iOAG1_DATA_KEY_2;
	}
	public void setIOAG1_DATA_VALUE_2(String iOAG1_DATA_VALUE_2) {
		IOAG1_DATA_VALUE_2 = iOAG1_DATA_VALUE_2;
	}
	
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setIOAG1_GA_ID_2(Stock.GetParameterValue("IOAG1_GA_ID_2"));
		this.setIOAG1_DATA_KEY_2(Stock.GetParameterValue("IOAG1_DATA_KEY_2"));
		this.setIOAG1_DATA_VALUE_2(Stock.GetParameterValue("IOAG1_DATA_VALUE_2"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&IOAG1_DATA_KEY_2="+IOAG1_DATA_KEY_2+"&IOAG1_GA_ID_2="+IOAG1_GA_ID_2+"&IOAG1_DATA_VALUE_2="+IOAG1_DATA_VALUE_2+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for IOGC service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run IOGC service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run IOGC service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response", "DATA KEY: "+doc.getElementsByTagName("IOAG1_DATA_KEY_0").item(0).getTextContent(), false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String data_key = null;
		String data_key_resp=doc.getElementsByTagName("IOAG1_DATA_KEY_0").item(0).getTextContent();
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForIOGCGA_ID")[1], this.IOAG1_GA_ID_2, data_key_resp);
		if(DB.getRecordSetCount(queryResultSet)>0){
			while(queryResultSet.next()){
				Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
				data_key = queryResultSet.getString("DATA_KEY");
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: IOAG_CUSTOMIZATION", 
						"Data Key "+queryResultSet.getString("DATA_KEY")+
						"\nProv Company "+queryResultSet.getString("PROV_COMPANY")+
						"\nData Value "+queryResultSet.getString("DATA_VALUE")+
						"\nGA_ID"+queryResultSet.getString("GA_ID"), false);
				
				if(data_key_resp.equalsIgnoreCase(data_key)){
					Reporter.logEvent(Status.PASS, "Comparing and validating Data key from Response and Database","Both values are same as expected"+
				"\nFrom response: "+data_key_resp+"\nFrom database: "+data_key,false);
				}
				else{
					Reporter.logEvent(Status.FAIL, "Comparing and validating Data key from Response and Database","Values are not same as expected",false);
				}
			}	
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
		
	}

	
}
