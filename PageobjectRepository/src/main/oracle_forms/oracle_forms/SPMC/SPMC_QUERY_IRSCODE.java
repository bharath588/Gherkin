package pageobject.SPMC;

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

public class SPMC_QUERY_IRSCODE {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SPMC_QUERY_IRSCODE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SELECTION_IRS_CODE_0;
	String SELECTION_BEGIN_EFFDATE_0;
	String SELECTION_END_EFFDATE_0;
	
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
	public void setSELECTION_IRS_CODE_0(String sELECTION_IRS_CODE_0) {
		SELECTION_IRS_CODE_0 = sELECTION_IRS_CODE_0;
	}
	public void setSELECTION_BEGIN_EFFDATE_0(String sELECTION_BEGIN_EFFDATE_0) {
		SELECTION_BEGIN_EFFDATE_0 = sELECTION_BEGIN_EFFDATE_0;
	}
	public void setSELECTION_END_EFFDATE_0(String sELECTION_END_EFFDATE_0) {
		SELECTION_END_EFFDATE_0 = sELECTION_END_EFFDATE_0;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSELECTION_BEGIN_EFFDATE_0(Stock.GetParameterValue("SELECTION_BEGIN_EFFDATE_0"));
		this.setSELECTION_END_EFFDATE_0(Stock.GetParameterValue("SELECTION_END_EFFDATE_0"));
		this.setSELECTION_IRS_CODE_0(Stock.GetParameterValue("SELECTION_IRS_CODE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SELECTION_IRS_CODE_0="+SELECTION_IRS_CODE_0+"&SELECTION_BEGIN_EFFDATE_0="+SELECTION_BEGIN_EFFDATE_0+"&SELECTION_END_EFFDATE_0="+SELECTION_END_EFFDATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SPMC_QUERY_IRSCODE service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run SPMC_QUERY_IRSCODE service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SPMC_QUERY_IRSCODE service", "Running Failed:", false);
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
	
	public void validateInDatabase() throws SQLException
	{
		queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("SPMC_QUERY_IRSCODE")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Verifies whether record exists in Database.", "Records exists in Database\nTableName: STMT_ACTY_PERD\n"
					+ "GA_ID\n"
					+ "GA_NAME\n" 
					+ "STMT_HANDLING_CODE\n"
					+ "IRSRL_CODE \n"
					+ "ID\n"
					+ "\nEND_EFFDATE", false);
			Reporter.logEvent(Status.INFO,"Record count in DB","Record Count:"+DB.getRecordSetCount(queryResultSet), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Verifies whether records exist in Database.", "Records do not exist into Database.", false);
		}
		
		while(queryResultSet.next())
		{
			String ga_idDB = queryResultSet.getString("GA_ID");
			String ga_idForms = doc.getElementsByTagName("GROUP_ACCOUNT_GA_ID_0").item(0).getTextContent();
			if(ga_idDB.equalsIgnoreCase(ga_idForms))
			{
				Reporter.logEvent(Status.PASS,"Validating if DB value matches with the response.Response value:"+ga_idForms, 
						"DB value matches with the response.DB Value:"+ga_idDB,false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Validating if DB value matches with the response.Response value:"+ga_idForms, 
						"DB value matches with the response.DB Value:"+ga_idDB,false);
			}
			
		}
	}
}
