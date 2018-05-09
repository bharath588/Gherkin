package pageobject.QYGR;

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

public class QYGR_Test_Query_Group {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/QYGR_Test_Query_Group_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	
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
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 + "&GET_GA_GA_ID_0=" + GET_GA_GA_ID_0 
				+"&numOfRowsInTable=20&json=false&handlePopups=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for QYGR service", this.queryString.replaceAll("&", "\n"), false);
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
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run QYGR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run QYGR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","GA1_PROD_ID_0: "+doc.getElementsByTagName("GA1_PROD_ID_0").item(0).getTextContent()+
					"\nGROUP_HEADER1_DISP_PROV_COMPANY_0: "+doc.getElementsByTagName("GROUP_HEADER1_DISP_PROV_COMPANY_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase() throws SQLException{
		
		String prod_id = doc.getElementsByTagName("GA1_PROD_ID_0").item(0).getTextContent();
		String prov_co = doc.getElementsByTagName("GROUP_HEADER1_DISP_PROV_COMPANY_0").item(0).getTextContent();
		String prod_id_db = null;
		String prov_co_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForQYGR1")[1], this.GET_GA_GA_ID_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: GROUP_ACCOUNT", false);
			while(queryResultSet.next()){
				prod_id_db = queryResultSet.getString("PROD_ID");
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ID:  "+ queryResultSet.getString("ID")+
		 				"\nSTATUS_CODE: "+ queryResultSet.getString("STATUS_CODE")+
						"\nPRODID: "+queryResultSet.getString("PROD_ID"), false);
			}
			if(prod_id.equalsIgnoreCase(prod_id_db)){
				Reporter.logEvent(Status.PASS, "Comparing and Verifying PROD_ID from Response and Database", "Both the values are same as expected"+
						"\nFrom Response: " +prod_id+"\nFrom Database: "+prod_id_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Verifying PROD_ID from Response and Database", "Both the values are not same"+
						"\nFrom Response: " +prod_id+"\nFrom Database: "+prod_id_db, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForQYGR2")[1], prod_id);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: PRODUCT", false);
			while(queryResultSet.next()){
				prov_co_db = queryResultSet.getString("PROV_COMPANY");
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ID:  "+ queryResultSet.getString("ID")+
						"\nNAME: "+ queryResultSet.getString("NAME")+
						"\nPROV_COMPANY: "+queryResultSet.getString("PROV_COMPANY"), false);
			}
			if(prov_co.equalsIgnoreCase(prov_co_db)){
				Reporter.logEvent(Status.PASS, "Comparing and Verifying PROV_COMPANY from Response and Database", "Both the values are same as expected"+
						"\nFrom Response: " +prov_co+"\nFrom Database: "+prov_co_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Verifying PROV_COMPANY from Response and Database", "Both the values are not same"+
						"\nFrom Response: " +prov_co+"\nFrom Database: "+prov_co_db, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
}
