package pageobject.SSPU;

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

public class SSPU_Setup_Stmt_Special_Processing {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SSPU_Setup_Stmt_Special_Processing";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SELECTION_PROV_CO_0;
	String GROUP_ACCOUNT_SELECT_IND_0;
	String GROUP_INFO_DIVIDEND_IND_0;
	String GROUP_INFO_QA_SAMPLING_USER_ID_0;
	
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
	public void setSELECTION_PROV_CO_0(String sELECTION_PROV_CO_0) {
		SELECTION_PROV_CO_0 = sELECTION_PROV_CO_0;
	}
	public void setGROUP_ACCOUNT_SELECT_IND_0(String gROUP_ACCOUNT_SELECT_IND_0) {
		GROUP_ACCOUNT_SELECT_IND_0 = gROUP_ACCOUNT_SELECT_IND_0;
	}
	public void setGROUP_INFO_DIVIDEND_IND_0(String gROUP_INFO_DIVIDEND_IND_0) {
		GROUP_INFO_DIVIDEND_IND_0 = gROUP_INFO_DIVIDEND_IND_0;
	}
	public void setGROUP_INFO_QA_SAMPLING_USER_ID_0(
			String gROUP_INFO_QA_SAMPLING_USER_ID_0) {
		GROUP_INFO_QA_SAMPLING_USER_ID_0 = gROUP_INFO_QA_SAMPLING_USER_ID_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGROUP_ACCOUNT_SELECT_IND_0(Stock.GetParameterValue("GROUP_ACCOUNT_SELECT_IND_0"));
		this.setGROUP_INFO_DIVIDEND_IND_0(Stock.GetParameterValue("GROUP_INFO_DIVIDEND_IND_0"));
		this.setGROUP_INFO_QA_SAMPLING_USER_ID_0(Stock.GetParameterValue("GROUP_INFO_QA_SAMPLING_USER_ID_0"));
		this.setSELECTION_PROV_CO_0(Stock.GetParameterValue("SELECTION_PROV_CO_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SELECTION_PROV_CO_0="+SELECTION_PROV_CO_0+"&GROUP_ACCOUNT_SELECT_IND_0="+GROUP_ACCOUNT_SELECT_IND_0+"&GROUP_INFO_DIVIDEND_IND_0="+GROUP_INFO_DIVIDEND_IND_0+
				"&GROUP_INFO_QA_SAMPLING_USER_ID_0="+GROUP_INFO_QA_SAMPLING_USER_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SSPU service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run SSPU service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SSPU service", "Running Failed:", false);
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
	
	public void validateInDatabase()throws SQLException{
		
		String user_id = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSSPU")[1], this.GROUP_INFO_QA_SAMPLING_USER_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
			
			while(queryResultSet.next()){
				user_id = queryResultSet.getString("QA_SAMPLING_USER_ID");
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: STMT_SPECIAL_PROCESSING", "Expected: Display row for the group account along with indicators set to Y or N based on test inputs."+
						"\nGA_ID: "+queryResultSet.getString("GA_ID")+
						"\nDIVIDEND_IND: "+queryResultSet.getString("DIVIDEND_IND")+
						"\nQA_SAMPLING_USER_ID: "+queryResultSet.getString("QA_SAMPLING_USER_ID"), false);
			}
			if(this.GROUP_INFO_QA_SAMPLING_USER_ID_0.equalsIgnoreCase(user_id)){
				Reporter.logEvent(Status.PASS, "Comparing and validating User ID from Input and Database", "Both the values are same as expected"+
			"\nFrom Input: "+this.GROUP_INFO_QA_SAMPLING_USER_ID_0+"\nFrom Database: "+user_id, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and validating User ID from Input and Database", "Both the values are not same "+
						"\nFrom Input: "+this.GROUP_INFO_QA_SAMPLING_USER_ID_0+"\nFrom Database: "+user_id, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
				
	}
}
